package com.xy.xydoctor.ui.fragment.patientinfo;

import android.os.Bundle;
import android.view.View;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;

import retrofit2.Call;

/**
 * 类描述：治疗方案
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class PatientInfoProgrammeFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {


    public static PatientInfoProgrammeFragment newInstance(String userId) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        PatientInfoProgrammeFragment fragment = new PatientInfoProgrammeFragment();
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
        Call<String> requestCall = DataManager.getFollowList("para1","para2", (call, response) -> {
            if (200 == response.code) {


            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }




    private void initView() {
        View view = View.inflate(getPageContext(), R.layout._fragment_injection_list, null);

        containerView().addView(view);

    }






}
