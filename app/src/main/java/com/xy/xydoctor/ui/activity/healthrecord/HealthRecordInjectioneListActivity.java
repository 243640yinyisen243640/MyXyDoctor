package com.xy.xydoctor.ui.activity.healthrecord;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.InjectionBaseData;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.ui.fragment.patientinfo.PatientInfoInjectionFragment;
import com.xy.xydoctor.ui.fragment.patientinfo.PatientInfoProgrammeFragment;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class HealthRecordInjectioneListActivity extends XYSoftUIBaseActivity implements View.OnClickListener {
    private TextView tvNum;
    private TextView tvState;
    private TextView tvRank;
    private TextView tvCompany;
    private LinearLayout llPlan;
    private LinearLayout llProgramme;
    private TextView tvPlanNum;
    private TextView tvTimeYear;
    private TextView tvTimeMonth;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("注射数据");
        containerView().addView(initView());
        userId = getIntent().getStringExtra("userid");
        initListener();
        initValues();
        getData();
    }

    private void initListener() {
        llPlan.setOnClickListener(this);
        llProgramme.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout._activity_zhusheshujv, null);
        tvNum = view.findViewById(R.id.tv_injection_num);
        tvState = view.findViewById(R.id.tv_injection_state);
        tvRank = view.findViewById(R.id.tv_injection_rank);
        tvCompany = view.findViewById(R.id.tv_injection_company);
        llPlan = view.findViewById(R.id.ll_injection_plan);
        llProgramme = view.findViewById(R.id.ll_injection_programme);
        tvPlanNum = view.findViewById(R.id.tv_injection_plan_num);
        tvTimeYear = view.findViewById(R.id.tv_injection_time_year);
        tvTimeMonth = view.findViewById(R.id.tv_injection_time_month);

        viewPager = getViewByID(view, R.id.vp_injection);
        return view;
    }

    private void initValues() {
        fragments = new ArrayList<>();
        PatientInfoInjectionFragment injectionFragment = PatientInfoInjectionFragment.newInstance(userId);
        fragments.add(injectionFragment);

        PatientInfoProgrammeFragment programmeFragment = PatientInfoProgrammeFragment.newInstance(userId);
        fragments.add(programmeFragment);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        viewPager.setOffscreenPageLimit(fragments.size());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_injection_plan:
                llPlan.setBackground(getResources().getDrawable(R.drawable._2));
                llProgramme.setBackground(getResources().getDrawable(R.color.transparent));
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_injection_programme:
                llPlan.setBackground(getResources().getDrawable(R.color.transparent));
                llProgramme.setBackground(getResources().getDrawable(R.drawable._2));
                viewPager.setCurrentItem(0);
                break;
            default:
                break;
        }
    }

    private void getData() {
        DataManager.getInjectionBaseInfo(userId, (call, response) -> {
            if (response.code == 200) {
                InjectionBaseData injectionBaseData = (InjectionBaseData) response.object;
                Log.i("yys", "injectionBaseData=="+injectionBaseData.toString());
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }
}
