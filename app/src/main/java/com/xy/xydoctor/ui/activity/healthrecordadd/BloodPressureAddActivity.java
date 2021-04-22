package com.xy.xydoctor.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.util.TableLayoutUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.layout.NoScrollViewPager;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.fragment.BloodPressureAddManualFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:    添加血压
 * 作者: LYD
 * 创建日期: 2019/7/8 15:29
 */
@BindEventBus
public class BloodPressureAddActivity extends BaseEventBusActivity {
    @BindView(R.id.bt_back)
    Button btnBack;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    NoScrollViewPager vpContent;
    @BindView(R.id.tv_more)
    TextView tvBaseRight;
    @BindView(R.id.tv_target)
    TextView tvTarget;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] typeArray;
    private String high = "120";
    private String low = "80";
    private String time;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_blood_pressure_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        hideLine();
        setFragment();
        setFirstTime();
    }

    private void setFirstTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        time = nowString;
    }


    private void setFragment() {
        //添加分割线
        TableLayoutUtils.addVerticalDivider(tlTab, R.drawable.layout_divider_vertical, 15);
        //设置下划线宽度
        tlTab.setTabIndicatorFullWidth(false);
        //设置字体大小
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (0 == position) {
                    tvBaseRight.setVisibility(View.VISIBLE);
                } else {
                    tvBaseRight.setVisibility(View.GONE);
                }
                TextView title = (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextSize(18);
                title.setTextAppearance(getPageContext(), R.style.Tab_Text_Select);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView title = (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextSize(16);
                title.setTextAppearance(getPageContext(), R.style.Tab_Text_UnSelect);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //设置Fragment
        typeArray = getResources().getStringArray(R.array.blood_pressure_title);
        fragmentList.add(new BloodPressureAddManualFragment());
        //fragmentList.add(new BloodPressureAddAutoFragment());
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        vpContent.setOffscreenPageLimit(1);//ButterKnife报错处理
        tlTab.setupWithViewPager(vpContent);
    }

    @OnClick({R.id.bt_back, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_more:
                toDoSubmit();
                break;
        }
    }

    private void toDoSubmit() {
        HashMap map = new HashMap<>();
        map.put("uid", getIntent().getStringExtra("userId"));
        map.put("systolic", high);//收缩
        map.put("diastole", low);//舒张
        map.put("heartrate", "");
        map.put("datetime", time);
        map.put("type", "2");
        RxHttp.postForm(XyUrl.ADD_BLOOD)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtils.showShort(s);
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.BLOOD_PRESSURE_ADD_HIGH:
                high = event.getMsg();
                break;
            case ConstantParam.EventCode.BLOOD_PRESSURE_ADD_LOW:
                low = event.getMsg();
                break;
            case ConstantParam.EventCode.BLOOD_PRESSURE_ADD_TIME:
                String selectTime = event.getMsg();
                time = selectTime;
                break;
        }
    }

    private class TabAdapter extends FragmentPagerAdapter {

        private TabAdapter(FragmentManager fm) {
            super(fm);
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
