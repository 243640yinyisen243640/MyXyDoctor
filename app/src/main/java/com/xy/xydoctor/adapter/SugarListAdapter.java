package com.xy.xydoctor.adapter;

import android.content.Context;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.SugarListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class SugarListAdapter extends CommonAdapter<SugarListBean> {

    private static final String TAG = "SugarListAdapter";

    public SugarListAdapter(Context context, int layoutId, List<SugarListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SugarListBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_center, item.getGlucosevalue() + "");
        viewHolder.setText(R.id.tv_right, item.getType());
    }
}
