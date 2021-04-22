package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.os.Bundle;
import android.widget.ListView;

import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MyTreatPlanAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.MyTreatPlanBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 我的治疗方案
 * 作者: LYD
 * 创建日期: 2019/3/29 17:50
 */
public class MyTreatPlanListActivity extends BaseActivity {
    @BindView(R.id.lv_my_treat_plan)
    ListView lvMyTreatPlan;
    @BindView(R.id.srl_my_treat_plan)
    SmartRefreshLayout srlMyTreatPlan;

    //分页开始
    private MyTreatPlanAdapter adapter;
    private List<MyTreatPlanBean> list;//血压数据
    private List<MyTreatPlanBean> tempList;//上拉加载数据
    private int pageIndex = 2;//上拉加载页数
    //分页结束

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_treat_plan_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            setTitle("个性化降压方案");
        } else if ("1".equals(type)) {
            setTitle("糖尿病自我管理处方");
        } else {
            setTitle("减重自我管理处方");
        }
        getLvData(type);
        initRefresh();
    }


    private void getLvData(String type) {
        HashMap map = new HashMap<>();
        map.put("page", 1);
        if ("0".equals(type)) {
            map.put("uid", getIntent().getStringExtra("userid"));
        } else {
            map.put("userid", getIntent().getStringExtra("userid"));
        }
        String url;
        if ("0".equals(type)) {
            url = XyUrl.PLAN_GET_PLAN_LIST;
        } else if ("1".equals(type)) {
            url = XyUrl.GET_DOCTOR_PROFESSION_LIST;
        } else {
            url = XyUrl.GET_LOSE_WEIGHT_LIST;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponseList(MyTreatPlanBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyTreatPlanBean>>() {
                    @Override
                    public void accept(List<MyTreatPlanBean> myTreatPlanBeans) throws Exception {
                        list = myTreatPlanBeans;
                        String type = getIntent().getStringExtra("type");
                        adapter = new MyTreatPlanAdapter(getPageContext(), R.layout.item_my_treat_plan, list, type, getIntent().getStringExtra("userid"));
                        lvMyTreatPlan.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getMoreData(String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("userid", getIntent().getStringExtra("userid"));
        String url;
        if ("0".equals(type)) {
            url = XyUrl.PLAN_GET_PLAN_LIST;
        } else if ("1".equals(type)) {
            url = XyUrl.GET_DOCTOR_PROFESSION_LIST;
        } else {
            url = XyUrl.GET_LOSE_WEIGHT_LIST;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponseList(MyTreatPlanBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyTreatPlanBean>>() {
                    @Override
                    public void accept(List<MyTreatPlanBean> myTreatPlanBeans) throws Exception {
                        tempList = myTreatPlanBeans;
                        list.addAll(tempList);
                        pageIndex++;
                        adapter.notifyDataSetChanged();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initRefresh() {
        //刷新开始
        srlMyTreatPlan.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlMyTreatPlan.finishRefresh(2000);
                pageIndex = 2;
                String type = getIntent().getStringExtra("type");
                getLvData(type);
            }
        });
        srlMyTreatPlan.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlMyTreatPlan.finishLoadMore(2000);
                String type = getIntent().getStringExtra("type");
                getMoreData(type);
            }
        });
        //刷新结束
    }
}
