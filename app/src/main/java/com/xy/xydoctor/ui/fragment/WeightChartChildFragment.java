package com.xy.xydoctor.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.horen.chart.linechart.ILineChartData;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.lyd.baselib.util.TurnsUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.TestLineData;
import com.xy.xydoctor.bean.WeightListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.LineChartHelperUtils;
import com.xy.xydoctor.view.NewMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class WeightChartChildFragment extends BaseFragment {
    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.tv_chart_desc)
    TextView tvChartDesc;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weight_chart_child;
    }

    @Override
    protected void init(View rootView) {
        String position = getArguments().getString("position");
        switch (position) {
            case "0":
                getWeightChart(7);
                tvChartDesc.setText("近7次体重记录");
                break;
            case "1":
                getWeightChart(30);
                tvChartDesc.setText("近30次体重记录");
                break;
            case "2":
                getWeightChart(60);
                tvChartDesc.setText("近60次体重记录");
                break;
        }
    }


    /**
     * 获取图表
     *
     * @param num
     */
    private void getWeightChart(int num) {
        HashMap<String, Object> map = new HashMap<>();
        String userId = getArguments().getString("userId");
        map.put("uid", userId);
        map.put("num", num);
        RxHttp.postForm(XyUrl.GET_WEIGHT_CHART)
                .addAll(map)
                .asResponseList(WeightListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<WeightListBean>>() {
                    @Override
                    public void accept(List<WeightListBean> list) throws Exception {
                        lineChart.setVisibility(View.VISIBLE);
                        rlNoData.setVisibility(View.GONE);
                        initLineChart(list);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        lineChart.setVisibility(View.GONE);
                        rlNoData.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void initLineChart(List<WeightListBean> list) {
        //单个柱状图数据
        ArrayList<ILineChartData> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WeightListBean bean = list.get(i);
            double doubleY = TurnsUtils.getDouble(bean.getWeight(), 0);
            entries.add(new TestLineData(doubleY, bean.getDatetime()));
        }
        LineChartHelperUtils lineChartHelper = new LineChartHelperUtils(lineChart);
        //创建一条折线的图表
        lineChartHelper.showSingleLineChart(entries, ColorUtils.getColor(R.color.weight_chart_line), 7);
        //设置圆点
        List<ILineDataSet> sets = lineChart.getData().getDataSets();
        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            set.setDrawCircles(true);
        }
        //设置makeView
        int[] colors = {Color.parseColor("#43B8BC")};
        final NewMarkerView markerView = new NewMarkerView(getPageContext(), R.layout.custom_marker_view_layout, colors, 1);
        markerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) (x);
                if (index < 0) {
                    return;
                }
                WeightListBean weightListBean = list.get(index);
                String weight = weightListBean.getWeight();
                String weightText = String.format(Utils.getApp().getString(R.string.weight_chart), weight);
                weightText += "\n";
                weightText += weightListBean.getAddtime();
                markerView.getTvContent().setText(weightText);
            }
        });
        markerView.setChartView(lineChart);
        lineChart.setMarker(markerView);
        //设置Y轴最大值
        String count = list.get(0).getCount();
        float floatCount = TurnsUtils.getFloat(count, 0);
        lineChart.getAxisLeft().setAxisMaximum(floatCount);
    }
}
