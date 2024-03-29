package com.xy.xydoctor.adapter;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.BloodPressureBean;

import java.util.List;

public class BloodPressureListAdapter extends BaseQuickAdapter<BloodPressureBean, BaseViewHolder> {
    public BloodPressureListAdapter(@Nullable List<BloodPressureBean> data) {
        super(R.layout.item_blood_pressure_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BloodPressureBean item) {
        String datetime = item.getDatetime();
        helper.setText(R.id.tv_time, datetime);
        String value = item.getSystolic() + "/" + item.getDiastole();
        Log.i("yys", "heatRate==" + item.getHeartrate());
        String heartRate = item.getHeartrate() + "";
        helper.setText(R.id.tv_value_heart, heartRate);
        //1偏高  2偏低  3正常
        int ishight = item.getIshight();
        TextView tvValue = helper.getView(R.id.tv_value);
        switch (ishight) {
            case 1:
                tvValue.setTextColor(ColorUtils.getColor(R.color.bp_value_high));
                break;
            case 2:
                tvValue.setTextColor(ColorUtils.getColor(R.color.bp_value_normal));
                break;
            case 3:
                tvValue.setTextColor(ColorUtils.getColor(R.color.bp_value_low));
                break;
        }
        tvValue.setText(value);
        //1自动  2手动
        int type = item.getType();
        if (1 == type) {
            helper.setText(R.id.tv_type, "自动");
            helper.setImageResource(R.id.img_type, R.drawable.doctor_bp_auto_upload);
        } else {
            helper.setText(R.id.tv_type, "手动");
            helper.setImageResource(R.id.img_type, R.drawable.doctor_bp_manual_upload);
        }
    }
}
