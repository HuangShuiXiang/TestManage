package com.testmanage.oldtest.card.cardModelView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.materlistview.cardModelView.CardItemView;
import com.testmanage.R;
import com.testmanage.oldtest.card.cardModel.ItemCard;


/**
 * Created by Administrator on 2016/3/28.
 */
public class ItemCardView extends CardItemView<ItemCard> {
    public ItemCardView(Context context) {
        super(context);
    }

    public ItemCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(final ItemCard card) {
        super.build(card);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        CardView card_item = (CardView) findViewById(R.id.card_item);
        tv_name.setText(card.getName());
        card_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                card.getOnAddFreshUIPressedListener().onButtonPressedListener(null, card);
            }
        });
    }
}
