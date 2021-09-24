package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/9/2 9:07
 * Description:
 * 传参  username  userid
 */
public class SpecialOperateActivity extends XYSoftUIBaseActivity {
    private String userName;
    private String userid;

    private int isDead;

    private CheckBox deleteCheckBox;
    private CheckBox dieCheckBox;
    private EditText reasonEditText;
    private TextView sureTextView;
    private TextView tipTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("userid");
        isDead = getIntent().getIntExtra("isDead", 0);
        Log.i("yys","isDead"+isDead);
        Log.i("yys", "name==" + userName);
        topViewManager().titleTextView().setText(userName);
        containerView().addView(initView());
        initLiatener();
    }

    private void initLiatener() {
        sureTextView.setOnClickListener(v -> {
            String type = deleteCheckBox.isChecked() ? "1" : "2";
            String reason = reasonEditText.getText().toString().trim();
            if ("1".equals(type)) {
                reason = "";
            } else {
                if (TextUtils.isEmpty(reason)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_input_die_reason);
                    return;
                }
            }
            Call<String> requestCall = DataManager.editUser(userid, type, reason, (call, response) -> {

                if (response.code == 200) {
                    setResult(RESULT_OK);
                    finish();
                }
            }, (call, t) -> {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            });
        });

        deleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dieCheckBox.setChecked(false);
                    reasonEditText.setVisibility(View.GONE);
                    tipTextView.setVisibility(View.GONE);
                } else {
                    dieCheckBox.setChecked(true);
                    reasonEditText.setVisibility(View.VISIBLE);
                    tipTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        dieCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reasonEditText.setVisibility(View.VISIBLE);
                    tipTextView.setVisibility(View.VISIBLE);
                    deleteCheckBox.setChecked(false);
                } else {
                    reasonEditText.setVisibility(View.GONE);
                    tipTextView.setVisibility(View.GONE);
                    deleteCheckBox.setChecked(true);
                }
            }
        });
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_special_operate, null);
        deleteCheckBox = view.findViewById(R.id.cb_community_so_delete);
        dieCheckBox = view.findViewById(R.id.cb_community_so_die);
        reasonEditText = view.findViewById(R.id.tv_et_community_so_reason);
        tipTextView = view.findViewById(R.id.tv_community_so_reason_tip);
        sureTextView = view.findViewById(R.id.tv_community_so_submit);
        if (isDead == 1) {
            dieCheckBox.setVisibility(View.GONE);
        } else {
            dieCheckBox.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
