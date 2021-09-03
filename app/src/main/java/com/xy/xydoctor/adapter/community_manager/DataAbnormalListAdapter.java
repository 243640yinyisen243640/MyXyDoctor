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
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.List;

/**
 * 描述: 数据异常·
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class DataAbnormalListAdapter extends UIBaseRecycleViewAdapter<DataAbnormalInfo> {
    private String type;

    private DataAbnormalChildVisitListAdapter childVisitListAdapter;


    public DataAbnormalListAdapter(Context mContext, List<DataAbnormalInfo> mList, String type, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
        this.type = type;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_data_abnormal_list, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataAbnormalInfo info = getList().get(position);

        viewHolder.nameTextView.setText(info.getCom_name());

        if (info.isSelected()) {
            viewHolder.checkTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_special_operate_check, 0, 0, 0);
        } else {
            viewHolder.checkTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_special_operate_uncheck, 0, 0, 0);
        }


        if (info.getCommunityUser() == null || info.getCommunityUser().size() == 0) {
            viewHolder.myListView.setVisibility(View.GONE);
        } else {
            viewHolder.myListView.setVisibility(View.VISIBLE);
        }
        if (info.isCheck()) {
            viewHolder.checkTextView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checkTextView.setVisibility(View.GONE);
            childVisitListAdapter = new DataAbnormalChildVisitListAdapter(getContext(), R.layout.item_data_abnormal_child_list, info.getCommunityUser(), type, position, getListener());

            viewHolder.myListView.setAdapter(childVisitListAdapter);
        }


        if (getListener() != null) {
            viewHolder.checkTextView.setOnClickListener(v -> {
                getListener().adapterClickListener(position, v);
            });

        }


    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView checkTextView;
        private TextView nameTextView;
        private MyListView myListView;
        private LinearLayout clickLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            checkTextView = itemView.findViewById(R.id.tv_data_abnormal_check);
            nameTextView = itemView.findViewById(R.id.tv_data_abnormal_name);
            myListView = itemView.findViewById(R.id.lv_data_abnormal_child);
            clickLinearLayout = itemView.findViewById(R.id.ll_data_abnormal_click);
        }
    }


    public CommonAdapter adapter() {
        return childVisitListAdapter;
    }

}
