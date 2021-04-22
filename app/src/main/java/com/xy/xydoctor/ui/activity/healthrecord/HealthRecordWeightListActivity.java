package com.xy.xydoctor.ui.activity.healthrecord;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wei.android.lib.colorview.view.ColorRelativeLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.fragment.WeightChartFragment;
import com.xy.xydoctor.ui.fragment.WeightListFragment;
import com.xy.xydoctor.view.popup.SlideFromTopPopup;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  减重列表
 * 作者: LYD
 * 创建日期: 2020/5/25 15:10
 */
public class HealthRecordWeightListActivity extends BaseHideLineActivity {
    @BindView(R.id.bt_back_weight)
    Button btBackWeight;
    @BindView(R.id.tv_title_weight)
    TextView tvTitleWeight;
    @BindView(R.id.img_weight_list)
    ImageView imgWeightList;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.img_weight_chart)
    ImageView imgWeightChart;
    @BindView(R.id.ll_more)
    ColorRelativeLayout llMore;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    //
    SlideFromTopPopup popu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_record_weight_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        tvTitleWeight.setText("体重记录");
        addFirstFragment();
        initPopup();
    }

    private void initPopup() {
        popu = new SlideFromTopPopup(getPageContext(), getIntent().getStringExtra("userid"));
    }

    private void addFirstFragment() {
        String userId = getIntent().getStringExtra("userid");
        Bundle bundle = new Bundle();
        bundle.putString("userid", userId);
        WeightListFragment weightListFragment = new WeightListFragment();
        weightListFragment.setArguments(bundle);
        FragmentUtils.replace(getSupportFragmentManager(), weightListFragment, R.id.fl_content, false);
    }


    @OnClick({R.id.bt_back_weight, R.id.img_weight_list, R.id.img_weight_chart, R.id.tv_title_weight})
    public void onViewClicked(View view) {
        String userId = getIntent().getStringExtra("userid");
        Bundle bundle = new Bundle();
        bundle.putString("userid", userId);
        switch (view.getId()) {
            case R.id.bt_back_weight:
                finish();
                break;
            case R.id.img_weight_list:
                imgWeightList.setImageResource(R.drawable.weight_list_check);
                imgWeightChart.setImageResource(R.drawable.weight_chart_uncheck);
                WeightListFragment weightListFragment = new WeightListFragment();
                weightListFragment.setArguments(bundle);
                FragmentUtils.replace(getSupportFragmentManager(), weightListFragment, R.id.fl_content, false);
                break;
            case R.id.img_weight_chart:
                imgWeightList.setImageResource(R.drawable.weight_list_uncheck);
                imgWeightChart.setImageResource(R.drawable.weight_chart_check);
                WeightChartFragment weightChartFragment = new WeightChartFragment();
                weightChartFragment.setArguments(bundle);
                FragmentUtils.replace(getSupportFragmentManager(), weightChartFragment, R.id.fl_content, false);
                break;
            case R.id.tv_title_weight:
                dismissPopu();
                break;
        }
    }

    /**
     * 隐藏popu
     */
    private void dismissPopu() {
        //        if (!popu.isShowing())
        //            startShowArrowAnima();
        popu.showPopupWindow(tvTitleWeight);
    }
}
