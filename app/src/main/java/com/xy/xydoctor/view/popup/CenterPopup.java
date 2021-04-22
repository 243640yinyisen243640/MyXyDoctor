package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

public class CenterPopup extends BasePopupWindow {

    public CenterPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_center);
    }
}
