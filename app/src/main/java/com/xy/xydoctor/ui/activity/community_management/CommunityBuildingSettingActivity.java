package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityBuildingSettingAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.NoConflictGridView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText(R.string.community_add_buliding_set);
        topViewManager().moreTextView().setText(R.string.base_add);
        topViewManager().moreTextView().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), CommunityAddBuildingActivity.class);
            startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
        });
        containerView().addView(initView());

        initValues();

        initListener();
    }

    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getPageContext(), CommunityEditBuildingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_REFRESH) {
                initValues();
            }
        }
    }
}
