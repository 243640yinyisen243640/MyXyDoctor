package com.xy.xydoctor.adapter;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.BloodPressureBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class BloodPressureAdapter extends CommonAdapter<BloodPressureBean> {

    public BloodPressureAdapter(Context context, int layoutId, List<BloodPressureBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BloodPressureBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        //高压是收缩压，低压是舒张压
        //String pressure = String.format(Utils.getApp().getString(R.string.blood_pressure_left_right), item.getDiastole() + "", item.getSystolic() + "");
        String pressure = String.format(Utils.getApp().getString(R.string.blood_pressure_left_right), item.getSystolic() + "", item.getDiastole() + "");
        viewHolder.setText(R.id.tv_right_left, pressure);
        viewHolder.setText(R.id.tv_right_right, "mmHg");
    }
}
