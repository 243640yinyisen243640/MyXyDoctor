package com.xy.xydoctor.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Author: LYD
 * Date: 2021/9/24 11:05
 * Description:
 */
public class JavascriptInterfaces extends Object {
    private Context context;

    public JavascriptInterfaces(Context context) {
        this.context = context;
    }

    //这个注解是必须的
    @JavascriptInterface
    //方法名  和后端保持一致参数也需要保持一致
    public void setDataRefresh(int a) {
        Log.i("yys", "setDataRefresh==" + a);
        ((Activity) context).setResult(Activity.RESULT_OK);
        ((Activity) context).finish();
    }
}
