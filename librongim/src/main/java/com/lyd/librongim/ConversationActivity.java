package com.lyd.librongim;

import android.os.Bundle;

import com.blankj.utilcode.util.KeyboardUtils;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.librongim.databinding.ActivityConversationBinding;


/**
 * 描述: 配置会话页面
 * 作者: LYD
 * 创建日期: 2019/2/28 9:57
 */
public class ConversationActivity extends BaseViewBindingActivity<ActivityConversationBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        KeyboardUtils.fixAndroidBug5497(this);
        String title = getIntent().getData().getQueryParameter("title");
        setTitle(title);
    }
}
