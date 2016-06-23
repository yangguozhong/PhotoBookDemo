package com.yang.test.photobook.weiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 横向展示文字或者纵向展示文字的自定义view
 */
public class VHTextView extends View {
    private Context mContext;
    // 是否横向显示文字
    public boolean isOritation = false;
    // 绘制方向
    public static final int RIGHT = 1;
    public static final int LEFT = 0;
    // 绘制的最大长度
    public int MaxInOneLine = 0;
    // 绘制文字画笔
    private Paint paint;
    // 绘制下划线画笔
    private Paint mPaint;
    // 绘制下划线的路径
    private Path mPath;
    // 绘制的x,y 位置
    private int mTextPosX = 0;
    private int mTextPosY = 0;
    // 绘制宽度
    private int mTextWidth = 0;
    // 绘制高度
    private int mTextHeight = 0;
    // 绘制字体高度
    private int mFontHeight = 0;

    // 字体大小
    private float mFontSize = 40;
    // 传入的文本
    private String text = "";
    // 字符串真实的行数
    private int mLineSize = 0;
    // 列宽度
    private int mLineWidth = 0;
    //行高度
    private int mLineHeight = 0;
    // 不包含下划线的列宽度
    private int mLineWidthWithoutUnderLine = 0;
    // 列间距
    private int mLineSpacing = 20;
    // 字符串长度
    private int TextLength = 0;
    // 每行超过字数自动换行数
    private static int maxCountInOneLine = 20;
    // 存储上次的width
    private int oldwidth = 0;
    // 绘制字体的默认方向
    private Paint.Align textStartAlign = Paint.Align.RIGHT;
    // 是否开启下划线
    private boolean IsOpenUnderLine = false;
    // 下划线的宽度
    private int underLineWidth = 5;
    // 下划线的颜色
    private int underLineColor = Color.BLACK;
    // 下划线的间距
    private int underLineSpacing = 10;
    // 传入用于处理事件的Handler
    private Handler mHandler = null;
    // 获取的背景
    private BitmapDrawable drawable = (BitmapDrawable) getBackground();

    public VHTextView(Context context) {
        super(context);
        mContext = context;
        paint = new Paint();
//        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/fun_font.TTF"));
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/FZKATJW.TTF"));
        // 文字居中
        paint.setTextAlign(Paint.Align.CENTER);
        // 平滑处理
        paint.setAntiAlias(true);
        // 默认文字颜色
        paint.setColor(Color.BLACK);

        if (IsOpenUnderLine) {
            mPath = new Path();
            mPaint = new Paint();
            mPaint.setColor(underLineColor);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(underLineWidth);
        }
        mFontSize = 40;
    }

    public VHTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        paint = new Paint();
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/FZKATJW.TTF"));
        // 文字居中
        paint.setTextAlign(Paint.Align.CENTER);
        // 平滑处理
        paint.setAntiAlias(true);
        // 默认文字颜色
        paint.setColor(Color.BLACK);

        if (IsOpenUnderLine) {
            mPath = new Path();
            mPaint = new Paint();
            mPaint.setColor(underLineColor);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(underLineWidth);
        }

