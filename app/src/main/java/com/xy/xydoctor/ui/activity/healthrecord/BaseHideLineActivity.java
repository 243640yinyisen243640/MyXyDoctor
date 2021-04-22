package com.xy.xydoctor.ui.activity.healthrecord;

import android.os.Bundle;

import com.xy.xydoctor.base.activity.BaseActivity;

public abstract class BaseHideLineActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideLine();
    }
}
