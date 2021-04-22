package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  快速签约
 * 作者: LYD
 * 创建日期: 2020/1/13 10:32
 */
public class HomeSignQuickPopup extends BasePopupWindow {
    public HomeSignQuickPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        ImageView imgDismiss = findViewById(R.id.img_dismiss);
        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_home_sign_quick);
    }
}
