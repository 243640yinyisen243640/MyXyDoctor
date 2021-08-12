package com.xy.xydoctor.ui.fragment.community_management;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.community_management.SettingsPropertyActivity;

import butterknife.BindView;

/**
 * Author: LYD
 * Date: 2021/8/10 16:36
 * Description: 社区管理
 */
public class FragmentCommunityManagement extends BaseFragment implements View.OnClickListener {

    /**
     * 筛选
     */
    @BindView(R.id.tv_community_filter)
    TextView filterTextView;
    /**
     * 搜索
     */
    @BindView(R.id.tv_community_search)
    TextView searchTextView;
    /**
     * 所在位置
     */
    @BindView(R.id.tv_community_location)
    TextView locationTextView;
    /**
     * 添加用户
     */
    @BindView(R.id.tv_community_add_user)
    TextView addUserTextView;
    /**
     * 小区数量
     */
    @BindView(R.id.tv_community_num)
    TextView communityNumTextView;

    /**
     * 小区数量
     */
    @BindView(R.id.tv_community_building_num)
    TextView buildingNumTextView;
    /**
     * 用户数量
     */
    @BindView(R.id.tv_community_person_num)
    TextView personNumTextView;
    /**
     * 随访代办
     */
    @BindView(R.id.tv_community_follow_up_to_be_done)
    TextView toBeDoneNumTextView;
    /**
     * 数据异常
     */
    @BindView(R.id.tv_community_abnormal_data)
    TextView abnormalDataTextView;
    /**
     * 用药提醒
     */
    @BindView(R.id.tv_community_medication_reminder)
    TextView medicationReminderTextView;
    /**
     * 随访统计
     */
    @BindView(R.id.tv_community_follow_up_statistics)
    TextView followUpStatisticsTextView;
    /**
     * 数据统计
     */
    @BindView(R.id.tv_community_num_statistics)
    TextView numStatisticsTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_manager;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
        initListener();
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)  //指定状态栏颜色,根据情况是否设置
                .init();

    }

    private void initListener() {
        addUserTextView.setOnClickListener(this);
    }

    private void getCommunityInfo() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_community_add_user:
                Intent intent = new Intent(getPageContext(), SettingsPropertyActivity.class);

                startActivity(intent);
                break;
            default:
                break;
        }
    }
}