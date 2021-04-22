package com.xy.xydoctor.mvp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.wei.android.lib.colorview.view.ColorButton;
import com.xy.xydoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpLoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.bt_login)
    ColorButton btLogin;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }


    @Override
    public void onLoginSuccess() {
        //相关逻辑
        ToastUtils.showShort("登录成功");
    }

    @Override
    public void onLoginFailed(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        String username = etLoginUsername.getText().toString().trim();
        String pwd = etLoginPwd.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        loginPresenter.login(new LoginUserBean(username, pwd));
    }
}
