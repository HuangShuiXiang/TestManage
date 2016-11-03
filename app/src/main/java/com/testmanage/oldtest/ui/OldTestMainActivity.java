package com.testmanage.oldtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.materlistview.cardModel.Card;
import com.materlistview.cardModel.OnButtonPressListener;
import com.materlistview.materListView.MaterialListView;
import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;
import com.testmanage.oldtest.card.cardModel.ItemCard;

import java.util.ArrayList;
import java.util.List;

/**
 *  * 2015年做得练习项目
 */
public class OldTestMainActivity extends BaseActivity {
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
                    ItemCard card1 = (ItemCard)card;
                    switch (card1.getPosition()){
                        case 0:
                            startActivity(new Intent(OldTestMainActivity.this, DesignTestActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(OldTestMainActivity.this, AnimatorTestActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(OldTestMainActivity.this, RxJavaTestActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(OldTestMainActivity.this, CanvasTestActivity.class));
                            break;
                        case 4:
                            startActivity(new Intent(OldTestMainActivity.this, CustomViewTestActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(OldTestMainActivity.this, ProgressViewTestActivity.class));
                            break;
                        case 6:
                            startActivity(new Intent(OldTestMainActivity.this, ViewGroupTestActivity.class));
                            break;
                        case 7:
                            startActivity(new Intent(OldTestMainActivity.this, ViewGroupTestTwoActivity.class));
                            break;
                        case 8:
                            startActivity(new Intent(OldTestMainActivity.this, ViewGroupTestThreeActivity.class));
                            break;
                        case 9:
                            startActivity(new Intent(OldTestMainActivity.this, ViewGroupTestFoureActivity.class));
                            break;
                        case 10:
                            startActivity(new Intent(OldTestMainActivity.this, CustomViewTwoActivity.class));
                            break;
                    }
                }
            });
            material_ListView.add(card);
        }
    }

    private void initList() {
        list.add("设计模式test");
        list.add("属性动画test");
        list.add("RxJava test");
        list.add("Canvas类 test");
        list.add("自定义View(一) test");
        list.add("自定义View(二)，圆环交替 test");
        list.add("自定义ViewGroup(一)，四个格子 test");
        list.add("自定义ViewGroup(二)，ViewDragHelper test");
        list.add("自定义ViewGroup(三)，竖直滑动");
        list.add("自定义ViewGroup(四)，标签流布局");
        list.add("自定义View(三)，侧滑菜单");
    }
}
