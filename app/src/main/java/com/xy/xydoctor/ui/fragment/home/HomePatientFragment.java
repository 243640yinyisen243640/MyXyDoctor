package com.xy.xydoctor.ui.fragment.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseEventBusFragment;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientGroupLevelAdapter;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.bean.PatientGroupLevelOneBean;
import com.xy.xydoctor.bean.PatientGroupLevelZeroBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.groupmanage.GroupManageActivity;
import com.xy.xydoctor.ui.activity.patientadd.PatientAddSearchActivity;
import com.xy.xydoctor.ui.activity.patienteducation.PatientEducationAndMassMsgActivity;
import com.xy.xydoctor.ui.activity.user.SearchUserActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 患者Fragment
 * 作者: LYD
 * 创建日期: 2019/2/26 14:29
 */
@BindEventBus
public class HomePatientFragment extends BaseEventBusFragment implements SimpleImmersionOwner {
    @BindView(R.id.ll_patient_add)
    LinearLayout llPatientAdd;
    @BindView(R.id.ll_patient_my_group)
    LinearLayout llPatientMyGroup;
    @BindView(R.id.ll_patient_msg)
    LinearLayout llPatientMsg;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rv_patient_group)
    RecyclerView rvPatientGroup;

    @BindView(R.id.srl_patient_group)
    SmartRefreshLayout srlPatientGroup;

    ArrayList<MultiItemEntity> res;
    List<PatientGroupLevelZeroBean> lv0 = new ArrayList<>();
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);
    //private NewDialogUtils bleDialogUtils = new NewDialogUtils();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_patient;
    }

    @Override
    protected void init(View rootView) {
        initValue();
        initRefresh();
        FloatingView.get().remove();
        //EasyFloat.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        initValue();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //状态栏
        //状态栏
        //        UltimateBarX.create(UltimateBarX.STATUS_BAR)        // 设置状态栏
        //                .fitWindow(true)                           // 布局是否侵入状态栏（true 不侵入，false 侵入）
        //                .bgColorRes(com.lyd.baselib.R.color.white)                // 状态栏背景颜色（资源id）
        //                .light(false)                             // light模式（状态栏字体灰色 Android 6.0 以上支持）
        //                .apply(this);
    }

    private void initRefresh() {
        //刷新
        srlPatientGroup.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initValue();
                srlPatientGroup.finishRefresh();
            }
        });
        //没有分页加载 关闭加载更多
        srlPatientGroup.setEnableLoadMore(false);
    }


    private void initValue() {
        //LoadingDialogUtils.show(getActivity());
        //bleDialogUtils.showProgress(getActivity());
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
                .addAll(map)
                .asResponseList(GroupListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupListBean>>() {
                    @Override
                    public void accept(List<GroupListBean> myTreatPlanBeans) throws Exception {
                        //LoadingDialogUtils.unInit();
                        //bleDialogUtils.dismissProgress();
                        List<GroupListBean> data = myTreatPlanBeans;
                        if (data != null) {
                            addData(data);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        //bleDialogUtils.dismissProgress();
                    }
                });
    }


    private void addData(List<GroupListBean> data) {
        res = new ArrayList<>();
        lv0.clear();
        for (int i = 0; i < data.size(); i++) {
            GroupListBean groupListBean = data.get(i);
            lv0.add(new PatientGroupLevelZeroBean(groupListBean.getGname(), groupListBean.getGid()));
            List<GroupListBean.GroupersBean> groupersList = groupListBean.getGroupers();
            for (int j = 0; j < groupersList.size(); j++) {
                GroupListBean.GroupersBean groupersBean = groupersList.get(j);
                lv0.get(i).addSubItem(new PatientGroupLevelOneBean(groupersBean.getUserid(), groupersBean.getPicture(), groupersBean.getNickname(), groupersBean.getSex(), groupersBean.getAge(), groupersBean.getUsername(), groupersBean.getDiabeteslei() + ""));
            }
        }
        for (int j = 0; j < lv0.size(); j++) {
            res.add(lv0.get(j));
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rvPatientGroup.setAdapter(new PatientGroupLevelAdapter(res, getActivity()));
        rvPatientGroup.setLayoutManager(manager);
    }


    @OnClick({R.id.ll_patient_add, R.id.ll_patient_my_group, R.id.ll_patient_msg, R.id.ll_search})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_patient_add:
                intent = new Intent(getActivity(), PatientAddSearchActivity.class);
                intent.putExtra("from", "homePatient");
                startActivity(intent);
                break;
            case R.id.ll_patient_my_group:
                startActivity(new Intent(getActivity(), GroupManageActivity.class));
                break;
            //群发消息
            case R.id.ll_patient_msg:
                intent = new Intent(getActivity(), PatientEducationAndMassMsgActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.ll_search:
                intent = new Intent(getPageContext(), SearchUserActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.GROUP_ADD:
            case ConstantParam.EventCode.GROUP_DEL:
            case ConstantParam.EventCode.GROUP_MEMBER_ADD:
            case ConstantParam.EventCode.PATIENT_ADD:
            case ConstantParam.EventCode.GROUP_MEMBER_DEL:
                initValue();
                break;
        }
    }


    /**
     * Fragment 沉浸式开始
     */
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment 沉浸式结束
     */
}
