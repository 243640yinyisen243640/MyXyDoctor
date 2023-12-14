package com.xy.xydoctor.ui.activity.insulin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.InjectionBaseData;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.ui.fragment.insulin.InsulinBaseRateFragment;
import com.xy.xydoctor.ui.fragment.insulin.InsulinLargeDoseFragment;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinPlanDetailsActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private TextView tvLargeDose;
    private TextView tvBasalRate;
    private CustomViewPager viewPager;
    private List<Fragment> fragments;
    private InjectionBaseData injectionBaseData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String time = getIntent().getStringExtra("time");
        topViewManager().titleTextView().setText(time);
        containerView().addView(initView());
        initListener();
        getData();
    }

    private void initListener() {
        tvLargeDose.setOnClickListener(this);
        tvBasalRate.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_plan_details, null);
        tvLargeDose = view.findViewById(R.id.tv_infusion_add_large_dose);
        tvBasalRate = view.findViewById(R.id.tv_infusion_add_basal_rate);
        viewPager = getViewByID(view, R.id.vp_insulin_details);

        return view;
    }

    private void initValues() {
        fragments = new ArrayList<>();
        InsulinLargeDoseFragment largeDoseFragment = InsulinLargeDoseFragment.newInstance();
        fragments.add(largeDoseFragment);

        InsulinBaseRateFragment baseRateFragment = InsulinBaseRateFragment.newInstance();
        fragments.add(baseRateFragment);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        viewPager.setOffscreenPageLimit(fragments.size());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_infusion_add_large_dose:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_infusion_add_basal_rate:
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }


    private void getData() {

        DataManager.getInjectionBaseInfo("", (call, response) -> {
            if (response.code == 200) {
                injectionBaseData = (InjectionBaseData) response.object;
                setData();
                initValues();
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void setData() {

    }


}
