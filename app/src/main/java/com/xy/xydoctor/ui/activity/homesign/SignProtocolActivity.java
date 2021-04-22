package com.xy.xydoctor.ui.activity.homesign;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SignProtocolBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.engine.GlideImageEngine;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/*
 * 类名:     SignProtocolActivity
 * 描述:     签约协议
 * 作者:     ZWK
 * 创建日期: 2020/1/15 14:47
 */

public class SignProtocolActivity extends BaseActivity {
    @BindView(R.id.iv_group_photo)
    ImageView ivGroupPhoto;
    @BindView(R.id.iv_doctor_autograph)
    ImageView ivDoctorAutograph;
    @BindView(R.id.iv_patient_autograph)
    ImageView ivPatientAutograph;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private SignProtocolBean signProtocol;


    @OnClick({R.id.iv_group_photo, R.id.iv_doctor_autograph, R.id.iv_patient_autograph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_group_photo:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(signProtocol.getGroup_photo())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivGroupPhoto);
                break;
            case R.id.iv_doctor_autograph:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(signProtocol.getDoc_sign())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivDoctorAutograph);
                break;
            case R.id.iv_patient_autograph:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(signProtocol.getUser_sign())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivPatientAutograph);
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_protocol;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("签约协议");
        String user_id = getIntent().getStringExtra("user_id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", user_id);
        RxHttp.postForm(XyUrl.GET_AGREEMENT)
                .addAll(map)
                .asResponse(SignProtocolBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SignProtocolBean>() {
                    @Override
                    public void accept(SignProtocolBean signProtocol) throws Exception {
                        Glide.with(Utils.getApp()).load(signProtocol.getGroup_photo()).placeholder(R.drawable.group_photo).into(ivGroupPhoto);
                        Glide.with(Utils.getApp()).load(signProtocol.getDoc_sign()).placeholder(R.drawable.doctor_autograph).into(ivDoctorAutograph);
                        Glide.with(Utils.getApp()).load(signProtocol.getUser_sign()).placeholder(R.drawable.patient_autograph).into(ivPatientAutograph);
                        tvTime.setText(signProtocol.getAddtime());
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
