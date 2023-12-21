package com.xy.xydoctor.ui.activity.insulin;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.InsulinInfusionPlanAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.PlanInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.PickerUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:当前下发方案次数
 */
public class InsulinInfusionCurrentListActivity extends XYSoftUIBaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private InsulinInfusionPlanAdapter adapter;
    private List<PlanInfo> infoList = new ArrayList<>();
    private List<PlanInfo> infoListTemp = new ArrayList<>();
    private int page = 1;

    private TextView tvLargeDose;
    private TextView tvBasalRate;

    private LinearLayout llStartTime;
    private TextView tvStartTime;
    private LinearLayout llEndTime;
    private TextView tvEndTime;
    private TextView tvstate;
    private LinearLayout llLast;
    private TextView tvNoData;
    /**
     * 1大剂量 2基础率
     */
    private String type = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("当前下发方案次数");
        containerView().addView(initView());
        initListner();
        initReFresh();
        getData();
    }

    private void initReFresh() {
        //下拉刷新
        smartRefreshLayout.setEnableRefresh(false);
        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
    }

    private void initListner() {
        tvLargeDose.setOnClickListener(this);
        tvBasalRate.setOnClickListener(this);
        llStartTime.setOnClickListener(this);
        llEndTime.setOnClickListener(this);
        tvstate.setOnClickListener(this);
    }


    private void getData() {
        Call<String> requestCall = DataManager.getInjectionHistoryList("", 1, (call, response) -> {
            if (200 == response.code) {
                if (page == 1) {
                    infoList.clear();
                }
                infoListTemp = (List<PlanInfo>) response.object;
                if (infoListTemp != null && infoListTemp.size() > 0) {
                    if (infoListTemp.size() < 10) {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        smartRefreshLayout.finishLoadMore();
                    }
                    infoList.addAll(infoListTemp);
                    adapter.notifyDataSetChanged();
                }

            } else {
                ToastUtils.showShort("网络连接不可用，请稍后重试！");
            }
        }, (call, t) -> {
            ToastUtils.showShort("网络连接不可用，请稍后重试！");
        });
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_infusion_plan, null);

        tvLargeDose = view.findViewById(R.id.tv_infusion_current_large_dose);
        tvBasalRate = view.findViewById(R.id.tv_infusion_current_basal_rate);

        llStartTime = view.findViewById(R.id.ll_data_infusion_current_start_time);
        tvStartTime = view.findViewById(R.id.tv_data_infusion_current_start_time);
        llEndTime = view.findViewById(R.id.ll_data_infusion_current_end_time);
        tvEndTime = view.findViewById(R.id.tv_data_infusion_current_end_time);
        tvstate = view.findViewById(R.id.tv_data_infusion_current_state);

        smartRefreshLayout = view.findViewById(R.id.srl_infusion_current);
        recyclerView = view.findViewById(R.id.rv_infusion_current);
        llLast = view.findViewById(R.id.ll_insulin_infusion_current_last);
        tvNoData = view.findViewById(R.id.tv_insulin_current_no_data);
        adapter = new InsulinInfusionPlanAdapter(getPageContext(), infoList, type, new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {

            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getPageContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_infusion_current_large_dose:
                setBg(tvLargeDose, tvBasalRate);
                break;
            case R.id.tv_infusion_current_basal_rate:
                setBg(tvBasalRate, tvLargeDose);
                break;
            case R.id.ll_data_infusion_current_start_time:
                showTimeWindow(1);
                break;
            case R.id.ll_data_infusion_current_end_time:
                showTimeWindow(2);
                break;
            case R.id.tv_data_infusion_current_state:
                showBottomSelect();
                break;
            default:
                break;
        }
    }

    private void showBottomSelect() {
        String[] sexStr = getResources().getStringArray(R.array.insulin_state_choose);
        List<String> listStr = Arrays.asList(sexStr);
        PickerUtils.showOptionPosition(getPageContext(), (content, position) -> {
            tvstate.setText(content);
            //保存style
        }, listStr);
    }

    /**
     * 时间选择器
     *
     * @param type 开始时间  结束时间
     */
    private void showTimeWindow(int type) {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONDAY) - 1;
        startDate.set(currentYear, currentMonth, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(getPageContext(), (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y_M_D);
            if (1 == type) {
                tvStartTime.setText(content);

            } else {
                tvEndTime.setText(content);

            }

        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.black_text))
                .isDialog(true)

                //                .setDecorView(allLiner)
                .build();
        //设置dialog弹出位置
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.leftMargin = 0;
        params.rightMargin = 0;
        ViewGroup contentContainer = timePickerView.getDialogContainerLayout();
        contentContainer.setLayoutParams(params);
        timePickerView.getDialog().getWindow().setGravity(Gravity.BOTTOM);//可以改成Bottom
        timePickerView.getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerView.show();

    }

    /**
     * 比较两个时间
     *
     * @param starTime  开始时间
     * @param endString 结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public static boolean compareTwoTime(String starTime, String endString) {
        boolean isMoreThan = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DataFormatManager.TIME_FORMAT_Y_M_D);
        try {
            Date startData = dateFormat.parse(starTime);
            Date endData = dateFormat.parse(endString);
            long diff = endData.getTime() - startData.getTime();
            if (diff >= 0) {
                isMoreThan = true;
            } else {
                isMoreThan = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isMoreThan;

    }

    private void setBg(TextView tvCheck, TextView tvUncheck1) {
        tvCheck.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.shape_red_30_3_2);
        tvCheck.setTextSize(18);
        tvCheck.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvUncheck1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvUncheck1.setTextSize(16);
        tvUncheck1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

}
