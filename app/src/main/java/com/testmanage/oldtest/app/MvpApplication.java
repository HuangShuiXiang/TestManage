package com.testmanage.oldtest.app;

import android.app.Application;
import android.content.pm.PackageManager;

import com.materlistview.CrashHandler.CrashHandler;

/**
 * Created by Administrator on 2016/3/28.
 */
public class MvpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PackageManager packageManager = getPackageManager();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(),"testmanage");
    }
}
