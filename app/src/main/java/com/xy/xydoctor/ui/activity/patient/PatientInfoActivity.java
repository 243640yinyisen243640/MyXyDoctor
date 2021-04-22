package com.xy.xydoctor.ui.activity.patient;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SPStaticUtils;
import com.clj.fastble.BleManager;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.util.TableLayoutUtils;
import com.lyd.librongim.rongim.RongImUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.TabPagePatientInfoAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.IsLiverFileBean;
import com.xy.xydoctor.bean.UserInfoBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.fragment.patientinfo.PatientHealthArchiveFragment;
import com.xy.xydoctor.ui.fragment.patientinfo.PatientHealthRecordFragment;
import com.xy.xydoctor.ui.fragment.patientinfo.PatientLiverFilesFragment;
import com.xy.xydoctor.ui.fragment.patientinfo.PatientOnlineTestFragment;
import com.xy.xydoctor.view.popup.OnlineTestPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:患者详情
 * 作者: LYD
 * 创建日期: 2019/3/4 11:42
 */
public class PatientInfoActivity extends BaseActivity {
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    @BindView(R.id.rl_send_msg)
    RelativeLayout rlSendMsg;
    @BindView(R.id.img_online_test_add)
    ImageView imgOnlineTestAdd;

    String userid;
    @BindView(R.id.btn_back_new)
    Button btnBackNew;
    @BindView(R.id.tv_title_new)
    TextView tvTitleNew;
    @BindView(R.id.tv_base_right_new)
    TextView tvBaseRightNew;
    private UserInfoBean userInfoBean;
    private OnlineTestPopup onlineTestPopup;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //状态栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_red).init();
        hideTitleBar();
        hideLine();
        TableLayoutUtils.setTabRippleColor(tlTab, getPageContext());
        tvTitleNew.setText("患者详情");
        initPopup();
        getUserInfo();
        getIsLiverInfo();
    }

    /**
     * 初始化弹窗
     */
    private void initPopup() {
        onlineTestPopup = new OnlineTestPopup(getPageContext(), getIntent().getStringExtra("userid"));
    }

    /**
     * 获取患者信息
     */
    private void getUserInfo() {
        userid = getIntent().getStringExtra("userid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        RxHttp.postForm(XyUrl.GET_USER_INFO)
                .addAll(map)
                .asResponseList(UserInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<UserInfoBean>>() {
                    @Override
                    public void accept(List<UserInfoBean> myTreatPlanBeans) throws Exception {
                        List<UserInfoBean> data = myTreatPlanBeans;
                        userInfoBean = data.get(0);
                        //保存用户随机码
                        String guid = userInfoBean.getUserId();
                        //后边单独接口用到
                        SPStaticUtils.put("userGuid", guid);
                        //RxHttp请求用到
                        String token = SPStaticUtils.getString("token");
                        //在此添加公共参数
                        RxHttp.setOnParamAssembly(p -> {
                            Method method = p.getMethod();
                            if (method.isGet()) { //Get请求

                            } else if (method.isPost()) { //Post请求

                            }
                            return p.add("guid", guid)
                                    .add("access_token", token)
                                    .add("version", ConstantParam.SERVER_VERSION);
                        });
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 档案判断
     */
    private void getIsLiverInfo() {
        HashMap map = new HashMap<>();
        RxHttp.postForm(XyUrl.IS_LIVER_FILE)
                .addAll(map)
                .asResponse(IsLiverFileBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<IsLiverFileBean>() {
                    @Override
                    public void accept(IsLiverFileBean isLiverFileBean) throws Exception {
                        String archivestyle = isLiverFileBean.getArchivestyle();
                        initFragment(archivestyle);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 初始化Fragment
     */
    private void initFragment(String type) {
        String userid = getIntent().getStringExtra("userid");
        List<Fragment> fragmentList = new ArrayList<>();
        PatientHealthRecordFragment patientHealthRecordFragment = new PatientHealthRecordFragment();
        //档案 Start
        PatientHealthArchiveFragment patientHealthArchiveFragment = new PatientHealthArchiveFragment();
        PatientLiverFilesFragment patientLiverFilesFragment = new PatientLiverFilesFragment();
        //档案 End
        PatientOnlineTestFragment patientOnlineTestFragment = new PatientOnlineTestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);
        bundle.putString("archivestyle", type);
        patientHealthArchiveFragment.setArguments(bundle);
        patientHealthRecordFragment.setArguments(bundle);
        patientLiverFilesFragment.setArguments(bundle);
        patientOnlineTestFragment.setArguments(bundle);

        fragmentList.add(patientHealthRecordFragment);
        if ("1".equals(type)) {
            fragmentList.add(patientHealthArchiveFragment);
        } else {
            fragmentList.add(patientLiverFilesFragment);
        }
        fragmentList.add(patientOnlineTestFragment);
        TabPagePatientInfoAdapter tabAdapter = new TabPagePatientInfoAdapter(getSupportFragmentManager(), this, "patientInfo", fragmentList);
        vpContent.setAdapter(tabAdapter);
        vpContent.setOffscreenPageLimit(fragmentList.size() - 1);
        tlTab.setupWithViewPager(vpContent);
        //设置Tablayout
        for (int i = 0; i < fragmentList.size(); i++) {
            TabLayout.Tab tab = tlTab.getTabAt(i);
            //注意！！！这里就是添加我们自定义的布局
            assert tab != null;
            tab.setCustomView(tabAdapter.getCustomView(i));
            //这里是初始化时，默认item0被选中，setSelected（true）是为了给图片和文字设置选中效果，代码在文章最后贴出
            if (i == 0) {
                assert tab.getCustomView() != null;
                tab.getCustomView().findViewById(R.id.view).setSelected(true);
                tab.getCustomView().findViewById(R.id.tv_patient_info_title).setSelected(true);
            }
        }
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                assert tab.getCustomView() != null;
                //发送消息判断显示隐藏
                int position = tab.getPosition();
                if (2 == position) {
                    rlSendMsg.setVisibility(View.GONE);
                    imgOnlineTestAdd.setVisibility(View.VISIBLE);
                } else {
                    rlSendMsg.setVisibility(View.VISIBLE);
                    imgOnlineTestAdd.setVisibility(View.GONE);
                }
                //标题
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_patient_info_title);
                tvTitle.setSelected(true);
                tvTitle.setTextSize(18);
                //线
                tab.getCustomView().findViewById(R.id.view).setVisibility(View.VISIBLE);
                vpContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                assert tab.getCustomView() != null;
                //标题
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_patient_info_title);
                tvTitle.setSelected(false);
                tvTitle.setTextSize(16);
                //线
                tab.getCustomView().findViewById(R.id.view).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 解除FastBle的绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
        //移除Guid公共参数
        String token = SPStaticUtils.getString("token");
        RxHttp.setOnParamAssembly(p -> {
            Method method = p.getMethod();
            if (method.isGet()) { //Get请求

            } else if (method.isPost()) { //Post请求

            }
            return p.add("access_token", token).add("version", ConstantParam.SERVER_VERSION);
        });
    }


    @OnClick({R.id.rl_send_msg, R.id.img_online_test_add, R.id.btn_back_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_new:
                finish();
                break;
            //发送消息
            case R.id.rl_send_msg:
                String userName = TextUtils.isEmpty(userInfoBean.getNickname()) ? "" : userInfoBean.getNickname();
                RongImUtils.startPrivateChat(getPageContext(), userInfoBean.getUserid() + "", userName);
                break;
            //显示添加
            case R.id.img_online_test_add:
                onlineTestPopup.showPopupWindow(imgOnlineTestAdd);
                break;
        }
    }
}
