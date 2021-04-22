package com.xy.xydoctor.ui.activity.chat;

import android.os.Bundle;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * 描述:医生建议
 * 作者: LYD
 * 创建日期: 2019/6/12 9:46
 */
public class DoctorAdviceActivity extends BaseActivity {
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_advice)
    TextView tvAdvice;
    @BindView(R.id.tv_text_unit)
    TextView tvTextUnit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_advice;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("医生建议");
        String advice = getIntent().getStringExtra("advice");
        String type = getIntent().getStringExtra("type");
        String typeName = getIntent().getStringExtra("typeName");
        String val = getIntent().getStringExtra("val");
        tvAdvice.setText(advice);
        tvText.setText(val);
        //1 血压 2 血糖
        if ("1".equals(type)) {
            tvDesc.setText(typeName + "血压值");
            tvTextUnit.setText("mmHg");
        } else {
            tvDesc.setText(typeName + "血糖值");
            tvTextUnit.setText("mmol/L");
        }

    }
}
