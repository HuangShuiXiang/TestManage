package com.testmanage.oldtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;
import com.testmanage.oldtest.view.SlidingMenu;


public class CustomViewTwoActivity extends BaseActivity {
    private static final String TAG = "CustomViewTwoActivity";
    private SlidingMenu slidingMenu;
    private Button btn_content;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_custom_two;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    private void initView() {
        slidingMenu = (SlidingMenu) findViewById(R.id.slid_menu);
        btn_content = (Button) findViewById(R.id.btn_content);
        btn_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
    }

}
