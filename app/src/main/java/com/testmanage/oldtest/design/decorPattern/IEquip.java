package com.testmanage.oldtest.design.decorPattern;

/**
 * Created by Administrator on 2016/3/23.
 * 装备超类
 */
public interface IEquip {
    /**
     * 计算攻击力
     */
    int calculateAttack();

    /**
     * 描述
     * @return
     */
    String description();
}
