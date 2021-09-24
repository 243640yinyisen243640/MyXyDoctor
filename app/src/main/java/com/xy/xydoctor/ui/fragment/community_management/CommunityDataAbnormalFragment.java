package com.xy.xydoctor.ui.fragment.community_management;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.DataAbnormalListAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalChildInfo;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IACommunityFilterChoose;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.ui.activity.community_management.CommunityDataAbnormalActivity;
import com.xy.xydoctor.ui.activity.community_management.DataAbnormalRemindListActivity;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.view.popup.DataAbnormalPopup1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD  type   1：血压 2：血糖
 * Date: 2021/8/16 16:36
 * Description: 数据异常
 */
public class CommunityDataAbnormalFragment extends XYBaseFragment implements View.OnClickListener, IACommunityFilterChoose {

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

    /**
     * activity里面的全选按钮
     */
    private TextView checkTextView;
    private TextView sureTextView;

    private View checkView;


    public static CommunityDataAbnormalFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        CommunityDataAbnormalFragment fragment = new CommunityDataAbnormalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public List<DataAbnormalInfo> checkList() {
        return mList;
    }

    /**
     * 全选按钮
     */
    private void initCheckView() {
        if (checkView == null) {
            checkView = View.inflate(getPageContext(), R.layout.include_data_abnormal_check_all, null);
            checkTextView = checkView.findViewById(R.id.tv_data_abnormal_check_all_click);
            sureTextView = checkView.findViewById(R.id.tv_data_abnormal_check_all_sure);

            checkTextView.setOnClickListener(v -> {

                if (checkTextView.isSelected()) {
                    checkTextView.setSelected(false);
                    setUnCheckAll();
                } else {
                    checkTextView.setSelected(true);
                    setCheckAll();
                }
            });

            sureTextView.setOnClickListener(v -> {
                //这个是点击确定按钮应该走的逻辑
                List<DataAbnormalChildInfo> checkList = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    for (int j = 0; j < mList.get(i).getCommunityUser().size(); j++) {
                        if (mList.get(i).getCommunityUser().get(j).isSelected()) {
                            checkList.add(mList.get(i).getCommunityUser().get(j));
                        }
                    }
                }
                if (checkList.size() == 0) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.you_can_not_choose_person);
                } else {
                    saveData(checkList);
                }
            });
        } else {
            checkView.setVisibility(View.VISIBLE);
        }
        if (containerView().indexOfChild(checkView) != -1) {
            containerView().removeView(checkView);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        containerView().addView(checkView, params);

    }

    /**
     * 上传数据
     *
     * @param checkList
     */
    private void saveData(List<DataAbnormalChildInfo> checkList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < checkList.size(); i++) {
            String userid = checkList.get(i).getUserid();
            stringBuilder.append(userid);
            stringBuilder.append(",");

        }
        //截取最后,
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);

        Call<String> requestCall = DataManager.loadCheckList(substring, type, (call, response) -> {
            if (response.code == 200) {
                mPageIndex = 1;
                onPageLoad();
            }
        }, (call, t) -> {
            ToastUtils.showShort(getString(R.string.network_error));
        });
    }

    public void setCheckAllIsVisible() {
        initCheckView();
    }

    @Override
    protected void onCreate() {
        topViewManager().topView().removeAllViews();
        //        Calendar ca = Calendar.getInstance();
        //        ca.add(Calendar.MONTH, -1);
        //
        //        Date lastMonth = ca.getTime();
        //        String s = DataUtils.convertDateToString(lastMonth, DataFormatManager.TIME_FORMAT_M);
        starttime = DataUtils.getLastMonthTime();
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
                    CommunityDataAbnormalActivity activity = (CommunityDataAbnormalActivity) getActivity();
                    if (200 == response.code) {
                        activity.topTextView().setVisibility(View.VISIBLE);
                        activity.topTextView().setText("处理");
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
                        activity.topTextView().setVisibility(View.GONE);
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
        mRefreshLayout.setEnableRefresh(false);
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

    /**
     * 设置全选
     */
    public void setCheckAll() {

        //在activity 里面点击处理，下面会出现全选按钮，列表会出现让选择的按钮，点击全选 会走这个方法，这个方法是让让一级二级列表全部选中
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(true);
            for (int j = 0; j < mList.get(i).getCommunityUser().size(); j++) {
                mList.get(i).getCommunityUser().get(j).setSelected(true);
            }
        }
        mAdapter.notifyDataSetChanged();
        mAdapter.adapter().notifyDataSetChanged();
    }

    /**
     * 设置未全选
     */
    public void setUnCheckAll() {

        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(false);
            for (int j = 0; j < mList.get(i).getCommunityUser().size(); j++) {
                mList.get(i).getCommunityUser().get(j).setSelected(false);
            }
        }
        mAdapter.notifyDataSetChanged();
        mAdapter.adapter().notifyDataSetChanged();
    }

    /**
     * 在activity里面，点击处理，列表会显示选择按钮
     */
    public void setCheckIsVisible() {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setCheck(!mList.get(i).isCheck());
        }

        for (int i = 0; i < mList.size(); i++) {
            for (int j = 0; j < mList.get(i).getCommunityUser().size(); j++) {
                mList.get(i).getCommunityUser().get(j).setCheck(!mList.get(i).getCommunityUser().get(j).isCheck());
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setDataRefresh() {
        mPageIndex = 1;
        checkView.setVisibility(View.GONE);
        onPageLoad();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_data_abnormal_down:
                showMenuWindow();

                break;
            default:
                break;
        }
    }


    /**
     * 展示筛选的pop
     */
    private void showMenuWindow() {
        popu = new DataAbnormalPopup1(getPageContext(), type, object -> {

            int position = (int) object;
            switch (position) {


                default:
                    break;
            }

        });
        popu.setOnChooseOkListener(this);
        if (!popu.isShowing()) {
            popu.showAsDropDown(showLinearLayout);
        }
    }

    @Override
    public void IAFollowUpChoose(String startTime, String endTime, String style, String styleName, String startSugar, String endSugar, String status, String statusName) {
        this.starttime = startTime;
        this.endtime = endTime;
        this.startSugar = startSugar;
        this.endSugar = endSugar;
        this.status = status;

        Log.i("yys", "statusName==" + statusName + "styleName==" + styleName);
        secondTextView.setText(statusName);
        CommunityDataAbnormalActivity activity = (CommunityDataAbnormalActivity) getActivity();
        TextView more = activity.topTextView();
        if ("未处理".equals(statusName)) {
            more.setVisibility(View.VISIBLE);
        } else {
            more.setVisibility(View.GONE);
        }

        if ("1".equals(style) || "2".equals(style) || "3".equals(style) || "4".equals(style)) {
            this.style = style;
            firstTextView.setText(styleName);
        } else {
            this.style = "5";
            firstTextView.setText(startSugar + "-" + endSugar);
        }

        popu.dismiss();
        onPageLoad();
    }

    private class OnItemClickListener implements IAdapterViewClickListener {

        @Override
        public void adapterClickListener(int position, View view) {

            switch (view.getId()) {
                //一级的点击事件
                case R.id.tv_data_abnormal_check:
                    //点击一级
                    if (mList.get(position).isSelected()) {
                        //点击时是选中
                        for (int i = 0; i < mList.get(position).getCommunityUser().size(); i++) {
                            mList.get(position).getCommunityUser().get(i).setSelected(false);
                        }
                        mList.get(position).setSelected(false);
                    } else {
                        //点击时是未选中
                        for (int i = 0; i < mList.get(position).getCommunityUser().size(); i++) {
                            mList.get(position).getCommunityUser().get(i).setSelected(true);
                        }
                        mList.get(position).setSelected(true);
                    }
                    for (int i = 0; i < mList.size(); i++) {
                        //判断是否全选
                        if (!mList.get(i).isSelected()) {
                            //没有全选
                            checkTextView.setSelected(false);
                            break;
                        }
                        //全选
                        checkTextView.setSelected(true);
                    }
                    mAdapter.notifyDataSetChanged();
                    mAdapter.adapter().notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            switch (view.getId()) {
                case R.id.tv_data_abnormal_child_check:
                    //二级的点击事件
                    //二级按钮改变
                    mList.get(position).getCommunityUser().get(index).setSelected(!mList.get(position).getCommunityUser().get(index).isSelected());
                    //判断二级按钮是否选中
                    for (int i = 0; i < mList.get(position).getCommunityUser().size(); i++) {
                        if (!mList.get(position).getCommunityUser().get(i).isSelected()) {
                            //没有完全选中
                            mList.get(position).setSelected(false);
                            break;
                        }
                        //完全选中
                        mList.get(position).setSelected(true);
                    }
                    for (int i = 0; i < mList.size(); i++) {
                        //判断是否全选
                        if (!mList.get(i).isSelected()) {
                            //没有全选
                            checkTextView.setSelected(false);
                            break;
                        }
                        //全选
                        checkTextView.setSelected(true);
                    }
                    mAdapter.notifyDataSetChanged();
                    mAdapter.adapter().notifyDataSetChanged();
                    break;
                case R.id.tv_data_abnormal_child_phone_img:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + mList.get(position).getCommunityUser().get(index).getTel());
                    intent.setData(data);
                    startActivity(intent);
                    break;
                case R.id.ll_data_abnormal_child_click:
                    Intent intent1 = new Intent(getPageContext(), DataAbnormalRemindListActivity.class);
                    intent1.putExtra("type", type);
                    intent1.putExtra("sugarOrPressureList", (Serializable) mList.get(position).getCommunityUser().get(index).getList());
                    startActivity(intent1);
                    break;
                default:
                    break;

            }
        }
    }
}
