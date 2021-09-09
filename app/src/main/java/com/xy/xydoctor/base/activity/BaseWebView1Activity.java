package com.xy.xydoctor.base.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.view.X5WebView;

/**
 * 类描述：
 * 类传参： title (String) 标题
 *
 * @author android.zsj
 * @date 2019/9/25
 */
public class BaseWebView1Activity extends XYSoftUIBaseActivity {

    /**
     * webView
     */
    private X5WebView webView;
    /**
     * 说明/协议链接
     */
    private String url;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(getIntent().getStringExtra("title"));
        topViewManager().moreTextView().setText("保存");
        topViewManager().lineView().setVisibility(View.GONE);
        topViewManager().moreTextView().setOnClickListener(v -> {
            String call = "javascript:saveFormData()";
            webView.loadUrl(call);
        });
        containerView().addView(initView());
        containerView().setBackgroundColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
        url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            setWebViewData(webView, url);
        }
    }


    public View initView() {
        View view = View.inflate(getPageContext(), R.layout.base_activity_webview_help, null);
        progressBar = getViewByID(view, R.id.progressBar);
        webView = getViewByID(view, R.id.wv_helper);
        return view;
    }

    protected void setWebViewData(final X5WebView webView, final String url) {
        webView.setVisibility(View.INVISIBLE);
        initHardwareAccelerate();

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String s) {
                //这个是让浏览器打开下载
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                if (!TextUtils.isEmpty(s)) {
                    if (s.endsWith(".apk")) {
                        Uri content_url = Uri.parse(s);
                        Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
                        startActivity(intent);
                        return true;
                    } else {
                        view.loadUrl(s);
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);

            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                //下载文件
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int i) {
                super.onProgressChanged(view, i);
                if (i == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    webView.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(i);//设置进度值
                }
                super.onProgressChanged(webView, i);
            }
        });
    }

    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        } catch (Exception e) {
        }
    }
}
