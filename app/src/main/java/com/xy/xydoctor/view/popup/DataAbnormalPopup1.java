package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalAdapter;
import com.xy.xydoctor.base.utils.XYSoftDensityUtils;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.bean.community_manamer.DiseaseTypeInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.imp.BaseCallBack;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 描述: 数据异常的筛选
 * 作者: LYD
 * 创建日期: 2019/7/14 11:25
 */
public class DataAbnormalPopup1 extends PopupWindow {

    private Context context;

    private TextView startTextView;
    private TextView endTextView;
    private LinearLayout allLiner;

    public DataAbnormalPopup1(Context context, BaseCallBack callBack) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.data_abnormal_popwindow, null);

        NoConflictGridView sugarGridView = view.findViewById(R.id.gv_data_abnormal_sugar);
        NoConflictGridView typeGridView = view.findViewById(R.id.gv_data_abnormal_type);
        startTextView = view.findViewById(R.id.tv_data_abnormal_start_time);
        endTextView = view.findViewById(R.id.tv_data_abnormal_end_time);
        TextView resetTextView = view.findViewById(R.id.tv_data_abnormal_filter_reset);
        TextView submitTextView = view.findViewById(R.id.tv_data_abnormal_filter_submit);
        allLiner = view.findViewById(R.id.ll_show_pop_all);
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        //        this.setHeight(XyScreenUtils.dip2px(context, 250));
        this.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);


        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Base_Window_Fade_Anim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //因为某些机型是虚拟按键的,所以要加上以下设置防止挡住按键.
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        List<DiseaseTypeInfo> abnormalInfos = new ArrayList<>();
        DiseaseTypeInfo abnormalInfo1 = new DiseaseTypeInfo(context.getString(R.string.data_abnormal_up));
        abnormalInfos.add(abnormalInfo1);
        DiseaseTypeInfo abnormalInfo2 = new DiseaseTypeInfo(context.getString(R.string.data_abnormal_low));
        abnormalInfos.add(abnormalInfo2);

        DiseaseTypeInfo abnormalInfo3 = new DiseaseTypeInfo(context.getString(R.string.data_abnormal_normal));
        abnormalInfos.add(abnormalInfo3);

        DiseaseTypeInfo abnormalInfo4 = new DiseaseTypeInfo(context.getString(R.string.data_abnormal_not));

        abnormalInfos.add(abnormalInfo4);

        DataAbnormalAdapter abnormalAdapter = new DataAbnormalAdapter(context, abnormalInfos);
        sugarGridView.setAdapter(abnormalAdapter);


        List<DataAbnormalInfo> typeList = new ArrayList<>();
        DataAbnormalInfo typeInfo1 = new DataAbnormalInfo(context.getString(R.string.data_abnormal_filter_no_deal));
        typeList.add(typeInfo1);
        DataAbnormalInfo typeInfo2 = new DataAbnormalInfo(context.getString(R.string.data_abnormal_filter_all));
        typeList.add(typeInfo2);

        DataAbnormalInfo typeInfo3 = new DataAbnormalInfo(context.getString(R.string.data_abnormal_filter_have_deal));
        typeList.add(typeInfo3);

        DataAbnormalAdapter typeDataAbnormalAdapter = new DataAbnormalAdapter(context, abnormalInfos);
        typeGridView.setAdapter(typeDataAbnormalAdapter);

        sugarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abnormalAdapter.setClickPosition(position);
            }
        });
        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeDataAbnormalAdapter.setClickPosition(position);
            }
        });

        startTextView.setOnClickListener(v -> {
            showTimeWindow(1);
        });

        endTextView.setOnClickListener(v -> {
            showTimeWindow(2);
        });
    }


    /**
     * 比较两个时间
     *
     * @param starTime  开始时间
     * @param endString 结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public static boolean compareTwoTime(String starTime, String endString) {
        boolean isMoreThan = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date startData = dateFormat.parse(starTime);
            Date endData = dateFormat.parse(endString);
            long diff = endData.getTime() - startData.getTime();
            if (diff >= 0) {
                isMoreThan = true;
            } else {
                isMoreThan = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isMoreThan;

    }

    /**
     * 时间选择器
     *
     * @param type 开始时间  结束时间
     */
    private void showTimeWindow(int type) {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(context, (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y_M_D);
            if (1 == type) {
                startTextView.setText(content);
            } else {
                endTextView.setText(content);
            }

        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitColor(ContextCompat.getColor(context, R.color.main_red))
                .setCancelColor(ContextCompat.getColor(context, R.color.black_text))
                //                .isDialog(true)
                .setDecorView(allLiner)
                .build();
        //        //设置dialog弹出位置
        //        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        //        params.leftMargin = 0;
        //        params.rightMargin = 0;
        //        ViewGroup contentContainer = timePickerView.getDialogContainerLayout();
        //        contentContainer.setLayoutParams(params);
        //        timePickerView.getDialog().getWindow().setGravity(Gravity.BOTTOM);//可以改成Bottom
        //        timePickerView.getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerView.show();

    }

    @Override
    public void showAsDropDown(View parent) {//parent 你要把popUpWindow放在哪个控件下方
        //该window，不需要充满全屏，不需写以下代码
        if (Build.VERSION.SDK_INT == 24) {// 只有7.0 的系统有这个问题
            //            setHeight(HHSoftScreenUtils.screenHeight(parent.getContext()) - HHSoftDensityUtils.dip2px(parent.getContext(), 48) - HHSoftScreenUtils.statusBarHeight(parent.getContext()) - HHSoftDensityUtils.dip2px(parent.getContext(), 43));
            setHeight(XyScreenUtils.screenHeight(parent.getContext()) - XyScreenUtils.dip2px(parent.getContext(), 48) - XYSoftDensityUtils.statusBarHeight(parent.getContext()));
        }
        super.showAsDropDown(parent, 0, 0);
    }
}
