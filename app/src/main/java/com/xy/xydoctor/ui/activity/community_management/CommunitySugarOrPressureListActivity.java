package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunitySugarOrPressureListAdapter;
import com.xy.xydoctor.base.activity.WebViewActivity;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import java.util.List;

import retrofit2.Call;

/**
 * 类描述：血糖血压随访  1：血糖 2：血压 血糖随访血压随访
 * 类传参：
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class CommunitySugarOrPressureListActivity extends XYSoftUIBaseActivity {
    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private RecyclerView mRecyclerView;

    private NestedScrollView presentNestedSrcollView;
    private TextView stateTextView;

    private String type;

    private List<SugarOrPressureInfo> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            topViewManager().titleTextView().setText(R.string.user_info_blood_sugar_follow_up);
        } else {
            topViewManager().titleTextView().setText(R.string.user_info_blood_pressure);
        }

        initView();
        initValue();
        getData();
    }

    private void getData() {
        String userid = getIntent().getStringExtra("userid");
        //1：血糖  2：血压

        Call<String> requestCall = DataManager.getSugarOrPressureList(userid, type, (call, response) -> {
            if (response.code == 200) {
                mRecyclerView.setVisibility(View.VISIBLE);
                presentNestedSrcollView.setVisibility(View.GONE);
                list = (List<SugarOrPressureInfo>) response.object;
                CommunitySugarOrPressureListAdapter adapter = new CommunitySugarOrPressureListAdapter(getPageContext(), list, new OnItemClickListener());
                mRecyclerView.setAdapter(adapter);
            } else if (response.code == 30002) {
                mRecyclerView.setVisibility(View.GONE);
                presentNestedSrcollView.setVisibility(View.VISIBLE);
                stateTextView.setText(R.string.no_data);
            } else {
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_sugar_pressure_list, null);
        mRecyclerView = getViewByID(view, R.id.rv_live_sugar_pressure);
        presentNestedSrcollView = getViewByID(view, R.id.nsv_present_nodate_sugar_pressure);
        stateTextView = getViewByID(view, R.id.tv_no_data_sugar_pressure);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_REFRESH) {
                getData();
            }
        }
    }

    private class OnItemClickListener implements IAdapterViewClickListener {
        @Override
        public void adapterClickListener(int position, View view) {

            //一级的点击事件
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            switch (view.getId()) {
                case R.id.ll_sugar_or_pressure_child_click:
                    if (!"待开启".equals(list.get(position).getList().get(index).getStatus())) {
                        Intent intent = new Intent(getPageContext(), WebViewActivity.class);
                        intent.putExtra("url", list.get(position).getList().get(index).getLink());
                        intent.putExtra("id", list.get(position).getList().get(index).getId());
                        intent.putExtra("status", list.get(position).getList().get(index).getId());
                        if ("1".equals(type)) {
                            intent.putExtra("title", getResources().getString(R.string.user_info_blood_sugar_follow_up));
                        } else {
                            intent.putExtra("title", getResources().getString(R.string.user_info_blood_pressure));
                        }

                        startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
                    }

                    break;
                default:
                    break;
            }

        }
    }


}
