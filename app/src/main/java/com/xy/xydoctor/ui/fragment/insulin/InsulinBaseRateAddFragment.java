package com.xy.xydoctor.ui.fragment.insulin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.BaseRateDetailsAddAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.insulin.PlanAddInfo;
import com.xy.xydoctor.ui.activity.insulin.InsulinPlanSignActivity;

import java.util.ArrayList;
import java.util.List;

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
    private BaseRateDetailsAddAdapter adapter;
    private List<PlanAddInfo> listInfos = new ArrayList<>();
    private String userid;
    /**
     * 1：大剂量  2：基础率
     */
    private String type;

    public static InsulinBaseRateAddFragment newInstance(String userid, String type) {
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);
        bundle.putString("type", type);
        InsulinBaseRateAddFragment fragment = new InsulinBaseRateAddFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        type = getArguments().getString("type");
        userid = getArguments().getString("userid");
        initView();
        for (int i = 0; i < 24; i++) {
            String start;
            String end;
            if (i < 10) {
                start = "0" + i + ":" + "00";
            } else {
                start = i + ":" + "00";
            }
            if (i < 9) {
                end = "0" + (i + 1) + ":" + "00";
            } else {
                end = (i + 1) + ":" + "00";
            }

            PlanAddInfo info = new PlanAddInfo(start, end);
            listInfos.add(info);
        }
        adapter = new BaseRateDetailsAddAdapter(getPageContext(), listInfos);
        LvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        LvList.setAdapter(adapter);
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_insulin_infusion_base_rate_add, null);
        tvStarTime = view.findViewById(R.id.tv_infusion_base_rate_star_time);
        tvEndTime = view.findViewById(R.id.tv_infusion_base_rate_end_time);
        tvRate = view.findViewById(R.id.tv_infusion_base_rate_rate);
        LvList = view.findViewById(R.id.rv_base_rate_detail);
        tvSure = view.findViewById(R.id.tv_insulin_base_rate_sure);
        containerView().addView(view);
        tvSure.setOnClickListener(v -> {

            boolean isSuccess = true;
            for (int i = 0; i < listInfos.size(); i++) {
                if (Double.parseDouble(listInfos.get(i).getValue()) < 0.1) {
                    ToastUtils.showShortToast(getPageContext(), "请输入正确的数据");
                    isSuccess = false;
                    return;
                }
            }
            if (isSuccess) {
                String data = new Gson().toJson(listInfos);
                Intent intent = new Intent(getPageContext(), InsulinPlanSignActivity.class);
                intent.putExtra("data", data);
                intent.putExtra("type", "2");
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
    }


}
