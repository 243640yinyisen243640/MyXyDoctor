package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class CommunitySugarPressureChildListAdapter extends CommonAdapter<FollowUpAgentListBean> {

    private String type;
    private IAdapterViewClickListener clickListener;
    private int parentPosi;

    public CommunitySugarPressureChildListAdapter(Context context, int layoutId, List<FollowUpAgentListBean> datas, String type, int parentPosi, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.clickListener = clickListener;
        this.parentPosi = parentPosi;
    }


    @Override
    protected void convert(ViewHolder viewHolder, FollowUpAgentListBean item, int position) {
        TextView titleTextView = viewHolder.getView(R.id.fu_sugar_pressure_title_child);
        TextView timeTextView = viewHolder.getView(R.id.fu_sugar_pressure_time_child);
        TextView stateTextView = viewHolder.getView(R.id.fu_sugar_pressure_state_child);
    }


}
