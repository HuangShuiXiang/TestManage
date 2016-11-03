package com.testmanage.oldtest.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2016/3/31.
 */
public class ViewGroupCustomTwo extends LinearLayout {
    private ViewDragHelper mDraggper;
    private View mDragView;  //可以拖动的view
    private View mAutoBackView;  //可以拖动后自动回滚的类
    private View mEdgeTrackerView;  //可以跟随检测边界移动的类
    private Point mAutoBackOriginPos = new Point();
    private Point mEdgeTrackerViewOriginPos = new Point();

    public ViewGroupCustomTwo(Context context) {
        super(context);
    }

    public ViewGroupCustomTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDraggper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //这里mEdgeTrackerView禁止直接移动
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
            /**
             * 手机释放的回调
             */

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
//                super.onViewReleased(releasedChild, xvel, yvel);
                if(releasedChild == mAutoBackView){
                    //手指放开之后，用scoller回滚到原位
                    mDraggper.settleCapturedViewAt(mAutoBackOriginPos.x,mAutoBackOriginPos.y);
                    invalidate();
                }else if(releasedChild == mEdgeTrackerView){
                    mDraggper.settleCapturedViewAt(mEdgeTrackerViewOriginPos.x,mEdgeTrackerViewOriginPos.y);
                    invalidate();
                }
            }
            /**
             * 在边界拖动的回调
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                //同时移动mEdgeTrackerView
                mDraggper.captureChildView(mEdgeTrackerView,pointerId);
            }

        });
        //添加边界检测
        mDraggper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
      /*  普通的移动演示
      mDraggper = ViewDragHelper.create(this,1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //左右边界处理，如不做边界处理直接返回left
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //上下边界处理，如不做边界处理直接返回top
                final int topBound = getPaddingTop();
                final int buttomBound = getHeight() - child.getHeight() - topBound;
                final int newtop = Math.min(Math.max(top, topBound), buttomBound);
                return newtop;
            }
        });*/
    }

    @Override
    public void computeScroll() {
        if(mDraggper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //布局的时候，记录原位
        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
        mEdgeTrackerViewOriginPos.x = mEdgeTrackerView.getLeft();
        mEdgeTrackerViewOriginPos.y = mEdgeTrackerView.getTop();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDraggper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDraggper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
