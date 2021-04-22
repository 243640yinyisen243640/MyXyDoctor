package com.xy.xydoctor.ui.activity.followupvisit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.FollowListBean;
import com.xy.xydoctor.bean.SignProtocolBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.IAFollowUpChoose;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.window.FollowUpTemplatePopupWindow;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class FollowUpVisitAddNewActivity extends XYSoftUIBaseActivity implements View.OnClickListener, IAFollowUpChoose {

    /**
     * 使用模板创建
     */
    private TextView templateTextView;
    /**
     * 手动创建
     */
    private TextView handTextView;

    private TextView sureTextView;

    /**
     * 1：糖尿病 2：高血压 3：肝病
     */
    private String type;
    /**
     *
     */
    private LinearLayout templateLinearLayout;

    private FollowUpTemplatePopupWindow upTemplatePopupWindow;
    /**
     * 模板ID
     */
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        topViewManager().titleTextView().setText(R.string.follow_up_visit_add_title);
        topViewManager().backTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_base_back_red, 0, 0, 0);
        containerView().addView(initView());
        initListener();
        initiValues();
    }

    private void initiValues() {
        if ("3".equals(type)){
            templateLinearLayout.setVisibility(View.GONE);
        }else {
            templateLinearLayout.setVisibility(View.VISIBLE);
        }

    }

    private void initListener() {
        templateLinearLayout.setOnClickListener(this);
        handTextView.setOnClickListener(this);
        sureTextView.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_follow_up_add_new, null);
        templateTextView = view.findViewById(R.id.tv_follow_up_add_new_template);
        templateLinearLayout = view.findViewById(R.id.ll_follow_up_add_new_template);
        handTextView = view.findViewById(R.id.tv_follow_up_add_new_hand);
        sureTextView = view.findViewById(R.id.tv_follow_up_sure);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_follow_up_add_new_template:
                getFastReplyList();
                break;
            case R.id.tv_follow_up_add_new_hand:
                intent = new Intent(getPageContext(), FollowUpVisitAddActivity.class);
                intent.putExtra("type", type);
                if ("1".equals(type)) {
                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
                    intent.putExtra("is_family", getIntent().getBooleanExtra("is_family", false));
                } else if ("2".equals(type)) {
                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
                    intent.putExtra("is_family", getIntent().getBooleanExtra("is_family", false));
                } else {
                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
                }
                startActivity(intent);
                break;
            case R.id.tv_follow_up_sure:
                submitData();
                break;
            default:
                break;

        }
    }

    /**
     * 获取模板列表
     */
    private void getFastReplyList() {
        String docId = SPStaticUtils.getString("docId");
        String token = SPStaticUtils.getString("token");
        String userGuid = SPStaticUtils.getString("userGuid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("docid", docId);
        map.put("type", type);
        map.put("access_token", token);
        map.put("guid", userGuid);
        map.put("version", ConstantParam.SERVER_VERSION);
        RxHttp.postForm(XyUrl.FOLLOW_UP_ADD_FOLLOW_LIST)
                .addAll(map)
                .asResponseList(FollowListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FollowListBean>>() {
                    @Override
                    public void accept(List<FollowListBean> listBean) throws Exception {
                        showPopupWindow(listBean);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

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

    @Override
    public void IAFollowUpChoose(int id, String name) {
        this.id = id;
        templateTextView.setText(name);
    }

    private void submitData() {
        String userid = getIntent().getStringExtra("userid");
        String userGuid = SPStaticUtils.getString("userGuid");
        String token = SPStaticUtils.getString("token");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("plan_id", id);
        map.put("access_token", token);
//        map.put("guid", userGuid);
        map.put("type", type);
        map.put("version", ConstantParam.SERVER_VERSION);
        Log.i("yys", "map==" + map);
        RxHttp.postForm(XyUrl.FOLLOW_UP_VISIT_CREATE)
                .addAll(map)
                .asResponse(SignProtocolBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SignProtocolBean>() {
                    @Override
                    public void accept(SignProtocolBean signProtocol) throws Exception {
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUMMARY_ADD));
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT));
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUMMARY_ADD));
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT));
                        finish();
                    }
                });
    }
}
