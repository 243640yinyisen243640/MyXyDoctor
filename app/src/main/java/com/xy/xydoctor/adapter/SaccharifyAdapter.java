package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.SaccharifyBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class SaccharifyAdapter extends CommonAdapter<SaccharifyBean> {
    public SaccharifyAdapter(Context context, int layoutId, List<SaccharifyBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SaccharifyBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_right_left, item.getDiastaticvalue() + "%");
        viewHolder.getView(R.id.tv_right_right).setVisibility(View.GONE);
    }
}
