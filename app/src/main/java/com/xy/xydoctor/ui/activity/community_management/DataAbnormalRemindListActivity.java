package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalRemindAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述:异常提醒
 * * 作者: LYD
 *
 * @paramtype 1:血糖 2血压
 * 创建日期: 2020/5/26 11:07
 */
public class DataAbnormalRemindListActivity extends XYSoftUIBaseActivity {

    private RecyclerView rvRecyclerView;

    private DataAbnormalRemindAdapter adapter;

    /**
     * 1：血压  2：血糖
     */
    private String type = "1";

    private List<SugarOrPressureInfo> sugarOrPressureInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("数据异常");
        type = getIntent().getStringExtra("type");
        sugarOrPressureInfoList = (List<SugarOrPressureInfo>) getIntent().getSerializableExtra("sugarOrPressureList");
        containerView().addView(initView());
        initValues();
    }

    private void initValues() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvRecyclerView.setLayoutManager(layoutManager);
        adapter = new DataAbnormalRemindAdapter(getPageContext(), sugarOrPressureInfoList, type, new OnItemClickListener());
        rvRecyclerView.setAdapter(adapter);
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_data_abnormal_remind_list, null);
        rvRecyclerView = view.findViewById(R.id.rv_data_abnormal_remind_list);
        return view;
    }

    private class OnItemClickListener implements IAdapterViewClickListener {

        @Override
        public void adapterClickListener(int position, View view) {

        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
        }
    }


}
