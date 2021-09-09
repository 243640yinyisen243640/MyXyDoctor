package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ClassTopListAdapter;
import com.xy.xydoctor.adapter.community_manager.CommunityBuildingModelListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.ArrayList;

/**
 * Author: LYD
 * Date: 2021/8/23 16:38
 *
 * @paramtype 1:小区数量 2：随访待办 3:添加用户  buildingName小区名字  comid小区id
 * Description: 随访代办的那个楼层图
 */
public class CommunityFollowUpBuildingActivity extends XYSoftUIBaseActivity {

    /**
     * 添加楼栋回来刷新
     */
    private static final int REQUEST_CODE_FOR_ADD_BUILDING = 10;
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
    private NoConflictGridView contentGridView;
    /**
     * 1：小区数量 2随访待办 3添加用户
     */
    private String type;

    private String comid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        comid = getIntent().getStringExtra("comid");

        Log.i("yys", "type==" + type);
        if ("2".equals(type)) {
            topViewManager().titleTextView().setText(R.string.follow_up_agent_title);
            topViewManager().moreTextView().setText(R.string.fu_more_title_change_list);
            topViewManager().moreTextView().setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
        } else {
            String title = getIntent().getStringExtra("buildingName");
            topViewManager().titleTextView().setText(title);
            topViewManager().moreTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.fub_top_add, 0, 0, 0);
        }


        topViewManager().moreTextView().setOnClickListener(v -> {
            // 1:小区数量 2：随访待办
            if ("2".equals(type)) {
                //进入到待随访  失访  已完成的页面
                Intent intent = new Intent(getPageContext(), CommunityFollowUpActivity.class);
                startActivity(intent);

            } else {
                //添加楼栋
                Intent intent = new Intent(getPageContext(), CommunityBuildingSettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_ADD_BUILDING);
            }

        });
        containerView().addView(initView());
        initValue();

        getData();

        initListener();
    }

    private void initListener() {
    }

    private void getData() {

    }


    private void initValue() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getPageContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        numRecycleView.setLayoutManager(linearLayoutManager);
        numRecycleView.setHasFixedSize(true);
        numRecycleView.setNestedScrollingEnabled(false);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getPageContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        unitRecycleView.setLayoutManager(linearLayoutManager1);
        unitRecycleView.setHasFixedSize(true);
        unitRecycleView.setNestedScrollingEnabled(false);


        ClassTopListAdapter topListAdapter = new ClassTopListAdapter(getPageContext(), new ArrayList<>(), new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {

            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        numRecycleView.setAdapter(topListAdapter);

        CommunityBuildingModelListAdapter unitAdapter = new CommunityBuildingModelListAdapter(getPageContext(), new ArrayList<>(), "", new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {

            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        unitRecycleView.setAdapter(unitAdapter);

        contentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getPageContext(), CommunityBuildingUnitActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_ADD_BUILDING) {
                getData();
            }
        }
    }
}
