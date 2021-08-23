package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.HHAtMostGridView;

/**
 * Author: LYD
 * Date: 2021/8/23 16:38
 * Description: 随访代办的那个楼层图
 */
public class CommunityFollowUpBuildingActivity extends XYSoftUIBaseActivity {

    private TextView nameTextView;
    private TextView locationTextView;
    private TextView buildingNumTextView;
    private TextView unitNumTextView;
    private TextView personNumTextView;
    private TextView allPersonNumTextView;
    private TextView pressureTextView;
    private TextView sugarTextView;
    private TextView deseaseAllNumTextView;
    private RecyclerView numRecycleView;
    private RecyclerView unitRecycleView;
    private HHAtMostGridView contentGridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("buildingName");
        topViewManager().titleTextView().setText(title);
        topViewManager().moreTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.fub_top_add, 0, 0, 0);
        containerView().addView(initView());
        initValue();
    }


    private void initValue() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getPageContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        numRecycleView.setLayoutManager(linearLayoutManager);
        numRecycleView.setHasFixedSize(true);
        numRecycleView.setNestedScrollingEnabled(false);

        unitRecycleView.setLayoutManager(linearLayoutManager);
        unitRecycleView.setHasFixedSize(true);
        unitRecycleView.setNestedScrollingEnabled(false);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_followup_building, null);
        nameTextView = view.findViewById(R.id.tv_fub_building_name);
        locationTextView = view.findViewById(R.id.tv_fub_building_location);
        buildingNumTextView = view.findViewById(R.id.tv_fub_building_num);
        unitNumTextView = view.findViewById(R.id.tv_fub_building_unit_num);
        personNumTextView = view.findViewById(R.id.tv_fub_building_person_num);
        allPersonNumTextView = view.findViewById(R.id.tv_fub_building_all_person_num);
        pressureTextView = view.findViewById(R.id.tv_fub_building_high_pressure);
        sugarTextView = view.findViewById(R.id.tv_fub_building_sugar);
        deseaseAllNumTextView = view.findViewById(R.id.tv_fub_building_all_num);
        numRecycleView = view.findViewById(R.id.rv_fub_building_num);
        unitRecycleView = view.findViewById(R.id.rv_fub_building_unit);
        contentGridView = view.findViewById(R.id.gv_fub_building_content);
        return view;
    }
}
