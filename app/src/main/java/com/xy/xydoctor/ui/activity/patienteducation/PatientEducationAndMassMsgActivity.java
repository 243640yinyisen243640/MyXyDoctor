package com.xy.xydoctor.ui.activity.patienteducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPStaticUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.ui.activity.director_massmsg.MassMsgDirectorActivity;
import com.xy.xydoctor.ui.activity.director_patienteducation.PatientEductionDirectorActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgMainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 患教和群发 选择页面
 * 作者: LYD
 * 创建日期: 2020/7/22 14:05
 */
public class PatientEducationAndMassMsgActivity extends BaseActivity {

    @BindView(R.id.rl_one)
    RelativeLayout rlOne;
    @BindView(R.id.rl_two)
    RelativeLayout rlTwo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_education_and_mass_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("群发消息");
    }


    @OnClick({R.id.rl_one, R.id.rl_two})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_one:
                toJudgeToPatientEducation();
                break;
            case R.id.rl_two:
                toJudgeToMassMsg();
                break;
        }
    }

    private void toJudgeToMassMsg() {
        int type = SPStaticUtils.getInt("docType");
        if (3 == type) {
            startActivity(new Intent(getPageContext(), MassMsgDirectorActivity.class));
        } else {
            Intent intent = new Intent(getPageContext(), MassMsgMainActivity.class);
            intent.putExtra("type", "0");
            startActivity(intent);
        }
    }

    private void toJudgeToPatientEducation() {
        int type = SPStaticUtils.getInt("docType");
        if (3 == type) {
            startActivity(new Intent(getPageContext(), PatientEductionDirectorActivity.class));
        } else {
            Intent intent = new Intent(getPageContext(), PatientEducationMainActivity.class);
            startActivity(intent);
        }
    }
}