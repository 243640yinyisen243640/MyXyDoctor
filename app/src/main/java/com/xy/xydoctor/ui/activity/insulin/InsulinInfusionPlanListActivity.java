package com.xy.xydoctor.ui.activity.insulin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.InsulinInfusionPlanAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.PlanInfo;
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
 * 描述:输注方案
 */
public class InsulinInfusionPlanListActivity extends XYSoftUIBaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private InsulinInfusionPlanAdapter adapter;
    private List<PlanInfo> infoList = new ArrayList<>();
    private List<PlanInfo> infoListTemp = new ArrayList<>();
    private int page = 1;

    private LinearLayout llLargeDose;
    private TextView tvLargeDose;
    private LinearLayout llBasalRate;
    private TextView tvBasalRate;
    private LinearLayout llLast;
    private TextView tvNoData;
    /**
     * 1大剂量 2基础率
     */
    private String type = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("输注方案");
        containerView().addView(initView());
        initListner();
        initReFresh();
        getData();
    }




    private void initReFresh() {
        //下拉刷新
        smartRefreshLayout.setEnableRefresh(false);
        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
    }

    private void initListner() {
        llLargeDose.setOnClickListener(this);
        llBasalRate.setOnClickListener(this);
    }


    private void getData() {
        String token = SPStaticUtils.getString("token");
        Call<String> requestCall = DataManager.getusereqplan(token, type, page + "", (call, response) -> {
            if (200 == response.code) {
                if (page == 1) {
                    infoList.clear();
                }
                infoListTemp = (List<PlanInfo>) response.object;
                if (infoListTemp != null && infoListTemp.size() > 0) {
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    if (infoListTemp.size() < 10) {
                        llLast.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        smartRefreshLayout.finishLoadMore();
                    }
                    infoList.addAll(infoListTemp);
                    adapter.notifyDataSetChanged();
                } else {
                    smartRefreshLayout.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                    llLast.setVisibility(View.GONE);
                }

            } else if (30002 == response.code) {
                smartRefreshLayout.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                llLast.setVisibility(View.GONE);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(),"网络连接不可用，请稍后重试！");
        });
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_infusion_plan, null);
        llLargeDose = view.findViewById(R.id.ll_infusion_large_dose);
        llBasalRate = view.findViewById(R.id.ll_infusion_basal_rate);

        tvLargeDose = view.findViewById(R.id.tv_infusion_large_dose);
        tvBasalRate = view.findViewById(R.id.tv_infusion_basal_rate);

        smartRefreshLayout = view.findViewById(R.id.srl_infusion_plan);
        recyclerView = view.findViewById(R.id.rv_infusion_plan);
        llLast = view.findViewById(R.id.ll_insulin_infusion_plan_last);
        tvNoData = view.findViewById(R.id.tv_insulin_plan_no_data);
        adapter = new InsulinInfusionPlanAdapter(getPageContext(), infoList, type, new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view1) {
                switch (view1.getId()) {
                    case R.id.ll_insulin_infusion_plan_click:
                        //1大剂量 2基础率
                        if ("1".equals(type)) {
                            Intent intent = new Intent(getPageContext(), InsulinDetailsLargeDoseActivity.class);
                            intent.putExtra("time", infoList.get(position).getAddtime());
                            intent.putExtra("plan_id", infoList.get(position).getPlan_id());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getPageContext(), InsulinDetailsBaseRateActivity.class);
                            intent.putExtra("time", infoList.get(position).getAddtime());
                            intent.putExtra("plan_id", infoList.get(position).getPlan_id());
                            startActivity(intent);
                        }

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getPageContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_infusion_large_dose:
                type = "1";
                getData();
                setBg(tvLargeDose, tvBasalRate);
                break;
            case R.id.ll_infusion_basal_rate:
                type = "2";
                getData();
                setBg(tvBasalRate, tvLargeDose);
                break;
            default:
                break;
        }
    }


    private void setBg(TextView tvCheck, TextView tvUncheck1) {
        tvCheck.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.shape_red_30_3_2);
        tvCheck.setTextSize(18);
        tvCheck.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvUncheck1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvUncheck1.setTextSize(16);
        tvUncheck1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }


}
