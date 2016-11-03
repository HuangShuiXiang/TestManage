package com.testmanage.oldtest.util.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A webview which support many Html5 feature like video ,audio etc.
 */
public class Html5Webview extends WebView {

    private Context mContext;
    private MyWebChromeClient mWebChromeClient;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    @SuppressLint("NewApi")
    private void init(Context context) {
        mContext = context;
        Activity mActivity = (Activity) mContext;

        // Configure the webview
        WebSettings s = getSettings();
        s.setBuiltInZoomControls(true);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        // s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        mWebChromeClient = new MyWebChromeClient();
        setWebChromeClient(mWebChromeClient);

        setWebViewClient(new MyWebViewClient());

        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        setWebContentsDebuggingEnabled(true);
        // enable Web Storage: localStorage, sessionStorage
        s.setDomStorageEnabled(true);
        //开放给测试人员使用的 打包时关闭
//        setWebContentsDebuggingEnabled(true);
    }

    public Html5Webview(Context context) {
        super(context);
        init(context);
    }

    public Html5Webview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Html5Webview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canGoBack()) {
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(final WebView view, String url) {
            // TODO Auto-generated method stub
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            callBack.onLoadFinish();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            callBack.onLoadError();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Html5Webview.this.setVisibility(View.GONE);
            mCustomViewCallback = callback;
        }

        @Override
        public void onHideCustomView() {
            mCustomViewCallback.onCustomViewHidden();
            Html5Webview.this.setVisibility(View.VISIBLE);
            Html5Webview.this.goBack();
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            /*((Activity) mContext).getWindow().setFeatureInt(
                    Window.FEATURE_PROGRESS, newProgress * 100);*/
            if (newProgress == 100) {
                callBack.setVisibility(GONE);
                visibility = GONE;
            } else {
                if (visibility == GONE) {
                    visibility = VISIBLE;
                    callBack.setVisibility(VISIBLE);
                }
                callBack.setProgress(newProgress);
            }
        }


        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

    }

    private ProgressbarCallBack callBack;
    private int visibility = View.GONE;

    public void setCallBack(ProgressbarCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ProgressbarCallBack {
        //设这进度
        void setProgress(int newProgress);
        //显示影厂进度条
        void setVisibility(int visibility);
        //网页加载失败
        void onLoadError();
        //网页加载失败
        void onLoadFinish();
    }
}
