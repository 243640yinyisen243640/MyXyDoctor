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
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/25 17:44
 * Description: 未完成
 */
public class CommunityNoFinishActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private TextView reasonTextView;
    private TextView closeTextView;
    private TextView submitTextView;

    private String checkID = "0";

    /**
     * 1:待随访  2：失访
     */
    private String type;
    private String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        userid = getIntent().getStringExtra("userid");
        topViewManager().titleTextView().setText(R.string.goods_add_spe_no_finish);
        containerView().addView(initView());
        initLiseter();
    }

    private void initLiseter() {
        reasonTextView.setOnClickListener(this);
        closeTextView.setOnClickListener(this);
        submitTextView.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_no_finish, null);
        reasonTextView = view.findViewById(R.id.tv_no_finish_choose_reason);
        closeTextView = view.findViewById(R.id.tv_no_finish_choose_close);
        submitTextView = view.findViewById(R.id.tv_no_finish_submit);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_no_finish_choose_reason:
                List<FilterSugarPressureInfo> reasonList = new ArrayList<>();
                FilterSugarPressureInfo sugar1 = new FilterSugarPressureInfo("电话打不通", "1");
                reasonList.add(sugar1);
                FilterSugarPressureInfo sugar2 = new FilterSugarPressureInfo("患者拒绝随访", "2");
                reasonList.add(sugar2);
                FilterSugarPressureInfo sugar3 = new FilterSugarPressureInfo("拒接", "3");
                reasonList.add(sugar3);
                FilterSugarPressureInfo sugar4 = new FilterSugarPressureInfo("患者个人检验数据不清楚", "4");
                reasonList.add(sugar4);
                FilterSugarPressureInfo sugar5 = new FilterSugarPressureInfo("其他", "5");
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
                if ("0".equals(checkID)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.plwase_choose_reason);
                    return;
                }
                upLoadData();
                break;
            default:
                break;
        }
    }

    private void upLoadData() {
        Call<String> requestCall = DataManager.followEdit(type, userid, closeTextView.isSelected() ? "1" : "0", checkID, "", (call, response) -> {
            if (response.code == 200) {
                setResult(RESULT_OK);
                finish();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    /**
     * 选择糖尿病或者高血压的类型
     */
    private void showSugarOrPressureType(List<FilterSugarPressureInfo> diseaseTypeInfos) {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getPageContext(), (options1, options2, options3, v) -> {
            String s = diseaseTypeInfos.get(options1).getDiseaseName();
            reasonTextView.setText(s);
            checkID = diseaseTypeInfos.get(options1).getCheckID();
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
