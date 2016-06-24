package com.yang.test.photobook.weiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yang.test.photobook.BookTemplate;

public class PhotoBookView extends ViewGroup {
    private PhotoBookView photoBookLayout;
    private BookTemplate bookTemplate;
    private View photoView;
    private RelativeLayout container;
    private int photo_padding = 0;
    private int index;
    private Context mContext;
    private BookTemplate.BandTemplate mTemplate;
    private BookTemplate.BandTemplate.TextEntity textEntity;
    private BookTemplate.BandTemplate.ImageEntity imageEntity;
    private double scale = 1.0f;

    public void setScale(double scale) {
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
        container = (RelativeLayout) getChildAt(1);
    }

    /**
     * view 属于第几个条目
     *
     * @param bookTemplate
     * @param index    数据第几个模板
     */
    public void setBookBean(BookTemplate bookTemplate, int index) {
        this.bookTemplate = bookTemplate;
        this.index = index;
        try {
            if (bookTemplate.getBand() != null && bookTemplate.getBand().size() > 0) {
                mTemplate = bookTemplate.getBand().get(index);
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
        int left = 0;
        int top = 0;
        if (bookTemplate == null) {
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        } else {
            if (mTemplate != null) {
//                setMeasuredDimension(dip2px(mContext, bookTemplate.getWidth()), dip2px(mContext, bookTemplate.getHeight()));
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
                    container = (RelativeLayout) getChildAt(1);
                    LayoutParams photoLayoutParams = (LayoutParams) container.getLayoutParams();
                    photoLayoutParams.left = left + scaleView(textEntity.getX());
                    photoLayoutParams.top = top + scaleView(textEntity.getY());
                    photoLayoutParams.width = scaleView(textEntity.getWidth());
                    photoLayoutParams.height = scaleView(textEntity.getHeight());
                    container.measure(photoLayoutParams.width, photoLayoutParams.height);
                } else {
                    container.setVisibility(View.GONE);
                }
            }
            setMeasuredDimension(scaleView(bookTemplate.getWidth()), scaleView(bookTemplate.getHeight()));
        }
    }

    private int scaleView(int value) {
        return (int) (scale * value);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        photoView = getChildAt(0);
        container = (RelativeLayout) getChildAt(1);
        LayoutParams childBackgroundLayoutParams = (LayoutParams) photoView.getLayoutParams();
        photoView.layout(childBackgroundLayoutParams.left,
                childBackgroundLayoutParams.top,
                childBackgroundLayoutParams.left + childBackgroundLayoutParams.width,
                childBackgroundLayoutParams.top + childBackgroundLayoutParams.height);
//        photoView.setBackgroundColor(Color.parseColor("#ffffff"));

        LayoutParams photoLayoutParams = (LayoutParams) container.getLayoutParams();
        container.layout(photoLayoutParams.left,
                photoLayoutParams.top,
                photoLayoutParams.left + photoLayoutParams.width,
                photoLayoutParams.top + photoLayoutParams.height);

    }

    public void addTextView(View textView) {
        container = (RelativeLayout) getChildAt(1);
        container.removeAllViews();
        container.addView(textView);
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

    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    public Bitmap clip() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        return bitmap;
    }

}
