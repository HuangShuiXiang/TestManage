package com.testmanage.oldtest;

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
import com.testmanage.oldtest.card.cardModel.ItemCard;
import com.testmanage.oldtest.ui.AnimatorTestActivity;
import com.testmanage.oldtest.ui.CanvasTestActivity;
import com.testmanage.oldtest.ui.CustomViewTestActivity;
import com.testmanage.oldtest.ui.CustomViewTwoActivity;
import com.testmanage.oldtest.ui.DesignTestActivity;
import com.testmanage.oldtest.ui.NewTestMainActivity;
import com.testmanage.oldtest.ui.OldTestMainActivity;
import com.testmanage.oldtest.ui.ProgressViewTestActivity;
import com.testmanage.oldtest.ui.RxJavaTestActivity;
import com.testmanage.oldtest.ui.ViewGroupTestActivity;
import com.testmanage.oldtest.ui.ViewGroupTestFoureActivity;
import com.testmanage.oldtest.ui.ViewGroupTestThreeActivity;
import com.testmanage.oldtest.ui.ViewGroupTestTwoActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        material_ListView = (MaterialListView) findViewById(R.id.material_ListView);
        fillArrayList();
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
                            startActivity(new Intent(MainActivity.this, OldTestMainActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(MainActivity.this, NewTestMainActivity.class));
                            break;
                    }
                }
            });
            material_ListView.add(card);
        }
    }

    private void initList() {
        list.add("2015练习");
        list.add("2016练习");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "请求/响应日志");
        menu.add(1, 1, 1, "crash闪退日志");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent inient = new Intent(this,
                ServerLogActivity.class);
        inient.putExtra(ServerLogActivity.APPNAME, "testmanage");
        switch (item.getItemId()) {
            case 0:
                startActivity(inient);
                break;
            case 1:
                inient.putExtra("crash", "crash");
                startActivity(inient);
                break;
        }

        return true;
    }
}
