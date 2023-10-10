package com.xy.xydoctor.utils;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 描述:  角标工具类
 * 作者: LYD
 * 创建日期: 2020/12/15 11:02
 */
public class BadgeUtils {
    public static void cancelBadge() {
        setHuaWei(0);
        setVivo(0);
    }


    /**
     * 设置华为角标
     *
     * @param number
     */
    public static void setHuaWei(int number) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("package", AppUtils.getAppPackageName());
            bundle.putString("class", "com.xy.xydoctor.ui.activity.user.SplashActivity");
            bundle.putInt("badgenumber", number);
            Utils.getApp().getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置华为角标
     *
     * @param number
     */
    public static void setVivo(int number) {
        try {
            Intent intent = new Intent();
            intent.setAction("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", AppUtils.getAppPackageName());
            intent.putExtra("className", "com.xy.xydoctor.ui.activity.user.SplashActivity");
            intent.putExtra("notificationNum", number);
            //8.0以上为隐藏api
            //intent.addFlags(Intent.FLAG_RECEIVER_INCLUDE_BACKGROUND);
            Utils.getApp().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBadgeNumber(Notification notification, int number) {
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
