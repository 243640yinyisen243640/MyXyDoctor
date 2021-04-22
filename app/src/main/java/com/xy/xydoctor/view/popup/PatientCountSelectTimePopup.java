package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.View;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  kkk
 * 作者: LYD
 * 创建日期: 2019/9/28 16:20
 */
public class PatientCountSelectTimePopup extends BasePopupWindow {
    public PatientCountSelectTimePopup(Context context) {
        super(context);
        setBackground(0);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_patient_count_select_time);
    }
}
