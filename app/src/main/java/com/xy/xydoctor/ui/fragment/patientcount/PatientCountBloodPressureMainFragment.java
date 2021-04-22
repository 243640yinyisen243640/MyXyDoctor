package com.xy.xydoctor.ui.fragment.patientcount;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.util.TableLayoutUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.fragment.LazyLoadBaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述: 患者情况统计之血压
 * 作者: LYD
 * 创建日期: 2019/9/26 11:45
 */
public class PatientCountBloodPressureMainFragment extends LazyLoadBaseFragment {
    private static final String TAG = "PatientCount";
    @BindView(R.id.tbl_list)
    TabLayout tblList;
    @BindView(R.id.vp_list)
    ViewPager vpList;

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] listTitle = {"偏高", "偏低", "正常", "未测"};


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_count_blood_sugar_and_blood_pressure_main;
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initVp();
    }

    private void initVp() {
        TableLayoutUtils.addVerticalDivider(tblList, R.drawable.layout_divider_vertical, 14);
        //设置
        for (int i = 0; i < listTitle.length; i++) {
            PatientCountBloodPressureListFragment childFragment = new PatientCountBloodPressureListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("mainPosition", 1);
            bundle.putInt("listPosition", i);
            childFragment.setArguments(bundle);
            listFragment.add(childFragment);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        vpList.setOffscreenPageLimit(listFragment.size() - 1);
        vpList.setAdapter(adapter);
        tblList.setupWithViewPager(vpList);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }
    }
}
