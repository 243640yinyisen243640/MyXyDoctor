package com.xy.xydoctor.ui.activity.community_management;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityDataStaticsInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.CirclePercentView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import retrofit2.Call;

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


    private String time;

    private CommunityDataStaticsInfo staticsInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().topView().removeAllViews();
        containerView().addView(initView());
        initValues();
    }


    private void getData() {
        Call<String> request = DataManager.getDataStatics(time, (call, response) -> {
            if (response.code == 200) {
                staticsInfo = (CommunityDataStaticsInfo) response.object;
                bindData();
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {

        setTextStyle(getString(R.string.community_statics_all_times), staticsInfo.getCommunityUser(), R.color.base_black, allPersonTextView, 12);
        setTextStyle(getString(R.string.community_statics_finish_times), staticsInfo.getSugarUser(), R.color.base_black, sugarPersonTextView, 12);
        setTextStyle(getString(R.string.community_statics_no_times), staticsInfo.getBloodUser(), R.color.base_black, pressurePersonTextView, 12);
        setTextStyle(getString(R.string.community_statics_lost_times), staticsInfo.getBothUsers(), R.color.base_black, totalPersonTextView, 12);

//        setTextStyle1(getString(R.string.community_statics_patient_num), staticsInfo.getSugarRates(), R.color.community_content_black, patientTextView);
//        setTextStyle1(getString(R.string.community_statics_year_finish_num), staticsInfo.getEmptyRates(), R.color.community_content_black, yearFinishTextView);
//        setTextStyle1(getString(R.string.community_statics_year_lost_num), staticsInfo.getUnemptyRates(), R.color.community_content_black, yearLostTextView);

        firstCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getSugarRates(), 0));
        secondCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getEmptyRates(), 0));
        thirdCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getUnemptyRates(), 0));


    }

    private void setTextStyle(String content, String title, int color, TextView textView, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content).append("\n");
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title);
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(XyScreenUtils.sp2px(getPageContext(), size)), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableStringBuilder);
    }

    private void setTextStyle1(String content, String title, int color, TextView textView) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content);
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title).append("%");
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableStringBuilder);
    }

    private void initValues() {
        firstCpv.setPercentage(50);//传入百分比的值
        secondCpv.setPercentage(30);//传入百分比的值
        thirdCpv.setPercentage(20);//传入百分比的值
        firstCpv.setRotation(-50 * 360 / 200);
        secondCpv.setRotation(-30 * 360 / 200 - 90);
        thirdCpv.setRotation(-20 * 360 / 200);
        time = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y);
        timeTextView.setText(time);
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
