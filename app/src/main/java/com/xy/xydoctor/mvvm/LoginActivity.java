package com.xy.xydoctor.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.databinding.ActivityLoginBinding;
import com.xy.xydoctor.ui.activity.MainActivity;

public class LoginActivity extends BaseMVVMActivity<LoginViewModel, ActivityLoginBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBar();

        setTextChangeListener();
        isLogin();
        hideTitleBar();
        bindingView.setViewModel(viewModel);
        bindingView.btLogin.setOnClickListener(this);
        bindingView.tvLoginAgreement.setOnClickListener(this);
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(getString(R.string.login_agreement_left));
        int startUser = stringBuilder.length();
        stringBuilder.append(getString(R.string.login_agreement_user_agreement));
        int endUser = stringBuilder.length();
        stringBuilder.append(getString(R.string.login_agreement_user_and));
        int startPrivacy = stringBuilder.length();
        stringBuilder.append(getString(R.string.login_agreement_privacy));
        int endPrivacy = stringBuilder.length();
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.main_red)), startUser, endUser, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.main_red)), startPrivacy, endPrivacy, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "用户服务协议");
                intent.putExtra("url", "file:///android_asset/user_protocol.html");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, startUser, endUser, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "隐私政策");
                intent.putExtra("url", "http://chronics.xiyuns.cn/index/caseapp");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, startPrivacy, endPrivacy, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bindingView.tvLoginAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        bindingView.tvLoginAgreement.setText(stringBuilder);

    }

    private void setStatusBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .init();
    }

    private void isLogin() {
        String token = SPStaticUtils.getString("token");
        if (!TextUtils.isEmpty(token)) {
            startActivity(new Intent(getPageContext(), MainActivity.class));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                //LiveData观察ViewModel中的数据变化
                viewModel.login().observe(LoginActivity.this, aBoolean -> {
                    //收到回调以后
                    if (!bindingView.tvLoginAgreement.isSelected()) {
                        ToastUtils.showShort("请先勾选协议");
                        return;
                    }
                    if (aBoolean != null && aBoolean) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        KeyboardUtils.hideSoftInput(LoginActivity.this);
                    }
                });
                break;
            case R.id.tv_login_agreement:
                if (bindingView.tvLoginAgreement.isSelected()) {
                    bindingView.tvLoginAgreement.setSelected(false);
                    bindingView.tvLoginAgreement.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rb_follow_up_visit_checked_false, 0, 0, 0);
                } else {
                    bindingView.tvLoginAgreement.setSelected(true);
                    bindingView.tvLoginAgreement.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rb_follow_up_visit_checked, 0, 0, 0);
                }
                break;
        }
    }
}