package com.xy.xydoctor.adapter.insulin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.insulin.PlanInfo;

import java.util.List;


/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class BaseRateDetailsAddAdapter extends RecyclerView.Adapter<BaseRateDetailsAddAdapter.ViewHolder> {
    private Context context;
    private List<PlanInfo> listInfos;

    public BaseRateDetailsAddAdapter(Context context, List<PlanInfo> listInfos) {
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
        holder.tvStarTime.setText(listInfos.get(position).getAddtime());
        holder.tvStarTime.setText(listInfos.get(position).getAddtime());
        holder.tvStarTime.setText(listInfos.get(position).getAddtime());
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
