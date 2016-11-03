package com.testmanage.oldtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.testmanage.R;


/**
 * Created by Administrator on 2016/3/23.
 */
public class MoveTestView extends TextView {
    int mColor;
    private Paint mPaint;
    public MoveTestView(Context context) {
        super(context);
        init();
    }

    public MoveTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoveTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MoveTestView);
        mColor = array.getColor(R.styleable.MoveTestView_TextColor, Color.BLACK);
        array.recycle();
        init();
    }

   /* public MoveTestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }*/
    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("hello world",0,0,mPaint);
        super.onDraw(canvas);
    }

    int mLastX;
    int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int delax = x -mLastX;
                int delay = y -mLastY;
                int translationX = (int)ViewHelper.getTranslationX(this) + delax;
                int translationY = (int)ViewHelper.getTranslationY(this) + delay;
                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }

        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}
