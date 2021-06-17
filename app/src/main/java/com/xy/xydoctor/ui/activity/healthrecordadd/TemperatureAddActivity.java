package com.xy.xydoctor.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.datamanager.DataManager;
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
import retrofit2.Call;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:体温添加
 * 作者: LYD
 * 创建日期: 2019/7/15 10:30
 */
public class TemperatureAddActivity extends BaseActivity {
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.ruler_view_temperature)
    RulerView rulerViewTemperature;

    @BindView(R.id.tv_time_temperature)
    TextView tvTime;
    @BindView(R.id.ll_select_time_temperature)
    LinearLayout llSelectTime;


    private void setFirstTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvTime.setText(nowString);
    }

    private void initRulerListener() {
        rulerViewTemperature.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                //                tvTemperature.setText(floatStringToIntString(result));
                tvTemperature.setText(result);

            }
        });

    }

    private void initTitle() {
        setTitle(getString(R.string.temperature_record));
        getTvMore().setVisibility(View.VISIBLE);
        getTvMore().setText("保存");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }


    @OnClick({R.id.ll_select_time_temperature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_time_temperature:
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
        String docId = SPStaticUtils.getString("docId");
        String token = SPStaticUtils.getString("token");
        String temperature = tvTemperature.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        Call<String> requestCall = DataManager.saveDataTemperature(token, uid, temperature, time, docId,"2", (call, response) -> {
            if (response.code == 200) {
                ToastUtils.showShort(response.msg);
                finish();
            }else {

            }
        }, (call, t) -> {
            ToastUtils.showShort(getString(R.string.network_error));
        });
    }

    private void toSaveData() {
        String uid = getIntent().getStringExtra("userId");
        String docId = SPStaticUtils.getString("docId");
        String token = SPStaticUtils.getString("token");
        String temperature = tvTemperature.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("uid", uid);
        map.put("temperature", temperature);
        map.put("datetime", time);
        //上传类型 1设备 2手动
        map.put("type", 2);
        map.put("docuserid", docId);
        RxHttp.postForm(XyUrl.ADD_TEMPERATURE)
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
        return R.layout.activity_height_and_temperature_add;
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
