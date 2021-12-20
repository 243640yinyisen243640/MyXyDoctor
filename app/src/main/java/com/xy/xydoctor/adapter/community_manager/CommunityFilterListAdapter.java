package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.flexbox.FlexboxLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterChildInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.List;

/**
 * 描述: 筛选列表
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityFilterListAdapter extends UIBaseRecycleViewAdapter<CommunityFilterChildInfo> {
    /**
     * 0：空房间
     * 1：不是空房间
     */
    private String isEmpty;


    public CommunityFilterListAdapter(Context mContext, List<CommunityFilterChildInfo> mList, String isEmpty, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
        this.isEmpty = isEmpty;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_filter_list, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CommunityFilterChildInfo info = getList().get(position);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        viewHolder.myListView.setLayoutManager(layoutManager);

        if ("1".equals(isEmpty)) {
            viewHolder.noFrameLayout.setVisibility(View.VISIBLE);
            viewHolder.emptyTextView.setVisibility(View.GONE);
            viewHolder.typeFlexboxLayout.removeAllViews();
            if (info.getDiseases() != null && info.getDiseases().size() > 0) {
                viewHolder.typeFlexboxLayout.setVisibility(View.VISIBLE);

                int padding = XyScreenUtils.dip2px(getContext(), 5);
                FlexboxLayout.LayoutParams flexLP = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, XyScreenUtils.dip2px(getContext(), 20));
                flexLP.setMargins(0, padding * 2, padding * 2, 0);
                for (int i = 0; i < info.getDiseases().size(); i++) {
                    TextView textView = new TextView(getContext());
                    textView.setTextSize(12);
                    textView.setPadding(padding * 2, 0, padding * 2, 0);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.community_disease_type));
                    textView.setBackgroundResource(R.drawable.shape_bg_blue__light_2);
                    textView.setText(info.getDiseases().get(i));
                    viewHolder.typeFlexboxLayout.addView(textView, flexLP);
                }
            } else {
                viewHolder.typeFlexboxLayout.setVisibility(View.GONE);
            }

            /**
             * 是否死亡
             *      * 1:是
             *      * 2:否
             */
            if ("1".equals(info.getIsdeath())) {
                viewHolder.deadImageView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.deadImageView.setVisibility(View.GONE);
            }
            viewHolder.nameTextView.setText(info.getNickname());
            viewHolder.sexTextView.setText(info.getSex());
            viewHolder.telTextView.setText(info.getTel());
            viewHolder.ageTextView.setText(info.getAge() + "岁");
            viewHolder.locationTextView.setText(info.getCom_name() + info.getBuild_name() + info.getUnit_name() + info.getHouse_num());
        } else {
            viewHolder.noFrameLayout.setVisibility(View.GONE);
            viewHolder.emptyTextView.setVisibility(View.VISIBLE);
            viewHolder.emptyTextView.setText(info.getCom_name() + info.getBuild_name() + info.getUnit_name() + info.getHouse_num());
        }

        CommunityFilterDeseaseImgAdapter imgAdapter = new CommunityFilterDeseaseImgAdapter(getContext(), info.getImgs(), new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {

            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        viewHolder.myListView.setAdapter(imgAdapter);

        viewHolder.emptyTextView.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });

        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });


    }


    class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout noFrameLayout;
        TextView nameTextView;
        RecyclerView myListView;
        TextView sexTextView;
        TextView ageTextView;
        TextView telTextView;
        FlexboxLayout typeFlexboxLayout;
        TextView locationTextView;
        ImageView deadImageView;
        TextView emptyTextView;
        LinearLayout clickLinearLayout;


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
            clickLinearLayout = itemView.findViewById(R.id.ll_filter_click);

        }
    }

}
