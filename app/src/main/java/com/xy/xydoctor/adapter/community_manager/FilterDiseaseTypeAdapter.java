package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.FilterSugarPressureInfo;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/12 14:49
 * Description: 疾病类型
 */
public class FilterDiseaseTypeAdapter extends XYSoftBaseAdapter<FilterSugarPressureInfo> {
    private int pos = 0;
    /**
     * 1:是筛选  2：添加用户
     */
    private String type;

    public FilterDiseaseTypeAdapter(Context context, List<FilterSugarPressureInfo> list, String type) {
        super(context, list);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.adapter_filter_disease_type, null);
            holder.checkTextView = convertView.findViewById(R.id.cb_filter_disease_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FilterSugarPressureInfo typeInfo = getList().get(position);
        holder.checkTextView.setText(typeInfo.getDiseaseName());
        if (typeInfo.isSelected()) {
            holder.checkTextView.setTextColor(getContext().getResources().getColor(R.color.base_text_gray1));
        } else {
            holder.checkTextView.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        if ("1".equals(type)) {
            if (typeInfo.isCheck()) {
                holder.checkTextView.setTextColor(getContext().getResources().getColor(R.color.main_red));
                holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.community_filter_choose));
            } else {
                holder.checkTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_background_5));
            }


        } else {
            if (typeInfo.isCheck()) {
                holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_main_90));
            } else {
                holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_white_black_90));
            }
        }


        return convertView;
    }

    private class ViewHolder {
        TextView checkTextView;
    }

    public void setClickPosition(int position) {
        this.pos = position;
        this.notifyDataSetChanged();
    }

    public int getClickPosition() {
        return pos;
    }
}
