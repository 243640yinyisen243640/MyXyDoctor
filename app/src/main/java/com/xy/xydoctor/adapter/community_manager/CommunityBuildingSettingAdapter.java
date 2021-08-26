package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.BuildingInfo;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 10:03
 * Description: 楼栋设置
 */
public class CommunityBuildingSettingAdapter extends XYSoftBaseAdapter<BuildingInfo> {
    public CommunityBuildingSettingAdapter(Context context, List<BuildingInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_community_setting_building, null);
            holder.buildingTextView = convertView.findViewById(R.id.tv_building_set_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BuildingInfo typeInfo = getList().get(position);



        return convertView;
    }

    private class ViewHolder {
        TextView buildingTextView;
    }
}
