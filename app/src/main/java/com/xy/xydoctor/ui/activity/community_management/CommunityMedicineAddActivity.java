package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.DiseaseTypeInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.utils.DataUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 16:31
 * Description: 添加用药
 */
public class CommunityMedicineAddActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private EditText nameEditText;
    private EditText allAmountEditText;
    private TextView chooseAmountTextView;
    private EditText timesEditText;
    private TextView timesTextView;
    private EditText doseEditText;
    private TextView chooseTimeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topViewManager().titleTextView().setText(R.string.add_medicine_title);
        topViewManager().moreTextView().setText(R.string.base_save);
        topViewManager().moreTextView().setOnClickListener(v -> {

        });
        containerView().addView(initView());

        initListener();
    }

    private void initListener() {
        chooseAmountTextView.setOnClickListener(this);
        timesTextView.setOnClickListener(this);
        chooseTimeTextView.setOnClickListener(this);
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_medicine_add, null);
        nameEditText = view.findViewById(R.id.et_add_medicine_name);
        allAmountEditText = view.findViewById(R.id.et_add_medicine_all_amount);
        chooseAmountTextView = view.findViewById(R.id.tv_add_medicine_choose_amount);
        timesEditText = view.findViewById(R.id.et_add_medicine_smoke_times);
        timesTextView = view.findViewById(R.id.tv_add_medicine_smoke_times);
        doseEditText = view.findViewById(R.id.et_add_medicine_dose);
        chooseTimeTextView = view.findViewById(R.id.tv_add_medicine_choose);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_medicine_choose_amount:
                List<DiseaseTypeInfo> doseList = new ArrayList<>();
                DiseaseTypeInfo mg = new DiseaseTypeInfo("mg");
                doseList.add(mg);
                DiseaseTypeInfo g = new DiseaseTypeInfo("g");
                doseList.add(g);
                DiseaseTypeInfo iu = new DiseaseTypeInfo("iu");
                doseList.add(iu);
                DiseaseTypeInfo ml = new DiseaseTypeInfo("ml");
                doseList.add(ml);
                DiseaseTypeInfo ug = new DiseaseTypeInfo("ug");
                doseList.add(ug);
                showDose(doseList);
                break;
            case R.id.tv_add_medicine_smoke_times:
                List<DiseaseTypeInfo> timesList = new ArrayList<>();
                DiseaseTypeInfo day = new DiseaseTypeInfo("日");
                timesList.add(day);
                DiseaseTypeInfo week = new DiseaseTypeInfo("周");
                timesList.add(week);
                DiseaseTypeInfo month = new DiseaseTypeInfo("月");
                timesList.add(month);
                showDose(timesList);
                break;
            case R.id.tv_add_medicine_choose:
                showTimeWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 选选择剂量
     */
    private void showDose(List<DiseaseTypeInfo> diseaseTypeInfos) {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
            String s = diseaseTypeInfos.get(options1).getDiseaseName();
            chooseAmountTextView.setText(s);
        }).setLineSpacingMultiplier(2.5f)
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray))
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .build();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < diseaseTypeInfos.size(); i++) {
            String typeName = diseaseTypeInfos.get(i).getDiseaseName();
            list.add(typeName);
        }
        optionsPickerView.setPicker(list);
        optionsPickerView.show();
    }

    private void showTimeWindow() {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(getPageContext(), (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_H_M);
            chooseTimeTextView.setText(content);
        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.gray_light))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                //                .setDecorView(all)
                .build();
        timePickerView.show();
    }
}
