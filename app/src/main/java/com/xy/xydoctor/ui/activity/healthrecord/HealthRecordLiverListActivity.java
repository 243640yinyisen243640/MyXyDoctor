package com.xy.xydoctor.ui.activity.healthrecord;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.xy.xydoctor.adapter.HepatopathyPabulumListAdapter;
import com.xy.xydoctor.bean.HepatopathyPabulumListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.view.popup.SlideFromTopPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  肝病记录
 * 作者: LYD
 * 创建日期: 2020/5/25 14:21
 */
public class HealthRecordLiverListActivity extends BaseHideLineActivity {
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.img_top_back)
    ImageView imgTopBack;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.img_up_down)
    ImageView imgUpDown;
    @BindView(R.id.ll_up_down)
    LinearLayout llUpDown;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rv_liver_list)
    RecyclerView rvLiverList;
    @BindView(R.id.srl_height_and_weight)
    SmartRefreshLayout srlHeightAndWeight;

    SlideFromTopPopup popu;
    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<HepatopathyPabulumListBean> list = new ArrayList<>();//ListView显示的数据
    private List<HepatopathyPabulumListBean> tempList; //用于临时保存ListView显示的数据
    private HepatopathyPabulumListAdapter adapter;
    private String beginTime = "";
    private String endTime = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_record_liver_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTopTitle.setText("肝病记录");
        hideTitleBar();
        initReFresh();
        initValue(beginTime, endTime);
        initPopu();
    }

    private void initPopu() {
        popu = new SlideFromTopPopup(getPageContext(), getIntent().getStringExtra("userid"));
    }


    @OnClick({R.id.img_top_back, R.id.tv_start_time, R.id.tv_end_time, R.id.ll_up_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_back:
                finish();
                break;
            case R.id.ll_up_down://popu
                dismissPopu();
                break;
            case R.id.tv_start_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String time) {
                        tvStartTime.setText(time);
                        beginTime = time;
                        if (!TextUtils.isEmpty(endTime)) {
                            pageIndex = 1;
                            //解决空指针
                            if (tempList != null) {
                                tempList.clear();
                            }
                            if (list != null) {
                                list.clear();
                            }
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                            //解决空指针
                            initValue(beginTime, endTime);
                        }
                    }
                });
                break;
            case R.id.tv_end_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String time) {
                        tvEndTime.setText(time);
                        endTime = time;
                        if (!TextUtils.isEmpty(beginTime)) {
                            pageIndex = 1;
                            if (tempList != null) {
                                tempList.clear();
                            }
                            if (list != null) {
                                list.clear();
                            }
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                            initValue(beginTime, endTime);
                        }
                    }
                });
                break;
        }
    }

    /**
     * 隐藏popu
     */
    private void dismissPopu() {
        popu.showPopupWindow(rlTitle);
    }


    private void initValue(String beginTime, String endTime) {
        String userId = getIntent().getStringExtra("userid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userId + "");
        map.put("begintime", beginTime);
        map.put("endtime", endTime);
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.HEPATOPATHY_PABULUM_LIVER_LIST)
                .addAll(map)
                .asResponseList(HepatopathyPabulumListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<HepatopathyPabulumListBean>>() {
                    @Override
                    public void accept(List<HepatopathyPabulumListBean> myTreatPlanBeans) throws Exception {
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
                            adapter = new HepatopathyPabulumListAdapter(list);
                            rvLiverList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvLiverList.setAdapter(adapter);
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

    private void initReFresh() {
        srlHeightAndWeight.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlHeightAndWeight.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                initValue(beginTime, endTime);
                srlHeightAndWeight.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlHeightAndWeight.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                initValue(beginTime, endTime);
            }
        });
    }
}
