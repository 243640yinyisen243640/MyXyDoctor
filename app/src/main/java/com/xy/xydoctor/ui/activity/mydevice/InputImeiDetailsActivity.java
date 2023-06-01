package com.xy.xydoctor.ui.activity.mydevice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 *
 */
public class InputImeiDetailsActivity extends BaseActivity {
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.et_imei)
    ColorEditText etImei;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.bt_sure)
    ColorButton btSure;

    private String userid;
    /**
     * 血糖设备号
     */
    private String imei="";
    /**
     * 血压设备号
     */
    private String suNum="";
    /**
     * 1:血糖  2血压
     */
    private String type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_imei;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userid = getIntent().getStringExtra("userid");
        imei = getIntent().getStringExtra("imei");
        suNum = getIntent().getStringExtra("suNum");
        type = getIntent().getStringExtra("type");
        setIMEI();
        setTitle("设备详情");

        btSure.setText("解除绑定");
    }


    private void setIMEI() {
        if ("1".equals(type)) {
            etImei.setText(imei);
            etImei.setSelection(imei.length());
        } else {
            etImei.setText(suNum);
            etImei.setSelection(suNum.length());
        }

        etImei.setEnabled(false);
    }


    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        toDoUnbind();
    }

    private void toDoUnbind() {
        HashMap<String, Object> map = new HashMap<>();
        if ("1".equals(type)) {
            map.put("imei", imei);
        } else {
            map.put("imei", suNum);
        }

        RxHttp.postForm(XyUrl.DEVICE_UN_BIND_PATIENT)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtils.showShort("成功");
                        Intent intent = new Intent();
                        intent.putExtra("type", type);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.showShort("失败");
                    }
                });
    }
}
