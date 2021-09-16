package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.net.Uri;
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
import com.xy.xydoctor.adapter.community_manager.UserMedicineListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/16 16:36
 * Description: 用药提醒
 */
public class CommunityUserMedicineActivity extends XYSoftUIBaseActivity {

    private UserMedicineListAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<CommunityUseMedicineInfo> mList = new ArrayList<>();
    private List<CommunityUseMedicineInfo> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private boolean mIsLoading = false;
    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;

    private static final int REQUEST_CODE_FOR_REFRESH = 10;

    private String userid = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = getIntent().getStringExtra("userid");
        topViewManager().titleTextView().setText("用药提醒");
        containerView().addView(initView());

        initValue();
        initLinstener();
        onPageLoad();
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_user_medicine, null);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout_data_remind);
        mRecyclerView = getViewByID(view, R.id.rv_live_data_remind);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate_remind);
        stateTextView = getViewByID(view, R.id.tv_no_data_remind);
        return view;
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

    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Call<String> requestCall = DataManager.useMedicineRemind(userid, mPageIndex + "",
                (call, response) -> {
                    mIsLoading = false;
                    if (1 != mPageIndex) {
                        mRefreshLayout.finishLoadMore();
                    } else {
                        mRefreshLayout.finishRefresh();
                    }
                    if (200 == response.code) {
                        mTempList = (List<CommunityUseMedicineInfo>) response.object;
                        mPageCount = mTempList == null ? 0 : mTempList.size();
                        if (1 == mPageIndex) {
                            if (mList == null) {
                                mList = new ArrayList<>();
                            } else {
                                mList.clear();
                            }
                            mList.addAll(mTempList);
                            if (mAdapter == null) {
                                mAdapter = new UserMedicineListAdapter(getPageContext(), mList, new OnItemClickListener());
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

    private class OnItemClickListener implements IAdapterViewClickListener {

        @Override
        public void adapterClickListener(int position, View view) {

            switch (view.getId()) {

                default:
                    break;
            }
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            switch (view.getId()) {
                case R.id.tv_user_child_medicine_phone:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + mList.get(position).getPharmacys().get(index).getTel());
                    intent.setData(data);
                    startActivity(intent);
                    break;
                case R.id.tv_use_medicine_child_edit:

                    Intent intent1 = new Intent(getPageContext(), CommunityMedicineAddActivity.class);
                    intent1.putExtra("pharmacy_id", mList.get(position).getPharmacys().get(index).getId());
                    intent1.putExtra("userid", userid);
                    intent1.putExtra("type", "2");
                    startActivityForResult(intent1, REQUEST_CODE_FOR_REFRESH);
                    break;
                case R.id.tv_use_medicine_child_finish:
                    finishWaiting(position, index);
                    break;
                case R.id.tv_use_medicine_child_remind_user:
                    remindUser(position, index);
                    break;


                default:
                    break;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FOR_REFRESH:
                    mPageIndex = 1;
                    onPageLoad();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 完成代办
     *
     * @param position
     * @param index
     */
    private void finishWaiting(int position, int index) {
        Call<String> requestCall = DataManager.finishWaiting(mList.get(position).getPharmacys().get(index).getId(), (call, response) -> {
            if (response.code == 200) {
                mPageIndex = 1;
                onPageLoad();
            }
        }, (Call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    /**
     * 提醒用户
     *
     * @param position
     * @param index
     */
    private void remindUser(int position, int index) {
        Call<String> requestCall = DataManager.remindUser(mList.get(position).getPharmacys().get(index).getId(), (call, response) -> {
            if (response.code == 200) {
                //                mPageIndex = 1;
                //                onPageLoad();
                //                mAdapter.returnAdapter().notifyDataSetChanged();
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (Call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

}
