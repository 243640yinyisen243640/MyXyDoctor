package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;

import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class FollowupAgentSearchListAdapter extends CommonAdapter<FollowUpAgentListBean> {


    public FollowupAgentSearchListAdapter(Context context, int layoutId, List<FollowUpAgentListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpAgentListBean item, int position) {

    }
}
