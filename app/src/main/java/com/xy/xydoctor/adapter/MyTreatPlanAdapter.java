package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.bean.MyTreatPlanBean;
import com.xy.xydoctor.ui.activity.smart.smartmakepolicy.MyTreatPlanDetailActivity;
import com.xy.xydoctor.ui.activity.smart.smartmakepolicy.MyTreatPlanDetailFollowUpActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MyTreatPlanAdapter extends CommonAdapter<MyTreatPlanBean> {
    private String type;
    private String userId;

    public MyTreatPlanAdapter(Context context, int layoutId, List<MyTreatPlanBean> datas, String type, String userId) {
        super(context, layoutId, datas);
        this.type = type;
        this.userId = userId;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MyTreatPlanBean item, int position) {
        if ("0".equals(type)) {
            viewHolder.setText(R.id.tv_name, "治疗方案");
            viewHolder.setText(R.id.tv_time, item.getTime());
        } else if ("1".equals(type)) {
            viewHolder.setText(R.id.tv_name, "治疗方案");
            viewHolder.setText(R.id.tv_time, item.getSendtime());
        } else {
            viewHolder.setText(R.id.tv_name, "治疗方案");
            viewHolder.setText(R.id.tv_time, item.getSendtime());
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(type)) {
                    String treatment = item.getTreatment();
                    if ("1".equals(treatment)) {
                        Intent intent = new Intent(Utils.getApp(), MyTreatPlanDetailActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        intent.putExtra("type", "1");
                        intent.putExtra("userid", userId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                    } else {
                        Intent intent = new Intent(Utils.getApp(), MyTreatPlanDetailFollowUpActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        intent.putExtra("type", "1");
                        intent.putExtra("userid", userId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                    }
                } else if ("1".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                    intent.putExtra("title", "处方");
                    intent.putExtra("url", item.getUrl());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                    intent.putExtra("title", "处方");
                    intent.putExtra("url", item.getUrl());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }

            }
        });
    }
}
