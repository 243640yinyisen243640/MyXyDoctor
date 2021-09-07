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
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 数用药提醒
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class UserMedicineListAdapter extends UIBaseRecycleViewAdapter<CommunityUseMedicineInfo> {
    private IAdapterViewClickListener clickListener;
    private String type;

    public UserMedicineListAdapter(Context mContext, List<CommunityUseMedicineInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_user_medicine_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CommunityUseMedicineInfo info = getList().get(position);
        viewHolder.nameTextView.setText(info.getCom_name());
        viewHolder.locationTextView.setText(info.getCom_address());

        UserMedicineChildListAdapter adapter = new UserMedicineChildListAdapter(getContext(), R.layout.item_user_medicine_child_list, info.getPharmacys(), "", position, clickListener);
        viewHolder.myListView.setAdapter(adapter);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView locationTextView;
        MyListView myListView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_um_name);
            locationTextView = itemView.findViewById(R.id.tv_um_location);
            myListView = itemView.findViewById(R.id.lv_user_medicine_child);

        }
    }


    private class OnAdapterItemClickListener implements View.OnClickListener {
        private int position;

        public OnAdapterItemClickListener(int position) {
            this.position = position;

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.adapterClickListener(position, v);
            }
        }
    }

}
