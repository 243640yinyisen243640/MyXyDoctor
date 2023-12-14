package com.xy.xydoctor.ui.fragment.insulin;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
public class InsulinLargeDoseAddFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {
    private EditText etBreakFast;
    private EditText etLunnch;
    private EditText etDinner;
    private EditText etMore;
    private TextView tvSure;

    public static InsulinLargeDoseAddFragment newInstance() {
        Bundle bundle = new Bundle();
        InsulinLargeDoseAddFragment fragment = new InsulinLargeDoseAddFragment();
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
        View view = View.inflate(getPageContext(), R.layout.fragment_insulin_plan_large_dose_add, null);
        etBreakFast = view.findViewById(R.id.et_plan_add_breakfast);
        etLunnch = view.findViewById(R.id.et_plan_add_lunch);
        etDinner = view.findViewById(R.id.et_plan_add_dinner);
        etMore = view.findViewById(R.id.et_plan_add_more);
        tvSure = view.findViewById(R.id.tv_plan_add_sure);
        containerView().addView(view);

    }


}
