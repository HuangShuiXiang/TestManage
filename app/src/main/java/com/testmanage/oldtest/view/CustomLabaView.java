package com.testmanage.oldtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.testmanage.R;


/**
 * Created by Administrator on 2016/3/30.
 * 音量调节
 */
public class CustomLabaView extends View {
    //第一个颜色
    private int mFirstColor;
    //第二个颜色
    private int mSecondColor;
    //圆环宽度
    private int mCircleWidth;
    //当前进度
    private int mCurrentCount;
    //个数
    private int mCount;
    //中间的图片
    private Bitmap mIamge;
    //块之间的间隙
    private int mSplitSize;

    private Paint mPaint;
    private Rect rect;

    public CustomLabaView(Context context) {
        this(context, null);
//        init();
    }

    public CustomLabaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
//        init();
    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomLabaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomLabaView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustomLabaView_firstColor:
                    mFirstColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomLabaView_secondColor:
                    mSecondColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomLabaView_circleWidth:
                    mCircleWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
//                    mCircleWidth = array.getInt(attr,0);
                    break;
                case R.styleable.CustomLabaView_bg:
                    mIamge = BitmapFactory.decodeResource(getResources(),array.getResourceId(attr,0));
                    break;
                case R.styleable.CustomLabaView_splitSize:
                    mSplitSize = array.getInt(attr,20);
                    break;
                case R.styleable.CustomLabaView_dotCount:
                    mCount = array.getInt(attr,20);
                    break;

            }
        }
        array.recycle();
        mPaint = new Paint();
        rect = new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2; //获取中心点
        int radius = center - mCircleWidth / 2; //计算半径

        mPaint.setStrokeWidth(mCircleWidth);  //设置圆环宽度
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);  //设置空心
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段形状为圆头
        //画圆弧
        drawOval(canvas, center, radius);
    }

    /**
     * 根据参数画圆
     * @param canvas
     * @param center
     * @param radius
     */
    private void drawOval(Canvas canvas, int center, int radius) {
        //每个item的长度
        float itemSize = (360 * 1.0f - mCount*mSplitSize) /mCount;
        //用于定义圆弧的形状和大小
        RectF rectF = new RectF(center-radius,center-radius,center+radius,center+radius);
        mPaint.setColor(mFirstColor);//设置圆环颜色
        for (int i = 0; i < mCount; i++) {
            //canvas.drawArc（范围，开始角度，扫描角度，是否闭合，画笔），
            canvas.drawArc(rectF,i*(itemSize + mSplitSize),itemSize,false,mPaint); //画初始圆弧
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(rectF,i*(itemSize + mSplitSize),itemSize,false,mPaint);  //画进度圆弧
        }

    }
    private void up(){
        if(mCurrentCount < mCount){
            mCurrentCount++;
            postInvalidate();
        }

    }
    private void down(){
        if(mCurrentCount >= 1){
            mCurrentCount--;
            postInvalidate();
        }

    }
    int xDown,xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int)event.getY();
                if(xDown < xUp){
                    down();
                }else {
                    up();
                }
                break;

        }
        return true;
    }
}
