package com.xy.xydoctor.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.util.notification.NotificationUtils;
import com.lyd.baselib.util.sp.SPUtils;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.lyd.librongim.rongim.RongImInterface;
import com.lyd.librongim.rongim.RongImUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.BuildConfig;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.ImTokenBean;
import com.xy.xydoctor.bean.IsFamilyBean;
import com.xy.xydoctor.bean.UpdateBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.mydevice.HomeScanActivity;
import com.xy.xydoctor.utils.DialogUtils;
import com.xy.xydoctor.utils.UpdateUtils;
import com.xy.xydoctor.view.NumberProgressBar;
import com.xy.xydoctor.view.popup.UpdatePopup;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
import retrofit2.Call;
import rxhttp.wrapper.param.RxHttp;

/**
 * ??????: ?????????
 * ??????: LYD
 * ????????????: 2019/6/11 11:50
 */
@BindEventBus
public class MainActivity extends BaseEventBusActivity implements IUnReadMessageObserver, View.OnClickListener, OnDownloadListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    //????????????
    private UpdatePopup updatePopup;
    //?????????apk????????????
    private AppCompatTextView tvUpdateName;
    private LinearLayout closeLinearLayout;
    //????????????apk??????
    private AppCompatTextView tvUpdateSize;
    //????????????
    private AppCompatTextView tvUpdateContent;
    //?????????
    private NumberProgressBar pbUpdateProgress;
    //????????????
    private AppCompatTextView tvUpdateUpdate;

    //????????????(????????????)
    private AppCompatImageView ivUpdateClose;
    //????????????
    //????????????
    private String updateUrl;
    private File updateApk;
    private static final int GET_UPDATE_DATA = 10012;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //???????????????????????????
        getAllUsers();
        //????????????????????????
        initBottomBar();
        //??????
        initPopup();
        //????????????token
        getImKitToken();
        //????????????????????????
        getImUnReadMsgCount();
        //????????????????????????
        setUPushAlias();
        //????????????
        toUpDataNew();
        //??????????????????
        //getIsFamily();
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkNotifySettings();
    }

    /**
     * ??????????????????
     */
    private void checkNotifySettings() {
        boolean notificationEnabled = NotificationUtils.isNotificationEnabled(getPageContext());
        if (notificationEnabled) {
        } else {
            DialogUtils.getInstance().showDialog(getPageContext(), "", "????????????????????????????????????,???????????????.", true, new DialogUtils.DialogCallBack() {
                @Override
                public void execEvent() {
                    NotificationUtils.goToSettings(getPageContext());
                }
            });
        }
    }

    /**
     * ??????
     */
    private void getIsFamily() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.IS_FAMILY)
                .addAll(map)
                .asResponse(IsFamilyBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<IsFamilyBean>() {
                    @Override
                    public void accept(IsFamilyBean isFamilyBean) throws Exception {
                        //MenuItem homeSignItem = bnvMain.getMenu().findItem(R.id.navigation_home_sign);
                        //int isfamily = isFamilyBean.getIsfamily();
                        //if (1 == isfamily) {
                        //    homeSignItem.setVisible(true);
                        //} else {
                        //    homeSignItem.setVisible(false);
                        //}
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
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
        ivUpdateClose = updatePopup.findViewById(R.id.iv_update_close);
        closeLinearLayout = updatePopup.findViewById(R.id.ll_update_cancel);
        tvUpdateUpdate.setOnClickListener(this);
        ivUpdateClose.setOnClickListener(this);
    }


    /**
     * ?????????????????????
     */
    private void initBottomBar() {
        int type = SPStaticUtils.getInt("docType");
        String isproperty = SPStaticUtils.getString("isproperty");
        MenuItem patientItem = bnvMain.getMenu().findItem(R.id.navigation_patient);
        MenuItem doctorItem = bnvMain.getMenu().findItem(R.id.navigation_doctor);
        MenuItem workItem = bnvMain.getMenu().findItem(R.id.navigation_home);
        MenuItem myItem = bnvMain.getMenu().findItem(R.id.navigation_user);
        MenuItem managerItem = bnvMain.getMenu().findItem(R.id.navigation_community_manager);
        // type:3:??????  4:??????  10:??????
        //isproperty???????????????????????? 1:????????? 2:?????????
        //??????????????????????????????????????????????????????
        //??????????????????????????????????????????????????????
        //???????????????????????????????????????
        int startDestinationID = R.id.navigation_home;

        if (3 == type) {
            startDestinationID = R.id.navigation_home;
            setTitleVisible();
            workItem.setVisible(true);
            patientItem.setVisible(false);
            doctorItem.setVisible(true);
            myItem.setVisible(true);
            managerItem.setVisible(false);
        } else if (4 == type) {
            startDestinationID = R.id.navigation_home;
            setTitleVisible();
            if ("2".equals(isproperty)) {
                workItem.setVisible(true);
                patientItem.setVisible(true);
                doctorItem.setVisible(false);
                myItem.setVisible(true);
                managerItem.setVisible(true);
            } else {
                workItem.setVisible(true);
                patientItem.setVisible(true);
                doctorItem.setVisible(false);
                myItem.setVisible(true);
                managerItem.setVisible(false);
            }

        } else {
            startDestinationID = R.id.navigation_community_manager;
            FloatingView.get().remove();
            hideTitleBar();
            hideBack();
            showLine();
            workItem.setVisible(false);
            patientItem.setVisible(false);
            doctorItem.setVisible(false);
            myItem.setVisible(false);
            managerItem.setVisible(true);
        }

        getTvMore().setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getPageContext(), R.drawable.home_index_top_scan), null, null, null);
        getTvMore().setCompoundDrawablePadding(5);
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(getPageContext(), HomeScanActivity.class);
                                intent.putExtra("type", "main");
                                startActivity(intent);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("???????????????????????????");
                            }
                        }).request();
            }
        });
        //???????????????????????????
        bnvMain.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bnvMain, navController);
        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.navigation_main_activity);
        navGraph.setStartDestination(startDestinationID);
        navController.setGraph(navGraph);
        bnvMain.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.navigation_home:
                    navController.navigate(R.id.navigation_home);
                    showTitleBar();
                    hideBack();
                    showLine();
                    setTitle("?????????");
                    getTvMore().setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_patient:
                    navController.navigate(R.id.navigation_patient);
                    showTitleBar();
                    hideBack();
                    showLine();
                    setTitle("????????????");
                    getTvMore().setVisibility(View.GONE);
                    break;
                case R.id.navigation_doctor:
                    navController.navigate(R.id.navigation_doctor);
                    showTitleBar();
                    hideBack();
                    showLine();
                    setTitle("????????????");
                    getTvMore().setVisibility(View.GONE);
                    break;
                case R.id.navigation_community_manager:
                    navController.navigate(R.id.navigation_community_manager);//??????????????????????????????
                    hideTitleBar();
                    hideBack();
                    hideLine();
                    setTitle("????????????");
                    getTvMore().setVisibility(View.GONE);
                    break;
                // case R.id.navigation_home_sign:
                //     navController.navigate(R.id.navigation_home_sign);
                //     showTitleBar();
                //     hideBack();
                //     setTitle("??????");
                //     break;
                case R.id.navigation_user:
                    navController.navigate(R.id.navigation_user);
                    hideTitleBar();
                    hideLine();
                    getTvMore().setVisibility(View.GONE);
                    break;
            }
            return false;
        });
    }

    private void setTitleVisible() {
        showTitleBar();
        hideBack();
        showLine();
        setTitle("?????????");
        getTvMore().setVisibility(View.VISIBLE);
    }

    private void initWindow(boolean isVisibility) {
        if (isVisibility && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            //????????????????????????????????????????????????
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /**
     * ????????????
     */
    private void setUPushAlias() {
        String docId = SPStaticUtils.getString("docId");
        String aliasBefore = BuildConfig.ENVIRONMENT ? "p_" : "t_";
        String bindAccount = aliasBefore + docId;
        Log.e("aliPush", "??????==" + bindAccount);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.bindAccount(bindAccount, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("aliPush", "??????????????????" + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.e("aliPush", "??????????????????");
            }
        });
    }


    /**
     * ????????????????????????
     */
    private void getImUnReadMsgCount() {
        final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE};
        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
    }


    /**
     * ??????????????????
     */
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
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {


                    }
                });
    }

    /***
     * ????????????
     * @param data
     */
    private void toDoUpdate(UpdateBean data) {
        int netVersion = data.getVersion();
        int localVersion = AppUtils.getAppVersionCode();
        if (localVersion < netVersion) {
            toShowUpdateDialog(data);
        }
    }

    /**
     * ????????????
     *
     * @param data
     */
    private void toShowUpdateDialog(UpdateBean data) {
        if ("1".equals(data.getIs_force())) {
            closeLinearLayout.setVisibility(View.GONE);
        } else {
            closeLinearLayout.setVisibility(View.VISIBLE);
        }
        updateUrl = data.getUpdateurl();
        String versionName = data.getVersionname();
        String apkSize = data.getAppsize();
        String updateContent = data.getUpcontent();
        tvUpdateName.setText(versionName);
        tvUpdateSize.setText(apkSize);
        tvUpdateContent.setText(updateContent);
        tvUpdateContent.setVisibility(updateContent == null ? View.GONE : View.VISIBLE);
        updatePopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        updatePopup.setBackPressEnable(false);
        updatePopup.setAllowDismissWhenTouchOutside(false);
        updatePopup.showPopupWindow();
    }

    /**
     * ????????????token
     */
    private void getImKitToken() {
        HashMap map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_IM_TOKEN)
                .addAll(map)
                .asResponse(ImTokenBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ImTokenBean>() {
                    @Override
                    public void accept(ImTokenBean imTokenBean) throws Exception {
                        String imToken = imTokenBean.getToken();
                        SPStaticUtils.put("loginImToken", imToken);
                        RongImUtils.logout();
                        //?????????????????????
                        RongImUtils.connect(imToken, new RongImInterface.ConnectCallback() {
                            @Override
                            public void onSuccess() {
                                //??????????????????
                                RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
                                    @Override
                                    public void onSuccess(List<Conversation> conversations) {
                                        if (conversations != null && conversations.size() > 0) {
                                            for (int i = 0; i < conversations.size(); i++) {
                                                Conversation conversation = conversations.get(i);
                                                long sentTime = conversation.getSentTime();
                                                String targetId = conversation.getTargetId();
                                                int unreadMessageCount = conversation.getUnreadMessageCount();
                                                Log.e(TAG, "??????==" + sentTime);
                                                Log.e(TAG, "?????????Id==" + targetId);
                                                Log.e(TAG, "??????????????????==" + unreadMessageCount);
                                                MessageContent messageContent = conversation.getLatestMessage();
                                                if (messageContent instanceof TextMessage) {
                                                    //????????????
                                                    TextMessage textMessage = (TextMessage) messageContent;
                                                    String str = textMessage.getContent();
                                                    Log.e(TAG, "????????????????????????==" + str);
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {
                                    }
                                }, Conversation.ConversationType.PRIVATE);
                            }
                        });
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * ??????
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ????????????????????????
     */
    public void exit() {
        //if ((System.currentTimeMillis() - exitTime) > 2000) {
        //    ToastUtils.showShort("????????????????????????");
        //    exitTime = System.currentTimeMillis();
        //} else {
        //    ActivityUtils.finishAllActivities();
        //    System.exit(0);
        //}
        ClickUtils.back2HomeFriendly("????????????????????????");
    }

    /**
     * ???????????????????????????????????????????????????
     * <p>
     * ???????????????????????????????????????
     */
    @Override
    protected void onDestroy() {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
        super.onDestroy();
    }


    /**
     * ????????????????????????
     *
     * @param count
     */
    @Override
    public void onCountChanged(int count) {
        Log.e(TAG, "count: " + count);
        SPStaticUtils.put("imUnReadMsgCount", count);
        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.IM_UNREAD_MSG_COUNT));
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
        }
    }

    private void toDownLoadAndInstallApk() {
        UpdateUtils.downloadAndInstall(updateUrl, this);
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
    }

    @Override
    public void cancel() {
    }

    @Override
    public void error(Exception e) {
        e.printStackTrace();
        tvUpdateUpdate.setText("????????????");
    }


    /**
     *
     */
    private void getAllUsers() {
        int docType = SPStaticUtils.getInt("docType");
        HashMap<String, Object> map = new HashMap<>();
        map.put("gid", 0 + "");
        if (3 == docType) {
            int imDocid = SPStaticUtils.getInt("imDocid", 0);
            map.put("userid", imDocid);
        }
        RxHttp.postForm(XyUrl.GET_GROUP_USER)
                .addAll(map)
                .asResponseList(GroupUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupUserBean>>() {
                    @Override
                    public void accept(List<GroupUserBean> list) throws Exception {
                        Log.e(TAG, "allUsers??????==" + list.size());
                        GroupUserBean doctorUserBean = new GroupUserBean();
                        if (3 == docType) {
                            int docId = SPStaticUtils.getInt("imDocid");
                            String docName = SPStaticUtils.getString("imDocName");
                            String docHeadImg = SPStaticUtils.getString("imDocPic");
                            doctorUserBean.setUserid(docId);
                            doctorUserBean.setPicture(docHeadImg);
                            doctorUserBean.setNickname(docName);
                        } else {
                            String docId = SPStaticUtils.getString("docId");
                            String docName = SPStaticUtils.getString("docName");
                            String docHeadImg = SPStaticUtils.getString("docHeadImg");
                            int intDoctorId = TurnsUtils.getInt(docId, 0);
                            doctorUserBean.setUserid(intDoctorId);
                            doctorUserBean.setPicture(docHeadImg);
                            doctorUserBean.setNickname(docName);
                        }
                        list.add(doctorUserBean);
                        SPUtils.putBean("allUsers", list);
                        RongImUtils.setCurrentUserInfo(list, doctorUserBean);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.cancel();
                    }
                });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.PATIENT_ADD:
            case ConstantParam.EventCode.DIRECTOR_REFRESH:
                getAllUsers();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int docType = SPStaticUtils.getInt("docType");
        //type:3:??????  4:??????  10:??????
        if (10 != docType) {
            FloatingView.get().attach(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        FloatingView.get().detach(this);
    }

    private void toUpDataNew() {
        String token = SPStaticUtils.getString("token");
        Call<String> requestCall = DataManager.getUpData(token, (call, response) -> {
            if (response.code == 200) {
                UpdateBean updateBean = (UpdateBean) response.object;
                toDoUpdate(updateBean);
            }
        }, (call, t) -> {

        });
    }
}
