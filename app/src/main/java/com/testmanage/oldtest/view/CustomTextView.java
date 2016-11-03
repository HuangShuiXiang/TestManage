package com.testmanage.oldtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.testmanage.R;


/**
 * Created by Administrator on 2016/3/30.
 */
public class CustomTextView extends View {
    private String mTitle;
    private int mTitleColor;
    private int mTitleSize;
    private Rect mBounds;
    private Rect rect;
    private Paint mPaint;
    private Bitmap mImage;
    private int mIamegScale;
    int mWidth;
    int mHheight ;

    public CustomTextView(Context context) {
        this(context,null);
//        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
//        init();
    }

    /**
     * 获取自定义属性
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView,defStyleAttr,0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int arrt = array.getIndex(i);
            switch (arrt){
                case R.styleable.CustomTextView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), array.getResourceId(arrt,0));
                    break;
                case R.styleable.CustomTextView_imageScaleType:
                    mIamegScale = array.getInt(arrt, 0);
                    break;
                case R.styleable.CustomTextView_TitleText:
                    mTitle = array.getString(arrt);
                    break;
                case R.styleable.CustomTextView_TitleTextColor:
                    mTitleColor = array.getColor(arrt, Color.BLACK);
                    break;
                case R.styleable.CustomTextView_TitleTextSize:
                    mTitleSize = array.getDimensionPixelSize(arrt,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        // mPaint.setColor(mTitleTextColor);
        mBounds = new Rect();
        rect = new Rect();
        mPaint.setTextSize(mTitleSize);
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
//        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            //由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            //有字体决定的宽
            int desireByTitle = getPaddingLeft()+ getPaddingRight() + mBounds.width();
            if(specMode == MeasureSpec.AT_MOST){
                int desire = Math.max(desireByImg,desireByTitle);
                mWidth = Math.min(desire,specSize);
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            mHheight = specSize;
        }else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mBounds.height();
            if(specMode == MeasureSpec.AT_MOST){
                mHheight = Math.min(desire,specSize);
            }
        }
        setMeasuredDimension(mWidth,mHheight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(4);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHheight - getPaddingBottom();

        mPaint.setColor(mTitleColor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当字体宽度大于显示宽度时，变成xx...
         */
        if(mBounds.width() > mWidth){
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle,paint,(float)mWidth-getPaddingLeft()-getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(),mHheight - getPaddingBottom(),mPaint);
        }else {
            canvas.drawText(mTitle, mWidth / 2 - mBounds.width() * 1.0f / 2, mHheight - getPaddingBottom(), mPaint);
        }

         // 取消使用掉的快
        rect.bottom -= mBounds.height();
//        IMAGE_SCALE_FITXY
        if(mIamegScale == 0){
            canvas.drawBitmap(mImage,null,rect,mPaint);
        }else {//IMAGE_SCALE_CENTER
            //计算居中的矩形范围
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHheight - mBounds.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHheight - mBounds.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage,null,rect,mPaint);
        }
    }

}
