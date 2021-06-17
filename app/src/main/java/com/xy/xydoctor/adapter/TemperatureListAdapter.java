package com.xy.xydoctor.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.TemperatureDataBean;

import java.util.List;

public class TemperatureListAdapter extends BaseQuickAdapter<TemperatureDataBean.TemperatureDataListBean, BaseViewHolder> {
    public TemperatureListAdapter(@Nullable List<TemperatureDataBean.TemperatureDataListBean> data) {
        super(R.layout.item_temperature_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TemperatureDataBean.TemperatureDataListBean item) {
        String datetime = item.getDatetime();
        helper.setText(R.id.tv_time, datetime);
        if ("1".equals(item.getType())) {
            helper.setText(R.id.tv_type_temperature, R.string.up_temperature_automatic);
        } else {
            helper.setText(R.id.tv_type_temperature, R.string.up_temperature_manualc);
        }
        helper.setText(R.id.tv_value_temperature, item.getTemperature());
    }
}
