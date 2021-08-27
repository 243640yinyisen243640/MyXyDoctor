package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityBuildingUnitListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;

/**
 * 类描述：家庭人员详情
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class CommunityBuildingUnitActivity extends XYSoftUIBaseActivity implements TabFragmentAdapter.RefeshFragment {

    private CommunityBuildingUnitListAdapter mAdapter;

    private TextView nameTextView;
    private TextView locationTextView;
    private ImageView partyMemberImageView;
    private ImageView handicappedImageView;
    private ImageView mentalIllnessImageView;
    private TextView followTextView;
    private TextView sexTextView;
    private TextView ageTextView;
    private TextView phoneTextView;
    private TextView sugarTextView;
    private TextView pressureTextView;
    private TextView sugarFollowTextView;
    private TextView pressureFollowTextView;
    private TextView waitFollowTextView;
    private TextView timeSugarTextView;
    private TextView unitTextView;
    private TextView timePressureTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("");
        initView();
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_building_unit, null);
        nameTextView = view.findViewById(R.id.tv_building_unit_name);
        locationTextView = view.findViewById(R.id.tv_building_unit_location);
        partyMemberImageView = view.findViewById(R.id.iv_building_unit_party_member);
        handicappedImageView = view.findViewById(R.id.iv_building_unit_handicapped);
        mentalIllnessImageView = view.findViewById(R.id.iv_building_unit_mental_illness);
        followTextView = view.findViewById(R.id.tv_building_unit_follow);
        sexTextView = view.findViewById(R.id.tv_building_unit_sex);
        ageTextView = view.findViewById(R.id.tv_building_unit_age);
        phoneTextView = view.findViewById(R.id.tv_building_unit_phone);
        sugarTextView = view.findViewById(R.id.tv_building_unit_sugar);
        pressureTextView = view.findViewById(R.id.tv_building_unit_pressure);
        sugarFollowTextView = view.findViewById(R.id.tv_building_unit_sugar_follow);
        pressureFollowTextView = view.findViewById(R.id.tv_building_unit_pressure_follow);
        waitFollowTextView = view.findViewById(R.id.tv_building_unit_wait_follow);
        timeSugarTextView = view.findViewById(R.id.tv_building_unit_time_and_sugar);
        unitTextView = view.findViewById(R.id.tv_building_unit_unit);
        timePressureTextView = view.findViewById(R.id.tv_building_unit_time_and_pressure);
        containerView().addView(view);

    }


}
