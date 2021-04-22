package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2020/1/20 14:54
 */
public class HomeSignSuccessPopup extends BasePopupWindow {
    public HomeSignSuccessPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_home_sign_success);
    }
}
