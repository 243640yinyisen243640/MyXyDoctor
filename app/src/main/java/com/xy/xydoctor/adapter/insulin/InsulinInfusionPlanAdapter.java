package com.xy.xydoctor.adapter.insulin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.insulin.PlanInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinInfusionPlanAdapter extends RecyclerView.Adapter<InsulinInfusionPlanAdapter.ViewHolder> {
    private Context context;
    private List<PlanInfo> list;
    private String type;
    private IAdapterViewClickListener clickListener;

    public InsulinInfusionPlanAdapter(Context context, List<PlanInfo> list, String type, IAdapterViewClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public InsulinInfusionPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_insulin_infusion_plan, parent, false);
        return new InsulinInfusionPlanAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InsulinInfusionPlanAdapter.ViewHolder holder, int position) {
        holder.tvTime.setText(list.get(position).getAddtime());
        holder.tvName.setText(list.get(position).getValue());
        holder.llClick.setOnClickListener(v -> {
            clickListener.adapterClickListener(position, v);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvName;
        LinearLayout llClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_insulin_infusion_plan_time);
            tvName = itemView.findViewById(R.id.tv_insulin_infusion_plan_values);
            llClick = itemView.findViewById(R.id.ll_insulin_infusion_plan_click);
        }
    }

}
