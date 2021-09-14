package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    private CheckBox deleteCheckBox;
    private CheckBox dieCheckBox;
    private TextView sureTextView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("userName");
        userid = getIntent().getStringExtra("userid");
        topViewManager().titleTextView().setText(userName);
        containerView().addView(initView());
        initLiatener();
    }

    private void initLiatener() {
        sureTextView.setOnClickListener(v -> {
            Call<String> requestCall = DataManager.editUser(userid, deleteCheckBox.isChecked() ? "1" : "2", "", (call, response) -> {

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
                    if (dieCheckBox.isChecked()) {
                        dieCheckBox.setChecked(false);
                    } else {
                        deleteCheckBox.setChecked(true);
                    }
                }
            }
        });

        dieCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (deleteCheckBox.isChecked()) {
                        dieCheckBox.setChecked(false);
                    } else {
                        deleteCheckBox.setChecked(true);
                    }

                }
            }
        });
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_special_operate, null);
        deleteCheckBox = view.findViewById(R.id.cb_community_so_delete);
        dieCheckBox = view.findViewById(R.id.cb_community_so_die);
        sureTextView = view.findViewById(R.id.tv_community_so_submit);
        return view;
    }
}
