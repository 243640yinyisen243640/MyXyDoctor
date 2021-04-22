package com.xy.xydoctor.ui.activity.homesign;
/*
 * 包名:     com.xy.xydoctor.ui.activity.homesign
 * 类名:     MyHomeSignListActivity
 * 描述:     我的家庭签约列表
 * 作者:     ZWK
 * 创建日期: 2020/1/6 9:22
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.edittext.EditTextUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.HomeSignFamilyNewAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.HomeSignFamilyBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/*
 * 包名:     com.xy.xydoctor.ui.activity.homesign
 * 类名:     FamilyActivity
 * 描述:     家庭签约列表
 * 作者:     ZWK
 * 创建日期: 2020/1/6 10:12
 */

public class MyHomeSignListActivity extends BaseActivity {

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
    @BindView(R.id.tv_finish)
    ColorTextView tvFinish;
    private List<HomeSignFamilyBean> homeSignList;
    private String key = "";
    private int page = 1;
    private HomeSignFamilyNewAdapter adapter;
    private boolean isSelect;


    private void initExtra(int type) {
        int family_id = getIntent().getIntExtra("family_id", -1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", key);
        map.put("id", family_id);
        map.put("page", page);
        RxHttp.postForm(XyUrl.GET_FAMILY_DEL_LIST)
                .addAll(map)
                .asResponseList(HomeSignFamilyBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<HomeSignFamilyBean>>() {
                    @Override
                    public void accept(List<HomeSignFamilyBean> myTreatPlanBeans) throws Exception {
                        switch (type) {
                            case GET_DATA:
                            case REFRESH:
                                homeSignList = myTreatPlanBeans;
                                adapter = new HomeSignFamilyNewAdapter(homeSignList);
                                //adapter.setRecyclerView(rvHomeSign);
                                rvHomeSign.setAdapter(adapter);
                                rvHomeSign.setLayoutManager(new LinearLayoutManager(getPageContext()));
                                break;
                            case LOAD_MORE:
                                homeSignList.addAll(myTreatPlanBeans);
                                adapter.notifyDataSetChanged();
                                srlHomeSign.finishLoadMore();
                                break;
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initRefresh() {
        srlHomeSign.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlHomeSign.finishRefresh(2000);
                page = 1;
                if (isSelect) {
                    initExtra(REFRESH);
                } else {
                    initData(REFRESH);
                }
            }
        });
        srlHomeSign.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlHomeSign.finishLoadMore(2000);
                page++;
                if (isSelect) {
                    initExtra(LOAD_MORE);
                } else {
                    initData(LOAD_MORE);
                }
            }
        });
    }

    private void initData(int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", key);
        map.put("page", page);
        RxHttp.postForm(XyUrl.GET_FAMILY_LIST)
                .addAll(map)
                .asResponseList(HomeSignFamilyBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<HomeSignFamilyBean>>() {
                    @Override
                    public void accept(List<HomeSignFamilyBean> myTreatPlanBeans) throws Exception {
                        switch (type) {
                            case GET_DATA:
                            case REFRESH:
                                homeSignList = myTreatPlanBeans;
                                adapter = new HomeSignFamilyNewAdapter(homeSignList);
                                rvHomeSign.setAdapter(adapter);
                                rvHomeSign.setLayoutManager(new LinearLayoutManager(getPageContext()));
                                adapter.setOnItemSelectListener(new HomeSignFamilyNewAdapter.OnItemSelectListener() {
                                    @Override
                                    public void OnItemSelect(View v, HomeSignFamilyBean bean) {
                                        Intent intent = new Intent(getPageContext(), FamilyActivity.class);
                                        intent.putExtra("family_id", bean.getId());
                                        intent.putExtra("name", bean.getNickname());
                                        startActivity(intent);
                                    }
                                });
                                srlHomeSign.finishRefresh();
                                break;
                            case LOAD_MORE:
                                homeSignList.addAll(myTreatPlanBeans);
                                adapter.notifyDataSetChanged();
                                srlHomeSign.finishLoadMore();
                                break;
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @OnClick({R.id.tv_search, R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                page = 1;
                key = etSearch.getText().toString().trim();
                if (isSelect) {
                    initExtra(GET_DATA);
                } else {
                    initData(GET_DATA);
                }
                break;
            case R.id.tv_finish:
                int family_id = getIntent().getIntExtra("family_id", -1);
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", adapter.getId());
                map.put("familyid", family_id);
                RxHttp.postForm(XyUrl.FAMILY_MEMBER_IN)
                        .addAll(map)
                        .asResponse(String.class)
                        .to(RxLife.toMain(this))
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String response) throws Exception {
                                ToastUtils.showShort("操作成功");
                                EventBusUtils.post(new EventMessage<Integer>(ConstantParam.EventCode.FAMILY_MEMBER_ADD));
                                finish();
                            }
                        }, new OnError() {
                            @Override
                            public void onError(ErrorInfo error) throws Exception {

                            }
                        });
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_home_sign_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("我的家庭签约列表");
        isSelect = getIntent().getBooleanExtra("select", false);
        if (isSelect) {
            tvFinish.setVisibility(View.VISIBLE);
            initExtra(GET_DATA);
            initRefresh();
            EditTextUtils.setOnActionSearch(etSearch, new EditTextUtils.OnActionSearchListener() {
                @Override
                public void onActionSearch() {
                    key = etSearch.getText().toString().trim();
                    page = 1;
                    initExtra(GET_DATA);
                }
            });
        } else {
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
    }
}
