package com.xy.xydoctor.mvp;


/**
 * 描述: Activity中的 UI逻辑 抽象成 View接口
 * 作者: LYD
 * 创建日期: 2020/4/17 16:32
 */

public interface LoginView {
    /**
     * 登录成功
     */
    void onLoginSuccess();

    /**
     * 登录失败
     *
     * @param errorMsg
     */
    void onLoginFailed(String errorMsg);
}
