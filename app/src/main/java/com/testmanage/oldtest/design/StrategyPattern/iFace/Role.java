package com.testmanage.oldtest.design.StrategyPattern.iFace;

/**
 * Created by Administrator on 2016/3/23.
 */
public abstract class Role {
    protected String name;
    protected IAttackBehavior attackBehavior;
    protected IDefendBehavior defendBehavior;
    protected IDisplayBehavior displayBehavior;
    protected IRunBehavior runBehavior;

    public Role setAttackBehavior(IAttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
        return this;
    }

    public Role setDefendBehavior(IDefendBehavior defendBehavior) {
        this.defendBehavior = defendBehavior;
        return this;
    }

    public Role setDisplayBehavior(IDisplayBehavior displayBehavior) {
        this.displayBehavior = displayBehavior;
        return this;
    }

    public Role setRunBehavior(IRunBehavior runBehavior) {
        this.runBehavior = runBehavior;
        return this;
    }
    public void attack(){
        attackBehavior.attack();
    }
    public void defend(){
        defendBehavior.defend();
    }
    public void display(){
        displayBehavior.display();
    }
    public void run(){
        runBehavior.run();
    }
}
