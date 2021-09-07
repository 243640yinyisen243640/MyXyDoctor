package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.FilterDiseaseTypeAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.utils.DataUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/21 17:32
 * Description:添加用户
 */
public class UserAddActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    /**
     * 位置信息
     */
    private TextView locationTextView;
    /**
     * 姓名
     */
    private EditText nameEditText;
    /**
     * 手机号
     */
    private EditText phoneEditText;
    /**
     * 性别
     */
    private TextView sexTextView;
    /**
     * 出生日期
     */
    private TextView bornTextView;
    /**
     * 与户主的关系
     */
    private TextView relationTextView;
    /**
     * 选择医院
     */
    private TextView hospitalTextView;
    /**
     * 选择科室
     */
    private TextView departmentTextView;
    /**
     * 选择医生
     */
    private TextView doctorTextView;
    /**
     * 疾病
     */
    private NoConflictGridView diseaseGridView;
    /**
     * 类型
     */
    private NoConflictGridView typeGridView;
    /**
     * 血糖仪输入
     */
    private EditText sugarEditText;
    /**
     * 血糖仪扫一扫
     */
    private ImageView sugarImageView;
    /**
     * 血压计输入
     */
    private EditText pressureEditText;
    /**
     * 血压计扫一扫
     */
    private ImageView pressureImageView;

    private String sex;

    private List<FilterSugarPressureInfo> typeInfoList;

    private FilterDiseaseTypeAdapter diseaseTypeAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.user_add_title);
        containerView().addView(initView());
        initListener();
        initValues();
    }

    private void initListener() {
        sexTextView.setOnClickListener(this);
        bornTextView.setOnClickListener(this);
        relationTextView.setOnClickListener(this);
        hospitalTextView.setOnClickListener(this);
        departmentTextView.setOnClickListener(this);
        doctorTextView.setOnClickListener(this);
        sugarImageView.setOnClickListener(this);
        pressureImageView.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_user_add, null);
        locationTextView = view.findViewById(R.id.tv_user_add_location);
        nameEditText = view.findViewById(R.id.et_user_add_name);
        phoneEditText = view.findViewById(R.id.et_user_add_phone);
        sexTextView = view.findViewById(R.id.tv_user_add_sex);
        bornTextView = view.findViewById(R.id.tv_user_add_born);
        relationTextView = view.findViewById(R.id.tv_user_add_master_relation);
        hospitalTextView = view.findViewById(R.id.tv_user_add_hospital_choose);
        departmentTextView = view.findViewById(R.id.tv_user_add_department_choose);
        doctorTextView = view.findViewById(R.id.tv_user_add_doctor_choose);
        diseaseGridView = view.findViewById(R.id.gv_user_add_disease);
        typeGridView = view.findViewById(R.id.gv_user_add_type);
        sugarEditText = view.findViewById(R.id.et_user_add_device_sugar);
        sugarImageView = view.findViewById(R.id.iv_user_add_device_sugar);
        pressureEditText = view.findViewById(R.id.et_user_add_device_pressure);
        pressureImageView = view.findViewById(R.id.iv_user_add_device_pressure);
        return view;
    }

    private void initValues() {
        typeInfoList = new ArrayList<>();
        FilterSugarPressureInfo typeInfo1 = new FilterSugarPressureInfo("糖尿病", "1");
        typeInfoList.add(typeInfo1);
        FilterSugarPressureInfo typeInfo2 = new FilterSugarPressureInfo("高血压", "2");
        typeInfoList.add(typeInfo2);
        FilterSugarPressureInfo typeInfo3 = new FilterSugarPressureInfo("超重/肥胖", "3");
        typeInfoList.add(typeInfo3);
        FilterSugarPressureInfo typeInfo4 = new FilterSugarPressureInfo("脑卒中", "4");
        typeInfoList.add(typeInfo4);
        FilterSugarPressureInfo typeInfo5 = new FilterSugarPressureInfo("脂肪肝", "5");
        typeInfoList.add(typeInfo5);
        FilterSugarPressureInfo typeInfo6 = new FilterSugarPressureInfo("冠心病", "6");
        typeInfoList.add(typeInfo6);

        diseaseTypeAdapter = new FilterDiseaseTypeAdapter(getPageContext(), typeInfoList, "2");
        diseaseGridView.setAdapter(diseaseTypeAdapter);


        List<FilterSugarPressureInfo> otherInfoList = new ArrayList<>();
        FilterSugarPressureInfo otherInfoList3 = new FilterSugarPressureInfo("党员", "1");
        otherInfoList.add(otherInfoList3);
        FilterSugarPressureInfo otherInfoList1 = new FilterSugarPressureInfo("残疾人", "2");
        otherInfoList.add(otherInfoList1);
        FilterSugarPressureInfo otherInfoList2 = new FilterSugarPressureInfo("精神疾病", "3");
        otherInfoList.add(otherInfoList2);

        FilterSugarPressureInfo otherInfoList4 = new FilterSugarPressureInfo("重点关注", "4");
        otherInfoList.add(otherInfoList4);

        diseaseGridView.setOnItemClickListener((parent, view, position, id) -> {
            List<FilterSugarPressureInfo> sugarList = new ArrayList<>();
            FilterSugarPressureInfo sugar1 = new FilterSugarPressureInfo("一型", "1");
            sugarList.add(sugar1);
            FilterSugarPressureInfo sugar2 = new FilterSugarPressureInfo("二型", "2");
            sugarList.add(sugar2);
            FilterSugarPressureInfo sugar3 = new FilterSugarPressureInfo("妊娠", "3");
            sugarList.add(sugar3);
            FilterSugarPressureInfo sugar4 = new FilterSugarPressureInfo("其他", "4");
            sugarList.add(sugar4);
            showSugarOrPressureType(sugarList, typeInfoList.get(position).getDiseaseName(), position);

        });
        FilterDiseaseTypeAdapter otherTypeAdapter = new FilterDiseaseTypeAdapter(getPageContext(), otherInfoList, "2");
        typeGridView.setAdapter(otherTypeAdapter);

        typeGridView.setOnItemClickListener((parent, view, position, id) -> {
            otherInfoList.get(position).setCheck(!otherInfoList.get(position).isCheck());
            otherTypeAdapter.notifyDataSetChanged();

        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_user_add_sex:
                chooseSexWindow();
                break;
            case R.id.tv_user_add_born:
                showTimeWindow();
                break;
            case R.id.tv_user_add_master_relation:

                break;
            case R.id.tv_user_add_hospital_choose:
                break;
            case R.id.tv_user_add_department_choose:
                break;
            case R.id.tv_user_add_doctor_choose:
                break;
            case R.id.iv_user_add_device_sugar:
                break;
            case R.id.iv_user_add_device_pressure:
                break;
            default:
                break;
        }
    }


    /**
     * 选择糖尿病或者高血压的类型
     */
    private void showSugarOrPressureType(List<FilterSugarPressureInfo> diseaseTypeInfos, String diseaseName, int position) {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
            String s = diseaseTypeInfos.get(options1).getDiseaseName();
            typeInfoList.get(position).setDiseaseName(s);
            typeInfoList.get(position).setCheck(!typeInfoList.get(position).isCheck());
            diseaseTypeAdapter.notifyDataSetChanged();
        }).setLineSpacingMultiplier(2.5f)
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray))
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .setTitleText(diseaseName)
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
            bornTextView.setText(content);
        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.gray_light))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                //                .setDecorView(all)
                .build();
        timePickerView.show();
    }


    /**
     * 选择性别
     */
    private void chooseSexWindow() {
        List<String> sexList = new ArrayList<>();
        sexList.add(getString(R.string.base_male));
        sexList.add(getString(R.string.base_female));
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
            sex = options1 + "";
            sexTextView.setText("1".equals(sex) ? "女" : "男");
        }).setLineSpacingMultiplier(2.5f)
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_light))
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .build();
        optionsPickerView.setPicker(sexList);
        optionsPickerView.show();
    }
}
