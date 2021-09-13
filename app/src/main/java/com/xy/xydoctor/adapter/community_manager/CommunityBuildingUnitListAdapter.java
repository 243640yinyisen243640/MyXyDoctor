package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.SearchInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述:具体某个
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityBuildingUnitListAdapter extends UIBaseRecycleViewAdapter<SearchInfo> {


    public CommunityBuildingUnitListAdapter(Context mContext, List<SearchInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("yys", "onCreateViewHolder===");
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_community_building_unit, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SearchInfo info = getList().get(position);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        viewHolder.ImgListView.setLayoutManager(layoutManager);

        viewHolder.nameTextView.setText(info.getNickname());
        if (info.getImg() != null && info.getImg().size() > 0) {
            viewHolder.ImgListView.setVisibility(View.VISIBLE);
            CommunityFilterDeseaseImgAdapter imgAdapter = new CommunityFilterDeseaseImgAdapter(getContext(), info.getImg(), new IAdapterViewClickListener() {
                @Override
                public void adapterClickListener(int position, View view) {

                }

                @Override
                public void adapterClickListener(int position, int index, View view) {

                }
            });
            viewHolder.ImgListView.setAdapter(imgAdapter);
        } else {
            viewHolder.ImgListView.setVisibility(View.INVISIBLE);
        }

        if ("1".equals(info.getIscare())) {
            viewHolder.followTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_impotant_follow, 0, 0, 0);
            viewHolder.followTextView.setText(R.string.community_have_follow);
        } else {
            viewHolder.followTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_unimpotant_follow, 0, 0, 0);
            viewHolder.followTextView.setText(R.string.community_have_unfollow);
        }
        if ("1".equals(info.getRelation())) {
            viewHolder.relationTextView.setText("户主");
        } else if ("2".equals(info.getRelation())) {
            viewHolder.relationTextView.setText("配偶");
        } else if ("3".equals(info.getRelation())) {
            viewHolder.relationTextView.setText("子女");
        } else if ("4".equals(info.getRelation())) {
            viewHolder.relationTextView.setText("儿媳");
        } else if ("5".equals(info.getRelation())) {
            viewHolder.relationTextView.setText("女婿");
        } else if ("6".equals(info.getRelation())) {
            viewHolder.relationTextView.setText("父母");
        } else {
            viewHolder.relationTextView.setText("其他");
        }
        if ("1".equals(info.getSex())) {
            viewHolder.sexTextView.setText(R.string.base_male);
        } else {
            viewHolder.sexTextView.setText(R.string.base_female);
        }

        viewHolder.ageTextView.setText(info.getAge());

        if ("1".equals(info.getDiabeteslei())) {
            viewHolder.sugarTextView.setText(R.string.community_user_info_sugar_one);

        } else if ("2".equals(info.getDiabeteslei())) {
            viewHolder.sugarTextView.setText(R.string.community_user_info_sugar_two);

        } else if ("3".equals(info.getDiabeteslei())) {
            viewHolder.sugarTextView.setText(R.string.community_user_info_sugar_three);

        } else if ("4".equals(info.getDiabeteslei())) {
            viewHolder.sugarTextView.setText(R.string.community_user_info_sugar_four);

        } else {
            viewHolder.sugarTextView.setText(R.string.community_user_info_sugar_no);
        }
        if ("1".equals(info.getHypertension())) {
            viewHolder.pressureTextView.setVisibility(View.VISIBLE);
            if ("1".equals(info.getBloodLevel())) {
                viewHolder.pressureTextView.setText("1级高血压");
            } else {
                viewHolder.pressureTextView.setText("2级高血压");
            }
        } else {
            viewHolder.pressureTextView.setVisibility(View.GONE);
        }

        viewHolder.sugarFollowTextView.setText("血糖随访" + info.getSugar_follow());
        viewHolder.pressureFollowTextView.setText("血压随访" + info.getBlood_follow());

        if ("0".equals(info.getFollow_status())) {
            viewHolder.waitFollowTextView.setVisibility(View.GONE);
        } else {
            viewHolder.waitFollowTextView.setVisibility(View.VISIBLE);
        }

        Log.i("yys", "sugar==" + info.getSugarEmpty() + "blood==" + info.getBloodEmpty());
        if ("2".equals(info.getSugarEmpty())) {
            viewHolder.sugarLinearLayout.setVisibility(View.VISIBLE);
            SpannableStringBuilder sugarStringBuilder = new SpannableStringBuilder();
            sugarStringBuilder.append(info.getSugar().getDatetime());
            int start = sugarStringBuilder.length();
            sugarStringBuilder.append(info.getSugar().getGlucosevalue());
            int end = sugarStringBuilder.length();
            sugarStringBuilder.append("    mmol/L");
            sugarStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.base_black)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            sugarStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            viewHolder.timeSugarTextView.setText(sugarStringBuilder);
            viewHolder.unitTextView.setText(info.getSugar().getCategory());
            if ("1".equals(info.getSugar().getIshight())) {
                viewHolder.unitTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_hign, 0, 0, 0);
            } else if ("2".equals(info.getSugar().getIshight())) {
                viewHolder.unitTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_low, 0, 0, 0);
            } else {
                viewHolder.unitTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_normal, 0, 0, 0);
            }
        } else {
            viewHolder.sugarLinearLayout.setVisibility(View.GONE);
        }

        if ("2".equals(info.getBloodEmpty())) {
            viewHolder.timePressureTextView.setVisibility(View.VISIBLE);
            SpannableStringBuilder pressureStringBuilder = new SpannableStringBuilder();
            pressureStringBuilder.append(info.getBlood().getDatetime());
            int start = pressureStringBuilder.length();
            pressureStringBuilder.append(info.getBlood().getSystolic()).append(info.getBlood().getDiastole());
            int end = pressureStringBuilder.length();
            pressureStringBuilder.append(" mmHg");
            pressureStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.base_black)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            pressureStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            viewHolder.timePressureTextView.setText(pressureStringBuilder);

            if ("1".equals(info.getBlood().getIshight())) {
                viewHolder.timePressureTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_hign, 0);
            } else if ("2".equals(info.getBlood().getIshight())) {
                viewHolder.timePressureTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_low, 0);
            } else {
                viewHolder.timePressureTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_normal, 0);
            }
        } else {
            viewHolder.timePressureTextView.setVisibility(View.GONE);
        }


        viewHolder.followTextView.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private RecyclerView ImgListView;

        private TextView followTextView;
        private TextView relationTextView;
        private LinearLayout sugarLinearLayout;
        private TextView sexTextView;
        private TextView ageTextView;
        private TextView sugarTextView;
        private TextView pressureTextView;
        private TextView sugarFollowTextView;
        private View line;
        private TextView pressureFollowTextView;
        private TextView waitFollowTextView;
        private TextView timeSugarTextView;
        private TextView unitTextView;
        private TextView timePressureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_building_unit_name_item);
            ImgListView = itemView.findViewById(R.id.ml_family_child_img);
            followTextView = itemView.findViewById(R.id.tv_building_unit_follow_item);
            relationTextView = itemView.findViewById(R.id.tv_building_unit_relation_item);
            sugarLinearLayout = itemView.findViewById(R.id.ll_sugar_child);
            sexTextView = itemView.findViewById(R.id.tv_building_unit_sex_item);
            ageTextView = itemView.findViewById(R.id.tv_building_unit_age_item);
            sugarTextView = itemView.findViewById(R.id.tv_building_unit_sugar_item);
            line = itemView.findViewById(R.id.view_line);
            pressureTextView = itemView.findViewById(R.id.tv_building_unit_pressure_item);
            sugarFollowTextView = itemView.findViewById(R.id.tv_building_unit_sugar_follow_item);
            pressureFollowTextView = itemView.findViewById(R.id.tv_building_unit_pressure_follow_item);
            waitFollowTextView = itemView.findViewById(R.id.tv_building_unit_wait_follow_item);
            timeSugarTextView = itemView.findViewById(R.id.tv_building_unit_time_and_sugar_item);
            unitTextView = itemView.findViewById(R.id.tv_building_unit_unit_item);
            timePressureTextView = itemView.findViewById(R.id.tv_building_unit_time_and_pressure_item);
        }
    }


}

