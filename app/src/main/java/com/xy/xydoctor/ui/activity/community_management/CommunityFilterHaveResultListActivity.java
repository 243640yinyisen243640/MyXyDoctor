package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityFilterListAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterChildInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 类描述：筛选有数据时
 * * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class CommunityFilterHaveResultListActivity extends XYSoftUIBaseActivity {

    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<CommunityFilterChildInfo> mList = new ArrayList<>();
    private List<CommunityFilterChildInfo> mTempList;
    private int mPageIndex = 1, mPageSize = 6, mPageCount = 0;
    private CommunityFilterListAdapter mAdapter;
    private boolean mIsLoading = false;

    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;

    /**
     * 筛选结果
     */
    private TextView haveResultTextView;

    /**
     * 是否空房间
     */
    private String isEmpty;
    /**
     * 小区id，全部小区传0
     */
    private String com_id;
    /**
     * 性别
     * 1：男
     * 2：女
     */
    private String sex;
    /**
     * 年龄最小值
     */
    private String age_min;
    /**
     * 年龄最大值
     */
    private String age_max;
    /**
     * 其他信息(英文逗号分隔)
     * 1：残疾，
     * 2：精神问题
     * 3：党员
     * 4：重点关注
     * 5：死亡
     */
    private String other = "";
    /**
     * 疾病类型(英文逗号分隔)
     * 1：糖尿病
     * 2：高血压
     * 3：超重/肥胖
     * 4：冠心病
     * 5：脑卒中
     * 6：脂肪肝
     */
    private String disease = "";

    private String filterInfoStr;

    private CommunityFilterInfo filterInfo;

    private CommunityFilterInfo resultResponseInfo;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topViewManager().titleTextView().setText("筛选列表");
        topViewManager().moreTextView().setText(R.string.base_filter);
        topViewManager().moreTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.community_filter_red, 0);
        topViewManager().moreTextView().setCompoundDrawablePadding(2);

        topViewManager().moreTextView().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), CommunityFilterActivity.class);
            intent.putExtra("type", "2");
            startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
        });
        initView();
        initValue();
        initLinstener();
        filterInfoStr = getIntent().getStringExtra("info");
        filterInfo = new Gson().fromJson(filterInfoStr, CommunityFilterInfo.class);
        isEmpty = filterInfo.getIsempty();
        com_id = filterInfo.getCom_id();
        sex = filterInfo.getSex();
        age_min = filterInfo.getAge_min();
        age_max = filterInfo.getAge_max();
        if (filterInfo.getDiseaseTypeInfos() != null && filterInfo.getDiseaseTypeInfos().size() != 0) {

            StringBuilder stringBuilderDisease = new StringBuilder();
            for (int i = 0; i < filterInfo.getDiseaseTypeInfos().size(); i++) {
                String userid = filterInfo.getDiseaseTypeInfos().get(i).getCheckID();
                stringBuilderDisease.append(userid);
                stringBuilderDisease.append(",");

            }
            //截取最后,
            disease = stringBuilderDisease.substring(0, stringBuilderDisease.length() - 1);
        }

        if (filterInfo.getOtherList() != null && filterInfo.getOtherList().size() != 0) {
            StringBuilder stringBuilderOther = new StringBuilder();
            for (int i = 0; i < filterInfo.getOtherList().size(); i++) {
                String userid = filterInfo.getOtherList().get(i).getCheckID();
                stringBuilderOther.append(userid);
                stringBuilderOther.append(",");

            }
            //截取最后
            other = stringBuilderOther.substring(0, stringBuilderOther.length() - 1);
        }


        onPageLoad();
    }


    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Call<String> requestCall = DataManager.getFilterList(isEmpty, com_id, sex, age_min, age_max, other, disease, mPageIndex + "",
                (call, response) -> {
                    mIsLoading = false;
                    if (1 != mPageIndex) {
                        mRefreshLayout.finishLoadMore();
                    } else {
                        mRefreshLayout.finishRefresh();
                    }
                    if (200 == response.code) {
                        resultResponseInfo = (CommunityFilterInfo) response.object;
                        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                        stringBuilder.append("已筛选");
                        int start = stringBuilder.length();
                        stringBuilder.append(resultResponseInfo.getTotalCount());
                        int end = stringBuilder.length();
                        stringBuilder.append("个");
                        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.main_red)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                        haveResultTextView.setText(stringBuilder);
                        mTempList = resultResponseInfo.getLists();
                        mPageCount = mTempList == null ? 0 : mTempList.size();
                        if (1 == mPageIndex) {
                            if (mList == null) {
                                mList = new ArrayList<>();
                            } else {
                                mList.clear();
                            }
                            mList.addAll(mTempList);
                            if (mAdapter == null) {
                                mAdapter = new CommunityFilterListAdapter(getPageContext(), mList, isEmpty, new OnItemClickListener());
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

    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_filter_list, null);
        mRefreshLayout = getViewByID(view, R.id.refreshLayout_filter);
        mRecyclerView = getViewByID(view, R.id.rv_live_filter);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate_filter);
        stateTextView = getViewByID(view, R.id.tv_no_data_filter);
        haveResultTextView = getViewByID(view, R.id.tv_filter_have_result);
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
            Intent intent;

            //一级的点击事件
            switch (view.getId()) {
                case R.id.ll_filter_click:
                    intent = new Intent(getPageContext(), CommunityUserInfoActivity.class);
                    intent.putExtra("userid", mList.get(position).getUserid());
                    startActivity(intent);
                    break;
                case R.id.tv_filter_empty:
                    intent = new Intent(getPageContext(), CommunityFollowUpBuildingActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;

            }
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_REFRESH) {
                if (data != null) {
                    String infoStr = data.getStringExtra("info");
                    CommunityFilterInfo info = new Gson().fromJson(infoStr, CommunityFilterInfo.class);
                    isEmpty = info.getIsempty();
                    com_id = info.getCom_id();
                    sex = info.getSex();
                    age_min = info.getAge_min();
                    age_max = info.getAge_max();
                    if (filterInfo.getDiseaseTypeInfos() != null && filterInfo.getDiseaseTypeInfos().size() != 0) {

                        StringBuilder stringBuilderDisease = new StringBuilder();
                        for (int i = 0; i < filterInfo.getDiseaseTypeInfos().size(); i++) {
                            String userid = filterInfo.getDiseaseTypeInfos().get(i).getCheckID();
                            stringBuilderDisease.append(userid);
                            stringBuilderDisease.append(",");

                        }
                        //截取最后,
                        disease = stringBuilderDisease.substring(0, stringBuilderDisease.length() - 1);
                    }

                    if (filterInfo.getOtherList() != null && filterInfo.getOtherList().size() != 0) {
                        StringBuilder stringBuilderOther = new StringBuilder();
                        for (int i = 0; i < filterInfo.getOtherList().size(); i++) {
                            String userid = filterInfo.getOtherList().get(i).getCheckID();
                            stringBuilderOther.append(userid);
                            stringBuilderOther.append(",");

                        }
                        //截取最后
                        other = stringBuilderOther.substring(0, stringBuilderOther.length() - 1);
                    }
                    mPageIndex = 1;
                    onPageLoad();
                }

            }
        }
    }
}
