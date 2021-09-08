package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.CommunityStaticsInfo;

import java.util.List;

/**
 * 描述:  随访统计
 * 作者: LYD
 * 创建日期: 2020/11/3 16:23
 */
public class CommunitySetFollowAdapter extends XYSoftBaseAdapter<CommunityStaticsInfo> {


    public CommunitySetFollowAdapter(Context context, List<CommunityStaticsInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_follow_data_statics, null);
            holder.nameTextView = convertView.findViewById(R.id.tv_follow_statics_name);
            holder.resourceImgView = convertView.findViewById(R.id.img_follow_statics_pic);
            holder.rateProgressBar = convertView.findViewById(R.id.pb_follow_statics_rate);
            holder.rateTextView = convertView.findViewById(R.id.tv_follow_statics_rate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置图片
        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.reach_follow_statics_pic);
        holder.resourceImgView.setImageResource(imgArray.getResourceId(position, 0));
        //设置文字
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.reach_follow_statics_name);
        holder.nameTextView.setText(stringArray[position]);
        //设置进度条

        holder.rateProgressBar.setProgress(TurnsUtils.getInt(getList().get(position).getTypeName(), 0));
        setPbDrawable(holder.rateProgressBar, ResourceUtils.getDrawable(R.drawable.progressbar_bg_blue));
        //设置比率
        holder.rateTextView.setText(getList().get(position).getTypeName() + "%");


        return convertView;
    }

    private class ViewHolder {
        TextView nameTextView;
        TextView rateTextView;
        ImageView resourceImgView;
        ProgressBar rateProgressBar;
    }

    private void setPbDrawable(ProgressBar pb, Drawable draw) {
        draw.setBounds(pb.getProgressDrawable().getBounds());
        pb.setProgressDrawable(draw);
    }
}
