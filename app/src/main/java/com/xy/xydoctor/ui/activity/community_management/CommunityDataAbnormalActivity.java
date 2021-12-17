package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    /**
     * 当前选中fragment
     */
    private int index = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_data_abnormal_title);

        topViewManager().moreTextView().setText(R.string.base_deal);

        topViewManager().moreTextView().setOnClickListener(v -> {
            CommunityDataAbnormalFragment fragment = (CommunityDataAbnormalFragment) fragments.get(index);
            if (topViewManager().moreTextView().getText().equals("处理")) {
                topViewManager().moreTextView().setText(R.string.cancel);
                //头部的按钮是处理 ，处理-确定

                fragment.setCheckIsVisible();
                fragment.setCheckAllIsVisible();
            } else {
                topViewManager().moreTextView().setText(R.string.base_deal);
                fragment.setDataRefresh();
                //                //这个是点击确定按钮应该走的逻辑
                //                List<DataAbnormalChildInfo> checkList = new ArrayList<>();
                //                List<DataAbnormalInfo> abnormalInfos = fragment.checkList();
                //                for (int i = 0; i < abnormalInfos.size(); i++) {
                //                    for (int j = 0; j < abnormalInfos.get(i).getCommunityUser().size(); j++) {
                //                        if (abnormalInfos.get(i).getCommunityUser().get(j).isSelected()) {
                //                            checkList.add(abnormalInfos.get(i).getCommunityUser().get(j));
                //                        }
                //                    }
                //                }
                //                if (checkList.size() == 0) {
                //                    TipUtils.getInstance().showToast(getPageContext(), R.string.you_can_not_choose_person);
                //                } else {
                //                    saveData(checkList);
                //                }
            }


        });
        containerView().addView(initView());
        initListener();
        initValues();

    }


    //    private void saveData(List<DataAbnormalChildInfo> checkList) {
    //        StringBuilder stringBuilder = new StringBuilder();
    //        for (int i = 0; i < checkList.size(); i++) {
    //            String userid = checkList.get(i).getUserid();
    //            stringBuilder.append(userid);
    //            stringBuilder.append(",");
    //
    //        }
    //        //截取最后,
    //        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
    //        String type = "";
    //        Log.i("yys", "index==" + index);
    //        if (index == 0) {
    //            type = "2";
    //        } else {
    //            type = "1";
    //        }
    //        Call<String> requestCall = DataManager.loadCheckList(substring, type, (call, response) -> {
    //            if (response.code == 200) {
    //                topViewManager().moreTextView().setText("处理");
    //                CommunityDataAbnormalFragment fragment = (CommunityDataAbnormalFragment) fragments.get(index);
    //                fragment.setDataRefresh();
    //            }
    //        }, (call, t) -> {
    //            ToastUtils.showShort(getString(R.string.network_error));
    //        });
    //    }

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
        topViewManager().moreTextView().setText("处理");
        index = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));
        viewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        topViewManager().moreTextView().setText("处理");

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
            CommunityDataAbnormalFragment talkFragment = CommunityDataAbnormalFragment.newInstance(i + "");
//            Bundle bundle = new Bundle();
//            bundle.putString("type", i + "");
//            talkFragment.setArguments(bundle);
            fragments.add(talkFragment);
        }

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        radioGroup.check(radioGroup.getChildAt(0).getId());
        viewPager.setOffscreenPageLimit(fragments.size());

    }

    public TextView topTextView() {
        return topViewManager().moreTextView();
    }


}
