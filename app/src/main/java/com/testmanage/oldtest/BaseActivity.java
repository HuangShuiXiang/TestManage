package com.testmanage.oldtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.testmanage.R;

/**
 * Created by liucanwen on 15/12/4.
 *
 * @detail 普通页面基类，注册通用控件，封装通用进度，弹窗提示，toast
 * @tip writer 黄水祥
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseUi,IPageStatusUi{
    //基类上下文
    protected Context mContext = null;
    //公用标题栏
    protected Toolbar mToolbar;
    //沉浸式通知栏的一个开源库SystemBarTint,..
    private SystemBarTintManager tintManager;
    //activity异常关闭保存数据
    protected Bundle savedInstanceState;
    //网络访问失败iv提示
    private ImageView pageStatusIconIv;
    //网络访问失败text提示
    private TextView pageStatusTextTv;
    private int colorPrimary = R.color.colorPrimary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        this.savedInstanceState = savedInstanceState;
        mContext = this;
        setupSystemBar();
        //设置布局
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initViewsAndEvents();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) findViewById( R.id.common_toolbar);
        pageStatusIconIv = (ImageView) findViewById(R.id.page_status_icon_iv);
        pageStatusTextTv = (TextView) findViewById(R.id.page_status_text_tv);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dimissKProgress();
        //友盟
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dimissKProgress();
    }

    /**
     * 沉浸式状态栏
     */
    public void setupSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(getStautausBarColor());
    }

    public int getStautausBarColor() {
        return setStatusBarColor();
    }

    //基类提供修改状态栏颜色的方法。
    public int setStatusBarColor() {
        return colorPrimary;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 绑定布局xml文件
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化布局和事件，在onFirstUserVisible之前执行
     */
    protected abstract void initViewsAndEvents();

    @Override
    public void showToastLong(String msg) {
        if (null != msg && !TextUtils.isEmpty(msg)) {
            toast(msg, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void showToastShort(String msg) {
        if (null != msg && !TextUtils.isEmpty(msg)) {
            toast(msg, Toast.LENGTH_SHORT);
        }
    }

    private Toast mToast = null;

    private void toast(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        if (!isFinishing()) {
            mToast.show();
        }
    }

    //    KProgressHUD builder = null;
    public MaterialDialog builder = null;

    @Override
    public void showKProgress(String label) {

        builder = new MaterialDialog.Builder(this)
                .content(label)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();

        builder.show();
    }

    /**
     * 显示进度框
     *
     * @param label
     * @param isCancelable
     */
    @Override
    public void showKProgress(String label, boolean isCancelable) {

        builder = new MaterialDialog.Builder(this)
                .content(label)
                .cancelable(isCancelable)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();

        builder.show();
    }

    /**
     * 销毁进度框
     */
    @Override
    public void dimissKProgress() {
        if (builder != null && builder.isShowing()) {
            builder.dismiss();
        }
    }
    /**
     * 跳转Activity
     *
     * @param activity
     * @param bundle
     */
    public void gotoActivity(Class<? extends Activity> activity,
                             Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * 显示网络失败状态文字
     *
     * @param message
     */
    @Override
    public void showPageStatusView(String message) {
        if (null != pageStatusTextTv) {
            pageStatusTextTv.setVisibility(View.VISIBLE);
            pageStatusTextTv.setText(message);
        }
    }
    /**
     * 显示网络失败状态icon
     *
     * @param iconRes
     * @param message
     */
    @Override
    public void showPageStatusView(int iconRes, String message) {
        if (null != pageStatusTextTv) {
            pageStatusTextTv.setVisibility(View.VISIBLE);
            pageStatusTextTv.setText(message);
        }

        if (null != pageStatusIconIv) {
            pageStatusIconIv.setVisibility(View.VISIBLE);
            pageStatusIconIv.setImageResource(iconRes);
        }
    }

    /**
     * \
     * 隐藏网络失败状态
     */
    @Override
    public void hidePageStatusView() {
        if (null != pageStatusTextTv) {
            pageStatusTextTv.setVisibility(View.GONE);
        }

        if (null != pageStatusIconIv) {
            pageStatusIconIv.setVisibility(View.GONE);
        }
    }

    public TextView getPageStatusTextTv() {
        return pageStatusTextTv;
    }

}
