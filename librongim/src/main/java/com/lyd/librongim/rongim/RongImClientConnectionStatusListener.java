package com.lyd.librongim.rongim;

import io.rong.imlib.RongIMClient;


/**
 * 描述: 设置连接状态变化的监听器
 * 作者: LYD
 * 创建日期: 2019/12/4 16:40
 */
public class RongImClientConnectionStatusListener implements RongIMClient.ConnectionStatusListener {
    private RongImInterface.ConnectionStatusListener listener;

    public RongImClientConnectionStatusListener(RongImInterface.ConnectionStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        listener.onChanged(connectionStatus);
    }
}
