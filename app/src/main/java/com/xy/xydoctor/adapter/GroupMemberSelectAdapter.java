package com.xy.xydoctor.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.lyd.baselib.util.glide.GlideUtils;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class GroupMemberSelectAdapter extends CommonAdapter<GroupUserBean> {

    public GroupMemberSelectAdapter(Context context, int layoutId, List<GroupUserBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GroupUserBean item, int position) {
        QMUIRadiusImageView imgPic = viewHolder.getView(R.id.img_head);
        ImageView imgSex = viewHolder.getView(R.id.img_sex);
        if (item.getSex() == 1) {
            GlideUtils.load(item.getPicture(), R.drawable.head_man, imgPic);
        } else {
            GlideUtils.load(item.getPicture(), R.drawable.head_woman, imgPic);
        }
        imgSex.setImageResource(item.getSex() == 1 ? R.drawable.male : R.drawable.female);
        viewHolder.setText(R.id.tv_name, item.getNickname());
        viewHolder.setText(R.id.tv_age, item.getAge() + "岁");
    }


}
