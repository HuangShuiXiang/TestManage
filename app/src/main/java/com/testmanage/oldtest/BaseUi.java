package com.testmanage.oldtest;

import android.view.View;

/**
 * Created by liucanwen on 15/12/14.
 */
public interface BaseUi {

    void showToastLong(String msg);

    void showToastShort(String msg);

    void showKProgress(String label);

    void showKProgress(String label, boolean cancelable);

    void dimissKProgress();
}
