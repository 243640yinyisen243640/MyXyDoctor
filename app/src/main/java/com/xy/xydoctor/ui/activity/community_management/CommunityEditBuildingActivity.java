package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityEditBuildingAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.NoConflictListView;

/**
 * Author: LYD
 * Date: 2021/8/26 9:59
 * Description: 编辑楼栋
 */
public class CommunityEditBuildingActivity extends XYSoftUIBaseActivity {

    private TextView numTextView;
    private TextView highTextView;
    private TextView unitTextView;
    private TextView deleteTextView;
    private NoConflictListView listView;

    private CommunityEditBuildingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.add_building_edit_title);
        containerView().addView(initView());
        initListener();
    }

    private void initListener() {
        deleteTextView.setOnClickListener(v -> {

        });
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_building_edit, null);
        numTextView = view.findViewById(R.id.tv_add_building_num);
        highTextView = view.findViewById(R.id.tv_add_building_high);
        unitTextView = view.findViewById(R.id.tv_add_building_unit);
        listView = view.findViewById(R.id.nl_add_building);
        deleteTextView = view.findViewById(R.id.tv_add_building_delete);
        return view;
    }
}
