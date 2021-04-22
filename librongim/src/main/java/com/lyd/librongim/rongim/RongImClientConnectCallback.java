package com.lyd.librongim.rongim;

import android.util.Log;

import io.rong.imlib.RongIMClient;

/**
 * 描述: 融云连接回调
 * 作者: LYD
 * 创建日期: 2019/12/4 14:33
 */
public class RongImClientConnectCallback extends RongIMClient.ConnectCallback {
    private static final String TAG = "RongImCallback";
    private RongImInterface.ConnectCallback callback;

    public RongImClientConnectCallback(RongImInterface.ConnectCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSuccess(String s) {
        Log.e(TAG, "连接成功");
        callback.onSuccess();
    }

    @Override
    public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
        Log.e(TAG, "连接失败code==" + connectionErrorCode.getValue());
    }

    @Override
    public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
        //消息数据库打开，可以进入到主页面
    }
}
