package com.xy.xydoctor.ui.activity.massmsg;

import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MsgHistoryAdapter;
import com.xy.xydoctor.base.activity.BaseListViewActivity;
import com.xy.xydoctor.bean.MassHistoryBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 群发消息历史记录
 * 作者: LYD
 * 创建日期: 2019/3/1 17:15
 */
public class MassMsgHistoryActivity extends BaseListViewActivity {
    private static final String TAG = "MassMsgHistoryActivity";
    //当前获取的是第几页的数据
    private int pageIndex = 1;
    //ListView显示的数据
    private List<MassHistoryBean> list = new ArrayList<>();
    //ListView的Adapter
    private MsgHistoryAdapter adapter;


    @Override
    protected void loadPageInfo() {
        setTitle("群发消息历史记录");
    }

    @Override
    protected void loadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", pageIndex + "");
        map.put("wid", getIntent().getStringExtra("type"));
        RxHttp.postForm(XyUrl.MASS_MSG_HISTORY)
                .addAll(map)
                .asResponseList(MassHistoryBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MassHistoryBean>>() {
                    @Override
                    public void accept(List<MassHistoryBean> massHistoryBeans) throws Exception {
                        if (massHistoryBeans != null && massHistoryBeans.size() > 0) {
                            setLvData(massHistoryBeans);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        showEmptyView();
                    }
                });

    }


    /**
     * 设置列表数据
     *
     * @param massHistoryBeans
     */
    private void setLvData(List<MassHistoryBean> massHistoryBeans) {
        showNormalContentView();
        //少于10条,将不会再次触发加载更多事件
        if (massHistoryBeans.size() < 10) {
            getSrlBase().finishLoadMoreWithNoMoreData();
        } else {
            getSrlBase().finishLoadMore();
        }
        //设置数据
        if (pageIndex == 1) {
            //下拉刷新
            if (list == null) {
                list = new ArrayList<>();
            } else {
                list.clear();
            }
            //添加数据
            list.addAll(massHistoryBeans);
            adapter = new MsgHistoryAdapter(Utils.getApp(), R.layout.item_mass_msg_history, list);
            getLvBase().setAdapter(adapter);
        } else {
            //上拉加载
            //上拉添加数据
            list.addAll(massHistoryBeans);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onBaseRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh(2000);
        pageIndex = 1;
        loadData();
    }

    @Override
    protected void onBaseLoadMore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadMore(2000);
        ++pageIndex;
        loadData();
    }
}
