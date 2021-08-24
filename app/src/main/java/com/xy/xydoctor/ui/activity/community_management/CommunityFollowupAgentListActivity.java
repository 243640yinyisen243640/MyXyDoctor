package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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
import com.xy.xydoctor.adapter.community_manager.FollowupAgentListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.base.custom.GridSpaceItemDecoration;
import com.xy.xydoctor.base.utils.XYSoftDensityUtils;
import com.xy.xydoctor.bean.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:随访代办列表，小区数量列表
 * * 作者: LYD
 * * @param type 1：是全部小区 2：是随访代办
 * 创建日期: 2020/5/26 11:07
 */
public class CommunityFollowupAgentListActivity extends XYSoftUIBaseActivity {
    private EditText searchTextView;
    private RecyclerView followUpList;
    private SmartRefreshLayout followSmartRefreshLayout;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<FollowUpAgentListBean> list = new ArrayList<>();//ListView显示的数据
    private List<FollowUpAgentListBean> tempList; //用于临时保存ListView显示的数据
    private FollowupAgentListAdapter adapter;

    /**
     * 1：全部小区  2：随访代办
     */
    private String type = "1";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("1".equals(type)) {
            String commuinityName = getIntent().getStringExtra("commuinityName");
            topViewManager().titleTextView().setText(commuinityName);
        } else {
            topViewManager().titleTextView().setText(R.string.follow_up_agent_title);
        }
        containerView().addView(initView());
        initValues();
        getData();
        initRefresh();
        initListener();

    }

    private void initListener() {
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData();
            }
        });
    }

    private void initValues() {
        //设置每一个item间距
        GridLayoutManager layoutManager = new GridLayoutManager(getPageContext(), 1);
        followUpList.addItemDecoration(new GridSpaceItemDecoration(XYSoftDensityUtils.dip2px(getPageContext(), 0), false));
        followUpList.setLayoutManager(layoutManager);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_follow_up_agent_list, null);
        searchTextView = view.findViewById(R.id.et_follow_up_agent);
        followUpList = view.findViewById(R.id.rv_follow_up_agent_list);
        followSmartRefreshLayout = view.findViewById(R.id.srl_follow_up_agent);
        return view;
    }


    private void initRefresh() {
        followSmartRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        followSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getData();
                followSmartRefreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        followSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                .asResponseList(FollowUpAgentListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FollowUpAgentListBean>>() {
                    @Override
                    public void accept(List<FollowUpAgentListBean> myTreatPlanBeans) throws Exception {
                        tempList = myTreatPlanBeans;
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            followSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            followSmartRefreshLayout.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            adapter = new FollowupAgentListAdapter(getPageContext(), list, new IAdapterViewClickListener() {
                                @Override
                                public void adapterClickListener(int position, View view) {
                                    Intent intent = new Intent(getPageContext(), CommunityFollowUpBuildingActivity.class);
                                    intent.putExtra("buildingName", list.get(position).getBpmval());
                                    intent.putExtra("type", type);
                                    startActivity(intent);
                                }

                                @Override
                                public void adapterClickListener(int position, int index, View view) {

                                }
                            });
                            followUpList.setLayoutManager(new LinearLayoutManager(getPageContext()));

                            followUpList.setAdapter(adapter);
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
