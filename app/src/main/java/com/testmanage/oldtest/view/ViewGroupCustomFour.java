package com.testmanage.oldtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/31.
 */
public class ViewGroupCustomFour extends ViewGroup {
    private String TAG = "ViewGroupCustomFour";
    public ViewGroupCustomFour(Context context) {
        super(context);
    }

    public ViewGroupCustomFour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取父容器为自己设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //如果是wrap_content 情况下，记录宽高
        int width = 0;
        int height = 0;
        //记录每一行的宽度，width不断取最大宽度
        int lineWidth = 0;
        //记录每一行的高度，累加到height
        int lineHeight = 0;
        int mCount = getChildCount();
        //遍历给个子元素
        for (int i = 0; i < mCount; i++) {
            View childView = getChildAt(i);
            //测量每个子view的宽高
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            //得到子view的lp
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            //当前子view实际占据的宽度。
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //当前子view实际占据的高度
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            /**
             * 如果加入当前子view，超出最大宽度，则给目前最大宽度给with，累加height，然后开启新行
             */
            if(lineWidth + childWidth > sizeWidth){
                width = Math.max(lineWidth,childWidth); //取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                //叠加当前高度
                height += lineHeight;
                //开启记录下一行的高度
                lineHeight = childHeight;
            }else {
                //否则累加值
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);

            }
            //如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if(i == mCount){
                width = Math.max(width,lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }
    //存储所有的View，按行记录
    private List<List<View>> mAllViews = new ArrayList<>();
    //记录每一行的最大高度
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        //存储每一行所有的childView
        List<View> lineViews = new ArrayList<>();
        int mCount = getChildCount();
        //遍历所有的孩子
        for (int i = 0; i < mCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //如果已经需要换行
            if(childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width){
                //记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                //将当前的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                //重置宽高
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }
            //如果不需要换行，则累加
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight+lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        //记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        int left = 0;
        int top = 0;
        //得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            //每一行的所有的view
            lineViews = mAllViews.get(i);
            //当前行的最大高度
            lineHeight = mLineHeight.get(i);
            Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
            Log.e(TAG, "第" + i + "行， ：" + lineHeight);
            //遍历当前行所有的view
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if(child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //计算childView的left，top,right,bottom;
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc,tc,rc,bc);
                left += child.getMeasuredWidth() + lp.rightMargin+ lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }
}
