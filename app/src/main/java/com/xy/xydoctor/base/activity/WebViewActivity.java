package com.xy.xydoctor.base.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.utils.JavascriptInterfaces;
import com.xy.xydoctor.utils.PhotoUtils1;
import com.xy.xydoctor.window.CommunityChoosePopupWindow;

import java.io.File;

public class WebViewActivity extends XYSoftUIBaseActivity {
    private WebView webView;
    private Uri imageUri;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int PHOTO_REQUEST = 100;

    private String url;
    private String id;
    private String title;
    private String status;

    private String statusStr;

    private CommunityChoosePopupWindow choosePopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        Log.i("yys", "url==" + url);
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        topViewManager().titleTextView().setText(title);
        topViewManager().titleTextView().setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
        View view = View.inflate(getPageContext(), R.layout.activty_webview, null);
        containerView().addView(view);
        webView = findViewById(R.id.wv_activity);
        if (!"?????????".equals(status)) {
            statusStr = "1";
            topViewManager().moreTextView().setVisibility(View.VISIBLE);
            topViewManager().moreTextView().setText("??????");
        } else {
            statusStr = "0";
            topViewManager().moreTextView().setVisibility(View.GONE);
        }

        topViewManager().backTextView().setOnClickListener(v -> {
            webViewFinish();
        });
        topViewManager().moreTextView().setOnClickListener(v -> {
            //Android??????h5
            String call = "javascript:saveFormData()";
            webView.loadUrl(call);
        });
        setWebView();
    }

    private void setWebView() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // ???????????????Content Provider????????????????????? true
        settings.setAllowFileAccess(true);    // ??????????????????????????????????????? true
        // ??????????????????file url?????????Javascript?????????????????????????????? false
        settings.setAllowFileAccessFromFileURLs(false);
        // ??????????????????file url?????????Javascript??????????????????(????????????,http,https)???????????? false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //??????JavaScript??????
        settings.setJavaScriptEnabled(true);
        // ????????????
        settings.setSupportZoom(true);
        //??????h5?????????????????????
        webView.setWebChromeClient(new MyChromeWebClient());
        //h5??????Android                                               ????????????????????????????????????   ??????Android??????window.??????????????????
        webView.addJavascriptInterface(new JavascriptInterfaces(this), "Android");
        //??????WebView????????????????????????
        //        webView.loadUrl("http://d.xiyuns.cn/mobile/community/bloodsugar?id=1");
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return webViewFinish();
        }
        return false;
    }

    private boolean webViewFinish() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
        }
        return false;
    }

    //????????? WebChromeClient ??????WebView???????????????????????????<input type=file> ?????????????????????
    public class MyChromeWebClient extends WebChromeClient {
        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.i("xie", "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
            mUploadMessage = uploadMsg;
            takePhoto();
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.i("xie", "onShowFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");

            mUploadCallbackAboveL = filePathCallback;


            if (choosePopupWindow != null && choosePopupWindow.isShowing()) {
                choosePopupWindow.dismiss();
            }

            if (choosePopupWindow==null){
                choosePopupWindow = new CommunityChoosePopupWindow(getPageContext(), object -> {

                    int position = (int) object;
                    switch (position) {
                        case 0:
                            mUploadCallbackAboveL.onReceiveValue(null);
                            mUploadCallbackAboveL = null;
                            break;
                        case 1:
                            PermissionUtils
                                    .permission(PermissionConstants.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .callback(new PermissionUtils.SimpleCallback() {
                                        @Override
                                        public void onGranted() {
                                            takePhoto();
                                        }

                                        @Override
                                        public void onDenied() {
                                            ToastUtils.showShort("???????????????????????????");
                                        }
                                    }).request();
                            break;
                        case 2:
                            PermissionUtils
                                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .callback(new PermissionUtils.SimpleCallback() {
                                        @Override
                                        public void onGranted() {
                                            album();
                                        }

                                        @Override
                                        public void onDenied() {
                                            ToastUtils.showShort("???????????????????????????");
                                        }
                                    }).request();
                            break;
                        default:
                            break;
                    }
                });
            }

            choosePopupWindow.showAtLocation(containerView(), Gravity.BOTTOM, 0, 0);


            return true;
        }
    }

    private void takePhoto() {
        File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getPageContext(), getPackageName() + ".FileProvider", fileUri);//??????FileProvider????????????content?????????Uri
        }
        PhotoUtils1.takePicture(WebViewActivity.this, imageUri, PHOTO_REQUEST);//??????
    }

    private void album() {
        File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getPageContext(), getPackageName() + ".FileProvider", fileUri);//??????FileProvider????????????content?????????Uri
        }
        PhotoUtils1.openPic(WebViewActivity.this, PHOTO_REQUEST);//????????????
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else {

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != PHOTO_REQUEST || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setWebChromeClient(null);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }
}


