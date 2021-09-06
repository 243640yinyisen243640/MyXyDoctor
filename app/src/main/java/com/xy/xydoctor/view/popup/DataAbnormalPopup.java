package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalAdapter;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.imp.BaseCallBack;
import com.xy.xydoctor.utils.DataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述: 数据异常的筛选
 * 作者: LYD
 * 创建日期: 2019/7/14 11:25
 */
public class DataAbnormalPopup extends BasePopupWindow {

    private Context context;

    private TextView startTextView;
    private TextView endTextView;
    private LinearLayout allLiner;

    public DataAbnormalPopup(Context context, BaseCallBack callBack) {
        super(context);
        this.context = context;
        setBackPressEnable(true);
        setAlignBackground(true);
        NoConflictGridView sugarGridView = findViewById(R.id.gv_data_abnormal_sugar);
        NoConflictGridView typeGridView = findViewById(R.id.gv_data_abnormal_type);
        startTextView = findViewById(R.id.tv_data_abnormal_start_time);
        endTextView = findViewById(R.id.tv_data_abnormal_end_time);
        TextView resetTextView = findViewById(R.id.tv_data_abnormal_filter_reset);
        TextView submitTextView = findViewById(R.id.tv_data_abnormal_filter_submit);
        allLiner = findViewById(R.id.ll_show_pop_all);


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
                abnormalInfos.get(position).setCheck(!abnormalInfos.get(position).isCheck());
                abnormalAdapter.notifyDataSetChanged();
            }
        });
        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeList.get(position).setCheck(!typeList.get(position).isCheck());
                typeDataAbnormalAdapter.notifyDataSetChanged();
            }
        });

        startTextView.setOnClickListener(v -> {
            showTimeWindow(1);
        });

        endTextView.setOnClickListener(v -> {
            showTimeWindow(2);
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.data_abnormal_popwindow);
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


}
