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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.gyf.immersionbar.ImmersionBar;
import com.horen.chart.barchart.BarChartHelper;
import com.horen.chart.barchart.IBarData;
import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.TestBarData;
import com.xy.xydoctor.bean.community_manamer.CommunityDataStaticsInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.CirclePercentView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/25 11:31
 * Description: 数据统计血糖
 */
public class CommunityDataStatisticsActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private ImageView backImageView;
    /**
     * 时间 ， 总人数 高血糖，高血压，合并人数
     */
    private TextView timeTextView, allPersonTextView, sugarPersonTextView, pressurePersonTextView, totalPersonTextView;
    /**
     * 总达标率，空腹达标率，未空腹达标率
     */
    private CirclePercentView firstCpv, secondCpv, thirdCpv;

    private TextView allRateTextView, emptyRateTextView, unemptyRateTextView;

    private BarChart sugarBarChart;

    private CirclePercentView pressureCpv;

    private BarChart pressureBc;
    private TextView pressureRateTextView;

    private String time;

    private CommunityDataStaticsInfo staticsInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().topView().removeAllViews();
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)  //指定状态栏颜色,根据情况是否设置
                .init();
        containerView().addView(initView());
        initValues();
        initListener();
        getData();
    }

    private void initListener() {
        timeTextView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
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

        setTextStyle(getString(R.string.community_statics_all_person), staticsInfo.getCommunityUser(), R.color.data_gray_light, allPersonTextView, 12);
        setTextStyle(getString(R.string.community_statics_sugar_person), staticsInfo.getSugarUser(), R.color.data_gray_light, sugarPersonTextView, 12);
        setTextStyle(getString(R.string.community_statics_pressure_person), staticsInfo.getBloodUser(), R.color.data_gray_light, pressurePersonTextView, 12);
        setTextStyle(getString(R.string.community_statics_total_person), staticsInfo.getBothUser(), R.color.data_gray_light, totalPersonTextView, 12);

        setTextStyle1(getString(R.string.community_statics_all_rate), staticsInfo.getSugarRates(), R.color.base_black, allRateTextView);
        setTextStyle1(getString(R.string.community_statics_empty_rate), staticsInfo.getEmptyRates(), R.color.base_black, emptyRateTextView);
        setTextStyle1(getString(R.string.community_statics_unempty_rate), staticsInfo.getUnemptyRates(), R.color.base_black, unemptyRateTextView);
        setTextStyle1(getString(R.string.community_statics_all_rate), staticsInfo.getBloodRates(), R.color.base_black, pressureRateTextView);

        firstCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getSugarRates(), 0));
        secondCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getEmptyRates(), 0));
        thirdCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getUnemptyRates(), 0));
        pressureCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getBloodRates(), 0));
        setBarChart();
        setBarChartPressure();
    }

    /**
     * 设置血糖
     */
    private void setBarChart() {
        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("血糖");
        names.add("血糖");
        //多条柱状图数据集合
        List<IBarData> data = new ArrayList<>();
        // 单个柱状图数据

        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getSugarOne(), 0)), "第一季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getSugarTwo(), 0)), "第二季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getSugarThree(), 0)), "第三季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getSugarFour(), 0)), "第四季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getAlllSugar(), 0)), "年度"));


        //设置y轴数据
        YAxis leftAxis = sugarBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100);

        //设置不可点击
        sugarBarChart.setTouchEnabled(true);
        new BarChartHelper.Builder()
                .setContext(getPageContext())
                // 柱状图
                .setBarChart(sugarBarChart)
                .setBarColor(ContextCompat.getColor(getPageContext(), R.color.community_statics_barchart_green))
                // 多柱状图
                .setBarData(data)
                // 多柱状图 标签名集合
                .setLabels(names)
                //一页X轴显示个数
                .setDisplayCount(5)
                // 标签显示隐藏
                .setLegendEnable(false)
                // 标签文字大小
                .setLegendTextSize(16)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                //X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                .setGroupSpace(0.4f)
                //X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
    }

    /**
     * 设置血压
     */
    private void setBarChartPressure() {
        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("血糖");
        names.add("血糖");
        //多条柱状图数据集合
        List<IBarData> data = new ArrayList<>();
        // 单个柱状图数据

        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getBloodOne(), 0)), "第一季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getBloodTwo(), 0)), "第二季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getBloodThree(), 0)), "第三季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getBloodFour(), 0)), "第四季度"));
        data.add(new TestBarData((TurnsUtils.getInt(staticsInfo.getAllBlood(), 0)), "年度"));


        //设置y轴数据
        YAxis leftAxis = pressureBc.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100);

        //设置不可点击
        pressureBc.setTouchEnabled(true);
        new BarChartHelper.Builder()
                .setContext(getPageContext())
                // 柱状图
                .setBarChart(pressureBc)
                .setBarColor(ContextCompat.getColor(getPageContext(), R.color.community_statics_barchart_orange))
                // 多柱状图
                .setBarData(data)
                // 多柱状图 标签名集合
                .setLabels(names)
                //一页X轴显示个数
                .setDisplayCount(5)
                // 标签显示隐藏
                .setLegendEnable(false)
                // 标签文字大小
                .setLegendTextSize(16)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                //X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                .setGroupSpace(0.4f)
                //X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
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
        spannableStringBuilder.append(content).append("  ");
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
        timeTextView.setText(time + "年");
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_data_statistics, null);
        backImageView = view.findViewById(R.id.iv_data_static_finish);
        timeTextView = view.findViewById(R.id.tv_ds_time);
        allPersonTextView = view.findViewById(R.id.tv_ds_all_person_num);
        sugarPersonTextView = view.findViewById(R.id.tv_ds_sugar_person_num);
        pressurePersonTextView = view.findViewById(R.id.tv_ds_pressure_num);
        totalPersonTextView = view.findViewById(R.id.tv_ds_total_person_num);
        firstCpv = view.findViewById(R.id.cpv_ds_first);
        allRateTextView = view.findViewById(R.id.tv_ds_sugar_all_rate);
        secondCpv = view.findViewById(R.id.cpv_ds_second);
        emptyRateTextView = view.findViewById(R.id.tv_ds_sugar_empty_rate);

        thirdCpv = view.findViewById(R.id.cpv_ds_third);
        unemptyRateTextView = view.findViewById(R.id.tv_ds_sugar_unempty_rate);
        sugarBarChart = view.findViewById(R.id.bar_ds_finish_sugar);
        pressureCpv = view.findViewById(R.id.cpv_ds_sugar);
        pressureBc = view.findViewById(R.id.bar_ds_finish_pressure);
        pressureRateTextView = view.findViewById(R.id.tv_ds_finish_pressure);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ds_time:
                showTimeWindow();
                break;
            case R.id.iv_data_static_finish:
                finish();
                break;
            default:
                break;
        }
    }

    private void showTimeWindow() {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(getPageContext(), (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y);
            timeTextView.setText(content + "年");
            time = content;
            getData();
        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, false, false, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.black_text))
                //                .isDialog(true)
                //                .setDecorView(allLiner)
                .build();
        //        //设置dialog弹出位置
        //        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        //        params.leftMargin = 0;
        //        params.rightMargin = 0;
        //        ViewGroup contentContainer = timePickerView.getDialogContainerLayout();
        //        contentContainer.setLayoutParams(params);
        //        timePickerView.getDialog().getWindow().setGravity(Gravity.BOTTOM);//可以改成Bottom
        //        timePickerView.getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerView.show();

    }
}
