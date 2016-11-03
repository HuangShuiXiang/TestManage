package com.testmanage.oldtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/28.
 */
public class CanvasView extends View {
    private Paint mPaint;
    private RectF oval1;
    private int startAngle = 0;
    private int sweepAngle = 180;

    public CanvasView(Context context) {
        super(context);
        init();
    }


    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        //设置画笔空心，不设置就填满
        mPaint.setStyle(Paint.Style.STROKE);
        oval1 = new RectF(500,400,800,800);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //画布背景
        canvas.drawColor(Color.BLACK);
        //画线
        canvas.drawLine(50,50,150,150,mPaint);
        canvas.drawLine(150,150,250,50,mPaint);

        canvas.drawLine(250,50,350,150,mPaint);
        canvas.drawLine(350,150,450,50,mPaint);

        canvas.drawLine(450,50,550,150,mPaint);
        canvas.drawLine(550,150,650,50,mPaint);
        //画矩形
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(new Rect(50,160,150,260),mPaint);
        //文字
        mPaint.setTextSize(24f);
        canvas.drawText("正方形",75,210,mPaint);
        //画圆角矩形
        canvas.drawRoundRect(new RectF(170,160,350,260),20,20,mPaint);
        //文字
        canvas.drawText("圆角矩形",245,210,mPaint);
        //实心
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        //圆
        canvas.drawCircle(460,210,100,mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("圆",450,210,mPaint);
        //画弧线
        mPaint.setStyle(Paint.Style.STROKE);
//        RectF oval1=new RectF(150,20,180,40);
      /*canvas.drawArc(oval1, 180, 180, false, mPaint);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, mPaint);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, mPaint);//小弧形
*/
//        oval1.set(700,210,850,300);
        canvas.drawArc(oval1,startAngle,sweepAngle,true,mPaint);
        canvas.drawRect(oval1,mPaint);
        //路径绘画
        Path path = new Path();
//        500,400,800,800)
        path.moveTo(20,800);
        path.lineTo(45,850);
        path.lineTo(65,600);
        path.close();
        canvas.drawPath(path,mPaint);
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }
    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void onFresh(){

        invalidate();
    }
}
