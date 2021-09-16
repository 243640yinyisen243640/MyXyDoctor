package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.FilterDiseaseTypeAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineInfo;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/12 11:04
 * Description: 筛选页面
 */
public class CommunityFilterActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private TextView allCommunityTextView;
    private CheckBox buildingInformationCb;
    private CheckBox femaleCheckBox;
    private CheckBox maleCheckBox;
    private EditText lowermostEditText;
    private EditText mostEditText;
    private NoConflictGridView diseaseGridView;
    private NoConflictGridView otherGridView;
    private TextView resetTextView;
    private TextView submitTextView;

    /**
     * 小区的id
     */
    private String communityID = "0";

    private List<FilterSugarPressureInfo> diseaseList;
    private List<FilterSugarPressureInfo> otherInfoList;

    private FilterDiseaseTypeAdapter diseaseTypeAdapter;
    private FilterDiseaseTypeAdapter otherTypeAdapter;

    /**
     * 空房间是否选中
     */
    private boolean isCancheck = true;


    /**
     * 是否空房间
     */
    private String isEmpty;
    /**
     * 小区id，全部小区传0
     */
    private String com_id;
    /**
     * 性别
     * 1：男
     * 2：女
     */
    private String sex;
    /**
     * 年龄最小值
     */
    private String age_min;
    /**
     * 年龄最大值
     */
    private String age_max;
    /**
     * 其他信息(英文逗号分隔)
     * 1：残疾，
     * 2：精神问题
     * 3：党员
     * 4：重点关注
     * 5：死亡
     */
    private String other;
    /**
     * 疾病类型(英文逗号分隔)
     * 1：糖尿病
     * 2：高血压
     * 3：超重/肥胖
     * 4：冠心病
     * 5：脑卒中
     * 6：脂肪肝
     */
    private String disease;

    private String type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.base_filter);
        type = getIntent().getStringExtra("type");
        containerView().addView(initView());
        initListener();
        initValues();
    }

    private void initValues() {
        diseaseList = new ArrayList<>();
        FilterSugarPressureInfo typeInfo1 = new FilterSugarPressureInfo("糖尿病", "1");
        diseaseList.add(typeInfo1);
        FilterSugarPressureInfo typeInfo2 = new FilterSugarPressureInfo("高血压", "2");
        diseaseList.add(typeInfo2);
        FilterSugarPressureInfo typeInfo3 = new FilterSugarPressureInfo("超重/肥胖", "3");
        diseaseList.add(typeInfo3);
        FilterSugarPressureInfo typeInfo4 = new FilterSugarPressureInfo("冠心病", "4");
        diseaseList.add(typeInfo4);
        FilterSugarPressureInfo typeInfo5 = new FilterSugarPressureInfo("脑卒中", "5");
        diseaseList.add(typeInfo5);
        FilterSugarPressureInfo typeInfo6 = new FilterSugarPressureInfo("脂肪肝", "6");
        diseaseList.add(typeInfo6);

        diseaseTypeAdapter = new FilterDiseaseTypeAdapter(getPageContext(), diseaseList, "1");
        diseaseGridView.setAdapter(diseaseTypeAdapter);


        otherInfoList = new ArrayList<>();
        FilterSugarPressureInfo otherInfoList1 = new FilterSugarPressureInfo("残疾人", "1");
        otherInfoList.add(otherInfoList1);
        FilterSugarPressureInfo otherInfoList2 = new FilterSugarPressureInfo("精神问题", "2");
        otherInfoList.add(otherInfoList2);
        FilterSugarPressureInfo otherInfoList3 = new FilterSugarPressureInfo("党员", "3");
        otherInfoList.add(otherInfoList3);
        FilterSugarPressureInfo otherInfoList4 = new FilterSugarPressureInfo("重点关注", "4");
        otherInfoList.add(otherInfoList4);
        FilterSugarPressureInfo otherInfoList5 = new FilterSugarPressureInfo("死亡", "5");
        otherInfoList.add(otherInfoList5);
        diseaseGridView.setOnItemClickListener((parent, view, position, id) -> {
            Log.i("yys", "diseaseGridView");
            diseaseList.get(position).setCheck(!diseaseList.get(position).isCheck());
            diseaseTypeAdapter.notifyDataSetChanged();

        });
        otherTypeAdapter = new FilterDiseaseTypeAdapter(getPageContext(), otherInfoList, "1");
        otherGridView.setAdapter(otherTypeAdapter);

        otherGridView.setOnItemClickListener((parent, view, position, id) -> {
            otherInfoList.get(position).setCheck(!otherInfoList.get(position).isCheck());
            otherTypeAdapter.notifyDataSetChanged();

        });

    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_filter, null);
        allCommunityTextView = view.findViewById(R.id.tv_filter_all_community);
        buildingInformationCb = view.findViewById(R.id.tv_filter_empty_room);
        femaleCheckBox = view.findViewById(R.id.tv_filter_female);
        maleCheckBox = view.findViewById(R.id.tv_filter_male);
        lowermostEditText = view.findViewById(R.id.et_filter_lowermost_age);
        mostEditText = view.findViewById(R.id.et_filter_most_age);
        diseaseGridView = view.findViewById(R.id.gv_community_type_of_disease);
        otherGridView = view.findViewById(R.id.gv_community_type_of_other);
        resetTextView = view.findViewById(R.id.tv_filter_reset);
        submitTextView = view.findViewById(R.id.tv_filter_submit);
        return view;
    }

    private void initListener() {
        //所有小区
        allCommunityTextView.setOnClickListener(this);
            femaleCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isCancheck){
                    return;
                }
                if (isChecked) {
                    maleCheckBox.setChecked(false);
                } else {
                    maleCheckBox.setChecked(true);
                }
            });
            maleCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isCancheck){
                    return;
                }
                if (isChecked) {
                    femaleCheckBox.setChecked(false);
                } else {
                    femaleCheckBox.setChecked(true);
                }
            });

        buildingInformationCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isCancheck=false;
            femaleCheckBox.setChecked(false);
            maleCheckBox.setChecked(false);
            isCancheck=true;
            if (isChecked) {
                allCommunityTextView.setText("全部小区");
                communityID = "0";
                isClickable(false);
                setTextColor(R.color.base_text_gray1, "1");
            } else {
                isClickable(true);
                setTextColor(R.color.black, "2");
            }
        });
        //重置
        resetTextView.setOnClickListener(this);
        //提交
        submitTextView.setOnClickListener(this);
    }

    /**
     * 是否可以选择
     *
     * @param isClickable
     */
    private void isClickable(boolean isClickable) {
        allCommunityTextView.setClickable(isClickable);
        maleCheckBox.setClickable(isClickable);
        femaleCheckBox.setClickable(isClickable);
        lowermostEditText.setEnabled(isClickable);
        mostEditText.setEnabled(isClickable);
        diseaseGridView.setEnabled(isClickable);
        otherGridView.setEnabled(isClickable);
    }

    private void setTextColor(int color, String type) {
        allCommunityTextView.setTextColor(getResources().getColor(color));
        maleCheckBox.setTextColor(getResources().getColor(color));
        femaleCheckBox.setTextColor(getResources().getColor(color));
        if ("1".equals(type)) {
            for (int i = 0; i < diseaseList.size(); i++) {
                diseaseList.get(i).setSelected(true);
            }
            diseaseTypeAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < diseaseList.size(); i++) {
                diseaseList.get(i).setSelected(false);
            }
            diseaseTypeAdapter.notifyDataSetChanged();
        }

        if ("1".equals(type)) {
            for (int i = 0; i < otherInfoList.size(); i++) {
                otherInfoList.get(i).setSelected(true);
            }
            otherTypeAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < otherInfoList.size(); i++) {
                otherInfoList.get(i).setSelected(false);
            }
            otherTypeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter_all_community:
                chooseBuilding();
                break;
            case R.id.tv_filter_reset:
                isCancheck=false;
                femaleCheckBox.setChecked(false);
                maleCheckBox.setChecked(false);
                isCancheck=true;
                allCommunityTextView.setText("全部小区");
                communityID = "0";

                buildingInformationCb.setChecked(false);
                maleCheckBox.setChecked(false);
                femaleCheckBox.setChecked(false);
                lowermostEditText.setText("");
                mostEditText.setText("");
                for (int i = 0; i < diseaseList.size(); i++) {
                    diseaseList.get(i).setCheck(false);
                    diseaseList.get(i).setCheckID("0");
                    diseaseTypeAdapter.notifyDataSetChanged();
                }

                for (int i = 0; i < otherInfoList.size(); i++) {
                    otherInfoList.get(i).setCheck(false);
                    otherInfoList.get(i).setCheckID("0");
                    otherTypeAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_filter_submit:
                CommunityFilterInfo info = new CommunityFilterInfo();
                info.setCom_id(communityID);
                info.setIsempty(buildingInformationCb.isChecked() ? "0" : "1");
                if (femaleCheckBox.isChecked()) {
                    info.setSex("1");
                } else {
                    info.setSex("2");
                }

                info.setAge_min(lowermostEditText.getText().toString().trim());
                info.setAge_max(mostEditText.getText().toString().trim());
                List<FilterSugarPressureInfo> diseaseTypeInfos = new ArrayList<>();
                for (int i = 0; i < diseaseList.size(); i++) {
                    if (diseaseList.get(i).isCheck()) {
                        diseaseTypeInfos.add(diseaseList.get(i));
                    }
                }

                info.setDiseaseTypeInfos(diseaseTypeInfos);

                List<FilterSugarPressureInfo> otherList = new ArrayList<>();

                for (int i = 0; i < otherList.size(); i++) {
                    if (otherList.get(i).isCheck()) {
                        otherList.add(otherList.get(i));
                    }
                }

                info.setOtherList(otherList);
                if ("1".equals(type)) {
                    Intent intent = new Intent(getPageContext(), CommunityFilterHaveResultListActivity.class);
                    intent.putExtra("info", new Gson().toJson(info));
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("info", new Gson().toJson(info));
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
            default:
                break;
        }
    }


    /**
     * 获取楼栋
     */
    private void chooseBuilding() {
        TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.waiting, false);
        Call<String> requestCall = DataManager.getCommunityList((call, response) -> {
            TipUtils.getInstance().dismissProgressDialog();
            if (200 == response.code) {
                List<CommunityUseMedicineInfo> logisticsCompanyInfos = (List<CommunityUseMedicineInfo>) response.object;
                List<CommunityUseMedicineInfo> infoList = new ArrayList<>();
                CommunityUseMedicineInfo info = new CommunityUseMedicineInfo();
                info.setId("0");
                info.setCom_name("全部小区");
                infoList.add(info);
                infoList.addAll(logisticsCompanyInfos);
                if (infoList != null && infoList.size() > 0) {
                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
                        communityID = infoList.get(options1).getId();
                        String s = infoList.get(options1).getCom_name();
                        allCommunityTextView.setText(s);
                    }).setLineSpacingMultiplier(2.5f)
                            .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                            .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                            .build();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < infoList.size(); i++) {
                        String typeName = infoList.get(i).getCom_name();
                        list.add(typeName);
                    }
                    Log.i("yys", "size==" + list.size());
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
}
