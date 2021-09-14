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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.ClassTopListAdapter;
import com.xy.xydoctor.adapter.community_manager.CommunityBuildingModelListAdapter;
import com.xy.xydoctor.adapter.community_manager.CommunityRoomAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;
import com.xy.xydoctor.bean.community_manamer.FollowListInfo;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.bean.community_manamer.UpLoadParamInfo;
import com.xy.xydoctor.customerView.NoConflictGridView;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.List;

import retrofit2.Call;

/**
 * Author: LYD
 * Date: 2021/8/23 16:38
 *
 * @paramtype 1:小区数量 2：随访待办 3:添加用户  buildingName小区名字  comid小区id
 * Description: 随访代办的那个楼层图
 */
public class CommunityFollowUpBuildingActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    /**
     * 添加楼栋回来刷新
     */
    private static final int REQUEST_CODE_FOR_ADD_BUILDING = 10;
    private TextView searchTextView;
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView buildingNumTextView;
    private TextView unitNumTextView;
    private TextView personNumTextView;
    private View lineView;
    private TextView allPersonNumTextView;
    private TextView pressureTextView;
    private TextView sugarTextView;
    private TextView deseaseAllNumTextView;
    private RecyclerView numRecycleView;
    private RecyclerView unitRecycleView;
    private NoConflictGridView contentGridView;
    /**
     * 1：小区数量 2随访待办 3添加用户
     */
    private String type;

    private String comid;
    private String unityid;

    /**
     * 几号楼
     */
    private ClassTopListAdapter topListAdapter;
    /**
     * 几单元
     */
    private CommunityBuildingModelListAdapter unitAdapter;

    private List<FollowListInfo> buildList;

    /**
     * 当前选中的item
     */
    private int buildindex = 0;
    private int unitindex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        comid = getIntent().getStringExtra("comid");


        if ("2".equals(type)) {
            topViewManager().titleTextView().setText(R.string.follow_up_agent_title);
            topViewManager().moreTextView().setText(R.string.fu_more_title_change_list);
            topViewManager().moreTextView().setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
        } else {
            String title = getIntent().getStringExtra("buildingName");
            topViewManager().titleTextView().setText(title);
            topViewManager().moreTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.fub_top_add, 0, 0, 0);
        }


        topViewManager().moreTextView().setOnClickListener(v -> {
            // 1:小区数量 2：随访待办
            if ("2".equals(type)) {
                //进入到待随访  失访  已完成的页面
                Intent intent = new Intent(getPageContext(), CommunityFollowUpActivity.class);
                startActivity(intent);

            } else {
                //添加楼栋
                Intent intent = new Intent(getPageContext(), CommunityBuildingSettingActivity.class);
                intent.putExtra("comid", comid);
                startActivityForResult(intent, REQUEST_CODE_FOR_ADD_BUILDING);
            }

        });
        containerView().addView(initView());
        initValue();
        initListener();
        getCommunityInfo();
        getBuildingUnitInfo();
    }

    private void initListener() {
        searchTextView.setOnClickListener(this);
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


    /**
     * 获取房间详情
     */
    private void getCommunityInfo() {
        Call<String> requestCall = DataManager.getCommunityDetails(type, comid, (call, response) -> {
            if (response.code == 200) {
                FollowUpAgentListBean info = (FollowUpAgentListBean) response.object;
                nameTextView.setText(info.getCom_name());
                locationTextView.setText(info.getCom_address());
                if ("1".equals(type)) {
                    allPersonNumTextView.setVisibility(View.VISIBLE);
                    lineView.setVisibility(View.VISIBLE);
                    setTextStyle(info.getBuild_count(), getString(R.string.follow_up_agent_building_num), R.color.community_content_black, buildingNumTextView, 17);
                    setTextStyle(info.getUnity_count(), getString(R.string.follow_up_agent_unit_num), R.color.community_content_black, unitNumTextView, 17);
                    setTextStyle(info.getHouse_count(), getString(R.string.follow_up_agent_households_num1), R.color.community_content_black, personNumTextView, 17);
                    setTextStyle(info.getMember_count(), getString(R.string.follow_up_agent_total_num1), R.color.community_content_black, allPersonNumTextView, 17);

                    setTextStyle(info.getGxy_count(), getString(R.string.flb_high_pressure_num), R.color.main_red, pressureTextView, 17);
                    setTextStyle(info.getDiabeteslei_count(), getString(R.string.follow_up_sugar_person_num), R.color.community_statics_orange, sugarTextView, 17);
                    setTextStyle(info.getBoth_count(), getString(R.string.follow_up_total_person_num), R.color.community_follow_blue, deseaseAllNumTextView, 17);

                } else {
                    allPersonNumTextView.setVisibility(View.GONE);
                    lineView.setVisibility(View.GONE);
                    setTextStyle(info.getBuild_count(), getString(R.string.follow_up_agent_building_num), R.color.community_content_black, buildingNumTextView, 17);
                    setTextStyle(info.getUnity_count(), getString(R.string.follow_up_agent_unit_num), R.color.community_content_black, unitNumTextView, 17);
                    setTextStyle(info.getHouse_count(), getString(R.string.follow_up_agent_households_num1), R.color.community_content_black, personNumTextView, 17);

                    setTextStyle(info.getMember_count(), getString(R.string.follow_up_agent_total_num), R.color.community_follow_blue, pressureTextView, 17);
                    setTextStyle(info.getGxy_count(), getString(R.string.flb_high_pressure_num), R.color.main_red, sugarTextView, 17);
                    setTextStyle(info.getDiabeteslei_count(), getString(R.string.follow_up_sugar_person_num), R.color.community_statics_orange, allPersonNumTextView, 17);

                }
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);

        });

    }

    private void getBuildingUnitInfo() {
        Call<String> requestCall = DataManager.getCommunityBuildUnitInfo(comid, (call, response) -> {
            if (response.code == 200) {
                buildList = (List<FollowListInfo>) response.object;
                if (buildList != null && buildList.size() > 0) {
                    initUnitData();
                }
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void initUnitData() {
        //展示楼数据
        buildList.get(0).setCheck(true);
        topListAdapter = new ClassTopListAdapter(getPageContext(), buildList, new OnItemClickListener());
        numRecycleView.setAdapter(topListAdapter);
        List<UpLoadParamInfo> units = buildList.get(0).getUnits();
        if (units != null && units.size() > 0) {
            unityid = units.get(0).getId();
            buildList.get(0).getUnits().get(0).setCheck(true);
        }
        showUnitData();
        getRoomInfo();
    }

    private void getRoomInfo() {
        Call<String> requestCall = DataManager.getCommunityRoomInfo(unityid, (call, response) -> {
            if (response.code == 200) {
                List<CommunityFilterInfo> roomList = (List<CommunityFilterInfo>) response.object;
                int anInt = TurnsUtils.getInt(buildList.get(buildindex).getUnits().get(unitindex).getHousehold(), 0);
                if (anInt < 4) {
                    anInt = 4;
                }
                if (anInt > 5) {
                    anInt = 5;
                }
                contentGridView.setNumColumns(anInt);
                contentGridView.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(getPageContext(), CommunityBuildingUnitActivity.class);
                    intent.putExtra("house_id", roomList.get(position).getId());
                    intent.putExtra("house_name", roomList.get(position).getHouse_num());
                    intent.putExtra("build_id", buildList.get(buildindex).getId());
                    startActivity(intent);
                });
                contentGridView.setVisibility(View.VISIBLE);
                CommunityRoomAdapter roomAdapter = new CommunityRoomAdapter(getPageContext(), roomList);
                contentGridView.setAdapter(roomAdapter);
            } else {
                showRoomError();
                TipUtils.getInstance().showToast(getPageContext(), response.msg);
            }
        }, (call, t) -> {
            showRoomError();
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void showRoomError() {
        contentGridView.setVisibility(View.GONE);
    }


    private class OnItemClickListener implements IAdapterViewClickListener {

        @Override
        public void adapterClickListener(int position, View view) {
            switch (view.getId()) {
                case R.id.ll_top_class:
                    Log.i("yys", "1position==" + position);
                    buildindex = position;
                    unitindex = 0;
                    for (int i = 0; i < buildList.size(); i++) {
                        buildList.get(i).setCheck(false);
                    }
                    buildList.get(position).setCheck(true);
                    topListAdapter.notifyDataSetChanged();
                    showUnitData();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            switch (view.getId()) {


                default:
                    break;

            }
        }
    }

    /**
     * 展示单元
     */
    private void showUnitData() {
        List<UpLoadParamInfo> units = buildList.get(buildindex).getUnits();
        unitAdapter = new CommunityBuildingModelListAdapter(getPageContext(), units, new OnItemClickListener1());
        unitRecycleView.setAdapter(unitAdapter);
    }

    private class OnItemClickListener1 implements IAdapterViewClickListener {

        @Override
        public void adapterClickListener(int position, View view) {

            switch (view.getId()) {
                case R.id.ll_build_unit_click:
                    Log.i("yys", "2position==" + position);
                    for (int i = 0; i < buildList.get(buildindex).getUnits().size(); i++) {
                        buildList.get(buildindex).getUnits().get(i).setCheck(false);
                    }
                    unitindex = position;
                    buildList.get(buildindex).getUnits().get(position).setCheck(true);
                    unitAdapter.notifyDataSetChanged();
                    unityid = buildList.get(buildindex).getUnits().get(unitindex).getId();
                    getRoomInfo();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void adapterClickListener(int position, int index, View view) {
            switch (view.getId()) {


                default:
                    break;

            }
        }
    }

    private void initValue() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getPageContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        numRecycleView.setLayoutManager(linearLayoutManager);
        numRecycleView.setHasFixedSize(true);
        numRecycleView.setNestedScrollingEnabled(false);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getPageContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        unitRecycleView.setLayoutManager(linearLayoutManager1);
        unitRecycleView.setHasFixedSize(true);
        unitRecycleView.setNestedScrollingEnabled(false);


    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_followup_building, null);
        searchTextView = view.findViewById(R.id.tv_follow_up_agent);
        nameTextView = view.findViewById(R.id.tv_fub_building_name);
        locationTextView = view.findViewById(R.id.tv_fub_building_location);
        buildingNumTextView = view.findViewById(R.id.tv_fub_building_num);
        unitNumTextView = view.findViewById(R.id.tv_fub_building_unit_num);
        personNumTextView = view.findViewById(R.id.tv_fub_building_person_num);
        lineView = view.findViewById(R.id.view_fub);
        allPersonNumTextView = view.findViewById(R.id.tv_fub_building_all_person_num);
        pressureTextView = view.findViewById(R.id.tv_fub_building_high_pressure);
        sugarTextView = view.findViewById(R.id.tv_fub_building_sugar);
        deseaseAllNumTextView = view.findViewById(R.id.tv_fub_building_all_num);
        numRecycleView = view.findViewById(R.id.rv_fub_building_num);
        unitRecycleView = view.findViewById(R.id.rv_fub_building_unit);
        contentGridView = view.findViewById(R.id.gv_fub_building_content);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_ADD_BUILDING) {
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_follow_up_agent:
                break;
            default:
                break;
        }
    }
}
