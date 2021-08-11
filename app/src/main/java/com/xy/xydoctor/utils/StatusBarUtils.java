package com.xy.xydoctor.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.core.content.ContextCompat;

/**
 * Author: LYD
 * Date: 2021/8/10 18:12
 * Description:
 */
public class StatusBarUtils {
    /**
     * 全屏及状态栏设置
     * 注意：
     * 一、该方法仅支持6.0及以上版本
     * 二、该方法设置后，布局占用状态栏高度,
     *
     * @param activity
     * @param colorResID 状态栏高度
     * @param isWhite    状态栏字体是否白色
     * @return
     */
    public static boolean fullScreenWithStatusBarColor(Activity activity, int colorResID, boolean isWhite) {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = activity.getWindow();
            window.addFlags(-2147483648);
            window.clearFlags(67108864);
            window.setStatusBarColor(ContextCompat.getColor(activity, colorResID));
            View decorView = window.getDecorView();
            short option;
            if (isWhite) {
                option = 1280;
                decorView.setSystemUiVisibility(option);
            } else {
                option = 9472;
                decorView.setSystemUiVisibility(option);
            }
            return true;
        } else {
            return false;
        }
    }
}
