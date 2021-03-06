package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.BuildInfo;
import com.xy.xydoctor.bean.community_manamer.DepartmentInfo;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.bean.community_manamer.SearchInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.param.UserAddReq;
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
 * 传参
 */
public class UserAddSecondActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_FOR_IME_CODE = 10;
    private static final int REQUEST_CODE_FOR_CHOOSE = 11;
    /**
     * 位置信息
     */
    private TextView communityNameTextView;
    private TextView communityBuildNameTextView;
    private TextView communityUnitNameTextView;
    private TextView communityRoomNameTextView;
    /**
     * 姓名
     */
    private EditText nameEditText;
    /**
     * 手机号
     */
    private EditText phoneEditText;
    private EditText idNumEditText;
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

    private TextView sugarTextView, pressureTextView, headTextView;

    private CheckBox heartCheckBox, spiritCheckBox, pregnantCheckBox, tuberculosisCheckBox;
    private CheckBox apartCheckBox, illCheckBox, allowanceCheckBox, heavyCheckBox, specialCheckBox, epidemicCheckBox, importCheckBox;
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


    private TextView addTextView;

    /**
     * 如果搜索出来，那这个是必传的
     */
    private String userid;


    private String sex = "0";

    /**
     * 生日
     */
    private String born;


    /**
     * 科室id
     */
    private String departmentid = "0";
    /**
     * 医生id
     */
    private String doctorID = "0";
    /**
     * 医院id
     */
    private String hospitalid = "0";

    /**
     * 糖尿病类型id
     */
    private String diabeteslei = "0";

    /**
     * 血压等级
     */
    private String bloodLevel;
    /**
     * 是否是高血压  1否 2是
     */
    private String hypertension = "1";


    /**
     * 关系id
     */
    private String releationId = "-1";

    private UserAddReq addReq = new UserAddReq();


    /**
     * 小区id
     */
    private String communityID = "0";
    /**
     * 楼栋id
     */
    private String communityBuildID = "0";
    /**
     * 单元id
     */
    private String communityUnitID = "0";
    /**
     * 房间id  上传是只需要这个id
     */
    private String communityHouseID = "0";

    private String buildid = "0";
    private String comName = "";
    private String unitName = "";
    private String buildName = "";
    private String houseName = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.user_add_title);
        communityHouseID = getIntent().getStringExtra("houserid");
        buildid = getIntent().getStringExtra("buildid");
        comName = getIntent().getStringExtra("comName");
        unitName = getIntent().getStringExtra("unitName");
        buildName = getIntent().getStringExtra("buildName");
        houseName = getIntent().getStringExtra("houseName");
        topViewManager().moreTextView().setText("选择");
        topViewManager().moreTextView().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), CommunityWaitImportListActivity.class);
            intent.putExtra("type", "2");
            startActivityForResult(intent, REQUEST_CODE_FOR_CHOOSE);
        });
        containerView().addView(initView());
        initListener();
        initValues();
    }

    private void initValues() {
        communityNameTextView.setText(comName);
        communityBuildNameTextView.setText(buildName);
        communityUnitNameTextView.setText(unitName);
        communityRoomNameTextView.setText(houseName);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_user_add_community_name:
                getCommunityInfo("", "1");
                break;
            case R.id.tv_user_add_community_build_name:
                if ("0".equals(communityID)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_choose_community);
                    return;
                }
                getCommunityInfo(communityID, "2");

                break;
            case R.id.tv_user_add_community_unit_name:
                if ("0".equals(communityBuildID)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_choose_community_build);
                    return;
                }
                getCommunityInfo(communityBuildID, "3");
                break;
            case R.id.tv_user_add_community_room_name:
                if ("0".equals(communityUnitID)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_choose_community_unit);
                    return;
                }
                getCommunityInfo(communityUnitID, "4");
                break;

            case R.id.tv_community_user_add_sex:
                setDissMiss();
                chooseSexWindow();
                break;
            case R.id.tv_community_user_add_born:
                setDissMiss();
                showTimeWindow();
                break;
            case R.id.tv_community_user_add_master_relation:
                // 1 户主 2配偶 3子女 4儿媳 5女婿 6父母 0其他
                List<FilterSugarPressureInfo> releationShapeList = new ArrayList<>();
                FilterSugarPressureInfo releation1 = new FilterSugarPressureInfo("户主", "1");
                releationShapeList.add(releation1);
                FilterSugarPressureInfo releation2 = new FilterSugarPressureInfo("配偶", "2");
                releationShapeList.add(releation2);
                FilterSugarPressureInfo releation3 = new FilterSugarPressureInfo("子女", "3");
                releationShapeList.add(releation3);
                FilterSugarPressureInfo releation4 = new FilterSugarPressureInfo("儿媳", "4");
                releationShapeList.add(releation4);
                FilterSugarPressureInfo releation5 = new FilterSugarPressureInfo("女婿", "5");
                releationShapeList.add(releation5);
                FilterSugarPressureInfo releation6 = new FilterSugarPressureInfo("父母", "6");
                releationShapeList.add(releation6);
                FilterSugarPressureInfo releation7 = new FilterSugarPressureInfo("其他", "0");
                releationShapeList.add(releation7);
                getSugar(releationShapeList, "1");
                break;
            case R.id.tv_community_user_add_hospital_choose:
                getHospital("", "1");
                break;
            case R.id.tv_community_user_add_department_choose:
                if ("0".equals(hospitalid)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_hospital_choose);
                    return;
                }
                getDepartment();
                break;
            case R.id.tv_community_user_add_doctor_choose:
                if ("0".equals(departmentid) || "0".equals(hospitalid)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_department_choose_or_doctor);
                    return;
                }

                getDoctor();
                break;

            case R.id.ivb_community_add_device_sugar:
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(getPageContext(), CommunityScanActivity.class);
                                intent.putExtra("type", "2");
                                startActivityForResult(intent, REQUEST_CODE_FOR_IME_CODE);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();


                break;
            case R.id.iv_community_user_add_device_pressure:

                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(getPageContext(), CommunityScanActivity.class);
                                intent.putExtra("type", "1");
                                startActivityForResult(intent, REQUEST_CODE_FOR_IME_CODE);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();

                break;
            case R.id.cb_community_disease_sugar:

                if (sugarTextView.isSelected()) {
                    diabeteslei = "0";
                    sugarTextView.setSelected(false);
                } else {
                    List<FilterSugarPressureInfo> sugarList = new ArrayList<>();
                    FilterSugarPressureInfo info1 = new FilterSugarPressureInfo(getString(R.string.community_user_info_sugar_one), "1");
                    sugarList.add(info1);
                    FilterSugarPressureInfo info2 = new FilterSugarPressureInfo(getString(R.string.community_user_info_sugar_two), "2");
                    sugarList.add(info2);
                    FilterSugarPressureInfo info3 = new FilterSugarPressureInfo(getString(R.string.community_user_info_sugar_three), "3");
                    sugarList.add(info3);
                    FilterSugarPressureInfo info4 = new FilterSugarPressureInfo(getString(R.string.community_user_info_sugar_four), "4");
                    sugarList.add(info4);
                    getSugar(sugarList, "2");
                }

                break;
            case R.id.tv_community_disease_presure:
                if (pressureTextView.isSelected()) {
                    bloodLevel = "0";
                    hypertension = "1";
                    pressureTextView.setSelected(false);
                } else {
                    List<FilterSugarPressureInfo> pressureList = new ArrayList<>();
                    FilterSugarPressureInfo pressureinfo1 = new FilterSugarPressureInfo(getString(R.string.community_user_info_pressure_one), "1");
                    pressureList.add(pressureinfo1);
                    FilterSugarPressureInfo pressureinfo2 = new FilterSugarPressureInfo(getString(R.string.community_user_info_pressure_two), "2");
                    pressureList.add(pressureinfo2);
                    FilterSugarPressureInfo pressureinfo3 = new FilterSugarPressureInfo(getString(R.string.community_user_info_pressure_three), "3");
                    pressureList.add(pressureinfo3);

                    getSugar(pressureList, "3");
                }
                break;
            case R.id.tv_community_disease_head:
                if (headTextView.isSelected()) {
                    headTextView.setSelected(false);
                } else {
                    headTextView.setSelected(true);
                }
                break;
            case R.id.tvb_community_add_user_submit:
                upLoadData();
                break;
            default:
                break;
        }
    }

    /**
     * @param id   小区/楼栋/单元id，type != 1时,此字段必传，
     * @param type 1：查询小区2：查询楼栋3：查询单元 4：查询房间
     */
    private void getCommunityInfo(String id, String type) {
        TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.waiting, false);
        Call<String> requestCall = DataManager.getCommunityInfo(id, type, (call, response) -> {
            TipUtils.getInstance().dismissProgressDialog();
            if (200 == response.code) {
                List<BuildInfo> logisticsCompanyInfos = (List<BuildInfo>) response.object;
                if (logisticsCompanyInfos != null && logisticsCompanyInfos.size() > 0) {
                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                        if ("1".equals(type)) {
                            communityID = logisticsCompanyInfos.get(options1).getId();
                        } else if ("2".equals(type)) {
                            communityBuildID = logisticsCompanyInfos.get(options1).getId();
                        } else if ("3".equals(type)) {
                            communityUnitID = logisticsCompanyInfos.get(options1).getId();
                        } else {
                            communityHouseID = logisticsCompanyInfos.get(options1).getId();
                        }

                        String s = logisticsCompanyInfos.get(options1).getName();
                        if ("1".equals(type)) {
                            communityNameTextView.setText(s);
                        } else if ("2".equals(type)) {
                            communityBuildNameTextView.setText(s);
                        } else if ("3".equals(type)) {
                            communityUnitNameTextView.setText(s);
                        } else {
                            communityRoomNameTextView.setText(s);
                        }
                    }).setLineSpacingMultiplier(2.5f)
                            .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_text))
                            .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                            .build();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < logisticsCompanyInfos.size(); i++) {
                        String typeName = logisticsCompanyInfos.get(i).getName();
                        list.add(typeName);
                    }
                    optionsPickerView.setPicker(list);
                    optionsPickerView.show();
                }
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, throwable) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });

    }


    /**
     * 上传数据
     */
    private void upLoadData() {
        if ("0".equals(communityHouseID)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.please_choose_community_house);
            return;
        }

        String name = nameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_please_input_name);
            return;
        }
        String tel = phoneEditText.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_please_input_phone);
            return;
        }

        String idNum = idNumEditText.getText().toString().trim();

        if ("0".equals(sex)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_please_choose_sex);
            return;
        }

        if ("-1".equals(releationId)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.please_choose_user_add_master_relation);
            return;
        }
        if ("0".equals(hospitalid) || TextUtils.isEmpty(hospitalid)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_hospital_choose);
            return;
        }
        if ("0".equals(departmentid) || TextUtils.isEmpty(departmentid)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_department_choose);
            return;
        }
        if ("0".equals(doctorID) || TextUtils.isEmpty(doctorID)) {
            TipUtils.getInstance().showToast(getPageContext(), R.string.user_add_doctor_choose);
            return;
        }

        addReq.setHouse_id(communityHouseID);
        if (userid == null) {
            addReq.setUserid("0");
        } else {
            addReq.setUserid(userid);
        }

        addReq.setNickname(name);
        addReq.setTel(tel);
        addReq.setIdcard(idNum);

        addReq.setSex(sex);
        addReq.setBirthtime(born);
        addReq.setRelation(releationId);
        addReq.setHos_userid(hospitalid);
        addReq.setDep_userid(departmentid);
        addReq.setDoc_userid(doctorID);
        if (addReq.getDiabeteslei() == null) {
            addReq.setDiabeteslei("0");
        } else {
            addReq.setDiabeteslei(diabeteslei);
        }
        addReq.setHypertension(pressureTextView.isSelected() ? "1" : "2");
        if ("1".equals(addReq.getHypertension())) {
            addReq.setBloodLevel(bloodLevel);
        } else {
            addReq.setBloodLevel("0");
        }

        addReq.setStroke(headTextView.isSelected() ? "1" : "2");
        addReq.setCoronary(heartCheckBox.isChecked() ? "1" : "2");
        addReq.setMental_illness(spiritCheckBox.isChecked() ? "1" : "2");
        addReq.setPregnant(pregnantCheckBox.isChecked() ? "1" : "2");
        addReq.setTuberculosis(tuberculosisCheckBox.isChecked() ? "1" : "2");

        addReq.setParty_member(apartCheckBox.isChecked() ? "1" : "2");
        addReq.setDisability(illCheckBox.isChecked() ? "1" : "2");
        addReq.setDibao(allowanceCheckBox.isChecked() ? "1" : "2");
        addReq.setScd(heavyCheckBox.isChecked() ? "1" : "2");
        addReq.setSpecial_family(specialCheckBox.isChecked() ? "1" : "2");
        addReq.setEpidemic(epidemicCheckBox.isChecked() ? "1" : "2");
        addReq.setIscare(importCheckBox.isChecked() ? "1" : "2");
        addReq.setSugar_imei(sugarEditText.getText().toString().trim());
        addReq.setBlood_imei(pressureEditText.getText().toString().trim());
        Call<String> requestCall = DataManager.addUser(addReq, (call, response) -> {
            if (response.code == 200) {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
                setResult(RESULT_OK);
                finish();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, t) -> {
            addTextView.setClickable(true);
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    /**
     * 获取医生列表
     */
    private void getDoctor() {
        Call<String> requestCall = DataManager.getDoctorList(departmentid, buildid, (call, response) -> {
            if (200 == response.code) {
                SearchInfo searchInfo = (SearchInfo) response.object;
                List<DepartmentInfo> logisticsCompanyInfos = searchInfo.getDoc_list();
                if (logisticsCompanyInfos != null && logisticsCompanyInfos.size() > 0) {
                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                        doctorID = logisticsCompanyInfos.get(options1).getDoc_userid();
                        String s = logisticsCompanyInfos.get(options1).getDocname();
                        doctorTextView.setText(s);
                    }).setLineSpacingMultiplier(2.5f)
                            .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
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
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });

    }


    /**
     * 获取糖尿病的类型
     *
     * @param releationList
     * @param type          1:关系  2：糖尿病类型  3:血压
     */
    private void getSugar(List<FilterSugarPressureInfo> releationList, String type) {
        if (releationList != null && releationList.size() > 0) {
            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                if ("1".equals(type)) {

                    releationId = releationList.get(options1).getCheckID();
                    String s = releationList.get(options1).getDiseaseName();
                    relationTextView.setText(s);
                } else if ("2".equals(type)) {
                    sugarTextView.setSelected(true);
                    diabeteslei = releationList.get(options1).getCheckID();
                    String s = releationList.get(options1).getDiseaseName();
                    sugarTextView.setText(s);
                } else {
                    hypertension = "2";
                    bloodLevel = releationList.get(options1).getCheckID();
                    String s = releationList.get(options1).getDiseaseName();
                    pressureTextView.setText(s);
                    pressureTextView.setSelected(true);

                }
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
     * 根据医院id 获取科室
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
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });

    }

    /**
     * @param tel  电话
     * @param type 1：选择主要是获取医院的id 2：是根据手机号去搜索
     */
    private void getHospital(String tel, String type) {
        Call<String> requestCall = DataManager.getHospitalList(tel, buildid, (call, response) -> {
            if ("1".equals(type)) {
                if (30004 == response.code) {
                    SearchInfo searchInfo = (SearchInfo) response.object;
                    Log.i("yys", "searchinfo==" + (searchInfo == null));
                    hospitalid = searchInfo.getHospital().getHos_userid();
                    hospitalTextView.setText(searchInfo.getHospital().getHosp_name());
                } else if (200 == response.code) {
                    SearchInfo searchInfo = (SearchInfo) response.object;
                    hospitalid = searchInfo.getHosInfo().getHos_userid();
                    hospitalTextView.setText(searchInfo.getHosInfo().getHosp_name());
                }

            } else if ("2".equals(type)) {
                //这是搜索到人
                if (response.code == 200) {
                    SearchInfo searchInfo = (SearchInfo) response.object;
                    //搜索到人，这里要赋值，同时设置是否可以点击
                    userid = searchInfo.getUserid();
                    nameEditText.setText(searchInfo.getNickname());
                    sex = searchInfo.getSex();
                    if ("1".equals(searchInfo.getSex())) {
                        sexTextView.setText("男");
                    } else {
                        sexTextView.setText("女");
                    }
                    born = searchInfo.getBirthtime();
                    bornTextView.setText(born);
                    if (!TextUtils.isEmpty(searchInfo.getHospitalname())) {
                        hospitalid = searchInfo.getHos_userid();
                        hospitalTextView.setText(searchInfo.getHospitalname());
                        hospitalTextView.setClickable(true);
                    } else {
                        hospitalTextView.setClickable(false);
                    }
                    if (!TextUtils.isEmpty(searchInfo.getDepname())) {
                        departmentid = searchInfo.getDep_userid();
                        departmentTextView.setText(searchInfo.getDepname());
                        departmentTextView.setClickable(true);
                    } else {
                        departmentTextView.setClickable(false);
                    }

                    if (!TextUtils.isEmpty(searchInfo.getDocname())) {
                        doctorID = searchInfo.getDoc_id();
                        doctorTextView.setText(searchInfo.getDocname());
                        doctorTextView.setClickable(true);
                    } else {
                        doctorTextView.setClickable(false);
                    }


                    diabeteslei = searchInfo.getDiabeteslei();
                    if ("1".equals(searchInfo.getDiabeteslei())) {
                        sugarTextView.setText(R.string.community_user_info_sugar_one);
                        sugarTextView.setSelected(true);
                    } else if ("2".equals(searchInfo.getDiabeteslei())) {
                        sugarTextView.setText(R.string.community_user_info_sugar_two);
                        sugarTextView.setSelected(true);
                    } else if ("3".equals(searchInfo.getDiabeteslei())) {
                        sugarTextView.setText(R.string.community_user_info_sugar_three);
                        sugarTextView.setSelected(true);
                    } else if ("4".equals(searchInfo.getDiabeteslei())) {
                        sugarTextView.setText(R.string.community_user_info_sugar_four);
                        sugarTextView.setSelected(true);
                    } else if ("0".equals(searchInfo.getDiabeteslei())) {
                        sugarTextView.setText(R.string.community_user_info_sugar_zero);
                        sugarTextView.setSelected(false);
                    } else {
                        sugarTextView.setText(R.string.community_user_info_sugar_zero);
                        sugarTextView.setSelected(false);
                    }
                    hypertension = searchInfo.getHypertension();

                    if ("0".equals(searchInfo.getHypertension())) {
                        pressureTextView.setSelected(false);
                        pressureTextView.setText(R.string.community_user_info_pressure_zero);
                    } else if ("1".equals(searchInfo.getHypertension())) {
                        pressureTextView.setSelected(true);
                        bloodLevel = "1";
                        pressureTextView.setText(R.string.community_user_info_pressure_one);
                        pressureTextView.setSelected(true);
                    } else if ("2".equals(searchInfo.getHypertension())) {
                        bloodLevel = "2";
                        pressureTextView.setText(R.string.community_user_info_pressure_two);
                        pressureTextView.setSelected(true);
                    } else {
                        bloodLevel = "3";
                        pressureTextView.setText(R.string.community_user_info_pressure_three);
                        pressureTextView.setSelected(true);
                    }


                    if ("1".equals(searchInfo.getCoronary())) {
                        heartCheckBox.setChecked(true);
                    } else {
                        heartCheckBox.setChecked(false);
                    }
                    if ("1".equals(searchInfo.getMental_illness())) {
                        spiritCheckBox.setChecked(true);
                    } else {
                        spiritCheckBox.setChecked(false);
                    }

                    if ("1".equals(searchInfo.getParty_member())) {
                        apartCheckBox.setChecked(true);
                    } else {
                        apartCheckBox.setChecked(false);
                    }

                    if ("1".equals(searchInfo.getDisability())) {
                        illCheckBox.setChecked(true);
                    } else {
                        illCheckBox.setChecked(false);
                    }


                    if (!TextUtils.isEmpty(searchInfo.getSugar_imei())) {
                        sugarEditText.setText(searchInfo.getSugar_imei());
                    }
                    if (!TextUtils.isEmpty(searchInfo.getBlood_imei())) {
                        pressureEditText.setText(searchInfo.getSugar_imei());
                    }


                    //这一块是关于医生科室
                    if (!TextUtils.isEmpty(searchInfo.getDocname())) {
                        //绑定医生
                        hospitalTextView.setClickable(false);
                        departmentTextView.setClickable(false);
                        doctorTextView.setClickable(false);
                        doctorID = searchInfo.getDoc_id();
                        departmentid = searchInfo.getDep_userid();
                        hospitalid = searchInfo.getHos_userid();
                    } else {
                        hospitalTextView.setClickable(true);
                        departmentTextView.setClickable(true);
                        doctorTextView.setClickable(true);
                    }

                    sugarEditText.setText(searchInfo.getSugar_imei());
                    pressureEditText.setText(searchInfo.getBlood_imei());
                } else if (30004 == response.code) {
                    SearchInfo searchInfo = (SearchInfo) response.object;
                    hospitalid = searchInfo.getHospital().getHos_userid();
                    hospitalTextView.setText(searchInfo.getHospital().getHosp_name());
                }


            }
        }, (call, throwable) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });

    }


    private void showTimeWindow() {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(getPageContext(), (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y_M_D);
            born = content;
            bornTextView.setText(content);
        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
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

            sex = options1 + 1 + "";
            sexTextView.setText("1".equals(sex) ? "男" : "女");
        }).setLineSpacingMultiplier(2.5f)
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.gray_light))
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .build();
        optionsPickerView.setPicker(sexList);
        optionsPickerView.show();
    }

    private void setDissMiss() {
        View view = (this).getWindow().peekDecorView();
        if (isSoftShowing()) {
            if (view != null && view.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private boolean isSoftShowing() {
        //获取当屏幕内容的高度
        int screenHeight = this.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_IME_CODE) {
                if (data != null) {
                    String type = data.getStringExtra("type");
                    String imei = data.getStringExtra("imei");
                    if ("1".equals(type)) {
                        pressureEditText.setText(imei);
                    } else {
                        sugarEditText.setText(imei);
                    }
                }
            } else if (requestCode == REQUEST_CODE_FOR_CHOOSE) {
                if (data != null) {
                    SearchInfo info = (SearchInfo) data.getSerializableExtra("importInfo");
                    userid = info.getUserid();
                    nameEditText.setText(info.getNickname());
                    phoneEditText.setText(info.getTel());
                    idNumEditText.setText(info.getIdcard());
                    sex = info.getSex();
                    if ("1".equals(info.getSex())) {
                        sexTextView.setText(R.string.base_male);
                    } else {
                        sexTextView.setText(R.string.base_female);
                    }
                    born = info.getBirthtime();
                    bornTextView.setText(info.getBirthtime());
                }

            }
        }
    }

    private void initListener() {
        //        communityNameTextView.setOnClickListener(this);
        //        communityBuildNameTextView.setOnClickListener(this);
        //        communityUnitNameTextView.setOnClickListener(this);
        //        communityRoomNameTextView.setOnClickListener(this);
        sexTextView.setOnClickListener(this);
        bornTextView.setOnClickListener(this);
        relationTextView.setOnClickListener(this);
        hospitalTextView.setOnClickListener(this);
        departmentTextView.setOnClickListener(this);
        doctorTextView.setOnClickListener(this);
        headTextView.setOnClickListener(this);
        sugarImageView.setOnClickListener(this);
        pressureImageView.setOnClickListener(this);
        pressureTextView.setOnClickListener(this);
        sugarTextView.setOnClickListener(this);
        addTextView.setOnClickListener(this);


        phoneEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    getHospital(phoneEditText.getText().toString().trim(), "2");
                    return true;
                }
                return false;
            }
        });


    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_user_add_second, null);
        communityNameTextView = view.findViewById(R.id.tv_user_add_community_name);
        communityBuildNameTextView = view.findViewById(R.id.tv_user_add_community_build_name);
        communityUnitNameTextView = view.findViewById(R.id.tv_user_add_community_unit_name);
        communityRoomNameTextView = view.findViewById(R.id.tv_user_add_community_room_name);
        nameEditText = view.findViewById(R.id.et_community_user_add_name);
        phoneEditText = view.findViewById(R.id.et_community_user_add_phone);
        idNumEditText = view.findViewById(R.id.et_community_user_add_id_number);
        sexTextView = view.findViewById(R.id.tv_community_user_add_sex);
        bornTextView = view.findViewById(R.id.tv_community_user_add_born);
        relationTextView = view.findViewById(R.id.tv_community_user_add_master_relation);
        hospitalTextView = view.findViewById(R.id.tv_community_user_add_hospital_choose);
        departmentTextView = view.findViewById(R.id.tv_community_user_add_department_choose);
        doctorTextView = view.findViewById(R.id.tv_community_user_add_doctor_choose);


        sugarTextView = view.findViewById(R.id.cb_community_disease_sugar);
        pressureTextView = view.findViewById(R.id.tv_community_disease_presure);
        headTextView = view.findViewById(R.id.tv_community_disease_head);
        heartCheckBox = view.findViewById(R.id.cb_community_disease_heart);
        spiritCheckBox = view.findViewById(R.id.cb_community_disease_mental_illness);
        pregnantCheckBox = view.findViewById(R.id.cb_community_disease_pregnant);
        tuberculosisCheckBox = view.findViewById(R.id.cb_community_disease_tuberculosis);

        apartCheckBox = view.findViewById(R.id.cb_community_disease_apart);
        illCheckBox = view.findViewById(R.id.cb_community_disease_ill);
        allowanceCheckBox = view.findViewById(R.id.cb_community_disease_subsistence_allowance);
        heavyCheckBox = view.findViewById(R.id.cb_community_disease_heavy_ill);
        specialCheckBox = view.findViewById(R.id.cb_community_disease_special_family);
        epidemicCheckBox = view.findViewById(R.id.cb_community_disease_control_epidemic);
        importCheckBox = view.findViewById(R.id.cb_community_disease_import);
        sugarEditText = view.findViewById(R.id.et_community_user_add_device_sugar);
        sugarImageView = view.findViewById(R.id.ivb_community_add_device_sugar);
        pressureEditText = view.findViewById(R.id.et_community_user_add_device_pressure);
        pressureImageView = view.findViewById(R.id.iv_community_user_add_device_pressure);
        addTextView = view.findViewById(R.id.tvb_community_add_user_submit);
        return view;
    }

}
