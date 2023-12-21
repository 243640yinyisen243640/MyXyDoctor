package com.xy.xydoctor.ui.fragment.insulin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.BaseRateDetailsAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.insulin.PlanInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 类描述：基础率
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class InsulinBaseRateAddFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {
    private TextView tvStarTime;
    private TextView tvEndTime;
    private TextView tvRate;
    private RecyclerView LvList;
    private TextView tvSure;
    private BaseRateDetailsAdapter adapter;
    private List<PlanInfo> listInfos = new ArrayList<>();

    public static InsulinBaseRateAddFragment newInstance() {
        Bundle bundle = new Bundle();
        InsulinBaseRateAddFragment fragment = new InsulinBaseRateAddFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        initView();
        adapter = new BaseRateDetailsAdapter(getPageContext(), listInfos);
        LvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        LvList.setAdapter(adapter);
        EventBusUtils.register(this);
        getData();
    }

    private void getData() {

        Call<String> requestCall = DataManager.getInjectionList("", "", (call, response) -> {
            if (200 == response.code) {
                listInfos.clear();
                listInfos.addAll((List<PlanInfo>) response.object);
                adapter.notifyDataSetChanged();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_insulin_infusion_base_rate_add, null);
        tvStarTime = view.findViewById(R.id.tv_infusion_base_rate_star_time);
        tvEndTime = view.findViewById(R.id.tv_infusion_base_rate_end_time);
        tvRate = view.findViewById(R.id.tv_infusion_base_rate_rate);
        LvList = view.findViewById(R.id.rv_base_rate_detail);
        tvSure = view.findViewById(R.id.tv_insulin_base_rate_sure);
        containerView().addView(view);

    }


}
