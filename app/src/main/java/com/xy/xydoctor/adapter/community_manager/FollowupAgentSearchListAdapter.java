package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;


public class FollowupAgentSearchListAdapter extends UIBaseRecycleViewAdapter<FollowUpAgentListBean> {


    public FollowupAgentSearchListAdapter(Context mContext, List<FollowUpAgentListBean> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_follow_up_agent_search, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FollowUpAgentListBean info = getList().get(position);

        viewHolder.nameTextView.setText(info.getNickname());
        if ("1".equals(info.getSex())) {
            viewHolder.sexTextView.setText(R.string.base_male);
        } else {
            viewHolder.sexTextView.setText(R.string.base_female);
        }
        viewHolder.ageTextView.setText(info.getAge() + "岁");
        viewHolder.phoneTextView.setText(info.getTel());
        viewHolder.locationTextView.setText(info.getCom_name() + info.getBuild_name() + info.getUnit_name() + info.getHouse_num());
        viewHolder.detailsTextView.setText(info.getCom_address());
        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView sexTextView;
        TextView ageTextView;
        TextView phoneTextView;
        TextView locationTextView;
        TextView detailsTextView;
        LinearLayout clickLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_fu_search_name);
            sexTextView = itemView.findViewById(R.id.tv_fu_search_sex);
            ageTextView = itemView.findViewById(R.id.tv_fu_search_age);
            phoneTextView = itemView.findViewById(R.id.tv_fu_search_phone);
            locationTextView = itemView.findViewById(R.id.tv_fu_search_location);
            detailsTextView = itemView.findViewById(R.id.tv_fu_location_search_details);
            clickLinearLayout = itemView.findViewById(R.id.ll_fu_search_click);
        }
    }
}
