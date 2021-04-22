package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyd.baselib.base.adapter.XyBaseAdapter;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.ui.activity.groupmanage.GroupMemberListActivity;

import java.util.List;

public class SelectGroupAdapter extends XyBaseAdapter {

    private Context mContext;
    private List<GroupListBean> mList;

    public SelectGroupAdapter(Context mContext, List mList) {
        super(mContext, mList);
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_manage_check, parent, false);
            viewHolder.rlItem = convertView.findViewById(R.id.rl_item);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GroupListBean groupListBean = mList.get(position);
        viewHolder.tvName.setText(groupListBean.getGname());
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GroupMemberListActivity.class);
                intent.putExtra("gid", groupListBean.getGid() + "");
                intent.putExtra("title", groupListBean.getGname());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout rlItem;
        TextView tvName;
    }


}
