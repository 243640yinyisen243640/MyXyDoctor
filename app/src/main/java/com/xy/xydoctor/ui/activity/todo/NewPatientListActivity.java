package com.xy.xydoctor.ui.activity.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.HomeSignNewPatientListAdapter;
import com.xy.xydoctor.adapter.multi.NewPatientListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.NewPatientListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:新的患者列表
 * 作者: LYD
 * 创建日期: 2019/5/30 11:06
 */
@BindEventBus
public class NewPatientListActivity extends BaseEventBusActivity implements AdapterClickImp {
    private static final String TAG = "NewPatientListActivity";
    @BindView(R.id.lv_new_patient)
    ListView lvNewPatient;
    @BindView(R.id.srl_new_patient)
    SmartRefreshLayout srlNewPatient;

    private int pageIndex = 1;
    private List<NewPatientListBean.DataBean> list = new ArrayList<>();
    private List<NewPatientListBean.DataBean> tempList;
    private NewPatientListAdapter adapter;
    private HomeSignNewPatientListAdapter homeSignAdapter;

    private void getNewPatientList() {
        String type = getIntent().getStringExtra("type");
        HashMap<String, Object> map = new HashMap<>();
        int userid = getIntent().getIntExtra("userid", 0);
        map.put("userid", userid);
        map.put("page", pageIndex + "");
        String url;
        if ("homeSign".equals(type)) {
            url = XyUrl.HOME_SIGN_APPLY_USER;
        } else {
            url = XyUrl.APPLY_USER;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(NewPatientListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<NewPatientListBean>() {
                    @Override
                    public void accept(NewPatientListBean baseBean) throws Exception {
                        tempList = baseBean.getData();
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlNewPatient.finishLoadMoreWithNoMoreData();
                        } else {
                            srlNewPatient.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            if ("homeSign".equals(type)) {
                                homeSignAdapter = new HomeSignNewPatientListAdapter(Utils.getApp(), R.layout.item_apply_to_hospital, list);
                                lvNewPatient.setAdapter(homeSignAdapter);
                            } else {
                                adapter = new NewPatientListAdapter(Utils.getApp(), list, NewPatientListActivity.this);
                                lvNewPatient.setAdapter(adapter);
                            }
                        } else {
                            list.addAll(tempList);
                            if ("homeSign".equals(type)) {
                                homeSignAdapter.notifyDataSetChanged();
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initRefresh() {
        srlNewPatient.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlNewPatient.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getNewPatientList();
                srlNewPatient.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlNewPatient.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getNewPatientList();
            }
        });
    }


    @Override
    public void onAdapterClick(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_yes:
                toDoOperate(position, "2");
                break;
            case R.id.tv_no:
                toDoOperate(position, "1");
                break;
        }
    }

    private void toDoOperate(int position, String status) {
        int id = list.get(position).getId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        RxHttp.postForm(XyUrl.CHANGE_PATIENT_STATE)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        pageIndex = 1;
                        getNewPatientList();
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.NEW_PATIENT_OPERATE));//刷新列表
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
            case ConstantParam.EventCode.SIGN_DEAL:
                getNewPatientList();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_patient_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("新的患者");
        getNewPatientList();
        initRefresh();
    }
}
