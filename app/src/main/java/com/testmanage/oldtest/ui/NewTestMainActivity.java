package com.testmanage.oldtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.materlistview.CrashHandler.ServerLogActivity;
import com.materlistview.cardModel.Card;
import com.materlistview.cardModel.OnButtonPressListener;
import com.materlistview.materListView.MaterialListView;
import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;
import com.testmanage.oldtest.card.cardModel.ItemCard;
import com.testmanage.oldtest.util.QRcode.MipcaActivityCapture;
import com.testmanage.oldtest.util.webview.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016年做得练习项目
 */
public class NewTestMainActivity extends BaseActivity {
    private MaterialListView material_ListView;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    private void initView() {
        material_ListView = (MaterialListView) findViewById(R.id.material_ListView);
        fillArrayList();
 /*       double x = Math.pow(2.0,36.0);
        x = x/1000000;
        Toast.makeText(this,""+x, Toast.LENGTH_SHORT).show();
        Log.i("tag",x+"");*/
        /*HandlerThread thread = new HandlerThread("thread1");
        thread.start();
        thread.quit();*/
    }

    /**
     * 填充卡片
     */
    private void fillArrayList() {
        initList();
        for (int i = 0; i < list.size(); i++) {
            ItemCard card = new ItemCard(this);
            card.setName(list.get(i));
            card.setPosition(i);
            card.setOnAddFreshUIPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {
                    ItemCard card1 = (ItemCard) card;
                    switch (card1.getPosition()) {
                        case 0:
                            startActivityForResult(new Intent(NewTestMainActivity.this, MipcaActivityCapture.class), 100);
                            break;
                    }
                }
            });
            material_ListView.add(card);
        }
    }

    private void initList() {
        list.add("zing二维码扫描");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        Bundle bundle = data.getExtras();
        //显示扫描到的内容
        Bundle bundle1 = new Bundle();
        bundle1.putString(WebActivity.WebUrl, bundle.getString("result"));
        bundle1.putString(WebActivity.Title, "扫描结果");
        bundle1.putBoolean(WebActivity.ISShowSavaBtn, false);
        gotoActivity(WebActivity.class, bundle1);
        showToastLong(bundle.getString("result"));
    }
}
