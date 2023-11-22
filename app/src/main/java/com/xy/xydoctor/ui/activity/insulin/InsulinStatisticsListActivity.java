package com.xy.xydoctor.ui.activity.insulin;

import android.os.Bundle;
import android.view.View;
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
 * 描述:当前佩戴人数
 */
public class InsulinStatisticsListActivity extends XYSoftUIBaseActivity {
    private TextView tvNoData;
    private RecyclerView rvStatistics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("当前佩戴人数");
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

            }

            @Override
            public void adapterClickListener(int position, int index, View view) {

            }
        });
        rvStatistics.setAdapter(adapter);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_insulin_statistics_list, null);
        tvNoData = view.findViewById(R.id.tv_insulin_statistics_list_only_no);
        rvStatistics = view.findViewById(R.id.rv_insulin_statistics_list_only);
        return view;
    }
}
