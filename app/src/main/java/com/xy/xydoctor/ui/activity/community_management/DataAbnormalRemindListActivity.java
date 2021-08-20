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
import com.xy.xydoctor.adapter.community_manager.DataAbnormalRemindAdapter;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalRemindInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
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
 * 描述:异常提醒
 * * 作者: LYD
 * 创建日期: 2020/5/26 11:07
 */
public class DataAbnormalRemindListActivity extends BaseHideLineActivity {
    @BindView(R.id.iv_data_abnormal_remind_back)
    ImageView imgTopBack;
    @BindView(R.id.tv_data_abnormal_remind_title)
    TextView titleTextView;
    @BindView(R.id.rv_data_abnormal_remind_list)
    RecyclerView rvList;
    @BindView(R.id.srl_data_abnormal_remind_agent)
    SmartRefreshLayout srlHeightAndWeight;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<DataAbnormalRemindInfo> list = new ArrayList<>();//ListView显示的数据
    private List<DataAbnormalRemindInfo> tempList; //用于临时保存ListView显示的数据
    private DataAbnormalRemindAdapter adapter;

    private String beginTime = "";
    private String endTime = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_abnormal_remind_list;
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
                .asResponseList(DataAbnormalRemindInfo.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DataAbnormalRemindInfo>>() {
                    @Override
                    public void accept(List<DataAbnormalRemindInfo> myTreatPlanBeans) throws Exception {
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
                            //这个type是血压还是血糖公用一个页面和一个adapter
                            adapter = new DataAbnormalRemindAdapter(getPageContext(), list, "", new IAdapterViewClickListener() {
                                @Override
                                public void adapterClickListener(int position, View view) {

                                }

                                @Override
                                public void adapterClickListener(int position, int index, View view) {

                                }
                            });
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

    @OnClick({R.id.iv_data_abnormal_remind_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_data_abnormal_remind_back:
                finish();
                break;

            default:
                break;
        }
    }

}
