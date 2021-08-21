package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.gyf.immersionbar.ImmersionBar;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FollowManagementGvAdapter;
import com.xy.xydoctor.adapter.HealthRecordGvAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.view.popup.OnlineTestPopup;

import java.util.ArrayList;

/**
 * Author: LYD
 * Date: 2021/8/13 9:09
 * Description: 个人信息
 */
public class CommunityUserInfoActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    /**
     * 返回键
     */
    private ImageView backImageView;
    /**
     * 头像
     */
    private ImageView headImageView;
    /**
     * 头像
     */
    private ImageView deadImageView;
    /**
     * 姓名
     */
    private TextView nameTextView;
    /**
     * 性别和年龄
     */
    private TextView sexAndAgeTextView;
    /**
     * 第一种病
     */
    private TextView diseaseFirstTextView;
    /**
     * 第二种
     */
    private TextView diseaseSecondTextView;
    /**
     * 设备管理
     */
    private TextView deviceManagerTextView;
    /**
     * 血糖测量
     */
    private TextView sugarTextTextView;
    /**
     * 血压测量
     */
    private TextView pressureTextTextView;
    /**
     * 血糖随访
     */
    private TextView sugarFollowView;
    /**
     * 血压随访
     */
    private TextView pressureFollowTextView;

    /**
     * 健康记录
     */
    private NoConflictGridView gvHealthRecord;
    /**
     * 随访管理
     */
    private NoConflictGridView gvManagementRecord;
    /**
     * 用药提醒
     */
    private LinearLayout medicanLinearLayout;

    private ImageView textImageView;
    private OnlineTestPopup onlineTestPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topViewManager().topView().removeAllViews();

        containerView().addView(initView());
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)  //指定状态栏颜色,根据情况是否设置
                .init();
        initListener();

        initValues();
    }

    private void initListener() {
        backImageView.setOnClickListener(this);
        deviceManagerTextView.setOnClickListener(this);
        medicanLinearLayout.setOnClickListener(this);
    }

    private void initValues() {

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + "");
        }
        String userid = getIntent().getStringExtra("userid");
        HealthRecordGvAdapter adapter = new HealthRecordGvAdapter(Utils.getApp(), R.layout.item_gv_health_record, list, userid);
        gvHealthRecord.setAdapter(adapter);

        ArrayList<String> managementList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            managementList.add(i + "");
        }
        FollowManagementGvAdapter managementGvAdapter = new FollowManagementGvAdapter(Utils.getApp(), R.layout.item_gv_health_record, managementList, userid);
        gvManagementRecord.setAdapter(managementGvAdapter);
    }


    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_user_info, null);
        backImageView = view.findViewById(R.id.iv_user_info_back);
        headImageView = view.findViewById(R.id.iv_user_info_head);
        deadImageView = view.findViewById(R.id.iv_user_info_dead);
        nameTextView = view.findViewById(R.id.tv_user_info_name);
        sexAndAgeTextView = view.findViewById(R.id.tv_user_info_sex_and_age);
        diseaseFirstTextView = view.findViewById(R.id.tv_user_info_disease_first);
        diseaseSecondTextView = view.findViewById(R.id.tv_user_info_disease_second);
        deviceManagerTextView = view.findViewById(R.id.tv_user_info_device_manager);
        sugarTextTextView = view.findViewById(R.id.tv_user_info_blood_sugar_text);
        pressureTextTextView = view.findViewById(R.id.tv_user_info_blood_pressure_text);
        sugarFollowView = view.findViewById(R.id.tv_user_info_blood_disease_follow_up);
        pressureFollowTextView = view.findViewById(R.id.tv_user_info_blood_pressure_follow_up);
        gvHealthRecord = view.findViewById(R.id.gv_user_info_health_record);
        gvManagementRecord = view.findViewById(R.id.gv_follow_up_management_health_record);
        medicanLinearLayout = view.findViewById(R.id.ll_user_info_user_medican);
        textImageView = view.findViewById(R.id.iv_user_info_text);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_info_device_manager:
                break;
            case R.id.iv_user_info_back:
                break;
            case R.id.ll_user_info_user_medican:
                break;
            case R.id.iv_user_info_text:
                onlineTestPopup = new OnlineTestPopup(getPageContext(), getIntent().getStringExtra("userid"));
                onlineTestPopup.showPopupWindow(textImageView);
                break;
            default:
                break;
        }
    }

    //    private void initCheckView() {
    //        if (textView == null) {
    //            textView = View.inflate(getPageContext(), R.layout.include_data_abnormal_text_view, null);
    //            ImageView textImageView = textView.findViewById(R.id.iv_data_abnormal_text);
    //
    //            textImageView.setOnClickListener(v -> {
    //
    //
    //            });
    //
    //        }
    //        if (containerView().indexOfChild(textView) != -1) {
    //            containerView().removeView(textView);
    //        }
    //        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //        params.bottomMargin = XYSoftDensityUtils.dip2px(getPageContext(), 65);
    //        params.rightMargin = XYSoftDensityUtils.dip2px(getPageContext(), 10);
    //        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
    //        containerView().addView(textView, params);
    //
    //    }
}
