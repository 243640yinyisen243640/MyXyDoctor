package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpVisitListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述: 数据异常·
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class DataAbnormalListAdapter extends CommonAdapter<FollowUpVisitListBean.DataBean> {
    private IAdapterViewClickListener clickListener;
    private String type;

    public DataAbnormalListAdapter(Context context, int layoutId, List<FollowUpVisitListBean.DataBean> datas, String type, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.clickListener = clickListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitListBean.DataBean item, int position) {
        viewHolder.setText(R.id.tv_data_abnormal_name, item.getPlan_name());
        MyListView lvChild = viewHolder.getView(R.id.lv_data_abnormal_child);
        LinearLayout clickLinearLayout = viewHolder.getView(R.id.ll_data_abnormal_click);
        OnAdapterItemClickListener adapterItemClickListener = new OnAdapterItemClickListener(position);

        clickLinearLayout.setOnClickListener(adapterItemClickListener);
        lvChild.setAdapter(new DataAbnormalChildVisitListAdapter(lvChild.getContext(), R.layout.item_data_abnormal_child_list, item.getPlan_list(), type, position, clickListener));
    }

    private class OnAdapterItemClickListener implements View.OnClickListener {
        private int position;

        public OnAdapterItemClickListener(int position) {
            this.position = position;

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.adapterClickListener(position, v);
            }
        }
    }

}
