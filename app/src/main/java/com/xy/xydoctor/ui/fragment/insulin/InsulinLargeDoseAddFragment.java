package com.xy.xydoctor.ui.fragment.insulin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.ui.activity.insulin.InsulinPlanSignActivity;
import com.xy.xydoctor.utils.TipUtils;

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

    private String userid;
    private String type;

    public static InsulinLargeDoseAddFragment newInstance(String userid, String type) {
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);
        bundle.putString("type", type);
        InsulinLargeDoseAddFragment fragment = new InsulinLargeDoseAddFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        type = getArguments().getString("type");
        userid = getArguments().getString("userid");
        initView();
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.fragment_insulin_plan_large_dose_add, null);
        etBreakFast = view.findViewById(R.id.et_plan_add_breakfast);
        etLunnch = view.findViewById(R.id.et_plan_add_lunch);
        etDinner = view.findViewById(R.id.et_plan_add_dinner);
        etMore = view.findViewById(R.id.et_plan_add_more);
        tvSure = view.findViewById(R.id.tv_plan_add_sure);
        tvSure.setOnClickListener(v -> {

            if (TextUtils.isEmpty(etBreakFast.getText().toString().trim()) || TextUtils.isEmpty(etLunnch.getText().toString().trim())
                    || TextUtils.isEmpty(etDinner.getText().toString().trim()) || TextUtils.isEmpty(etMore.getText().toString().trim())) {
                TipUtils.getInstance().showToast(getPageContext(), "请输入合法的数据");
                return;
            }
            Log.i("yys", "data===" + getSendData());
            Intent intent = new Intent(getPageContext(), InsulinPlanSignActivity.class);
            intent.putExtra("data", getSendData());
            intent.putExtra("type", "1");
            intent.putExtra("userid", userid);
            startActivity(intent);
        });
        containerView().addView(view);
    }


    private String getSendData() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("big1\":").append("\"").append(etBreakFast.getText().toString().trim()).append("\"").append(",");
        builder.append("big2\":").append("\"").append(etLunnch.getText().toString().trim()).append("\"").append(",");
        builder.append("big3\":").append("\"").append(etDinner.getText().toString().trim()).append("\"").append(",");
        builder.append("big4\":").append("\"").append(etMore.getText().toString().trim()).append("\"");
        builder.append("}");
        return builder.toString();
    }
}
