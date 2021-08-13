package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.FilterDiseaseTypeAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.DiseaseTypeInfo;
import com.xy.xydoctor.customerView.NoConflictGridView;

import java.util.ArrayList;
import java.util.List;

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
    private EditText lowermostCheckBox;
    private EditText mostCheckBox;
    private NoConflictGridView diseaseGridView;
    private NoConflictGridView otherGridView;
    private TextView resetTextView;
    private TextView submitTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.base_filter);
        containerView().addView(initView());
        initListener();
        initValues();
    }

    private void initValues() {
        List<DiseaseTypeInfo> typeInfoList = new ArrayList<>();
        DiseaseTypeInfo typeInfo1 = new DiseaseTypeInfo("糖尿病");
        typeInfoList.add(typeInfo1);
        DiseaseTypeInfo typeInfo2 = new DiseaseTypeInfo("高血压");
        typeInfoList.add(typeInfo2);
        DiseaseTypeInfo typeInfo3 = new DiseaseTypeInfo("超重/肥胖");
        typeInfoList.add(typeInfo3);
        DiseaseTypeInfo typeInfo4 = new DiseaseTypeInfo("超重/肥胖");
        typeInfoList.add(typeInfo4);
        DiseaseTypeInfo typeInfo5 = new DiseaseTypeInfo("脑卒中");
        typeInfoList.add(typeInfo5);
        DiseaseTypeInfo typeInfo6 = new DiseaseTypeInfo("脂肪肝");
        typeInfoList.add(typeInfo6);

        FilterDiseaseTypeAdapter diseaseTypeAdapter = new FilterDiseaseTypeAdapter(getPageContext(), typeInfoList);
        diseaseGridView.setAdapter(diseaseTypeAdapter);


        List<DiseaseTypeInfo> otherInfoList = new ArrayList<>();
        DiseaseTypeInfo otherInfoList1 = new DiseaseTypeInfo("残疾人");
        otherInfoList.add(otherInfoList1);
        DiseaseTypeInfo otherInfoList2 = new DiseaseTypeInfo("精神问题");
        otherInfoList.add(otherInfoList2);
        DiseaseTypeInfo otherInfoList3 = new DiseaseTypeInfo("党员");
        otherInfoList.add(otherInfoList3);
        DiseaseTypeInfo otherInfoList4 = new DiseaseTypeInfo("重点关注");
        otherInfoList.add(otherInfoList4);
        DiseaseTypeInfo otherInfoList5 = new DiseaseTypeInfo("死亡");
        otherInfoList.add(otherInfoList5);
        diseaseGridView.setOnItemClickListener((parent, view, position, id) -> {
            Log.i("yys", "diseaseGridView");
            typeInfoList.get(position).setCheck(!typeInfoList.get(position).isCheck());
            diseaseTypeAdapter.notifyDataSetChanged();

        });
        FilterDiseaseTypeAdapter otherTypeAdapter = new FilterDiseaseTypeAdapter(getPageContext(), otherInfoList);
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
        lowermostCheckBox = view.findViewById(R.id.et_filter_lowermost_age);
        mostCheckBox = view.findViewById(R.id.et_filter_most_age);
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
            if (isChecked) {
                maleCheckBox.setChecked(false);
            } else {
                maleCheckBox.setChecked(true);
            }
        });
        maleCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                femaleCheckBox.setChecked(false);
            } else {
                femaleCheckBox.setChecked(true);
            }
        });

        //重置
        resetTextView.setOnClickListener(this);
        //提交
        submitTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter_all_community:
//                chooseBuilding();
                break;
            case R.id.tv_filter_reset:
                break;
            case R.id.tv_filter_submit:
                break;
            default:
                break;
        }
    }

    /**
     * 物流
     */
//    private void chooseBuilding() {
//        TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.waiting, false);
//        Call<String> requestCall = GoodsDataManager.getDeliverCompany((call, response) -> {
//            TipUtils.getInstance().dismissProgressDialog();
//            if (100 == response.code) {
//                List<LogisticsCompanyInfo> logisticsCompanyInfos = (List<LogisticsCompanyInfo>) response.object;
//                if (logisticsCompanyInfos != null && logisticsCompanyInfos.size() > 0) {
//                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
//                        logisticsCompanyID = logisticsCompanyInfos.get(options1).getLogisticsCompanyID();
//                        String s = logisticsCompanyInfos.get(options1).getLogisticsCompany();
//                        companyTextView.setText(s);
//                        companyTextView.setTextSize(16);
//                        companyTextView.setTextColor(context.getResources().getColor(R.color.black));
//                    }).setLineSpacingMultiplier(2.5f)
//                            .setCancelColor(ContextCompat.getColor(context, R.color.text_gray))
//                            .setSubmitColor(ContextCompat.getColor(context, R.color.main_base_color))
//                            .build();
//                    List<String> list = new ArrayList<>();
//                    for (int i = 0; i < logisticsCompanyInfos.size(); i++) {
//                        String typeName = logisticsCompanyInfos.get(i).getLogisticsCompany();
//                        list.add(typeName);
//                    }
//                    optionsPickerView.setPicker(list);
//                    optionsPickerView.show();
//                }
//            } else {
//                TipUtils.getInstance().showToast(context, response.msg);
//            }
//        }, (call, throwable) -> {
//        });
//
//    }
}
