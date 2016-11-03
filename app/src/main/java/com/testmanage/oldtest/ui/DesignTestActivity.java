package com.testmanage.oldtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorSet;
import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;
import com.testmanage.oldtest.design.StrategyPattern.iFace.Role;
import com.testmanage.oldtest.design.StrategyPattern.iFaceImpl.AttackJYSG;
import com.testmanage.oldtest.design.StrategyPattern.iFaceImpl.DefendJZZ;
import com.testmanage.oldtest.design.StrategyPattern.iFaceImpl.DisplayA;
import com.testmanage.oldtest.design.StrategyPattern.iFaceImpl.RoleA;
import com.testmanage.oldtest.design.StrategyPattern.iFaceImpl.RunJCTQ;
import com.testmanage.oldtest.design.decorPattern.ArmEquip;
import com.testmanage.oldtest.design.decorPattern.BlueGemDecorator;
import com.testmanage.oldtest.design.decorPattern.IEquip;
import com.testmanage.oldtest.design.decorPattern.RedGemDecorator;
import com.testmanage.oldtest.presenter.ILoginPresenter;
import com.testmanage.oldtest.presenter.LoginPresenterImpl;
import com.testmanage.oldtest.util.ToastUitl;
import com.testmanage.oldtest.view.ILoginView;
import com.testmanage.oldtest.view.MoveTestView;


public class DesignTestActivity extends BaseActivity implements ILoginView {
    private EditText ed_user;
    private EditText ed_pwd;
    private Button btn_login;
    private Button btn_clear;
    private ILoginPresenter loginPresenter;
    private MoveTestView tv_num;
    AnimatorSet animatorSet;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_design_test;
    }

    @Override
    protected void initViewsAndEvents() {
        ed_user = (EditText) findViewById(R.id.ed_user);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        tv_num = (MoveTestView) findViewById(R.id.tv_num);
        //mvp模式
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.doLogin(ed_user.getText().toString(),ed_pwd.getText().toString());

            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.clear();
            }
        });
        loginPresenter = new LoginPresenterImpl(this);

        //装饰者模式
        IEquip iEquip = new BlueGemDecorator(new RedGemDecorator(new BlueGemDecorator(new ArmEquip())));
        ToastUitl.showShort(this,"攻击力" + iEquip.calculateAttack());
        ToastUitl.showShort(this,"描述" + iEquip.description());

        //策略模式
        Role role = new RoleA("张无忌");
        role.setAttackBehavior(new AttackJYSG())
                .setDefendBehavior(new DefendJZZ())
                .setDisplayBehavior(new DisplayA())
                .setRunBehavior(new RunJCTQ());
        role.attack();
        role.defend();
        role.display();
        role.run();

        //工厂模式

        //观察者模式

        //mvc模式

        //mvvm模式
    }

    @Override
    public void loginResult(String code) {
        if(code .equals("0")){
            Toast.makeText(this,"登录成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"登录失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void clearText() {
        ed_pwd.setText("");
        ed_user.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(animatorSet != null){
            animatorSet.cancel();
        }
    }


}
