package com.xy.xydoctor.base.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;

import butterknife.BindView;

/**
 * 描述: ListView列表基类
 * 作者: LYD
 * 创建日期: 2019/12/10 11:23
 */
public abstract class BaseListViewActivity extends BaseActivity {
    /**
     * 是否加载更多
     */
    public static final String FLAG_LOAD_MORE = "load_more";
    /**
     * 是否可以下拉刷新
     */
    public static final String FLAG_REFRESH = "refresh";
    //列表开始
    @BindView(R.id.lv_base)
    ListView lvBase;
    @BindView(R.id.srl_base)
    SmartRefreshLayout srlBase;
    //列表开始

    //空页面开始
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //空页面结束

    //Srl状态开始
    //是否下拉刷新
    private boolean mIsRefresh = true;
    //是否加载更多
    private boolean mIsLoadMore = true;

    //Srl状态结束

    /**
     * 设置SmartRefreshLayout
     */
    private void initSrl() {
        //设置是否启用下拉刷新和上拉加载功能
        mIsRefresh = getIntent().getBooleanExtra(FLAG_REFRESH, true);
        mIsLoadMore = getIntent().getBooleanExtra(FLAG_LOAD_MORE, true);
        //是否启用下拉刷新功能
        srlBase.setEnableRefresh(mIsRefresh);
        //是否启用上拉加载功能
        srlBase.setEnableLoadMore(mIsLoadMore);
        //是否启用列表惯性滑动到底部时自动加载更多
        srlBase.setEnableAutoLoadMore(true);
        //设置刷新监听器（不设置，默认3秒后关刷新）
        srlBase.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshlayout) {
                onBaseRefresh(refreshlayout);
            }
        });
        //设置加载监听器（不设置，默认3秒后关加载）
        srlBase.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                onBaseLoadMore(refreshLayout);
            }
        });
    }

    /**
     * 加载Activity的数据，该方法在init中执行
     * 主要用于设置Activity的一些信息
     */
    protected abstract void loadPageInfo();

    /**
     * 从服务器接口加载数据，子类进行实现
     */
    protected abstract void loadData();

    /**
     * 下拉刷新加载回调方法
     *
     * @param refreshlayout
     */
    protected abstract void onBaseRefresh(RefreshLayout refreshlayout);

    /**
     * 上拉加载更多回调方法
     *
     * @param refreshlayout
     */
    protected abstract void onBaseLoadMore(RefreshLayout refreshlayout);


    /**
     * 设置标题栏结束
     */


    /**
     * 返回SmartRefreshLayout
     *
     * @return
     */
    protected SmartRefreshLayout getSrlBase() {
        return srlBase;
    }

    /**
     * 返回ListView
     *
     * @return
     */
    protected ListView getLvBase() {
        return lvBase;
    }

    /**
     * 显示空布局
     */
    protected void showEmptyView() {
        srlBase.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
    }


    /**
     * 显示正常列表内容的View
     */
    protected void showNormalContentView() {
        srlBase.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loadPageInfo();
        initSrl();
        loadData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_listview;
    }
}
