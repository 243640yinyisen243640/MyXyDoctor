package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.XYSoftBaseAdapter;
import com.xy.xydoctor.bean.community_manamer.UpLoadParamInfo;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 11:50
 * Description: 编辑楼栋
 */
public class CommunityEditBuildingAdapter extends XYSoftBaseAdapter<UpLoadParamInfo> {
    public CommunityEditBuildingAdapter(Context context, List<UpLoadParamInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_edit_building, null);
            holder.buildingTextView = convertView.findViewById(R.id.tv_item_add_building_name);
            holder.buildingNumTextView = convertView.findViewById(R.id.tv_item_add_building_num);
            holder.line = convertView.findViewById(R.id.view_edit_build);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UpLoadParamInfo info = getList().get(position);

        holder.buildingTextView.setText(info.getUnit_name() + "每层户数");
        holder.buildingNumTextView.setText(info.getHousehold());

        if (getList().size()==1){
            holder.line.setVisibility(View.GONE);
        }else {
            if (getList().size()-1==position){
                holder.line.setVisibility(View.GONE);
            }else {
                holder.line.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    private class ViewHolder {
        TextView buildingTextView;
        TextView buildingNumTextView;
        View line;
    }
}
