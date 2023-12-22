package com.xy.xydoctor.ui.activity.insulin;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.ui.fragment.insulin.InsulinBaseRateAddFragment;
import com.xy.xydoctor.ui.fragment.insulin.InsulinLargeDoseAddFragment;
import com.xy.xydoctor.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinPlanAddActivity extends XYSoftUIBaseActivity implements View.OnClickListener {
    private TextView tvLargeDose;
    private TextView tvBasalRate;
    private CustomViewPager viewPager;
    private List<Fragment> fragments;

    private String userid;
    private String type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("新增方案");
        type = getIntent().getStringExtra("type");
        userid = getIntent().getStringExtra("userid");
        containerView().addView(initView());
        initValues();
        initListener();
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
        InsulinLargeDoseAddFragment largeDoseFragment = InsulinLargeDoseAddFragment.newInstance(userid, type);
        fragments.add(largeDoseFragment);

        InsulinBaseRateAddFragment baseRateFragment = InsulinBaseRateAddFragment.newInstance(userid, type);
        fragments.add(baseRateFragment);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(Integer.parseInt(type) - 1);//默认选中项
        if ("1".equals(type)){
            setBg(tvLargeDose, tvBasalRate);
        }else {
            setBg(tvBasalRate, tvLargeDose);
        }
        viewPager.setOffscreenPageLimit(fragments.size());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_infusion_add_large_dose:
                setBg(tvLargeDose, tvBasalRate);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_infusion_add_basal_rate:
                viewPager.setCurrentItem(1);
                setBg(tvBasalRate, tvLargeDose);
                break;
            default:
                break;
        }
    }

    private void setBg(TextView tvCheck, TextView tvUncheck1) {
        tvCheck.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.shape_red_30_3_2);
        tvCheck.setTextSize(18);
        tvCheck.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvUncheck1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvUncheck1.setTextSize(16);
        tvUncheck1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }
}
