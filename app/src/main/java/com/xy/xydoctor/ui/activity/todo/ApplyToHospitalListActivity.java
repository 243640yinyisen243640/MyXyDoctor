package com.xy.xydoctor.ui.activity.todo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.ApplyToHospitalAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.ApplyToHospitalListBean;
import com.xy.xydoctor.constant.ConstantParam;
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
 * 描述: 住院申请
 * 作者: LYD
 * 创建日期: 2019/5/30 16:23
 */
@BindEventBus
public class ApplyToHospitalListActivity extends BaseEventBusActivity {
    @BindView(R.id.lv_hospital)
    ListView lvHospital;
    @BindView(R.id.srl_hospital)
    SmartRefreshLayout srlHospital;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<ApplyToHospitalListBean.DataBean> list = new ArrayList<>();//ListView显示的数据
    private List<ApplyToHospitalListBean.DataBean> tempList;//用于临时保存ListView显示的数据
    private ApplyToHospitalAdapter adapter;


    private void getHospitalList() {
        String type = getIntent().getStringExtra("type");
        HashMap<String, Object> map = new HashMap<>();
        int userid = getIntent().getIntExtra("userid", 0);
        map.put("userid", userid);
        map.put("page", pageIndex);
        String url;
        if ("homeSign".equals(type)) {
            url = XyUrl.HOME_SIGN_APPLY_HOSPITAL;
        } else {
            url = XyUrl.APPLY_HOSPITAL;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(ApplyToHospitalListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ApplyToHospitalListBean>() {
                    @Override
                    public void accept(ApplyToHospitalListBean data) throws Exception {
                        tempList = data.getData();
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlHospital.finishLoadMoreWithNoMoreData();
                        } else {
                            srlHospital.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            adapter = new ApplyToHospitalAdapter(Utils.getApp(), R.layout.item_apply_to_hospital, list, type);
                            lvHospital.setAdapter(adapter);
                        } else {
                            list.addAll(tempList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initRefresh() {
        srlHospital.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlHospital.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getHospitalList();
                srlHospital.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlHospital.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getHospitalList();
            }
        });
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.APPLY_TO_HOSPITAL:
                getHospitalList();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_to_hospital_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("住院申请");
        getHospitalList();
        initRefresh();
    }
}
