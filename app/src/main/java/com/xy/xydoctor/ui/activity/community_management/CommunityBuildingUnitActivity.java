package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.community_manager.CommunityBuildingUnitListAdapter;
import com.xy.xydoctor.adapter.community_manager.CommunityFilterDeseaseImgAdapter;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.FamilyAllInfo;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.TipUtils;

import retrofit2.Call;

/**
 * 类描述：家庭人员详情
 * 类传参： house_id  house_name
 *
 * @author android.yys
 * @date 2021/1/15
 */
public class CommunityBuildingUnitActivity extends XYSoftUIBaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_FOR_REFRESH = 10;
    private CommunityBuildingUnitListAdapter mAdapter;

    private LinearLayout allLinearLayout;
    private LinearLayout holdLinearLayout;
    private LinearLayout emptyLinearLayout;
    private TextView nameTextView;
    private TextView locationTextView;
    private RecyclerView imgListView;
    private TextView followTextView;
    private TextView releationTextView;
    private TextView ageTextView;
    private TextView sexTextView;
    private TextView sugarTextView;
    private TextView pressureTextView;
    private TextView sugarFollowTextView;
    private TextView pressureFollowTextView;
    private TextView waitFollowTextView;
    private TextView timeSugarTextView;
    private LinearLayout sugarLinearLayout;
    private TextView unitTextView;
    private TextView timePressureTextView;
    private RecyclerView memberMyListView;

    /**
     * 房间ID
     */
    private String house_id;
    /**
     * 房间名字
     */
    private String house_name;
    private String build_id;
    private FamilyAllInfo info;

    private String numid;
    private String unitid;
    private String roomnum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        house_id = getIntent().getStringExtra("house_id");
        house_name = getIntent().getStringExtra("house_name");
        build_id = getIntent().getStringExtra("build_id");

        numid = getIntent().getStringExtra("numid");
        unitid = getIntent().getStringExtra("unitid");
        roomnum = getIntent().getStringExtra("roomnum");

        topViewManager().titleTextView().setText(house_name);
        topViewManager().moreTextView().setText("添加成员");
        topViewManager().moreTextView().setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));

        topViewManager().moreTextView().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), UserAddActivity.class);
            intent.putExtra("houserid", house_id);
            intent.putExtra("houserid", house_id);
            intent.putExtra("buildid", build_id);
            intent.putExtra("houseinfo", numid + "号楼" + unitid + roomnum);
            startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
        });
        initView();

        initListener();
        initValues();
        getDataInfo();
    }

    private void initListener() {
        followTextView.setOnClickListener(this);
        holdLinearLayout.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_REFRESH) {
                getDataInfo();
            }
        }
    }

    private void initValues() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        imgListView.setLayoutManager(layoutManager);
        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        memberMyListView.setLayoutManager(layoutManager1);
