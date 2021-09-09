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
 * 描述: 楼栋模型点击切换列表
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityFollowUpChangeListAdapter extends UIBaseRecycleViewAdapter<FollowListInfo> {

    private String type;

    public CommunityFollowUpChangeListAdapter(Context mContext, List<FollowListInfo> mList, String type, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_community_follow_up_change, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FollowListInfo info = getList().get(position);

        viewHolder.nameTextView.setText(info.getBuild_name() + "号楼" + info.getUnit_name() + "单元");
        CommunityFollowUpChangeChildListAdapter childListAdapter = new CommunityFollowUpChangeChildListAdapter(getContext(), info.getCommunityUser(), getListener());
        viewHolder.myListView.setAdapter(childListAdapter);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private MyListView myListView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_fuc_name);
            myListView = itemView.findViewById(R.id.lv_fuc_child);

        }
    }


}

