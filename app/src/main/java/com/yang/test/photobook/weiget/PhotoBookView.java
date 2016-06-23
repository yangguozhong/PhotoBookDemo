package com.yang.test.photobook.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yang.test.photobook.BookBean;

public class PhotoBookView extends ViewGroup {
    private PhotoBookView photoBookLayout;
    private BookBean bookBean;
    private View photoView;
    private View textView;
    private int photo_padding = 0;
    private int index;
    private Context mContext;
    private BookBean.BandTemplate mTemplate;
    private BookBean.BandTemplate.TextEntity textEntity;
    private BookBean.BandTemplate.ImageEntity imageEntity;
    private float scale = 1.0f;

    public void setScale(float scale) {
        this.scale = scale;
    }

    public PhotoBookView(Context context) {
        super(context, null);
    }

    public PhotoBookView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PhotoBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        photoBookLayout = this;
        photoView = getChildAt(0);
        textView = getChildAt(1);
    }

    /**
     * view 属于第几个条目
     *
     * @param bookBean
     * @param index    数据第几个模板
     */
    public void setBookBean(BookBean bookBean, int index) {
        this.bookBean = bookBean;
        this.index = index;
        try {
            if (bookBean.getBand() != null && bookBean.getBand().size() > 0) {
                mTemplate = bookBean.getBand().get(index);
                textEntity = mTemplate.getText();
                imageEntity = mTemplate.getImage();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        invalidate();

    }

    public void updateView() {
        requestLayout();
        invalidate();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);
        int left = 0;
        int top = 0;
        if (bookBean == null) {
            setMeasuredDimension(rw, rh);
        } else {
            if (mTemplate != null) {
//                setMeasuredDimension(dip2px(mContext, bookBean.getWidth()), dip2px(mContext, bookBean.getHeight()));
                if (imageEntity != null) {
                    photoView = getChildAt(0);
                    LayoutParams layoutParams = (LayoutParams) photoView.getLayoutParams();
                    layoutParams.left = left + scaleView(imageEntity.getX());
                    layoutParams.top = top + scaleView(imageEntity.getY());
                    layoutParams.width = scaleView(imageEntity.getWidth());
                    layoutParams.height = scaleView(imageEntity.getHeight());
                    photoView.measure(layoutParams.width, layoutParams.height);
                }
                if (textEntity != null) {
                    textView = getChildAt(1);
                    LayoutParams photoLayoutParams = (LayoutParams) textView.getLayoutParams();
                    photoLayoutParams.left = left + scaleView(textEntity.getX());
                    photoLayoutParams.top = top + scaleView(textEntity.getY());
                    photoLayoutParams.width = scaleView(textEntity.getWidth());
                    photoLayoutParams.height = scaleView(textEntity.getHeight());
                    textView.measure(photoLayoutParams.width, photoLayoutParams.height);
                }else{
                    textView.setVisibility(View.GONE);
                }
            }
            setMeasuredDimension(scaleView(bookBean.getWidth()), scaleView(bookBean.getHeight()));
        }
    }

    private int scaleView(int value) {
        return (int) (scale * value);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        photoView = getChildAt(0);
        textView = getChildAt(1);
        LayoutParams childBackgroundLayoutParams = (LayoutParams) photoView.getLayoutParams();
        photoView.layout(childBackgroundLayoutParams.left,
                childBackgroundLayoutParams.top,
                childBackgroundLayoutParams.left + childBackgroundLayoutParams.width,
                childBackgroundLayoutParams.top + childBackgroundLayoutParams.height);
//        photoView.setBackgroundColor(Color.parseColor("#ffffff"));

        LayoutParams photoLayoutParams = (LayoutParams) textView.getLayoutParams();
        textView.layout(photoLayoutParams.left,
                photoLayoutParams.top,
                photoLayoutParams.left + photoLayoutParams.width,
                photoLayoutParams.top + photoLayoutParams.height);

    }

//    @Override
//    public android.view.ViewGroup.LayoutParams generateLayoutParams(
//            AttributeSet attrs) {
//        return new Custom_ImageView.LayoutParams(getContext(), attrs);
//    }
//
//    @Override
//    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
//        return new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof PhotoBookView.LayoutParams;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int left = 0;
        public int top = 0;
        public int right = 0;
        public int bottom = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }
}
