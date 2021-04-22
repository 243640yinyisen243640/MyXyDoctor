package com.xy.xydoctor.ui.fragment.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.HomeDoctorListAdapter;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.director.DoctorListActivity;
import com.xy.xydoctor.ui.activity.patientadd.PatientAddSearchActivity;
import com.xy.xydoctor.ui.activity.patienteducation.PatientEducationAndMassMsgActivity;
import com.xy.xydoctor.ui.activity.user.SearchUserActivity;
import com.xy.xydoctor.utils.progress.BleDialogUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:   医生
 * 作者: LYD
 * 创建日期: 2020/5/18 11:14
 */
public class HomeDoctorFragment extends BaseFragment implements SimpleImmersionOwner {
    @BindView(R.id.ll_patient_add)
    LinearLayout llPatientAdd;
    @BindView(R.id.ll_patient_my_group)
    LinearLayout llPatientMyGroup;
    @BindView(R.id.ll_patient_msg)
    LinearLayout llPatientMsg;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.lv_patient_group)
    ListView lvPatientGroup;
    @BindView(R.id.srl_patient_group)
    SmartRefreshLayout srlPatientGroup;
    private BleDialogUtils bleDialogUtils = new BleDialogUtils();
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_doctor;
    }

    @Override
    protected void init(View rootView) {
        getDoctorList();
        FloatingView.get().remove();
        //EasyFloat.dismiss();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getDoctorList() {
        //LoadingDialogUtils.show(getActivity());
        bleDialogUtils.showProgress(getActivity());
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_LIST)
                .addAll(map)
                .asResponseList(DoctorListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DoctorListBean>>() {
                    @Override
                    public void accept(List<DoctorListBean> list) throws Exception {
                        //LoadingDialogUtils.unInit();
                        bleDialogUtils.dismissProgress();
                        if (list != null && list.size() > 0) {
                            HomeDoctorListAdapter adapter = new HomeDoctorListAdapter(getPageContext(), R.layout.item_doctor_list, list);
                            lvPatientGroup.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        bleDialogUtils.dismissProgress();
                    }
                });
    }

    @OnClick({R.id.ll_patient_add, R.id.ll_patient_my_group, R.id.ll_patient_msg, R.id.ll_search})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_patient_add:
                intent = new Intent(getActivity(), PatientAddSearchActivity.class);
                intent.putExtra("from", "homeDoctor");
                startActivity(intent);
                break;
            case R.id.ll_patient_my_group:
                intent = new Intent(getActivity(), DoctorListActivity.class);
                intent.putExtra("type", "homeDoctor");
                startActivity(intent);
                break;
            case R.id.ll_patient_msg:
                //intent = new Intent(getActivity(), MassMsgDirectorActivity.class);
                //startActivity(intent);
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
