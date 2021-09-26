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
    private String type;

    public DataAbnormalRemindAdapter(Context mContext, List<SugarOrPressureInfo> mList, String type, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
        this.type = type;
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

        holder.timeTextView.setText(sugarOrPressureInfo.getDatetime());

        if ("1".equals(type)) {
            //1偏高2低3正常
            holder.eatTextView.setVisibility(View.GONE);
            holder.amountTextView.setText(sugarOrPressureInfo.getSystolic() + "/" + sugarOrPressureInfo.getDiastole());
            if ("1".equals(sugarOrPressureInfo.getIshight())) {
                holder.HighAndLowTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_hign, 0);
            } else if ("2".equals(sugarOrPressureInfo.getIshight())) {
                holder.HighAndLowTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_low, 0);
            } else {
                holder.HighAndLowTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_normal, 0);
            }
            holder.HighAndLowTextView.setText("mmHg");
        } else {
            holder.eatTextView.setVisibility(View.VISIBLE);
            //1偏高2低3正常
            holder.amountTextView.setText(sugarOrPressureInfo.getGlucosevalue());
            if ("1".equals(sugarOrPressureInfo.getIshight())) {
                holder.HighAndLowTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_hign, 0);
            } else if ("2".equals(sugarOrPressureInfo.getIshight())) {
                holder.HighAndLowTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_low, 0);
            } else {
                holder.HighAndLowTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_normal, 0);
            }
            holder.HighAndLowTextView.setText("mmol/L");
            holder.eatTextView.setText(sugarOrPressureInfo.getCategoryname());
        }

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
        TextView timeTextView;
        TextView amountTextView;
        TextView HighAndLowTextView;
        TextView eatTextView;
        LinearLayout bgLinerLayout;
        View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgLinerLayout = itemView.findViewById(R.id.ll_data_abnormal_remind_bg);
            timeTextView = itemView.findViewById(R.id.tv_data_abnormal_remind_time);
            amountTextView = itemView.findViewById(R.id.tv_data_abnormal_remind_amonut);
            HighAndLowTextView = itemView.findViewById(R.id.tv_data_abnormal_remind_high_or_low);
            eatTextView = itemView.findViewById(R.id.tv_data_abnormal_remind_eat);
            line = itemView.findViewById(R.id.v_line);
        }
    }


}
