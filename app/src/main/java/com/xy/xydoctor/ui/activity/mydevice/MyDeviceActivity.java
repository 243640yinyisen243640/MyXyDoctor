package com.xy.xydoctor.ui.activity.mydevice;

import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.DialogUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  我的血压血糖设备
 * 作者: LYD
 * 创建日期: 2020/1/6 10:03
 */
public class MyDeviceActivity extends BaseActivity {
    @BindView(R.id.tv_imei)
    TextView tvImei;
    @BindView(R.id.bt_unbind)
    ColorButton btUnbind;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_device;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("我的设备");
        int position = getIntent().getIntExtra("position", -1);
        if (0 == position) {
            String imei = SPStaticUtils.getString("imei");
            tvImei.setText("设备号:" + imei);
        } else if (1 == position) {
            String imei = SPStaticUtils.getString("snnum");
            tvImei.setText("设备号:" + imei);
        }
    }


    private void toUnbind() {
        int position = getIntent().getIntExtra("position", -1);
        String desc = "";
        if (0 == position) {
            desc = "一旦解除绑定\n您今后的血糖值将不再自动上传";
        } else if (1 == position) {
            desc = "一旦解除绑定\n您今后的血压值将不再自动上传";
        }
        DialogUtils.getInstance().showDialog(getPageContext(), "", desc, true, () -> {
            toDoUnbind();
        });
    }

    private void toDoUnbind() {
        int position = getIntent().getIntExtra("position", -1);
        if (0 == position) {
            HashMap map = new HashMap<>();
            String imei = SPStaticUtils.getString("imei");
            map.put("imei", imei);
            RxHttp.postForm(XyUrl.DEVICE_UN_BIND)
                    .addAll(map)
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ToastUtils.showShort("获取成功");
                            SPStaticUtils.put("imei", "");
                            finish();
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        } else if (1 == position) {
            HashMap map = new HashMap<>();
            String snnum = SPStaticUtils.getString("snnum");
            map.put("snnum", snnum);
            RxHttp.postForm(XyUrl.SN_UN_BIND)
                    .addAll(map)
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ToastUtils.showShort("获取成功");
                            SPStaticUtils.put("snnum", "");
                            finish();
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        }

    }


    @OnClick(R.id.bt_unbind)
    public void onViewClicked() {
        toUnbind();
    }
}
