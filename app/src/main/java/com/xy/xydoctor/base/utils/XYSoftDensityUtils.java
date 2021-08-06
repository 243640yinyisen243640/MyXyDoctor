package com.xy.xydoctor.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.core.content.ContextCompat;

public class XYSoftDensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int statusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
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
