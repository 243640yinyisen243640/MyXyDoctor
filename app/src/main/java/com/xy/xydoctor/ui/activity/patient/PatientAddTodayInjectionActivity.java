package com.xy.xydoctor.ui.activity.patient;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientAddTodayListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.PatientAddTodayListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  当日新增患者
 * 作者: LYD
 * 创建日期: 2020/11/4 9:31
 */
public class PatientAddTodayInjectionActivity extends BaseActivity {
    @BindView(R.id.rv_patient_add_today)
    RecyclerView rvPatientAddToday;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.tv_empty_desc)
    TextView tvEmptyDesc;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_add_today_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("当日新增");
        getPatientAddTodayList();
    }


    /**
     *
     */
    private void getPatientAddTodayList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.PATIENT_ADD_TODAY_LIST)
                .addAll(map)
                .asResponseList(PatientAddTodayListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<PatientAddTodayListBean>>() {
                    @Override
                    public void accept(List<PatientAddTodayListBean> list) throws Exception {
                        rvPatientAddToday.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.GONE);
                        rvPatientAddToday.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        rvPatientAddToday.setAdapter(new PatientAddTodayListAdapter(list));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        rvPatientAddToday.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.VISIBLE);
                        tvEmptyDesc.setText("无任何新增!");
                    }
                });
    }


}