package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;

/**
 * Author: LYD
 * Date: 2021/11/29 15:44
 * Description:
 */
public class CommunityDiseaseTipsActivity extends XYSoftUIBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_follow_tips);
        containerView().addView(initView());
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_disease_tips, null);
        return view;
    }
}
