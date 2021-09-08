package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineUserInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 用药提醒
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunityUserMedicineForSomeOneListAdapter extends UIBaseRecycleViewAdapter<CommunityUseMedicineUserInfo> {

    public CommunityUserMedicineForSomeOneListAdapter(Context mContext, List<CommunityUseMedicineUserInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_user_medicine_some_one_list, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CommunityUseMedicineUserInfo item = getList().get(position);

        viewHolder.nameTextView.setText(item.getDrugname());
        viewHolder.lastDayTextView.setText("剩余：" + item.getSurplusTime());
        SpannableStringBuilder stringBuilderTimes = new SpannableStringBuilder();
        stringBuilderTimes.append("服用次数：");
        int start = stringBuilderTimes.length();
        stringBuilderTimes.append(item.getTimes()).append("   ");
        int end = stringBuilderTimes.length();
        stringBuilderTimes.append("用药剂量：");
        int start2 = stringBuilderTimes.length();
        stringBuilderTimes.append(item.getDosage());
        int end2 = stringBuilderTimes.length();
        stringBuilderTimes.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_content_black)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilderTimes.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_content_black)), start2, end2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.amountTextView.setText(stringBuilderTimes);


        SpannableStringBuilder allStringBuilder = new SpannableStringBuilder();
        allStringBuilder.append("总数").append("\n");
        int start3 = allStringBuilder.length();
        allStringBuilder.append(item.getNumber());
        int end3 = allStringBuilder.length();
        allStringBuilder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_content_black)), start3, end3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.allTextView.setText(allStringBuilder);

        SpannableStringBuilder haveStringBuilder = new SpannableStringBuilder();
        haveStringBuilder.append("已服用").append("\n");
        int start4 = haveStringBuilder.length();
        haveStringBuilder.append(item.getUsed());
        int end4 = haveStringBuilder.length();
        haveStringBuilder.append(item.getUnit_type());
        haveStringBuilder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_home_index_follow)), start4, end4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.haveTextView.setText(haveStringBuilder);


        SpannableStringBuilder lastStringBuilder = new SpannableStringBuilder();
        lastStringBuilder.append("剩余").append("\n");
        int start5 = lastStringBuilder.length();
        lastStringBuilder.append(item.getSurplus());
        int end5 = lastStringBuilder.length();
        lastStringBuilder.append(item.getUnit_type());
        lastStringBuilder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_home_index_follow)), start5, end5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.lastTextView.setText(lastStringBuilder);

        SpannableStringBuilder startTimeStringBuilder = new SpannableStringBuilder();
        startTimeStringBuilder.append("开始服用时间：");
        int start6 = startTimeStringBuilder.length();
        startTimeStringBuilder.append(item.getStarttime());
        int end6 = startTimeStringBuilder.length();
        startTimeStringBuilder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_content_black)), start6, end6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.timeTextView.setText(startTimeStringBuilder);

        SpannableStringBuilder thinkTimeStringBuilder = new SpannableStringBuilder();

        thinkTimeStringBuilder.append("预计服用完成日期：");
        int start7 = thinkTimeStringBuilder.length();
        thinkTimeStringBuilder.append(item.getStarttime());
        int end7 = thinkTimeStringBuilder.length();
        thinkTimeStringBuilder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.community_content_black)), start7, end7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.endTimeTextView.setText(thinkTimeStringBuilder);
        viewHolder.editTextView.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView lastDayTextView;
        private TextView amountTextView;
        private TextView allTextView;
        private TextView haveTextView;
        private TextView lastTextView;
        private TextView timeTextView;
        private TextView endTimeTextView;
        private TextView editTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_use_medicine_child_name_so);
            lastDayTextView = itemView.findViewById(R.id.tv_use_medicine_child_last_day_so);
            amountTextView = itemView.findViewById(R.id.tv_use_medicine_child_times_amount_so);
            allTextView = itemView.findViewById(R.id.tv_use_medicine_child_all_so);
            haveTextView = itemView.findViewById(R.id.tv_use_medicine_child_have_so);
            lastTextView = itemView.findViewById(R.id.tv_use_medicine_child_last_so);
            timeTextView = itemView.findViewById(R.id.tv_use_medicine_child_time_so);
            endTimeTextView = itemView.findViewById(R.id.tv_use_medicine_child_end_time_so);
            editTextView = itemView.findViewById(R.id.tv_use_medicine_child_edit_so);
        }
    }


}

