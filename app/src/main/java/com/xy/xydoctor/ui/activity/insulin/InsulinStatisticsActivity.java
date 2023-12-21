package com.xy.xydoctor.ui.activity.insulin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.InsulinStatisticsAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.InsulinAllInfo;
import com.xy.xydoctor.bean.insulin.InsulinPersonInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.StatusBarUtils;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:胰岛素泵统计
 */
public class InsulinStatisticsActivity extends XYSoftUIBaseActivity {
    private ImageView ivBack;
    private TextView tvNum;
    private TextView tvPlanNum;
    private TextView tvNoData;
    private RecyclerView rvStatistics;
    private SmartRefreshLayout smartRefreshLayout;
    private InsulinStatisticsAdapter adapter;
    private List<InsulinPersonInfo> infoList = new ArrayList<>();
    private List<InsulinPersonInfo> infoListTemp = new ArrayList<>();
    private int page = 1;

    private InsulinAllInfo allInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().topView().removeAllViews();
        StatusBarUtils.statusBarColor(this, ContextCompat.getColor(getPageContext(), R.color.main_red));
        containerView().addView(initView());
        initReFresh();
        getData();
    }

    private void getData() {
        Call<String> requestCall = DataManager.usereqstastic(page, "1", (call, response) -> {
            TipUtils.getInstance().showToast(getPageContext(), response.msg);
            if (200 == response.code) {
                allInfo = (InsulinAllInfo) response.object;
                tvNum.setText(allInfo.getTotal());
                tvPlanNum.setText(allInfo.getPlan_num());
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
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_statistics, null);
        ivBack = view.findViewById(R.id.iv_insulin_statistics_back);
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvNum = view.findViewById(R.id.tv_insulin_statistics_with_num);
        tvPlanNum = view.findViewById(R.id.tv_insulin_statistics_plan_num);
        rvStatistics = view.findViewById(R.id.rv_insulin_statistics_list);
        smartRefreshLayout = view.findViewById(R.id.srl_insulin_statistics);
        tvNoData = view.findViewById(R.id._insulin_statistics_list_no);
        tvNum.setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), InsulinStatisticsListActivity.class);
            startActivity(intent);
        });
        tvPlanNum.setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), InsulinInfusionCurrentListActivity.class);
            startActivity(intent);
        });
        adapter = new InsulinStatisticsAdapter(getPageContext(), infoList, new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {
                switch (view.getId()) {
                    case R.id.ll_insulin_statistics_click:
                        Intent intent = new Intent(getPageContext(), InsulinInfusionRecordListActivity.class);
                        intent.putExtra("userid", infoList.get(position).getUserid());
                        startActivity(intent);
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
