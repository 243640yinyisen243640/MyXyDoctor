package com.xy.xydoctor.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPStaticUtils;
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
        isLogin();
        hideTitleBar();
        bindingView.setViewModel(viewModel);
        bindingView.btLogin.setOnClickListener(this);
        bindingView.rlServiceAgreement.setOnClickListener(this);
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
                viewModel.login().observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        //收到回调以后
                        if (aBoolean != null && aBoolean) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            KeyboardUtils.hideSoftInput(LoginActivity.this);
                        }
                    }
                });
                break;
            case R.id.rl_service_agreement:
                Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "用户服务协议");
                intent.putExtra("url", "file:///android_asset/user_protocol.html");
                startActivity(intent);
                break;
        }
    }
}