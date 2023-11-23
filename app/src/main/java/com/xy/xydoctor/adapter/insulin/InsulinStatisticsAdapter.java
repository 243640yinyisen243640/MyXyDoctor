package com.xy.xydoctor.adapter.insulin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.insulin.InsulinDeviceInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 胰岛素泵统计
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class InsulinStatisticsAdapter extends UIBaseRecycleViewAdapter<InsulinDeviceInfo> {
    public InsulinStatisticsAdapter(Context mContext, List<InsulinDeviceInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_insulin_statistics, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        InsulinDeviceInfo info = getList().get(position);


        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPone;
        private TextView tvNum;
        private LinearLayout clickLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_insulin_statistics_head);
            tvName = itemView.findViewById(R.id.tv_insulin_statistics_name);
            tvPone = itemView.findViewById(R.id.tv_insulin_statistics_phone);
            tvNum = itemView.findViewById(R.id.tv_insulin_statistics_num);
            clickLinearLayout = itemView.findViewById(R.id.ll_insulin_statistics_click);

        }
    }


}

