package com.xy.xydoctor.ui.fragment.community_management;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.community_management.CommunityDataAbnormalActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityFilterActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityFollowupAgentListActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityFollowupAgentSearchListActivity;
import com.xy.xydoctor.ui.activity.community_management.UserAddActivity;

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
     * 小区数量点击
     */
    @BindView(R.id.ll_community_filter_building_num)
    LinearLayout buildingNumLinearLayout;
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
        getCommunityInfo();

    }

    private void initListener() {
        addUserTextView.setOnClickListener(this);
        buildingNumLinearLayout.setOnClickListener(this);
        toBeDoneNumTextView.setOnClickListener(this);
        abnormalDataTextView.setOnClickListener(this);
        followUpStatisticsTextView.setOnClickListener(this);
        numStatisticsTextView.setOnClickListener(this);
    }

    /**
     * 获取详细信息
     */
    private void getCommunityInfo() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_community_add_user:
                startActivity(new Intent(getPageContext(), UserAddActivity.class));
                break;
            case R.id.ll_community_filter_building_num:
                //小区数量  进入到当前社区的小区列表 ，从点击到进入楼栋模型，展示当前小区的楼栋数
                //现在传的type值只是我自己定义的，看接口如何让传，在改
                intent = new Intent(getPageContext(), CommunityFollowupAgentListActivity.class);
                intent.putExtra("type", "1");
//                intent.putExtra("commuinityName", );
                startActivity(intent);
                break;
            case R.id.tv_community_follow_up_to_be_done:
                //随访代办  进入到当前社区的有随访的列表 ，点击条目进入楼栋模型，展示当前小区的楼栋数,
                intent = new Intent(getPageContext(), CommunityFollowupAgentListActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.tv_community_search:
                intent = new Intent(getPageContext(), CommunityFollowupAgentSearchListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_community_filter:
                intent = new Intent(getPageContext(), CommunityFilterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_community_abnormal_data:
                intent = new Intent(getPageContext(), CommunityDataAbnormalActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}