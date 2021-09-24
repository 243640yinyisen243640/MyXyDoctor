package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;
import com.xy.xydoctor.utils.LoadImgUtils;

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
            convertView = View.inflate(getContext(), R.layout.item_building_room_info, null);
            holder.illImageView = convertView.findViewById(R.id.iv_building_room_ill);
            holder.numTextView = convertView.findViewById(R.id.tv_building_room_num);
            holder.bgFramelayout = convertView.findViewById(R.id.fl_build_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommunityFilterInfo info = getList().get(position);
        LoadImgUtils.loadImage(getContext(), R.drawable.community_mental_illness, info.getImg(), holder.illImageView);
        holder.numTextView.setText(info.getHouse_num());

        if (TextUtils.isEmpty(info.getImg())) {
            holder.illImageView.setVisibility(View.GONE);
        } else {
            holder.illImageView.setVisibility(View.VISIBLE);
        }

        if ("1".equals(info.getIsempty())) {
            holder.bgFramelayout.setBackgroundResource(R.drawable.shape_gray_hight);
        }
        if ("1".equals(info.getAbnormal())) {
            holder.bgFramelayout.setBackgroundResource(R.drawable.shape_yellow);
        }
        //        if ("1".equals(info.getIstodo())) {
        //            holder.numTextView.setBackgroundResource(R.drawable.shape_gray_hight);
        //        }

        return convertView;
    }

    private class ViewHolder {
        ImageView illImageView;
        TextView numTextView;
        FrameLayout bgFramelayout;
    }
}
