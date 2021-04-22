package com.xy.xydoctor.adapter.multi;

import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.NewPatientListBean;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * 描述: 未操作
 * 作者: LYD
 * 创建日期: 2019/5/30 15:17
 */
public class Delegate12 implements ItemViewDelegate<NewPatientListBean.DataBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_new_patient_done;
    }

    @Override
    public boolean isForViewType(NewPatientListBean.DataBean item, int position) {
        return !item.isOperate();
    }

    @Override
    public void convert(ViewHolder holder, NewPatientListBean.DataBean dataBean, int position) {
        ImageView imgHead = holder.getView(R.id.img_head);
        Glide.with(Utils.getApp()).load(dataBean.getPic())
                .error(R.drawable.new_patient_head_img)
                .placeholder(R.drawable.new_patient_head_img)
                .into(imgHead);
        holder.setText(R.id.tv_name, dataBean.getName());
        String edittime = dataBean.getEdittime();
        holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_edit_time), edittime));
        //审批状态（是否同意 1：拒绝 2：同意 0：未操作 ）
        if ("1".equals(dataBean.getStatus())) {
            holder.setText(R.id.tv_state, "已拒绝");
        } else {
            holder.setText(R.id.tv_state, "已同意");
        }
    }
}
