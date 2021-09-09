package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityEditBuildingAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.FollowListInfo;
import com.xy.xydoctor.customerView.NoConflictListView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import retrofit2.Call;

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

    private String buildingID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildingID = getIntent().getStringExtra("buildingid");
        topViewManager().titleTextView().setText(R.string.add_building_edit_title);
        containerView().addView(initView());
        initListener();
        getBuildingInfo();
    }

    private void initListener() {
        deleteTextView.setOnClickListener(v -> {
            deleteBuilding();

        });
    }

    private void deleteBuilding() {
        Call<String> requestCall = DataManager.deleteBuilding(buildingID, (call, response) -> {
            if (response.code == 200) {
                setResult(RESULT_OK);
                finish();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void getBuildingInfo() {
        TipUtils.getInstance().showProgressDialog(getPageContext(), R.string.waiting);
        Call<String> requestCall = DataManager.getBuildingInfo(buildingID, (call, response) -> {
            TipUtils.getInstance().dismissProgressDialog();
            if (response.code == 200) {
                FollowListInfo info = (FollowListInfo) response.object;
                numTextView.setText(info.getBuild_name());
                highTextView.setText(info.getLayer());
                unitTextView.setText(info.getUnit_name());
                adapter = new CommunityEditBuildingAdapter(getPageContext(), info.getBuild_data());
                listView.setAdapter(adapter);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
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
