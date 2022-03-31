package com.xy.xydoctor.ui.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ViewPagerAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgMainActivity;
import com.xy.xydoctor.ui.fragment.patientcount.PatientCountBloodPressureMainFragment;
import com.xy.xydoctor.ui.fragment.patientcount.PatientCountBloodSugarMainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 患者情况统计主页面
 * 作者: LYD
 * 创建日期: 2019/9/26 13:59
 */
public class PatientCountMain1Activity extends XYSoftUIBaseActivity implements PatientCountBloodSugarMainFragment.CallBackValue, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener  {
    private String beginTime;
    private String endTime;
    private String beginSugar;
    private String endSugar;
    private String style = "1";

    private TextView finishTextView;
    private ImageView searchImageView;
    private ImageView addImageView;
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private int index = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topViewManager().titleTextView().setText("患者情况统计");
        topViewManager().moreTextView().setText("提醒");
        topViewManager().moreTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getPageContext(), MassMsgMainActivity.class);
                intent.putExtra("type", "1");
                //对应接口的type
                intent.putExtra("mainPosition", 0);
                //对应接口的style
                intent.putExtra("listPosition", TurnsUtils.getInt(style, 0));
                //血糖
                intent.putExtra("beginTime", beginTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("beginSugar", beginSugar);
                intent.putExtra("endSugar", endSugar);
                startActivity(intent);
            }
        });
        containerView().addView(initView());
        initValues();
        initListener();
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_community_goods_area, null);
        radioGroup = getViewByID(view, R.id.rg_community_goods);
        viewPager = getViewByID(view, R.id.vp_community_goods);
        return view;
    }

    private void initValues() {
        fragments = new ArrayList<>();
        PatientCountBloodSugarMainFragment talkFragment = new PatientCountBloodSugarMainFragment();

        PatientCountBloodPressureMainFragment goodsChooseFragment = new PatientCountBloodPressureMainFragment();
        fragments.add(talkFragment);
        fragments.add(goodsChooseFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);//默认选中项
//        setCurrentPosiStatus(0);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        viewPager.setOffscreenPageLimit(fragments.size());

    }

    private void initListener() {
        viewPager.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }


//    private void setCurrentPosiStatus(int position) {
//        for (int i = 0; i < radioGroup.getChildCount(); i++) {
//            RadioButton button = (RadioButton) radioGroup.getChildAt(i);
//            if (position == i) {
//                button.setTextColor(ContextCompat.getColor(getPageContext(),R.color.white));
//                button.setBackground();
//            } else {
//                button.setTextColor(ContextCompat.getColor(getPageContext(),R.color.gray_light));
//            }
//        }
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        radioGroup.setOnCheckedChangeListener(null);
        index = position;
//        setCurrentPosiStatus(position);
        radioGroup.check(radioGroup.getChildAt(position).getId());
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        viewPager.clearOnPageChangeListeners();
        index = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));
        viewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)));
        index = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));
//        setCurrentPosiStatus(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void sendValue(String style, String beginTime, String endTime, String beginSugar, String endSugar) {
        this.style = style;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.beginSugar = beginSugar;
        this.endSugar = endSugar;
    }
}
