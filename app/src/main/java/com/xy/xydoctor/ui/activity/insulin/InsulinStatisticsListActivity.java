package com.xy.xydoctor.ui.activity.insulin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.InsulinStatisticsAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.InsulinAllInfo;
import com.xy.xydoctor.bean.insulin.InsulinDeviceInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:当前佩戴人数
 */
public class InsulinStatisticsListActivity extends XYSoftUIBaseActivity {
    private TextView tvNoData;
    private RecyclerView rvStatistics;
    private SmartRefreshLayout smartRefreshLayout;
    private InsulinStatisticsAdapter adapter;
    private List<InsulinDeviceInfo> infoList = new ArrayList<>();
    private List<InsulinDeviceInfo> infoListTemp = new ArrayList<>();
    private int page = 1;

    private InsulinAllInfo allInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("当前佩戴人数");
        containerView().addView(initView());
        initReFresh();
        getData();
    }

    private void getData() {
        Call<String> requestCall = DataManager.usereqstastic(page, (call, response) -> {
            TipUtils.getInstance().showToast(getPageContext(), response.msg);
            if (200 == response.code) {
                allInfo = (InsulinAllInfo) response.object;
                if (allInfo.getList() != null && allInfo.getList().size() > 0) {
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    if (page == 1) {
                        infoList.clear();
                    }

                    infoListTemp = allInfo.getList();
                    if (infoListTemp.size() < 10) {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        smartRefreshLayout.finishLoadMore();
                    }
                    infoList.addAll(infoListTemp);
                    adapter.notifyDataSetChanged();
                } else {
                    smartRefreshLayout.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }

            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_statistics_list, null);
        smartRefreshLayout = view.findViewById(R.id.srl_insulin_statistics_list_only);
        tvNoData = view.findViewById(R.id.tv_insulin_statistics_list_only_no);
        rvStatistics = view.findViewById(R.id.rv_insulin_statistics_list_only);
        adapter = new InsulinStatisticsAdapter(getPageContext(), infoList, new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {
                switch (view.getId()) {
                    case R.id.ll_insulin_statistics_click:
                        startActivity(new Intent(getPageContext(), InsulinInfusionRecordListActivity.class));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        rvStatistics.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvStatistics.setAdapter(adapter);
        return view;
    }

    private void initReFresh() {
        //下拉刷新
        smartRefreshLayout.setEnableRefresh(false);
        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData();
        });
    }
}
