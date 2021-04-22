package com.xy.xydoctor.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.fragment.BaseLazyFragment;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FollowUpVisitListAdapter;
import com.xy.xydoctor.bean.FollowUpVisitListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 随访管理
 * 作者: LYD
 * 创建日期: 2019/7/29 16:49
 */
@BindEventBus
public class FollowUpVisitFragment extends BaseLazyFragment {
    private static final String TAG = "FollowUpVisitFragment";
    @BindView(R.id.lv_follow_up_visit)
    ListView lvFollowUpVisit;
    @BindView(R.id.srl_follow_up_visit)
    SmartRefreshLayout srlFollowUpVisit;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页开始
    private FollowUpVisitListAdapter adapter;
    //总数据
    private List<FollowUpVisitListBean.DataBean> list;
    //上拉加载数据
    private List<FollowUpVisitListBean.DataBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow_up_visit;
    }

    @Override
    protected void init(View rootView) {
        initRefresh();
    }

    /**
     * 获取列表数据
     */
    private void getFollowUpList() {
        String type = getArguments().getString("type");
        String userId = getArguments().getString("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("page", 1);
        map.put("type", type);
        LogUtils.e(getArguments().getBoolean("is_family", false));
        if (getArguments().getBoolean("is_family", false)) {
            map.put("is_family", 1);
        }
        RxHttp.postJson(XyUrl.GET_FOLLOW_NEW)
                .addAll(map)
                .asResponse(FollowUpVisitListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<FollowUpVisitListBean>() {
                    @Override
                    public void accept(FollowUpVisitListBean followUpVisitListBean) {
                        Log.e(TAG, "成功回调执行了");
                        llEmpty.setVisibility(View.GONE);
                        srlFollowUpVisit.setVisibility(View.VISIBLE);
                        list = followUpVisitListBean.getData();
                        String type = getArguments().getString("type");
                        adapter = new FollowUpVisitListAdapter(getPageContext(), R.layout.item_follow_up_visit_list, list, type);
                        lvFollowUpVisit.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        ToastUtils.cancel();
                        if (error.getErrorCode() == ConstantParam.DEFAULT_NO_DATA) {
                            llEmpty.setVisibility(View.VISIBLE);
                            srlFollowUpVisit.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 刷新数据
     */
    private void initRefresh() {
        //刷新开始
        srlFollowUpVisit.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlFollowUpVisit.finishRefresh(2000);
                pageIndex = 2;
                getFollowUpList();
            }
        });
        srlFollowUpVisit.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlFollowUpVisit.finishLoadMore(2000);
                String type = getArguments().getString("type");
                String userId = getArguments().getString("userId");
                HashMap<String, Object> map = new HashMap<>();
                LogUtils.e(getArguments().getBoolean("is_family", false));
                if (getArguments().getBoolean("is_family", false)) {
                    map.put("is_family", 1);
                }
                map.put("userid", userId);
                map.put("page", pageIndex);
                map.put("type", type);
                RxHttp.postForm(XyUrl.GET_FOLLOW_NEW).addAll(map)
                        .asResponse(FollowUpVisitListBean.class)
                        .to(RxLife.toMain(getActivity()))
                        .subscribe(new Consumer<FollowUpVisitListBean>() {
                            @Override
                            public void accept(FollowUpVisitListBean followUpVisitListBean) throws Exception {
                                tempList = followUpVisitListBean.getData();
                                list.addAll(tempList);
                                pageIndex++;
                                adapter.notifyDataSetChanged();
                            }
                        }, new OnError() {
                            @Override
                            public void onError(ErrorInfo error) throws Exception {

                            }
                        });
            }
        });
        //刷新结束
    }

    /**
     * 懒加载
     */
    @Override
    public void loadData() {
        getFollowUpList();
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT:
                llEmpty.setVisibility(View.GONE);
                srlFollowUpVisit.setVisibility(View.VISIBLE);
                getFollowUpList();
                break;
        }
    }
}
