package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.MySugarLevel1Bean;
import com.xy.xydoctor.imp.AdapterClickLiverFilesImp;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


/**
 * 描述:  肝病档案
 * 作者: LYD
 * 创建日期: 2020/4/9 11:55
 */
public class MyLiverFilesLvAdapter extends CommonAdapter<MySugarLevel1Bean> {
    private AdapterClickLiverFilesImp clickImp;
    private int type;

    public MyLiverFilesLvAdapter(Context context, int layoutId, List<MySugarLevel1Bean> datas, AdapterClickLiverFilesImp clickImp, int type) {
        super(context, layoutId, datas);
        this.clickImp = clickImp;
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MySugarLevel1Bean item, int position) {
        //左边
        TextView tvName = viewHolder.getView(R.id.tv_left);
        //右边
        TextView tvContent = viewHolder.getView(R.id.tv_right);
        tvName.setText(item.getName());
        tvContent.setText(item.getContent());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImp.onAdapterClick(v, position, type);
            }
        });
    }
}
