package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.HealthRecordGvAdapter;

import java.util.ArrayList;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述: 健康记录 下拉八个
 * 作者: LYD
 * 创建日期: 2019/7/14 11:25
 */
public class SlideFromTopPopup extends BasePopupWindow {

    public SlideFromTopPopup(Context context, String userId) {
        super(context);
        setBackPressEnable(true);
        setAlignBackground(true);
        GridView gvHealthRecord = findViewById(R.id.gv_health_record);
        Button btDismiss = findViewById(R.id.bt_dismiss);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + "");
        }
        HealthRecordGvAdapter adapter = new HealthRecordGvAdapter(Utils.getApp(), R.layout.item_gv_health_record, list, userId);
        gvHealthRecord.setAdapter(adapter);
        //消失
        btDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_from_top);
    }


    @Override
    protected Animation onCreateShowAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, -ConvertUtils.dp2px(350f), 0);
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(1));
        return translateAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0, -ConvertUtils.dp2px(350f));
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(-4));
        return translateAnimation;
    }

}
