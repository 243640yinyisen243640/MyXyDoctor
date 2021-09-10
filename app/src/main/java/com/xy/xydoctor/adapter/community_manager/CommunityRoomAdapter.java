package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/10 15:30
 * Description:
 */
public class CommunityRoomAdapter extends XYSoftBaseAdapter<CommunityFilterInfo> {
    public CommunityRoomAdapter(Context context, List<CommunityFilterInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_doctor_list, null);
            holder.headImg = convertView.findViewById(R.id.img_head);
            holder.nameTextView = convertView.findViewById(R.id.tv_doc_name);
            holder.timeTextView = convertView.findViewById(R.id.tv_login_time);
            holder.arrowRightImageView = convertView.findViewById(R.id.img_right_arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        QMUIRadiusImageView headImg;
        TextView nameTextView;
        TextView timeTextView;
        ImageView arrowRightImageView;
    }
}
