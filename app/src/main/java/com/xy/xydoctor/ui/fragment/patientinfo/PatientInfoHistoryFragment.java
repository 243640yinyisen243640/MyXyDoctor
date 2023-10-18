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
 * 类描述：查看历史
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class PatientInfoHistoryFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {


    public static PatientInfoHistoryFragment newInstance(String para1, String para2) {
        Bundle bundle = new Bundle();
        bundle.putString("para1", para1);
        bundle.putString("para2", para2);
        PatientInfoHistoryFragment fragment = new PatientInfoHistoryFragment();
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
        View view = View.inflate(getPageContext(), R.layout.activity_follow_up_list, null);

        containerView().addView(view);

    }






}
