package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.FollowListChildListInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.List;

public class CommunityFollowUpChangeChildListAdapter extends XYSoftBaseAdapter<FollowListChildListInfo> {

    private String type;
    private IAdapterViewClickListener clickListener;
    private int parentPosi;

    public CommunityFollowUpChangeChildListAdapter(Context context, List<FollowListChildListInfo> list, String type, int parentPosi, IAdapterViewClickListener clickListener) {
        super(context, list);
        this.clickListener = clickListener;
        this.type = type;
        this.parentPosi = parentPosi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_community_follow_up_change_child_list, null);
            holder.callPhoneTextView = convertView.findViewById(R.id.tv_fuc_child_call_phone);
            holder.noFinishTextView = convertView.findViewById(R.id.tv_fuc_child_no_finish);
            holder.NamelocationTextView = convertView.findViewById(R.id.tv_fuc_child_name_and_location);
            holder.sexTextView = convertView.findViewById(R.id.tv_fuc_child_sex);
            holder.ageTextView = convertView.findViewById(R.id.tv_fuc_child_age);
            holder.phoneTextView = convertView.findViewById(R.id.tv_fuc_child_phone);
            holder.sugarTextView = convertView.findViewById(R.id.tv_fuc_child_sugar);
            holder.pressureTextView = convertView.findViewById(R.id.tv_fuc_child_pressure);
            holder.sugarFollowTextView = convertView.findViewById(R.id.tv_fuc_child_sugar_follow);
            holder.pressureFollowTextView = convertView.findViewById(R.id.tv_fuc_child_pressure_follow);
            holder.timeTextView = convertView.findViewById(R.id.tv_fuc_child_time);
            holder.memoTextView = convertView.findViewById(R.id.tv_fuc_child_memo);
            holder.memoLinearLayout = convertView.findViewById(R.id.ll_follow_memo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FollowListChildListInfo info = getList().get(position);


        if ("3".equals(type)) {
            holder.noFinishTextView.setVisibility(View.GONE);
        } else {
            holder.noFinishTextView.setVisibility(View.VISIBLE);
        }

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(info.getNickname() + "  ");
        int start = stringBuilder.length();
        stringBuilder.append(info.getHouse_num());
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.base_black)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new AbsoluteSizeSpan(XyScreenUtils.sp2px(getContext(), 17)), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.NamelocationTextView.setText(stringBuilder);
        holder.sexTextView.setText(info.getSex());
        holder.ageTextView.setText(info.getAge());
        holder.phoneTextView.setText(info.getTel());

        if ("1".equals(info.getDiabeteslei())) {
            holder.sugarTextView.setVisibility(View.VISIBLE);
            holder.sugarTextView.setText(R.string.community_user_info_sugar_one);
        } else if ("2".equals(info.getDiabeteslei())) {
            holder.sugarTextView.setVisibility(View.VISIBLE);
            holder.sugarTextView.setText(R.string.community_user_info_sugar_two);
        } else if ("3".equals(info.getDiabeteslei())) {
            holder.sugarTextView.setVisibility(View.VISIBLE);
            holder.sugarTextView.setText(R.string.community_user_info_sugar_three);
        } else if ("4".equals(info.getDiabeteslei())) {
            holder.sugarTextView.setVisibility(View.VISIBLE);
            holder.sugarTextView.setText(R.string.community_user_info_sugar_four);
        } else {
            holder.sugarTextView.setVisibility(View.GONE);
            holder.sugarTextView.setText(R.string.community_user_info_sugar_no);
        }
        if ("1".equals(info.getBloodLevel())) {
            holder.pressureTextView.setText(R.string.community_user_info_pressure_one);
        } else if ("2".equals(info.getBloodLevel())){
            holder.pressureTextView.setText(R.string.community_user_info_pressure_two);
        }else {
            holder.pressureTextView.setText(R.string.community_user_info_pressure_three);
        }

        holder.sugarFollowTextView.setText("血糖随访" + info.getSugar());
        holder.pressureFollowTextView.setText("血压随访" + info.getBlood());

        if ("2".equals(type)) {
            if (!TextUtils.isEmpty(info.getReason())) {
                holder.memoLinearLayout.setVisibility(View.VISIBLE);
            }
        } else {
            holder.memoLinearLayout.setVisibility(View.GONE);
        }
        MyClickListener clickListener = new MyClickListener(position);
        holder.callPhoneTextView.setOnClickListener(clickListener);
        holder.noFinishTextView.setOnClickListener(clickListener);
        holder.sugarFollowTextView.setOnClickListener(clickListener);
        holder.pressureFollowTextView.setOnClickListener(clickListener);
        return convertView;
    }


    /**
     * 点击事件
     */
    private class MyClickListener implements View.OnClickListener {

        private int childPosi;

        public MyClickListener(int childPosi) {
            this.childPosi = childPosi;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.adapterClickListener(parentPosi, childPosi, v);
            }
        }
    }

    private class ViewHolder {
        TextView callPhoneTextView;
        TextView noFinishTextView;
        TextView NamelocationTextView;
        TextView sexTextView;
        TextView phoneTextView;
        TextView ageTextView;
        TextView sugarTextView;
        TextView pressureTextView;
        TextView sugarFollowTextView;
        TextView pressureFollowTextView;
        TextView timeTextView;
        TextView memoTextView;
        LinearLayout memoLinearLayout;
    }
}
