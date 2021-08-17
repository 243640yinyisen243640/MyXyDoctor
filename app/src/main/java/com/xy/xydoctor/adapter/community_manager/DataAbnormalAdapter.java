package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/17 15:30
 * Description: 数据异常血糖 数据类型
 */
public class DataAbnormalAdapter extends XYSoftBaseAdapter<DataAbnormalInfo> {
    private int pos = 0;

    public DataAbnormalAdapter(Context context, List<DataAbnormalInfo> list) {
        super(context, list);
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
        DataAbnormalInfo typeInfo = getList().get(position);
        holder.checkTextView.setText(typeInfo.getTitle());

        if (position == pos) {
            holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_white_main_90));
        } else {
            holder.checkTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_white_black_90));

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

