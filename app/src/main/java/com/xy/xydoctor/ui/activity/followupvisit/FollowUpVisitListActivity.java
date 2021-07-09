package com.xy.xydoctor.ui.activity.followupvisit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.healthrecord.BaseHideLineActivity;
import com.xy.xydoctor.ui.fragment.FollowUpVisitFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:随访管理列表
 * 作者: LYD
 * 创建日期: 2019/7/19 9:49
 */
public class FollowUpVisitListActivity extends BaseHideLineActivity {
    private static final String TAG = "FollowUpVisitListActivity";
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] typeArray;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_up_visit_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initFragment();
        hideTitleBar();
    }

    private void initFragment() {
        LogUtils.e(getIntent().getBooleanExtra("from_family", false));
        if (getIntent().getBooleanExtra("from_family", false)) {
            typeArray = getResources().getStringArray(R.array.follow_up_visit_title_no_liver);
        } else {
            typeArray = getResources().getStringArray(R.array.follow_up_visit_title);
        }
        for (int i = 0; i < typeArray.length; i++) {
            FollowUpVisitFragment fragment = new FollowUpVisitFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", i + 1 + "");
            if (getIntent().getBooleanExtra("from_family", false)) {
                bundle.putBoolean("is_family", true);
            }
            bundle.putString("userId", getIntent().getStringExtra("userid"));
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        //设置TabLayout的模式
        tlTab.setTabMode(TabLayout.MODE_FIXED);
        //ViewPager的适配器
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        //关联ViewPager和TabLayout
        tlTab.setupWithViewPager(vpContent);
        vpContent.setOffscreenPageLimit(1);
        //设置当前
        int type = getIntent().getIntExtra("type", 0);
        vpContent.setCurrentItem(type);
    }


    @OnClick({R.id.bt_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_right:
                int currentItem = vpContent.getCurrentItem();
                if (0 == currentItem) {
                    Intent intent = new Intent(getPageContext(), FollowUpVisitAddNewActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
                    if (getIntent().getBooleanExtra("from_family", false)) {
                        intent.putExtra("is_family", true);
                    }
                    startActivity(intent);
                } else if (1 == currentItem) {
                    Intent intent = new Intent(getPageContext(), FollowUpVisitAddNewActivity.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
                    if (getIntent().getBooleanExtra("from_family", false)) {
                        intent.putExtra("is_family", true);
                    }
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getPageContext(), FollowUpVisitAddNewActivity.class);
                    intent.putExtra("type", "3");
                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
                    startActivity(intent);
                }
                break;
        }
    }


    private class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        //设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return typeArray[position];
        }
    }
}
