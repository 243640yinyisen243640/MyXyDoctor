package com.xy.xydoctor.base.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.lyd.baselib.widget.view.MyWebView;
import com.xy.xydoctor.R;

import butterknife.BindView;


/**
 * 描述: 基础webview
 * 作者: LYD
 * 创建日期: 2019/3/28 11:11
 */
public class BaseWebViewActivity extends BaseActivity {
    private static final String TAG = "BaseWebViewActivity";
    @BindView(R.id.pb_web_progress)
    ProgressBar pbWebProgress;
    @BindView(R.id.ll_base_webview)
    LinearLayout llBaseWebView;

    //更新为X5WebView
    private MyWebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setWebView();
        setPageTitle();
        loadUrl();
    }

    /**
     * 添加WebView
     * 不设置的话,有的网页高度不够!!!
     */
    private void setWebView() {
        //初始化
        webView = new MyWebView(getApplicationContext());
        //添加
        llBaseWebView.addView(webView);
        //动态设置宽高为充满
        ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        webView.setLayoutParams(layoutParams);
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
    }

    /**
     * 加载网页
     */
    private void loadUrl() {
        String url = getIntent().getStringExtra("url");
        initWebView(url);
    }


    /**
     * 开始加载
     *
     * @param url
     */
    private void initWebView(String url) {
        webView.loadUrl(url);
        //使用WebView加载网页,而不是浏览器
        webView.setWebViewClient(getWebViewClient());
        webView.setWebChromeClient(getWebChromeClient());
    }

    @Override
    protected void onResume() {
        webView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.onDestroy();
        super.onDestroy();
    }

    /**
     * 处理各种通知 & 请求事件
     *
     * @return
     */
    private WebViewClient getWebViewClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "拦截 url 跳转,在里边添加点击链接跳转或者操作");
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, "开始加载网页回调");
                pbWebProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbWebProgress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        };
        return webViewClient;
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     *
     * @return
     */
    private WebChromeClient getWebChromeClient() {
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            /**
             * 收到加载进度变化
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //Log.e(TAG, "newProgress==" + newProgress);
                if (newProgress == 100) {
                    pbWebProgress.setVisibility(View.GONE);
                } else {
                    pbWebProgress.setProgress(newProgress);
                    pbWebProgress.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        return webChromeClient;
    }


    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //        //用来确认WebView里是否还有可回退的历史记录。
    //        //通常我们会在WebView里重写返回键的点击事件，通过该方法判断WebView里是否还有历史记录，若有则返回上一页。
    //        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
    //            webView.goBack();
    //            return true;
    //        }
    //        return super.onKeyDown(keyCode, event);
    //    }
}
