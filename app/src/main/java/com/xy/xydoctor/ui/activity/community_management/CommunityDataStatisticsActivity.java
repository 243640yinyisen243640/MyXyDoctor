package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.CirclePercentView;

/**
 * Author: LYD
 * Date: 2021/8/25 11:31
 * Description: 数据统计血糖
 */
public class CommunityDataStatisticsActivity extends XYSoftUIBaseActivity {

    private CirclePercentView cpv1, cpv2, cpv3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerView().addView(initView());
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_data_statistics, null);
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
}
