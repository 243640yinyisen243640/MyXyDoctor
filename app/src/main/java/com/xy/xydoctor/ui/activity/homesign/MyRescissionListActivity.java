package com.xy.xydoctor.ui.activity.homesign;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.util.edittext.EditTextUtils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MyRescissionNewAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.HomeSignRescissionBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class MyRescissionListActivity extends BaseActivity {

    private static final int GET_DATA = 0x0081;
    private static final int REFRESH = 0x0082;
    private static final int LOAD_MORE = 0x0083;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    ColorTextView tvSearch;
    @BindView(R.id.rv_home_sign)
    RecyclerView rvHomeSign;
    @BindView(R.id.srl_home_sign)
    SmartRefreshLayout srlHomeSign;
    @BindView(R.id.iv_no_result)
    ImageView ivNoResult;

    private String key = "";
    private int page = 1;
    private List<HomeSignRescissionBean> rescissionBeanList;
    private MyRescissionNewAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_rescission_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData(GET_DATA);
        initRefresh();
        EditTextUtils.setOnActionSearch(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                key = etSearch.getText().toString().trim();
                page = 1;
                initData(GET_DATA);
            }
        });
    }

    private void initRefresh() {
        srlHomeSign.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlHomeSign.finishRefresh(2000);
                page = 1;
                initData(REFRESH);
            }
        });
        srlHomeSign.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlHomeSign.finishLoadMore(2000);
                page++;
                initData(LOAD_MORE);
            }
        });
    }

    private void initData(int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", key);
        map.put("page", page);
        RxHttp.postForm(XyUrl.GET_RESCISSION_LIST)
                .addAll(map)
                .asResponseList(HomeSignRescissionBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<HomeSignRescissionBean>>() {
                    @Override
                    public void accept(List<HomeSignRescissionBean> myTreatPlanBeans) throws Exception {
                        switch (type) {
                            case GET_DATA:
                            case REFRESH:
                                rescissionBeanList = myTreatPlanBeans;
                                adapter = new MyRescissionNewAdapter(rescissionBeanList);
                                rvHomeSign.setAdapter(adapter);
                                rvHomeSign.setLayoutManager(new LinearLayoutManager(getPageContext()));

                                srlHomeSign.finishRefresh();
                            case LOAD_MORE:
                                rescissionBeanList.addAll(myTreatPlanBeans);
                                adapter.notifyDataSetChanged();
                                srlHomeSign.finishLoadMore();
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                key = etSearch.getText().toString().trim();
                page = 1;
                initData(GET_DATA);
                break;
        }
    }
}
