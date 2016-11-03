package com.testmanage.oldtest.presenter;

import android.text.TextUtils;

import com.testmanage.oldtest.view.ILoginView;


/**
 * Created by Administrator on 2016/3/18.
 */
public class LoginPresenterImpl implements ILoginPresenter{
    private ILoginView loginView;
    public LoginPresenterImpl(ILoginView iLoginView){
        this.loginView = iLoginView;
    }

    @Override
    public void doLogin(String name, String pwd) {
        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd)){
            loginView.loginResult("1");
            return;
        }
        loginView.loginResult("0");
    }

    @Override
    public void clear() {
        loginView.clearText();
    }
}
