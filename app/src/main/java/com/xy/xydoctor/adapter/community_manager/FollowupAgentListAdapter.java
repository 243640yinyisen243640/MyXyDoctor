package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.BaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.util.List;


public class FollowupAgentListAdapter extends BaseRecycleViewAdapter<FollowUpAgentListBean> {

    public FollowupAgentListAdapter(Context mContext, List<FollowUpAgentListBean> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.adapter_follow_up_agent, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FollowUpAgentListBean info = getList().get(position);
        viewHolder.nameTextView.setText(info.getCom_name());
        viewHolder.locationTextView.setText(info.getCom_address());


        setTextStyle(info.getBuild_count(), getContext().getString(R.string.follow_up_agent_building_num), R.color.community_content_black, viewHolder.buildingNumTextView, 16);
        setTextStyle(info.getUnity_count(), getContext().getString(R.string.follow_up_agent_unit_num), R.color.community_content_black, viewHolder.unitNumTextView, 16);
        setTextStyle(info.getHouse_count(), getContext().getString(R.string.community_house_count), R.color.community_content_black, viewHolder.personNumTextView, 16);
        setTextStyle(info.getMember_count(), getContext().getString(R.string.community_member_count), R.color.community_content_black, viewHolder.allPersonNumTextView, 16);

        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
    }


    private void setTextStyle(String content, String title, int color, TextView textView, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(content).append("\n");
        int start = spannableStringBuilder.length();

        spannableStringBuilder.append(title);
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), color)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(XyScreenUtils.sp2px(getContext(), size)), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableStringBuilder);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView locationTextView;
        TextView buildingNumTextView;
        TextView unitNumTextView;
        TextView personNumTextView;
        TextView allPersonNumTextView;
        LinearLayout clickLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_fu_building_name);
            locationTextView = itemView.findViewById(R.id.tv_fu_building_location);
            buildingNumTextView = itemView.findViewById(R.id.tv_fu_building_num);
            unitNumTextView = itemView.findViewById(R.id.tv_fu_unit_num);
            personNumTextView = itemView.findViewById(R.id.tv_fu_person_num);
            allPersonNumTextView = itemView.findViewById(R.id.tv_fu_all_person_num);
            clickLinearLayout = itemView.findViewById(R.id.ll_fu_click);
        }

    }
}
