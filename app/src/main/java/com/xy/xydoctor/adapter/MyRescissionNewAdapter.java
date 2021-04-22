package com.xy.xydoctor.adapter;
/*
 * 类名:     MyRescissionAdapter
 * 描述:     解约列表
 * 作者:     ZWK
 * 创建日期: 2020/1/16 13:22
 */

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.HomeSignRescissionBean;

import java.util.List;

public class MyRescissionNewAdapter extends BaseQuickAdapter<HomeSignRescissionBean, BaseViewHolder> {

    private OnItemClickListener onItemClickListener;

    public MyRescissionNewAdapter(@Nullable List<HomeSignRescissionBean> data) {
        super(R.layout.item_my_rescission, data);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, HomeSignRescissionBean bean) {
        holder.setText(R.id.tv_name, bean.getNickname());
        holder.setText(R.id.tv_tel, String.format("手机号码 %s", bean.getTel()));
        holder.setText(R.id.tv_id, String.format("身份证 %s", bean.getIdcard()));
        holder.setText(R.id.tv_time, String.format("签约时间 %s   解约时间 %s", bean.getStarttime(), bean.getEndtime()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v);
            }
        });
    }

    public interface OnItemClickListener {
        void OnItemClick(View v);
    }
}
