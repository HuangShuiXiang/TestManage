package com.testmanage.oldtest.card.cardModel;

import android.content.Context;

import com.materlistview.cardModel.ExtendedCard;
import com.testmanage.R;


/**
 * Created by Administrator on 2016/3/28.
 */
public class ItemCard extends ExtendedCard {
    private String name;
    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ItemCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.card_item;
    }

}
