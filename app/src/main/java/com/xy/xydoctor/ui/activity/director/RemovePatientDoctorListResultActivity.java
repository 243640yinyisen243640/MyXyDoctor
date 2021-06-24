package com.xy.xydoctor.ui.activity.director;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientForListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Call;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  主任端 转移患者医生列表
 * 作者: LYD
 * 创建日期: 2020/5/18 9:36
 */
@BindEventBus
public class RemovePatientDoctorListResultActivity extends BaseEventBusActivity {

    private static final String TAG = "DoctorListActivity";
    @BindView(R.id.lv_doctor_list)
    ListView lvDoctorList;
    @BindView(R.id.srl_doctor_list)
    SmartRefreshLayout srlDoctorList;
    private int doctorID;

    private List<GroupUserBeanPatient> checkList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        doctorID = getIntent().getIntExtra("doctorID", 0);
        checkList = (List<GroupUserBeanPatient>) getIntent().getSerializableExtra("checkList");
        setTitle(getString(R.string.doctor_remove));
        getTvMore().setVisibility(View.GONE);
        getDoctorList();
        initRefresh();
    }


    private void initRefresh() {
        srlDoctorList.setEnableAutoLoadMore(true);
        //下拉刷新
        srlDoctorList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDoctorList();
                srlDoctorList.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        });
    }

    private void getDoctorList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_LIST)
                .addAll(map)
                .asResponseList(DoctorListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DoctorListBean>>() {
                    @Override
                    public void accept(List<DoctorListBean> list) throws Exception {
                        if (list != null && list.size() > 0) {
                            PatientForListAdapter adapter = new PatientForListAdapter(getPageContext(), list);
                            lvDoctorList.setAdapter(adapter);
                            lvDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //                                    Intent intent = new Intent(getPageContext(), PatientForDoctorListActivity.class);
                                    //                                    intent.putExtra("doctorName", list.get(position).getDocname());
                                    //                                    intent.putExtra("doctorID", list.get(position).getUserid());
                                    //                                    startActivityForResult(intent, REQUEST_CODE_FOR_CHECK);
                                    if (doctorID == list.get(position).getUserid()) {

                                        ToastUtils.showShort(getString(R.string.please_choice_other_doctor));
                                    } else {
                                        saveData(list.get(position).getUserid());
                                    }
                                }
                            });
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    private void saveData(int doctorID) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i).isCheck()) {
                int userid = checkList.get(i).getUserid();
                stringBuilder.append(userid);
                stringBuilder.append(",");
            }

        }
        //截取最后,
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);

        Call<String> requestCall = DataManager.removePatient(doctorID + "", substring, (call, response) -> {
            if (response.code == 200) {

            }
        }, (call, t) -> {
            ToastUtils.showShort(getString(R.string.network_error));
        });
    }

}
