package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.UpLoadParamInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityBuildingModelListAdapter extends UIBaseRecycleViewAdapter<UpLoadParamInfo> {


    public CommunityBuildingModelListAdapter(Context mContext, List<UpLoadParamInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        UpLoadParamInfo info = getList().get(position);
        viewHolder.nameTextView.setText(info.getUnit_name());
        if (info.isCheck()) {
            viewHolder.nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.main_red));
            viewHolder.nameTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_main_90_low));
        } else {
            viewHolder.nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.base_black));
            viewHolder.nameTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_white_black_90));
        }

        viewHolder.nameLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_building_unit, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private LinearLayout nameLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_building_unit);
            nameLinearLayout = itemView.findViewById(R.id.ll_build_unit_click);
        }
    }


}

