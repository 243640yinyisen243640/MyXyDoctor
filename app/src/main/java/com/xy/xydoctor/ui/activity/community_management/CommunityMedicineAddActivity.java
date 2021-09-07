package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineUserInfo;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

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
    private TextView doseTextView;
    private TextView chooseTimeTextView;


    private String pharmacy_id = "0";
    private String userid = "0";

    /**
     * 1：添加 2：编辑
     */
    private String type;

    private String startTime;

    private String typeCheckId = "1";
    private String timeTypeId = "1";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        topViewManager().titleTextView().setText(R.string.add_medicine_title);
        topViewManager().moreTextView().setText(R.string.base_save);
        topViewManager().moreTextView().setOnClickListener(v -> {
            loadData();
        });
        containerView().addView(initView());
        if ("2".equals(type)) {
            pharmacy_id = getIntent().getStringExtra("pharmacy_id");
            nameEditText.setEnabled(false);
            getDataInfo();
        } else {
            nameEditText.setEnabled(true);
            userid = getIntent().getStringExtra("userid");
            startTime = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y_M_D);
            chooseTimeTextView.setText(startTime);
        }
        initListener();
    }

    /**
     * 上传数据
     */
    private void loadData() {
        String drugname = nameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(drugname)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_medicine_name);
            return;
        }

        String allAmount = allAmountEditText.getText().toString().trim();
        if (TextUtils.isEmpty(allAmount)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_medicine_all_amount);
            return;
        }
        String times = timesEditText.getText().toString().trim();
        if (TextUtils.isEmpty(times)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_medicine_smoke_times);
            return;
        }
        String dosage = doseEditText.getText().toString().trim();
        if (TextUtils.isEmpty(dosage)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_medicine_dose);
            return;
        }

        Call<String> requestCall = DataManager.loadMedicineData(pharmacy_id, userid, drugname, allAmount, times, timeTypeId, dosage,
                typeCheckId, startTime, (call, response) -> {
                    if (response.code == 200) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }, (call, t) -> {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
                });
    }


    /**
     * 获取详情
     */
    private void getDataInfo() {
        Call<String> requestCall = DataManager.getUseMedicineInfo(pharmacy_id, (call, response) -> {
            if (response.code == 200) {

                CommunityUseMedicineUserInfo info = (CommunityUseMedicineUserInfo) response.object;
                bindData(info);
            }
        }, (call, response) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    /**
     * 绑定数据
     *
     * @param info
     */
    private void bindData(CommunityUseMedicineUserInfo info) {
        nameEditText.setText(info.getDrugname());
        allAmountEditText.setText(info.getNumber());
        typeCheckId = info.getType();
        timeTypeId = info.getTimeType();
        if ("1".equals(info.getType())) {
            chooseAmountTextView.setText("mg");
            doseTextView.setText("mg/次");
        } else if ("2".equals(info.getType())) {
            chooseAmountTextView.setText("g");
            doseTextView.setText("g/次");
        } else if ("3".equals(info.getType())) {
            chooseAmountTextView.setText("iu");
            doseTextView.setText("iu/次");
        } else if ("4".equals(info.getType())) {
            chooseAmountTextView.setText("ml");
            doseTextView.setText("ml/次");
        } else {
            chooseAmountTextView.setText("ug");
            doseTextView.setText("ug/次");
        }

        timesEditText.setText(info.getTimes());
        if ("1".equals(info.getTimeType())) {
            timesTextView.setText("日");
        } else if ("2".equals(info.getTimeType())) {
            timesTextView.setText("周");
        } else {
            timesTextView.setText("月");
        }

        doseEditText.setText(info.getDosage());
        chooseTimeTextView.setText(info.getStarttime());
        startTime = info.getStarttime();
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
        doseTextView = view.findViewById(R.id.tv_add_medicine_dose);
        chooseTimeTextView = view.findViewById(R.id.tv_add_medicine_choose);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_medicine_choose_amount:
                List<FilterSugarPressureInfo> doseList = new ArrayList<>();
                FilterSugarPressureInfo mg = new FilterSugarPressureInfo("mg", "1");
                doseList.add(mg);
                FilterSugarPressureInfo g = new FilterSugarPressureInfo("g", "2");
                doseList.add(g);
                FilterSugarPressureInfo iu = new FilterSugarPressureInfo("iu", "3");
                doseList.add(iu);
                FilterSugarPressureInfo ml = new FilterSugarPressureInfo("ml", "4");
                doseList.add(ml);
                FilterSugarPressureInfo ug = new FilterSugarPressureInfo("ug", "5");
                doseList.add(ug);
                showDose(doseList, "1");
                break;
            case R.id.tv_add_medicine_smoke_times:
                List<FilterSugarPressureInfo> timesList = new ArrayList<>();
                FilterSugarPressureInfo day = new FilterSugarPressureInfo("日", "1");
                timesList.add(day);
                FilterSugarPressureInfo week = new FilterSugarPressureInfo("周", "2");
                timesList.add(week);
                FilterSugarPressureInfo month = new FilterSugarPressureInfo("月", "3");
                timesList.add(month);
                showDose(timesList, "2");
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
    private void showDose(List<FilterSugarPressureInfo> diseaseTypeInfos, String type) {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
            String s = diseaseTypeInfos.get(options1).getDiseaseName();
            if ("1".equals(type)) {
                chooseAmountTextView.setText(s);
                typeCheckId = diseaseTypeInfos.get(options1).getCheckID();
            } else {
                timesTextView.setText(s);
                timeTypeId = diseaseTypeInfos.get(options1).getCheckID();
            }

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
