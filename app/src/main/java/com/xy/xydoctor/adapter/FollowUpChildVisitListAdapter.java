package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpVisitListBean;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitAddActivity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmit2Activity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmit2Activity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FollowUpChildVisitListAdapter extends CommonAdapter<FollowUpVisitListBean.DataBean.PlanListBean> {

    private String type;

    public FollowUpChildVisitListAdapter(Context context, int layoutId, List<FollowUpVisitListBean.DataBean.PlanListBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }


    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitListBean.DataBean.PlanListBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getAddtime());
        viewHolder.setText(R.id.tv_content, "第" + item.getTimes() + "次随访管理");
        int status = item.getStatus();
        String statusStr = status + "";
        switch (statusStr) {
            case "1"://草稿    (编辑草稿)
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_drafts);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "草稿箱");
                break;
            case "2"://待开启  (只能看,不能总结)->修改为医生可以看，可以为患者填写信息
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_doing);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "等待患者开启");
                break;
            case "3"://未完成 (只能看,不能总结)
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_un_done);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "未完成");
                break;
            case "4"://已完成,等待医生总结   (可以看,可以总结)
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_done_wait_doctor);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "已完成,等待医生总结");
                break;
            case "5"://已完成,查看随访报告  (可以看,已经总结过,不能总结)
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_done_look);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "已完成,查看随访报告");
                break;
        }
        //点击进详情
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (statusStr) {
                    case "1"://进编辑
                        intent = new Intent(Utils.getApp(), FollowUpVisitAddActivity.class);
                        intent.putExtra("id", item.getId());
                        intent.putExtra("type", type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                        break;
                /*    case "2":
                        ToastUtils.showShort("等待患者开启");

                        break;*/
                    case "3":
                    case "2":
                    case "4":
                    case "5":
                        if ("1".equals(type)) {
                            intent = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmit2Activity.class);
                            intent.putExtra("id", item.getId() + "");
                            //intent.putExtra("status", status + "");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent);
                        } else if ("2".equals(type)) {
                            intent = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmit2Activity.class);
                            intent.putExtra("id", item.getId() + "");
                            //intent.putExtra("status", status + "");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent);
                        } else {
                            intent = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
                            intent.putExtra("id", item.getId() + "");
                            //intent.putExtra("status", status + "");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent);
                        }
                        break;
                }
            }
        });
    }
}
