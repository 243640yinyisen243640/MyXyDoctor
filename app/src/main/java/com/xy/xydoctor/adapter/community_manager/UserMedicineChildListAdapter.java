package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineUserInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class UserMedicineChildListAdapter extends CommonAdapter<CommunityUseMedicineUserInfo> {

    private String type;
    private IAdapterViewClickListener clickListener;
    private int parentPosi;
    private Context context;

    public UserMedicineChildListAdapter(Context context, int layoutId, List<CommunityUseMedicineUserInfo> datas, String type, int parentPosi, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.clickListener = clickListener;
        this.parentPosi = parentPosi;
        this.context = context;
    }


    @Override
    protected void convert(ViewHolder viewHolder, CommunityUseMedicineUserInfo item, int position) {
        TextView amountTextView = viewHolder.getView(R.id.tv_use_medicine_child_times_amount);
        TextView allTextView = viewHolder.getView(R.id.tv_use_medicine_child_all);
        TextView haveTextView = viewHolder.getView(R.id.tv_use_medicine_child_have);
        TextView lastTextView = viewHolder.getView(R.id.tv_use_medicine_child_last);
        TextView startTextView = viewHolder.getView(R.id.tv_use_medicine_child_time);
        TextView endTextView = viewHolder.getView(R.id.tv_use_medicine_child_end_time);
        TextView editTextView = viewHolder.getView(R.id.tv_use_medicine_child_edit);
        TextView finishTextView = viewHolder.getView(R.id.tv_use_medicine_child_finish);
        TextView userTextView = viewHolder.getView(R.id.tv_use_medicine_child_remind_user);
        TextView phoneTextView = viewHolder.getView(R.id.tv_user_child_medicine_phone);

        viewHolder.setText(R.id.tv_user_child_medicine_name, item.getNickname());
        viewHolder.setText(R.id.tv_user_child_medicine_sex, item.getSex());
        viewHolder.setText(R.id.tv_user_child_medicine_age, item.getAge() + "岁");
        viewHolder.setText(R.id.tv_user_child_medicine_location, item.getHouse_info());
        viewHolder.setText(R.id.tv_use_medicine_child_name, item.getDrugname());
        viewHolder.setText(R.id.tv_use_medicine_child_last_day, "剩余：" + item.getSurplusTime());
        SpannableStringBuilder stringBuilderTimes = new SpannableStringBuilder();
        stringBuilderTimes.append("服用次数：");
        int start = stringBuilderTimes.length();
        stringBuilderTimes.append(item.getTimes()).append("   ");
        int end = stringBuilderTimes.length();
        stringBuilderTimes.append("用药剂量：");
        int start2 = stringBuilderTimes.length();
        stringBuilderTimes.append(item.getDosage());
        int end2 = stringBuilderTimes.length();
        stringBuilderTimes.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_content_black)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilderTimes.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_content_black)), start2, end2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        amountTextView.setText(stringBuilderTimes);


        SpannableStringBuilder allStringBuilder = new SpannableStringBuilder();
        allStringBuilder.append("总数").append("\n");
        int start3 = allStringBuilder.length();
        allStringBuilder.append(item.getNumber());
        int end3 = allStringBuilder.length();
        allStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_content_black)), start3, end3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        allTextView.setText(allStringBuilder);

        SpannableStringBuilder haveStringBuilder = new SpannableStringBuilder();
        haveStringBuilder.append("已服用").append("\n");
        int start4 = haveStringBuilder.length();
        haveStringBuilder.append(item.getUsed());
        int end4 = haveStringBuilder.length();
        haveStringBuilder.append(item.getUnit_type());
        haveStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_home_index_follow)), start4, end4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        haveTextView.setText(haveStringBuilder);


        SpannableStringBuilder lastStringBuilder = new SpannableStringBuilder();
        lastStringBuilder.append("剩余").append("\n");
        int start5 = lastStringBuilder.length();
        lastStringBuilder.append(item.getSurplus());
        int end5 = lastStringBuilder.length();
        lastStringBuilder.append(item.getUnit_type());
        lastStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_home_index_follow)), start5, end5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        lastTextView.setText(lastStringBuilder);

        SpannableStringBuilder startTimeStringBuilder = new SpannableStringBuilder();
        startTimeStringBuilder.append("开始服用时间：");
        int start6 = startTimeStringBuilder.length();
        startTimeStringBuilder.append(item.getStarttime());
        int end6 = startTimeStringBuilder.length();
        startTimeStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_content_black)), start6, end6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        startTextView.setText(startTimeStringBuilder);

        SpannableStringBuilder thinkTimeStringBuilder = new SpannableStringBuilder();

        thinkTimeStringBuilder.append("服用完成日期：");
        int start7 = thinkTimeStringBuilder.length();
        thinkTimeStringBuilder.append(item.getEndtime());
        int end7 = thinkTimeStringBuilder.length();
        thinkTimeStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.community_content_black)), start7, end7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        endTextView.setText(thinkTimeStringBuilder);

        Log.i("yys", "time1===" + item.getEndtime());
        Log.i("yys", "time==" + endTextView.getText());
        MyClickListener clickListener = new MyClickListener(position);
        editTextView.setOnClickListener(clickListener);
        finishTextView.setOnClickListener(clickListener);
        userTextView.setOnClickListener(clickListener);
        phoneTextView.setOnClickListener(clickListener);
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
}
