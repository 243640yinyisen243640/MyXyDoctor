package com.xy.xydoctor.ui.activity.todo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPStaticUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.FollowListBean;
import com.xy.xydoctor.bean.PatientApplyBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.IAFollowUpChoose;
import com.xy.xydoctor.imp.IAPatientApply;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.window.FollowUpTemplatePopupWindow;
import com.xy.xydoctor.window.PatientApplyPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class PatientApplyActivity extends XYSoftUIBaseActivity implements View.OnClickListener, IAFollowUpChoose, IAPatientApply {

    /**
     * 姓名
     */
    private LinearLayout nameLinearLayout;
    private TextView nameTextView;
    /**
     * 随访计划
     */
    private LinearLayout planLinearLayout;
    private TextView planTextView;
    /**
     * 选择模板
     */
    private LinearLayout templateLinearLayout;
    private TextView templateTextView;

    private TextView sureTextView;

    private PatientApplyPopupWindow patientApplyPopupWindow;

    private FollowUpTemplatePopupWindow upTemplatePopupWindow;
    /**
     * 模板ID
     */
    private int planID = 0;

    private String name;
    /**
     * 0:不启用 1：糖尿病 2：高血压
     */
    private String type = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        topViewManager().titleTextView().setText(R.string.patient_apply_title);
        topViewManager().backTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_base_back_red, 0, 0, 0);
        containerView().addView(initView());
        initListener();
        initiValues();
    }

    private void initiValues() {
        nameTextView.setText(name);
    }

    private void initListener() {
        templateLinearLayout.setOnClickListener(this);
        planLinearLayout.setOnClickListener(this);
        sureTextView.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_patient_apply, null);
        nameLinearLayout = view.findViewById(R.id.ll_patient_apply_name);
        nameTextView = view.findViewById(R.id.tv_patient_apply_name);

        planLinearLayout = view.findViewById(R.id.ll_patient_apply_plan);
        planTextView = view.findViewById(R.id.tv_patient_apply_plan);

        templateLinearLayout = view.findViewById(R.id.ll_patient_apply_template);
        templateTextView = view.findViewById(R.id.tv_patient_apply_template);

        sureTextView = view.findViewById(R.id.tv_patient_apply_sure);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_patient_apply_plan:
                getPlanType();
                break;
            case R.id.ll_patient_apply_template:
                getFastReplyList();
                break;
            case R.id.tv_patient_apply_sure:
                toDoOperate();
                break;
            default:
                break;

        }
    }

    private void getPlanType() {
        List<PatientApplyBean> list = new ArrayList<>();
        PatientApplyBean patientApplyBean = new PatientApplyBean();
        patientApplyBean.setId(0);
        patientApplyBean.setPlanname("不启用");
        list.add(patientApplyBean);
        PatientApplyBean patientApplyBean1 = new PatientApplyBean();
        patientApplyBean1.setId(1);
        patientApplyBean1.setPlanname("启用血糖随访计划");
        list.add(patientApplyBean1);
        PatientApplyBean patientApplyBean2 = new PatientApplyBean();
        patientApplyBean2.setId(2);
        patientApplyBean2.setPlanname("启用血压随访计划");
        list.add(patientApplyBean2);
        patientApplyPopupWindow = new PatientApplyPopupWindow(getPageContext(), list);
        patientApplyPopupWindow.setOnChooseOkListener(this);
        if (!patientApplyPopupWindow.isShowing()) {
            patientApplyPopupWindow.showAtLocation(containerView(), Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 获取模板列表
     */
    private void getFastReplyList() {
        //        String docId = SPStaticUtils.getString("docId");
        int docId = getIntent().getIntExtra("docid", 0);
        String token = SPStaticUtils.getString("token");
        String userGuid = SPStaticUtils.getString("userGuid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("docid", docId);
        map.put("access_token", token);
        map.put("guid", userGuid);
        // 1：糖尿病 2：高血压
        map.put("type", type);
        map.put("version", ConstantParam.SERVER_VERSION);
        RxHttp.postForm(XyUrl.FOLLOW_UP_ADD_FOLLOW_LIST)
                .addAll(map)
                .asResponseList(FollowListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FollowListBean>>() {
                    @Override
                    public void accept(List<FollowListBean> listBean) throws Exception {
                        Log.i("yys", "listBean==" + listBean);
                        showPopupWindow(listBean);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        List<FollowListBean> listBean = new ArrayList<>();
                        showPopupWindow(listBean);
                        Log.i("yys", "1listBean==" + error.getErrorCode());
                    }
                });
    }

    private void showPopupWindow(List<FollowListBean> list) {
        upTemplatePopupWindow = new FollowUpTemplatePopupWindow(getPageContext(), list);
        upTemplatePopupWindow.setOnChooseOkListener(this);
        if (!upTemplatePopupWindow.isShowing()) {
            upTemplatePopupWindow.showAtLocation(containerView(), Gravity.CENTER, 0, 0);
        }
    }

    //模板列表
    @Override
    public void IAFollowUpChoose(int id, String name) {
        this.planID = id;
        templateTextView.setText(name);
    }


    //随访计划的列表
    @Override
    public void IAPatientApply(int id, String name) {
        this.type = id + "";
        if (0 == id) {
            templateLinearLayout.setVisibility(View.GONE);
        } else {
            templateLinearLayout.setVisibility(View.VISIBLE);
        }
        planTextView.setText(name);
    }

    private void toDoOperate() {
        int id = getIntent().getIntExtra("listID", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", "2");
        map.put("plan_id", planID);
        Log.i("yys", "map==" + map);
        RxHttp.postForm(XyUrl.CHANGE_PATIENT_STATE)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
