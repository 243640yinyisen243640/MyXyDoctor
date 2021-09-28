package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.imp.IACommunityFilterChoose;
import com.xy.xydoctor.ui.fragment.community_management.CommunityDataAbnormalFragment1;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.view.popup.DataAbnormalPopup1;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/16 16:26
 * Description: 数据异常
 */
public class CommunityDataAbnormalActivity1 extends XYSoftUIBaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, IACommunityFilterChoose, View.OnClickListener {
    private RadioGroup radioGroup;
    private ViewPager viewPager;

    private List<Fragment> fragments;

    /**
     * 当前选中fragment
     */
    private int index = 0;


    private TextView filterTextView;
    private TextView secondTextView;
    private TextView firstTextView;

    private LinearLayout showLinearLayout;


    /**
     * 1高2低3正常4未测量5自定义
     */
    private String style = "1";
    /**
     * 血糖起始值
     */
    private String startSugar = "";
    /**
     * 血糖结束值
     */
    private String endSugar = "";
    /**
     * 处理是否 0全部 1是 2否
     */
    private String status = "2";
    private String statusName = "";
    /**
     * 开始时间
     */
    private String starttime;
    /**
     * 结束时间
     */
    private String endtime;

    private DataAbnormalPopup1 popu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_data_abnormal_title);
        starttime = DataUtils.getLastMonthTime();
        endtime = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y_M_D);
        topViewManager().moreTextView().setText(R.string.base_deal);
        for (int i = 0; i < 2; i++) {
            aaaList.add(new aaa("未处理","偏高"));//初始一个长度为2的列表用于保存两个fragment里面的数据
        }
        topViewManager().moreTextView().setOnClickListener(v -> {
            CommunityDataAbnormalFragment1 fragment = (CommunityDataAbnormalFragment1) fragments.get(index);
            if (topViewManager().moreTextView().getText().equals("处理")) {
                topViewManager().moreTextView().setText(R.string.cancel);
                //头部的按钮是处理 ，处理-确定

                fragment.setCheckIsVisible();
                fragment.setCheckAllIsVisible();
            } else {
                topViewManager().moreTextView().setText(R.string.base_deal);
                fragment.setDataRefresh();
            }


        });
        containerView().addView(initView());
        initListener();
        initValues();

    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_data_abnormal1, null);
        radioGroup = getViewByID(view, R.id.rg_community_data_abnormal);
        viewPager = getViewByID(view, R.id.vp_community_data_abnormal);
        firstTextView = getViewByID(view, R.id.tv_data_abnormal_first);
        secondTextView = getViewByID(view, R.id.tv_data_abnormal_second);
        filterTextView = getViewByID(view, R.id.tv_data_abnormal_down);
        showLinearLayout = getViewByID(view, R.id.ll_show_pop);
        return view;
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        filterTextView.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        viewPager.clearOnPageChangeListeners();
        topViewManager().moreTextView().setText("处理");
        CommunityDataAbnormalFragment1 fragment = (CommunityDataAbnormalFragment1) fragments.get(index);
        String type;
        if (index == 0) {
            type = "2";
        } else {
            type = "1";
        }
        fragment.refreshData(style, startSugar, endSugar, status, starttime, endtime,type);
        index = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));
        viewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)));
        viewPager.addOnPageChangeListener(this);

        //切换设置数据
        if (TextUtils.equals(aaaList.get(index).getStatusName(),"未处理")){
            topViewManager().moreTextView().setVisibility(View.VISIBLE);
        }else {
            topViewManager().moreTextView().setVisibility(View.GONE);
        }
        firstTextView.setText(aaaList.get(index).getStyleName());
        secondTextView.setText(aaaList.get(index).getStatusName());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        topViewManager().moreTextView().setText("处理");
        CommunityDataAbnormalFragment1 fragment = (CommunityDataAbnormalFragment1) fragments.get(index);
        String type;
        if (index == 0) {
            type = "2";
        } else {
            type = "1";
        }
        fragment.refreshData(style, startSugar, endSugar, status,starttime,endtime,type);
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
            CommunityDataAbnormalFragment1 talkFragment = CommunityDataAbnormalFragment1.newInstance(i + "");
            Bundle bundle = new Bundle();
            bundle.putString("type", i + "");
            talkFragment.setArguments(bundle);
            fragments.add(talkFragment);
        }

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
        radioGroup.check(radioGroup.getChildAt(0).getId());
        viewPager.setOffscreenPageLimit(fragments.size());


    }


    public TextView secondTextView() {
        return secondTextView;
    }


    @Override
    public void IAFollowUpChoose(String startTime, String endTime, String style, String styleName, String startSugar, String endSugar, String status, String statusName) {
        this.starttime = startTime;
        this.endtime = endTime;
        this.startSugar = startSugar;
        this.endSugar = endSugar;
        this.status = status;
        this.statusName = statusName;
        aaaList.get(index).setStatusName(statusName);//保存数据
        aaaList.get(index).setStyleName(styleName);
        Log.i("yys", "statusName==" + statusName + "styleName==" + styleName);
        secondTextView.setText(statusName);
        if ("未处理".equals(statusName)) {
            topViewManager().moreTextView().setVisibility(View.VISIBLE);
        } else {
            topViewManager().moreTextView().setVisibility(View.GONE);
        }

        if ("1".equals(style) || "2".equals(style) || "3".equals(style) || "4".equals(style)) {
            this.style = style;
            firstTextView.setText(styleName);
        } else {
            this.style = "5";
            firstTextView.setText(startSugar + "-" + endSugar);
        }

        popu.dismiss();
        CommunityDataAbnormalFragment1 fragment = (CommunityDataAbnormalFragment1) fragments.get(index);
        String type;
        if (index == 0) {
            type = "2";
        } else {
            type = "1";
        }
        fragment.refreshData(style, startSugar, endSugar, status,startTime,endTime,type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_data_abnormal_down:
                //                CommunityDataAbnormalActivity activity = (CommunityDataAbnormalActivity) getActivity();
                //                activity.topTextView().setVisibility(View.GONE);
                showMenuWindow();
                break;
            default:
                break;
        }


    }

    /**
     * 展示筛选的pop
     */
    private void showMenuWindow() {
        String type;
        /**
         * 1血压2血糖
         */
        if (index == 0) {
            type = "2";
        } else {
            type = "1";
        }
        popu = new DataAbnormalPopup1(getPageContext(), type, object -> {

            int position = (int) object;
            switch (position) {


                default:
                    break;
            }

        });
        //        popu.setOnDismissListener(() -> {
        //            CommunityDataAbnormalActivity activity = (CommunityDataAbnormalActivity) getActivity();
        //            activity.topTextView().setVisibility(View.VISIBLE);
        //        });
        popu.setOnChooseOkListener(this);
        if (!popu.isShowing()) {
            popu.showAsDropDown(showLinearLayout);
        }
    }


    private final List<aaa>aaaList =  new ArrayList<>();

    private class aaa{
        private String statusName;
        private String styleName;

        public aaa(String statusName, String styleName) {
            this.statusName = statusName;
            this.styleName = styleName;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getStyleName() {
            return styleName;
        }

        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }
    }
}
