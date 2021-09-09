package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.ui.fragment.community_management.CommunityFollowUpListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：随访待办的列表格式
 * 类传参：
 *
 * @author android.yys
 */
public class CommunityFollowUpActivity extends XYSoftUIBaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private RadioButton waitRadioButton, lostRadioButton, finishRadioButton;
    private ViewPager viewPager;

    private List<Fragment> fragments;

    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.follow_up_agent_title);
        containerView().addView(initView());
        initListener();
        initValues();
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_follow_up_change, null);
        radioGroup = getViewByID(view, R.id.rg_spe_shelves);
        waitRadioButton = getViewByID(view, R.id.rb_follow_wait_follow);
        lostRadioButton = getViewByID(view, R.id.rb_follow_lost_follow);
        finishRadioButton = getViewByID(view, R.id.rb_follow_finish_follow);
        viewPager = getViewByID(view, R.id.vp_spe_shelves);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        radioGroup.setOnCheckedChangeListener(null);
        radioGroup.check(radioGroup.getChildAt(position).getId());
        index = position;
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        viewPager.clearOnPageChangeListeners();
        viewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(i)));
        index = radioGroup.indexOfChild(radioGroup.findViewById(i));
        viewPager.addOnPageChangeListener(this);
    }

    private void initValues() {
        fragments = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            CommunityFollowUpListFragment talkFragment = CommunityFollowUpListFragment.newInstance(i + "");
            fragments.add(talkFragment);
        }
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        radioGroup.check(radioGroup.getChildAt(0).getId());
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    public RadioButton showFirst() {
        return waitRadioButton;
    }

    public RadioButton showSecond() {
        return waitRadioButton;
    }

    public RadioButton showThird() {
        return waitRadioButton;
    }
}
