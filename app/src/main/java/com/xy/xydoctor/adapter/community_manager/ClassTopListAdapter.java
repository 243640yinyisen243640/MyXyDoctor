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
import com.xy.xydoctor.bean.community_manamer.FollowListInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/23 17:30
 * Description: 楼层模型的几号楼
 */
public class ClassTopListAdapter extends UIBaseRecycleViewAdapter<FollowListInfo> {
    private int pos = 0;

    public ClassTopListAdapter(Context mContext, List<FollowListInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_class_list_top, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FollowListInfo info = getList().get(position);

        viewHolder.nameTextView.setText(info.getBuild_name());
        if (info.isCheck()) {
            viewHolder.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.shape_bg_red);
            viewHolder.nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.main_red));
        } else {
            viewHolder.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.shape_bg_transparent);
            viewHolder.nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.base_black));

        }


        viewHolder.allLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
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

