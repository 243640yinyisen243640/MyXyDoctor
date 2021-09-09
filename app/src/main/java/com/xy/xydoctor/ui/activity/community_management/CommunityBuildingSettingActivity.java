package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityBuildingSettingAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.FollowListChildListInfo;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/26 9:44
 * Description:楼栋设置
 */
public class CommunityBuildingSettingActivity extends XYSoftUIBaseActivity {
    /**
     * 编辑楼栋，删除，回来刷新
     */
    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private NoConflictGridView gridView;

    private CommunityBuildingSettingAdapter adapter;

    /**
     * 小区id
     */
    private String comid;

    private List<FollowListChildListInfo> listChildListInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comid = getIntent().getStringExtra("comid");
        topViewManager().titleTextView().setText(R.string.community_add_buliding_set);
        topViewManager().moreTextView().setText(R.string.base_add);
        topViewManager().moreTextView().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), CommunityAddBuildingActivity.class);
            intent.putExtra("comid", comid);
            startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
        });
        containerView().addView(initView());


        initListener();

        getData();
    }

    private void getData() {
        Call<String> requestCall = DataManager.getBuildingList(comid, (call, response) -> {
            if (response.code == 200) {
                listChildListInfos = (List<FollowListChildListInfo>) response.object;
                bindData();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {
        adapter = new CommunityBuildingSettingAdapter(getPageContext(), listChildListInfos);
        gridView.setAdapter(adapter);
    }

    private void initListener() {
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getPageContext(), CommunityEditBuildingActivity.class);
            intent.putExtra("buildingid", listChildListInfos.get(position).getBuild_id());
            startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
        });
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_add_building, null);
        gridView = view.findViewById(R.id.gv_community_add_building);
        return view;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_REFRESH) {
                getData();
            }
        }
    }
}
