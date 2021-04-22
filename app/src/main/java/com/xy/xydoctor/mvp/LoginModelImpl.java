package com.xy.xydoctor.mvp;

public class LoginModelImpl implements LoginModel {

    @Override
    public String login(LoginUserBean user) {
        try {
            //模拟网络连接
            Thread.sleep(1000);
            if ("lyd".equals(user.getUserName()) && "123456".equals(user.getPassword())) {
                return "true";
            } else {
                return "账号或密码错误";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
