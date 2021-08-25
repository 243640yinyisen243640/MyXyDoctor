package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.DiseaseTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/25 17:44
 * Description: 未完成
 */
public class CommunityNoFinishActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private TextView reasonTextView;
    private TextView closeTextView;
    private TextView submitTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.goods_add_spe_no_finish);
        containerView().addView(initView());
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_no_finish, null);
        reasonTextView = findViewById(R.id.tv_no_finish_choose_reason);
        closeTextView = findViewById(R.id.tv_no_finish_choose_close);
        submitTextView = findViewById(R.id.tv_no_finish_submit);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_no_finish_choose_reason:
                List<DiseaseTypeInfo> reasonList = new ArrayList<>();
                DiseaseTypeInfo sugar1 = new DiseaseTypeInfo("电话打不通");
                reasonList.add(sugar1);
                DiseaseTypeInfo sugar2 = new DiseaseTypeInfo("患者拒绝随访");
                reasonList.add(sugar2);
                DiseaseTypeInfo sugar3 = new DiseaseTypeInfo("拒接");
                reasonList.add(sugar3);
                DiseaseTypeInfo sugar4 = new DiseaseTypeInfo("患者个人检验数据不清楚");
                reasonList.add(sugar4);
                DiseaseTypeInfo sugar5 = new DiseaseTypeInfo("其他");
                reasonList.add(sugar5);
                showSugarOrPressureType(reasonList);
                break;
            case R.id.tv_no_finish_choose_close:
                if (closeTextView.isSelected()) {
                    closeTextView.setSelected(false);
                } else {
                    closeTextView.setSelected(true);
                }
                break;
            case R.id.tv_no_finish_submit:
                break;
            default:
                break;
        }
    }

    /**
     * 选择糖尿病或者高血压的类型
     */
    private void showSugarOrPressureType(List<DiseaseTypeInfo> diseaseTypeInfos) {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
            String s = diseaseTypeInfos.get(options1).getDiseaseName();
            reasonTextView.setText(s);
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
}
