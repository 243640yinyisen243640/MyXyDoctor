package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyd.baselib.base.adapter.XyBaseAdapter;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowListBean;

import java.util.List;

public class FollowUpListAdapter extends XyBaseAdapter<FollowListBean> {
    private int pos;
    private List<FollowListBean> list;

    public FollowUpListAdapter(Context mContext, List<FollowListBean> mList) {
        super(mContext, mList);
        this.list = mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_follow_list_template, null);
            viewHolder.chooseTextView = convertView.findViewById(R.id.tv_follow_up_list_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FollowListBean info = getList().get(position);
        viewHolder.chooseTextView.setText(info.getPlanname());
        if (position == pos) {
            viewHolder.chooseTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.follow_up_visit_add_check_ed, 0);
        } else {
            viewHolder.chooseTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.follow_up_visit_add_check, 0);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView chooseTextView;
    }

    public void setClickPosition(int position) {
        this.pos = position;
        this.notifyDataSetChanged();
    }

    public int getClickPosition() {
        return pos;
    }
}
