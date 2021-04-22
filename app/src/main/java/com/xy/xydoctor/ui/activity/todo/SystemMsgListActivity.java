package com.xy.xydoctor.ui.activity.todo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SystemMessageListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SystemMessageListBean;
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
 * 描述: 智慧助手
 * 作者: LYD
 * 创建日期: 2019/5/29 21:55
 */
public class SystemMsgListActivity extends BaseActivity {
    @BindView(R.id.lv_system_message)
    ListView lvSystemMessage;
    @BindView(R.id.srl_system_message)
    SmartRefreshLayout srlSystemMessage;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<SystemMessageListBean> list = new ArrayList<>();//ListView显示的数据
    private List<SystemMessageListBean> tempList;//用于临时保存ListView显示的数据
    private SystemMessageListAdapter adapter;


    private void getSystemMessageList() {
        NetworkUtils.is5G();
        HashMap<String, Object> map = new HashMap<>();
        int userid = getIntent().getIntExtra("userid", 0);
        map.put("userid", userid);
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_UNREAD_SYSTEM_MESSAGE_LIST)
                .addAll(map)
                .asResponseList(SystemMessageListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<SystemMessageListBean>>() {
                    @Override
                    public void accept(List<SystemMessageListBean> data) throws Exception {
                        tempList = data;
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlSystemMessage.finishLoadMoreWithNoMoreData();
                        } else {
                            srlSystemMessage.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            adapter = new SystemMessageListAdapter(Utils.getApp(), R.layout.item_system_message, list);
                            lvSystemMessage.setAdapter(adapter);
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
        srlSystemMessage.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlSystemMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getSystemMessageList();
                srlSystemMessage.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlSystemMessage.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getSystemMessageList();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_message_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("智慧助手");
        getSystemMessageList();
        initRefresh();
    }
}
