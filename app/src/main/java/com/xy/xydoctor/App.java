package com.xy.xydoctor;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDex;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.alibaba.sdk.android.push.register.OppoRegister;
import com.alibaba.sdk.android.push.register.VivoRegister;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.clj.fastble.BleManager;
import com.lyd.baselib.base.BaseApplication;
import com.lyd.librongim.myrongim.ImWarningMessage;
import com.lyd.librongim.myrongim.ImWarningMessageContentBean;
import com.lyd.librongim.myrongim.ImWarningMessageProvider;
import com.lyd.librongim.rongim.RongImInterface;
import com.lyd.librongim.rongim.RongImUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.xy.xydoctor.bean.CheckAdviceBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.ImWarningClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OkHttpInstance;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.chat.BloodPressureAbnormalActivity;
import com.xy.xydoctor.ui.activity.chat.DoctorAdviceActivity;
import com.xy.xydoctor.ui.activity.chat.SugarAbnormalActivity;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;

import io.reactivex.rxjava3.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.model.UIMessage;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: App
 * 作者: LYD
 * 创建日期: 2018/8/29 15:01
 */
public class App extends BaseApplication implements RongImInterface.ConversationClickListener, RongImInterface.ConnectionStatusListener, ImWarningClickListener, com.lyd.librongim.myrongim.ImWarningClickListener, RongImInterface.ConversationListBehaviorListener {
    private static final String TAG = "App";

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.background);
                return new MaterialHeader(context).setShowBezierWave(true)
                        .setColorSchemeColors(ContextCompat.getColor(context, R.color.main_red));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale)
                        .setAnimatingColor(context.getResources().getColor(R.color.main_red));
            }
        });
    }

    private String userId;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initRxHttp();
        initJPush();
        initImPush();
        initIm();
        initBle();
        initBugly();
        initAliPush();
    }

    /**
     * 初始化极光推送
     */
    private void initJPush() {
        // JPushUtils.init();
    }

    /**
     * 启用融云推送(必须在融云初始化之前配置)
     */
    private void initImPush() {
        //RongImUtils.initRongPush();
    }

    /**
     * 初始化Im
     */
    private void initIm() {
        //RongImUtils.initRongPush();
        //初始化Im
        RongImUtils.init(ConstantParam.IM_KEY);
        //单点登录
        RongImUtils.setConnectionStatusListener(this);
        //会话列表 头像点击
        RongImUtils.setConversationClickListener(this);
        //会话列表操作监听
        RongImUtils.setConversationListBehaviorListener(this);
        //添加自定义消息
        RongImUtils.setUserDefinedMessage(ImWarningMessage.class, new ImWarningMessageProvider(this));
        //发送消息监听
        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
            @Override
            public Message onSend(Message message) {
                MessageContent mMessageContent = message.getContent();
                int docType = SPStaticUtils.getInt("docType");
                if (3 == docType) {
                    if (mMessageContent instanceof ImageMessage) {
                        int imDocid = SPStaticUtils.getInt("imDocid", 0);
                        ImageMessage imageMessage = (ImageMessage) mMessageContent;
                        imageMessage.setExtra(imDocid + "");
                    }
                }
                return message;
            }

            @Override
            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
                return false;
            }
        });
    }


    /**
     * 初始化蓝牙
     */
    private void initBle() {
        BleManager.getInstance().init(Utils.getApp());
    }

    /**
     * 初始化RxHttp
     */
    private void initRxHttp() {
        OkHttpInstance.createInstance();
        //设置debug模式，默认为false，设置为true后，发请求，过滤"RxHttp"能看到请求日志
        RxHttp.setDebug(true);
        //添加公共参数
        Log.e(TAG, "添加公共参数开始执行");
        String token = SPStaticUtils.getString("token");
        RxHttp.setOnParamAssembly(p -> {
            Method method = p.getMethod();
            if (method.isGet()) { //Get请求

            } else if (method.isPost()) { //Post请求

            }
            return p.add("access_token", token).add("version", ConstantParam.SERVER_VERSION);
        });
    }

    /**
     * 初始化Bugly
     */
    private void initBugly() {
        //测试阶段建议设置成true，发布时设置为false
        //CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_ID, false);
    }


    @Override
    public void onCardClick(ImWarningMessage content, UIMessage uiMessage) {
        int messageId = uiMessage.getMessageId();
        String getContent = content.getContent();
        ImWarningMessageContentBean bean = GsonUtils.fromJson(getContent, ImWarningMessageContentBean.class);
        int type = bean.getType();
        if (1 == type || 2 == type) {
            getCheckAdvice(bean, messageId);
        }
    }

    /**
     * 获取建议是否
     *
     * @param bean
     */
    @SuppressLint("CheckResult")
    private void getCheckAdvice(ImWarningMessageContentBean bean, int messageId) {
        int wid = bean.getWid();
        int type = bean.getType();
        String typeName = bean.getTypename();
        String val = bean.getVal();
        RxHttp.postForm(XyUrl.CHECK_ADVICE)
                .add("id", wid)
                .asResponse(CheckAdviceBean.class)
                .subscribe(new Consumer<CheckAdviceBean>() {
                    @Override
                    public void accept(CheckAdviceBean checkAdviceBean) throws Exception {
                        String advice = checkAdviceBean.getContent();
                        int staticX = checkAdviceBean.getStaticX();
                        Intent intent;
                        if (1 == staticX) {
                            //1未处理
                            if (1 == type) {
                                //血压
                                intent = new Intent(Utils.getApp(), BloodPressureAbnormalActivity.class);
                                intent.putExtra("id", wid + "");
                                intent.putExtra("userid", userId);
                                intent.putExtra("messageId", messageId);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                //血糖
                                intent = new Intent(Utils.getApp(), SugarAbnormalActivity.class);
                                intent.putExtra("id", wid + "");
                                intent.putExtra("userid", userId);
                                intent.putExtra("messageId", messageId);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } else {
                            Log.e("RxHttp", "执行了");
                            //2已处理
                            intent = new Intent(Utils.getApp(), DoctorAdviceActivity.class);
                            intent.putExtra("advice", advice);
                            intent.putExtra("type", type + "");
                            intent.putExtra("typeName", typeName);
                            intent.putExtra("val", val);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public boolean onUserPortraitClick(UserInfo userInfo) {
        String docId = SPStaticUtils.getString("docId");
        String userId = userInfo.getUserId();
        if (!userId.equals(docId)) {
            Intent intent = new Intent(Utils.getApp(), PatientInfoActivity.class);
            intent.putExtra("userid", userId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        }
        return true;
    }

    @Override
    public void onChanged(RongIMClient.ConnectionStatusListener.ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            //            //用户账户在其他设备登录，本机会被踢掉线
            //            case KICKED_OFFLINE_BY_OTHER_CLIENT:
            //                String docid = SPStaticUtils.getString("docId");
            //                UPushUtils.deleteAlias(docid, "test");
            //                ToastUtils.showShort("该账号已在其他设备登录");
            //                SPStaticUtils.clear();
            //                ActivityUtils.finishAllActivities();
            //                Intent intent = new Intent(Utils.getApp(), LoginActivity.class);
            //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //                Utils.getApp().startActivity(intent);
            //                break;
            default:
                break;
        }
    }

    @Override
    public boolean onConversationClick(UIConversation uiConversation) {
        userId = uiConversation.getConversationSenderId();
        String targetUserId = uiConversation.getConversationTargetId();
        RxHttp.postForm(XyUrl.SET_READ_MESSAGE)
                .add("userid", targetUserId)
                .asResponse(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        // ToastUtils.showShort("");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
        return false;
    }


    /**
     * 初始化云推送通道
     *
     * @param
     */
    private void initAliPush() {
        createNotificationChannel();
        PushServiceFactory.init(this);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setLogLevel(CloudPushService.LOG_DEBUG);
        pushService.register(this, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e("aliPush", "初始化成功");
                String deviceId = pushService.getDeviceId();
                Log.e("aliPush", "设备deviceId==" + deviceId);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e("aliPush", "初始化失败-- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        //小米通道注册
        MiPushRegister.register(this, ConstantParam.MI_PUSH_ID, ConstantParam.MI_PUSH_KEY);
        //华为通道注册
        HuaWeiRegister.register(this);
        // OPPO通道注册
        OppoRegister.register(this, ConstantParam.OPPO_PUSH_KEY, ConstantParam.OPPO_PUSH_APP_SECRET);
        // VIVO通道注册
        VivoRegister.register(this);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //通知渠道的id
            String id = "1";
            //用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            //用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            //配置通知渠道的属性
            mChannel.setDescription(description);
            //设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            //设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

}
