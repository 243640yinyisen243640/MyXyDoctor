package com.xy.xydoctor.ui.fragment.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityFollowUpChangeListAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.ui.activity.community_management.CommunityNoFinishActivity;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * 类描述：
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class CommunityFollowUpListFragment extends XYBaseFragment implements TabFragmentAdapter.RefeshFragment {

    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<FollowUpAgentListBean> mList = new ArrayList<>();
    private List<FollowUpAgentListBean> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private CommunityFollowUpChangeListAdapter mAdapter;
    private boolean mIsLoading = false;
    private String searchContent = "";
    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;
    /**
     * 1：待随访:2：失访:3：已完成
     */
    private String type;




    public static CommunityFollowUpListFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        CommunityFollowUpListFragment fragment = new CommunityFollowUpListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
        initView();
        initValue();
        initLinstener();
        onPageLoad();
    }


    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Call<String> requestCall = DataManager.goodsList("", mPageIndex + "", mPageSize + "", type, searchContent,
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
                                mAdapter = new CommunityFollowUpChangeListAdapter(getPageContext(), mList, "", new OnItemClickListener());
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
                    } else if (101 == response.code) {
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
            if (100 != responseCode) {
                if (101 == responseCode) {
                    stateTextView.setText(getString(R.string.huahansoft_load_state_no_data));
                } else {
                    stateTextView.setText(getString(R.string.network_error));
                }
                stateTextView.setOnClickListener(view -> {
                    onPageLoad();
                });
                mRefreshLayout.setVisibility(View.VISIBLE);
                presentNestedSrcollView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.GONE);
                presentNestedSrcollView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_prefrerential_goods, null);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout);
        mRecyclerView = getViewByID(view, R.id.rv_live);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate);
        stateTextView = getViewByID(view, R.id.tv_no_data);
        containerView().addView(view);

    }

    private void initValue() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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

    private class OnItemClickListener implements IAdapterViewClickListener {
        @Override
        public void adapterClickListener(int position, View view) {

            //一级的点击事件
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            //二级的点击事件
            switch (view.getId()) {
                case R.id.tv_fuc_child_no_finish:
                    Intent intent = new Intent(getPageContext(), CommunityNoFinishActivity.class);
                    startActivityForResult(intent,REQUEST_CODE_FOR_REFRESH);
                    break;
                case R.id.tv_fuc_child_call_phone:

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_REFRESH) {
                mPageIndex = 1;
                onPageLoad();
            }
        }
    }
}
