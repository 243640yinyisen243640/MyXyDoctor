package com.xy.xydoctor.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.baselib.util.glide.GlideUtils;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;

import java.util.HashMap;
import java.util.List;

public class SelectPeopleDirectorAdapter extends BaseQuickAdapter<GroupUserBean, BaseViewHolder> {
    private static final String TAG = "SelectPeopleActivity";
    public HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private List<GroupUserBean> datas;

    public SelectPeopleDirectorAdapter(@Nullable List<GroupUserBean> datas, List<GroupUserBean> checkIdList) {
        super(R.layout.item_select_people_list_director, datas);
        this.datas = datas;
        init(checkIdList);
    }

    private void init(List<GroupUserBean> checkIdList) {
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
        //Log.e(TAG, "传进来的选中列表==" + checkIdList);
        //选中位置确定
        if (checkIdList != null && checkIdList.size() > 0) {
            for (int i = 0; i < checkIdList.size(); i++) {
                int checkId = checkIdList.get(i).getUserid();
                for (int j = 0; j < datas.size(); j++) {
                    int userId = datas.get(j).getUserid();
                    if (checkId == userId) {
                        int position = datas.indexOf(datas.get(j));
                        //Log.e(TAG, "选中位置==" + position);
                        isSelected.put(position, true);
                    }
                }
            }
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, GroupUserBean item) {
        QMUIRadiusImageView imgPic = viewHolder.getView(R.id.img_head);
        ImageView imgSex = viewHolder.getView(R.id.img_sex);
        if (item.getSex() == 1) {
            GlideUtils.load(item.getPicture(), R.drawable.head_man, imgPic);
            //Glide.with(Utils.getApp()).load(item.getPicture()).placeholder(R.drawable.head_man).error(R.drawable.head_man).into(imgPic);
        } else {
            GlideUtils.load(item.getPicture(), R.drawable.head_woman, imgPic);
            //Glide.with(Utils.getApp()).load(item.getPicture()).placeholder(R.drawable.head_woman).error(R.drawable.head_woman).into(imgPic);
        }
        imgSex.setImageResource(item.getSex() == 1 ? R.drawable.male : R.drawable.female);
        viewHolder.setText(R.id.tv_name, item.getNickname());
        viewHolder.setText(R.id.tv_age, item.getAge() + "岁");
        //选中处理
        ImageView imgCheck = viewHolder.getView(R.id.img_check);
        int position = viewHolder.getLayoutPosition();
        Boolean check = isSelected.get(position);
        if (check) {
            imgCheck.setImageResource(R.drawable.mass_msg_director_checked);
        } else {
            imgCheck.setImageResource(R.drawable.mass_msg_director_check);
        }
        //添加点击事件
        //viewHolder.addOnClickListener(R.id.img_check);
    }
}
