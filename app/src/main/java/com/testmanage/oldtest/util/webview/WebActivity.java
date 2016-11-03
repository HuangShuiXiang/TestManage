package com.testmanage.oldtest.util.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ProgressBar;

import com.testmanage.R;
import com.testmanage.oldtest.BaseActivity;
import com.testmanage.oldtest.util.NetUtils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 黄水祥
 * @date 2016/06/10
 * @detail 公用H5 访问页
 */
@SuppressLint("NewApi")
public class WebActivity extends BaseActivity {
    Html5Webview mWebView;
    ProgressBar progressbar;
    public static final String WebUrl = "WebUrl";
    public static final String ISShowSavaBtn = "ISShowSavaBtn";
    public static final String MaDanImageUrl = "MaDanImageUrl";
    public static final String Title = "Title";
    public static final String OrderId = "OrderId";
    private String url;
    private String imgeUrl;
    private String SavePath;
    private String orderId;
    private boolean isShowSavaBtn = true;
    MenuItem item;

    //    protected Toolbar mToolbar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isShowSavaBtn) {
            getMenuInflater().inflate(R.menu.menu_save, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if (TextUtils.isEmpty(url)) {
                showToastLong("码单链接为空！");
            } else if (TextUtils.isEmpty(imgeUrl)) {
                showToastLong("码单图片为空，请确认bps已经上传了码单图片");
            } else {
                savaMada(imgeUrl);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViewsAndEvents() {
        initView(savedInstanceState);
    }


    private void initView(Bundle savedInstanceState) {

        String title = getIntent().getStringExtra(Title);
        url = getIntent().getStringExtra(WebUrl);
        isShowSavaBtn = getIntent().getBooleanExtra(ISShowSavaBtn, true);
        imgeUrl = getIntent().getStringExtra(MaDanImageUrl);
        orderId = getIntent().getStringExtra(OrderId);
        mWebView = (Html5Webview) findViewById(R.id.webView);
//        mToolbar = (Toolbar) findViewById(R.id.common_toolbar);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        SavePath = android.os.Environment.getExternalStorageDirectory().toString() + "/BaibuBD/baibu_madan/";
        //设置进度条
        mWebView.setCallBack(new Html5Webview.ProgressbarCallBack() {
            @Override
            public void setProgress(int newProgress) {
                progressbar.setProgress(newProgress);
            }

            @Override
            public void setVisibility(int visibility) {
                setProgressStatus(visibility);
            }

            @Override
            public void onLoadError() {
//                showPageStatusView("网页加载失败，点击重新加载");
            }

            @Override
            public void onLoadFinish() {
                dimissKProgress();
            }
        });

        setTitle(title);
        if (TextUtils.isEmpty(url)) {
            showToastShort("访问链接为空");
            finish();
        }
        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            loadUrl();
        }
        getPageStatusTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKProgress("正在重新加载链接...");
                hidePageStatusView();
                loadUrl();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showToastShort("加载完成！");
                    progressbar.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 加载网页
     */
    private void loadUrl() {
        if (url != null && NetUtils.isNetworkAvailable(mContext)) {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
//            cookieManager.setCookie(ApiContants.getRootUrl(), MyApplication.getInstance().getCookie());//cookies是在HttpClient中获得的cookie
            CookieSyncManager.getInstance().sync();
            mWebView.loadUrl(url);
        } else {
            showPageStatusView("无网络连接，点击重新加载!");
        }
    }

    /**
     * 设置进度条属性
     */
    private void setProgressStatus(int visibility) {
        if (visibility == View.GONE) {
            if (progressbar.getProgress() != 100) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int progress = progressbar.getProgress();
                        while (progress < 100) {
                            try {
                                Thread.sleep(50);
                                progressbar.setProgress(progress);
                                progress += 5;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (progress >= 100) {
                            progressbar.setProgress(progress);
                            handler.sendEmptyMessage(0);
                        }

                    }
                }).start();
            }
        } else {
            progressbar.setVisibility(visibility);
        }
    }

    /**
     * 保存码单图片
     *
     * @param imgUrl
     */
    private void savaMada(String imgUrl) {
        showKProgress("图片保存中......");
//        ImageLoader.getInstance().loadImage(imgUrl, new ImageLoadingListener() {//url是图片的路径
//
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view,
//                                        FailReason failReason) {
//                // TODO Auto-generated method stub
//                dimissKProgress();
//                showToastLong("保存图片失败");
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                String saveFilename = "订单号" + orderId + "的码单" + ".png";
//                File file = ImageUtil.BitmapToFile(SavePath, saveFilename, loadedImage);
//                dimissKProgress();
//                showToastLong("码单图片已保存到" + SavePath);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }

    }

    @Override
    protected void onDestroy() {

        if (mWebView != null && mWebView.getSettings() != null) {//解决Receiver not registered: android.widget.ZoomButtonsController
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.setVisibility(View.GONE);// 把destroy()延后
            long timeout = ViewConfiguration.getZoomControlsTimeout();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mWebView.removeAllViews();
                            mWebView.destroy();
                        }
                    });

                }
            }, timeout);
        }

        super.onDestroy();
    }
}