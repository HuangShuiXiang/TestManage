package com.testmanage.oldtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.testmanage.R;
import com.testmanage.oldtest.view.ViewGroupCustomThree;


public class ViewGroupTestThreeActivity extends AppCompatActivity {
    private static final String TAG = "ViewGroupTestThreeActivity";
    private ViewGroupCustomThree viewGroupCustomThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup_three_test);
        initView();
    }

    private void initView() {
        viewGroupCustomThree = (ViewGroupCustomThree) findViewById(R.id.viewGroupCustomThree);
        viewGroupCustomThree.setOnPageChangeListener(new ViewGroupCustomThree.OnPageChangeListener() {
            @Override
            public void onPageChange(int page) {
                Toast.makeText(ViewGroupTestThreeActivity.this,"第" +page+"页", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
