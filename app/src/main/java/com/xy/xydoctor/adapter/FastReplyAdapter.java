package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FastReplyListBean;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FastReplyAdapter extends CommonAdapter<FastReplyListBean> {
    private AdapterClickImp adapterClickImp;

    public FastReplyAdapter(Context context, int layoutId, List<FastReplyListBean> datas, AdapterClickImp adapterClickImp) {
        super(context, layoutId, datas);
        this.adapterClickImp = adapterClickImp;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FastReplyListBean item, int position) {
        viewHolder.setText(R.id.tv_reply_content, item.getContent());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterClickImp.onAdapterClick(viewHolder.getConvertView(), position);
            }
        });
    }
}
