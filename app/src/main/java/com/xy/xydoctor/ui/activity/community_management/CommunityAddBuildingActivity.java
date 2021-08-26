package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.NoConflictGridView;

/**
 * Author: LYD
 * Date: 2021/8/26 9:44
 * Description:楼栋设置
 */
public class CommunityAddBuildingActivity extends XYSoftUIBaseActivity {

    private NoConflictGridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_add_buliding_set);
        topViewManager().moreTextView().setText(R.string.base_add);
        topViewManager().moreTextView().setOnClickListener(v -> {

        });
        containerView().addView(initView());

        initValues();
    }

    private void initValues() {
        getBuildingIno();
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_add_building, null);
        gridView = view.findViewById(R.id.gv_community_add_building);
        return view;
    }

    private void getBuildingIno() {
        //调到接口之后设置到GridView上面
    }


}
