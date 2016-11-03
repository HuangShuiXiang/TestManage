package com.testmanage.oldtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;


public class ViewGroupTestActivity extends BaseActivity {
    private static final String TAG = "ViewGroupTestActivity";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_viewgroup_test;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    private void initView() {
    }

}
