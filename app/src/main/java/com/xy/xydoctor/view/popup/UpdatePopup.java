package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  更新弹窗
 * 作者: LYD
 * 创建日期: 2019/10/9 16:31
 */
public class UpdatePopup extends BasePopupWindow {
    public UpdatePopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.dialog_update);
    }
}
