package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterChildInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 筛选列表
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityFilterListAdapter extends UIBaseRecycleViewAdapter<CommunityFilterChildInfo> {
    private String isEmpty;


    public CommunityFilterListAdapter(Context mContext, List<CommunityFilterChildInfo> mList, String isEmpty, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
        this.isEmpty = isEmpty;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_filter_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CommunityFilterChildInfo info = getList().get(position);


        if ("1".equals(isEmpty)) {
            viewHolder.noFrameLayout.setVisibility(View.VISIBLE);
            viewHolder.emptyTextView.setVisibility(View.GONE);
        } else {
            viewHolder.noFrameLayout.setVisibility(View.GONE);
            viewHolder.emptyTextView.setVisibility(View.VISIBLE);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout noFrameLayout;
        TextView nameTextView;
        MyListView myListView;
        TextView sexTextView;
        TextView ageTextView;
        TextView telTextView;
        FlexboxLayout typeFlexboxLayout;
        TextView locationTextView;
        ImageView deadImageView;
        TextView emptyTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noFrameLayout = itemView.findViewById(R.id.fl_filter_have);
            nameTextView = itemView.findViewById(R.id.tv_filter_name);
            myListView = itemView.findViewById(R.id.ml_filter_img);
            sexTextView = itemView.findViewById(R.id.tv_filter_sex);
            ageTextView = itemView.findViewById(R.id.tv_filter_age);
            telTextView = itemView.findViewById(R.id.tv_filter_tel);
            typeFlexboxLayout = itemView.findViewById(R.id.flex_fsiui_desease_type);
            locationTextView = itemView.findViewById(R.id.tv_filter_location);
            deadImageView = itemView.findViewById(R.id.iv_filter_have_dead);
            emptyTextView = itemView.findViewById(R.id.tv_filter_empty);

        }
    }

}
