package com.xy.xydoctor.ui.fragment.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalListAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.view.popup.DataAbnormalPopup1;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD  type   1：血压 2：血糖
 * Date: 2021/8/16 16:36
 * Description: 数据异常
 */
public class CommunityDataAbnormalFragment extends XYBaseFragment implements View.OnClickListener {

    private DataAbnormalListAdapter mAdapter;
    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<DataAbnormalInfo> mList = new ArrayList<>();
    private List<DataAbnormalInfo> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private boolean mIsLoading = false;
    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;

    private TextView filterTextView;
    private TextView secondTextView;
    private TextView firstTextView;

    private LinearLayout showLinearLayout;
    //
    private DataAbnormalPopup1 popu;

    /**
     * 1血压2血糖
     */
    private String type;

    /**
     * 1高2低3正常4未测量5自定义
     */
    private String style = "1";
    /**
     * 血糖起始值
     */
    private String startSugar = "";
    /**
     * 血糖结束值
     */
    private String endSugar = "";
    /**
     * 处理是否 0全部 1是 2否
     */
    private String status = "2";
    /**
     * 开始时间
     */
    private String starttime;
    /**
     * 结束时间
     */
    private String endtime;

    public static CommunityDataAbnormalFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        CommunityDataAbnormalFragment fragment = new CommunityDataAbnormalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        starttime = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y_M_D);
        endtime = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y_M_D);
        if (getArguments() != null) {
            type = getArguments().getString("type");

            if ("1".equals(type)) {
                type = "2";
            } else {
                type = "1";
            }
        }
        containerView().addView(initVew());

        initValue();
        initLinstener();
        onPageLoad();
    }


    private View initVew() {
        View view = View.inflate(getPageContext(), R.layout.fragment_data_abnormal, null);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout_data_abnormal);
        mRecyclerView = getViewByID(view, R.id.rv_live_data_abnormal);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate_data_abnormal);
        stateTextView = getViewByID(view, R.id.tv_no_data_data_abnormal);
        firstTextView = getViewByID(view, R.id.tv_data_abnormal_first);
        secondTextView = getViewByID(view, R.id.tv_data_abnormal_second);
        filterTextView = getViewByID(view, R.id.tv_data_abnormal_down);
        showLinearLayout = getViewByID(view, R.id.ll_show_pop);
        return view;
    }


    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Call<String> requestCall = DataManager.getDataAbnormalList(type, starttime, endtime, style, startSugar, endSugar, mPageIndex + "", status,
                (call, response) -> {
                    mIsLoading = false;
                    if (1 != mPageIndex) {
                        mRefreshLayout.finishLoadMore();
                    } else {
                        mRefreshLayout.finishRefresh();
                    }
                    if (200 == response.code) {
                        mTempList = (List<DataAbnormalInfo>) response.object;
                        mPageCount = mTempList == null ? 0 : mTempList.size();
                        if (1 == mPageIndex) {
                            if (mList == null) {
                                mList = new ArrayList<>();
                            } else {
                                mList.clear();
                            }
                            mList.addAll(mTempList);
                            if (mAdapter == null) {
                                mAdapter = new DataAbnormalListAdapter(getPageContext(), mList, type, new OnItemClickListener());
                                mRecyclerView.setAdapter(mAdapter);
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            mList.addAll(mTempList);
                            mAdapter.notifyDataSetChanged();
                        }
                        //如果是加载成功
                        mRefreshLayout.setVisibility(View.VISIBLE);
                        presentNestedSrcollView.setVisibility(View.GONE);
                    } else if (30002 == response.code) {
                        mPageCount = 0;
                        if (1 == mPageIndex) {
                            //如果是没有数据
                            mRefreshLayout.setVisibility(View.GONE);
                            presentNestedSrcollView.setVisibility(View.VISIBLE);

                        } else {
                            TipUtils.getInstance().showToast(getPageContext(), R.string.huahansoft_load_state_no_more_data);
                        }
                    } else {
                        mPageCount = 0;
                        if (1 == mPageIndex) {
                            mRefreshLayout.setVisibility(View.GONE);
                            presentNestedSrcollView.setVisibility(View.VISIBLE);
                        } else {
                            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
                        }
                    }
                    if (mList.size() > 0) {
                        changeLoadUI(response.code);
                    } else {
                        changeLoadUI(101);
                    }

                }, (call, t) -> {
                    handleRequestFailure();
                });
        addRequestCallToMap("goodsList", requestCall);
    }

    private void handleRequestFailure() {
        mPageCount = 0;
        mIsLoading = false;
        if (1 != mPageIndex) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishRefresh();
        }
        if (1 == mPageIndex) {

            mRefreshLayout.setVisibility(View.GONE);
            presentNestedSrcollView.setVisibility(View.VISIBLE);
        } else {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        }
        changeLoadUI(-1);
    }

    private void changeLoadUI(int responseCode) {
        presentNestedSrcollView.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
        if (1 == mPageIndex) {
            if (200 != responseCode) {
                if (30002 == responseCode) {
                    stateTextView.setText(getString(R.string.huahansoft_load_state_no_data));
                } else {
                    stateTextView.setText(getString(R.string.network_error));
                }
                stateTextView.setOnClickListener(view -> {
                    onPageLoad();
                });
                mRefreshLayout.setVisibility(View.GONE);
                presentNestedSrcollView.setVisibility(View.VISIBLE);
            }
        }
    }


    private void initValue() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //解决底部滚动到顶部时，顶部item上方偶尔会出现一大片间隔的问题
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[2];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
    }

    private void initLinstener() {

        filterTextView.setOnClickListener(this);
        //设置下拉刷新和上拉加载监听
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {

                mPageIndex = 1;
                onPageLoad();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(true);
        //        mRefreshLayout.setEnableAutoLoadMore(true);
        // mRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
       /* mRefreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
                return false;
            }

            @Override
            public boolean canLoadMore(View content) {
                return mPageCount == mPageSize && !mIsLoading;
            }
        });*/
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPageIndex++;
            onPageLoad();
        });
    }


    public void setCheckAll() {

        //在activity 里面点击处理，下面会出现全选按钮，列表会出现让选择的按钮，点击全选 会走这个方法，这个方法是让让一级二级列表全部选中

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_data_abnormal_down:
                //                popu = new DataAbnormalPopup(getPageContext(), new BaseCallBack() {
                //                    @Override
                //                    public void callBack(Object object) {
                //
                //                    }
                //                });
                //                popu.showPopupWindow(showLinearLayout);
                showMenuWindow();

                break;
            default:
                break;
        }
    }


    private void showMenuWindow() {
        popu = new DataAbnormalPopup1(getPageContext(), object -> {

            int position = (int) object;
            switch (position) {


                default:
                    break;
            }

        });

        if (!popu.isShowing()) {
            popu.showAsDropDown(showLinearLayout);
        }
    }

    private class OnItemClickListener implements IAdapterViewClickListener {
        @Override
        public void adapterClickListener(int position, View view) {

            //一级的点击事件
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            //二级的点击事件
            switch (view.getId()) {

                default:
                    break;
            }
        }
    }
}
