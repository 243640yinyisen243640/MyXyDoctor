package com.xy.xydoctor.mvp;

public interface LoginModel {
    /**
     * 登录
     *
     * @param user
     * @return 约定返回"true"为登录成功，其他为登录失败的错误信息
     */
    String login(LoginUserBean user);
}
