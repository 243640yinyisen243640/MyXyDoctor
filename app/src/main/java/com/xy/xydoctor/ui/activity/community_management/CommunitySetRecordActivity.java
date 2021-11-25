package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.UserRecordDataInfo;
import com.xy.xydoctor.constant.DataFormatManager;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.DataUtils;
import com.xy.xydoctor.utils.StatusBarUtils;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.Calendar;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/25 11:31
 * Description: 档案记录
 */
public class CommunitySetRecordActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private ImageView backImageView;

    /**
     * 时间
     */
    private TextView timeTextView;

    private LinearLayout mrmcClickLinearLayout;
    private LinearLayout mrocClickLinearLayout;
    private LinearLayout ormyClickLinearLayout;


    /**
     * 我的建档记录-我的小区-家庭
     */
    private TextView mRecordMCommunityFamilyTextView;
    /**
     * 我的建档记录-我的小区-居民
     */
    private TextView mRecordMCommunityPeopleTextView;
    /**
     * 我的建档记录-其他小区-家庭
     */
    private TextView mRecordOCommunityFamilyTextView;
    /**
     * 我的建档记录-其他小区-居民
     */
    private TextView mRecordOCommunityPeopleTextView;
    /**
     * 其他建档记录-我的小区-家庭
     */
    private TextView oRecordMCommunityFamilyTextView;
    /**
     * 其他建档记录-我的小区-居民
     */
    private TextView oRecordMCommunityPeopleTextView;
    /**
     * 时间
     */
    private String time;

    private UserRecordDataInfo dataInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().topView().removeAllViews();
//        topViewManager().titleTextView().setText(getString(R.string.community_record_statistics));
//        topViewManager().titleTextView().setTextColor(ContextCompat.getColor(getPageContext(), R.color.white));
//        topViewManager().lineViewVisibility(View.GONE);
//        topViewManager().topView().setBackgroundColor(ContextCompat.getColor(getPageContext(), R.color.community_red_title));
        StatusBarUtils.statusBarColor(this, ContextCompat.getColor(getPageContext(), R.color.community_red_title));
        containerView().addView(initView());
        initValues();
        initListener();
        getData();
    }

    private void initValues() {
        time = DataUtils.currentDateString(DataFormatManager.TIME_FORMAT_Y_M);
        timeTextView.setText(time);
    }

    private void initListener() {
        timeTextView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        mrmcClickLinearLayout.setOnClickListener(this);
        mrocClickLinearLayout.setOnClickListener(this);
        ormyClickLinearLayout.setOnClickListener(this);
    }


    private void getData() {
        Call<String> request = DataManager.getRecordData(time, (call, response) -> {
            if (response.code == 200) {
                dataInfo = (UserRecordDataInfo) response.object;
                bindData();
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {

        setTextStyle(dataInfo.getMyHouseCount(), getString(R.string.community_set_record_family), R.color.colorPrimaryDark, mRecordMCommunityFamilyTextView, 20);
        setTextStyle(dataInfo.getMyMemberCount(), getString(R.string.community_set_record_people), R.color.community_statics_green, mRecordMCommunityPeopleTextView, 20);
        setTextStyle(dataInfo.getMyOtherHouseCount(), getString(R.string.community_set_record_family), R.color.colorPrimaryDark, mRecordOCommunityFamilyTextView, 20);
        setTextStyle(dataInfo.getMyOtherMemberCount(), getString(R.string.community_set_record_people), R.color.community_statics_green, mRecordOCommunityPeopleTextView, 20);
        setTextStyle(dataInfo.getOtherHouseCount(), getString(R.string.community_set_record_family), R.color.colorPrimaryDark, oRecordMCommunityFamilyTextView, 20);
        setTextStyle(dataInfo.getOtherMemberCount(), getString(R.string.community_set_record_people), R.color.community_statics_green, oRecordMCommunityPeopleTextView, 20);
    }


    private void setTextStyle(String content, String title, int color, TextView textView, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content).append("\n");
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title);
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(XyScreenUtils.sp2px(getPageContext(), size)), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableStringBuilder);
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_set_record, null);
        backImageView = view.findViewById(R.id.iv_record_static_finish);
        timeTextView = view.findViewById(R.id.tv_mc_time);
        mrmcClickLinearLayout = view.findViewById(R.id.ll_record_my_community_click);
        mrocClickLinearLayout = view.findViewById(R.id.ll_record_other_community_click);
        ormyClickLinearLayout = view.findViewById(R.id.ll_record_or_community_click);
        mRecordMCommunityFamilyTextView = view.findViewById(R.id.tv_mr_mc_family);
        mRecordMCommunityPeopleTextView = view.findViewById(R.id.tv_mr_mc_people);
        mRecordOCommunityFamilyTextView = view.findViewById(R.id.tv_mr_oc_family);
        mRecordOCommunityPeopleTextView = view.findViewById(R.id.tv_mr_oc_people);
        oRecordMCommunityFamilyTextView = view.findViewById(R.id.tv_or_mc_family);
        oRecordMCommunityPeopleTextView = view.findViewById(R.id.tv_or_mc_people);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.tv_mc_time:
                showTimeWindow();
                break;
            case R.id.iv_record_static_finish:
                finish();
                break;
            case R.id.ll_record_my_community_click:
                intent = new Intent(getPageContext(), CommunityUserDateiListActivity.class);
                intent.putExtra("datatime", time);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.ll_record_other_community_click:
                intent = new Intent(getPageContext(), CommunityUserDateiListActivity.class);
                intent.putExtra("datatime", time);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.ll_record_or_community_click:
                intent = new Intent(getPageContext(), CommunityUserDateiListActivity.class);
                intent.putExtra("datatime", time);
                intent.putExtra("type", "3");
                startActivity(intent);
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
            String content = DataUtils.convertDateToString(date, DataFormatManager.TIME_FORMAT_Y_M);
            timeTextView.setText(content);
            time = content;
            getData();
        }).setDate(currentDate).setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setSubmitColor(ContextCompat.getColor(getPageContext(), R.color.main_red))
                .setCancelColor(ContextCompat.getColor(getPageContext(), R.color.black_text))
                .build();
        timePickerView.show();

    }
}
