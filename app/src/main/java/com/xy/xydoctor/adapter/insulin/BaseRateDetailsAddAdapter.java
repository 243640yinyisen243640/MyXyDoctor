package com.xy.xydoctor.adapter.insulin;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.util.edittext.EditTextUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.insulin.PlanAddInfo;

import java.util.List;


/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class BaseRateDetailsAddAdapter extends RecyclerView.Adapter<BaseRateDetailsAddAdapter.ViewHolder> {
    private Context context;
    private List<PlanAddInfo> listInfos;

    public BaseRateDetailsAddAdapter(Context context, List<PlanAddInfo> listInfos) {
        this.context = context;
        this.listInfos = listInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_base_rate_details_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.tvStarTime.setText(listInfos.get(position).getBegin());
        holder.tvEndTime.setText(listInfos.get(position).getEnd());
        holder.etRate.setText(listInfos.get(position).getValue());
        holder.etRate.setTag(position);
        EditTextUtils.decimalNumber(holder.etRate, 1, 4);
        holder.etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = (int) holder.etRate.getTag();
                if (TextUtils.isEmpty(s.toString())){
                    listInfos.get(pos).setValue("0");
                    Log.i("yys", "pos=="+pos + "==value=="+listInfos.get(pos).getValue());
                    return;
                }
                double value = Double.parseDouble(s.toString());
                if (value > 99) {
                    value = 99;
                    holder.etRate.setText(value + "");
                }
                listInfos.get(pos).setValue(value + "");
                Log.i("yys", "pos==pos=="+pos + "==value=="+listInfos.get(pos).getValue());
            }
        });
    }


    @Override
    public int getItemCount() {
        return listInfos.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStarTime;
        TextView tvEndTime;
        EditText etRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStarTime = itemView.findViewById(R.id.tv_infusion_base_rate_star_time_item_add);
            tvEndTime = itemView.findViewById(R.id.tv_infusion_base_rate_end_time_item_add);
            etRate = itemView.findViewById(R.id.et_plan_add_base_rate_hour_add);
        }
    }
}
