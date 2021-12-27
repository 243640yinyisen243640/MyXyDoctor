package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.librongim.rongim.RongImUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.UpdateBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.UpdateUtils;
import com.xy.xydoctor.view.NumberProgressBar;
import com.xy.xydoctor.view.popup.UpdatePopup;
import com.xy.xydoctor.window.OutLoginPopup;

import java.io.File;
import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Call;
import rxhttp.wrapper.param.RxHttp;

/**
 * Author: LYD
 * Date: 2021/9/2 9:06
 * Description:
 */
public class SettingsPropertyActivity extends XYSoftUIBaseActivity implements View.OnClickListener, OnDownloadListener {
    private LinearLayout upLinearLayout;
    private TextView outLoginTextView;
    private TextView versionTextView;

    //更新进度
    private UpdatePopup updatePopup;
    //更新的apk新版本号
    private AppCompatTextView tvUpdateName;
    //更新的新apk大小
    private AppCompatTextView tvUpdateSize;
    //更新内容
    private AppCompatTextView tvUpdateContent;
    //进度条
    private NumberProgressBar pbUpdateProgress;
    //升级按钮
    private AppCompatTextView tvUpdateUpdate;
    //关闭按钮和线(显示隐藏)
    private LinearLayout llUpdateCancel;
    //关闭按钮(点击取消)
    private AppCompatImageView ivUpdateClose;
    //更新进度
    //更新网址
    private String updateUrl;
    private File updateApk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_setting_title);
        containerView().addView(initView());
        initPopup();
        initListener();

    }

    /**
     * 初始化更新弹窗
     */
    private void initPopup() {
        updatePopup = new UpdatePopup(getPageContext());
        tvUpdateName = updatePopup.findViewById(R.id.tv_update_name);
        tvUpdateSize = updatePopup.findViewById(R.id.tv_update_size);
        tvUpdateContent = updatePopup.findViewById(R.id.tv_update_content);
        pbUpdateProgress = updatePopup.findViewById(R.id.pb_update_progress);
        tvUpdateUpdate = updatePopup.findViewById(R.id.tv_update_update);
        llUpdateCancel = updatePopup.findViewById(R.id.ll_update_cancel);
        ivUpdateClose = updatePopup.findViewById(R.id.iv_update_close);
        tvUpdateUpdate.setOnClickListener(this);
        ivUpdateClose.setOnClickListener(this);
    }

    private void initListener() {
        upLinearLayout.setOnClickListener(this);
        outLoginTextView.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_setting, null);
        upLinearLayout = view.findViewById(R.id.ll_community_out_version);
        outLoginTextView = view.findViewById(R.id.tv_community_out_login);
        versionTextView = view.findViewById(R.id.tv_community_out_version);
        String appVersionName = AppUtils.getAppVersionName();
        versionTextView.setText("V" + appVersionName);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_community_out_version:
                Log.i("yys", "ll_community_out_version==");
                //                toUpdate();
                toUpDataNew();
                break;
            case R.id.tv_community_out_login:
                Log.i("yys", "tv_community_out_login==");
                toDoExit();
                break;
            //更新升级按钮
            case R.id.tv_update_update:
                //判断字体获取状态
                String text = (String) tvUpdateUpdate.getText();
                switch (text) {
                    case "升级":
                        //下载并安装
                        toDownLoadAndInstallApk();
                        break;
                    case "下载失败":
                        //下载并安装
                        toDownLoadAndInstallApk();
                        break;
                    case "下载中":
                        //没有动作
                        break;
                    case "立即安装":
                        //安装apk
                        AppUtils.installApp(updateApk);
                        break;
                }
                break;
            //关闭按钮
            case R.id.iv_update_close:
                updatePopup.dismiss();
                break;
            default:
                break;

        }
    }

    /**
     * 下载并安装apk
     */
    private void toDownLoadAndInstallApk() {
        UpdateUtils.downloadAndInstall(updateUrl, this);
    }

    private void toUpdate() {
        String token = SPStaticUtils.getString("token");
        HashMap map = new HashMap<>();
        map.put("version_code", AppUtils.getAppVersionCode());
        map.put("access_token", token);
        map.put("version", ConstantParam.SERVER_VERSION);
        RxHttp.postForm(XyUrl.GET_UPDATE)
                .addAll(map)
                .asResponse(UpdateBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<UpdateBean>() {
                    @Override
                    public void accept(UpdateBean updateBean) throws Exception {
                        toDoUpdate(updateBean);
                    }
                });
    }

    private void toUpDataNew() {
        String token = SPStaticUtils.getString("token");
        Call<String> requestCall = DataManager.getUpData(token, (call, response) -> {
            if (response.code == 200) {
                UpdateBean updateBean = (UpdateBean) response.object;
                toShowUpdateDialog(updateBean);
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    /**
     * 本地判断更新
     *
     * @param data
     */
    private void toDoUpdate(UpdateBean data) {
        int netVersion = data.getVersion();
        int localVersion = AppUtils.getAppVersionCode();
        if (localVersion < netVersion) {
            toShowUpdateDialog(data);
        } else {
            ToastUtils.showShort("当前版本已是最新版本");
        }
    }


    /**
     * 显示发现新版本Dialog
     *
     * @param data
     */
    private void toShowUpdateDialog(UpdateBean data) {
        updateUrl = data.getUpdateurl();
        String versionName = data.getVersionname();
        String apkSize = data.getAppsize();
        String updateContent = data.getUpcontent();
        tvUpdateName.setText(versionName);
        tvUpdateSize.setText(apkSize);
        tvUpdateContent.setText(updateContent);
        tvUpdateContent.setVisibility(updateContent == null ? View.GONE : View.VISIBLE);
        updatePopup.showPopupWindow();
    }


    /**
     * 执行退出操作
     */
    private void toDoExit() {
        OutLoginPopup outLoginPopup = new OutLoginPopup(this);
        TextView cancle = outLoginPopup.findViewById(R.id.tv_oper_cancel);
        TextView sure = outLoginPopup.findViewById(R.id.tv_oper_sure);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outLoginPopup.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongImUtils.logout();
                //JPushUtils.deleteAlias();
                CloudPushService pushService = PushServiceFactory.getCloudPushService();
                pushService.unbindAccount(new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFailed(String s, String s1) {

                    }
                });
                SPStaticUtils.clear();
                ActivityUtils.finishAllActivities();
                startActivity(new Intent(getPageContext(), LoginActivity.class));
            }
        });
        outLoginPopup.setAllowDismissWhenTouchOutside(false);
        outLoginPopup.showPopupWindow();
    }

    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {

    }

    @Override
    public void done(File apk) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void error(Exception e) {

    }
}
