package com.testmanage.oldtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;
import com.testmanage.oldtest.view.CanvasView;


public class CanvasTestActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "CanvasTestActivity";
    private CanvasView view;
    private Button start_add;
    private Button start_subtract;
    private Button sweep_add;
    private Button sweep_subtract;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_canvas_test;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    private void initView() {
        view = (CanvasView) findViewById(R.id.view);
        start_add = (Button) findViewById(R.id.start_add);
        start_subtract = (Button) findViewById(R.id.start_subtract);
        sweep_add = (Button) findViewById(R.id.sweep_add);
        sweep_subtract = (Button) findViewById(R.id.sweep_subtract);
        start_add.setOnClickListener(this);
        start_subtract.setOnClickListener(this);
        sweep_add.setOnClickListener(this);
        sweep_subtract.setOnClickListener(this);
    }
    int start = 0;
    int sweep = 180;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_add:
                start+=10;
                view.setStartAngle(start);
            break;
            case R.id.start_subtract:
                start-=10;
                view.setStartAngle(start);
            break;
            case R.id.sweep_add:
                sweep+=10;
                view.setStartAngle(sweep);
            break;
            case R.id.sweep_subtract:
                sweep-=10;
                view.setStartAngle(sweep);
            break;
        }
        view.invalidate();
    }
}
