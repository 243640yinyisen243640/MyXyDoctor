package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import com.blankj.utilcode.util.ConvertUtils;
import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述: 数据异常的筛选
 * 作者: LYD
 * 创建日期: 2019/7/14 11:25
 */
public class DataAbnormalPopup extends BasePopupWindow {

    public DataAbnormalPopup(Context context, String userId) {
        super(context);
        setBackPressEnable(true);
        setAlignBackground(true);

    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.data_abnormal_popwindow);
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
