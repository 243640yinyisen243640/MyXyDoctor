package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/20 16:06
 * Description: 异常提醒
 */
public class DataAbnormalRemindAdapter extends UIBaseRecycleViewAdapter<SugarOrPressureInfo> {
    private int pos = 0;

    public DataAbnormalRemindAdapter(Context mContext, List<SugarOrPressureInfo> mList, String type, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_data_abnormal_remind, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        SugarOrPressureInfo sugarOrPressureInfo = getList().get(position);

        if (getList().size() == 1) {
            holder.bgLinerLayout.setBackgroundResource(R.drawable.shape_bg_white_5);
        } else {
            if (position == 0) {
                holder.bgLinerLayout.setBackgroundResource(R.drawable.shape_bg_white_5_5_0_0);
            } else if (getList().size() - 1 == position) {
                holder.bgLinerLayout.setBackgroundResource(R.drawable.shape_bg_white_0_0_5_5);
                holder.line.setVisibility(View.GONE);
            } else {
                holder.bgLinerLayout.setBackgroundResource(R.drawable.shape_bg_white_half_9);
            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeAndAmountTextView;
        TextView HighAndLowTextView;
        LinearLayout bgLinerLayout;
        View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgLinerLayout = itemView.findViewById(R.id.ll_data_abnormal_remind_bg);
            timeAndAmountTextView = itemView.findViewById(R.id.tv_data_abnormal_remind_time_and_amonut);
            HighAndLowTextView = itemView.findViewById(R.id.tv_data_abnormal_remind_high_or_low);
            line = itemView.findViewById(R.id.v_line);
        }
    }


}
