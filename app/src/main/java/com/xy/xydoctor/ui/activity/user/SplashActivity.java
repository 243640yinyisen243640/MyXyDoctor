package com.xy.xydoctor.ui.activity.user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.lyd.librongim.myrongim.ImWarningMessage;
import com.lyd.librongim.myrongim.ImWarningMessageContentBean;
import com.lyd.librongim.myrongim.ImWarningMessageProvider;
import com.lyd.librongim.rongim.RongImInterface;
import com.lyd.librongim.rongim.RongImUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.bean.CheckAdviceBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OkHttpInstance;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.MainActivity;
import com.xy.xydoctor.ui.activity.chat.BloodPressureAbnormalActivity;
import com.xy.xydoctor.ui.activity.chat.DoctorAdviceActivity;
import com.xy.xydoctor.ui.activity.chat.SugarAbnormalActivity;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
import com.xy.xydoctor.utils.SharedPreferencesUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:启动屏(设置SplashTheme)
 * 作者: LYD
 * 创建日期: 2019/4/4 14:34
 */
public class SplashActivity extends AppCompatActivity implements RongImInterface.ConnectionStatusListener,RongImInterface.ConversationClickListener,RongImInterface.ConversationListBehaviorListener{
    /**
     * 是否同意隐私政策，1是，0或空为否
     */
    private String isAgreePricacyProtect;

    /**
     * 隐私政策弹出框
     */
    private Dialog protectDialog;
    private String spanColor = "#FFC600";//隐私政策span颜色值
    private Context context;


    private String userId;


    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_splash);
        initValues();
        setSplash();
    }

    private void initValues() {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantParam.IS_AGREE_PRIVACY_PROTECT, "0");
        SharedPreferencesUtils.getInfo(getPageContext(), map);
        isAgreePricacyProtect = map.get(ConstantParam.IS_AGREE_PRIVACY_PROTECT);
    }

    private Context getPageContext() {
        return context;
    }


    /**
     * 设置启动页
     */
    private void setSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!"1".equals(isAgreePricacyProtect)) {
                    showPrivacyProtectDialog();
                } else {
                    String token = SPStaticUtils.getString("token");
                    String isproperty = SPStaticUtils.getString("isproperty");
                    if (TextUtils.isEmpty(token) || TextUtils.isEmpty(isproperty)) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                }

            }
        }, 1000);
    }


    /**
     * 隐私权限提示
     */
    private void showPrivacyProtectDialog() {
        if (protectDialog == null) {
            protectDialog = new Dialog(getPageContext(), R.style.HuaHanSoft_Dialog_Base);
            View view = View.inflate(getPageContext(), R.layout.dialog_privacy_protect, null);
            protectDialog.setContentView(view);
            WindowManager.LayoutParams attributes = protectDialog.getWindow().getAttributes();
            attributes.width = 4 * XyScreenUtils.screenWidth(getPageContext()) / 5;

            attributes.height = XyScreenUtils.dip2px(getPageContext(), 400);
            protectDialog.getWindow().setAttributes(attributes);
            protectDialog.setCancelable(false);

            TextView serviceAgreementTextView = view.findViewById(R.id.tv_dpp_service_agreement);
            TextView disagreeTextView = view.findViewById(R.id.tv_dpp_disagree);
            TextView agressTextView = view.findViewById(R.id.tv_dpp_agree);

            String privacyProtectHint = getString(R.string.privacy_protect_hint1);
            SpannableString ss = new SpannableString(privacyProtectHint);

            ss.setSpan(new UnderLineClickSpan() {
                @Override
                public void onClick(View widget) {
                    jumpToUserPrivacy();
                }
            }, privacyProtectHint.indexOf("《"), privacyProtectHint.indexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor(spanColor)), privacyProtectHint.indexOf("《"), privacyProtectHint.indexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ss.setSpan(new UnderLineClickSpan() {
                @Override
                public void onClick(View widget) {
                    jumpToUserAgreement();
                }
            }, privacyProtectHint.lastIndexOf("《"), privacyProtectHint.lastIndexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor(spanColor)), privacyProtectHint.lastIndexOf("《"), privacyProtectHint.lastIndexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            serviceAgreementTextView.setText(ss);
            serviceAgreementTextView.setHighlightColor(Color.TRANSPARENT);
            serviceAgreementTextView.setMovementMethod(LinkMovementMethod.getInstance());

            disagreeTextView.setOnClickListener(v -> {
                protectDialog.dismiss();
                finish();
            });
            agressTextView.setOnClickListener(v -> {
                SPStaticUtils.put("is_agree", "1");
                initRxHttp();
                initIm();
                initBle();
                initAliPush();
                protectDialog.dismiss();
                startActivity(new Intent(getPageContext(), LoginActivity.class));
                finish();
            });
            protectDialog.show();
        }
    }

    /**
     * 初始化云推送通道
     *
     * @param
     */
    private void initAliPush() {
        createNotificationChannel();
        PushServiceFactory.init(getApplication());
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
        HuaWeiRegister.register(getApplication());
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
        RongImUtils.setUserDefinedMessage(ImWarningMessage.class, new ImWarningMessageProvider((content, uiMessage) -> {
            int messageId = uiMessage.getMessageId();
            String getContent = content.getContent();
            ImWarningMessageContentBean bean = GsonUtils.fromJson(getContent, ImWarningMessageContentBean.class);
            int type = bean.getType();
            if (1 == type || 2 == type) {
                getCheckAdvice(bean, messageId);
            }
        }));
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
     * 页面跳转-用户政策
     */
    private void jumpToUserPrivacy() {
        Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
        intent.putExtra("title", "用户服务协议");
        intent.putExtra("url", "file:///android_asset/user_protocol.html");
        startActivity(intent);
    }

    /**
     * 页面跳转-用户政策
     */
    private void jumpToUserAgreement() {
        Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
        intent.putExtra("title", "隐私政策");
        intent.putExtra("url", "http://chronics.xiyuns.cn/index/caseapp");
        startActivity(intent);
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

    private abstract class UnderLineClickSpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
            ds.clearShadowLayer();
        }
    }

    //    @Override
    //    public void onWindowFocusChanged(boolean hasFocus) {
    //        super.onWindowFocusChanged(hasFocus);
    //
    //        if (hasFocus && !isFirst) {
    //            showPrivacyProtectDialog();
    //        }
    //    }


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
