package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.BaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;


public class FollowupAgentListAdapter extends BaseRecycleViewAdapter<FollowUpAgentListBean> {

    public FollowupAgentListAdapter(Context mContext, List<FollowUpAgentListBean> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(getContext(), R.layout.adapter_follow_up_agent, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView locationTextView;
        TextView buildingNumTextView;
        TextView unitNumTextView;
        TextView personNumTextView;
        TextView allPersonNumTextView;
        LinearLayout clickLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_fu_building_name);
            locationTextView = itemView.findViewById(R.id.tv_fu_building_location);
            buildingNumTextView = itemView.findViewById(R.id.tv_fu_building_num);
            unitNumTextView = itemView.findViewById(R.id.tv_fu_unit_num);
            personNumTextView = itemView.findViewById(R.id.tv_fu_person_num);
            allPersonNumTextView = itemView.findViewById(R.id.tv_fu_all_person_num);
            clickLinearLayout = itemView.findViewById(R.id.ll_fu_click);
        }

    }
}
