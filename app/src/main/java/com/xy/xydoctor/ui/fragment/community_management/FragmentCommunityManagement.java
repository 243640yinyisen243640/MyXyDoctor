package com.xy.xydoctor.ui.fragment.community_management;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SPStaticUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.CommunityManagerInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.ui.activity.community_management.CommunityDataAbnormalActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityFilterActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityFollowupAgentListActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityFollowupAgentSearchListActivity;
import com.xy.xydoctor.ui.activity.community_management.CommunityUserMedicineActivity;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/10 16:36
 * Description: 社区管理
 */
public class FragmentCommunityManagement extends BaseFragment {

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
     * 小区楼栋数
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
    @BindView(R.id.ll_community_wait_things_title)
    LinearLayout followUpStatisticsTitleLinearLayout;
    @BindView(R.id.ll_community_wait_things_content)
    LinearLayout followUpStatisticsContentLinearLayout;
    /**
     * 数据统计
     */
    @BindView(R.id.tv_community_wait_things_statics_title)
    TextView numStatisticsTitleTextView;
    @BindView(R.id.community_wait_things_statics_content)
    LinearLayout numStatisticsContentLinearLayout;
    @BindView(R.id.tv_community_num_statistics)
    TextView numStatisticsTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_manager;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)  //指定状态栏颜色,根据情况是否设置
                .init();
        initValues();
        getCommunityInfo();

    }

    private void initValues() {
        int type = SPStaticUtils.getInt("docType");
        if (10 == type) {
            followUpStatisticsTitleLinearLayout.setVisibility(View.GONE);
            followUpStatisticsContentLinearLayout.setVisibility(View.GONE);
            numStatisticsTitleTextView.setVisibility(View.GONE);
            numStatisticsContentLinearLayout.setVisibility(View.GONE);
        } else {
            followUpStatisticsTitleLinearLayout.setVisibility(View.VISIBLE);
            followUpStatisticsContentLinearLayout.setVisibility(View.VISIBLE);
            numStatisticsTitleTextView.setVisibility(View.VISIBLE);
            numStatisticsContentLinearLayout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 获取详细信息
     */
    private void getCommunityInfo() {
        Call<String> requestCall = DataManager.getCommunityHomeData((call, response) -> {
            if (response.code == 200) {
                CommunityManagerInfo managerInfo = (CommunityManagerInfo) response.object;
                getWaitingInfo(managerInfo);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }


    private void getWaitingInfo(CommunityManagerInfo managerInfo) {
        Call<String> requestCall = DataManager.getWaitingInfo((call, response) -> {
            if (response.code == 200) {

                String hospitalname = SPStaticUtils.getString("hospitalname");
                locationTextView.setText(hospitalname);
                CommunityManagerInfo communityManagerInfo = (CommunityManagerInfo) response.object;
                communityNumTextView.setText(managerInfo.getCommunityCount());
                setTextStyle(getString(R.string.community_building_count), managerInfo.getBuildingCount(), R.color.community_home_index_gray, buildingNumTextView, 13);
                setTextStyle(getString(R.string.community_person_count), managerInfo.getMemberCount(), R.color.community_home_index_gray, personNumTextView, 13);


                setTextStyle(communityManagerInfo.getFollowCount(), getString(R.string.follow_up_agent_title), R.color.community_home_index_follow, toBeDoneNumTextView, 18);
                setTextStyle(communityManagerInfo.getAbnormalCount(), getString(R.string.community_data_abnormal_title), R.color.main_red, abnormalDataTextView, 18);
                setTextStyle(communityManagerInfo.getReminderCount(), getString(R.string.user_info_medicine_remind), R.color.community_home_index_medicine, medicationReminderTextView, 18);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    /**
     * 设置随访的字样
     *
     * @param content
     * @param title
     * @param color
     */
    private void setTextStyle(String content, String title, int color, TextView textView, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content).append("\n");
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(XyScreenUtils.sp2px(getPageContext(), size)), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableStringBuilder);
    }


    @OnClick({R.id.tv_community_add_user, R.id.ll_community_filter_building_num, R.id.tv_community_follow_up_to_be_done, R.id.tv_community_search, R.id.tv_community_medication_reminder, R.id.tv_community_filter, R.id.tv_community_abnormal_data})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_community_add_user:
                intent = new Intent(getPageContext(), CommunityFollowupAgentListActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.ll_community_filter_building_num:
                //小区数量  进入到当前社区的小区列表 ，从点击到进入楼栋模型，展示当前小区的楼栋数
                //现在传的type值只是我自己定义的，看接口如何让传，在改
                intent = new Intent(getPageContext(), CommunityFollowupAgentListActivity.class);
                intent.putExtra("type", "1");
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
            case R.id.tv_community_medication_reminder:
                intent = new Intent(getPageContext(), CommunityUserMedicineActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}