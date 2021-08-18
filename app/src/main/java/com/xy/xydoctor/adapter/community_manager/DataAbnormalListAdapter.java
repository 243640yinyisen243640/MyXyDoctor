package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FollowUpChildVisitListAdapter;
import com.xy.xydoctor.bean.FollowUpVisitListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述: 数据异常·
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class DataAbnormalListAdapter extends CommonAdapter<FollowUpVisitListBean.DataBean> {

    private String type;

    public DataAbnormalListAdapter(Context context, int layoutId, List<FollowUpVisitListBean.DataBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitListBean.DataBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getPlan_name());
        MyListView lvChild = viewHolder.getView(R.id.lv_child);
        lvChild.setAdapter(new FollowUpChildVisitListAdapter(lvChild.getContext(), R.layout.item_data_abnormal_child_list, item.getPlan_list(), type));
    }
}
