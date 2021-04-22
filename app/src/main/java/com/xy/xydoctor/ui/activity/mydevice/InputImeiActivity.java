package com.xy.xydoctor.ui.activity.mydevice;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.MainActivity;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 手动输入设备号 页面
 * 作者: LYD
 * 创建日期: 2019/3/22 15:48
 */
public class InputImeiActivity extends BaseActivity {
    private static final String TAG = "InputImeiActivity";
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.et_imei)
    ColorEditText etImei;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.bt_sure)
    ColorButton btSure;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_imei;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setIMEI();
        setType();
        setPosition();
    }


    private void setIMEI() {
        String imei = getIntent().getStringExtra("imei");
        etImei.setText(imei);
        etImei.setSelection(imei.length());
    }

    private void setType() {
        int type = getIntent().getIntExtra("type", -1);
        switch (type) {
            case 1:
                setTitle("手动输入IMEI号");
                imgBg.setImageResource(R.drawable.imei_bg);
                tvHint.setText("请确定您输入正确的IMEI号码");
                etImei.setHint("请输入IMEI号码");
                break;
            case 2:
                setTitle("手动输入SN号");
                imgBg.setImageResource(R.drawable.sn_bg);
                tvHint.setText("请确定您输入正确的SN号码");
                etImei.setHint("请输入SN号码");
                break;
            case 3:
                setTitle("手动输入SN号");
                imgBg.setImageResource(R.drawable.bp_sn_bg);
                tvHint.setText("请确定您输入正确的SN号码");
                etImei.setHint("请输入SN号码");
                break;
            //直接扫码进来
            case 4:
                setTitle("绑定设备号");
                imgBg.setImageResource(R.drawable.default_scan_bg);
                tvHint.setText("请确定您输入正确的设备号");
                etImei.setHint("请输入设备号");
                break;
        }
    }

    private void setPosition() {
        int position = getIntent().getIntExtra("position", -1);
        Log.e(TAG, "position==" + position);
        switch (position) {
            case 0:
                setTitle("手动输入SN号");
                imgBg.setImageResource(R.drawable.sn_bg);
                tvHint.setText("请确定您输入正确的SN号码");
                etImei.setHint("请输入SN号码");
                break;
            case 1:
                setTitle("手动输入IMEI号");
                imgBg.setImageResource(R.drawable.imei_bg);
                tvHint.setText("请确定您输入正确的IMEI号码");
                etImei.setHint("请输入IMEI号码");
                break;
            case 2:
                setTitle("手动输入SN号");
                imgBg.setImageResource(R.drawable.bp_sn_bg);
                tvHint.setText("请确定您输入正确的SN号码");
                etImei.setHint("请输入SN号码");
                break;
        }
    }

    private void toBind() {
        String imei = etImei.getText().toString().trim();
        if (TextUtils.isEmpty(imei)) {
            ToastUtils.showShort("请输入设备号");
            return;
        }
        int type = getIntent().getIntExtra("type", -1);
        if (1 == type || 2 == type) {
            //执行
            HashMap map = new HashMap<>();
            map.put("imei", imei);
            RxHttp.postForm(XyUrl.DEVICE_BIND)
                    .addAll(map)
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ToastUtils.showShort("获取成功");
                            SPStaticUtils.put("imei", imei);
                            ActivityUtils.finishToActivity(MainActivity.class, false);//false 不包括结束这个
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        } else if (3 == type) {
            //执行
            HashMap map = new HashMap<>();
            map.put("snnum", imei);
            RxHttp.postForm(XyUrl.SN_BIND)
                    .addAll(map)
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ToastUtils.showShort("获取成功");
                            SPStaticUtils.put("snnum", imei);
                            ActivityUtils.finishToActivity(MainActivity.class, false);//false 不包括结束这个
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        } else if (4 == type) {
            //执行
            HashMap map = new HashMap<>();
            map.put("imei", imei);
            RxHttp.postForm(XyUrl.DEVICE_BIND_PATIENT)
                    .addAll(map)
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ToastUtils.showShort("获取成功");
                            EventBusUtils.post(new EventMessage(ConstantParam.EventCode.PATIENT_INFO_DEVICE_BIND));
                            ActivityUtils.finishToActivity(PatientInfoActivity.class, false);//false 不包括结束这个
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        }
    }


    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        toBind();
    }
}
