package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.glide.GlideUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.ApplyToHospitalListBean;
import com.xy.xydoctor.ui.activity.homesign.InHospitalManageActivity;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
import com.xy.xydoctor.ui.activity.todo.ApplyToHospitalDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:申请住院
 * 作者: LYD
 * 创建日期: 2019/6/24 10:34
 */
public class ApplyToHospitalAdapter extends CommonAdapter<ApplyToHospitalListBean.DataBean> {
    private String type;

    public ApplyToHospitalAdapter(Context context, int layoutId, List<ApplyToHospitalListBean.DataBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder holder, ApplyToHospitalListBean.DataBean dataBean, int position) {
        ImageView imgHead = holder.getView(R.id.img_head);
        GlideUtils.load(dataBean.getPic(), R.drawable.new_patient_head_img, imgHead);
        String creattime = dataBean.getCreattime();
        String edittime = dataBean.getEdittime();
        TextView tvState = holder.getView(R.id.tv_state);
        TextView tvLook = holder.getView(R.id.tv_look);
        holder.setText(R.id.tv_name, dataBean.getName());
        switch (dataBean.getStatus()) {
            case "1":
                tvLook.setVisibility(View.VISIBLE);
                tvState.setVisibility(View.GONE);
                holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_apply_time), creattime));
                break;
            case "2":
                tvLook.setVisibility(View.GONE);
                tvState.setVisibility(View.VISIBLE);
                tvState.setText("已同意");
                holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_edit_time), edittime));
                break;
            case "3":
                tvLook.setVisibility(View.GONE);
                tvState.setVisibility(View.VISIBLE);
                tvState.setText("已拒绝");
                holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_edit_time), edittime));
                break;
        }
        //查看点击事件
        tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("homeSign".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), InHospitalManageActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    Intent intent = new Intent(Utils.getApp(), ApplyToHospitalDetailActivity.class);
                    intent.putExtra("id", dataBean.getId() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }

            }
        });
        //头像点击事件
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), PatientInfoActivity.class);
                intent.putExtra("userid", dataBean.getUid() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
