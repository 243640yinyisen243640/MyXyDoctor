package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.xydoctor.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  医生签约选择类型
 * 作者: LYD
 * 创建日期: 2020/1/13 9:59
 */
public class HomeSignSelectTypePopup extends BasePopupWindow {
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    private OnTypeSelectListener onTypeSelectListener;

    public HomeSignSelectTypePopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        ImageView imgDismiss = findViewById(R.id.img_dismiss);
        tvTitle = findViewById(R.id.tv_title);
        tvLeft = findViewById(R.id.tv_left);
        tvRight = findViewById(R.id.tv_right);
        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTypeSelectListener.OnLeftSelected(v);
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTypeSelectListener.OnRightSelected(v);
            }
        });
    }

    public void setOnTypeSelectListener(OnTypeSelectListener onTypeSelectListener) {
        this.onTypeSelectListener = onTypeSelectListener;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_home_sign_select_type);
    }

    public void setText(String title, String left, String right) {
        tvTitle.setText(title);
        tvLeft.setText(left);
        tvRight.setText(right);
    }

    public void setTextColor(int title, int left, int right) {
        tvTitle.setTextColor(title);
        tvLeft.setTextColor(left);
        tvRight.setTextColor(right);
    }

    public interface OnTypeSelectListener {
        void OnLeftSelected(View v);

        void OnRightSelected(View v);
    }

}
