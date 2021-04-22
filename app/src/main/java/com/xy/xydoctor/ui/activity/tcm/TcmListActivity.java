package com.xy.xydoctor.ui.activity.tcm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.TcmListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.base.activity.BaseWebViewActivity;
import com.xy.xydoctor.bean.TcmListBean;
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
 * 描述:   中医体质列表
 * 作者: LYD
 * 创建日期: 2019/8/12 9:23
 */
public class TcmListActivity extends BaseActivity {

    @BindView(R.id.lv_tcm)
    ListView lvTcm;
    @BindView(R.id.srl_tcm)
    SmartRefreshLayout srlTcm;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    private TcmListAdapter adapter;
    private List<TcmListBean.DataBean> list;
    private List<TcmListBean.DataBean> tempList;
    private int pageIndex = 1;
    private String createUrl;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_tcm_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        getTcmList();
        initRefresh();
    }


    private void initTitle() {
        setTitle("中医体质");
        getTvMore().setVisibility(View.VISIBLE);
        getTvMore().setText("创建");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(createUrl)) {
                    Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                    intent.putExtra("title", "中医体质");
                    intent.putExtra("url", createUrl);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 刷新
     */
    private void initRefresh() {
        srlTcm.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlTcm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getTcmList();
                srlTcm.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlTcm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getTcmList();
            }
        });
    }

    /**
     * 获取列表
     */
    private void getTcmList() {
        String userId = getIntent().getStringExtra("userId");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_TCM_LIST)
                .addAll(map)
                .asResponse(TcmListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<TcmListBean>() {
                    @Override
                    public void accept(TcmListBean data) throws Exception {
                        createUrl = data.getCreaturl();
                        tempList = data.getData();
                        if (tempList.size() < 10) {
                            srlTcm.finishLoadMoreWithNoMoreData();
                        } else {
                            srlTcm.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            if (list.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                srlTcm.setVisibility(View.VISIBLE);
                                adapter = new TcmListAdapter(Utils.getApp(), R.layout.item_tcm_list, list);
                                lvTcm.setAdapter(adapter);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                srlTcm.setVisibility(View.GONE);
                            }
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
