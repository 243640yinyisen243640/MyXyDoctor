package com.xy.xydoctor.ui.activity.homesign;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.InHospitalDetaiBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.engine.GlideImageEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class InHospitalManageActivity extends BaseActivity {
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.tv_reply)
    EditText tvReply;
    @BindView(R.id.btn_submit)
    ColorButton btnSubmit;
    @BindView(R.id.rb_agree)
    RadioButton rbAgree;
    @BindView(R.id.rb_refuse)
    RadioButton rbRefuse;

    private InHospitalDetaiBean bean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_hospital_manage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("住院申请");
        int id = getIntent().getIntExtra("id", -1);
        getDetail(id);
    }

    private void getDetail(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_FAMILY_IN_HOSPITAL_DETAIL)
                .addAll(map)
                .asResponse(InHospitalDetaiBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<InHospitalDetaiBean>() {
                    @Override
                    public void accept(InHospitalDetaiBean updateBean) throws Exception {
                        bean = updateBean;
                        tvOne.setText(bean.getName());
                        if ("1".equals(bean.getType())) {
                            tvTwo.setText("初次住院");
                        } else if ("2".equals(bean.getType())) {
                            tvTwo.setText("再次住院");
                        }
                        tvThree.setText(bean.getAge());
                        if ("1".equals(bean.getSex())) {
                            tvFour.setText("男");
                        } else if ("2".equals(bean.getSex())) {
                            tvFour.setText("女");
                        }
                        tvFive.setText(bean.getTelephone());

                        Date date = TimeUtils.string2Date(bean.getIntime(), "yyyy-MM-dd HH:mm");
                        String time = TimeUtils.date2String(date, "yyyy-MM-dd");
                        tvSix.setText(time);
                        tvDescribe.setText(bean.getDescribe());
                        tvReply.setText(bean.getReason());

                        if ("1".equals(bean.getStatus())) {
                            tvReply.setEnabled(true);
                            btnSubmit.setVisibility(View.VISIBLE);
                        }

                        if (bean.getPic().size() > 0) {
                            Glide.with(getPageContext()).load(bean.getPic().get(0)).into(ivOne);
                        }
                        if (bean.getPic().size() > 1) {
                            Glide.with(getPageContext()).load(bean.getPic().get(1)).into(ivTwo);
                        }
                        if (bean.getPic().size() > 2) {
                            Glide.with(getPageContext()).load(bean.getPic().get(2)).into(ivThree);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    @OnClick({R.id.btn_submit, R.id.iv_one, R.id.iv_two, R.id.iv_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String reply = tvReply.getText().toString().trim();
                if (TextUtils.isEmpty(reply)) {
                    ToastUtils.showShort("请输入回复");
                    return;
                }
                if (!rbAgree.isChecked() && !rbRefuse.isChecked()) {
                    ToastUtils.showShort("请选择是否同意");
                    return;
                }
                int x = 1;
                if (rbAgree.isChecked()) {
                    x = 2;
                } else if (rbRefuse.isChecked()) {
                    x = 3;
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", getIntent().getIntExtra("id", -1));
                map.put("status", x);
                map.put("reason", reply);
                RxHttp.postForm(XyUrl.GET_FAMILY_IN_HOSPITAL_DEAL)
                        .addAll(map)
                        .asResponse(String.class)
                        .to(RxLife.toMain(this))
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String response) throws Exception {
                                ToastUtils.showShort("操作成功");
                                finish();
                            }
                        }, new OnError() {
                            @Override
                            public void onError(ErrorInfo error) throws Exception {

                            }
                        });
                break;
            case R.id.iv_one:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) bean.getPic())
                        .setCurrentPosition(0)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivOne);
                break;
            case R.id.iv_two:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) bean.getPic())
                        .setCurrentPosition(1)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivTwo);
                break;
            case R.id.iv_three:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) bean.getPic())
                        .setCurrentPosition(2)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivThree);
                break;
        }
    }
}
