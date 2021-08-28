package com.xy.xydoctor.adapter.community_manager;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.ReachTheStandRateBean;

import java.util.List;

/**
 * 描述:  随访统计
 * 作者: LYD
 * 创建日期: 2020/11/3 16:23
 */
public class CommunitySetFollowAdapter extends BaseQuickAdapter<ReachTheStandRateBean, BaseViewHolder> {
    public CommunitySetFollowAdapter(@Nullable List<ReachTheStandRateBean> data) {
        super(R.layout.item_follow_data_statics, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ReachTheStandRateBean item) {
        int position = helper.getAdapterPosition();
        //设置图片
        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.reach_follow_statics_pic);
        helper.setImageResource(R.id.img_follow_statics_pic, imgArray.getResourceId(position, 0));
        //设置文字
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.reach_follow_statics_name);
        helper.setText(R.id.tv_follow_statics_name, stringArray[position]);
        //设置进度条
        ProgressBar pbRate = helper.getView(R.id.pb_follow_statics_rate);
        pbRate.setProgress(item.getRate());
        switch (position) {
            case 0:
                setPbDrawable(pbRate, ResourceUtils.getDrawable(R.drawable.progressbar_bg_one));
                break;
            case 1:
                setPbDrawable(pbRate, ResourceUtils.getDrawable(R.drawable.progressbar_bg_two));
                break;
            case 2:
                setPbDrawable(pbRate, ResourceUtils.getDrawable(R.drawable.progressbar_bg_three));
                break;
        }
        //设置比率
        helper.setText(R.id.tv_follow_statics_rate, item.getRate() + "%");
    }


    private void setPbDrawable(ProgressBar pb, Drawable draw) {
        draw.setBounds(pb.getProgressDrawable().getBounds());
        pb.setProgressDrawable(draw);
    }
}
