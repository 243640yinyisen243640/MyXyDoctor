package com.xy.xydoctor.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.librongim.rongim.RongImUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.bean.UpdateBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.mydevice.DeviceAddListActivity;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.UpdateUtils;
import com.xy.xydoctor.view.NumberProgressBar;
import com.xy.xydoctor.view.popup.UpdatePopup;
import com.xy.xydoctor.window.OutLoginPopup;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class SettingActivity extends BaseActivity implements OnDownloadListener, View.OnClickListener {
    private static final String TAG = "SettingActivity";
    @BindView(R.id.tv_device_add)
    TextView tvDeviceAdd;
    @BindView(R.id.tv_change_pwd)
    TextView tvChangePwd;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;


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
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("设置");
        initPopup();
        setAppName();
    }

    private void setAppName() {
        String appVersionName = AppUtils.getAppVersionName();
        tvVersionName.setText("V" + appVersionName);
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

    @OnClick({R.id.tv_question_feed_back, R.id.tv_device_add, R.id.tv_change_pwd, R.id.ll_update, R.id.tv_user_agreement, R.id.bt_exit})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_question_feed_back:
                intent = new Intent(getPageContext(), FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_device_add:
                intent = new Intent(getPageContext(), DeviceAddListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_change_pwd:
                startActivity(new Intent(getPageContext(), SetPwdActivity.class));
                break;
            case R.id.ll_update:
                toUpdate();
                break;
            case R.id.tv_user_agreement:
                intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "用户服务协议");
                intent.putExtra("url", "file:///android_asset/user_protocol.html");
                startActivity(intent);
                break;
            case R.id.bt_exit:
                toExit();
                break;
        }
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
            TipUtils.getInstance().showToast(getPageContext(), "当前版本已是最新版本");
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
     * 下载并安装apk
     */
    private void toDownLoadAndInstallApk() {
        UpdateUtils.downloadAndInstall(updateUrl, this);
    }

    /**
     * 退出弹窗
     */
    private void toExit() {
        toDoExit();
        //        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要退出登录?", true, () -> {
        //            toDoExit();
        //        });
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
        //百分比
        int curr = (int) (progress / (double) max * 100.0);
        //下载中
        tvUpdateUpdate.setText("下载中");
        pbUpdateProgress.setVisibility(View.VISIBLE);
        pbUpdateProgress.setProgress(curr);
    }

    @Override
    public void done(File apk) {
        updateApk = apk;
        //立即安装
        tvUpdateUpdate.setText("立即安装");
        pbUpdateProgress.setVisibility(View.GONE);
        //updatePopup.dismiss();
    }

    @Override
    public void cancel() {
    }

    @Override
    public void error(Exception e) {
        e.printStackTrace();
        tvUpdateUpdate.setText("下载失败");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}