package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;

/**
 * Author: LYD
 * Date: 2021/9/2 9:06
 * Description:
 */
public class SettingsPropertyActivity extends XYSoftUIBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_setting, null);
        return view;
    }
}
