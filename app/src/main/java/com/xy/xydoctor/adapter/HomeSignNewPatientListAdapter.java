package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.glide.GlideUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.NewPatientListBean;
import com.xy.xydoctor.ui.activity.homesign.SignPatientInfoActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:家签 新的患者
 * 作者: LYD
 * 创建日期: 2019/6/24 10:34
 */
public class HomeSignNewPatientListAdapter extends CommonAdapter<NewPatientListBean.DataBean> {
    public HomeSignNewPatientListAdapter(Context context, int layoutId, List<NewPatientListBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewPatientListBean.DataBean dataBean, int position) {
        ImageView imgHead = holder.getView(R.id.img_head);
        GlideUtils.load(dataBean.getPic(), R.drawable.new_patient_head_img, imgHead);
        holder.setText(R.id.tv_name, dataBean.getName());
        String creattime = dataBean.getCreattime();
        String edittime = dataBean.getEdittime();
        TextView tvState = holder.getView(R.id.tv_state);
        TextView tvLook = holder.getView(R.id.tv_look);
        switch (dataBean.getStatus()) {
            case "0":
                tvLook.setVisibility(View.VISIBLE);
                tvState.setVisibility(View.GONE);
                holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_apply_time), creattime));
                break;
            case "1":
                tvLook.setVisibility(View.GONE);
                tvState.setVisibility(View.VISIBLE);
                tvState.setText("已拒绝");
                holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_edit_time), edittime));
                break;
            case "2":
                tvLook.setVisibility(View.GONE);
                tvState.setVisibility(View.VISIBLE);
                tvState.setText("已同意");
                holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_edit_time), edittime));
                break;
        }
        //查看点击事件
        tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //患者信息页面
                Intent intent = new Intent(Utils.getApp(), SignPatientInfoActivity.class);
                intent.putExtra("id", dataBean.getId() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
