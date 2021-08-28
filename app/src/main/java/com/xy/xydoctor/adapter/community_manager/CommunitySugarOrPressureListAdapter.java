package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 血糖随访血压随访
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunitySugarOrPressureListAdapter extends RecyclerView.Adapter<CommunitySugarOrPressureListAdapter.ViewHolder> {
    private Context context;
    private List<FollowUpAgentListBean> list;
    private IAdapterViewClickListener clickListener;

    private String type;


    public CommunitySugarOrPressureListAdapter(Context context, List<FollowUpAgentListBean> list, String type, IAdapterViewClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(context, R.layout.item_community_sugar_or_pressure, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FollowUpAgentListBean info = list.get(position);


        CommunitySugarPressureChildListAdapter childListAdapter = new CommunitySugarPressureChildListAdapter(context, R.layout.item_community_sugar_or_pressure_child, list.get(position).getPlan_list(), type, position, clickListener);
        holder.myListView.setAdapter(childListAdapter);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView;
        private MyListView myListView;

        public ViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.fu_sugar_pressure_time);
            myListView = itemView.findViewById(R.id.lv_fu_sugar_pressure_child);

        }
    }


}

