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
import com.xy.xydoctor.adapter.BloodPressureListAdapter;
import com.xy.xydoctor.bean.BloodPressureBean;
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
 * 描述: 血压记录
 * 作者: LYD
 * 创建日期: 2019/3/6 17:07
 */
public class HealthRecordBloodPressureListActivity extends BaseHideLineActivity {
    @BindView(R.id.img_top_back)
    ImageView imgTopBack;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.ll_up_down)
    LinearLayout llUpDown;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    //    @BindView(R.id.lv_blood_pressure)
    //    ListView lvBloodPressure;
    @BindView(R.id.rv_blood_pressure)
    RecyclerView rvBloodPressure;
    @BindView(R.id.srl_blood_pressure)
    SmartRefreshLayout srlBloodPressure;
    //popu开始
    private SlideFromTopPopup popu;
    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<BloodPressureBean> list = new ArrayList<>();//ListView显示的数据
    private List<BloodPressureBean> tempList;//用于临时保存ListView显示的数据
    //private BloodPressureAdapter adapter;
    private BloodPressureListAdapter adapter;
    private String beginTime = "";
    private String endTime = "";
    //popu结束

    private void initValue(String beginTime, String endTime) {
        String userId = getIntent().getStringExtra("userid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userId);
        map.put("begintime", beginTime);
        map.put("endtime", endTime);
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_BLOOD_PRESSURE_LIST)
                .addAll(map)
                .asResponseList(BloodPressureBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<BloodPressureBean>>() {
                    @Override
                    public void accept(List<BloodPressureBean> myTreatPlanBeans) throws Exception {
                        tempList = myTreatPlanBeans;
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlBloodPressure.finishLoadMoreWithNoMoreData();
                        } else {
                            srlBloodPressure.finishLoadMore();
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
                            adapter = new BloodPressureListAdapter(list);
                            rvBloodPressure.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvBloodPressure.setAdapter(adapter);
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
        srlBloodPressure.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlBloodPressure.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                initValue(beginTime, endTime);
                srlBloodPressure.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlBloodPressure.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                initValue(beginTime, endTime);
            }
        });
    }

    @OnClick({R.id.img_top_back, R.id.tv_start_time, R.id.tv_end_time, R.id.ll_up_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_back:
                finish();
                break;
            case R.id.tv_start_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String time) {
                        tvStartTime.setText(time);
                        beginTime = time;
                        if (!TextUtils.isEmpty(endTime)) {
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
            case R.id.ll_up_down://popu
                dismissPopu();
                break;
        }
    }

    /**
     * 初始化popu
     */
    private void initPopu() {
        popu = new SlideFromTopPopup(getPageContext(), getIntent().getStringExtra("userid"));
    }

    /**
     * 隐藏popu
     */
    private void dismissPopu() {
        popu.showPopupWindow(rlTitle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_record_blood_pressure_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTopTitle.setText("血压记录");
        hideTitleBar();
        initReFresh();
        initValue(beginTime, endTime);
        initPopu();
    }
}
