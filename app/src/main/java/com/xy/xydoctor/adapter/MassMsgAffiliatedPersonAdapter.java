package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.MassHistoryBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MassMsgAffiliatedPersonAdapter extends CommonAdapter<MassHistoryBean.UserBean> {
    public MassMsgAffiliatedPersonAdapter(Context context, int layoutId, List<MassHistoryBean.UserBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MassHistoryBean.UserBean item, int position) {
        QMUIRadiusImageView imgPic = viewHolder.getView(R.id.img_head);
        ImageView imgSex = viewHolder.getView(R.id.img_sex);
        if (item.getSex() == 1) {
            Glide.with(Utils.getApp()).load(item.getPicture()).placeholder(R.drawable.head_man).error(R.drawable.head_man).into(imgPic);
        } else {
            Glide.with(Utils.getApp()).load(item.getPicture()).placeholder(R.drawable.head_woman).error(R.drawable.head_woman).into(imgPic);
        }
        imgSex.setImageResource(item.getSex() == 1 ? R.drawable.male : R.drawable.female);
        viewHolder.setText(R.id.tv_name, item.getNickname());
        viewHolder.setText(R.id.tv_age, item.getAge() + "岁");

        viewHolder.getView(R.id.img_del).setVisibility(View.GONE);
    }
}
