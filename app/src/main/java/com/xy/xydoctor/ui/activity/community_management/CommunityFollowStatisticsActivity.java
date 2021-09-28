package com.xy.xydoctor.ui.activity.community_management;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunitySetFollowAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityStaticsInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.customerView.CirclePercentView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/25 11:31
 * Description: 随访统计
 */
public class CommunityFollowStatisticsActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private ImageView backImageView;
    /**
     * 时间 ， 总人数 随访总人数 当前随访人数
     */
    private TextView timeTextView, allPersonTextView, followNumTextView, currentNumTextView;

    /**
     * 随访总数，已完成，未完成，失访
     */
    private TextView followAllTextView, finishFollowTextView, noFollowTextView, lostFollowTextView;


    /**
     * 总达标率，空腹达标率，未空腹达标率
     */
    private CirclePercentView firstCpv, secondCpv, thirdCpv;

    private TextView patientTextView;
    private TextView yearFinishTextView;
    private TextView yearLostTextView;

    private MyListView listView;

    private String time;

    private CommunityStaticsInfo staticsInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)  //指定状态栏颜色,根据情况是否设置
                .init();
        topViewManager().topView().removeAllViews();
        containerView().addView(initView());
        initListener();
        time = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y);
        timeTextView.setText(time+"年");
        getData();

    }

    private void initListener() {
        timeTextView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
    }

    private void getData() {
        Call<String> request = DataManager.getFollowStatics(time, (call, response) -> {
            if (response.code == 200) {
                staticsInfo = (CommunityStaticsInfo) response.object;
                bindData();
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {
        setTextStyle(getString(R.string.community_statics_person_num), staticsInfo.getCommunityUser(), R.color.community_content_black, allPersonTextView, 13);
        setTextStyle(getString(R.string.community_statics_all_num), staticsInfo.getFollowUser(), R.color.community_content_black, followNumTextView, 13);
        setTextStyle(getString(R.string.community_member_count), staticsInfo.getFollowingUser(), R.color.community_content_black, currentNumTextView, 13);

        setTextStyle(getString(R.string.community_statics_all_times), staticsInfo.getFollowNum(), R.color.data_gray_light, followAllTextView, 12);
        setTextStyle(getString(R.string.community_statics_finish_times), staticsInfo.getFollowedNum(), R.color.data_gray_light, finishFollowTextView, 12);
        setTextStyle(getString(R.string.community_statics_no_times), staticsInfo.getUnfollowNum(), R.color.data_gray_light, noFollowTextView, 12);
        setTextStyle(getString(R.string.community_statics_lost_times), staticsInfo.getLostfollowNum(), R.color.data_gray_light, lostFollowTextView, 12);

        setTextStyle1(getString(R.string.community_statics_patient_num), staticsInfo.getCommunityUserPre(), R.color.community_content_black, patientTextView);
        setTextStyle1(getString(R.string.community_statics_year_finish_num), staticsInfo.getFinished(), R.color.community_content_black, yearFinishTextView);
        setTextStyle1(getString(R.string.community_statics_year_lost_num), staticsInfo.getLosted(), R.color.community_content_black, yearLostTextView);

        firstCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getCommunityUserPre(), 0));
        secondCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getFinished(), 0));
        thirdCpv.setPercentage(TurnsUtils.getFloat(staticsInfo.getLosted(), 0));


        setReachTheStandRate();
    }

    private void setReachTheStandRate() {

        List<CommunityStaticsInfo> list = new ArrayList<>();
        CommunityStaticsInfo bean0 = new CommunityStaticsInfo();
        bean0.setTypeName(staticsInfo.getUnthrough());
        bean0.setPosition(0);
        CommunityStaticsInfo bean1 = new CommunityStaticsInfo();
        bean1.setTypeName(staticsInfo.getRejectFollow());
        bean1.setPosition(1);
        CommunityStaticsInfo bean2 = new CommunityStaticsInfo();
        bean2.setTypeName(staticsInfo.getReject());
        bean2.setPosition(2);
        CommunityStaticsInfo bean3 = new CommunityStaticsInfo();
        bean3.setTypeName(staticsInfo.getUnknow());
        bean3.setPosition(4);
        CommunityStaticsInfo bean4 = new CommunityStaticsInfo();
        bean4.setTypeName(staticsInfo.getClosed());
        bean4.setPosition(4);
        list.add(bean0);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);

        listView.setAdapter(new CommunitySetFollowAdapter(getPageContext(), list));
    }

    private void setTextStyle(String content, String title, int color, TextView textView, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content).append("\n");
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title);
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(XyScreenUtils.sp2px(getPageContext(), size)), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableStringBuilder);
    }

    private void setTextStyle1(String content, String title, int color, TextView textView) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content).append("  ");
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title).append("%");
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableStringBuilder);
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_follow_statistics, null);
        backImageView = view.findViewById(R.id.img_statics_top_back);
        timeTextView = view.findViewById(R.id.tv_fs_time);
        allPersonTextView = view.findViewById(R.id.tv_fs_building_all_num);
        followNumTextView = view.findViewById(R.id.tv_fs_building_follow_all_num);
        currentNumTextView = view.findViewById(R.id.tv_fs_building_follow_current_num);
        followAllTextView = view.findViewById(R.id.tv_fs_follow_all_num);
        finishFollowTextView = view.findViewById(R.id.tv_fs_follow_finish_num);
        noFollowTextView = view.findViewById(R.id.tv_fs_follow_no_num);
        lostFollowTextView = view.findViewById(R.id.tv_fs_follow_lost_num);
        firstCpv = view.findViewById(R.id.fs_circle_percent_progress1);
        patientTextView = view.findViewById(R.id.tv_fs_follow_patient_num);
        secondCpv = view.findViewById(R.id.fs_circle_percent_progress2);
        yearFinishTextView = view.findViewById(R.id.tv_fs_follow_year_finish_num);
        thirdCpv = view.findViewById(R.id.fs_circle_percent_progress3);
        yearLostTextView = view.findViewById(R.id.tv_fs_follow_year_lost_num);
        listView = view.findViewById(R.id.rv_fs_follow_rate);

        listView.setItemsCanFocus(false);

        firstCpv.setPercentage(50);//传入百分比的值
        secondCpv.setPercentage(30);//传入百分比的值
        thirdCpv.setPercentage(20);//传入百分比的值
        firstCpv.setRotation(-50 * 360 / 200);
        secondCpv.setRotation(-30 * 360 / 200 - 90);
        thirdCpv.setRotation(-20 * 360 / 200);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fs_time:
                showTimeWindow();
                break;
            case R.id.img_statics_top_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showTimeWindow() {
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        startDate.set(currentYear - 120, 0, 1, 0, 0);
        TimePickerView timePickerView = new TimePickerBuilder(getPageContext(), (date, v) -> {
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y);
            timeTextView.setText(content);
            time = content;
            getData();
        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, false, false, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.black_text))
                //                .isDialog(true)
                //                .setDecorView(allLiner)
                .build();
        //        //设置dialog弹出位置
        //        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        //        params.leftMargin = 0;
        //        params.rightMargin = 0;
        //        ViewGroup contentContainer = timePickerView.getDialogContainerLayout();
        //        contentContainer.setLayoutParams(params);
        //        timePickerView.getDialog().getWindow().setGravity(Gravity.BOTTOM);//可以改成Bottom
        //        timePickerView.getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerView.show();

    }

}
