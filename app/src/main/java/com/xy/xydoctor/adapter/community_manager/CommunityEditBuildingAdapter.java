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
 * Date: 2021/8/26 11:50
 * Description: 编辑楼栋
 */
public class CommunityEditBuildingAdapter extends XYSoftBaseAdapter<BuildingInfo> {
    public CommunityEditBuildingAdapter(Context context, List<BuildingInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_edit_building, null);
            holder.buildingTextView = convertView.findViewById(R.id.tv_item_add_building_num);
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
