package com.testmanage.oldtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.materlistview.materListView.AppConfig;
import com.nineoldandroids.view.ViewHelper;
import com.testmanage.R;


/**
 * Created by Administrator on 2016/4/7.
 */
public class SlidingMenu extends HorizontalScrollView {
    private int mScreenWidth; //屏幕宽度
    private int mMenuRightPadding ; //菜单的右侧偏移量
    //菜单的宽度
    private int mMenuWidth;
    private int mHalfMenuWidth;
    private boolean once;
    private boolean isOpen;
    LinearLayout mMenu;
    LinearLayout mContent;

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public SlidingMenu(Context context) {
        this(context, null);

    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = AppConfig.getInstance(context).getScreenWidth();
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightPadding:
                    //默认50
                    mMenuRightPadding = array.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50F,getResources().getDisplayMetrics()));
                    break;
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            mMenu = (LinearLayout) wrapper.getChildAt(0);
            mContent = (LinearLayout) wrapper.getChildAt(1);
//            mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mMenuRightPadding,content.getResources().getDisplayMetrics());
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            mMenu.getLayoutParams().width = mMenuWidth;
            mContent.getLayoutParams().width = mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            //隐藏菜单栏
            this.scrollTo(mMenuWidth,0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            //up，进行判断，如果显示区域大于菜单宽度的一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX > mHalfMenuWidth){
                    isOpen = false;
                    this.smoothScrollTo(mMenuWidth,0);
                }else {
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }

                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l*1.0f/mMenuWidth;
        float leftScale = 1-0.3f*scale;
        float rightScale = 0.8f + scale*0.2f;

        ViewHelper.setScaleX(mMenu,leftScale);
        ViewHelper.setScaleY(mMenu,rightScale);
        ViewHelper.setAlpha(mMenu,0.6f+0.4f*(1-scale));
        ViewHelper.setTranslationX(mMenu,mMenuWidth *scale*0.6f);

        ViewHelper.setPivotX(mContent,0);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);
        ViewHelper.setScaleX(mContent,rightScale);
        ViewHelper.setScaleY(mContent,rightScale);
    }

    public void openMenu(){
        if(isOpen){
            return;
        }
        this.smoothScrollTo(0,0);
        isOpen = true;
    }
    public void closeMenu(){
        if(!isOpen){
            return;
        }
        this.smoothScrollTo(mMenuWidth,0);
        isOpen = false;
    }
    /**
     * 切换菜单状态
     */
    public void toggle()
    {
        if (isOpen)
        {
            closeMenu();
        } else
        {
            openMenu();
        }
    }
}
