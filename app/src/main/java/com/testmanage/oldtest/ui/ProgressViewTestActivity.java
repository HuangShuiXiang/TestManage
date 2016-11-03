package com.testmanage.oldtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;


public class ProgressViewTestActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "ProgressViewTestActivity";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_progress_test;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    private void initView() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_add:

            break;
        }
    }
}
