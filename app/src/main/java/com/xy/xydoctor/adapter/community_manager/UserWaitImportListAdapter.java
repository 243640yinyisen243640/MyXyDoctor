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
import com.xy.xydoctor.bean.community_manamer.SearchInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class UserWaitImportListAdapter extends UIBaseRecycleViewAdapter<SearchInfo> {
    public UserWaitImportListAdapter(Context mContext, List<SearchInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_user_wait_import_data, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SearchInfo info = getList().get(position);
        viewHolder.nameTextView.setText(info.getNickname());
        if ("1".equals(info.getSex())) {
            viewHolder.sexTextView.setText(getContext().getString(R.string.base_male));
        } else {
            viewHolder.sexTextView.setText(getContext().getString(R.string.base_female));
        }

        viewHolder.ageTextView.setText(info.getAge() + "岁");
        viewHolder.locationTextView.setText(info.getAddress());

        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });


    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout clickLinearLayout;
        TextView nameTextView;
        TextView sexTextView;
        TextView ageTextView;
        TextView locationTextView;
        TextView addTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clickLinearLayout = itemView.findViewById(R.id.ll_wi_click);
            locationTextView = itemView.findViewById(R.id.tv_community_wi_location);
            nameTextView = itemView.findViewById(R.id.tv_community_wi_name);
            sexTextView = itemView.findViewById(R.id.tv_community_wi_sex);
            ageTextView = itemView.findViewById(R.id.tv_community_wi_age);
            addTextView = itemView.findViewById(R.id.tv_community_wi_add);

        }
    }


}
