package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.FollowupAgentListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 描述:随访代办列表，小区数量列表
 * * 作者: LYD
 * * @param type 1：是全部小区 2：是随访代办,3添加用户
 * 创建日期: 2020/5/26 11:07
 */
public class CommunityFollowupAgentListActivity extends XYSoftUIBaseActivity {
    private EditText searchTextView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<FollowUpAgentListBean> mList = new ArrayList<>();
    private List<FollowUpAgentListBean> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private FollowupAgentListAdapter mAdapter;
    private boolean mIsLoading = false;
    /**
     * 1:普通小区列表 2：待办小区列表 3:添加用户
     */
    private String type;

    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;

    private String searchContent = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        KeyboardUtils.hideSoftInput(CommunityFollowupAgentListActivity.this);
        if ("1".equals(type)) {
            String commuinityName = SPStaticUtils.getString("hospitalname");
            topViewManager().titleTextView().setText(commuinityName);
            Log.i("yys", "commuinityName==" + commuinityName);
        } else if ("2".equals(type)) {
            topViewManager().titleTextView().setText(R.string.follow_up_agent_title);
        } else {
            String commuinityName = SPStaticUtils.getString("hospitalname");
            topViewManager().titleTextView().setText(commuinityName);
        }
        containerView().addView(initView());
        initValues();
        initListener();
        onPageLoad();
    }

    private void initListener() {
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchContent = s.toString();
                onPageLoad();
            }
        });

        //设置下拉刷新和上拉加载监听
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {

                mPageIndex = 1;
                onPageLoad();
            }
        });
        mRefreshLayout.setEnableLoadMore(true);
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

    private void initValues() {
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

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_follow_up_agent_list, null);
        searchTextView = view.findViewById(R.id.et_follow_up_agent);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout_agent);
        mRecyclerView = getViewByID(view, R.id.rv_live_agent);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate_agent);
        stateTextView = getViewByID(view, R.id.tv_no_data_agent);
        return view;
    }


    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Call<String> requestCall = DataManager.getAgentList(mPageIndex + "", searchContent, type,
                (call, response) -> {

                    mIsLoading = false;
                    if (1 != mPageIndex) {
                        mRefreshLayout.finishLoadMore();
                    } else {
                        mRefreshLayout.finishRefresh();
                    }
                    if (200 == response.code) {
                        mTempList = (List<FollowUpAgentListBean>) response.object;
                        mPageCount = mTempList == null ? 0 : mTempList.size();
                        if (1 == mPageIndex) {
                            if (mList == null) {
                                mList = new ArrayList<>();
                            } else {
                                mList.clear();
                            }
                            mList.addAll(mTempList);
                            if (mAdapter == null) {
                                mAdapter = new FollowupAgentListAdapter(getPageContext(), mList, new OnItemClickListener());
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
                        changeLoadUI(30002);
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

    private class OnItemClickListener implements IAdapterViewClickListener {
        @Override
        public void adapterClickListener(int position, View view) {

            //一级的点击事件
            switch (view.getId()) {
                case R.id.ll_fu_click:

                    break;
                default:
                    break;
            }
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
