package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.BuildInfo;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.bean.community_manamer.SearchInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.param.UserRecordReq;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.DialogUtils;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/21 17:32
 * Description：社区档案  有幸改到这个页面的朋友，请不要骂我，我本来脑子里构思的感觉很简单，但是写着写着越来越麻烦，但是我不想改回去了
 * 传参 userid 用户的userid
 */
public class UserRecordActivity extends XYSoftUIBaseActivity implements View.OnClickListener {
    private LinearLayout allLinearLayout;
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
    private TextView nameTextView;
    /**
     * 手机号
     */
    private TextView phoneTextView;
    private TextView idNumTextView;
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

    private TextView heartTextView, spiritTextView, pregnantTextView, tuberculosisTextView;
    private TextView apartTextView, illTextView, allowanceTextView, heavyTextView, specialTextView, epidemicTextView, importTextView;


    private TextView addTextView;

    private String userid;
    private String username;
    private String isDead;

    private SearchInfo info;
    private UserRecordReq addReq = new UserRecordReq();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_record_title);

        containerView().addView(initView());
        initListener();
        userid = getIntent().getStringExtra("userid");
        username = getIntent().getStringExtra("username");
        isDead = getIntent().getStringExtra("isDead");
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        Call<String> requestCall = DataManager.getRecordInfo(userid, (call, response) -> {
            if (response.code == 200) {
                allLinearLayout.setVisibility(View.VISIBLE);
                topViewManager().moreTextView().setText("特殊操作");
                topViewManager().moreTextView().setOnClickListener(v -> {
                    Intent intent = new Intent(getPageContext(), SpecialOperateActivity.class);

                    intent.putExtra("username", info.getNickname());
                    intent.putExtra("userid", userid);
                    intent.putExtra("isDead", info.getIsdeath());
                    startActivity(intent);
                });
                info = (SearchInfo) response.object;
                bindData();
            } else {
                allLinearLayout.setVisibility(View.GONE);
            }
        }, (call, t) -> {
            allLinearLayout.setVisibility(View.GONE);

            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {
        addReq.setUserid(userid);
        communityNameTextView.setText(info.getCom_name());
        communityBuildNameTextView.setText(info.getBuild_name());
        communityUnitNameTextView.setText(info.getUnit_name());
        communityRoomNameTextView.setText(info.getHouse_num());
        nameTextView.setText(info.getNickname());
        addReq.setNickname(info.getNickname());
        phoneTextView.setText(info.getTel());
        addReq.setTel(info.getTel());
        idNumTextView.setText(info.getIdcard());
        addReq.setSex(info.getSex());
        if ("1".equals(info.getSex())) {
            sexTextView.setText(R.string.base_male);
        } else {
            sexTextView.setText(R.string.base_female);
        }
        bornTextView.setText(info.getBirthtime());
        addReq.setBirthtime(info.getBirthtime());
        addReq.setRelation(info.getRelation());
        setRelationData();

        hospitalTextView.setText(info.getHospitalname());
        departmentTextView.setText(info.getDepname());
        doctorTextView.setText(info.getDocname());
        addReq.setDiabeteslei(info.getDiabeteslei());
        setSugarData();
        addReq.setBloodLevel(info.getBloodLevel());
        addReq.setHypertension(info.getHypertension());
        setBloodData(info.getHypertension());
        addReq.setStroke(info.getStroke());
        if ("1".equals(info.getStroke())) {
            setCheckData(true, headTextView);
        } else {
            setCheckData(false, headTextView);
        }
        addReq.setCoronary(info.getCoronary());
        if ("1".equals(info.getCoronary())) {
            setCheckData(true, heartTextView);
        } else {
            setCheckData(false, heartTextView);
        }
        addReq.setMental_illness(info.getMental_illness());
        if ("1".equals(info.getMental_illness())) {
            setCheckData(true, spiritTextView);
        } else {
            setCheckData(false, spiritTextView);
        }
        addReq.setPregnant(info.getPregnant());
        if ("1".equals(info.getPregnant())) {
            setCheckData(true, pregnantTextView);
        } else {
            setCheckData(false, pregnantTextView);
        }
        addReq.setTuberculosis(info.getTuberculosis());
        if ("1".equals(info.getTuberculosis())) {
            setCheckData(true, tuberculosisTextView);
        } else {
            setCheckData(false, tuberculosisTextView);
        }

        addReq.setParty_member(info.getParty_member());
        if ("1".equals(info.getParty_member())) {
            setCheckData(true, apartTextView);
        } else {
            setCheckData(false, apartTextView);
        }
        addReq.setDisability(info.getDisability());
        if ("1".equals(info.getDisability())) {
            setCheckData(true, illTextView);
        } else {
            setCheckData(false, illTextView);
        }
        addReq.setDibao(info.getDibao());
        if ("1".equals(info.getDibao())) {
            setCheckData(true, allowanceTextView);
        } else {
            setCheckData(false, allowanceTextView);
        }
        addReq.setScd(info.getScd());
        if ("1".equals(info.getScd())) {
            setCheckData(true, heavyTextView);
        } else {
            setCheckData(false, heavyTextView);
        }
        addReq.setSpecial_family(info.getSpecial_family());
        if ("1".equals(info.getSpecial_family())) {
            setCheckData(true, specialTextView);
        } else {
            setCheckData(false, specialTextView);
        }
        addReq.setEpidemic(info.getEpidemic());
        if ("1".equals(info.getEpidemic())) {
            setCheckData(true, epidemicTextView);
        } else {
            setCheckData(false, epidemicTextView);
        }
        addReq.setIscare(info.getIscare());
        if ("1".equals(info.getIscare())) {
            setCheckData(true, importTextView);
        } else {
            setCheckData(false, importTextView);
        }
    }

    private void setCheckData(boolean isCheck, TextView textView) {
        textView.setSelected(isCheck);
    }

    /**
     * 设置关系
     */
    private void setRelationData() {
        if ("1".equals(addReq.getRelation())) {
            relationTextView.setText("户主");
        } else if ("2".equals(addReq.getRelation())) {
            relationTextView.setText("配偶");
        } else if ("3".equals(addReq.getRelation())) {
            relationTextView.setText("子女");
        } else if ("4".equals(addReq.getRelation())) {
            relationTextView.setText("儿媳");
        } else if ("5".equals(addReq.getRelation())) {
            relationTextView.setText("女婿");
        } else if ("6".equals(addReq.getRelation())) {
            relationTextView.setText("父母");
        } else {
            relationTextView.setText("其他");
        }
    }

    /**
     * 设置血压
     */
    private void setBloodData(String hypertension) {
        if ("1".equals(hypertension)) {
            setCheckData(true, pressureTextView);
            if ("1".equals(addReq.getBloodLevel())) {
                setCheckData(true, pressureTextView);
                pressureTextView.setText(R.string.community_user_info_pressure_one);
            } else if ("2".equals(addReq.getBloodLevel())) {
                setCheckData(true, pressureTextView);
                pressureTextView.setText(R.string.community_user_info_pressure_two);
            } else {
                setCheckData(true, pressureTextView);
                pressureTextView.setText(R.string.community_user_info_pressure_three);
            }
        } else {
            setCheckData(false, pressureTextView);
            pressureTextView.setText("高血压");
        }
    }

    /**
     * 设置血糖
     */
    private void setSugarData() {
        if ("1".equals(addReq.getDiabeteslei())) {
            sugarTextView.setSelected(true);
            sugarTextView.setText(R.string.community_user_info_sugar_one);
        } else if ("2".equals(addReq.getDiabeteslei())) {
            sugarTextView.setSelected(true);
            sugarTextView.setText(R.string.community_user_info_sugar_two);
        } else if ("3".equals(addReq.getDiabeteslei())) {
            sugarTextView.setSelected(true);
            sugarTextView.setText(R.string.community_user_info_sugar_three);
        } else if ("4".equals(addReq.getDiabeteslei())) {
            sugarTextView.setSelected(true);
            sugarTextView.setText(R.string.community_user_info_sugar_four);
        } else {
            sugarTextView.setSelected(false);
        }

    }


    private void showEditDialog(String type, String title, String hint) {
        DialogUtils.getInstance().showEditTextDialog(getPageContext(), title, hint, text1 -> {
            editInfo(type, text1, "2");
        });
    }

    /**
     * @param type  1:姓名  2：手机号 3：性别 4：出生日期  5:关系6：血糖 7：血压 8:脑卒中 9：冠心病 10精神疾病 11孕  12结核
     *              13党员  14残疾人 15低保 16重慢病 17特殊家庭 18疫情防控 19重点关注
     * @param value
     */
    private void editInfo(String type, String value, String hypertension) {
        setReqData(type, value, false, hypertension);
        Log.i("yys", "1epidemic=======" + addReq.getEpidemic());
        Call<String> requestCall = DataManager.addRecord(addReq, (call, response) -> {
            TipUtils.getInstance().showToast(getPageContext(), response.msg);
            Log.i("yys", "1type==" + type + "1values==" + value);
            if (response.code == 200) {
                setReqData(type, value, true, hypertension);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void setReqData(String type, String value, boolean isSetText, String hypertension) {
        Log.i("yys", "2type===" + type + "2values==" + value);
        switch (type) {
            case "1":
                if (isSetText) {
                    nameTextView.setText(value);
                }
                addReq.setNickname(value);
                break;
            case "2":
                if (isSetText) {
                    phoneTextView.setText(value);
                }
                addReq.setTel(value);
                break;
            case "3":
                if (isSetText) {
                    sexTextView.setText("1".equals(value) ? "男" : "女");
                }
                addReq.setSex(value);
                break;
            case "4":
                if (isSetText) {
                    bornTextView.setText(value);
                }
                addReq.setBirthtime(value);
                break;
            case "5":
                if (isSetText) {
                    setRelationData();
                }
                addReq.setRelation(value);
                break;
            case "6":
                if (isSetText) {
                    setSugarData();
                }
                addReq.setDiabeteslei(value);
                break;
            case "7":
                addReq.setBloodLevel(value);
                addReq.setHypertension(value);
                if (isSetText) {
                    setBloodData(hypertension);
                }

                break;
            case "8":
                addReq.setStroke(value);
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, headTextView);
                    } else {
                        setCheckData(false, headTextView);
                    }
                }
                break;
            case "9":
                addReq.setCoronary(value);
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, heartTextView);
                    } else {
                        setCheckData(false, heartTextView);
                    }
                }
                break;
            case "10":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, spiritTextView);
                    } else {
                        setCheckData(false, spiritTextView);
                    }
                }
                addReq.setMental_illness(value);
                break;
            case "11":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, pregnantTextView);
                    } else {
                        setCheckData(false, pregnantTextView);
                    }
                }
                addReq.setPregnant(value);
                break;
            case "12":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, tuberculosisTextView);
                    } else {
                        setCheckData(false, tuberculosisTextView);
                    }
                }
                addReq.setTuberculosis(value);
                break;
            case "13":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, apartTextView);
                    } else {
                        setCheckData(false, apartTextView);
                    }
                }
                addReq.setParty_member(value);
                break;
            case "14":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, illTextView);
                    } else {
                        setCheckData(false, illTextView);
                    }
                }
                addReq.setDisability(value);
                break;
            case "15":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, allowanceTextView);
                    } else {
                        setCheckData(false, allowanceTextView);
                    }
                }
                addReq.setDibao(value);
                break;
            case "16":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, heavyTextView);
                    } else {
                        setCheckData(false, heavyTextView);
                    }
                }
                addReq.setScd(value);
                break;
            case "17":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, specialTextView);
                    } else {
                        setCheckData(false, specialTextView);
                    }
                }
                addReq.setSpecial_family(value);
                break;
            case "18":
                Log.i("yys", "3type==" + type + "3value" + value);
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, epidemicTextView);
                    } else {
                        setCheckData(false, epidemicTextView);
                    }
                }
                addReq.setEpidemic(value);
                break;
            case "19":
                if (isSetText) {
                    if ("1".equals(value)) {
                        setCheckData(true, importTextView);
                    } else {
                        setCheckData(false, importTextView);
                    }
                }
                addReq.setIscare(value);
                break;

            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_record_user_name:
                showEditDialog("1", getString(R.string.user_personal_change_nickname), getString(R.string.user_add_please_input_name));
                break;
            case R.id.tv_record_user_phone:
                showEditDialog("2", getString(R.string.user_add_phone), getString(R.string.user_add_please_input_phone));
                break;
            case R.id.tv_record_user_sex:
                setDissMiss();
                chooseSexWindow();
                break;
            case R.id.tv_record_user_born:
                setDissMiss();
                showTimeWindow();
                break;
            case R.id.tv_record_user_master_relation:
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
                getSugar(releationShapeList, "5");
                break;
            case R.id.tv_record_user_disease_sugar:
                if (sugarTextView.isSelected()) {
                    editInfo("6", "0", "2");
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
                    getSugar(sugarList, "6");
                }

                break;
            case R.id.tv_record_user_disease_presure:
                if (pressureTextView.isSelected()) {
                    editInfo("7", "2", "2");
                } else {
                    List<FilterSugarPressureInfo> pressureList = new ArrayList<>();
                    FilterSugarPressureInfo pressureinfo1 = new FilterSugarPressureInfo(getString(R.string.community_user_info_pressure_one), "1");
                    pressureList.add(pressureinfo1);
                    FilterSugarPressureInfo pressureinfo2 = new FilterSugarPressureInfo(getString(R.string.community_user_info_pressure_two), "2");
                    pressureList.add(pressureinfo2);
                    FilterSugarPressureInfo pressureinfo3 = new FilterSugarPressureInfo(getString(R.string.community_user_info_pressure_three), "3");
                    pressureList.add(pressureinfo3);

                    getSugar(pressureList, "7");
                }
                break;
            //1:姓名 2：手机号 3：性别 4：出生日期 5:关系6：血糖 7：血压 8:脑卒中 9：冠心病 10 精神疾病 11 孕 12 结核
            //      * 13 党员 14 残疾人 15 低保 16 重慢病 17 特殊家庭 18 疫情防控 19 重点关注
            case R.id.tv_record_user_disease_head:
                if ("1".equals(addReq.getStroke())) {
                    editInfo("8", "2", "2");
                } else {
                    editInfo("8", "1", "2");
                }
                break;
            case R.id.cb_record_disease_heart:
                if ("1".equals(addReq.getCoronary())) {
                    editInfo("9", "2", "2");
                } else {
                    editInfo("9", "1", "2");
                }
                break;
            case R.id.cb_record_disease_mental_illness:
                if ("1".equals(addReq.getMental_illness())) {
                    editInfo("10", "2", "2");
                } else {
                    editInfo("10", "1", "2");
                }
                break;
            case R.id.cb_record_disease_pregnant:
                if ("1".equals(addReq.getPregnant())) {
                    editInfo("11", "2", "2");
                } else {
                    editInfo("11", "1", "2");
                }
                break;
            case R.id.cb_filter_record_tuberculosis:
                if ("1".equals(addReq.getTuberculosis())) {
                    editInfo("12", "2", "2");
                } else {
                    editInfo("12", "1", "2");
                }
                break;
            case R.id.cb_record_disease_apart:
                if ("1".equals(addReq.getParty_member())) {
                    editInfo("13", "2", "2");
                } else {
                    editInfo("13", "1", "2");
                }
                break;
            case R.id.cb_record_disease_ill:
                if ("1".equals(addReq.getDisability())) {
                    editInfo("14", "2", "2");
                } else {
                    editInfo("14", "1", "2");
                }
                break;
            case R.id.cb_record_disease_subsistence_allowance:
                if ("1".equals(addReq.getDibao())) {
                    editInfo("15", "2", "2");
                } else {
                    editInfo("15", "1", "2");
                }
                break;
            case R.id.cb_record_disease_heavy_ill:
                if ("1".equals(addReq.getScd())) {
                    editInfo("16", "2", "2");
                } else {
                    editInfo("16", "1", "2");
                }
                break;
            case R.id.cb_record_disease_special_family:
                if ("1".equals(addReq.getSpecial_family())) {
                    editInfo("17", "2", "2");
                } else {
                    editInfo("17", "1", "2");
                }
                break;
            case R.id.cb_record_disease_control_epidemic:
                Log.i("yys", "epidemic===" + addReq.getEpidemic());
                Log.i("yys", "epidemic===" + info.getEpidemic());
                if ("1".equals(addReq.getEpidemic())) {
                    editInfo("18", "2", "2");
                } else {
                    editInfo("18", "1", "2");
                }
                break;
            case R.id.cb_record_disease_import:
                if ("1".equals(addReq.getIscare())) {
                    editInfo("19", "2", "2");
                } else {
                    editInfo("19", "1", "2");
                }
                break;


            case R.id.tv_record_user_look:
                intent = new Intent(getPageContext(), CommunityHeavyRecordActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
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
     * 获取糖尿病的类型
     *
     * @param releationList
     * @param type          5:关系  6：糖尿病类型  7:高血压
     */
    private void getSugar(List<FilterSugarPressureInfo> releationList, String type) {
        if (releationList != null && releationList.size() > 0) {
            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                editInfo(type, releationList.get(options1).getCheckID(), "1");
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


    private void showTimeWindow() {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(getPageContext(), (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y_M_D);
            editInfo("4", content, "2");
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

            String sex = options1 + 1 + "";
            editInfo("3", sex, "2");
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


    private void initListener() {
        nameTextView.setOnClickListener(this);
        phoneTextView.setOnClickListener(this);
        sexTextView.setOnClickListener(this);
        bornTextView.setOnClickListener(this);
        relationTextView.setOnClickListener(this);
        headTextView.setOnClickListener(this);
        sugarTextView.setOnClickListener(this);
        pressureTextView.setOnClickListener(this);
        headTextView.setOnClickListener(this);
        heartTextView.setOnClickListener(this);
        spiritTextView.setOnClickListener(this);
        pregnantTextView.setOnClickListener(this);
        tuberculosisTextView.setOnClickListener(this);
        apartTextView.setOnClickListener(this);
        illTextView.setOnClickListener(this);
        allowanceTextView.setOnClickListener(this);
        heavyTextView.setOnClickListener(this);
        specialTextView.setOnClickListener(this);
        epidemicTextView.setOnClickListener(this);
        importTextView.setOnClickListener(this);
        addTextView.setOnClickListener(this);

    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_user_record, null);
        allLinearLayout = view.findViewById(R.id.ll_record_all);
        communityNameTextView = view.findViewById(R.id.tv_user_record_community_name);
        communityBuildNameTextView = view.findViewById(R.id.tv_user_record_build_name);
        communityUnitNameTextView = view.findViewById(R.id.tv_user_record_unit_name);
        communityRoomNameTextView = view.findViewById(R.id.tv_user_record_room_name);
        nameTextView = view.findViewById(R.id.tv_record_user_name);
        phoneTextView = view.findViewById(R.id.tv_record_user_phone);
        idNumTextView = view.findViewById(R.id.tv_record_user_id_number);
        sexTextView = view.findViewById(R.id.tv_record_user_sex);
        bornTextView = view.findViewById(R.id.tv_record_user_born);
        relationTextView = view.findViewById(R.id.tv_record_user_master_relation);
        hospitalTextView = view.findViewById(R.id.tv_record_user_hospital_choose);
        departmentTextView = view.findViewById(R.id.tv_record_user_department_choose);
        doctorTextView = view.findViewById(R.id.tv_record_user_doctor_choose);


        sugarTextView = view.findViewById(R.id.tv_record_user_disease_sugar);
        pressureTextView = view.findViewById(R.id.tv_record_user_disease_presure);
        headTextView = view.findViewById(R.id.tv_record_user_disease_head);
        heartTextView = view.findViewById(R.id.cb_record_disease_heart);
        spiritTextView = view.findViewById(R.id.cb_record_disease_mental_illness);
        pregnantTextView = view.findViewById(R.id.cb_record_disease_pregnant);
        tuberculosisTextView = view.findViewById(R.id.cb_filter_record_tuberculosis);

        apartTextView = view.findViewById(R.id.cb_record_disease_apart);
        illTextView = view.findViewById(R.id.cb_record_disease_ill);
        allowanceTextView = view.findViewById(R.id.cb_record_disease_subsistence_allowance);
        heavyTextView = view.findViewById(R.id.cb_record_disease_heavy_ill);
        specialTextView = view.findViewById(R.id.cb_record_disease_special_family);
        epidemicTextView = view.findViewById(R.id.cb_record_disease_control_epidemic);
        importTextView = view.findViewById(R.id.cb_record_disease_import);
        addTextView = view.findViewById(R.id.tv_record_user_look);
        return view;
    }

}