//        memberMyListView.setNestedScrollingEnabled(false);
    }

    private void getDataInfo() {
        Call<String> requestCall = DataManager.getFamilyInfo(house_id, (call, response) -> {
            if (response.code == 200) {
                info = (FamilyAllInfo) response.object;
                allLinearLayout.setVisibility(View.VISIBLE);
                emptyLinearLayout.setVisibility(View.GONE);
                bindData();
            } else {
                allLinearLayout.setVisibility(View.GONE);
                emptyLinearLayout.setVisibility(View.VISIBLE);
            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private void bindData() {
        if (info.getMaster() == null) {
            holdLinearLayout.setVisibility(View.GONE);
        } else {
            holdLinearLayout.setVisibility(View.VISIBLE);
            nameTextView.setText(info.getMaster().getNickname());
            if (info.getMaster().getImg() != null && info.getMaster().getImg().size() > 0) {
                imgListView.setVisibility(View.VISIBLE);
                CommunityFilterDeseaseImgAdapter imgAdapter = new CommunityFilterDeseaseImgAdapter(getPageContext(), info.getMaster().getImg(), new IAdapterViewClickListener() {
                    @Override
                    public void adapterClickListener(int position, View view) {

                    }

                    @Override
                    public void adapterClickListener(int position, int index, View view) {

                    }
                });
                imgListView.setAdapter(imgAdapter);
            } else {
                imgListView.setVisibility(View.GONE);
            }

            locationTextView.setText(info.getMaster().getHouseinfo());
            if ("1".equals(info.getMaster().getIscare())) {
                followTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_impotant_follow, 0, 0, 0);
                followTextView.setText(R.string.community_have_follow);
            } else {
                followTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_family_unfollow, 0, 0, 0);
                followTextView.setText(R.string.community_have_unfollow);
            }

            if ("1".equals(info.getMaster().getSex())) {
                sexTextView.setText(R.string.base_male);
            } else {
                sexTextView.setText(R.string.base_female);
            }


            if ("1".equals(info.getMaster().getRelation())) {
                releationTextView.setText("户主");
            } else if ("2".equals(info.getMaster().getRelation())) {
                releationTextView.setText("配偶");
            } else if ("3".equals(info.getMaster().getRelation())) {
                releationTextView.setText("子女");
            } else if ("4".equals(info.getMaster().getRelation())) {
                releationTextView.setText("儿媳");
            } else if ("5".equals(info.getMaster().getRelation())) {
                releationTextView.setText("女婿");
            } else if ("6".equals(info.getMaster().getRelation())) {
                releationTextView.setText("父母");
            } else {
                releationTextView.setText("其他");
            }
            ageTextView.setText(info.getMaster().getAge()+"岁");


            if ("1".equals(info.getMaster().getDiabeteslei())) {
                sugarTextView.setVisibility(View.VISIBLE);

                sugarTextView.setText(R.string.community_user_info_sugar_one);

            } else if ("2".equals(info.getMaster().getDiabeteslei())) {
                sugarTextView.setVisibility(View.VISIBLE);

                sugarTextView.setText(R.string.community_user_info_sugar_two);

            } else if ("3".equals(info.getMaster().getDiabeteslei())) {
                sugarTextView.setVisibility(View.VISIBLE);

                sugarTextView.setText(R.string.community_user_info_sugar_three);

            } else if ("4".equals(info.getMaster().getDiabeteslei())) {
                sugarTextView.setText(R.string.community_user_info_sugar_four);

            } else {
                sugarTextView.setText(R.string.community_user_info_sugar_no);
                sugarTextView.setVisibility(View.GONE);
            }
            if ("1".equals(info.getMaster().getHypertension())) {
                pressureTextView.setVisibility(View.VISIBLE);
                if ("1".equals(info.getMaster().getBloodLevel())) {
                    pressureTextView.setText("1级高血压");
                } else {
                    pressureTextView.setText("2级高血压");
                }
            } else {
                pressureTextView.setVisibility(View.GONE);
            }

            sugarFollowTextView.setText("血糖随访" + info.getMaster().getSugar_follow());
            pressureFollowTextView.setText("血压随访" + info.getMaster().getBlood_follow());

            if ("0".equals(info.getMaster().getFollow_status())) {
                waitFollowTextView.setVisibility(View.GONE);
            } else {
                waitFollowTextView.setVisibility(View.VISIBLE);
            }

            if ("2".equals(info.getMaster().getSugarEmpty())) {
                sugarLinearLayout.setVisibility(View.VISIBLE);
                SpannableStringBuilder sugarStringBuilder = new SpannableStringBuilder();
                sugarStringBuilder.append(info.getMaster().getDatetime() + "  ");
                int start = sugarStringBuilder.length();
                sugarStringBuilder.append(info.getMaster().getGlucosevalue());
                int end = sugarStringBuilder.length();
                sugarStringBuilder.append("    mmol/L");
                sugarStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.base_black)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                sugarStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                timeSugarTextView.setText(sugarStringBuilder);
                unitTextView.setText(info.getMaster().getCategory());
                if ("1".equals(info.getMaster().getIshight())) {
                    unitTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_hign, 0, 0, 0);
                } else if ("2".equals(info.getMaster().getIshight())) {
                    unitTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_low, 0, 0, 0);
                } else {
                    unitTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_normal, 0, 0, 0);
                }
            } else {
                sugarLinearLayout.setVisibility(View.GONE);
            }

            if ("2".equals(info.getMaster().getBlood())) {
                timePressureTextView.setVisibility(View.VISIBLE);
                SpannableStringBuilder pressureStringBuilder = new SpannableStringBuilder();
                pressureStringBuilder.append(info.getMaster().getDatetime() + "  ");
                int start = pressureStringBuilder.length();
                pressureStringBuilder.append(info.getMaster().getSystolic()).append("/").append(info.getMaster().getDiastole() + "   ");
                int end = pressureStringBuilder.length();
                pressureStringBuilder.append(" mmHg");
                pressureStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.base_black)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                pressureStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                timePressureTextView.setText(pressureStringBuilder);

                if ("1".equals(info.getMaster().getIshight())) {
                    timePressureTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_hign, 0);
                } else if ("2".equals(info.getMaster().getIshight())) {
                    timePressureTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_low, 0);
                } else {
                    timePressureTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_normal, 0);
                }
            } else {
                timePressureTextView.setVisibility(View.GONE);
            }

        }

        mAdapter = new CommunityBuildingUnitListAdapter(getPageContext(), info.getMembers(), new OnItemClickListener());

        memberMyListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_building_unit_follow:
                // 1是 2否
                if ("1".equals(info.getMaster().getIscare())) {
                    followUser("2", info.getMaster().getUserid(), "1", 0);
                } else {
                    followUser("1", info.getMaster().getUserid(), "1", 0);
                }
                break;
            case R.id.ll_building_unit_hold:
                Intent intent = new Intent(getPageContext(), CommunityUserInfoActivity.class);
                intent.putExtra("userid", info.getMaster().getUserid());
                intent.putExtra("username", info.getMaster().getNickname());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void followUser(String isFollow, String userid, String type, int position) {
        Call<String> requestCall = DataManager.followUser(isFollow, userid, (call, response) -> {
            if (response.code == 200) {
                if ("1".equals(type)) {
                    if ("1".equals(isFollow)) {
                        followTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_impotant_follow, 0, 0, 0);
                        followTextView.setText(R.string.community_have_follow);
                    } else {
                        followTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_family_unfollow, 0, 0, 0);
                        followTextView.setText(R.string.community_have_unfollow);
                    }
                } else {
                    if ("1".equals(isFollow)) {
                        info.getMembers().get(position).setIscare("1");
                    } else {
                        info.getMembers().get(position).setIscare("2");
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    private class OnItemClickListener implements IAdapterViewClickListener {

        @Override
        public void adapterClickListener(int position, View view) {

            switch (view.getId()) {
                case R.id.tv_building_unit_follow_item:
                    if ("1".equals(info.getMembers().get(position).getIscare())) {
                        followUser("2", info.getMembers().get(position).getUserid(), "2", position);
                    } else {
                        followUser("1", info.getMembers().get(position).getUserid(), "2", position);
                    }
                    break;
                case R.id.ll_building_unit_click_item:
                    Intent intent = new Intent(getPageContext(), CommunityUserInfoActivity.class);
                    intent.putExtra("userid", info.getMembers().get(position).getUserid());
                    intent.putExtra("username", info.getMembers().get(position).getNickname());
                    startActivity(intent);
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


    private void initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_community_building_unit, null);
        nameTextView = view.findViewById(R.id.tv_building_unit_name);
        allLinearLayout = view.findViewById(R.id.ll_building_unit_all);
        holdLinearLayout = view.findViewById(R.id.ll_building_unit_hold);
        emptyLinearLayout = view.findViewById(R.id.ll_building_unit_empty);
        locationTextView = view.findViewById(R.id.tv_building_unit_location);
        imgListView = view.findViewById(R.id.ml_family_img);
        followTextView = view.findViewById(R.id.tv_building_unit_follow);
        releationTextView = view.findViewById(R.id.tv_building_unit_relation);
        ageTextView = view.findViewById(R.id.tv_building_unit_age);
        sexTextView = view.findViewById(R.id.tv_building_unit_sex);
        sugarTextView = view.findViewById(R.id.tv_building_unit_sugar);
        pressureTextView = view.findViewById(R.id.tv_building_unit_pressure);
        sugarFollowTextView = view.findViewById(R.id.tv_building_unit_sugar_follow);
        pressureFollowTextView = view.findViewById(R.id.tv_building_unit_pressure_follow);
        waitFollowTextView = view.findViewById(R.id.tv_building_unit_wait_follow);
        timeSugarTextView = view.findViewById(R.id.tv_building_unit_time_and_sugar);
        sugarLinearLayout = view.findViewById(R.id.ll_building_unit_sugar);
        unitTextView = view.findViewById(R.id.tv_building_unit_unit);
        timePressureTextView = view.findViewById(R.id.tv_building_unit_time_and_pressure);
        memberMyListView = view.findViewById(R.id.lv_building_unit_child);
        containerView().addView(view);

    }


}
