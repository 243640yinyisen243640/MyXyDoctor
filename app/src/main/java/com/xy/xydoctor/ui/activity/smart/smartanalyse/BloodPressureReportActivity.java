package com.xy.xydoctor.ui.activity.smart.smartanalyse;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.BPReportBean;
import com.xy.xydoctor.bean.BloodPressure;
import com.xy.xydoctor.bean.LineChartEntity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.view.NewMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 血压分析报告
 * 作者: LYD
 * 创建日期: 2019/4/19 9:53
 */
public class BloodPressureReportActivity extends BaseActivity {
    @BindView(R.id.new_lineChart)
    LineChart lineChart;
    @BindView(R.id.iv_up)
    ImageView ivUp;
    @BindView(R.id.tv_up_num)
    TextView tvMax;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.rlll)
    RelativeLayout rlll;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.tv_up_down)
    TextView tvMin;
    @BindView(R.id.rlll_down)
    RelativeLayout rlllDown;
    @BindView(R.id.tv_report_one)
    TextView tvCount;
    @BindView(R.id.rl_report_one)
    RelativeLayout rlReportOne;
    @BindView(R.id.tv_report_two)
    TextView tvHigh;
    @BindView(R.id.rl_report_two)
    RelativeLayout rlReportTwo;
    @BindView(R.id.tv_report_three)
    TextView tvLow;
    @BindView(R.id.rl_report_three)
    RelativeLayout rlReportThree;
    @BindView(R.id.tv_report_content)
    TextView tvReport;
    private DecimalFormat mFormat;
    private List<BloodPressure> bloodPressureList;
    private StringBuilder sb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blood_report;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("血压分析报告");
        sb = new StringBuilder();
        getData();
        getReport();
    }

    /**
     * 获取血压报告
     */
    private void getReport() {
        String userid = getIntent().getStringExtra("userid");
        HashMap map = new HashMap<>();
        map.put("uid", userid);
        map.put("starttime", getIntent().getStringExtra("time"));
        RxHttp.postForm(XyUrl.GET_INDEX_BLOOD_REPORTBP)
                .addAll(map)
                .asResponse(BPReportBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<BPReportBean>() {
                    @Override
                    public void accept(BPReportBean bpReport) throws Exception {
                        if (!bpReport.getCount().isEmpty() && !bpReport.getCount().equals("0")) {
                            tvCount.setText(bpReport.getCount());
                            tvHigh.setText(bpReport.getHigh());
                            tvLow.setText(bpReport.getLow());
                            tvMax.setText(bpReport.getMaxsbp());
                            tvMin.setText(bpReport.getMindbp());
                            sb.append("您近1月共测量血压" + bpReport.getCount() + "次，平均每日" + bpReport.getAvg() + "次。收缩压" + bpReport.getSbpcount() + "次，收缩压平均值为" + bpReport.getSbpavg() + "mmHg，舒张压" + bpReport.getDbpcount() + "次，舒张压平均值为" + bpReport.getDbpavg() + "mmHg，脉压差为" + bpReport.getDiff() + "mmHg，测量时间主要集中在" + bpReport.getSurveydate() + "。" +
                                    "您本段时间血压监测情况：正常次数" + bpReport.getNormal() + "次；异常总共" + bpReport.getExcep() + "次；");
                            if (!bpReport.getHigh().isEmpty() && !bpReport.getHigh().equals("0")) {
                                sb.append("血压偏高" + bpReport.getHigh() + "次，血压偏高主要集中在" + bpReport.getHighdate() + "；");
                            }
                            if (!bpReport.getLow().isEmpty() && !bpReport.getLow().equals("0")) {
                                sb.append("血压偏低" + bpReport.getLow() + "次，血压偏低主要集中在" + bpReport.getLowdate() + "；");
                            }
                            sb.append("您的血压控制率" + bpReport.getFactor() + "，评价为：" + bpReport.getRank() + "。" +
                                    "您的血压控制水平" + bpReport.getRankcontent() + "。");
                            if (!bpReport.getHighcontent().isEmpty()) {
                                sb.append(bpReport.getHighcontent());
                            }
                        }
                        if (!bpReport.getContent().isEmpty()) {
                            sb.append(bpReport.getContent() + "。");
                        }
                        tvReport.setText(sb.toString());
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 获取血压数据
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("type", 3);
        map.put("starttime", getIntent().getStringExtra("time"));
        map.put("endtime", "");
        map.put("uid", getIntent().getStringExtra("userid"));
        RxHttp.postForm(XyUrl.GET_INDEX_BLOOD_INDEX).addAll(map)
                .asResponseList(BloodPressure.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<BloodPressure>>() {
                    @Override
                    public void accept(List<BloodPressure> bloodPressures) throws Exception {
                        bloodPressureList = bloodPressures;
                        showBpLine();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void showBpLine() {
        List<Entry> values1 = new ArrayList<>();
        List<Entry> values2 = new ArrayList<>();
        BloodPressure bloodPressure;
        for (int i = 0; i < bloodPressureList.size(); i++) {
            bloodPressure = bloodPressureList.get(i);
            String amount = bloodPressure.getSystolic() + "";
            float f;
            try {
                f = Float.parseFloat(amount);
            } catch (Exception e) {
                e.printStackTrace();
                f = 0;
            }
            Entry entry = new Entry(i + 1, f);
            values1.add(entry);
        }
        for (int i = 0; i < bloodPressureList.size(); i++) {
            bloodPressure = bloodPressureList.get(i);
            String amount = bloodPressure.getDiastole() + "";
            float f;
            try {
                f = Float.parseFloat(amount);
            } catch (Exception e) {
                e.printStackTrace();
                f = 0;
            }
            Entry entry = new Entry(i + 1, f);
            values2.add(entry);
        }
        int[] callDurationColors = {Color.parseColor("#45A2FF"), Color.parseColor("#5fd1cc")};
        String thisYear = "";
        if (bloodPressureList.size() > 0) {
            thisYear = "低压";
        }
        String lastYear = "";
        if (bloodPressureList.size() > 0) {
            lastYear = "高压";
        }
        String[] labels = new String[]{thisYear, lastYear};
        updateLineChart(lineChart, callDurationColors, values1, values2, labels);
    }

    /**
     * 双平滑曲线传入数据，添加markview，添加实体类单位
     *
     * @param lineChart
     * @param colors
     * @param values2
     * @param values1
     * @param labels
     */
    private void updateLineChart(LineChart lineChart, int[] colors, List<Entry> values2, List<Entry> values1, final String[] labels) {
        mFormat = new DecimalFormat("#,###.##");//格式化数值（取整数部分）
        List<Entry>[] entries = new ArrayList[2];
        entries[0] = values1;
        entries[1] = values2;
        LineChartEntity lineChartEntity = new LineChartEntity(lineChart, entries, labels, colors, Color.parseColor("#999999"), 12f);
        lineChartEntity.drawCircle(true);
        lineChartEntity.toggleFilled(null, null, true);

        lineChartEntity.setLineMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineChartEntity.initLegend(Legend.LegendForm.CIRCLE, 12f, Color.parseColor("#999999"));
        lineChartEntity.updateLegendOrientation(Legend.LegendVerticalAlignment.TOP, Legend.LegendHorizontalAlignment.RIGHT, Legend.LegendOrientation.HORIZONTAL);


        //x坐标轴设置
        XAxis xAxis = lineChart.getXAxis();
        //x轴对齐位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏网格线
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        final NewMarkerView markerView = new NewMarkerView(this, R.layout.custom_marker_view_layout, colors, 2);
        markerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) (x);
                if (index < 0) {
                    return;
                }
                if (index > bloodPressureList.size()) {
                    bloodPressureList.size();
                    return;
                }
                String textTemp = "";
                textTemp += bloodPressureList.get(index - 1).getDatetime() + "  " + mFormat.format(Float.parseFloat(bloodPressureList.get(index - 1).getSystolic() + "")) + "";
                if (index <= bloodPressureList.size()) {
                    textTemp += "\n";
                    textTemp += bloodPressureList.get(index - 1).getDatetime() + "  " + mFormat.format(Float.parseFloat(bloodPressureList.get(index - 1).getDiastole() + "")) + "";
                }
                markerView.getTvContent().setText(textTemp);
            }
        });
        lineChartEntity.setMarkView(markerView);
        lineChart.getData().setDrawValues(false);


    }
}
