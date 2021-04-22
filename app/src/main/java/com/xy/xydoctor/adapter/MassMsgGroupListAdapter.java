package com.xy.xydoctor.adapter;

import android.content.Context;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.GroupListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MassMsgGroupListAdapter extends CommonAdapter<GroupListBean> {
    public MassMsgGroupListAdapter(Context context, int layoutId, List<GroupListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GroupListBean item, int position) {
        viewHolder.setText(R.id.tv_group_name, item.getGname());
    }
}
