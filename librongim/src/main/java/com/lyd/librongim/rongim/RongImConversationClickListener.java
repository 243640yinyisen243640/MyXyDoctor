package com.lyd.librongim.rongim;

import android.content.Context;
import android.util.Log;
import android.view.View;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * 描述: 融云会话界面操作的监听器。
 * 作者: LYD
 * 创建日期: 2019/12/4 15:49
 */
public class RongImConversationClickListener implements RongIM.ConversationClickListener {
    private static final String TAG = "RongImConversationClickListener";
    private RongImInterface.ConversationClickListener listener;

    public RongImConversationClickListener(RongImInterface.ConversationClickListener listener) {
        this.listener = listener;
    }

    /**
     * 自己处理了点击后的逻辑处理,则返回 true,否则返回false
     */
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        listener.onUserPortraitClick(userInfo);
        Log.e(TAG, "onUserPortraitClick==" + "头像被点击了");
        return true;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        Log.e(TAG, "onUserPortraitLongClick==" + "头像被长按了");
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        Log.e(TAG, "onMessageClick==" + "消息被点击了");
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s, Message message) {
        Log.e(TAG, "onMessageLinkClick==" + "链接消息被点击了");
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        Log.e(TAG, "onMessageLongClick==" + "消息被长按了");
        return false;
    }
}
