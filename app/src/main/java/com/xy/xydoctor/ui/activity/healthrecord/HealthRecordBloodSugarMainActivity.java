package com.xy.xydoctor.ui.activity.healthrecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.SevenAndThirtyBloodSugarListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.user.SugarControlTargetSettingActivity;
import com.xy.xydoctor.ui.fragment.SevenAndThirtyBloodSugarListFragment;
import com.xy.xydoctor.view.popup.SlideFromTopPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import razerdp.basepopup.BasePopupWindow;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 血糖记录
 * 作者: LYD
 * 创建日期: 2019/6/5 15:34
 */

public class HealthRecordBloodSugarMainActivity extends BaseHideLineActivity {
    @BindView(R.id.img_top_back)
    ImageView imgTopBack;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.ll_up_down)
    LinearLayout llUpDown;
    @BindView(R.id.tv_reset)
    ColorTextView tvReset;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    //popu开始
    SlideFromTopPopup popu;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    //popu结束
    private List<Fragment> fragmentList;
    private String[] typeArray;
    /**
     * popu取消监听
     */
    private BasePopupWindow.OnDismissListener onDismissListener = new BasePopupWindow.OnDismissListener() {

        @Override
        public boolean onBeforeDismiss() {
            return super.onBeforeDismiss();
        }

        @Override
        public void onDismiss() {

        }
    };

    /**
     * 获取
     */
    private void getSevenAndThirty() {
        String userid = getIntent().getStringExtra("userid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        RxHttp.postForm(XyUrl.GET_SEVEN_AND_THIRTY_BLOOD_SUGAR)
                .addAll(map)
                .asResponse(SevenAndThirtyBloodSugarListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SevenAndThirtyBloodSugarListBean>() {
                    @Override
                    public void accept(SevenAndThirtyBloodSugarListBean updateBean) throws Exception {
                        initFragment(updateBean);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initFragment(SevenAndThirtyBloodSugarListBean bean) {
        String userid = getIntent().getStringExtra("userid");
        fragmentList = new ArrayList<>();
        typeArray = getResources().getStringArray(R.array.health_record_title);
        for (int i = 0; i < 2; i++) {
            SevenAndThirtyBloodSugarListFragment fragment = new SevenAndThirtyBloodSugarListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userid", userid);
            bundle.putString("type", i + "");
            bundle.putSerializable("bean", bean);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        vpContent.setOffscreenPageLimit(1);//ButterKnife报错处理
        tlTab.setupWithViewPager(vpContent);
    }

    @OnClick({R.id.img_top_back, R.id.ll_up_down, R.id.tv_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_back://popu
                finish();
                break;
            case R.id.tv_reset:
                Intent intent = new Intent(getPageContext(), SugarControlTargetSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_up_down://popu
                dismissPopu();
                break;
        }
    }

    /**
     * 初始化popu
     */
    private void initPopu() {
        popu = new SlideFromTopPopup(getPageContext(), getIntent().getStringExtra("userid"));
        popu.setOnDismissListener(onDismissListener);
    }

    /**
     * 隐藏popu
     */
    private void dismissPopu() {
        popu.showPopupWindow(rlTitle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_record_blood_sugar_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTopTitle.setText("血糖记录");
        tvReset.setVisibility(View.VISIBLE);
        hideTitleBar();
        initPopu();
        getSevenAndThirty();
    }


    private class TabAdapter extends FragmentPagerAdapter {

        private TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
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
