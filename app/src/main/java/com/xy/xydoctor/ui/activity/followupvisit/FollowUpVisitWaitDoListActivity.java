package com.xy.xydoctor.ui.activity.followupvisit;

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
import com.xy.xydoctor.adapter.FollowUpVisitWaitDoListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.FollowUpVisitWaitDoListBean;
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
 * 描述:   随访待办列表
 * 作者: LYD
 * 创建日期: 2019/8/8 9:38
 */
@BindEventBus
public class FollowUpVisitWaitDoListActivity extends BaseEventBusActivity {
    @BindView(R.id.lv_follow_up_wait_do)
    ListView lvFollowUpWaitDo;
    @BindView(R.id.srl_follow_up_wait_do)
    SmartRefreshLayout srlFollowUpWaitDo;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<FollowUpVisitWaitDoListBean.DataBean> list = new ArrayList<>();//ListView显示的数据
    private List<FollowUpVisitWaitDoListBean.DataBean> tempList;//用于临时保存ListView显示的数据
    private FollowUpVisitWaitDoListAdapter adapter;

    /**
     * 刷新
     */
    private void initRefresh() {
        srlFollowUpWaitDo.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlFollowUpWaitDo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getFollowUpWaitDoList();
                srlFollowUpWaitDo.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlFollowUpWaitDo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getFollowUpWaitDoList();
            }
        });

    }

    /**
     * 获取数据
     */
    private void getFollowUpWaitDoList() {
        String type = getIntent().getStringExtra("type");
        HashMap<String, Object> map = new HashMap<>();
        int userid = getIntent().getIntExtra("userid", 0);
        map.put("userid", userid);
        map.put("page", pageIndex);
        String url;
        if ("homeSign".equals(type)) {
            url = XyUrl.HOME_SIGN_FOLLOW_LIST;
        } else {
            url = XyUrl.FOLLOW_LIST;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(FollowUpVisitWaitDoListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<FollowUpVisitWaitDoListBean>() {
                    @Override
                    public void accept(FollowUpVisitWaitDoListBean data) throws Exception {
                        tempList = data.getData();
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlFollowUpWaitDo.finishLoadMoreWithNoMoreData();
                        } else {
                            srlFollowUpWaitDo.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            adapter = new FollowUpVisitWaitDoListAdapter(Utils.getApp(), R.layout.item_follow_up_visit_wait_do, list);
                            lvFollowUpWaitDo.setAdapter(adapter);
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


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.FOLLOW_UP_VISIT_SUMMARY_ADD:
                pageIndex = 1;
                getFollowUpWaitDoList();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_up_visit_wait_do_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("随访待办");
        getFollowUpWaitDoList();
        initRefresh();
    }
}
