package com.xy.xydoctor.ui.activity.community_management;

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
import com.xy.xydoctor.adapter.community_manager.CommunityUserMedicineForSomeOneListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineUserInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 类描述：用药提醒记录
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class CommunityUserMedicineRecordListActivity extends XYSoftUIBaseActivity {

    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<CommunityUseMedicineUserInfo> mList = new ArrayList<>();
    private List<CommunityUseMedicineUserInfo> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private CommunityUserMedicineForSomeOneListAdapter mAdapter;
    private boolean mIsLoading = false;

    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;

    private String userid;

    private TextView recordTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = getIntent().getStringExtra("userid");
        topViewManager().titleTextView().setText(R.string.user_info_medicine_remind);
        topViewManager().moreTextView().setText(R.string.add_medicine_title);
        topViewManager().moreTextView().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), CommunityMedicineAddActivity.class);
            intent.putExtra("userid", userid);
            intent.putExtra("pharmacy_id", "0");
            intent.putExtra("type", "1");
            startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
        });
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
        Call<String> requestCall = DataManager.useMedicineRemindUser(userid, mPageIndex + "",
                (call, response) -> {
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mIsLoading = false;
                    if (1 != mPageIndex) {
                        mRefreshLayout.finishLoadMore();
                    } else {
                        mRefreshLayout.finishRefresh();
                    }
                    if (200 == response.code) {
                        mTempList = (List<CommunityUseMedicineUserInfo>) response.object;
                        mPageCount = mTempList == null ? 0 : mTempList.size();
                        if (1 == mPageIndex) {
                            if (mList == null) {
                                mList = new ArrayList<>();
                            } else {
                                mList.clear();
                            }
                            mList.addAll(mTempList);
                            if (mAdapter == null) {
                                mAdapter = new CommunityUserMedicineForSomeOneListAdapter(getPageContext(), mList, new OnItemClickListener());
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
                        recordTextView.setVisibility(View.VISIBLE);
                    } else if (30002 == response.code) {
                        mPageCount = 0;
                        if (1 == mPageIndex) {
                            //如果是没有数据
                            mRefreshLayout.setVisibility(View.GONE);
                            presentNestedSrcollView.setVisibility(View.VISIBLE);
                            recordTextView.setVisibility(View.GONE);

                        } else {
                            TipUtils.getInstance().showToast(getPageContext(), R.string.huahansoft_load_state_no_more_data);
                        }
                    } else {
                        mPageCount = 0;
                        if (1 == mPageIndex) {
                            mRefreshLayout.setVisibility(View.GONE);
                            recordTextView.setVisibility(View.GONE);
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
            recordTextView.setVisibility(View.GONE);
            presentNestedSrcollView.setVisibility(View.VISIBLE);
        } else {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        }
        changeLoadUI(-1);
    }

    private void changeLoadUI(int responseCode) {
        presentNestedSrcollView.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
        recordTextView.setVisibility(View.VISIBLE);
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
                recordTextView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.GONE);
                presentNestedSrcollView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_use_medicine_for_some_one, null);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout_record);
        mRecyclerView = getViewByID(view, R.id.rv_um_record);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_um_record_nodate);
        stateTextView = getViewByID(view, R.id.tv_um_record_no_data);
        recordTextView = getViewByID(view, R.id.tv_user_medicine_record);
        containerView().addView(view);

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

            switch (view.getId()) {
                case R.id.tv_use_medicine_child_edit_so:
                    remindUser(position);
                    break;
                default:
                    break;
            }
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

    /**
     * 提醒用户
     *
     * @param position
     */
    private void remindUser(int position) {
        Call<String> requestCall = DataManager.remindUser(mList.get(position).getId(), (call, response) -> {
            TipUtils.getInstance().showToast(getPageContext(), response.msg);
        }, (Call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
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
