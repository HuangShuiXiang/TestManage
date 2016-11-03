package com.testmanage.oldtest.design.StrategyPattern.iFaceImpl;

import android.util.Log;

import com.testmanage.oldtest.design.StrategyPattern.iFace.IDefendBehavior;


/**
 * Created by Administrator on 2016/3/23.
 */
public class DefendJZZ implements IDefendBehavior {
    @Override
    public void defend() {
        Log.i("defend","金钟罩");
    }
}
