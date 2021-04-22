package com.xy.xydoctor.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;


public class TimeFormatUtils {
    /**
     * 获取年月日Format
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getDefaultFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
