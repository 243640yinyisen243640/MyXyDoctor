package com.xy.xydoctor.ui.fragment.community_management;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.fragment.BaseLazyFragment;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalListAdapter;
import com.xy.xydoctor.bean.FollowUpVisitListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.BaseCallBack;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.view.popup.DataAbnormalPopup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * Author: LYD
 * Date: 2021/8/16 16:36
 * Description: 数据异常
 */
public class CommunityDataAbnormalFragment extends BaseLazyFragment {
    private static final String TAG = "FollowUpVisitFragment";
    @BindView(R.id.tv_data_abnormal_first)
    TextView firstTextView;
    @BindView(R.id.ll_show_pop)
    LinearLayout showLinearLayout;
    @BindView(R.id.tv_data_abnormal_second)
    TextView secondTextView;
    @BindView(R.id.tv_data_abnormal_down)
    TextView filterTextView;
    @BindView(R.id.lv_data_abnormal)
    ListView lvFollowUpVisit;
    @BindView(R.id.srl_data_abnormal)
    SmartRefreshLayout srlFollowUpVisit;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页开始
    private DataAbnormalListAdapter adapter;
    //总数据
    private List<FollowUpVisitListBean.DataBean> list;
    //上拉加载数据
    private List<FollowUpVisitListBean.DataBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束

    //
    DataAbnormalPopup popu;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_abnormal;
    }

    @Override
    protected void init(View rootView) {
        initRefresh();
        popu = new DataAbnormalPopup(getPageContext(), new BaseCallBack() {
            @Override
            public void callBack(Object object) {

            }
        });
    }


    /**
     * 获取列表数据
     */
    private void getFollowUpList() {
        String type = getArguments().getString("type");
        String userId = getArguments().getString("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("page", 1);
        map.put("type", type);
        LogUtils.e(getArguments().getBoolean("is_family", false));
        if (getArguments().getBoolean("is_family", false)) {
            map.put("is_family", 1);
        }
        RxHttp.postJson(XyUrl.GET_FOLLOW_NEW)
                .addAll(map)
                .asResponse(FollowUpVisitListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<FollowUpVisitListBean>() {
                    @Override
                    public void accept(FollowUpVisitListBean followUpVisitListBean) {
                        Log.e(TAG, "成功回调执行了");
                        llEmpty.setVisibility(View.GONE);
                        srlFollowUpVisit.setVisibility(View.VISIBLE);
                        list = followUpVisitListBean.getData();
                        String type = getArguments().getString("type");
                        adapter = new DataAbnormalListAdapter(getPageContext(), R.layout.item_data_abnormal_list, list, type);
                        lvFollowUpVisit.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        ToastUtils.cancel();
                        if (error.getErrorCode() == ConstantParam.DEFAULT_NO_DATA) {
                            llEmpty.setVisibility(View.VISIBLE);
                            srlFollowUpVisit.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 刷新数据
     */
    private void initRefresh() {
        //刷新开始
        srlFollowUpVisit.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlFollowUpVisit.finishRefresh(2000);
                pageIndex = 2;
                //                getFollowUpList();
            }
        });
        srlFollowUpVisit.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlFollowUpVisit.finishLoadMore(2000);
                String type = getArguments().getString("type");
                String userId = getArguments().getString("userId");
                HashMap<String, Object> map = new HashMap<>();
                LogUtils.e(getArguments().getBoolean("is_family", false));
                if (getArguments().getBoolean("is_family", false)) {
                    map.put("is_family", 1);
                }
                map.put("userid", userId);
                map.put("page", pageIndex);
                map.put("type", type);
                RxHttp.postForm(XyUrl.GET_FOLLOW_NEW).addAll(map)
                        .asResponse(FollowUpVisitListBean.class)
                        .to(RxLife.toMain(getActivity()))
                        .subscribe(new Consumer<FollowUpVisitListBean>() {
                            @Override
                            public void accept(FollowUpVisitListBean followUpVisitListBean) throws Exception {
                                tempList = followUpVisitListBean.getData();
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
        });
        //刷新结束
    }

    /**
     * 懒加载
     */
    @Override
    public void loadData() {
        //        getFollowUpList();
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT:
                llEmpty.setVisibility(View.GONE);
                srlFollowUpVisit.setVisibility(View.VISIBLE);
                //                getFollowUpList();
                break;
        }
    }

    @OnClick({R.id.tv_data_abnormal_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_data_abnormal_down:
                popu.showPopupWindow(showLinearLayout);
                break;
            default:
                break;
        }
    }


    //    private DataAbnormalPopup menuWindow;
    //
    //    private void showMenuWindow() {
    //        if (menuWindow != null && menuWindow.isShowing()) {
    //            menuWindow.dismiss();
    //        }
    //        if (menuWindow == null) {
    //            menuWindow = new DataAbnormalPopup(getPageContext(), object -> {
    //                menuWindow.dismiss();
    //                int position = (int) object;
    //                Intent intent;
    //                switch (position) {
    //                    case 0:
    //                        break;
    //                    default:
    //                        break;
    //                }
    //            });
    //        }
    //
    //        if (!isRemoving()) {
    //            menuWindow.showAsDropDown(showLinearLayout);
    //        }
    //    }

}
