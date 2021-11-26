package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterChildInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 10:03
 * Description: 楼栋设置
 */
public class UserDateiDataChildListAdapter extends XYSoftBaseAdapter<CommunityFilterChildInfo> {
    private IAdapterViewClickListener clickListener;
    private int parPosition;

    public UserDateiDataChildListAdapter(Context context, List<CommunityFilterChildInfo> list, int parPosition, IAdapterViewClickListener clickListener) {
        super(context, list);
        this.clickListener = clickListener;
        this.parPosition = parPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_user_datei_data_child, null);
            holder.nameTextView = convertView.findViewById(R.id.tv_user_child_datei_name);
            holder.clickFrameLayout = convertView.findViewById(R.id.ll_datei_child_click);
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
            holder.sexTextView.setText(getContext().getString(R.string.base_male));
        } else {
            holder.sexTextView.setText(getContext().getString(R.string.base_female));
        }

        if ("1".equals(info.getIsdeath())) {
            holder.deadImageView.setVisibility(View.VISIBLE);
        } else {
            holder.deadImageView.setVisibility(View.GONE);

        }

        holder.ageTextView.setText(info.getAge() + "岁");
        holder.phoneTextView.setText(info.getTel());
        holder.locationTextView.setText(info.getBuild_name() + info.getUnit_name() + info.getHouse_num());
        holder.recordNameTextView.setText("建档人  ：" + info.getCreate_user());
        MyClickListener clickListener = new MyClickListener(position);

        holder.clickFrameLayout.setOnClickListener(clickListener);

        return convertView;
    }

    private class ViewHolder {
        LinearLayout clickFrameLayout;
        TextView nameTextView;
        TextView sexTextView;
        TextView ageTextView;
        TextView phoneTextView;
        TextView locationTextView;
        TextView recordNameTextView;
        ImageView deadImageView;
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
