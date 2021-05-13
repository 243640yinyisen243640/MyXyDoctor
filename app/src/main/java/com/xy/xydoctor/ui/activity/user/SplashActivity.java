package com.xy.xydoctor.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPStaticUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.ui.activity.MainActivity;
import com.xy.xydoctor.utils.SharedPreferencesUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:启动屏(设置SplashTheme)
 * 作者: LYD
 * 创建日期: 2019/4/4 14:34
 */
public class SplashActivity extends AppCompatActivity {
    /**
     * 是否同意隐私政策，1是，0或空为否
     */
    private String isAgreePricacyProtect;

    /**
     * 隐私政策弹出框
     */
    private Dialog protectDialog;
    private String spanColor = "#FFC600";//隐私政策span颜色值
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_splash);
        initValues();
        setSplash();
    }

    private void initValues() {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantParam.IS_AGREE_PRIVACY_PROTECT, "0");
        SharedPreferencesUtils.getInfo(getPageContext(), map);
        isAgreePricacyProtect = map.get(ConstantParam.IS_AGREE_PRIVACY_PROTECT);
    }

    private Context getPageContext() {
        return context;
    }


    /**
     * 设置启动页
     */
    private void setSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!"1".equals(isAgreePricacyProtect)) {
                    showPrivacyProtectDialog();
                } else {
                    String token = SPStaticUtils.getString("token");
                    if (TextUtils.isEmpty(token)) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                }

            }
        }, 1000);
    }


    /**
     * 隐私权限提示
     */
    private void showPrivacyProtectDialog() {
        if (protectDialog == null) {
            protectDialog = new Dialog(getPageContext(), R.style.HuaHanSoft_Dialog_Base);
            View view = View.inflate(getPageContext(), R.layout.dialog_privacy_protect, null);
            protectDialog.setContentView(view);
            WindowManager.LayoutParams attributes = protectDialog.getWindow().getAttributes();
            attributes.width = 4 * XyScreenUtils.screenWidth(getPageContext()) / 5;
            protectDialog.getWindow().setAttributes(attributes);
            protectDialog.setCancelable(false);

            TextView serviceAgreementTextView = view.findViewById(R.id.tv_dpp_service_agreement);
            TextView disagreeTextView = view.findViewById(R.id.tv_dpp_disagree);
            TextView agressTextView = view.findViewById(R.id.tv_dpp_agree);

            String privacyProtectHint = getString(R.string.privacy_protect_hint);
            SpannableString ss = new SpannableString(privacyProtectHint);

            ss.setSpan(new UnderLineClickSpan() {
                @Override
                public void onClick(View widget) {
                    jumpToUserPrivacy();
                }
            }, privacyProtectHint.indexOf("《"), privacyProtectHint.indexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor(spanColor)), privacyProtectHint.indexOf("《"), privacyProtectHint.indexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

           /* ss.setSpan(new UnderLineClickSpan() {
                @Override
                public void onClick(View widget) {
                    jumpToUserAgreement();
                }
            }, privacyProtectHint.lastIndexOf("《"), privacyProtectHint.lastIndexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor(spanColor)), privacyProtectHint.lastIndexOf("《"), privacyProtectHint.lastIndexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
            serviceAgreementTextView.setText(ss);
            serviceAgreementTextView.setHighlightColor(Color.TRANSPARENT);
            serviceAgreementTextView.setMovementMethod(LinkMovementMethod.getInstance());

            disagreeTextView.setOnClickListener(v -> {
                protectDialog.dismiss();
                finish();
            });
            agressTextView.setOnClickListener(v -> {
                SharedPreferencesUtils.saveInfo(getPageContext(), ConstantParam.IS_AGREE_PRIVACY_PROTECT, "1");

                isAgreePricacyProtect = "1";
                protectDialog.dismiss();
                String token = SPStaticUtils.getString("token");
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            });
        }
        if (!protectDialog.isShowing()) {
            protectDialog.show();
        }
    }

    /**
     * 页面跳转-用户政策
     */
    private void jumpToUserPrivacy() {
        Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
        intent.putExtra("title", "用户服务协议");
        intent.putExtra("url", "file:///android_asset/user_protocol.html");
        startActivity(intent);
    }

    /**
     * 页面跳转-服务协议
     */
    private void jumpToUserAgreement() {
        //        Intent intent = new Intent(getPageContext(), WebViewHelperActivity.class);
        //        intent.putExtra("title", getString(R.string.privacy_appointment));
        //        intent.putExtra("explainId", "62");
        //        startActivity(intent);
    }

    private abstract class UnderLineClickSpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
            ds.clearShadowLayer();
        }
    }

    //    @Override
    //    public void onWindowFocusChanged(boolean hasFocus) {
    //        super.onWindowFocusChanged(hasFocus);
    //
    //        if (hasFocus && !isFirst) {
    //            showPrivacyProtectDialog();
    //        }
    //    }
}
