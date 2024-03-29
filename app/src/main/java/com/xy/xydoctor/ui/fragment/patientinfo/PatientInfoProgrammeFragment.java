package com.xy.xydoctor.ui.fragment.patientinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：治疗方案
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class PatientInfoProgrammeFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {
    private TextView tvTitle;
    private CustomViewPager viewPager;
    private List<Fragment> fragments;
    private String userId;

    public static PatientInfoProgrammeFragment newInstance(String userId) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        PatientInfoProgrammeFragment fragment = new PatientInfoProgrammeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        userId = getArguments().getString("userId");
        initView();
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout._fragment_data, null);
        tvTitle = view.findViewById(R.id.tv_injection_title);
        viewPager = view.findViewById(R.id.rv_injection);
        containerView().addView(view);

        fragments = new ArrayList<>();
        PatientInfoCurrentFragment currentFragment = PatientInfoCurrentFragment.newInstance(userId);
        fragments.add(currentFragment);

        PatientInfoHistoryFragment historyFragment = PatientInfoHistoryFragment.newInstance(userId);
        fragments.add(historyFragment);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        viewPager.setOffscreenPageLimit(fragments.size());
        tvTitle.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() == 0 ? 1 : 0);
            tvTitle.setText(viewPager.getCurrentItem() == 0 ?  "查看历史": "查看当前");
        });
    }


}
