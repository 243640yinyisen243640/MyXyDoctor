package com.xy.xydoctor.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 描述: 时间选择的工具类
 * 作者: LYD
 * 所需参数: 无
 * 创建日期: 2018/10/31 15:14
 */
public class PickerUtils {
    private PickerUtils() {
    }

    /**
     * 显示 年月日 时间
     *
     * @param context
     * @param callBack
     */
    public static void showTime(Context context, final PickerUtils.TimePickerCallBack callBack) {
        boolean[] booleans = {true, true, true, false, false, false};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        showTime(context, callBack, booleans, format);
    }

    /**
     * 显示年月日 时分日期
     *
     * @param context
     * @param callBack
     */
    public static void showTimeHm(Context context, final PickerUtils.TimePickerCallBack callBack) {
        boolean[] booleans = {true, true, true, true, true, false};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        showTime(context, callBack, booleans, format);
    }

    /**
     * 显示 时分日期
     */
    public static void showTimeHourAndMin(Context context, final PickerUtils.TimePickerCallBack callBack) {
        boolean[] booleans = {false, false, false, true, true, false};
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        showTime(context, callBack, booleans, format);
    }

    /**
     * 显示年月日 时分秒日期
     *
     * @param context
     * @param callBack
     * @param booleans
     * @param format
     */
    public static void showTime(Context context, final PickerUtils.TimePickerCallBack callBack, boolean[] booleans, SimpleDateFormat format) {
        Calendar currentDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(context, (date, v) -> {
            String time = format.format(date);
            callBack.execEvent(time);
        })
                .setType(booleans)
                .setContentTextSize(21)
                .isDialog(true)
                .setDate(currentDate)
                .setRangDate(startDate
                ,endDate)
                .build();
        timePickerView.show();
        Dialog mDialog = timePickerView.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    /**
     * 显示选项
     *
     * @param context
     * @param callBack
     * @param listOption
     */
    public static void showOption(Context context, final PickerUtils.TimePickerCallBack callBack, List listOption) {
        OptionsPickerView pv = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                callBack.execEvent(listOption.get(options1).toString());
            }
        })
                .setContentTextSize(21)
                .build();//构造
        pv.show();//显示
        pv.setPicker(listOption);
        pv.setSelectOptions(0);
    }

    /**
     * 显示选项
     *
     * @param context
     * @param callBack
     * @param listOption
     */
    public static void showOptionPosition(Context context, final PickerUtils.PositionCallBack callBack, List listOption) {
        OptionsPickerView pv = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                callBack.execEvent(listOption.get(options1).toString(), options1);
            }
        })
                .setContentTextSize(21)
                .build();//构造
        pv.show();//显示
        pv.setPicker(listOption);
        pv.setSelectOptions(0);
    }


    public static void showTimeWindow(Context context, boolean[] booleans, String dataManager,
                                      final PickerUtils.TimePickerCallBack callBack) {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);

        TimePickerView timePickerView = new TimePickerBuilder(context, (date, v) -> {
            String content = DataUtils.convertDateToString(date, dataManager);
            callBack.execEvent(content);
        })
                .setDate(currentDate)
                .setRangDate(startDate, endDate)
                .setType(booleans)
                .setSubmitColor(ContextCompat.getColor(context, R.color.main_green))
                .setCancelColor(ContextCompat.getColor(context, R.color.black_text))
                .build();
        timePickerView.show();
    }


    public static void showTimeWindow(Context context, boolean[] booleans, String dataManager,String currentTime,
                                      final PickerUtils.TimePickerCallBack callBack) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(dataManager).parse(currentTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);

        TimePickerView timePickerView = new TimePickerBuilder(context, (date, v) -> {
            String content = DataUtils.convertDateToString(date, dataManager);
            callBack.execEvent(content);
        })
                .setDate(calendar)
                .setRangDate(startDate, endDate)
                .setType(booleans)
                .setSubmitColor(ContextCompat.getColor(context, R.color.main_green))
                .setCancelColor(ContextCompat.getColor(context, R.color.black_text))
                .build();
        timePickerView.show();
    }


    public interface TimePickerCallBack {
        void execEvent(String content);
    }


    /**
     * 回调位置
     */
    public interface PositionCallBack {
        void execEvent(String content, int position);
    }
}
