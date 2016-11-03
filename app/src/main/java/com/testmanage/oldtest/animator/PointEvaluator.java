package com.testmanage.oldtest.animator;

import com.nineoldandroids.animation.TypeEvaluator;

/**
 * Created by Administrator on 2016/3/24.
 */
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point pointStart = (Point)startValue;
        Point pointEnd = (Point)endValue;
        float x = pointStart.getX() +fraction * (pointEnd.getX() - pointStart.getX());
        float y = pointStart.getY() +fraction * (pointEnd.getY() - pointStart.getY());
        Point point = new Point(x,y);
        return point;
    }
}
