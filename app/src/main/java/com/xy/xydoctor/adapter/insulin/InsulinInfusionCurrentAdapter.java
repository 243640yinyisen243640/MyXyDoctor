package com.xy.xydoctor.adapter.insulin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.insulin.PlanInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.LoadImgUtils;

import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinInfusionCurrentAdapter extends RecyclerView.Adapter<InsulinInfusionCurrentAdapter.ViewHolder> {
    private Context context;
    private List<PlanInfo> list;
    private String type;
    private IAdapterViewClickListener clickListener;

    public InsulinInfusionCurrentAdapter(Context context, List<PlanInfo> list, String type, IAdapterViewClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public InsulinInfusionCurrentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_insulin_infusion_current, parent, false);
        return new InsulinInfusionCurrentAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InsulinInfusionCurrentAdapter.ViewHolder holder, int position) {
        LoadImgUtils.loadImage(context, R.drawable.head_default, list.get(position).getPicture(), holder.ivHead);
        holder.tvName.setText(list.get(position).getNickname());
        holder.tvTime.setText(list.get(position).getAddtime());
        holder.tvTel.setText(list.get(position).getTel());
        holder.tvTel.setOnClickListener(v -> {
            clickListener.adapterClickListener(position, v);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivHead;
        TextView tvName;
        TextView tvTime;
        TextView tvTel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_insulin_infusion_current_head);
            tvName = itemView.findViewById(R.id.tv_insulin_infusion_current_name);
            tvTime = itemView.findViewById(R.id.tv_insulin_infusion_current_time);
            tvTel = itemView.findViewById(R.id.tv_insulin_infusion_current_phone);
        }
    }

}
