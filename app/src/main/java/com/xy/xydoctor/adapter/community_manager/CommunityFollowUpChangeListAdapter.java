package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 楼栋模型点击切换列表
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityFollowUpChangeListAdapter extends RecyclerView.Adapter<CommunityFollowUpChangeListAdapter.ViewHolder> {
    private Context context;
    private List<FollowUpAgentListBean> list;
    private IAdapterViewClickListener clickListener;

    private String type;


    public CommunityFollowUpChangeListAdapter(Context context, List<FollowUpAgentListBean> list, String type, IAdapterViewClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(context, R.layout.item_community_follow_up_change, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FollowUpAgentListBean info = list.get(position);


        CommunityFollowUpChangeChildListAdapter childListAdapter = new CommunityFollowUpChangeChildListAdapter(context, R.layout.item_community_follow_up_change_child_list, list.get(position).getPlan_list(), type, position, clickListener);
        holder.myListView.setAdapter(childListAdapter);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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

