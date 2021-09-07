package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.List;

/**
 * 描述: 筛选列表
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityFilterListAdapter extends UIBaseRecycleViewAdapter<CommunityFilterInfo> {
    private String type;


    private UserMedicineChildListAdapter adapter;

    public CommunityFilterListAdapter(Context mContext, List<CommunityFilterInfo> mList, String type, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
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
        CommunityFilterInfo info = getList().get(position);


        if (getListener() != null) {
            viewHolder.clickLinearLayout.setOnClickListener(v -> {
                getListener().adapterClickListener(position, v);
            });
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView locationTextView;
        MyListView myListView;
        LinearLayout clickLinearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_um_name);
            locationTextView = itemView.findViewById(R.id.tv_um_location);
            myListView = itemView.findViewById(R.id.lv_user_medicine_child);
            clickLinearLayout = itemView.findViewById(R.id.ll_use_medicine_remind_click);

        }
    }


    /**
     * 二级列表
     *
     * @return
     */
    public CommonAdapter returnAdapter() {
        return adapter;
    }

}
