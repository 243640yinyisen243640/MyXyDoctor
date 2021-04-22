package com.lyd.librongim.rongim;

import android.content.Context;
import android.view.View;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

/**
 * 描述: 融云会话列表监听
 * 作者: LYD
 * 创建日期: 2019/12/9 11:29
 */
public class RongImConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
    private RongImInterface.ConversationListBehaviorListener listener;

    public RongImConversationListBehaviorListener(RongImInterface.ConversationListBehaviorListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        listener.onConversationClick(uiConversation);
        return false;
    }
}
