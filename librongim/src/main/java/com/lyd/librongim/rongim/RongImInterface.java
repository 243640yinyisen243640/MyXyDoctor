package com.lyd.librongim.rongim;

import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * 描述: 融云工具类使用到的接口
 * 作者: LYD
 * 创建日期: 2019/12/6 13:53
 */
public interface RongImInterface {

    /**
     * 成功连接融云后的回调
     */
    interface ConnectCallback {
        void onSuccess();
    }

    /**
     * 设置连接状态变化的监听器
     */
    interface ConnectionStatusListener {
        void onChanged(RongIMClient.ConnectionStatusListener.ConnectionStatus connectionStatus);
    }


    /**
     * 融云(会话界面)操作的监听器
     */
    interface ConversationClickListener {
        //当点击用户头像后执行。
        boolean onUserPortraitClick(UserInfo userInfo);

        //当长按用户头像后执行。
        //boolean onUserPortraitLongClick();

        //当点击消息时执行。
        //boolean onMessageClick();

        //当点击链接消息时执行。
        //boolean onMessageLinkClick();

        //当长按消息时执行。
        //boolean onMessageLongClick();
    }


    /**
     * 设置(会话列表界面)操作的监听器
     */
    interface ConversationListBehaviorListener {
        //点击会话列表中的item时执行。
        boolean onConversationClick(UIConversation uiConversation);

        //长按会话列表中的item时执行。
        //boolean onConversationLongClick();

        //当点击会话头像后执行。
        //boolean onConversationPortraitClick();

        //当长按会话头像后执行。
        //boolean onConversationPortraitLongClick();
    }
}
