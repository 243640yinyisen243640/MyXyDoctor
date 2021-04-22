package com.xy.xydoctor.adapter;

import android.content.Context;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.WeightListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class WeightListAdapter extends CommonAdapter<WeightListBean> {


    public WeightListAdapter(Context context, int layoutId, List<WeightListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, WeightListBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_right, item.getWeight() + "kg");
    }
}
