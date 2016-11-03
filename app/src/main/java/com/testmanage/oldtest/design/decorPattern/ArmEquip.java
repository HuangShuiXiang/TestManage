package com.testmanage.oldtest.design.decorPattern;


/**
 * Created by Administrator on 2016/3/23.
 * 武器
 */
public class ArmEquip implements IEquip {

    @Override
    public int calculateAttack() {
        return 20;
    }

    @Override
    public String description() {
        return "屠龙刀";
    }
}
