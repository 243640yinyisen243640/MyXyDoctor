package com.xy.xydoctor.ui.activity.patienteducation;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientEducationHistoryAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.PatientEducationHistoryListBean;
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
 * 描述: 患教文章发送历史页
 * 作者: LYD
 * 创建日期: 2019/6/3 14:57
 */
public class PatientEducationHistoryListActivity extends BaseActivity {
    @BindView(R.id.lv_mass_msg_history)
    ListView lvMassMsgHistory;
    @BindView(R.id.srl_mass_msg_history)
    SmartRefreshLayout srlMassMsgHistory;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<PatientEducationHistoryListBean.DataBean> list = new ArrayList<>();//ListView显示的数据
    private List<PatientEducationHistoryListBean.DataBean> tempList;//用于临时保存ListView显示的数据
    private PatientEducationHistoryAdapter adapter;


    private void initRefresh() {
        srlMassMsgHistory.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlMassMsgHistory.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getHistoryList();
                srlMassMsgHistory.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlMassMsgHistory.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getHistoryList();
            }
        });

    }

    private void getHistoryList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_ARTICLE_HISTORY_LIST)
                .addAll(map)
                .asResponse(PatientEducationHistoryListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<PatientEducationHistoryListBean>() {
                    @Override
                    public void accept(PatientEducationHistoryListBean data) throws Exception {
                        tempList = data.getData();
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlMassMsgHistory.finishLoadMoreWithNoMoreData();
                        } else {
                            srlMassMsgHistory.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            //
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            //
                            list.addAll(tempList);
                            adapter = new PatientEducationHistoryAdapter(Utils.getApp(), R.layout.item_mass_msg_history, list);
                            //lvMassMsgHistory.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                            lvMassMsgHistory.setAdapter(adapter);
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mass_msg_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("历史记录");
        getHistoryList();
        initRefresh();
    }
}
