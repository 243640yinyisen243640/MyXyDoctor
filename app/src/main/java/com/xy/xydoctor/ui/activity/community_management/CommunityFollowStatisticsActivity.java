package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.IndexBean;
import com.xy.xydoctor.bean.ReachTheStandRateBean;
import com.xy.xydoctor.customerView.CirclePercentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/25 11:31
 * Description: 随访统计
 */
public class CommunityFollowStatisticsActivity extends XYSoftUIBaseActivity {

    private CirclePercentView cpv1, cpv2, cpv3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerView().addView(initView());
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_follow_statistics, null);
        cpv1 = view.findViewById(R.id.circle_percent_progress1);
        cpv2 = view.findViewById(R.id.circle_percent_progress2);
        cpv3 = view.findViewById(R.id.circle_percent_progress3);
        cpv1.setPercentage(50);//传入百分比的值
        cpv2.setPercentage(30);//传入百分比的值
        cpv3.setPercentage(20);//传入百分比的值
        cpv1.setRotation(-50 * 360 / 200);
        cpv2.setRotation(-30 * 360 / 200 - 90);
        cpv3.setRotation(-20 * 360 / 200);
        return view;
    }

    private void setReachTheStandRate(IndexBean.SugarBean sugars) {
        int total = sugars.getTotal();
        int empstomach = sugars.getEmpstomach();
        int unempstomach = sugars.getUnempstomach();
        List<ReachTheStandRateBean> list = new ArrayList<>();
        ReachTheStandRateBean bean0 = new ReachTheStandRateBean(total);
        ReachTheStandRateBean bean1 = new ReachTheStandRateBean(empstomach);
        ReachTheStandRateBean bean2 = new ReachTheStandRateBean(unempstomach);
        list.add(bean0);
        list.add(bean1);
        list.add(bean2);
        //        rvReachTheStandRate.setLayoutManager(new LinearLayoutManager(getPageContext()));
        //        rvReachTheStandRate.setAdapter(new ReachTheStandRateAdapter(list));
    }
}
