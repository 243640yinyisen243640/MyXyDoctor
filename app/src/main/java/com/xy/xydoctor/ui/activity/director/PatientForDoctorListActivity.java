package com.xy.xydoctor.ui.activity.director;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientForDoctorAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/6/22 16:47
 * Description:
 */
public class PatientForDoctorListActivity extends BaseActivity {
    @BindView(R.id.rv_select_list_patient)
    RecyclerView patientRecyclerView;
    @BindView(R.id.tv_group_add_patient)
    TextView removeTextView;
    @BindView(R.id.ll_patient_search_list)
    LinearLayout searchLinearLayout;

    private PatientForDoctorAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remove_patient_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String doctorName = getIntent().getStringExtra("doctorName");
        setTitle(doctorName);
        getGroupList();
    }


    @OnClick({R.id.tv_group_add_patient, R.id.ll_patient_search_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_group_add_patient:

                break;
            case R.id.ll_patient_search_list:


                break;
            default:
                break;
        }
    }


    private void getGroupList() {
        int doctorID = getIntent().getIntExtra("doctorID", 0);
        Call<String> requestCall = DataManager.getGroupList(doctorID+"", (call, response) -> {
            if (response.code == 200) {
                List<GroupUserBeanPatient> allDataBean = (List<GroupUserBeanPatient>) response.object;
                if (allDataBean!= null && allDataBean.size() > 0) {
                    adapter = new PatientForDoctorAdapter(getPageContext(), allDataBean, new IAdapterViewClickListener() {
                        @Override
                        public void adapterClickListener(int position, View view) {

                        }

                        @Override
                        public void adapterClickListener(int position, int index, View view) {

                        }
                    });
                    patientRecyclerView.setAdapter(adapter);
                }
            }
        }, (call, t) -> {
            ToastUtils.showShort(getString(R.string.network_error));
        });
    }


    //    private void getGroupList() {
    //        String doctorID = getIntent().getStringExtra("doctorID");
    //        HashMap<String, Object> map = new HashMap<>();
    //        map.put("userid", doctorID);
    //        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
    //                .addAll(map)
    //                .asResponseList(GroupUserBeanPatient.class)
    //                .to(RxLife.toMain(this))
    //                .subscribe(new Consumer<List<GroupUserBeanPatient>>() {
    //                    @Override
    //                    public void accept(List<GroupUserBeanPatient> list) throws Exception {
    //
    //
    //                        Log.i("yys", "list==" + list);
    //                    }
    //                }, new OnError() {
    //                    @Override
    //                    public void onError(ErrorInfo error) throws Exception {
    //
    //                    }
    //                });
    //    }
}
