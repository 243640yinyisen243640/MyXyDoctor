package com.xy.xydoctor.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.PatientAddTodayListBean;

import java.util.List;

public class PatientAddTodayListAdapter extends BaseQuickAdapter<PatientAddTodayListBean, BaseViewHolder> {

    public PatientAddTodayListAdapter(@Nullable List<PatientAddTodayListBean> data) {
        super(R.layout.item_patient_add_today, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PatientAddTodayListBean item) {
        String picture = item.getPicture();
        QMUIRadiusImageView imgHead = helper.getView(R.id.img_head);
        Glide.with(Utils.getApp())
                .load(picture)
                .error(R.drawable.patient_add_today_left_pic)
                .placeholder(R.drawable.patient_add_today_left_pic)
                .into(imgHead);
        String nickname = item.getNickname();
        int sex = item.getSex();
        int age = item.getAge();
        String tel = item.getTel();
        int sugar = item.getSugar();
        int blood = item.getBlood();
        helper.setText(R.id.tv_name, nickname);
        if (1 == sex) {
            helper.setImageResource(R.id.img_sex, R.drawable.patient_add_today_sex_male);
        } else {
            helper.setImageResource(R.id.img_sex, R.drawable.patient_add_today_sex_female);
        }
        helper.setText(R.id.tv_age, age + "岁");
        helper.setText(R.id.tv_tel, tel);

        ColorTextView tvGxy = helper.getView(R.id.tv_gxy);
        ColorTextView tvTnb = helper.getView(R.id.tv_tnb);
        if (1 == sugar) {
            tvTnb.setVisibility(View.VISIBLE);
        } else {
            tvTnb.setVisibility(View.GONE);
        }

        if (1 == blood) {
            tvGxy.setVisibility(View.VISIBLE);
        } else {
            tvGxy.setVisibility(View.GONE);
        }
    }
}
