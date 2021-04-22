package com.lyd.librongim.rongim;


import android.content.Context;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;
import com.lyd.librongim.myrongim.ConstantParam;
import com.lyd.librongim.myrongim.GroupUserBean;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;

/**
 * 描述: 融云相关工具类
 * 作者: LYD
 * 创建日期: 2019/12/2 16:35
 */
public class RongImUtils {
    private static final String TAG = "RongImUtils";

    public RongImUtils() {
    }


    /**
     * 0.启用融云推送(必须在融云初始化之前配置)
     */
    public static void initRongPush() {
        PushConfig config = new PushConfig.Builder()
                .enableMiPush(ConstantParam.MI_PUSH_ID, ConstantParam.MI_PUSH_KEY)
                .enableOppoPush(ConstantParam.OPPO_PUSH_KEY, ConstantParam.OPPO_PUSH_APP_SECRET)
                .enableVivoPush(true)
                .enableHWPush(false)
                .build();
        RongPushClient.setPushConfig(config);
    }

    /**
     * 1.初始化融云
     * //初始化SDK,在整个应用程序全局只需要调用一次,建议在Application继承类中调用
     * //如果您基于IMKitSDK进行开发,那在初始化SDK之后,请通过RongIM.getInstance()方法获取实例,然后调用相应的api方法.
     * //不要使用RongIMClient实例去调用相关接口,否则会导致 UI 显示异常.
     */
    public static void init(String appKey) {
        RongIM.init(Utils.getApp(), appKey);
    }


    /**
     * 2.连接融云
     * //连接服务器,在整个应用程序全局,只需要调用一次.
     *
     * @param token 融云token(后台返回的token)
     * @param
     */
    public static void connect(String token, RongImInterface.ConnectCallback callback) {
        RongImClientConnectCallback connectCallback = new RongImClientConnectCallback(callback);
        RongIM.connect(token, connectCallback);
    }

    /**
     * 3.成功连接融云后,设置用户信息(头像和昵称)
     * //参考官网连接: https://www.rongcloud.cn/docs/android.html#user_info
     *
     * @param list 所有用户信息
     */
    public static void setCurrentUserInfo(final List<GroupUserBean> list, GroupUserBean bean) {
        list.add(bean);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String imUserId) {
                for (GroupUserBean userBean : list) {
                    String serverId = userBean.getUserid() + "";
                    if (serverId.equals(imUserId)) {
                        UserInfo userInfo = new UserInfo(serverId, userBean.getNickname(), Uri.parse(userBean.getPicture()));
                        //调用刷新方法, 刷新用户信息缓存
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                        return userInfo;
                    }
                }
                return null;
            }
        }, false);
    }


    /**
     * 4.打开单聊窗口
     *
     * @param context      上下文
     * @param targetUserId 要与之聊天的用户 Id.
     * @param title        聊天的标题,开发者需要在聊天界面通过intent.getData().getQueryParameter("title")
     *                     获取该值, 再手动设置为聊天界面的标题.
     */
    public static void startPrivateChat(Context context, String targetUserId, String title) {
        //单行不要收起
        RongIM.getInstance().startPrivateChat(context, targetUserId, title);
    }


    /**
     * 5.设置连接状态变化的监听器.
     * //单点登录
     */
    public static void setConnectionStatusListener(RongImInterface.ConnectionStatusListener listener) {
        //单行不要收起
        RongIM.setConnectionStatusListener(new RongImClientConnectionStatusListener(listener));
    }


    /**
     * 6.会话界面操作的监听器.
     * //五种点击,目前只用到了(头像点击)
     */
    public static void setConversationClickListener(RongImInterface.ConversationClickListener listener) {
        //单行不要收起
        RongIM.setConversationClickListener(new RongImConversationClickListener(listener));
    }

    /**
     * 7.会话列表界面操作的监听器.
     * //四种点击,目前只用到了(会话列表Item点击)
     */
    public static void setConversationListBehaviorListener(RongImInterface.ConversationListBehaviorListener listener) {
        //单行不要收起
        RongIM.setConversationListBehaviorListener(new RongImConversationListBehaviorListener(listener));
    }

    /**
     * 8.添加自定义消息
     * //https://docs.rongcloud.cn/im/imkit/android/conversation/custom_message/
     */
    public static void setUserDefinedMessage(Class<? extends MessageContent> messageContentClass, IContainerItemProvider.MessageProvider provider) {
        //注册自定义消息类型
        RongIM.registerMessageType(messageContentClass);
        //注册消息模板
        RongIM.registerMessageTemplate(provider);
    }


    /**
     * 退出登录
     */
    public static void logout() {
        RongIM.getInstance().logout();
    }

}
