package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.MyTreatPlanDetailBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 降压方案详情
 * 作者: LYD
 * 创建日期: 2019/3/29 18:01
 */
public class MyTreatPlanDetailActivity extends BaseActivity {
    private static final String TAG = "MyTreatPlanDetail";
    @BindView(R.id.tv_advice)
    TextView tvAdvice;
    @BindView(R.id.tv_follow_up_advice)
    TextView tvFollowUpAdvice;
    @BindView(R.id.img_get_medicine)
    ImageView imgGetMedicine;
    @BindView(R.id.ll_medicine)
    LinearLayout llMedicine;
    @BindView(R.id.bt_get_again)
    ColorButton btGetAgain;
    @BindView(R.id.view_40)
    View view40;
    @BindView(R.id.tv_doctor_advice)
    TextView tvDoctorAdvice;
    private MyTreatPlanDetailBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_treat_plan_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("降压方案");
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
                    public void accept(MyTreatPlanDetailBean myTreatPlanDetailBean) throws Exception {
                        data = myTreatPlanDetailBean;
                        //设置值
                        String treatment = data.getTreatment();
                        if ("1".equals(treatment)) {
                            //1：生活方式干预
                            tvAdvice.setText("随诊建议");
                            List<String> rec_article = data.getRec_article();
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 0; i < rec_article.size(); i++) {
                                stringBuffer.append(rec_article.get(i));
                            }
                            String replace = stringBuffer.toString().replace('；', '\n');
                            tvFollowUpAdvice.setText(replace);
                        } else {
                            //2及时就诊
                            tvAdvice.setText("就诊建议");
                            String recommend = data.getRecommend();
                            String s1 = recommend.replace('；', '\n');
                            tvFollowUpAdvice.setText(s1);
                        }
                        int drugs = data.getDrugs();
                        //是否推荐药物 1是 0否
                        if (1 == drugs) {
                            llMedicine.setVisibility(View.VISIBLE);
                            view40.setVisibility(View.GONE);
                        } else {
                            view40.setVisibility(View.VISIBLE);
                            llMedicine.setVisibility(View.GONE);
                        }
                        tvDoctorAdvice.setText(data.getMessage());
                        //保存Id
                        SPStaticUtils.put("pid", data.getPaid() + "");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @OnClick({R.id.img_get_medicine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_get_medicine://获取药物
                Intent intent = new Intent(getPageContext(), MedicineSelectListActivity.class);
                intent.putExtra("id", data.getId());
                startActivity(intent);
                break;
            //            case R.id.bt_get_again://答题
            //                startActivity(new Intent(getPageContext(), PerTestActivity.class));
            //                break;
        }
    }
}
