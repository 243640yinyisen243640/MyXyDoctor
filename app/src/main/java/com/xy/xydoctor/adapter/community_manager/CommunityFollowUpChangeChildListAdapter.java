package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpAgentListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class CommunityFollowUpChangeChildListAdapter extends CommonAdapter<FollowUpAgentListBean> {

    private String type;
    private IAdapterViewClickListener clickListener;
    private int parentPosi;

    public CommunityFollowUpChangeChildListAdapter(Context context, int layoutId, List<FollowUpAgentListBean> datas, String type, int parentPosi, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.clickListener = clickListener;
        this.parentPosi = parentPosi;
    }


    @Override
    protected void convert(ViewHolder viewHolder, FollowUpAgentListBean item, int position) {
        TextView callTextView = viewHolder.getView(R.id.tv_fuc_child_call_phone);
        TextView noFinishTextView = viewHolder.getView(R.id.tv_fuc_child_no_finish);
        MyClickListener clickListener = new MyClickListener(position);
        callTextView.setOnClickListener(clickListener);
        noFinishTextView.setOnClickListener(clickListener);
    }

    /**
     * 点击事件
     */
    private class MyClickListener implements View.OnClickListener {

        private int childPosi;

        public MyClickListener(int childPosi) {
            this.childPosi = childPosi;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.adapterClickListener(parentPosi, childPosi, v);
            }
        }
    }
}
