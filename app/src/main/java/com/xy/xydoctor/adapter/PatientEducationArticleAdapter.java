package com.xy.xydoctor.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.PatientEducationArticleListBean;

import java.util.List;

public class PatientEducationArticleAdapter extends BaseQuickAdapter<PatientEducationArticleListBean.DataBean, BaseViewHolder> {
    //默认选中位置
    private int selected = -1;
    private com.xy.xydoctor.imp.OnItemClickListener mOnItemClickListener;

    public PatientEducationArticleAdapter(@Nullable List<PatientEducationArticleListBean.DataBean> data) {
        super(R.layout.item_patient_education_article, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, PatientEducationArticleListBean.DataBean item) {
        int position = holder.getLayoutPosition();
        int type = item.getType();
        switch (type) {
            case 1:
                holder.setImageResource(R.id.img_type, R.drawable.patient_education_video);
                break;
            case 2:
                holder.setImageResource(R.id.img_type, R.drawable.patient_education_music);
                break;
            case 3:
                holder.setImageResource(R.id.img_type, R.drawable.patient_education_text);
                break;
        }
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_sub_title, item.getSubtitle());
        //单选处理
        ImageView imgCheck = holder.getView(R.id.img_check);
        if (selected == position) {
            imgCheck.setImageResource(R.drawable.apply_to_hospital_checked);
        } else {
            imgCheck.setImageResource(R.drawable.apply_to_hospital_uncheck);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }


    public void setOnItemClickListener(com.xy.xydoctor.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }
}
