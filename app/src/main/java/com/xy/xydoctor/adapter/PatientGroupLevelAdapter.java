package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.PatientGroupLevelOneBean;
import com.xy.xydoctor.bean.PatientGroupLevelZeroBean;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;

import java.util.List;

/**
 * 描述: 患者分组Adapter
 * 作者: LYD
 * 创建日期: 2019/3/4 10:42
 */
public class PatientGroupLevelAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context mContext;

    public PatientGroupLevelAdapter(List<MultiItemEntity> data, Context context) {
        super(data);
        this.mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.item_patient_group_level_zero);
        addItemType(TYPE_LEVEL_1, R.layout.item_patient_group_level_one);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                PatientGroupLevelZeroBean lv0 = (PatientGroupLevelZeroBean) item;
                helper.setText(R.id.tv_group_name, lv0.getGroupName());
                helper.setImageResource(R.id.img_right_arrow, lv0.isExpanded() ? R.drawable.right_arrow_down : R.drawable.right_arrow);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                PatientGroupLevelOneBean lv1 = (PatientGroupLevelOneBean) item;
                QMUIRadiusImageView imgHead = helper.getView(R.id.img_head);
                if (1 == lv1.getSex()) {
                    Glide.with(mContext).load(lv1.getImgHeadUrl()).placeholder(R.drawable.head_man).error(R.drawable.head_man).into(imgHead);
                } else {
                    Glide.with(mContext).load(lv1.getImgHeadUrl()).placeholder(R.drawable.head_woman).error(R.drawable.head_woman).into(imgHead);
                }
                if (TextUtils.isEmpty(lv1.getName())) {
                    helper.setText(R.id.tv_name, lv1.getTel());
                } else {
                    helper.setText(R.id.tv_name, lv1.getName());
                }
                helper.setText(R.id.tv_age, lv1.getAge() + "岁");
                ImageView imgSex = helper.getView(R.id.img_sex);
                imgSex.setImageResource(1 == lv1.getSex() ? R.drawable.male : R.drawable.female);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //随机码点击
                        Intent intent = new Intent(mContext, PatientInfoActivity.class);
                        intent.putExtra("userid", lv1.getUserid() + "");
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
