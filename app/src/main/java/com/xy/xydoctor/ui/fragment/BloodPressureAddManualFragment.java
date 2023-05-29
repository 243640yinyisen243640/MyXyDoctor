package com.xy.xydoctor.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.lsp.RulerView;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.xy.xydoctor.R;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:手动添加血压
 * 作者: LYD
 * 创建日期: 2019/7/8 16:12
 */
public class BloodPressureAddManualFragment extends BaseFragment {
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.ruler_view_high)
    RulerView rulerViewHigh;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.ruler_view_low)
    RulerView rulerViewLow;

//    @BindView(R.id.ruler_view_low_bmi)
//    RulerView rulerViewLowBmi;
//    @BindView(R.id.tv_low_bmi)
//    TextView bmiDataTextView;
//    @BindView(R.id.ll_bmi_record)
//    LinearLayout bmiDataLinearLayout;

    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;
    @BindView(R.id.tv_time)
    TextView tvTime;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blood_pressure_add_manual;
    }

    @Override
    protected void init(View rootView) {
        initRulerListener();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvTime.setText(nowString);
    }


    private void initRulerListener() {
        rulerViewHigh.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvHigh.setText(floatStringToIntString(result));
                EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.BLOOD_PRESSURE_ADD_HIGH, result));
            }
        });
        rulerViewLow.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvLow.setText(floatStringToIntString(result));
                EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.BLOOD_PRESSURE_ADD_LOW, result));
            }
        });

//        rulerViewLowBmi.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
//            @Override
//            public void onEndResult(String result) {
//
//            }
//
//            @Override
//            public void onScrollResult(String result) {
//                bmiDataTextView.setText(floatStringToIntString(result));
//                EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.BLOOD_PRESSURE_ADD_BMI, result));
//            }
//        });
    }


    @OnClick(R.id.ll_select_time)
    public void onViewClicked() {
        PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvTime.setText(content);
                EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.BLOOD_PRESSURE_ADD_TIME, content));
            }
        });
    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }
}
