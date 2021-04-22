package com.xy.xydoctor.mvp;


import android.os.Handler;
import android.os.Looper;

/**
 * 描述:Activity中的 业务逻辑 抽象成 Presenter接口
 * 作者: LYD
 * 创建日期: 2020/4/17 17:13
 */
public class LoginPresenter {
    private final LoginView loginView;
    private final LoginModelImpl loginModel;

    public LoginPresenter(LoginView userView) {
        this.loginView = userView;
        this.loginModel = new LoginModelImpl();
    }

    /**
     * 登录
     *
     * @param user
     */
    public void login(final LoginUserBean user) {
        new Thread() {
            @Override
            public void run() {
                final String res = loginModel.login(user);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if ("true".equals(res)) {
                            loginView.onLoginSuccess();
                        } else {
                            loginView.onLoginFailed(res);
                        }
                    }
                });
            }
        }.start();
    }

}
