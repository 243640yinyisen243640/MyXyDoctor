package com.xy.xydoctor.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.xy.xydoctor.R;

import java.util.List;

public class SelectUserAdapter extends BaseQuickAdapter<GroupUserBean, BaseViewHolder> {

    public SelectUserAdapter(@Nullable List<GroupUserBean> data) {
        super(R.layout.item_select_user, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, GroupUserBean groupUserBean) {
        ImageView imgHead = holder.getView(R.id.img_head);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvId = holder.getView(R.id.tv_userid);
        if (1 == groupUserBean.getSex()) {
            Glide.with(Utils.getApp()).load(groupUserBean.getPicture()).error(R.drawable.head_man).placeholder(R.drawable.head_man).into(imgHead);
        } else {
            Glide.with(Utils.getApp()).load(groupUserBean.getPicture()).error(R.drawable.head_woman).placeholder(R.drawable.head_woman).into(imgHead);
        }
        tvName.setText(groupUserBean.getNickname());
        tvId.setText(groupUserBean.getUserId());
    }
}
