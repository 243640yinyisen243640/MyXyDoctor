package com.xy.xydoctor.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpVisitAddRvBean;

import java.util.HashMap;
import java.util.List;

public class FollowUpVisitAddRvAdapter extends BaseQuickAdapter<FollowUpVisitAddRvBean, BaseViewHolder> {

    public HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private List<FollowUpVisitAddRvBean> datas;
    private com.xy.xydoctor.imp.OnItemClickListener mOnItemClickListener;

    public FollowUpVisitAddRvAdapter(@Nullable List<FollowUpVisitAddRvBean> datas) {
        super(R.layout.item_follow_up_visit_add, datas);
        this.datas = datas;
        init();
    }


    private void init() {
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    public void setOnItemClickListener(com.xy.xydoctor.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, FollowUpVisitAddRvBean followUpVisitAddRvBean) {
        ImageView imgCheck = holder.getView(R.id.img_check);
        ImageView imgPic = holder.getView(R.id.img_pic);
        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvDesc = holder.getView(R.id.tv_desc);
        int position = holder.getLayoutPosition();
        Boolean check = isSelected.get(position);
        if (check) {
            imgCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check_ed);
        } else {
            imgCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check);
        }
        tvTitle.setText(datas.get(position).getTitle());
        tvDesc.setText(datas.get(position).getDesc());
        imgPic.setImageResource(datas.get(position).getImg());
        //选中改变的监听
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });
        }
    }
}
