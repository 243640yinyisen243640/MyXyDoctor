package com.lyd.librongim;

import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SPStaticUtils;

import io.rong.imkit.fragment.ConversationFragment;

/**
 * 描述:  MyConversationFragment
 * 作者: LYD
 * 创建日期: 2019/12/1 11:21
 */
public class MyConversationFragment extends ConversationFragment {

    /**
     * 发送按钮监听
     *
     * @param view 发送控件
     * @param text 发送文本
     */
    @Override
    public void onSendToggleClick(View view, String text) {
        Log.e(TAG, "onSendToggleClick: " + text);
        int type = SPStaticUtils.getInt("docType");
        String docName = SPStaticUtils.getString("docName");
        if (3 == type) {
            text += "\n" + "---该消息来自于" + docName;
        }
        super.onSendToggleClick(view, text);
    }
}
