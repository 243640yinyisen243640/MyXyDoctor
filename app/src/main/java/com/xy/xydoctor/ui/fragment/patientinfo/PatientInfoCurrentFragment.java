package com.xy.xydoctor.ui.fragment.patientinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.InjectionCurrentAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.community_manamer.InjectionDataDetail;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordInjectioneListActivity;
import com.xy.xydoctor.utils.TipUtils;

import retrofit2.Call;

/**
 * 类描述：治疗方案
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class PatientInfoCurrentFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {
    private TextView tvName;
    private TextView tvTime;
    private RecyclerView recyclerView;
    private InjectionCurrentAdapter adapter;
    private InjectionDataDetail dataDetail;

    public static PatientInfoCurrentFragment newInstance(String userId) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        PatientInfoCurrentFragment fragment = new PatientInfoCurrentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        containerView().addView(initView());
        getData();
    }

    public void getData() {
        String userId = getArguments().getString("userId");
        String action_time = ((HealthRecordInjectioneListActivity) getActivity()).getTime();
        Call<String> requestCall = DataManager.getInjectionDetail(userId, action_time, "1", (call, response) -> {
            if (200 == response.code) {
                dataDetail = (InjectionDataDetail) response.object;
                setData();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void setData() {
        tvName.setText(dataDetail.getPlan_name());
        tvTime.setText(dataDetail.getAction_time() + "执行");
        recyclerView.setLayoutManager(new LinearLayoutManager(getPageContext()));
        adapter = new InjectionCurrentAdapter(getPageContext(), dataDetail.getDetail());
        recyclerView.setAdapter(adapter);
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout._fragment_injection_currcnt, null);
        tvName = view.findViewById(R.id.tv_current_name);
        tvTime = view.findViewById(R.id.tv_current_time);
        recyclerView = view.findViewById(R.id.rv_current);
        return view;
    }


}
