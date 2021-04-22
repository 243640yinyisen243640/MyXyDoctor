package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  选择家庭组
 * 作者: LYD
 * 创建日期: 2020/1/14 9:13
 */
public class FamilySelectUserPopup extends BasePopupWindow {
    public FamilySelectUserPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_family_select_user);
    }
}
