package com.lyd.librongim;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;


/**
 * 描述: 配置会话列表
 * 作者: LYD
 * 创建日期: 2019/2/28 9:27
 */
public class ConversationListActivity extends FragmentActivity {
    private static final String TAG = "ConversationListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
    }
}
