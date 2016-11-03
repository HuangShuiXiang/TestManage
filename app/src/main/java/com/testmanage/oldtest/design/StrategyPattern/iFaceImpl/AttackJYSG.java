package com.testmanage.oldtest.design.StrategyPattern.iFaceImpl;

import android.util.Log;

import com.testmanage.oldtest.design.StrategyPattern.iFace.IAttackBehavior;


/**
 * Created by Administrator on 2016/3/23.
 */
public class AttackJYSG implements IAttackBehavior
{
    @Override
    public void attack() {
        Log.i("attack", "九阳神功");
    }
}
