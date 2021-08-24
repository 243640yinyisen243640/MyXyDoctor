package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.UseMedicineListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述: 数用药提醒
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class UserMedicineListAdapter extends CommonAdapter<UseMedicineListBean.DataBean> {
    private IAdapterViewClickListener clickListener;
    private String type;

    public UserMedicineListAdapter(Context context, int layoutId, List<UseMedicineListBean.DataBean> datas, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.clickListener = clickListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, UseMedicineListBean.DataBean item, int position) {
        viewHolder.setText(R.id.tv_user_medicine_phone, item.getPlan_name());
        MyListView lvChild = viewHolder.getView(R.id.lv_user_medicine_child);
        TextView phoneTextView = viewHolder.getView(R.id.tv_user_medicine_phone);
        OnAdapterItemClickListener adapterItemClickListener = new OnAdapterItemClickListener(position);

        phoneTextView.setOnClickListener(adapterItemClickListener);
        lvChild.setAdapter(new UserMedicineChildListAdapter(lvChild.getContext(), R.layout.item_user_medicine_child_list, item.getPlan_list(), type, position, clickListener));
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