        try {
            mFontSize = Float.parseFloat(attrs.getAttributeValue(null, "textSize"));
        } catch (Exception e) {
            Log.e("get font size", "error");
            mFontSize = 40;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (drawable != null) {
//            // 画背景
//            Bitmap b = Bitmap.createBitmap(drawable.getBitmap(), 0, 0, mTextWidth, mTextHeight);
//            canvas.drawBitmap(b, new Matrix(), paint);
//        }
        // 画字
        draw(canvas, this.text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isOritation) {
            int measuredWeight = measureWidth(widthMeasureSpec);
            if (mTextHeight == 0) getOritationTextSize();
            Log.e("measuredHeight", measuredWeight + "" + "----横向------MaxInOneLine------" + MaxInOneLine);
            setMeasuredDimension(Math.min(measuredWeight, MaxInOneLine), mTextHeight);
        } else {
            int measuredHeight = measureHeight(heightMeasureSpec);
            if (mTextWidth == 0) getTextSize();
            Log.e("measuredHeight", measuredHeight + "" + "-----纵向-----MaxInOneLine------" + MaxInOneLine);
            setMeasuredDimension(mTextWidth, Math.min(measuredHeight, MaxInOneLine));
        }

//        if (oldwidth != getWidth()) {
//            oldwidth = getWidth();
//            if (mHandler != null) mHandler.sendEmptyMessage(LAYOUT_CHANGED);
//        }
    }

    /**
     * 绘制
     *
     * @param canvas
     * @param text
     */
    private void draw(Canvas canvas, String text) {
        if (IsOpenUnderLine) {
            mPath = new Path();
            mPaint = new Paint();
            mPaint.setColor(underLineColor);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(underLineWidth);
        }
        int baseLine2BottomH = getBaseLine2BottomHeight(mFontSize);
        if (isOritation) {
            char StringItem;
            mTextPosY = mLineHeight;
//            canvas.drawText(String.valueOf("我"), mTextPosX, mTextPosY -
//                    baseLine2BottomH, paint);
            // 获得字体高度
            mTextPosX = getPaddingLeft();
            for (int i = 0; i < this.TextLength; i++) {
                StringItem = text.charAt(i);
                if (StringItem == '\n') {
                    // 换行
                    if (IsOpenUnderLine)
                        drawUnderLine(canvas);
                    mTextPosY += mLineHeight;
                    mTextPosX = getPaddingLeft();
                } else {

                    if (mTextPosX > this.mTextWidth) {
                        if (IsOpenUnderLine)
                            drawUnderLine(canvas);
                        mTextPosY += mLineHeight;
                        i--;
                        mTextPosX = getPaddingLeft();
                    } else {
                        canvas.drawText(String.valueOf(StringItem), mTextPosX, mTextPosY -
                                baseLine2BottomH, paint);
                        int mLineWidth = (int) paint.measureText(String.valueOf(StringItem));
                        mTextPosX += mLineWidth;
                        if (i == this.TextLength - 1) {
                            if (IsOpenUnderLine)
                                drawUnderLine(canvas);
                        }

                    }
                }
            }
        } else {
            char StringItem;
            mTextPosY = 0;
            mTextPosX = textStartAlign == Paint.Align.LEFT ? mLineWidth : mTextWidth - mLineWidth;

            for (int i = 0; i < this.TextLength; i++) {
                StringItem = text.charAt(i);
                if (StringItem == '\n') {
                    // 换行
                    if (IsOpenUnderLine)
                        drawUnderLine(canvas);
                    if (textStartAlign == Paint.Align.LEFT) {
                        mTextPosX += mLineWidth;
                    } else {
                        mTextPosX -= mLineWidth;
                    }
                    mTextPosY = 0;
                } else {
                    mTextPosY += mFontHeight;
                    if (mTextPosY > this.mTextHeight) {
                        if (IsOpenUnderLine)
                            drawUnderLine(canvas);
                        if (textStartAlign == Paint.Align.LEFT) {
                            mTextPosX += mLineWidth;
                        } else {
                            mTextPosX -= mLineWidth;
                        }
                        i--;
                        mTextPosY = 0;
                    } else {
                        canvas.drawText(String.valueOf(StringItem), mTextPosX, mTextPosY - baseLine2BottomH, paint);
                        if (i == this.TextLength - 1) {
                            if (IsOpenUnderLine)
                                drawUnderLine(canvas);
                        }
                    }
                }
            }
        }

    }

    // 传入数值，转换为px。
    public int dp2px(int value) {
        return (int) (value * mContext.getResources().getDisplayMetrics().density);
    }

    /**
     * 绘制下划线
     *
     * @param canvas
     */
    private void drawUnderLine(Canvas canvas) {
        if (isOritation) {
            mPath.moveTo(0, mTextPosY + mLineWidthWithoutUnderLine / 2 - underLineSpacing);
            mPath.lineTo(mTextPosX, mTextPosY + mLineWidthWithoutUnderLine / 2 - underLineSpacing);
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
        } else {
            mPath.moveTo(mTextPosX - mLineWidthWithoutUnderLine / 2 - underLineSpacing, 0);
            mPath.lineTo(mTextPosX - mLineWidthWithoutUnderLine / 2 - underLineSpacing, mTextPosY);
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

    }

    /**
     * 计算控件的高度
     *
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // 获得字体高度
        int textHeight = mFontHeight = getFontHeight(mFontSize);
        int result = textHeight * maxCountInOneLine;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        // 设置文本高度
        mTextHeight = result;
//        mTextHeight = (int)widths[0] * maxCountInOneLine;
        return result;
    }

    /**
     * 计算控件的高度
     *
     * @param measureSpec
     * @return
     */
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // 获取单个汉字的宽度
        paint.setTextSize(mFontSize);
        // 获得字体高度
        int mLineWidth =(int) paint.measureText(String.valueOf("哈"));
        int result = mLineWidth * maxCountInOneLine;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        // 设置文本高度
        mTextWidth = result;
//        mTextHeight = (int)widths[0] * maxCountInOneLine;
        return result;
    }

    public void setText(String text) {
        this.text = text;
        this.TextLength = text.length();
        // 重新调整位置
        if (mTextHeight > 0) {
            if (isOritation) {
                getOritationTextSize();
            } else {

                getTextSize();
            }
        }

    }

    /**
     * 计算文字行数和总高
     */
    private void getOritationTextSize() {
        char StringItem;
        // 每行的字符数所对应的长度
        int SizeInOneLine = 0;
        paint.setTextSize(mFontSize);
        // 获得字体高度
        mFontHeight = getFontHeight(mFontSize);
        // 获得字宽
        if (mLineHeight == 0) {
            // 计算列宽度
//            mLineWidth = (int) Math.ceil(widths[0] * 1.2 + 2);
            mLineHeight = mLineSpacing + mFontHeight;
            if (IsOpenUnderLine) {
                mLineWidthWithoutUnderLine = mLineHeight;
                mLineHeight += underLineWidth + underLineSpacing;
            }
        }

        // 计算文字行数
        mLineSize = 0;
        MaxInOneLine = 0;
        // 获得字体高度
        int mLineWidth =(int) paint.measureText(String.valueOf("W"));
        for (int i = 0; i < this.TextLength; i++) {
            StringItem = this.text.charAt(i);
            if (StringItem == '\n') {
                // 行数加一
                mLineSize++;
                SizeInOneLine = 0;
            } else {

                SizeInOneLine += mLineWidth;
                // 如果某一行的字符总长超过了之前获取的长度，就会换行
                if (SizeInOneLine > this.mTextWidth) {
                    mLineSize++;
                    // 重新读入这个字符，由于h被清空会在下一行进行绘制
                    i--;
                    SizeInOneLine = 0;
                } else {
                    if (SizeInOneLine > MaxInOneLine) {
                        MaxInOneLine = SizeInOneLine;
                    }

                    // 最后一个字符结束再加入一行，否则最后一行显示不全
                    if (i == this.TextLength - 1) {
                        mLineSize++;
                    }
                }
            }
        }
        mLineSize++;
        // 计算文字总宽度
        mTextHeight = mLineHeight * mLineSize;
        // 重新调整大小
        measure(getWidth(), mTextHeight);
        // 重新绘制容器位置
        layout(getLeft(), getBottom() - mTextHeight, getRight(), getBottom());
    }


    /**
     * 计算文字行数和总宽
     */
    private void getTextSize() {
        char StringItem;
        // 每行的字符数所对应的长度
        int SizeInOneLine = 0;
        paint.setTextSize(mFontSize);
        // 获得字宽
        if (mLineWidth == 0) {
            // 计算列宽度
//            mLineWidth = (int) Math.ceil(widths[0] * 1.2 + 2);
            mLineWidth = mLineSpacing +(int) paint.measureText(String.valueOf("W"));
            if (IsOpenUnderLine) {
                mLineWidthWithoutUnderLine = mLineWidth;
                mLineWidth += underLineWidth + underLineSpacing;
            }
        }
        // 获得字体高度
        mFontHeight = getFontHeight(mFontSize);

        // 计算文字行数
        mLineSize = 0;
        MaxInOneLine = 0;
        for (int i = 0; i < this.TextLength; i++) {
            StringItem = this.text.charAt(i);
            if (StringItem == '\n') {
                // 行数加一
                mLineSize++;
                SizeInOneLine = 0;
            } else {
                SizeInOneLine += mFontHeight;
                // 如果某一行的字符总长超过了之前获取的长度，就会换行
                if (SizeInOneLine > this.mTextHeight) {
                    mLineSize++;
                    // 重新读入这个字符，由于h被清空会在下一行进行绘制
                    i--;
                    SizeInOneLine = 0;
                } else {
                    if (SizeInOneLine > MaxInOneLine) {
                        MaxInOneLine = SizeInOneLine;
                    }

                    // 最后一个字符结束再加入一行，否则最后一行显示不全
                    if (i == this.TextLength - 1) {
                        mLineSize++;
                    }
                }
            }
        }
        mLineSize++;
        // 计算文字总宽度
        mTextWidth = mLineWidth * mLineSize;
        // 重新调整大小
        measure(mTextWidth, getHeight());
        // 重新绘制容器位置
        layout(getRight() - mTextWidth, getTop(), getRight(), getBottom());
    }

    public void setFontSize(float mFontSize) {
        if (mFontSize != paint.getTextSize()) {
            this.mFontSize = mFontSize;
            // 重新调整位置
            if (mTextHeight > 0) {
                if (isOritation) {
                    getOritationTextSize();
                } else {
                    getTextSize();
                }
            }

        }
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    /**
     * 设定与下划线的间距
     *
     * @param mLineSpacing
     */
    public void setmLineSpacing(int mLineSpacing) {
        this.mLineSpacing = mLineSpacing;
    }

    /**
     * 设定一行的最大字数
     *
     * @param maxCountInOneLine
     */
    public static void setMaxCountInOneLine(int maxCountInOneLine) {
        VHTextView.maxCountInOneLine = maxCountInOneLine;
    }

    /**
     * 设定文字的起始方向
     *
     * @param leftOrRight
     */
    public void setTextStartAlign(int leftOrRight) {
        switch (leftOrRight) {
            case RIGHT:
                textStartAlign = Paint.Align.RIGHT;
                break;
            case LEFT:
                textStartAlign = Paint.Align.LEFT;
                break;
        }
    }

    /**
     * 文本颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        paint.setColor(color);
    }

    /**
     * 下划线开关
     *
     * @param isOpenUnderLine
     */
    public void setIsOpenUnderLine(boolean isOpenUnderLine) {
        IsOpenUnderLine = isOpenUnderLine;
    }

    /**
     * 下划线宽度
     *
     * @param underLineWidth
     */
    public void setUnderLineWidth(int underLineWidth) {
        this.underLineWidth = underLineWidth;
    }

    /**
     * 下划线颜色
     *
     * @param underLineColor
     */
    public void setUnderLineColor(int underLineColor) {
        this.underLineColor = underLineColor;
    }

    /**
     * 下划线间距
     *
     * @param underLineSpacing
     */
    public void setUnderLineSpacing(int underLineSpacing) {
        this.underLineSpacing = underLineSpacing;
    }

    /**
     * 是否为横向显示
     *
     * @param oritation
     */
    public void setOritation(boolean oritation) {
        isOritation = oritation;
    }

    public int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    public int getBaseLine2BottomHeight(float mFontSize) {
        Paint paint = new Paint();
        paint.setTextSize(mFontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        float viewHeight = (float) (Math.ceil(fm.descent - fm.top) + 2);
        float textCenterVerticalBaselineY = viewHeight / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        return (int) (viewHeight - textCenterVerticalBaselineY);
//        return 0;
    }


}
