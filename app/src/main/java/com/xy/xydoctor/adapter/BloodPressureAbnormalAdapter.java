package com.xy.xydoctor.adapter;

import android.content.Context;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.BloodPressureAbnormalBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class BloodPressureAbnormalAdapter extends CommonAdapter<BloodPressureAbnormalBean.SevenpressureBean> {
    public BloodPressureAbnormalAdapter(Context context, int layoutId, List<BloodPressureAbnormalBean.SevenpressureBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BloodPressureAbnormalBean.SevenpressureBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_center, item.getSystolic() + "");
        viewHolder.setText(R.id.tv_right, item.getDiastole() + "");
    }
}
