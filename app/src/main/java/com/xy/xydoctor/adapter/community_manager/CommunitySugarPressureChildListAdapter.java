package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureChildInfo;
import com.xy.xydoctor.utils.toChineseNumUtill;

import java.util.List;

public class CommunitySugarPressureChildListAdapter extends XYSoftBaseAdapter<SugarOrPressureChildInfo> {

    public CommunitySugarPressureChildListAdapter(Context context, List<SugarOrPressureChildInfo> list) {
        super(context, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_community_sugar_or_pressure_child, null);
            holder.timeTextView = convertView.findViewById(R.id.fu_sugar_pressure_time_child);
            holder.titleTextView = convertView.findViewById(R.id.fu_sugar_pressure_title_child);
            holder.stateTextView = convertView.findViewById(R.id.fu_sugar_pressure_state_child);
            holder.lineView = convertView.findViewById(R.id.view_sugar_or_pressure);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SugarOrPressureChildInfo info = getList().get(position);

        holder.titleTextView.setText(getContext().getString(R.string.community_times_di) + toChineseNumUtill.numberToChinese(info.getTimes()) + getContext().getString(R.string.community_follow_times));

        holder.timeTextView.setText(info.getEndtime());
        //随访状态 0待开启（不可点击） 1待随访 2随访完成 3已过期 4关闭随访
        if ("0".equals(info.getStatus())) {
            holder.stateTextView.setText(getContext().getString(R.string.goods_add_spe_no_finish));

            setTextColor(R.color.data_gray_light, holder.titleTextView);
            setTextColor(R.color.data_gray_light, holder.timeTextView);
            setTextColor(R.color.data_gray_light, holder.stateTextView);

        } else if ("1".equals(info.getStatus())) {
            holder.stateTextView.setText(getContext().getString(R.string.community_wait_follow_up));

            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        } else if ("2".equals(info.getStatus())) {
            holder.stateTextView.setText(getContext().getString(R.string.community_wait_follow_up_finish));

            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        } else if ("3".equals(info.getStatus())) {
            holder.stateTextView.setText(getContext().getString(R.string.community_wait_follow_up_over_time));
            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        } else {
            holder.stateTextView.setText(getContext().getString(R.string.community_wait_follow_up_close));
            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        }

        if (getList().size() == 1) {
            holder.lineView.setVisibility(View.GONE);
        } else if (getList().size() - 1 == position) {
            holder.lineView.setVisibility(View.GONE);
        } else {
            holder.lineView.setVisibility(View.VISIBLE);
        }
        return convertView;


    }

    private void setTextColor(int color, TextView textView) {
        textView.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    private class ViewHolder {
        TextView titleTextView;
        TextView timeTextView;
        TextView stateTextView;
        View lineView;
    }
}
