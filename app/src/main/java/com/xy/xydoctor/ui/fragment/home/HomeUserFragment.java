package com.xy.xydoctor.ui.fragment.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.bean.DoctorInfoBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.homesign.MyQRCodeActivity;
import com.xy.xydoctor.ui.activity.user.SettingActivity;
import com.xy.xydoctor.utils.DialogUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 我的Fragment
 * 作者: LYD
 * 创建日期: 2019/2/26 14:29
 */
public class HomeUserFragment extends BaseFragment implements SimpleImmersionOwner {
    private static final String TAG = "HomeUserFragment";
    @BindView(R.id.tv_sign_status)
    TextView tvSignStatus;

    @BindView(R.id.img_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_section_office)
    TextView tvSectionOffice;
    @BindView(R.id.tv_professional_title)
    TextView tvProfessionalTitle;
    @BindView(R.id.tv_personal_excel)
    TextView tvPersonalExcel;
    @BindView(R.id.tv_personal_desc)
    TextView tvPersonalDesc;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    @BindView(R.id.tv_user_chronic_disease_manager)
    TextView diseaseManagerTextView;
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_user;
    }

    @Override
    protected void init(View rootView) {
        getUserInfo();
        FloatingView.get().remove();
    }

    private void getUserInfo() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_INFO)
                .addAll(map)
                .asResponse(DoctorInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<DoctorInfoBean>() {
                    @Override
                    public void accept(DoctorInfoBean data) throws Exception {
                        //融云修改保存头像昵称
                        String docName = data.getDocname();
                        String docHeadImg = data.getImgurl();
                        SPStaticUtils.put("docName", docName);
                        SPStaticUtils.put("docHeadImg", docHeadImg);
                        //保存血糖设备码
                        String imei = data.getImei();
                        SPStaticUtils.put("imei", imei);
                        //保存血压设备码
                        String snnum = data.getSnnum();
                        SPStaticUtils.put("snnum", snnum);
                        String imgUrl = data.getImgurl();
                        Glide.with(Utils.getApp()).load(imgUrl).placeholder(R.drawable.head_man).error(R.drawable.head_man).into(imgHead);
                        tvUsername.setText(data.getDocname());
                        tvTel.setText(data.getTelephone());
                        tvHospital.setText(data.getHospitalname());
                        tvSectionOffice.setText(data.getDepname());
                        tvProfessionalTitle.setText(data.getDoczhc());
                        tvPersonalExcel.setText(data.getSpecialty());
                        tvPersonalDesc.setText(data.getContents());
                        int type = SPStaticUtils.getInt("docType");
                        if (3 == type) {
                            llQrcode.setVisibility(View.GONE);
                        } else {
                            llQrcode.setVisibility(View.VISIBLE);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.img_head, R.id.img_setting, R.id.rl_personal_excel, R.id.ll_personal_desc, R.id.rl_qrcode, R.id.tv_user_chronic_disease_manager})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_head:
                break;
            case R.id.img_setting:
                startActivity(new Intent(getPageContext(), SettingActivity.class));
                break;
            //擅长
            case R.id.rl_personal_excel:
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "擅长", "请输入擅长", InputType.TYPE_CLASS_TEXT, text1 -> {
                    toSave("specialty", text1);
                    tvPersonalExcel.setText(text1);
                });
                break;
            //简介
            case R.id.ll_personal_desc:
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "简介", "请输入简介", InputType.TYPE_CLASS_TEXT, text1 -> {
                    toSave("contents", text1);
                    tvPersonalDesc.setText(text1);
                });
                break;
            case R.id.rl_qrcode:
                startActivity(new Intent(getPageContext(), MyQRCodeActivity.class));
                break;
            case R.id.tv_user_chronic_disease_manager:
                //http://doch5.xiyuns.cn/doctorIndex?token=3641b52b51c56d82dfceb89e8652de3d
                intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "慢病管理");
                intent.putExtra("url", "http://doch5.xiyuns.cn/doctorIndex?token=" + SPStaticUtils.getString("token"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    /**
     * @param key
     */
    private void toSave(String key, String value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        RxHttp.postForm(XyUrl.EDIT_DOCTOR_INFO)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("获取成功");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_red).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }
}
