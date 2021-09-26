package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.SPStaticUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.FollowupAgentSearchListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 描述:搜索页面
 * 作者: LYD
 * 创建日期: 2020/5/26 11:07
 */
public class CommunityFollowupAgentSearchListActivity extends XYSoftUIBaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<FollowUpAgentListBean> mList = new ArrayList<>();
    private List<FollowUpAgentListBean> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private boolean mIsLoading = false;

    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;
    private FollowupAgentSearchListAdapter mAdapter;

    private ImageView finishImageView;
    private EditText contentEditText;
    private TextView sureTextView;

    /**
     * 搜索内容
     */
    private String searchContent = "";

    private String comid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topViewManager().topView().removeAllViews();
        comid = getIntent().getStringExtra("comid");
        containerView().addView(initView());
        initValue();
        initLinstener();
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
        finishImageView.setOnClickListener(this);
        sureTextView.setOnClickListener(this);

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

    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Call<String> requestCall = DataManager.searchUser(comid, searchContent, mPageIndex + "",
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
                                mAdapter = new FollowupAgentSearchListAdapter(getPageContext(), mList, new OnItemClickListener());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_follow_search_img_top_back_user:
                finish();
                break;
            case R.id.tv_follow_up_search_sure_user:
                String content = contentEditText.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    TipUtils.getInstance().showToast(getPageContext(), "请输入关键字");
                    return;
                }
                searchContent = content;
                mPageIndex = 1;
                onPageLoad();

                break;
            default:
                break;
        }
    }

    private class OnItemClickListener implements IAdapterViewClickListener {
        @Override
        public void adapterClickListener(int position, View view) {
            Intent intent;

            //一级的点击事件
            switch (view.getId()) {
                case R.id.ll_fu_search_click:
                    //3:主任  4:医生  10:物业
                    int type = SPStaticUtils.getInt("docType");
                    if (10 != type) {
                        intent = new Intent(getPageContext(), CommunityUserInfoActivity.class);
                        intent.putExtra("userid", mList.get(position).getUserid());
                        intent.putExtra("username", mList.get(position).getNickname());
                        startActivity(intent);
                    }

                    break;
                default:
                    break;

            }
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {

        }
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_follow_up_agent_search_list, null);
        finishImageView = getViewByID(view, R.id.iv_follow_search_img_top_back_user);
        contentEditText = getViewByID(view, R.id.et_follow_up_search_user);
        sureTextView = getViewByID(view, R.id.tv_follow_up_search_sure_user);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout_search);
        mRecyclerView = getViewByID(view, R.id.rv_live_search);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate_search);
        stateTextView = getViewByID(view, R.id.tv_no_data_search);
        return view;
    }


}
