package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitListActivity;
import com.xy.xydoctor.ui.activity.smart.smartanalyse.AnalyseMonthListActivity;
import com.xy.xydoctor.ui.activity.smart.smartmakepolicy.MyTreatPlanListActivity;
import com.xy.xydoctor.ui.activity.tcm.TcmListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class PatientInfoBottomTopThreeAdapter extends CommonAdapter {
    private static final String TAG = "PatientInfoBottomTopThreeAdapter";
    private List<String> datas;


    public PatientInfoBottomTopThreeAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        //设置图片背景
        int[] imgLlRes = new int[]{R.drawable.patient_info_label_one, R.drawable.patient_info_label_two, R.drawable.patient_info_label_three};
        viewHolder.setBackgroundRes(R.id.ll_label, imgLlRes[position]);
        //设置图片
        int[] imgRes = new int[]{R.drawable.patient_info_label_one_left, R.drawable.patient_info_label_two_left, R.drawable.patient_info_label_three_left};
        viewHolder.setImageResource(R.id.img_label, imgRes[position]);
        //设置文字
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.patient_info_tv_label);
        viewHolder.setText(R.id.tv_label, stringArray[position]);

        //设置上文字
        String[] topArray = Utils.getApp().getResources().getStringArray(R.array.patient_info_tv_top);
        viewHolder.setText(R.id.tv_top, topArray[position]);
        //设置中文字
        String[] centerArray = Utils.getApp().getResources().getStringArray(R.array.patient_info_tv_center);
        viewHolder.setText(R.id.tv_center, centerArray[position]);
        //设置下文字
        String[] bottomArray = Utils.getApp().getResources().getStringArray(R.array.patient_info_tv_bottom);
        viewHolder.setText(R.id.tv_bottom, bottomArray[position]);
        //显示判断
        LinearLayout llBottom = viewHolder.getView(R.id.ll_bottom);
        LinearLayout llBottomBottom = viewHolder.getView(R.id.ll_bottom_bottom);
        //动态设置宽高
        LinearLayout llItem = viewHolder.getView(R.id.ll_item);
        ViewGroup.LayoutParams paramItem = llItem.getLayoutParams();
        View lineVerticalLeft = viewHolder.getView(R.id.line_vertical_left);
        View lineVerticalRight = viewHolder.getView(R.id.line_vertical_right);
        ViewGroup.LayoutParams paramLeft = lineVerticalLeft.getLayoutParams();
        ViewGroup.LayoutParams paramRight = lineVerticalRight.getLayoutParams();
        int totalHeight = ConvertUtils.dp2px(250);
        int columnHeight = ConvertUtils.dp2px(46);
        switch (position) {
            case 0:
                llBottom.setVisibility(View.GONE);
                llBottomBottom.setVisibility(View.GONE);
                paramItem.height = totalHeight - columnHeight - columnHeight;
                llItem.setLayoutParams(paramItem);
                paramLeft.height = totalHeight - columnHeight - columnHeight;
                paramRight.height = totalHeight - columnHeight - columnHeight;
                lineVerticalLeft.setLayoutParams(paramLeft);
                lineVerticalRight.setLayoutParams(paramRight);
                break;
            case 1:
                llBottom.setVisibility(View.VISIBLE);
                llBottomBottom.setVisibility(View.VISIBLE);
                paramItem.height = totalHeight;
                llItem.setLayoutParams(paramItem);
                paramLeft.height = totalHeight;
                paramRight.height = totalHeight;
                lineVerticalLeft.setLayoutParams(paramLeft);
                lineVerticalRight.setLayoutParams(paramRight);
                break;
            case 2:
                llBottom.setVisibility(View.VISIBLE);
                llBottomBottom.setVisibility(View.GONE);
                paramItem.height = totalHeight - columnHeight;
                llItem.setLayoutParams(paramItem);
                paramLeft.height = totalHeight - columnHeight;
                paramRight.height = totalHeight - columnHeight;
                lineVerticalLeft.setLayoutParams(paramLeft);
                lineVerticalRight.setLayoutParams(paramRight);
                break;
        }
        //获取用户Id
        String userId = datas.get(position);
        viewHolder.getView(R.id.ll_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (position) {
                    //血糖分析报告
                    case 0:
                        intent = new Intent(Utils.getApp(), AnalyseMonthListActivity.class);
                        //0 血压 1 血糖
                        intent.putExtra("userid", userId);
                        intent.putExtra("type", "1");
                        break;
                    //糖尿病自我管理处方
                    case 1:
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("userid", userId);
                        intent.putExtra("type", "1");
                        break;
                    case 2://血糖随访
                        intent = new Intent(Utils.getApp(), FollowUpVisitListActivity.class);
                        intent.putExtra("userid", userId);
                        intent.putExtra("type", 0);
                        break;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
        viewHolder.getView(R.id.ll_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (position) {
                    case 0://血压分析报告
                        intent = new Intent(Utils.getApp(), AnalyseMonthListActivity.class);
                        intent.putExtra("userid", userId);
                        //0 血压 1 血糖
                        intent.putExtra("type", "0");
                        break;
                    case 1://个性化降压方案
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("userid", userId);
                        intent.putExtra("type", "0");
                        break;
                    case 2://血压随访
                        intent = new Intent(Utils.getApp(), FollowUpVisitListActivity.class);
                        intent.putExtra("userid", userId);
                        intent.putExtra("type", 1);
                        break;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
        viewHolder.getView(R.id.ll_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (position) {
                    case 1:
                        intent = new Intent(Utils.getApp(), TcmListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("userId", userId);
                        Utils.getApp().startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Utils.getApp(), FollowUpVisitListActivity.class);
                        intent.putExtra("userid", userId);
                        intent.putExtra("type", 2);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                        break;

                }
            }
        });
        viewHolder.getView(R.id.ll_bottom_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                intent.putExtra("userid", userId);
                intent.putExtra("type", "2");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
