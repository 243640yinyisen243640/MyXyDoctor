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
import com.xy.xydoctor.datamanager.DataManager;
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
import retrofit2.Call;
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


    //????????????
    private UpdatePopup updatePopup;
    //?????????apk????????????
    private AppCompatTextView tvUpdateName;
    //????????????apk??????
    private AppCompatTextView tvUpdateSize;
    //????????????
    private AppCompatTextView tvUpdateContent;
    //?????????
    private NumberProgressBar pbUpdateProgress;
    //????????????
    private AppCompatTextView tvUpdateUpdate;
    //??????????????????(????????????)
    private LinearLayout llUpdateCancel;
    //????????????(????????????)
    private AppCompatImageView ivUpdateClose;
    //????????????
    //????????????
    private String updateUrl;
    private File updateApk;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("??????");
        initPopup();
        setAppName();
    }

    private void setAppName() {
        String appVersionName = AppUtils.getAppVersionName();
        tvVersionName.setText("V" + appVersionName);
    }


    /**
     * ?????????????????????
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
                //                toUpdate();
                toUpDataNew();
                break;
            case R.id.tv_user_agreement:
                intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "??????????????????");
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
     * ??????????????????
     *
     * @param data
     */
    private void toDoUpdate(UpdateBean data) {
        int netVersion = data.getVersion();
        int localVersion = AppUtils.getAppVersionCode();
        if (localVersion < netVersion) {
            toShowUpdateDialog(data);
        } else {
            TipUtils.getInstance().showToast(getPageContext(), "??????????????????????????????");
        }
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
     * ?????????????????????Dialog
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
     * ???????????????apk
     */
    private void toDownLoadAndInstallApk() {
        UpdateUtils.downloadAndInstall(updateUrl, this);
    }

    /**
     * ????????????
     */
    private void toExit() {
        toDoExit();
    }


    /**
     * ??????????????????
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
        //?????????
        int curr = (int) (progress / (double) max * 100.0);
        //?????????
        tvUpdateUpdate.setText("?????????");
        pbUpdateProgress.setVisibility(View.VISIBLE);
        pbUpdateProgress.setProgress(curr);
    }

    @Override
    public void done(File apk) {
        updateApk = apk;
        //????????????
        tvUpdateUpdate.setText("????????????");
        pbUpdateProgress.setVisibility(View.GONE);
        //updatePopup.dismiss();
    }

    @Override
    public void cancel() {
    }

    @Override
    public void error(Exception e) {
        e.printStackTrace();
        tvUpdateUpdate.setText("????????????");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //??????????????????
            case R.id.tv_update_update:
                //????????????????????????
                String text = (String) tvUpdateUpdate.getText();
                switch (text) {
                    case "??????":
                        //???????????????
                        toDownLoadAndInstallApk();
                        break;
                    case "????????????":
                        //???????????????
                        toDownLoadAndInstallApk();
                        break;
                    case "?????????":
                        //????????????
                        break;
                    case "????????????":
                        //??????apk
                        AppUtils.installApp(updateApk);
                        break;
                }
                break;
            //????????????
            case R.id.iv_update_close:
                updatePopup.dismiss();
                break;
            default:
                break;
        }
    }
}