package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureChildInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.utils.NumberToChineseUtil;

import java.util.List;

public class CommunitySugarPressureChildListAdapter extends XYSoftBaseAdapter<SugarOrPressureChildInfo> {
    private IAdapterViewClickListener clickListener;
    private int parPosition;

    public CommunitySugarPressureChildListAdapter(Context context, List<SugarOrPressureChildInfo> list, int parPosition, IAdapterViewClickListener clickListener) {
        super(context, list);
        this.clickListener = clickListener;
        this.parPosition = parPosition;
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
            holder.clickLinearLayout = convertView.findViewById(R.id.ll_sugar_or_pressure_child_click);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SugarOrPressureChildInfo info = getList().get(position);

        holder.titleTextView.setText(getContext().getString(R.string.community_times_di) + NumberToChineseUtil.intToChinese(info.getTimes()) + getContext().getString(R.string.community_follow_times));

        holder.timeTextView.setText(info.getEndtime());
        holder.stateTextView.setText(info.getStatus());
        //随访状态 0待开启（不可点击） 1待随访 2随访完成 3已过期 4关闭随访
        if ("待开启".equals(info.getStatus())) {
            setTextColor(R.color.data_gray_light, holder.titleTextView);
            setTextColor(R.color.data_gray_light, holder.timeTextView);
            setTextColor(R.color.data_gray_light, holder.stateTextView);

        } else if ("待随访".equals(info.getStatus())) {
            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        } else if ("随访完成".equals(info.getStatus())) {
            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        } else if ("已过期".equals(info.getStatus())) {
            setTextColor(R.color.community_content_black, holder.titleTextView);
            setTextColor(R.color.community_content_black, holder.timeTextView);
            setTextColor(R.color.community_content_black, holder.stateTextView);
        } else {
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
        MyClickListener clickListener = new MyClickListener(position);

        holder.clickLinearLayout.setOnClickListener(clickListener);
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
        LinearLayout clickLinearLayout;
    }

    private class MyClickListener implements View.OnClickListener {

        private int childPosi;

        public MyClickListener(int childPosi) {
            this.childPosi = childPosi;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.adapterClickListener(parPosition, childPosi, v);
            }
        }
    }

}
