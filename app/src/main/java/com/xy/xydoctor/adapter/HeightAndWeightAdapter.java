package com.xy.xydoctor.adapter;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.HeightAndWeightBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class HeightAndWeightAdapter extends CommonAdapter<HeightAndWeightBean> {
    public HeightAndWeightAdapter(Context context, int layoutId, List<HeightAndWeightBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, HeightAndWeightBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        //String weight = String.format(Utils.getApp().getString(R.string.weight), item.getWeight() + "");//姓名:电话
        String height_weight = String.format(Utils.getApp().getString(R.string.height_weight), item.getHeight() + "", item.getWeight() + "");//姓名:电话
        viewHolder.setText(R.id.tv_right_left, item.getBmi() + "");
        viewHolder.setText(R.id.tv_right_right, height_weight);
    }
}
