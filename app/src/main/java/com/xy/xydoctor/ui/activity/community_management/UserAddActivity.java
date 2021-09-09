package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
import com.xy.xydoctor.bean.community_manamer.DepartmentInfo;
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

    private TextView sugarTextView, pressureTextView, overTextView;

    private CheckBox headCheckBox, fattyCheckBox, heartCheckBox;
    private CheckBox apartCheckBox, illCheckBox, mentalCheckBox, importantCheckBox;
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

    private String departmentid = "-1";
    private String doctorID = "-1";
    private String hospitalid;

    private String diabeteslei;

    private String buildid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildid = getIntent().getStringExtra("buildid");
        topViewManager().titleTextView().setText(R.string.user_add_title);
        containerView().addView(initView());
        initListener();
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
        sugarTextView = view.findViewById(R.id.cb_filter_disease_sugar);
        pressureTextView = view.findViewById(R.id.cb_filter_disease_presure);
        overTextView = view.findViewById(R.id.tv_filter_disease_over_weight);
        headCheckBox = view.findViewById(R.id.cb_filter_disease_head);
        fattyCheckBox = view.findViewById(R.id.cb_filter_disease_fatty);
        heartCheckBox = view.findViewById(R.id.cb_filter_disease_heart);
        apartCheckBox = view.findViewById(R.id.cb_filter_disease_apart);
        illCheckBox = view.findViewById(R.id.cb_filter_disease_ill);
        mentalCheckBox = view.findViewById(R.id.cb_filter_disease_mental);
        importantCheckBox = view.findViewById(R.id.cb_filter_disease_important);
        sugarEditText = view.findViewById(R.id.et_user_add_device_sugar);
        sugarImageView = view.findViewById(R.id.iv_user_add_device_sugar);
        pressureEditText = view.findViewById(R.id.et_user_add_device_pressure);
        pressureImageView = view.findViewById(R.id.iv_user_add_device_pressure);
        return view;
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
                getHospital();
                break;
            case R.id.tv_user_add_department_choose:
                getDepartment();
                break;
            case R.id.tv_user_add_doctor_choose:
                if ("-1".equals(departmentid)) {
                    TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.user_add_department_choose);
                    return;
                }
                getDepartmentDoctor();
                break;
            case R.id.iv_user_add_device_sugar:
                break;
            case R.id.iv_user_add_device_pressure:
                break;
            case R.id.cb_filter_disease_sugar:
                List<FilterSugarPressureInfo> sugarList = new ArrayList<>();
                FilterSugarPressureInfo info1 = new FilterSugarPressureInfo("I型糖尿病", "1");
                sugarList.add(info1);
                FilterSugarPressureInfo info2 = new FilterSugarPressureInfo("II型糖尿病", "1");
                sugarList.add(info2);
                FilterSugarPressureInfo info3 = new FilterSugarPressureInfo("妊娠糖尿病", "1");
                sugarList.add(info3);
                FilterSugarPressureInfo info4 = new FilterSugarPressureInfo("其他", "1");
                sugarList.add(info4);
                FilterSugarPressureInfo info5 = new FilterSugarPressureInfo("无", "0");
                sugarList.add(info5);
                getSugar(sugarList);
                break;
            default:
                break;
        }
    }


    private void getHospital() {
        List<DepartmentInfo> logisticsCompanyInfos = new ArrayList<>();
        if (logisticsCompanyInfos != null && logisticsCompanyInfos.size() > 0) {
            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                hospitalid = logisticsCompanyInfos.get(options1).getHosp_userid();
                String s = logisticsCompanyInfos.get(options1).getHosp_name();
                hospitalTextView.setText(s);
            }).setLineSpacingMultiplier(2.5f)
                    .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_text))
                    .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                    .build();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < logisticsCompanyInfos.size(); i++) {
                String typeName = logisticsCompanyInfos.get(i).getHosp_name();
                list.add(typeName);
            }
            optionsPickerView.setPicker(list);
            optionsPickerView.show();
        }

    }

    private void getSugar(List<FilterSugarPressureInfo> releationList) {
        if (releationList != null && releationList.size() > 0) {
            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                diabeteslei = releationList.get(options1).getCheckID();
                String s = releationList.get(options1).getDiseaseName();
                sugarTextView.setText(s);
                diseaseTypeAdapter.notifyDataSetChanged();
            }).setLineSpacingMultiplier(2.5f)
                    .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_text))
                    .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                    .build();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < releationList.size(); i++) {
                String typeName = releationList.get(i).getDiseaseName();
                list.add(typeName);
            }
            optionsPickerView.setPicker(list);
            optionsPickerView.show();
        }
    }

    /**
     * 获取科室
     */
    private void getDepartment() {
        TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.waiting, false);
        Call<String> requestCall = DataManager.getDepartmentList(buildid, hospitalid, (call, response) -> {
            TipUtils.getInstance().dismissProgressDialog();
            if (200 == response.code) {
                List<DepartmentInfo> logisticsCompanyInfos = (List<DepartmentInfo>) response.object;
                if (logisticsCompanyInfos != null && logisticsCompanyInfos.size() > 0) {
                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                        departmentid = logisticsCompanyInfos.get(options1).getDep_userid();
                        String s = logisticsCompanyInfos.get(options1).getDepname();
                        departmentTextView.setText(s);
                    }).setLineSpacingMultiplier(2.5f)
                            .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_text))
                            .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                            .build();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < logisticsCompanyInfos.size(); i++) {
                        String typeName = logisticsCompanyInfos.get(i).getDepname();
                        list.add(typeName);
                    }
                    optionsPickerView.setPicker(list);
                    optionsPickerView.show();
                }
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, throwable) -> {
            TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.network_error);
        });

    }

    /**
     * 获取楼栋
     */
    private void getDepartmentDoctor() {
        TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.waiting, false);
        Call<String> requestCall = DataManager.getCommunityList((call, response) -> {
            TipUtils.getInstance().dismissProgressDialog();
            if (200 == response.code) {
                List<DepartmentInfo> logisticsCompanyInfos = (List<DepartmentInfo>) response.object;
                if (logisticsCompanyInfos != null && logisticsCompanyInfos.size() > 0) {
                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                        doctorID = logisticsCompanyInfos.get(options1).getDoc_userid();
                        String s = logisticsCompanyInfos.get(options1).getDocname();
                        departmentTextView.setText(s);
                    }).setLineSpacingMultiplier(2.5f)
                            .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_text))
                            .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                            .build();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < logisticsCompanyInfos.size(); i++) {
                        String typeName = logisticsCompanyInfos.get(i).getDocname();
                        list.add(typeName);
                    }
                    optionsPickerView.setPicker(list);
                    optionsPickerView.show();
                }
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, throwable) -> {
            TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.network_error);
        });

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
