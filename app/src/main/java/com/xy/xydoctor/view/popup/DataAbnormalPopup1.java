package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalAdapter;
import com.xy.xydoctor.base.utils.XYSoftDensityUtils;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.imp.BaseCallBack;
import com.xy.xydoctor.imp.IACommunityFilterChoose;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;
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
    private IACommunityFilterChoose iaCommunityFilterChoose;

    private String starttime;
    private String endtime;

    private boolean isChange1 = false;
    private boolean isChange2 = false;

    /**
     * 1血压  2血糖
     */
    private String type;

    public DataAbnormalPopup1(Context context, String type, BaseCallBack callBack) {
        super(context);
        this.context = context;
        this.type = type;
        View view = View.inflate(context, R.layout.data_abnormal_popwindow, null);

        NoConflictGridView sugarGridView = view.findViewById(R.id.gv_data_abnormal_sugar);
        NoConflictGridView typeGridView = view.findViewById(R.id.gv_data_abnormal_type);
        EditText firstEditText = view.findViewById(R.id.et_data_abnormal_first);
        EditText secondEditText = view.findViewById(R.id.et_data_abnormal_second);
        startTextView = view.findViewById(R.id.tv_data_abnormal_start_time);
        endTextView = view.findViewById(R.id.tv_data_abnormal_end_time);
        TextView resetTextView = view.findViewById(R.id.tv_data_abnormal_filter_reset);
        TextView submitTextView = view.findViewById(R.id.tv_data_abnormal_filter_submit);
        LinearLayout inputLinearLayout = view.findViewById(R.id.ll_data_abnormal_input);
        TextView sugarOrPressureTextView = view.findViewById(R.id.tv_data_abnormal_sugar_pressure);

        if ("1".equals(type)) {
            inputLinearLayout.setVisibility(View.GONE);
            sugarOrPressureTextView.setText(context.getString(R.string.community_base_pressure));
        } else {
            inputLinearLayout.setVisibility(View.VISIBLE);
            sugarOrPressureTextView.setText(context.getString(R.string.community_base_sugar));
        }
        allLiner = view.findViewById(R.id.ll_show_pop_all);
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        //                this.setHeight(XyScreenUtils.dip2px(context, 350));
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

        starttime = DataUtils.getLastMonthTime();
        endtime = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y_M_D);

        //        startTextView.setText(starttime);
        //        endTextView.setText(endtime);

        List<FilterSugarPressureInfo> abnormalInfos = new ArrayList<>();
        FilterSugarPressureInfo abnormalInfo1 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_up), "1");
        abnormalInfos.add(abnormalInfo1);
        FilterSugarPressureInfo abnormalInfo2 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_low), "2");
        abnormalInfos.add(abnormalInfo2);

        FilterSugarPressureInfo abnormalInfo3 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_normal), "3");
        abnormalInfos.add(abnormalInfo3);

        FilterSugarPressureInfo abnormalInfo4 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_not), "4");

        abnormalInfos.add(abnormalInfo4);

        DataAbnormalAdapter abnormalAdapter = new DataAbnormalAdapter(context, abnormalInfos);
        sugarGridView.setAdapter(abnormalAdapter);


        List<FilterSugarPressureInfo> typeList = new ArrayList<>();
        FilterSugarPressureInfo typeInfo1 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_filter_no_deal), "2");
        typeList.add(typeInfo1);
        FilterSugarPressureInfo typeInfo2 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_filter_all), "0");
        typeList.add(typeInfo2);

        FilterSugarPressureInfo typeInfo3 = new FilterSugarPressureInfo(context.getString(R.string.data_abnormal_filter_have_deal), "1");
        typeList.add(typeInfo3);

        DataAbnormalAdapter typeDataAbnormalAdapter = new DataAbnormalAdapter(context, typeList);
        typeGridView.setAdapter(typeDataAbnormalAdapter);

        sugarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChange1 = true;
                isChange2 = true;
                firstEditText.setText("");
                secondEditText.setText("");

                for (int i = 0; i < abnormalInfos.size(); i++) {
                    abnormalInfos.get(i).setCheck(false);
                }
                abnormalInfos.get(position).setCheck(true);
                //                abnormalInfos.get(position).setCheck(!abnormalInfos.get(position).isCheck());
                abnormalAdapter.notifyDataSetChanged();
                //                abnormalAdapter.setClickPosition(position);
            }
        });
        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //                typeList.get(position).setCheck(!typeList.get(position).isCheck());
                for (int i = 0; i < typeList.size(); i++) {
                    typeList.get(i).setCheck(false);
                }
                typeList.get(position).setCheck(true);


                typeDataAbnormalAdapter.notifyDataSetChanged();
                //                typeDataAbnormalAdapter.setClickPosition(position);
            }
        });

        startTextView.setOnClickListener(v -> {
            showTimeWindow(1);
        });

        endTextView.setOnClickListener(v -> {
            showTimeWindow(2);
        });

        firstEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChange1) {
                    isChange1 = false;
                    return;
                }
                for (int i = 0; i < abnormalInfos.size(); i++) {
                    abnormalInfos.get(i).setCheck(false);
                    abnormalAdapter.notifyDataSetChanged();
                    //                    abnormalAdapter.setClickPosition(i);
                }
            }
        });

        secondEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChange2) {
                    isChange2 = false;
                    return;
                }
                for (int i = 0; i < abnormalInfos.size(); i++) {
                    abnormalInfos.get(i).setCheck(false);
                    abnormalAdapter.notifyDataSetChanged();
                    //                    abnormalAdapter.setClickPosition(i);
                }
            }
        });

        resetTextView.setOnClickListener(v -> {
            startTextView.setText("");
            endTextView.setText("");

            for (int i = 0; i < abnormalInfos.size(); i++) {
                abnormalInfos.get(i).setCheck(false);
            }
            abnormalAdapter.notifyDataSetChanged();

            firstEditText.setText("");
            secondEditText.setText("");

            for (int i = 0; i < typeList.size(); i++) {
                typeList.get(i).setCheck(false);
            }
            typeDataAbnormalAdapter.notifyDataSetChanged();

        });

        submitTextView.setOnClickListener(v -> {
            String status = "-1";
            String statusName = "";
            String style = "-1";
            String styleName = "";
            String startSugar = "";
            String endSugar = "";
            if (!compareTwoTime(starttime, endtime)) {
                TipUtils.getInstance().showToast(context, R.string.please_choose_time_at_month);
                return;
            }
            if ("1".equals(type)) {

                for (int i = 0; i < abnormalInfos.size(); i++) {
                    if (abnormalInfos.get(i).isCheck()) {
                        style = abnormalInfos.get(i).getCheckID();
                        styleName = abnormalInfos.get(i).getDiseaseName();
                    }
                }
                for (int i = 0; i < typeList.size(); i++) {
                    if (typeList.get(i).isCheck()) {
                        status = typeList.get(i).getCheckID();
                        statusName = typeList.get(i).getDiseaseName();
                    }
                }

                if ("-1".equals(style)) {
                    TipUtils.getInstance().showToast(context, R.string.please_choose_pressure);
                    return;
                }
                if ("-1".equals(status)) {
                    TipUtils.getInstance().showToast(context, R.string.please_choose_data_type);
                    return;
                }
            } else {

                if (TextUtils.isEmpty(firstEditText.getText().toString().trim()) && TextUtils.isEmpty(secondEditText.getText().toString().trim())) {
                    for (int i = 0; i < abnormalInfos.size(); i++) {
                        if (abnormalInfos.get(i).isCheck()) {
                            style = abnormalInfos.get(i).getCheckID();
                            styleName = abnormalInfos.get(i).getDiseaseName();
                        }
                    }
                }
                startSugar = firstEditText.getText().toString().trim();
                endSugar = secondEditText.getText().toString().trim();

                for (int i = 0; i < typeList.size(); i++) {
                    if (typeList.get(i).isCheck()) {
                        status = typeList.get(i).getCheckID();
                        statusName = typeList.get(i).getDiseaseName();
                    }
                }



                if ("-1".equals(style) && (TextUtils.isEmpty(startSugar) || TextUtils.isEmpty(endSugar))) {
                    TipUtils.getInstance().showToast(context, R.string.please_choose_sugar);
                    return;
                }
                if (TurnsUtils.getInt(startSugar, 0) > TurnsUtils.getInt(endSugar, 0)) {
                    TipUtils.getInstance().showToast(context, R.string.please_input_right_sugar);
                    return;
                }
                if ("-1".equals(status)) {
                    TipUtils.getInstance().showToast(context, R.string.please_choose_data_type);
                    return;
                }
            }

            Log.i("yys", "status==" + status + "statusName" + "style==" + style + "styleName==" + styleName);
            iaCommunityFilterChoose.IAFollowUpChoose(starttime, endtime, style, styleName, startSugar, endSugar, status, statusName);
        });
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
        int currentMonth = currentDate.get(Calendar.MONDAY) - 1;
        startDate.set(currentYear, currentMonth, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(context, (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y_M_D);
            if (1 == type) {
                startTextView.setText(content);
                starttime = content;
            } else {
                endTextView.setText(content);
                endtime = content;
            }

        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitColor(ContextCompat.getColor(context, R.color.main_red))
                .setCancelColor(ContextCompat.getColor(context, R.color.black_text))
                .isDialog(true)

                //                .setDecorView(allLiner)
                .build();
        //设置dialog弹出位置
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.leftMargin = 0;
        params.rightMargin = 0;
        ViewGroup contentContainer = timePickerView.getDialogContainerLayout();
        contentContainer.setLayoutParams(params);
        timePickerView.getDialog().getWindow().setGravity(Gravity.BOTTOM);//可以改成Bottom
        timePickerView.getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerView.show();

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
        SimpleDateFormat dateFormat = new SimpleDateFormat(DataFormatManager.TIME_FORMAT_Y_M_D);
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


    @Override
    public void showAsDropDown(View parent) {//parent 你要把popUpWindow放在哪个控件下方
        //该window，不需要充满全屏，不需写以下代码
        if (Build.VERSION.SDK_INT == 24) {// 只有7.0 的系统有这个问题
            //            setHeight(HHSoftScreenUtils.screenHeight(parent.getContext()) - HHSoftDensityUtils.dip2px(parent.getContext(), 48) - HHSoftScreenUtils.statusBarHeight(parent.getContext()) - HHSoftDensityUtils.dip2px(parent.getContext(), 43));
            setHeight(XyScreenUtils.screenHeight(parent.getContext()) - XyScreenUtils.dip2px(parent.getContext(), 48) - XYSoftDensityUtils.statusBarHeight(parent.getContext()));
        }
        super.showAsDropDown(parent, 0, 0);
    }


    /**
     * 交给activity的方法回调，设置监听
     *
     * @param filterChoose
     */
    public void setOnChooseOkListener(IACommunityFilterChoose filterChoose) {
        this.iaCommunityFilterChoose = filterChoose;
    }
}
