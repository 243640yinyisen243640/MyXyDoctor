package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.CirclePercentView;

/**
 * Author: LYD
 * Date: 2021/8/25 11:31
 * Description: 数据统计血糖
 */
public class CommunityDataStatisticsActivity extends XYSoftUIBaseActivity {

    /**
     * 时间 ， 总人数 高血糖，高血压，合并人数
     */
    private TextView timeTextView, allPersonTextView, sugarPersonTextView, pressurePersonTextView, totalPersonTextView;
    /**
     * 总达标率，空腹达标率，未空腹达标率
     */
    private CirclePercentView firstCpv, secondCpv, thirdCpv;

    private BarChart sugarBarChart;

    private CirclePercentView pressureCpv;

    private BarChart pressureBc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerView().addView(initView());
        initValues();
    }

    private void initValues() {
        firstCpv.setPercentage(50);//传入百分比的值
        secondCpv.setPercentage(30);//传入百分比的值
        thirdCpv.setPercentage(20);//传入百分比的值
        firstCpv.setRotation(-50 * 360 / 200);
        secondCpv.setRotation(-30 * 360 / 200 - 90);
        thirdCpv.setRotation(-20 * 360 / 200);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_data_statistics, null);
        timeTextView = view.findViewById(R.id.tv_ds_time);
        allPersonTextView = view.findViewById(R.id.tv_ds_all_person_num);
        sugarPersonTextView = view.findViewById(R.id.tv_ds_sugar_person_num);
        pressurePersonTextView = view.findViewById(R.id.tv_ds_pressure_num);
        totalPersonTextView = view.findViewById(R.id.tv_ds_total_person_num);
        firstCpv = view.findViewById(R.id.cpv_ds_first);
        secondCpv = view.findViewById(R.id.cpv_ds_second);
        thirdCpv = view.findViewById(R.id.cpv_ds_third);
        sugarBarChart = view.findViewById(R.id.bar_ds_finish_sugar);
        pressureCpv = view.findViewById(R.id.cpv_ds_sugar);
        pressureBc = view.findViewById(R.id.bar_ds_finish_pressure);
        return view;
    }
}
