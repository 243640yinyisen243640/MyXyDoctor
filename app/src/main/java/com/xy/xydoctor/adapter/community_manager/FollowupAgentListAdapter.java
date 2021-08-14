package com.xy.xydoctor.adapter.community_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpAgentListBean;

import java.util.List;


public class FollowupAgentListAdapter extends BaseQuickAdapter<FollowUpAgentListBean, BaseViewHolder> {

    public FollowupAgentListAdapter(@Nullable List<FollowUpAgentListBean> data) {
        super(R.layout.adapter_follow_up_agent, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, FollowUpAgentListBean bean) {
        String datetime = bean.getDatetime();
        holder.setText(R.id.tv_oxygen_time, datetime);
        String oxygen = bean.getOxygen();
        String bpmval = bean.getBpmval();
        holder.setText(R.id.tv_oxygen_number, oxygen + "" + "/" + bpmval);
        holder.setText(R.id.tv_tongyong, "%" + "/" + "bmp");
    }
}
