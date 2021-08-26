package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.UserMedicineListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.UseMedicineListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * Author: LYD
 * Date: 2021/8/16 16:36
 * Description: 用药提醒
 */
public class CommunityUserMedicineActivity extends XYSoftUIBaseActivity {

    private TextView nameAndLocationTextView;
    private ListView medicineListView;
    private SmartRefreshLayout medicineSmartRefreshLayout;
    //分页开始
    private UserMedicineListAdapter adapter;
    //总数据
    private List<UseMedicineListBean.DataBean> list;
    //上拉加载数据
    private List<UseMedicineListBean.DataBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("用药提醒");
        containerView().addView(initView());
        getFollowUpList();
        initRefresh();
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_user_medicine, null);
        nameAndLocationTextView = view.findViewById(R.id.tv_um_name_and_location);
        medicineListView = view.findViewById(R.id.lv_user_medicine);
        medicineSmartRefreshLayout = view.findViewById(R.id.srl_user_medicine);
        return view;
    }


    /**
     * 获取列表数据
     */
    private void getFollowUpList() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", 1);
        RxHttp.postJson(XyUrl.GET_FOLLOW_NEW)
                .addAll(map)
                .asResponse(UseMedicineListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<UseMedicineListBean>() {
                    @Override
                    public void accept(UseMedicineListBean followUpVisitListBean) {
                        list = followUpVisitListBean.getData();
                        adapter = new UserMedicineListAdapter(getPageContext(), R.layout.item_user_medicine_list, list, new IAdapterViewClickListener() {
                            @Override
                            public void adapterClickListener(int position, View view) {

                            }

                            @Override
                            public void adapterClickListener(int position, int index, View view) {

                            }
                        });
                        medicineListView.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        ToastUtils.cancel();
                        if (error.getErrorCode() == ConstantParam.DEFAULT_NO_DATA) {

                        }
                    }
                });
    }

    /**
     * 刷新数据
     */
    private void initRefresh() {
        //刷新开始
        medicineSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                medicineSmartRefreshLayout.finishRefresh(2000);
                pageIndex = 2;
                getFollowUpList();
            }
        });
        medicineSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                medicineSmartRefreshLayout.finishLoadMore(2000);

                HashMap<String, Object> map = new HashMap<>();
                map.put("page", pageIndex);
                RxHttp.postForm(XyUrl.GET_FOLLOW_NEW).addAll(map)
                        .asResponse(UseMedicineListBean.class)
                        .to(RxLife.toMain(CommunityUserMedicineActivity.this))
                        .subscribe(new Consumer<UseMedicineListBean>() {
                            @Override
                            public void accept(UseMedicineListBean followUpVisitListBean) throws Exception {
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

}
