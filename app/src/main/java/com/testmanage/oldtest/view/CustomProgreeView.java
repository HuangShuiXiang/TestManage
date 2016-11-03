package com.testmanage.oldtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.testmanage.R;


/**
 * Created by Administrator on 2016/3/30.
 */
public class CustomProgreeView extends View {
    //第一个颜色
    private int mFirstColor;
    //第二个颜色
    private int mSecondColor;
    //速度
    private int mSpeed;
    //圆环宽度
    private int mCircleWidth;
    private Paint mPaint;
    //当前进度
    private int mProgress;
    //是否进行下一个
    private boolean isNext = false;

    public CustomProgreeView(Context context) {
        this(context, null);
//        init();
    }

    public CustomProgreeView(Context context, AttributeSet attrs) {
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
    public CustomProgreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgreeView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgreeView_firstColor:
                    mFirstColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomProgreeView_secondColor:
                    mSecondColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomProgreeView_circleWidth:
                    mCircleWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
//                    mCircleWidth = array.getInt(attr,0);
                    break;
                case R.styleable.CustomProgreeView_speed:
                    mSpeed = array.getInt(attr, 20);
                    break;
            }
        }
        array.recycle();
        mPaint = new Paint();
        //绘图线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        //满一个圆圈就重绘
                        mProgress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2; //获取中心点
        int radius = center - mCircleWidth / 2; //计算半径
        mPaint.setStrokeWidth(mCircleWidth);  //设置圆环宽度
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);  //设置空心
        //用于定义圆弧的形状和大小
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段形状为圆头
        if (!isNext) {
            //第一圈颜色完整，跑第二圈
            mPaint.setColor(mFirstColor); //设置圆环颜色
            canvas.drawCircle(center, center, radius, mPaint); //画出圆环
            mPaint.setColor(mSecondColor); // 设置圆环颜色
            canvas.drawArc(rectF, -90, mProgress, false, mPaint); //根据进度画圆弧
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(rectF, -90, mProgress, false, mPaint);
        }
    }

}
