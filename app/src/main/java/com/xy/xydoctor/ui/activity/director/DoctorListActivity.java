package com.xy.xydoctor.ui.activity.director;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.DoctorListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  主任端 医生列表
 * 作者: LYD
 * 创建日期: 2020/5/18 9:36
 */
@BindEventBus
public class DoctorListActivity extends BaseEventBusActivity {
    private static final String TAG = "DoctorListActivity";
    @BindView(R.id.lv_doctor_list)
    ListView lvDoctorList;
    @BindView(R.id.srl_doctor_list)
    SmartRefreshLayout srlDoctorList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra("type");
        if ("homeDoctor".equals(type)) {
            setTitle("医生管理");
            getTvMore().setText("添加");
            getTvMore().setVisibility(View.VISIBLE);
            getTvMore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getPageContext(), DoctorAddAndEditActivity.class);
                    intent.putExtra("type", "add");
                    startActivity(intent);
                }
            });
        } else {
            setTitle("待办事项");
        }
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
                            String type = getIntent().getStringExtra("type");
                            DoctorListAdapter adapter = new DoctorListAdapter(getPageContext(), R.layout.item_doctor_list, list, type);
                            lvDoctorList.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.DOCTOR_ADD_SUCCESS:
                //Log.e(TAG, "接收成功");
                getDoctorList();
                break;
            case ConstantParam.EventCode.DOCTOR_TO_DO_2_DIRECTOR_TO_DO:
                Log.e(TAG, "接收成功");
                getDoctorList();

                break;
        }
    }
}
