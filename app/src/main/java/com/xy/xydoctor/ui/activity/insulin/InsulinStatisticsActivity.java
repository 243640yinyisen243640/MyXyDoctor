package com.xy.xydoctor.ui.activity.insulin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.insulin.InsulinStatisticsAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.ArrayList;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:胰岛素泵统计
 */
public class InsulinStatisticsActivity extends XYSoftUIBaseActivity {
    private ImageView ivBack;
    private TextView tvNum;
    private TextView tvNoData;
    private RecyclerView rvStatistics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().topView().removeAllViews();
        containerView().addView(initView());
        initValues();
    }

    private void initValues() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvStatistics.setLayoutManager(layoutManager);
        //解决底部滚动到顶部时，顶部item上方偶尔会出现一大片间隔的问题
        rvStatistics.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[2];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
        InsulinStatisticsAdapter adapter = new InsulinStatisticsAdapter(getPageContext(), new ArrayList<>(), new IAdapterViewClickListener() {
            @Override
            public void adapterClickListener(int position, View view) {
                switch (view.getId()) {
                    case R.id.ll_insulin_statistics_click:
                        Intent intent = new Intent(getPageContext(), InsulinInfusionRecordListActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        rvStatistics.setAdapter(adapter);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_statistics, null);
        ivBack = view.findViewById(R.id.iv_insulin_statistics_back);
        tvNum = view.findViewById(R.id.tv_insulin_statistics_with_num);
        rvStatistics = view.findViewById(R.id.rv_insulin_statistics_list);
        tvNoData = view.findViewById(R.id._insulin_statistics_list_no);
        tvNum.setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), InsulinStatisticsListActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
