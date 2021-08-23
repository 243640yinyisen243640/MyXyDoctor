package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.GoodsClassInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/23 17:30
 * Description: 楼层模型的几号楼
 */
public class ClassTopListAdapter extends RecyclerView.Adapter<ClassTopListAdapter.ViewHolder> {
    private Context context;
    private List<GoodsClassInfo> list;
    private IAdapterViewClickListener clickListener;
    private int pos = 0;

    public ClassTopListAdapter(Context context, List<GoodsClassInfo> list, IAdapterViewClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(context, R.layout.item_class_list_top, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsClassInfo info = list.get(position);
        if (position == pos) {
            holder.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,0, R.drawable.shape_bg_red);
        } else {
            holder.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,0, R.drawable.shape_bg_transparent);
        }

        holder.nameTextView.setText(info.getTitle());
        holder.allLinearLayout.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.adapterClickListener(position, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        LinearLayout allLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_top_class_name);
            allLinearLayout = itemView.findViewById(R.id.ll_top_class);

        }
    }
    public void setClickPosition(int position) {
        this.pos = position;
        this.notifyDataSetChanged();
    }

    public int getClickPosition() {
        return pos;
    }

}

