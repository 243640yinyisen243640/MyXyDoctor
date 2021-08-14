package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.FollowupAgentListAdapter;
import com.xy.xydoctor.bean.FollowUpAgentListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.healthrecord.BaseHideLineActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  血氧记录
 * 作者: LYD
 * 创建日期: 2020/5/26 11:07
 */
public class CommunityFollowupAgentListActivity extends BaseHideLineActivity {
    @BindView(R.id.community_follow_img_top_back)
    ImageView imgTopBack;
    @BindView(R.id.et_follow_up_agent)
    TextView searchTextView;
    @BindView(R.id.rv_follow_up_agent_list)
    RecyclerView rvList;
    @BindView(R.id.srl_follow_up_agent)
    SmartRefreshLayout srlHeightAndWeight;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<FollowUpAgentListBean> list = new ArrayList<>();//ListView显示的数据
    private List<FollowUpAgentListBean> tempList; //用于临时保存ListView显示的数据
    private FollowupAgentListAdapter adapter;

    private String beginTime = "";
    private String endTime = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_follow_up_agent_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        getData(beginTime, endTime);
        initRefresh();
    }

    private void initRefresh() {
        srlHeightAndWeight.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlHeightAndWeight.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getData(beginTime, endTime);
                srlHeightAndWeight.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlHeightAndWeight.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getData(beginTime, endTime);
            }
        });
    }


    private void getData(String b, String e) {
        HashMap map = new HashMap<>();
        String userid = getIntent().getStringExtra("userid");
        map.put("uid", userid);
        map.put("begintime", b);
        map.put("endtime", e);
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
                            srlHeightAndWeight.finishLoadMoreWithNoMoreData();
                        } else {
                            srlHeightAndWeight.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            adapter = new FollowupAgentListAdapter(list);
                            rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvList.setAdapter(adapter);
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

    @OnClick({R.id.community_follow_img_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_back:
                finish();
                break;

            default:
                break;
        }
    }

}
