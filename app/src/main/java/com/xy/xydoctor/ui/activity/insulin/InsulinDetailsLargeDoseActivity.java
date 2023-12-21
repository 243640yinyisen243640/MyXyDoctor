package com.xy.xydoctor.ui.activity.insulin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPStaticUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.insulin.PlanAllInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import retrofit2.Call;

/**
 * 类描述：大剂量
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class InsulinDetailsLargeDoseActivity extends XYSoftUIBaseActivity {
    private TextView tvBreakFast;
    private TextView tvLunnch;
    private TextView tvDinner;
    private TextView tvMore;

    private String plan_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plan_id = getIntent().getStringExtra("plan_id");
        String time = getIntent().getStringExtra("time");
        topViewManager().titleTextView().setText(time);
        initView();
        getData();
    }

    private void getData() {
        String token = SPStaticUtils.getString("token");
        String userid = getIntent().getStringExtra("userid");
        Call<String> requestCall = DataManager.getusereqplandetail(token, plan_id, userid, (call, response) -> {
            if (200 == response.code) {
                PlanAllInfo allInfo = (PlanAllInfo) response.object;

                tvBreakFast.setText(allInfo.getData().getBig1());
                tvLunnch.setText(allInfo.getData().getBig2());
                tvDinner.setText(allInfo.getData().getBig3());
                tvMore.setText(allInfo.getData().getBig4());
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), "网络连接不可用，请稍后重试！");
        });
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_injection_large_dose, null);
        tvBreakFast = view.findViewById(R.id.tv_insulin_large_dose_breakfast);
        tvLunnch = view.findViewById(R.id.tv_insulin_large_dose_lunnch);
        tvDinner = view.findViewById(R.id.tv_insulin_large_dose_dinner);
        tvMore = view.findViewById(R.id.tv_insulin_large_dose_more);
        containerView().addView(view);

    }

}
