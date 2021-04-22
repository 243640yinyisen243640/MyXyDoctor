package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.healthrecordadd.BloodOxygenAddActivity;
import com.xy.xydoctor.ui.activity.healthrecordadd.BloodPressureAddActivity;
import com.xy.xydoctor.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.xy.xydoctor.ui.activity.healthrecordadd.HeightAndWeightAddActivity;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/7/14 11:28
 */
public class OnlineTestPopup extends BasePopupWindow implements View.OnClickListener {
    private String userId;
    private Context context;

    public OnlineTestPopup(Context context, String userId) {
        super(context);
        this.context = context;
        this.userId = userId;
        setPopupGravity(Gravity.TOP);
        setBackground(0);
        LinearLayout llBloodSugar = findViewById(R.id.ll_blood_sugar);
        LinearLayout llBloodPressure = findViewById(R.id.ll_blood_pressure);
        LinearLayout llBloodOxygen = findViewById(R.id.ll_blood_oxygen);
        LinearLayout llHeightWeight = findViewById(R.id.ll_height_weight);
        llBloodSugar.setOnClickListener(this);
        llBloodPressure.setOnClickListener(this);
        llBloodOxygen.setOnClickListener(this);
        llHeightWeight.setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_online_test);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_blood_sugar://血糖
                intent = new Intent(context, BloodSugarAddActivity.class);
                intent.putExtra("userId", userId);
                break;
            case R.id.ll_blood_pressure://血压
                intent = new Intent(context, BloodPressureAddActivity.class);
                intent.putExtra("userId", userId);
                break;
            case R.id.ll_blood_oxygen://血氧
                intent = new Intent(context, BloodOxygenAddActivity.class);
                intent.putExtra("userId", userId);
                break;
            case R.id.ll_height_weight://身高体重
                intent = new Intent(context, HeightAndWeightAddActivity.class);
                intent.putExtra("userId", userId);
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
