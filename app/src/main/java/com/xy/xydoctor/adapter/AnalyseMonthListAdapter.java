package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.smart.smartanalyse.BloodPressureReportActivity;
import com.xy.xydoctor.ui.activity.smart.smartanalyse.BloodSugarReportActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class AnalyseMonthListAdapter extends CommonAdapter<String> {
    private String type;
    private String userid;

    public AnalyseMonthListAdapter(Context context, int layoutId, List datas, String type, String userid) {
        super(context, layoutId, datas);
        this.type = type;
        this.userid = userid;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_time, item);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                //分析报告
                if ("0".equals(type)) {
                    //血压
                    intent = new Intent(Utils.getApp(), BloodPressureReportActivity.class);
                    intent.putExtra("time", item);
                    intent.putExtra("userid", userid);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    //血糖
                    intent = new Intent(Utils.getApp(), BloodSugarReportActivity.class);
                    intent.putExtra("time", item);
                    intent.putExtra("userid", userid);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });
    }
}
