package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.window.CommunityAddBuildingPopupWindow;

/**
 * Author: LYD
 * Date: 2021/8/26 9:59
 * Description: 添加楼栋
 */
public class CommunityAddBuildingActivity extends XYSoftUIBaseActivity {

    private EditText numEditText;
    private EditText highEditText;
    private EditText unitEditText;
    private TextView sureTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.add_building_title);
        containerView().addView(initView());
        initListener();
    }

    private void initListener() {
        sureTextView.setOnClickListener(v -> {
            CommunityAddBuildingPopupWindow addBuildingPopupWindow = new CommunityAddBuildingPopupWindow(getPageContext(), TurnsUtils.getInt(unitEditText.getText().toString(), 0));
            if (!addBuildingPopupWindow.isShowing()) {
                addBuildingPopupWindow.showAtLocation(containerView(), Gravity.CENTER, 0, 0);
            }
        });
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_building_add, null);
        numEditText = view.findViewById(R.id.et_building_num);
        highEditText = view.findViewById(R.id.et_building_high);
        unitEditText = view.findViewById(R.id.et_building_unit_num);
        sureTextView = view.findViewById(R.id.tv_add_building_submit);
        return view;
    }


    private void setUnitNum() {


    }


}
