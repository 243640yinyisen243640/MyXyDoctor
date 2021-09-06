package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;

import com.xy.xydoctor.bean.community_manamer.UseMedicineListBean;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class UserMedicineChildListAdapter extends CommonAdapter<UseMedicineListBean.DataBean.PlanListBean> {

    private String type;
    private IAdapterViewClickListener clickListener;
    private int parentPosi;

    public UserMedicineChildListAdapter(Context context, int layoutId, List<UseMedicineListBean.DataBean.PlanListBean> datas, String type, int parentPosi, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.clickListener = clickListener;
        this.parentPosi = parentPosi;
    }


    @Override
    protected void convert(ViewHolder viewHolder, UseMedicineListBean.DataBean.PlanListBean item, int position) {


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
