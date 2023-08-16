package com.xy.xydoctor.ui.activity.followupvisit;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.FollowUpVisitBloodPressureDetailBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 肝病随访 患者提交 及详情
 * 作者: LYD
 * 创建日期: 2020/1/3 17:30
 */
public class FollowUpVisitHepatopathySubmitActivity extends BaseActivity {
    private static final int GET_FOLLOW_UP_VISIT_DETAIL = 10086;
    @BindView(R.id.bt_back_new)
    Button btBackNew;
    @BindView(R.id.tv_title_new)
    TextView tvTitleNew;
    @BindView(R.id.tv_more_new)
    TextView tvMore;
    @BindView(R.id.et_summary_main_question)
    ColorEditText etSummaryMainQuestion;
    @BindView(R.id.et_summary_improve_measure)
    ColorEditText etSummaryImproveMeasure;
    @BindView(R.id.et_summary_main_purpose)
    ColorEditText etSummaryMainPurpose;
    @BindView(R.id.ll_summary)
    LinearLayout llSummary;
    @BindView(R.id.et_alt)
    ColorEditText etAlt;
    @BindView(R.id.ll_alt)
    LinearLayout llAlt;
    @BindView(R.id.et_total)
    ColorEditText etTotal;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.et_white)
    ColorEditText etWhite;
    @BindView(R.id.ll_white)
    LinearLayout llWhite;
    @BindView(R.id.et_forward)
    ColorEditText etForward;
    @BindView(R.id.ll_forward)
    LinearLayout llForward;
    @BindView(R.id.et_blood_sugar)
    ColorEditText etBloodSugar;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout llBloodSugar;
    @BindView(R.id.et_blood_clotting)
    ColorEditText etBloodClotting;
    @BindView(R.id.ll_blood_clotting)
    LinearLayout llBloodClotting;
    @BindView(R.id.et_blood_red)
    ColorEditText etBloodRed;
    @BindView(R.id.ll_blood_red)
    LinearLayout llBloodRed;
    @BindView(R.id.et_blood_ammonia)
    ColorEditText etBloodAmmonia;
    @BindView(R.id.ll_blood_ammonia)
    LinearLayout llBloodAmmonia;
    @BindView(R.id.ll_index_text)
    LinearLayout llIndexText;
    @BindView(R.id.et_sas)
    ColorEditText etSas;
    @BindView(R.id.ll_sas)
    LinearLayout llSas;
    @BindView(R.id.et_ds)
    ColorEditText etDs;
    @BindView(R.id.ll_ds)
    LinearLayout llDs;
    @BindView(R.id.et_pe)
    ColorEditText etPe;
    @BindView(R.id.ll_pe)
    LinearLayout llPe;
    private String status;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_up_visit_hepatopathy_submit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        tvTitleNew.setText("随访管理");
        getFollowUpVisitDetail();
        KeyboardUtils.fixAndroidBug5497(this);
    }

    private void toDoSubmit() {
        String question = etSummaryMainQuestion.getText().toString().trim();
        if (TextUtils.isEmpty(question)) {
            ToastUtils.showShort("请输入患者目前存在的主要问题");
            return;
        }
        String improveMeasure = etSummaryImproveMeasure.getText().toString().trim();
        Log.i("yys","improveMeasure==="+improveMeasure);
        if (TextUtils.isEmpty(improveMeasure)) {
            ToastUtils.showShort("请输入主要改进措施");
            return;
        }
        String mainPurpose = etSummaryMainPurpose.getText().toString().trim();
        if (TextUtils.isEmpty(mainPurpose)) {
            ToastUtils.showShort("请输入预期到达目标");
            return;
        }
        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("vid", id);
        map.put("paquest", question);
        map.put("measures", improveMeasure);
        map.put("target", mainPurpose);
        RxHttp.postForm(XyUrl.FOLLOW_UP_VISIT_SUMMARY_ADD)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        finish();
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUMMARY_ADD));
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getFollowUpVisitDetail() {
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_FOLLOW_DETAIL_NEW)
                .addAll(map)
                .asResponse(FollowUpVisitBloodPressureDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<FollowUpVisitBloodPressureDetailBean>() {
                    @Override
                    public void accept(FollowUpVisitBloodPressureDetailBean data) throws Exception {
                        LogUtils.e("GET_FOLLOW_UP_VISIT_DETAIL");
                        status = data.getStatus() + "";
                        setRightText(status);
                        setData(data);
                        setRightText(status);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.bt_back_new, R.id.tv_more_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_new:
                finish();
                break;
            case R.id.tv_more_new:
                toDoSubmit();
                break;
        }
    }


    private void setData(FollowUpVisitBloodPressureDetailBean data) {
        int status = data.getStatus();
        if (4 == status) {
            llSummary.setVisibility(View.VISIBLE);
        } else if (5 == status) {
            llSummary.setVisibility(View.VISIBLE);
            String paquestion = data.getPaquestion();
            String measures = data.getMeasures();
            String target = data.getTarget();
            etSummaryMainQuestion.setText(paquestion);
            etSummaryImproveMeasure.setText(measures);
            etSummaryMainPurpose.setText(target);
            etSummaryMainQuestion.setEnabled(false);
            etSummaryImproveMeasure.setEnabled(false);
            etSummaryMainPurpose.setEnabled(false);
        } else {
            llSummary.setVisibility(View.GONE);
        }
        List<String> list = data.getQuestionstr();
        if (list != null && list.size() > 0) {
            //alt
            if (list.contains("1")) {
                llAlt.setVisibility(View.VISIBLE);
                String alt = data.getAlt();
                etAlt.setText(alt);
                etAlt.setEnabled(false);
            }
            //总胆红素
            if (list.contains("5")) {
                llTotal.setVisibility(View.VISIBLE);
                String total_bilirubin = data.getTotal_bilirubin();
                etTotal.setText(total_bilirubin);
                etTotal.setEnabled(false);
            }
            //白蛋白
            if (list.contains("2")) {
                llWhite.setVisibility(View.VISIBLE);
                String albumin1 = data.getAlbumin();
                etWhite.setText(albumin1);
                etWhite.setEnabled(false);
            }
            //前白蛋白
            if (list.contains("6")) {
                llForward.setVisibility(View.VISIBLE);
                String prealbumin = data.getPrealbumin();
                etForward.setText(prealbumin);
                etForward.setEnabled(false);
            }
            //血糖
            if (list.contains("3")) {
                llBloodSugar.setVisibility(View.VISIBLE);
                String blood_sugar = data.getBlood_sugar();
                etBloodSugar.setText(blood_sugar);
                etBloodSugar.setEnabled(false);
            }
            //凝血酶原活力度
            if (list.contains("7")) {
                llBloodClotting.setVisibility(View.VISIBLE);
                String prothrombin = data.getProthrombin();
                etBloodClotting.setText(prothrombin);
                etBloodClotting.setEnabled(false);
            }
            //血红蛋白
            if (list.contains("4")) {
                llBloodRed.setVisibility(View.VISIBLE);
                String haemoglobin = data.getHaemoglobin();
                etBloodRed.setText(haemoglobin);
                etBloodRed.setEnabled(false);
            }
            //血氨
            if (list.contains("8")) {
                llBloodAmmonia.setVisibility(View.VISIBLE);
                String blood_ammonia = data.getBlood_ammonia();
                etBloodAmmonia.setText(blood_ammonia);
                etBloodAmmonia.setEnabled(false);
            }
            if (list.contains("1") || list.contains("2") || list.contains("3") || list.contains("4") ||
                    list.contains("5") || list.contains("6") || list.contains("7") || list.contains("8")) {
                llIndexText.setVisibility(View.VISIBLE);
            }
            if (list.contains("9")) {
                llSas.setVisibility(View.VISIBLE);
                String sas = data.getSas();
                etSas.setText(sas);
                etSas.setEnabled(false);
            }
            if (list.contains("10")) {
                llDs.setVisibility(View.VISIBLE);
                String dietary_survey = data.getDietary_survey();
                etDs.setText(dietary_survey);
                etDs.setEnabled(false);
            }
            if (list.contains("11")) {
                llPe.setVisibility(View.VISIBLE);
                String physical_examination = data.getPhysical_examination();
                etPe.setText(physical_examination);
                etPe.setEnabled(false);
            }
        }
    }


    /**
     * 右上角
     *
     * @param status
     */
    private void setRightText(String status) {
        if ("4".equals(status)) {
            tvMore.setText("保存");
            tvMore.setVisibility(View.VISIBLE);
        } else {
            tvMore.setVisibility(View.GONE);
        }
    }

}
