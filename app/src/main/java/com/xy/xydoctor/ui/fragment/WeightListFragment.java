package com.xy.xydoctor.ui.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.WeightListAdapter;
import com.xy.xydoctor.bean.WeightListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  体重记录之列表
 * 作者: LYD
 * 创建日期: 2020/5/11 10:59
 */
public class WeightListFragment extends BaseFragment {
    private static final int GET_DATA_SUCCESS = 10010;
    private static final int GET_DATA_MORE = 10011;
    private static final int DEL_SUCCESS = 10012;
    private static final int GET_DATA_ERROR = 10013;
    @BindView(R.id.tv_start_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.lv_weight_list)
    ListView lvWeightList;
    @BindView(R.id.srl_weight_list)
    SmartRefreshLayout srlWeightList;

    private String beginTime = "";
    private String endTime = "";

    private WeightListAdapter adapter;
    private List<WeightListBean> list;
    //private List<WeightListBean> tempList;
    private int pageIndex = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weight_list;
    }

    @Override
    protected void init(View rootView) {
        getWeightList(beginTime, endTime);
        initRefresh();
    }


    private void initRefresh() {
        //刷新开始
        srlWeightList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlWeightList.finishRefresh(2000);
                pageIndex = 2;
                tvBeginTime.setText(getString(R.string.start_date));
                tvEndTime.setText(getString(R.string.end_date));
                getWeightList("", "");
            }
        });
        srlWeightList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlWeightList.finishLoadMore(2000);
                getDataMore(beginTime, endTime);
            }
        });
        //刷新结束
    }

    private void getWeightList(String beginTime, String endTime) {
        String userid = getArguments().getString("userid");
        HashMap map = new HashMap<>();
        map.put("uid", userid);
        map.put("begintime", beginTime);
        map.put("endtime", endTime);
        map.put("page", 1);
        RxHttp.postForm(XyUrl.GET_WEIGHT_LIST)
                .addAll(map)
                .asResponseList(WeightListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<WeightListBean>>() {
                    @Override
                    public void accept(List<WeightListBean> myTreatPlanBeans) throws Exception {
                        list = myTreatPlanBeans;
                        if (list != null && list.size() > 0) {
                            adapter = new WeightListAdapter(getPageContext(), R.layout.item_weight_list, list);
                            lvWeightList.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        ToastUtils.showShort("数据为空");
                        list = new ArrayList<>();
                        adapter = new WeightListAdapter(getPageContext(), R.layout.item_weight_list, list);
                        lvWeightList.setAdapter(adapter);
                    }
                });
    }


    /**
     * 获取更多
     *
     * @param bTime
     * @param eTime
     */
    private void getDataMore(String bTime, String eTime) {
        String userid = getArguments().getString("userid");
        HashMap map = new HashMap<>();
        map.put("uid", userid);
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_WEIGHT_LIST)
                .addAll(map)
                .asResponseList(WeightListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<WeightListBean>>() {
                    @Override
                    public void accept(List<WeightListBean> myTreatPlanBeans) throws Exception {
                        list.addAll(myTreatPlanBeans);
                        pageIndex++;
                        adapter.notifyDataSetChanged();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvBeginTime.setText(content);
                        endTime = tvEndTime.getText().toString().trim();
                        if ("选择结束时间".equals(endTime)) {
                            endTime = "";
                        }
                        beginTime = tvBeginTime.getText().toString().trim();
                        getWeightList(beginTime, endTime);
                    }

                });
                break;
            case R.id.tv_end_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvEndTime.setText(content);
                        beginTime = tvBeginTime.getText().toString().trim();
                        if ("选择开始时间".equals(beginTime)) {
                            beginTime = "";
                        }
                        endTime = tvEndTime.getText().toString().trim();
                        getWeightList(beginTime, endTime);
                    }
                });
                break;
        }
    }
}
