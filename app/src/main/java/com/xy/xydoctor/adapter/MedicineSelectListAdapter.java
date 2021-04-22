package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.MedicineSelectListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.ui.activity.smart.smartmakepolicy.MedicineSelectDetailListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MedicineSelectListAdapter extends CommonAdapter<MedicineSelectListBean.GroupsBean> {
    public MedicineSelectListAdapter(Context context, int layoutId, List<MedicineSelectListBean.GroupsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MedicineSelectListBean.GroupsBean item, int position) {
        viewHolder.setText(R.id.tv_group_name, item.getGroupname());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送黏性事件
                EventBusUtils.postSticky(new EventMessage<>(ConstantParam.EventCode.SEND_GROUP_ID, item.getId() + ""));
                //查看药物选择详情
                Intent intent = new Intent(Utils.getApp(), MedicineSelectDetailListActivity.class);
                intent.putExtra("id", item.getId() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
