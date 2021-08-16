package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.ui.fragment.community_management.CommunityDataAbnormalFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/16 16:26
 * Description: 数据异常
 */
public class CommunityDataAbnormalActivity extends XYSoftUIBaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    private ViewPager viewPager;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_data_abnormal_title);

        containerView().addView(initView());
        initListener();
        initValues();

    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_data_abnormal, null);
        radioGroup = getViewByID(view, R.id.rg_community_data_abnormal);
        viewPager = getViewByID(view, R.id.vp_community_data_abnormal);
        return view;
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        viewPager.clearOnPageChangeListeners();
        viewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        radioGroup.setOnCheckedChangeListener(null);
        radioGroup.check(radioGroup.getChildAt(position).getId());
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initValues() {
        fragments = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            CommunityDataAbnormalFragment talkFragment = new CommunityDataAbnormalFragment();
            fragments.add(talkFragment);
        }

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        radioGroup.check(radioGroup.getChildAt(0).getId());
        viewPager.setOffscreenPageLimit(fragments.size());

    }
}
