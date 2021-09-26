package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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

        if ("0".equals(info.getIsempty())) {
            holder.bgFramelayout.setBackgroundResource(R.drawable.shape_gray_hight);
        }

        Log.i("yys","empty"+info.getIsempty());
        Log.i("yys","abnormal"+info.getIsempty());
        Log.i("yys","isTodo"+info.getIstodo());
        /**
         *      * 有无异常
         *      * 0：无
         *      * 1：有
         *      */

        /**
         * 是否有待办
         * 0：无
         * 1：有
         */
        switch (info.getAbnormal()) {
            case "0":

                if ("1".equals(info.getIstodo())) {
                    holder.bgFramelayout.setBackgroundResource(R.drawable.shape_yellow);
                } else {
                    holder.bgFramelayout.setBackgroundResource(R.drawable.shape_white_heavy);
                }
                break;
            case "1":
                if ("1".equals(info.getIstodo())) {
                    holder.bgFramelayout.setBackgroundResource(R.drawable.shape_red);
                } else {
                    holder.bgFramelayout.setBackgroundResource(R.drawable.shape_yellow);
                }
                break;
            default:
                break;
        }
        //        if ("1".equals(info.getAbnormal())) {
        //            holder.bgFramelayout.setBackgroundResource(R.drawable.shape_red);
        //
        //        } else {
        //            if ("1".equals(info.getIstodo())) {
        //                holder.numTextView.setBackgroundResource(R.drawable.shape_yellow);
        //            }
        //        }


        return convertView;
    }

    private class ViewHolder {
        ImageView illImageView;
        TextView numTextView;
        FrameLayout bgFramelayout;
    }
}
