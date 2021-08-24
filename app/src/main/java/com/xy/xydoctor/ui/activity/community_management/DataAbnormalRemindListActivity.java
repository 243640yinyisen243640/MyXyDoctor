package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalRemindAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.base.custom.GridSpaceItemDecoration;
import com.xy.xydoctor.base.utils.XYSoftDensityUtils;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalRemindInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:异常提醒
 * * 作者: LYD
 *
 * @paramtype 1:血糖 2血压
 * 创建日期: 2020/5/26 11:07
 */
public class DataAbnormalRemindListActivity extends XYSoftUIBaseActivity {

    private RecyclerView rvRecyclerView;
    private SmartRefreshLayout remindSmartRefreshLayout;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<DataAbnormalRemindInfo> list = new ArrayList<>();//ListView显示的数据
    private List<DataAbnormalRemindInfo> tempList; //用于临时保存ListView显示的数据
    private DataAbnormalRemindAdapter adapter;

    /**
     * 1：血糖 2：血压
     */
    private String type = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("数据异常");
        type = getIntent().getStringExtra("type");
        containerView().addView(initView());
        initValues();
        getData();
        initRefresh();
    }

    private void initValues() {
        //设置每一个item间距
        GridLayoutManager layoutManager = new GridLayoutManager(getPageContext(), 1);
        rvRecyclerView.addItemDecoration(new GridSpaceItemDecoration(XYSoftDensityUtils.dip2px(getPageContext(), 0), false));
        rvRecyclerView.setLayoutManager(layoutManager);

    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_data_abnormal_remind_list, null);
        rvRecyclerView = view.findViewById(R.id.rv_data_abnormal_remind_list);
        remindSmartRefreshLayout = view.findViewById(R.id.srl_data_abnormal_remind_agent);
        return view;
    }


    private void initRefresh() {
        remindSmartRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        remindSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getData();
                remindSmartRefreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        remindSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getData();
            }
        });
    }


    private void getData() {
        HashMap map = new HashMap<>();
        String userid = getIntent().getStringExtra("userid");
        map.put("uid", userid);
        map.put("page", 1);
        RxHttp.postForm(XyUrl.GET_BLOOD_OXYGEN)
                .addAll(map)
                .asResponseList(DataAbnormalRemindInfo.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DataAbnormalRemindInfo>>() {
                    @Override
                    public void accept(List<DataAbnormalRemindInfo> myTreatPlanBeans) throws Exception {
                        tempList = myTreatPlanBeans;
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            remindSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            remindSmartRefreshLayout.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            //这个type是血压还是血糖公用一个页面和一个adapter
                            adapter = new DataAbnormalRemindAdapter(getPageContext(), list, type, new IAdapterViewClickListener() {
                                @Override
                                public void adapterClickListener(int position, View view) {

                                }

                                @Override
                                public void adapterClickListener(int position, int index, View view) {

                                }
                            });
                            rvRecyclerView.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvRecyclerView.setAdapter(adapter);
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


}
