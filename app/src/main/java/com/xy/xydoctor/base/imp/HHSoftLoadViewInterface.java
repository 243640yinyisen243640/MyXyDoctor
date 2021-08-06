package com.xy.xydoctor.base.imp;

import android.view.View;

import com.xy.xydoctor.base.model.HHSoftLoadViewConfig;


public interface HHSoftLoadViewInterface {
    void changeLoadState(HHSoftLoadStatus status);

    void changeLoadStateWithHint(HHSoftLoadStatus status, String hint);

    void setOnClickListener(HHSoftLoadStatus status, View.OnClickListener listener);

    void init(HHSoftLoadViewConfig config);
}
