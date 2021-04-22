package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.MyTreatPlanDetailBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 就诊详情
 * 作者: LYD
 * 创建日期: 2019/4/3 15:18
 */
public class MyTreatPlanDetailFollowUpActivity extends BaseActivity {
    @BindView(R.id.tv_follow_up_advice)
    TextView tvFollowUpAdvice;
    @BindView(R.id.tv_doctor_advice)
    TextView tvDoctorAdvice;

    @BindView(R.id.bt_get_again)
    ColorButton btGetAgain;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_treat_plan_detail_follow_up;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("智能决策");
        getData();
        setGetBtAgain();
    }

    private void setGetBtAgain() {
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            btGetAgain.setVisibility(View.VISIBLE);
        } else {
            btGetAgain.setVisibility(View.GONE);
        }
    }

    private void getData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        map.put("uid", getIntent().getStringExtra("userid"));
        RxHttp.postForm(XyUrl.PLAN_GET_PLAN_DETAIL)
                .addAll(map)
                .asResponse(MyTreatPlanDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<MyTreatPlanDetailBean>() {
                    @Override
                    public void accept(MyTreatPlanDetailBean data) throws Exception {
                        String recommend = data.getRecommend();
                        String s1 = recommend.replace('；', '\n');
                        tvFollowUpAdvice.setText(s1);
                        tvDoctorAdvice.setText(data.getMessage());
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
