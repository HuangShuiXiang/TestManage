package com.testmanage.oldtest.design.decorPattern;

/**
 * Created by Administrator on 2016/3/23.
 */
public class RedGemDecorator implements IEquipDecorator {
    private IEquip iEquip;
    public RedGemDecorator(IEquip iEquip){
        this.iEquip = iEquip;
    }
    @Override
    public int calculateAttack() {
        return 5+iEquip.calculateAttack();
    }

    @Override
    public String description() {
        return iEquip.description() +"红宝石";
    }
}
