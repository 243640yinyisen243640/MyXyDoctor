package com.xy.xydoctor.ui.fragment.insulin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
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
public class InsulinLargeDoseFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {
    private TextView tvBreakFast;
    private TextView tvLunnch;
    private TextView tvDinner;
    private TextView tvMore;
    private TextView tvSure;

    public static InsulinLargeDoseFragment newInstance() {
        Bundle bundle = new Bundle();
        InsulinLargeDoseFragment fragment = new InsulinLargeDoseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        initView();
        getData();
    }

    private void getData() {

        Call<String> requestCall = DataManager.getInjectionList("", "", (call, response) -> {
            if (200 == response.code) {

            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_injection_large_dose, null);
        tvBreakFast = view.findViewById(R.id.tv_insulin_large_dose_breakfast);
        tvLunnch = view.findViewById(R.id.tv_insulin_large_dose_lunnch);
        tvDinner = view.findViewById(R.id.tv_insulin_large_dose_dinner);
        tvMore = view.findViewById(R.id.tv_insulin_large_dose_more);
        tvSure = view.findViewById(R.id.tv_insulin_large_dose_sure);
        containerView().addView(view);

    }


}
