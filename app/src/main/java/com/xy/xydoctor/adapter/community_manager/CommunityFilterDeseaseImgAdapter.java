package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.utils.LoadImgUtils;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/8 9:29
 * Description:
 */
public class CommunityFilterDeseaseImgAdapter extends XYSoftBaseAdapter<String> {
    public CommunityFilterDeseaseImgAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_filter_desease_img, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv_filter_desease_img);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoadImgUtils.loadImage(getContext(), R.drawable.community_mental_illness, getList().get(position), viewHolder.imageView);
        convertView.setTag(viewHolder);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
