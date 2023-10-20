package com.xy.xydoctor.ui.fragment.patientinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.InjectionAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.community_manamer.InjectionDataListInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * 类描述：注射数据
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class PatientInfoInjectionFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {
    private TextView tvChange;
    private RecyclerView rvInjection;
    private String userId;
    private String beginTime;
    private InjectionAdapter adapter;
    private List<InjectionDataListInfo> listInfos = new ArrayList<>();

    public static PatientInfoInjectionFragment newInstance(String userid) {
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);
        PatientInfoInjectionFragment fragment = new PatientInfoInjectionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        userId = getArguments().getString("userid");
        initView();
        beginTime = DataUtils.convertDateToString(new Date(System.currentTimeMillis()), "YYYY-MM");
        adapter = new InjectionAdapter(getPageContext(), listInfos);
        rvInjection.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvInjection.setAdapter(adapter);
        getData();
    }

    private void getData() {
        Call<String> requestCall = DataManager.getInjectionList(userId, beginTime, (call, response) -> {
            if (200 == response.code) {
                listInfos.clear();
                listInfos.addAll((List<InjectionDataListInfo>) response.object);
                adapter.notifyDataSetChanged();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout._fragment_injection_list, null);
        tvChange = view.findViewById(R.id.tv_injection_change);
        tvChange.setOnClickListener(v -> {
            //选择时间
            beginTime = "10-19";
            getData();
        });
        rvInjection = view.findViewById(R.id.rv_injection);
        containerView().addView(view);

    }


}
