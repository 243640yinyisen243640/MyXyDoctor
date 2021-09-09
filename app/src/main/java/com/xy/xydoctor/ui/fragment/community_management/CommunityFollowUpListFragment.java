package com.xy.xydoctor.ui.fragment.community_management;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityFollowUpChangeListAdapter;
import com.xy.xydoctor.base.adapter.TabFragmentAdapter;
import com.xy.xydoctor.base.fragment.XYBaseFragment;
import com.xy.xydoctor.bean.community_manamer.FollowUpListAllInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.ui.activity.community_management.CommunityFollowUpActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityNoFinishActivity;
import com.xy.xydoctor.utils.TipUtils;

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
    private RecyclerView mRecyclerView;
    private CommunityFollowUpChangeListAdapter mAdapter;
    /**
     * 1：待随访:2：失访:3：已完成
     */
    private String type;

    private FollowUpListAllInfo allInfo;

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
        getData();
    }

    private void getData() {

        Call<String> requestCall = DataManager.getFollowList(type, (call, response) -> {
            if (200 == response.code) {
                allInfo = (FollowUpListAllInfo) response.object;
                bindData();
            } else {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {
        CommunityFollowUpActivity activity = (CommunityFollowUpActivity) getActivity();
        RadioButton firstRadioButton = activity.showFirst();
        firstRadioButton.setText(getString(R.string.community_wait_follow_up) + "\n" + "(" + allInfo.getFollowing() + ")");
        RadioButton secondRadioButton = activity.showSecond();
        secondRadioButton.setText(getString(R.string.goods_add_server) + "\n" + "(" + allInfo.getLosting() + ")");
        RadioButton thirdRadioButton = activity.showThird();
        thirdRadioButton.setText(getString(R.string.goods_add_spe) + "\n" + "(" + allInfo.getFinished() + ")");
        mAdapter = new CommunityFollowUpChangeListAdapter(getPageContext(), allInfo.getList(), type, new OnItemClickListener());
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_follow_up_list, null);
        mRecyclerView = getViewByID(view, R.id.rv_live_follow);
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


    private class OnItemClickListener implements IAdapterViewClickListener {
        @Override
        public void adapterClickListener(int position, View view) {

            //一级的点击事件
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            //二级的点击事件
            Intent intent;
            switch (view.getId()) {
                case R.id.tv_fuc_child_no_finish:
                    intent = new Intent(getPageContext(), CommunityNoFinishActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("userid", allInfo.getList().get(position).getCommunityUser().get(index).getUserid());
                    startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
                    break;
                case R.id.tv_fuc_child_call_phone:
                    intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + allInfo.getList().get(position).getCommunityUser().get(index).getTel());
                    intent.setData(data);
                    startActivity(intent);
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
                getData();
            }
        }
    }
}
