package com.xy.xydoctor.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:   记录血氧
 * 作者: LYD
 * 创建日期: 2019/7/15 9:38
 */
public class BloodOxygenAddActivity extends BaseActivity {
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.ruler_view_high)
    RulerView rulerViewHigh;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.ruler_view_low)
    RulerView rulerViewLow;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;


    private void setFirstTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvTime.setText(nowString);
    }

    private void initTitle() {
        setTitle("记录血氧");
        getTvMore().setVisibility(View.VISIBLE);
        getTvMore().setText("保存");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void initRulerListener() {
        rulerViewHigh.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvHigh.setText(floatStringToIntString(result));

            }
        });
        rulerViewLow.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvLow.setText(floatStringToIntString(result));
            }
        });
    }


    @OnClick({R.id.ll_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTime.setText(content);
                    }
                });
                break;
        }
    }

    private void saveData() {
        String uid = getIntent().getStringExtra("userId");
        String oxygen = tvHigh.getText().toString().trim();
        String pulse = tvLow.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("oxygen", oxygen);
        map.put("bpmval", pulse);
        map.put("type", 2);
        map.put("datetime", time);
        RxHttp.postForm(XyUrl.ADD_BLOOD_OXYGEN)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("获取成功");
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_blood_oxygen_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initRulerListener();
        setFirstTime();
    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }
}
