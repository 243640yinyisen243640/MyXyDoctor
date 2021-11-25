package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.customerView.NoConflictListView;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class UserDateiDataListAdapter extends UIBaseRecycleViewAdapter<DataAbnormalInfo> {
    public UserDateiDataListAdapter(Context mContext, List<DataAbnormalInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_user_datei_data, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataAbnormalInfo info = getList().get(position);
        viewHolder.locationTextView.setText(info.getCom_name());
        UserDateiDataChildListAdapter adapter = new UserDateiDataChildListAdapter(getContext(), info.getMembers());
        viewHolder.myListView.setAdapter(adapter);

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationTextView;
        NoConflictListView myListView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTextView = itemView.findViewById(R.id.tv_datei_location);
            myListView = itemView.findViewById(R.id.ls_datei_location);

        }
    }


}
