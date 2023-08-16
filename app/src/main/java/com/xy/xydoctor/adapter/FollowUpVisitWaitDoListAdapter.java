package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpVisitWaitDoListBean;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmit2Activity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmit2Activity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:   待办事项列表
 * 作者: LYD
 * 创建日期: 2019/8/8 14:09
 */
public class FollowUpVisitWaitDoListAdapter extends CommonAdapter<FollowUpVisitWaitDoListBean.DataBean> {
    public FollowUpVisitWaitDoListAdapter(Context context, int layoutId, List<FollowUpVisitWaitDoListBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitWaitDoListBean.DataBean item, int position) {
        String picture = item.getPicture();
        String nickname = item.getNickname();
        int status = item.getStatus();
        QMUIRadiusImageView imgHead = viewHolder.getView(R.id.img_head);
        Glide.with(Utils.getApp())
                .load(picture)
                .placeholder(R.drawable.head_default)
                .error(R.drawable.head_default)
                .into(imgHead);
        viewHolder.setText(R.id.tv_name, nickname);
        TextView tvDesc = viewHolder.getView(R.id.tv_desc);
        String addtime = item.getAddtime();
        String desc = String.format(Utils.getApp().getString(R.string.to_do_list_follow_up_visit_wait_add_time), addtime);
        tvDesc.setText(desc);
        //点击跳转
        String type = item.getType();
        int id = item.getId();
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1血糖 2血压
                if ("1".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmit2Activity.class);
                    intent.putExtra("id", id + "");
                    intent.putExtra("status", status + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else if ("2".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmit2Activity.class);
                    intent.putExtra("id", id + "");
                    intent.putExtra("status", status + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    Intent intent = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
                    intent.putExtra("id", id + "");
                    intent.putExtra("status", status + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });
    }
}
