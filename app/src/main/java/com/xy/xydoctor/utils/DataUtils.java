package com.xy.xydoctor.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: LYD
 * Date: 2021/8/17 15:50
 * Description:
 */
public class DataUtils {
    public static long currentTimestamp() {
        Date currentDate = new Date();
        return currentDate.getTime();
    }

    /**
     * 获取当前时间字符串
     *
     * @param outFormat 字符串输出时的格式
     * @return
     */
    public static String currentDateString(String outFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(outFormat);
            String dateStr = format.format(new Date());
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }
    }

    /**
     * 把时间字符串转换成Date
     *
     * @param dateString 要转换的字符串
     * @param inFormat   符串的格式，例如yyyy-MM-dd HH：mm:ss
     * @return 果转换成功返回转换以后的date，转换失败的话返回null
     */
    public static Date convertStringToDate(String dateString, String inFormat) {
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 把一个Date对象转换成相应格式的字符串
     *
     * @param date      时间
     * @param outFormat 输出的格式
     * @return 返回转换的字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertDateToString(Date date, String outFormat) {
        SimpleDateFormat format = new SimpleDateFormat(outFormat);
        return format.format(date);
    }

    /**
     * 获取一个月前的时间
     *
     * @return
     */
    public static String getLastMonthTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -1);

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH) + 1;

        int date = calendar.get(Calendar.DATE);


        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (date < 10 ? "0" + date : date);

    }

    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin, String time) {
        boolean result = false;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date nowDate = null;

        try {
            nowDate = df.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();

        }

        Time now = new Time();

        now.set(nowDate.getTime());

        Time startTime = new Time();

        startTime.set(nowDate.getTime());

        startTime.year = beginHour;

        startTime.month = beginMin;

        Time endTime = new Time();

        endTime.set(nowDate.getTime());

        endTime.year = endHour;

        endTime.month = endMin;

        if (!startTime.before(endTime)) {
            startTime.set(startTime.toMillis(true) - nowDate.getTime());

            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime

            Time startTimeInThisDay = new Time();

            startTimeInThisDay.set(startTime.toMillis(true) + nowDate.getTime());

            if (!now.before(startTimeInThisDay)) {
                result = true;

            }

        } else {
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime

        }

        return result;

    }

    public static String TIME_FORMAT3 = "yyyy-MM-dd";


    public static boolean isTimeEqualAtMonth(String time1, String time2) {
        boolean isEqual = false;
        if (TextUtils.isEmpty(time1) || TextUtils.isEmpty(time2)) {
            return isEqual;
        }
        String[] time1s = time1.split("-");
        String[] time2s = time2.split("-");
        if (time1s != null && time1s.length == 3 && time2s != null && time2s.length == 3) {
            if (Integer.parseInt(time1s[0]) == Integer.parseInt(time2s[0]) && Integer.parseInt(time1s[1]) == Integer.parseInt(time2s[1])) {
                isEqual = true;
            }
        }
        return isEqual;
    }


//    public static boolean isInMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(DataUtils.convertStringToDate("2021-09-27","yyyy-MM-dd"));
//        //日期的所属月份
//        int month = calendar.get(Calendar.MONTH) + 1;
//    }

}
