package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterChildInfo;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 10:03
 * Description: 楼栋设置
 */
public class UserDateiDataChildListAdapter extends XYSoftBaseAdapter<CommunityFilterChildInfo> {

    public UserDateiDataChildListAdapter(Context context, List<CommunityFilterChildInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_user_datei_data_child, null);
            holder.nameTextView = convertView.findViewById(R.id.tv_user_child_datei_name);
            holder.sexTextView = convertView.findViewById(R.id.tv_user_child_datei_sex);
            holder.ageTextView = convertView.findViewById(R.id.tv_user_child_datei_age);
            holder.phoneTextView = convertView.findViewById(R.id.tv_user_child_datei_phone);
            holder.locationTextView = convertView.findViewById(R.id.tv_user_child_datei_location);
            holder.recordNameTextView = convertView.findViewById(R.id.tv_user_child_datei_record_name);
            holder.deadImageView = convertView.findViewById(R.id.iv_user_child_datei_dead);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommunityFilterChildInfo info = getList().get(position);

        holder.nameTextView.setText(info.getNickname());
        if ("1".equals(info.getSex())) {
            holder.sexTextView.setText(getContext().getString(R.string.base_female));
        } else {
            holder.sexTextView.setText(getContext().getString(R.string.base_male));
        }

        if ("1".equals(info.getIsdeath())){
           holder.deadImageView.setVisibility(View.VISIBLE);
        }else {
            holder.deadImageView.setVisibility(View.GONE);

        }

        holder.ageTextView.setText(info.getAge() + "岁");
        holder.phoneTextView.setText(info.getTel());
        holder.locationTextView.setText(info.getBuild_name() + info.getUnit_name() + info.getHouse_num());
        Log.i("yys", "create_user==" + info.getCreate_user());
        holder.recordNameTextView.setText("建档人  ：" + info.getCreate_user());


        return convertView;
    }

    private class ViewHolder {
        TextView nameTextView;
        TextView sexTextView;
        TextView ageTextView;
        TextView phoneTextView;
        TextView locationTextView;
        TextView recordNameTextView;
        ImageView deadImageView;
    }
}
