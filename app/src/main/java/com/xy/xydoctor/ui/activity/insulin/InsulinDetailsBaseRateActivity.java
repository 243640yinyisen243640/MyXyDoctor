package com.xy.xydoctor.ui.activity.insulin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.BaseRateDetailsAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.PlanAllBaseInfo;
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
public class InsulinDetailsBaseRateActivity extends XYSoftUIBaseActivity {
    private RecyclerView LvList;
    private BaseRateDetailsAdapter adapter;
    private List<PlanInfo> listInfos = new ArrayList<>();

    private String plan_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String time = getIntent().getStringExtra("time");
        topViewManager().titleTextView().setText(time);
        plan_id = getIntent().getStringExtra("plan_id");

        initView();
        adapter = new BaseRateDetailsAdapter(getPageContext(), listInfos);
        LvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        LvList.setAdapter(adapter);
        getData();
    }

    private void getData() {
        String token = SPStaticUtils.getString("token");
        Call<String> requestCall = DataManager.getusereqplandetailBase(token, plan_id, (call, response) -> {
            if (200 == response.code) {
                PlanAllBaseInfo allBaseInfo = (PlanAllBaseInfo) response.object;

                listInfos.clear();
                listInfos.addAll(allBaseInfo.getData());
                adapter.notifyDataSetChanged();
            } else {
                TipUtils.getInstance().showToast(getPageContext(),response.msg);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(),"网络连接不可用，请稍后重试！");
        });
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_insulin_infusion_base_rate, null);
        LvList = view.findViewById(R.id.rv_base_rate_detail);

        containerView().addView(view);

    }



}
