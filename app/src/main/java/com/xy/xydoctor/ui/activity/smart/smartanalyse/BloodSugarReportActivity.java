package com.xy.xydoctor.ui.activity.smart.smartanalyse;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.lyd.baselib.widget.view.MyListView;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.RecentThreeMonthListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SugarReportBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.view.XYMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 血糖报告
 * 作者: LYD
 * 创建日期: 2019/4/23 11:16
 */
public class BloodSugarReportActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    //近3个月糖化血红蛋白情况
    @BindView(R.id.ll_recent_three_month)
    LinearLayout llRecentThreeMonth;
    @BindView(R.id.ll_recent_three_month_no_data)
    LinearLayout llRecentThreeMonthNoData;
    @BindView(R.id.tv_recent_three_month_no_data)
    TextView tvRecentThreeMonthNoData;
    @BindView(R.id.tv_sugar_control_target)
    TextView tvSugarControlTarget;
    @BindView(R.id.lv_blood_sugar_report)
    MyListView lvBloodSugarReport;
    @BindView(R.id.tv_recent_three_month_desc)
    TextView tvRecentThreeMonthDesc;
    //3月份血糖概况
    @BindView(R.id.tv_month_sugar_title)
    TextView tvMonthSugarTitle;
    @BindView(R.id.pie_chart)
    PieChart mPieChart;
    @BindView(R.id.tv_month_sugar_desc)
    TextView tvMonthSugarDesc;
    @BindView(R.id.ll_pie_have_data)
    LinearLayout llPieData;
    @BindView(R.id.img_pie_no_data)
    ImageView imgPieNoData;
    //00空腹血糖趋势图
    @BindView(R.id.tv_empty_sugar_control_target)
    TextView tvEmptySugarControlTarget;
    @BindView(R.id.sc_empty)
    ScatterChart scEmpty;
    @BindView(R.id.tv_empty_desc)
    TextView tvEmptyDesc;
    @BindView(R.id.ll_empty_have_data)
    LinearLayout llEmptyHaveData;
    @BindView(R.id.img_empty_no_data)
    ImageView imgEmptyNoData;
    //01早餐后
    @BindView(R.id.ll_after_break_have_data)
    LinearLayout llAfterBreakHaveData;
    @BindView(R.id.tv_after_break_sugar_control_target)
    TextView tvAfterBreakSugarControlTarget;
    @BindView(R.id.sc_after_break)
    ScatterChart scAfterBreak;
    @BindView(R.id.img_after_break_no_data)
    ImageView imgAfterBreakNoData;
    @BindView(R.id.tv_after_break_desc)
    TextView tvAfterBreakDesc;
    //02午餐前
    @BindView(R.id.ll_before_lunch_have_data)
    LinearLayout llBeforeLunchHaveData;
    @BindView(R.id.tv_before_lunch_sugar_control_target)
    TextView tvBeforeLunchSugarControlTarget;
    @BindView(R.id.sc_before_lunch)
    ScatterChart scBeforeLunch;
    @BindView(R.id.img_before_lunch_no_data)
    ImageView imgBeforeLunchNoData;
    @BindView(R.id.tv_before_lunch_desc)
    TextView tvBeforeLunchDesc;
    //03午餐后
    @BindView(R.id.ll_after_lunch_have_data)
    LinearLayout llAfterLunchHaveData;
    @BindView(R.id.tv_after_lunch_sugar_control_target)
    TextView tvAfterLunchSugarControlTarget;
    @BindView(R.id.sc_after_lunch)
    ScatterChart scAfterLunch;
    @BindView(R.id.img_after_lunch_no_data)
    ImageView imgAfterLunchNoData;
    @BindView(R.id.tv_after_lunch_desc)
    TextView tvAfterLunchDesc;
    //04晚餐前
    @BindView(R.id.ll_before_dinner_have_data)
    LinearLayout llBeforeDinnerHaveData;
    @BindView(R.id.tv_before_dinner_sugar_control_target)
    TextView tvBeforeDinnerSugarControlTarget;
    @BindView(R.id.sc_before_dinner)
    ScatterChart scBeforeDinner;
    @BindView(R.id.img_before_dinner_no_data)
    ImageView imgBeforeDinnerNoData;
    @BindView(R.id.tv_before_dinner_desc)
    TextView tvBeforeDinnerDesc;
    //05晚餐后
    @BindView(R.id.ll_after_dinner_have_data)
    LinearLayout llAfterDinnerHaveData;
    @BindView(R.id.tv_after_dinner_sugar_control_target)
    TextView tvAfterDinnerSugarControlTarget;
    @BindView(R.id.sc_after_dinner)
    ScatterChart scAfterDinner;
    @BindView(R.id.img_after_dinner_no_data)
    ImageView imgAfterDinnerNoData;
    @BindView(R.id.tv_after_dinner_desc)
    TextView tvAfterDinnerDesc;
    //06睡前
    @BindView(R.id.ll_before_sleep_have_data)
    LinearLayout llBeforeSleepHaveData;
    @BindView(R.id.tv_before_sleep_sugar_control_target)
    TextView tvBeforeSleepSugarControlTarget;
    @BindView(R.id.sc_before_sleep)
    ScatterChart scBeforeSleep;
    @BindView(R.id.img_before_sleep_no_data)
    ImageView imgBeforeSleepNoData;
    @BindView(R.id.tv_before_sleep_desc)
    TextView tvBeforeSleepDesc;
    //07凌晨
    @BindView(R.id.ll_night_have_data)
    LinearLayout llNightHaveData;
    @BindView(R.id.tv_night_sugar_control_target)
    TextView tvNightSugarControlTarget;
    @BindView(R.id.sc_night)
    ScatterChart scNight;
    @BindView(R.id.img_night_no_data)
    ImageView imgNightNoData;
    @BindView(R.id.tv_night_desc)
    TextView tvNightDesc;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_blood_sugar_report;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("血糖报告");
        getData();
    }

    private void getData() {
        HashMap map = new HashMap<>();
        map.put("uid", getIntent().getStringExtra("userid"));
        map.put("randtime", getIntent().getStringExtra("time"));
        RxHttp.postForm(XyUrl.GET_PORT_ANALYSIS_BLOOD)
                .addAll(map)
                .asResponse(SugarReportBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SugarReportBean>() {
                    @Override
                    public void accept(SugarReportBean data) throws Exception {
                        setTopDesc(data);
                        setThreeMonth(data);
                        setBloodSugarPie(data);
                        setEmpty(data);
                        setAfterBreakfast(data);
                        setBeforeLunch(data);
                        setAfterLunch(data);
                        setBeforeDinner(data);
                        setAfterDinner(data);
                        stBeforeSleep(data);
                        setNightSugar(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void setNightSugar(SugarReportBean data) {
        String nightSugarControlTarget = "凌晨血糖控制目标：";//凌晨
        List<Double> suijikzmb = data.getSuijikzmb();
        if (suijikzmb != null && suijikzmb.size() == 2) {
            Double left = suijikzmb.get(0);
            Double right = suijikzmb.get(1);
            tvNightSugarControlTarget.setText(nightSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String suijism = data.getSuijism();
        if (data.getSuijizhi() != null && data.getSuijizhi().size() > 0) {
            llNightHaveData.setVisibility(View.VISIBLE);
            imgNightNoData.setVisibility(View.GONE);
            setNightScatterData(data.getSuijizhi());
            String suiji = data.getSuiji();
            String suijizc = data.getSuijizc();
            String suijipg = data.getSuijipg();
            String suijipd = data.getSuijipd();
            double suijipgzhi = data.getSuijipgzhi();
            double suijipdzhi = data.getSuijipdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    suiji + "", "凌晨", suijizc + "", suijipg + "", suijipd + "", suijipgzhi + "", suijipdzhi + "");
            tvNightDesc.setText(emptyBloodSugarDesc + suijism);
        } else {
            llNightHaveData.setVisibility(View.GONE);
            imgNightNoData.setVisibility(View.VISIBLE);
            tvNightDesc.setText(suijism);
        }
    }


    private void stBeforeSleep(SugarReportBean data) {
        String beforeSleepControlTarget = "睡前血糖控制目标：";//睡前
        List<Double> shuiqkzmb = data.getShuiqkzmb();
        if (shuiqkzmb != null && shuiqkzmb.size() == 2) {
            Double left = shuiqkzmb.get(0);
            Double right = shuiqkzmb.get(1);
            tvBeforeSleepSugarControlTarget.setText(beforeSleepControlTarget + left + "-" + right + "mmol/L");
        }
        String shuiqsm = data.getShuiqsm();
        if (data.getShuiqzhi() != null && data.getShuiqzhi().size() > 0) {
            llBeforeSleepHaveData.setVisibility(View.VISIBLE);
            imgBeforeSleepNoData.setVisibility(View.GONE);
            setBeforeSleepScatterData(data.getShuiqzhi());
            String shuiq = data.getShuiq();
            String shuiqzc = data.getShuiqzc();
            String shuiqpg = data.getShuiqpg();
            String shuiqpd = data.getShuiqpd();
            double shuiqpgzhi = data.getShuiqpgzhi();
            double shuiqpdzhi = data.getShuiqpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    shuiq + "", "睡前", shuiqzc + "", shuiqpg + "", shuiqpd + "", shuiqpgzhi + "", shuiqpdzhi + "");
            tvBeforeSleepDesc.setText(emptyBloodSugarDesc + shuiqsm);
        } else {
            llBeforeSleepHaveData.setVisibility(View.GONE);
            imgBeforeSleepNoData.setVisibility(View.VISIBLE);
            tvBeforeSleepDesc.setText(shuiqsm);
        }
    }

    private void setAfterDinner(SugarReportBean data) {
        String afterDinnerSugarControlTarget = "晚餐后血糖控制目标：";//晚餐后
        List<Double> wanchkzmb = data.getWanchkzmb();
        if (wanchkzmb != null && wanchkzmb.size() == 2) {
            Double left = wanchkzmb.get(0);
            Double right = wanchkzmb.get(1);
            tvAfterDinnerSugarControlTarget.setText(afterDinnerSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String wanchsm = data.getWanchsm();
        if (data.getWanchzhi() != null && data.getWanchzhi().size() > 0) {
            llAfterDinnerHaveData.setVisibility(View.VISIBLE);
            imgAfterDinnerNoData.setVisibility(View.GONE);
            setAfterDinnerScatterData(data.getWanchzhi());
            String wanch = data.getWanch();
            String wanchzc = data.getWanchzc();
            String wanchpg = data.getWanchpg();
            String wanchpd = data.getWanchpd();
            double wanchpgzhi = data.getWanchpgzhi();
            double wanchpdzhi = data.getWanchpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wanch + "", "晚饭后", wanchzc + "", wanchpg + "", wanchpd + "", wanchpgzhi + "", wanchpdzhi + "");
            tvAfterDinnerDesc.setText(emptyBloodSugarDesc + wanchsm);
        } else {
            llAfterDinnerHaveData.setVisibility(View.GONE);
            imgAfterDinnerNoData.setVisibility(View.VISIBLE);
            tvAfterDinnerDesc.setText(wanchsm);
        }
    }


    private void setBeforeDinner(SugarReportBean data) {
        String beforeDinnerSugarControlTarget = "晚餐前血糖控制目标：";//晚餐前
        List<Double> wancqkzmb = data.getWancqkzmb();
        if (wancqkzmb != null && wancqkzmb.size() == 2) {
            Double left = wancqkzmb.get(0);
            Double right = wancqkzmb.get(1);
            tvBeforeDinnerSugarControlTarget.setText(beforeDinnerSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String wancqsm = data.getWancqsm();
        if (data.getWancqzhi() != null && data.getWancqzhi().size() > 0) {
            llBeforeDinnerHaveData.setVisibility(View.VISIBLE);
            imgBeforeDinnerNoData.setVisibility(View.GONE);
            setBeforeDinnerScatterData(data.getWancqzhi());
            String wancq = data.getWancq();
            String wancqzc = data.getWancqzc();
            String wancqpg = data.getWancqpg();
            String wancqpd = data.getWancqpd();
            double wancqpgzhi = data.getWancqpgzhi();
            double wancqpdzhi = data.getWancqpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wancq + "", "晚饭前", wancqzc + "", wancqpg + "", wancqpd + "", wancqpgzhi + "", wancqpdzhi + "");
            tvBeforeDinnerDesc.setText(emptyBloodSugarDesc + wancqsm);
        } else {
            llBeforeDinnerHaveData.setVisibility(View.GONE);
            imgBeforeDinnerNoData.setVisibility(View.VISIBLE);
            tvBeforeDinnerDesc.setText(wancqsm);
        }
    }

    private void setAfterLunch(SugarReportBean data) {
        String afterLunchSugarControlTarget = "午餐后血糖控制目标：";//午餐后
        List<Double> wuchkzmb = data.getWuchkzmb();
        if (wuchkzmb != null && wuchkzmb.size() == 2) {
            Double left = wuchkzmb.get(0);
            Double right = wuchkzmb.get(1);
            tvAfterLunchSugarControlTarget.setText(afterLunchSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String wuchsm = data.getWuchsm();
        if (data.getWuchzhi() != null && data.getWuchzhi().size() > 0) {
            llAfterLunchHaveData.setVisibility(View.VISIBLE);
            imgAfterLunchNoData.setVisibility(View.GONE);
            setAfterLunchScatterData(data.getWuchzhi());
            String wuch = data.getWuch();
            String wuchzc = data.getWuchzc();
            String wuchpg = data.getWuchpg();
            String wuchpd = data.getWuchpd();
            double wuchpgzhi = data.getWuchpgzhi();
            double wuchpdzhi = data.getWuchpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wuch + "", "午餐后", wuchzc + "", wuchpg + "", wuchpd + "", wuchpgzhi + "", wuchpdzhi + "");
            tvAfterLunchDesc.setText(emptyBloodSugarDesc + wuchsm);
        } else {
            llAfterLunchHaveData.setVisibility(View.GONE);
            imgAfterLunchNoData.setVisibility(View.VISIBLE);
            tvAfterLunchDesc.setText(wuchsm);
        }
    }

    private void setBeforeLunch(SugarReportBean data) {
        String beforeLunchControlTarget = "午餐前血糖控制目标：";//午餐前
        List<Double> wucqkzmb = data.getWucqkzmb();
        if (wucqkzmb != null && wucqkzmb.size() == 2) {
            Double left = wucqkzmb.get(0);
            Double right = wucqkzmb.get(1);
            tvBeforeLunchSugarControlTarget.setText(beforeLunchControlTarget + left + "-" + right + "mmol/L");
        }
        //图标
        String wucqsm = data.getWucqsm();
        if (data.getWucqzhi() != null && data.getWucqzhi().size() > 0) {
            llBeforeLunchHaveData.setVisibility(View.VISIBLE);
            imgBeforeLunchNoData.setVisibility(View.GONE);
            setBeforeLunchScatterData(data.getWucqzhi());
            int wucq = data.getWucq();
            String wucqzc = data.getWucqzc();
            int wucqpg = data.getWucqpg();
            String wucqpd = data.getWucqpd();
            double wucqpgzhi = data.getWucqpgzhi();
            double wucqpdzhi = data.getWucqpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wucq + "", "午餐前", wucqzc + "", wucqpg + "", wucqpd + "", wucqpgzhi + "", wucqpdzhi + "");
            tvBeforeLunchDesc.setText(emptyBloodSugarDesc + wucqsm);
        } else {
            llBeforeLunchHaveData.setVisibility(View.GONE);
            imgBeforeLunchNoData.setVisibility(View.VISIBLE);
            tvBeforeLunchDesc.setText(wucqsm);
        }
    }


    private void setAfterBreakfast(SugarReportBean data) {
        //控制目标
        String afterBreakfastSugarControlTarget = "早餐后血糖控制目标：";//早餐后
        List<Double> zch2kzmb = data.getZch2kzmb();
        if (zch2kzmb != null && zch2kzmb.size() == 2) {
            Double left = zch2kzmb.get(0);
            Double right = zch2kzmb.get(1);
            tvAfterBreakSugarControlTarget.setText(afterBreakfastSugarControlTarget + left + "-" + right + "mmol/L");
        }
        //图表
        String zch2sm = data.getZch2sm();//空腹说明
        if (data.getZch2zhi() != null && data.getZch2zhi().size() > 0) {
            llAfterBreakHaveData.setVisibility(View.VISIBLE);
            imgAfterBreakNoData.setVisibility(View.GONE);
            setAfterBreakfastScatterData(data.getZch2zhi());
            //说明
            String zch2 = data.getZch2();
            String zch2zc = data.getZch2zc();
            String zch2pg = data.getZch2pg();
            String zch2pd = data.getZch2pd();
            double zch2pgzhi = data.getZch2pgzhi();
            double zch2pdzhi = data.getZch2pdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    zch2, "早餐后", zch2zc, zch2pg, zch2pd, zch2pgzhi + "", zch2pdzhi + "");
            tvAfterBreakDesc.setText(emptyBloodSugarDesc + zch2sm);
        } else {
            llAfterBreakHaveData.setVisibility(View.GONE);
            imgAfterBreakNoData.setVisibility(View.VISIBLE);
            tvAfterBreakDesc.setText(zch2sm);
        }
    }

    private void setEmpty(SugarReportBean data) {
        String emptySugarControlTarget = "空腹血糖控制目标：";//空腹
        //空腹控制目标
        List<Double> kongfukzmb = data.getKongfukzmb();
        if (kongfukzmb != null && kongfukzmb.size() == 2) {
            Double left = kongfukzmb.get(0);
            Double right = kongfukzmb.get(1);
            tvEmptySugarControlTarget.setText(emptySugarControlTarget + left + "-" + right + "mmol/L");
        }
        //图表
        String kongfusm = data.getKongfusm();//空腹说明
        if (data.getKongfuzhi() != null && data.getKongfuzhi().size() > 0) {
            llEmptyHaveData.setVisibility(View.VISIBLE);
            imgEmptyNoData.setVisibility(View.GONE);
            setEmptyScatterData(data.getKongfuzhi());
            //说明
            String kongfu = data.getKongfu();//空腹测量总次数
            String kongfuzc = data.getKongfuzc();//空腹正常次数
            String kongfupg = data.getKongfupg();//空腹偏高次数
            String kongfupd = data.getKongfupd();//空腹偏低次数
            double kongfupgzhi = data.getKongfupgzhi();//空腹偏高值
            double kongfupdzhi = data.getKongfupdzhi();//空腹偏低值
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    kongfu, "空腹", kongfuzc, kongfupg, kongfupd, kongfupgzhi + "", kongfupdzhi + "");
            tvEmptyDesc.setText(emptyBloodSugarDesc + kongfusm);
        } else {
            llEmptyHaveData.setVisibility(View.GONE);
            imgEmptyNoData.setVisibility(View.VISIBLE);
            tvEmptyDesc.setText(kongfusm);
        }
    }

    private void setBloodSugarPie(SugarReportBean data) {
        String month = data.getMonth();
        tvMonthSugarTitle.setText(month + "月份血糖概况");
        int xuezci = data.getXuezci();//总次数
        int xuezc = data.getXuezc();//正常
        int xuepg = data.getXuepg();//偏高
        int xuepd = data.getXuepd();//偏低
        String xuesm = data.getXuesm();//说明
        String monthSugarDesc = String.format(Utils.getApp().getString(R.string.month_sugar_desc), xuezci + "", xuezc + "", xuepg + "", xuepd + "");
        tvMonthSugarDesc.setText(monthSugarDesc + xuesm);
        if (xuezci > 0) {
            llPieData.setVisibility(View.VISIBLE);
            imgPieNoData.setVisibility(View.GONE);
            setPieChart(xuezc, xuepg, xuepd);
        } else {
            llPieData.setVisibility(View.GONE);
            imgPieNoData.setVisibility(View.VISIBLE);
        }
    }

    private void setThreeMonth(SugarReportBean data) {
        String diabetesleim = data.getDiabetesleim();//糖尿病类型
        int num = data.getNum();//共检测糖化血红蛋白次数
        String tshuom = data.getTshuom();//糖化建议说明
        if (num > 0) {
            llRecentThreeMonthNoData.setVisibility(View.GONE);
            llRecentThreeMonth.setVisibility(View.VISIBLE);
            String thmb = data.getThmb();//糖化控制目标
            List<SugarReportBean.DanbaiBean> listDanBai = data.getDanbai();//糖化血红蛋白列表
            int dabiao = data.getDabiao();//达标次数
            int nobiao = data.getNobiao();//未达标次数
            tvSugarControlTarget.setText("糖化控制目标：" + thmb);
            String recentThreeMonthDesc = String.format(Utils.getApp().getString(R.string.sugar_control_target), num + "", dabiao + "", nobiao + "");
            tvRecentThreeMonthDesc.setText(recentThreeMonthDesc + tshuom);
            if (listDanBai != null && listDanBai.size() > 0) {
                lvBloodSugarReport.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_lv_blood_sugar_report, null));
                lvBloodSugarReport.setAdapter(new RecentThreeMonthListAdapter(getPageContext(), R.layout.item_lv_blood_sugar_report, listDanBai, diabetesleim));
            }
        } else {
            llRecentThreeMonthNoData.setVisibility(View.VISIBLE);
            llRecentThreeMonth.setVisibility(View.GONE);
            tvRecentThreeMonthNoData.setText(tshuom);
        }
    }

    private void setTopDesc(SugarReportBean data) {
        tvName.setText(data.getNickname() + ",您好!");
        String year = data.getYear();
        String month = data.getMonth();
        String diabetesleim = data.getDiabetesleim();//糖尿病类型
        String begintime = data.getBegintime();
        String endtime = data.getEndtime();
        String firstDesc = String.format(Utils.getApp().getString(R.string.sugar_desc), year, month, diabetesleim, begintime, endtime);
        tvDesc.setText(firstDesc);
    }

    /**
     * 凌晨散点图
     *
     * @param suijizhi
     */
    private void setNightScatterData(List<List<Integer>> suijizhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < suijizhi.size(); i++) {
            List<Integer> integers = suijizhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scNight.getDescription().setEnabled(false);
        scNight.getLegend().setEnabled(false);
        //支持缩放和拖动
        scNight.setTouchEnabled(true);
        scNight.setDragEnabled(false);
        scNight.setScaleEnabled(false);
        scNight.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scNight);
        scNight.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scNight.getAxisLeft();
        scNight.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scNight.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scNight.setData(data);
        scNight.invalidate();
    }

    /**
     * 睡前散点图
     *
     * @param shuiqzhi
     */
    private void setBeforeSleepScatterData(List<List<Integer>> shuiqzhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < shuiqzhi.size(); i++) {
            List<Integer> integers = shuiqzhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scBeforeSleep.getDescription().setEnabled(false);
        scBeforeSleep.getLegend().setEnabled(false);
        //支持缩放和拖动
        scBeforeSleep.setTouchEnabled(true);
        scBeforeSleep.setDragEnabled(false);
        scBeforeSleep.setScaleEnabled(false);
        scBeforeSleep.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scBeforeSleep);
        scBeforeSleep.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scBeforeSleep.getAxisLeft();
        scBeforeSleep.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scBeforeSleep.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scBeforeSleep.setData(data);
        scBeforeSleep.invalidate();
    }

    /**
     * 晚餐后散点图
     *
     * @param wanchzhi
     */
    private void setAfterDinnerScatterData(List<List<Integer>> wanchzhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < wanchzhi.size(); i++) {
            List<Integer> integers = wanchzhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scAfterDinner.getDescription().setEnabled(false);
        scAfterDinner.getLegend().setEnabled(false);
        //支持缩放和拖动
        scAfterDinner.setTouchEnabled(true);
        scAfterDinner.setDragEnabled(false);
        scAfterDinner.setScaleEnabled(false);
        scAfterDinner.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scAfterDinner);
        scAfterDinner.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scAfterDinner.getAxisLeft();
        scAfterDinner.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scAfterDinner.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scAfterDinner.setData(data);
        scAfterDinner.invalidate();
    }

    /**
     * 晚餐前散点图
     *
     * @param wancqzhi
     */
    private void setBeforeDinnerScatterData(List<List<Integer>> wancqzhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < wancqzhi.size(); i++) {
            List<Integer> integers = wancqzhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scBeforeDinner.getDescription().setEnabled(false);
        scBeforeDinner.getLegend().setEnabled(false);
        //支持缩放和拖动
        scBeforeDinner.setTouchEnabled(true);
        scBeforeDinner.setDragEnabled(false);
        scBeforeDinner.setScaleEnabled(false);
        scBeforeDinner.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scBeforeDinner);
        scBeforeDinner.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scBeforeDinner.getAxisLeft();
        scBeforeDinner.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scBeforeDinner.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scBeforeDinner.setData(data);
        scBeforeDinner.invalidate();
    }

    /**
     * 午餐后散点图
     *
     * @param wuchzhi
     */
    private void setAfterLunchScatterData(List<List<Integer>> wuchzhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < wuchzhi.size(); i++) {
            List<Integer> integers = wuchzhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scAfterLunch.getDescription().setEnabled(false);
        scAfterLunch.getLegend().setEnabled(false);
        //支持缩放和拖动
        scAfterLunch.setTouchEnabled(true);
        scAfterLunch.setDragEnabled(false);
        scAfterLunch.setScaleEnabled(false);
        scAfterLunch.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scAfterLunch);
        scAfterLunch.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scAfterLunch.getAxisLeft();
        scAfterLunch.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scAfterLunch.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scAfterLunch.setData(data);
        scAfterLunch.invalidate();
    }

    /**
     * 午餐前散点图
     *
     * @param wucqzhi
     */
    private void setBeforeLunchScatterData(List<List<Integer>> wucqzhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < wucqzhi.size(); i++) {
            List<Integer> integers = wucqzhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scBeforeLunch.getDescription().setEnabled(false);
        scBeforeLunch.getLegend().setEnabled(false);
        //支持缩放和拖动
        scBeforeLunch.setTouchEnabled(true);
        scBeforeLunch.setDragEnabled(false);
        scBeforeLunch.setScaleEnabled(false);
        scBeforeLunch.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scBeforeLunch);
        scBeforeLunch.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scBeforeLunch.getAxisLeft();
        scBeforeLunch.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scBeforeLunch.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scBeforeLunch.setData(data);
        scBeforeLunch.invalidate();
    }

    /**
     * 设置早餐后散点图
     *
     * @param zch2zhi
     */
    private void setAfterBreakfastScatterData(List<List<Integer>> zch2zhi) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < zch2zhi.size(); i++) {
            List<Integer> integers = zch2zhi.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scAfterBreak.getDescription().setEnabled(false);
        scAfterBreak.getLegend().setEnabled(false);
        //支持缩放和拖动
        scAfterBreak.setTouchEnabled(true);
        scAfterBreak.setDragEnabled(false);
        scAfterBreak.setScaleEnabled(false);
        scAfterBreak.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scAfterBreak);
        scAfterBreak.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scAfterBreak.getAxisLeft();
        scAfterBreak.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scAfterBreak.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scAfterBreak.setData(data);
        scAfterBreak.invalidate();
    }

    /**
     * 设置空腹散点图
     *
     * @param emptyScatterDataList
     */
    private void setEmptyScatterData(List<List<Integer>> emptyScatterDataList) {
        List<Integer> listX = new ArrayList<>();//X轴:时间轴
        List<Integer> listY = new ArrayList<>();//Y轴:值轴
        for (int i = 0; i < emptyScatterDataList.size(); i++) {
            List<Integer> integers = emptyScatterDataList.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Integer integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        //禁用标签
        scEmpty.getDescription().setEnabled(false);
        scEmpty.getLegend().setEnabled(false);
        //支持缩放和拖动
        scEmpty.setTouchEnabled(true);
        scEmpty.setDragEnabled(false);
        scEmpty.setScaleEnabled(false);
        scEmpty.setPinchZoom(false);
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(scEmpty);
        scEmpty.setMarker(mv);
        //y轴
        YAxis yAxisLeft = scEmpty.getAxisLeft();
        scEmpty.getAxisRight().setEnabled(false);
        yAxisLeft.setAxisMinimum(0);//设置最小值
        yAxisLeft.setAxisMaximum(12.2f);//设置最大值
        yAxisLeft.setGranularity(1f);//网格线条间距
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = scEmpty.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        xl.setAxisMinimum(1);//设置最小值
        xl.setAxisMaximum(31f);//设置最大值
        //创建一个数据集,并给它一个类型
        ArrayList<Entry> listData = new ArrayList<>();
        for (int i = 0; i < listX.size(); i++) {
            listData.add(new Entry(listX.get(i), listY.get(i)));
        }
        ScatterDataSet set = new ScatterDataSet(listData, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_blue));
        set.setScatterShapeHoleRadius(3f);
        set.setScatterShapeSize(8f);
        //顶点显示值
        set.setDrawValues(false);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        scEmpty.setData(data);
        scEmpty.invalidate();
    }

    /**
     * 设置饼图
     *
     * @param xuezc 正常次数
     * @param xuepg 偏高
     * @param xuepd 偏低
     */
    private void setPieChart(int xuezc, int xuepg, int xuepd) {
        //以百分比为单位
        mPieChart.setUsePercentValues(true);
        //描述
        mPieChart.getDescription().setEnabled(false);
        //图例
        mPieChart.getLegend().setEnabled(false);
        //圆环距离屏幕上下左右的距离
        //mPieChart.setExtraOffsets(20, 0, 20, 0);
        //是否将饼心绘制空心(ture:环形图 false:饼图)
        mPieChart.setDrawHoleEnabled(true);
        //设置饼图中心圆的颜色
        mPieChart.setHoleColor(Color.WHITE);
        //设置饼图中心圆的边颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);
        //设置饼图中心圆的边的透明度，0--255 0表示完全透明 255完全不透明
        mPieChart.setTransparentCircleAlpha(255);
        //设置饼图中心圆的半径
        mPieChart.setHoleRadius(58f);
        //设置饼图中心圆的边的半径
        mPieChart.setTransparentCircleRadius(61f);
        //设置是否可以触摸旋转
        mPieChart.setRotationEnabled(true);
        //设置是否点击后将对应的区域进行突出
        mPieChart.setHighlightPerTapEnabled(false);
        //标签颜色
        mPieChart.setEntryLabelColor(ColorUtils.getColor(R.color.black_text));
        mPieChart.setEntryLabelTextSize(12f);
        mPieChart.setRotationAngle(45f);//初始旋转90
        //设置数据//设置颜色
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if (0 == xuezc) {

        } else {
            colors.add(ColorUtils.getColor(R.color.sugar_normal));
            entries.add(new PieEntry(xuezc, xuezc + "次血糖正常"));
        }
        if (0 == xuepg) {

        } else {
            colors.add(ColorUtils.getColor(R.color.sugar_high));
            entries.add(new PieEntry(xuepg, xuepg + "次血糖偏高"));
        }
        if (0 == xuepd) {

        } else {
            colors.add(ColorUtils.getColor(R.color.sugar_low));
            entries.add(new PieEntry(xuepd, xuepd + "次血糖偏低"));
        }
        //添加数据
        PieDataSet dataSet = new PieDataSet(entries, "");
        //设置颜色
        dataSet.setColors(colors);
        //设置饼图每个区域的间隔
        dataSet.setSliceSpace(5f);
        //设置出界
        dataSet.setValueLinePart1OffsetPercentage(60.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //添加数据
        PieData pieData = new PieData(dataSet);
        //自定义饼图每个区域显示的值
        pieData.setValueFormatter(new PercentFormatter());
        //设置值的大小
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(ColorUtils.getColor(R.color.black_text));
        //设置数据
        mPieChart.setData(pieData);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }


}
