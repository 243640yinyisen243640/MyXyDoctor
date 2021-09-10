package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.FollowListInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityBuildingModelListAdapter extends UIBaseRecycleViewAdapter<FollowListInfo> {


    public CommunityBuildingModelListAdapter(Context mContext, List<FollowListInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FollowListInfo info = getList().get(position);

        //        if (typeInfo.isCheck()) {
        //            holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_main_90));
        //        } else {
        //            holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_white_black_90));
        //        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_building_model_type, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private MyListView myListView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cb_filter_disease_type);

        }
    }


}

