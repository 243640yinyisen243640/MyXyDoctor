package com.xy.xydoctor.ui.activity.director;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientForDoctorAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.io.Serializable;
import java.util.ArrayList;
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
    /**
     * 搜索带回来选中的列表
     */
    private static final int REQUEST_CODE_FOR_CHECK = 10;
    @BindView(R.id.rv_select_list_patient)
    RecyclerView patientRecyclerView;
    @BindView(R.id.tv_group_add_patient)
    TextView removeTextView;
    @BindView(R.id.ll_patient_search_list)
    LinearLayout searchLinearLayout;

    private PatientForDoctorAdapter adapter;
    private int doctorID;

    private List<GroupUserBeanPatient> allDataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remove_patient_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String doctorName = getIntent().getStringExtra("doctorName");
        doctorID = getIntent().getIntExtra("doctorID", 0);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        patientRecyclerView.setLayoutManager(layoutManager);
        setTitle(doctorName);
        getGroupList();
    }


    @OnClick({R.id.tv_group_add_patient, R.id.ll_patient_search_list})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_group_add_patient:
                //                intent = new Intent();
                //                intent.putExtra("doctorID",doctorID);
                //                intent.putExtra("checkList", (Serializable) allDataBean);
                //                setResult(RESULT_OK,intent);
                //                finish();
                List<GroupUserBeanPatient> checkList = new ArrayList<>();

                if (allDataBean != null && allDataBean.size() > 0) {
                    for (int i = 0; i < allDataBean.size(); i++) {
                        if (allDataBean.get(i).isCheck()) {
                            checkList.add(allDataBean.get(i));
                        }
                    }
                }

                if (checkList == null || checkList.size() == 0) {
                    ToastUtils.showShort(getString(R.string.please_choice_patient));
                    return;
                }
                intent = new Intent(getPageContext(), RemovePatientDoctorListResultActivity.class);
                intent.putExtra("doctorID", doctorID);
                intent.putExtra("checkList", (Serializable) allDataBean);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_patient_search_list:
                intent = new Intent(getPageContext(), SearchPatientActivity.class);
                intent.putExtra("docid", doctorID);
                startActivityForResult(intent, REQUEST_CODE_FOR_CHECK);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FOR_CHECK:
                    if (data != null) {
                        List<GroupUserBeanPatient> checkListForSearch = (List<GroupUserBeanPatient>) data.getSerializableExtra("checkList");
                        List<Integer> list = new ArrayList<>();
                        if (checkListForSearch != null) {
                            for (int i = 0; i < allDataBean.size(); i++) {
                                for (int j = 0; j < checkListForSearch.size(); j++) {
                                    if (allDataBean.get(i).getUserid() == checkListForSearch.get(j).getUserid()) {
                                        list.add(i);
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            allDataBean.get(list.get(i)).setCheck(true);
                            adapter.notifyDataSetChanged();
                        }


                    }

                    break;
                default:
                    break;
            }
        }
    }


    private void getGroupList() {

        Call<String> requestCall = DataManager.getGroupList(doctorID + "", (call, response) -> {
            if (response.code == 200) {
                allDataBean = (List<GroupUserBeanPatient>) response.object;
                if (allDataBean != null && allDataBean.size() > 0) {
                    adapter = new PatientForDoctorAdapter(getPageContext(), allDataBean, new IAdapterViewClickListener() {
                        @Override
                        public void adapterClickListener(int position, View view) {

                            allDataBean.get(position).setCheck(!allDataBean.get(position).isCheck());
                            adapter.notifyDataSetChanged();
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
