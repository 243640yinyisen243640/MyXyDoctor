package com.xy.xydoctor.adapter.multi;

import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.NewPatientListBean;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * 描述: 未操作
 * 作者: LYD
 * 创建日期: 2019/5/30 15:17
 */
public class Delegate0 implements ItemViewDelegate<NewPatientListBean.DataBean> {

    private AdapterClickImp listener;

    public Delegate0(AdapterClickImp listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_new_patient_doing;
    }

    @Override
    public boolean isForViewType(NewPatientListBean.DataBean item, int position) {
        return item.isOperate();
    }

    @Override
    public void convert(ViewHolder holder, NewPatientListBean.DataBean dataBean, int position) {
        ImageView imgHead = holder.getView(R.id.img_head);
        Glide.with(Utils.getApp()).load(dataBean.getPic())
                .error(R.drawable.new_patient_head_img)
                .placeholder(R.drawable.new_patient_head_img)
                .into(imgHead);
        holder.setText(R.id.tv_name, dataBean.getName());

        String creattime = dataBean.getCreattime();
        holder.setText(R.id.tv_desc, String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital_apply_time), creattime));

        holder.setOnClickListener(R.id.tv_yes, new OnAdapterViewClickListener(holder.getView(R.id.tv_yes), position));
        holder.setOnClickListener(R.id.tv_no, new OnAdapterViewClickListener(holder.getView(R.id.tv_no), position));
    }


    private class OnAdapterViewClickListener implements View.OnClickListener {

        View view;
        int posi;


        public OnAdapterViewClickListener(View view, int posi) {
            this.view = view;
            this.posi = posi;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onAdapterClick(view, posi);
            }
        }
    }
}
