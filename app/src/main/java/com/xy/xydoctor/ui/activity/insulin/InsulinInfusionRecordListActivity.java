package com.xy.xydoctor.ui.activity.insulin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPStaticUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.InsulinInfusionRecordAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.InsulinDeviceInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:泵输注数据
 */
public class InsulinInfusionRecordListActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private TextView tvNum;
    private TextView tvPlan;
    private TextView tvTime;
    private TextView tvMode;
    private TextView tvRate;
    private TextView tvInjction;

    private TextView tvDayAll;
    private TextView tvInfoBig;
    private TextView tvBaseRate;
    private TextView tvWarning;
    private ListView lvDataInfo;
    private TextView tvNoData;

    private String type = "1";

    private InsulinDeviceInfo deviceInfo;

    private String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("泵输注数据");
        userid = getIntent().getStringExtra("userid");
        containerView().addView(initView());
        initListner();
        getDataInfo();
    }

    private void getDataInfo() {
        String token = SPStaticUtils.getString("token");

        DataManager.geteqinfo(userid, (call, response) -> {
            if (response.code == 200) {
                deviceInfo = (InsulinDeviceInfo) response.object;
                setData();
                getData();
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void setData() {
        tvNum.setText(deviceInfo.getEq_code());
        if (!"暂无".equals(deviceInfo.getEq_code())) {
            tvMode.setText("1".equals(deviceInfo.getModel()) ? "基础模式1" : "基础模式2");
            tvRate.setText("当前基础率" + deviceInfo.getBase_rate());
            tvInjction.setText("已输注" + deviceInfo.getValue());
            tvTime.setText("最近同步时间："+deviceInfo.getUpdatetime());
        } else {
            tvMode.setText("暂无");
            tvRate.setText("暂无");
            tvInjction.setText("暂无");
            tvTime.setText("最近同步时间：" + deviceInfo.getUpdatetime());
        }
    }

    private void getData() {
        DataManager.geteqinsulins(userid, type, (call, response) -> {
            if (response.code == 200) {
                lvDataInfo.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);
                List<InsulinDeviceInfo> list = (List<InsulinDeviceInfo>) response.object;
                InsulinInfusionRecordAdapter deviceListAdapter = new InsulinInfusionRecordAdapter(getPageContext(), list, type);
                lvDataInfo.setAdapter(deviceListAdapter);
            } else if (response.code == 30002) {
                lvDataInfo.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);

            }


        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_infusion_record, null);

        tvNum = view.findViewById(R.id.tv_infusion_info_num);
        tvPlan = view.findViewById(R.id.tv_main_insulin_infusion_plan);
        tvTime = view.findViewById(R.id.tv_main_insulin_time);
        tvMode = view.findViewById(R.id.tv_main_insulin_mode);
        tvRate = view.findViewById(R.id.tv_main_insulin_rate);
        tvInjction = view.findViewById(R.id.tv_main_insulin_injction);

        tvDayAll = view.findViewById(R.id.tv_infusion_info_day_all);
        tvInfoBig = view.findViewById(R.id.tv_infusion_info_big);
        tvBaseRate = view.findViewById(R.id.tv_infusion_info_base_rate);
        tvWarning = view.findViewById(R.id.tv_infusion_info_warning);
        tvNoData = view.findViewById(R.id.tv_insulin_base_info_no_data);
        lvDataInfo = view.findViewById(R.id.lv_insulin_base_info_list);
        return view;
    }

    private void initListner() {

        tvDayAll.setOnClickListener(this);
        tvInfoBig.setOnClickListener(this);
        tvBaseRate.setOnClickListener(this);
        tvWarning.setOnClickListener(this);
        tvPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_insulin_infusion_plan:
                Intent intent = new Intent(getPageContext(), InsulinInfusionPlanListActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
            case R.id.tv_infusion_info_day_all:
                setBg(tvDayAll, tvInfoBig, tvBaseRate, tvWarning);
                type = "1";
                getData();
                break;
            case R.id.tv_infusion_info_big:
                setBg(tvInfoBig, tvDayAll, tvBaseRate, tvWarning);
                type = "2";
                getData();
                break;
            case R.id.tv_infusion_info_base_rate:
                setBg(tvBaseRate, tvInfoBig, tvDayAll, tvWarning);
                type = "3";
                getData();
                break;
            case R.id.tv_infusion_info_warning:
                setBg(tvWarning, tvInfoBig, tvBaseRate, tvDayAll);
                type = "4";
                getData();
                break;
            default:
                break;
        }
    }

    private void setBg(TextView tvCheck, TextView tvUncheck1, TextView tvUncheck2, TextView tvUncheck3) {
        tvCheck.setBackground(getResources().getDrawable(R.drawable.shape_bg_main_90));
        tvCheck.setTextColor(getResources().getColor(R.color.white));
        tvUncheck1.setBackground(null);
        tvUncheck1.setTextColor(getResources().getColor(R.color.green_text_666));
        tvUncheck2.setBackground(null);
        tvUncheck2.setTextColor(getResources().getColor(R.color.green_text_666));
        tvUncheck3.setBackground(null);
        tvUncheck3.setTextColor(getResources().getColor(R.color.green_text_666));

    }
}
