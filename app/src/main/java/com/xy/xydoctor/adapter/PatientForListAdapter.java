package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.DoctorListBean;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/6/23 11:32
 * Description:
 */
public class PatientForListAdapter extends XYSoftBaseAdapter<DoctorListBean> {
    public PatientForListAdapter(Context context, List<DoctorListBean> list) {
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
        holder.arrowRightImageView.setVisibility(View.VISIBLE);
        Glide.with(Utils.getApp()).load(getList().get(position).getPicture()).into(holder.headImg);
        holder.timeTextView.setText(getList().get(position).getLogin_time());
        holder.nameTextView.setText(getList().get(position).getDocname());

        return convertView;
    }

    private class ViewHolder {
        QMUIRadiusImageView headImg;
        TextView nameTextView;
        TextView timeTextView;
        ImageView arrowRightImageView;
    }
}
