package com.testmanage.oldtest.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.testmanage.oldtest.animator.Point;
import com.testmanage.oldtest.animator.PointEvaluator;


/**
 * Created by Administrator on 2016/3/24.
 */
public class AnimationView extends View {
    public static final float Radius = 50f;
    private Point currentPoint ;
    private Paint mPaint;

    public AnimationView(Context context) {
        super(context);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(currentPoint == null){
            currentPoint = new Point(Radius,Radius);
            drawCircle(canvas);
            startAnimation();
        }else {
            drawCircle(canvas);
        }
    }
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),Radius,mPaint);
    }

    private void startAnimation() {
        Integer.parseInt("a",16);
//        Point pointStart = new Point(Radius,Radius);
//        Point pointEnd = new Point(getWidth()-Radius,getHeight() -Radius);
        Point pointStart = new Point(getWidth()/2,Radius);
        Point pointEnd = new Point(getWidth()/2-Radius,getHeight() -Radius);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),pointStart,pointEnd);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(3000);
        anim.start();
        //组合动画
//        ObjectAnimator anim2 = ObjectAnimator.ofObject(this,"color",new ColorEvaluator(), "#0000FF", "#FF0000");
//        AnimatorSet set = new AnimatorSet();
//        set.setInterpolator(new BounceInterpolator());
//        set.play(anim).with(anim2);
//        set.setDuration(3000);
//        set.start();
    }
}
