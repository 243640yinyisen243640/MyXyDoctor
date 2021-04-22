package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.CheckBean;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordCheckDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class CheckAdapter extends CommonAdapter<CheckBean> {
    public CheckAdapter(Context context, int layoutId, List<CheckBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CheckBean checkBean, int position) {
        List<String> listPic = checkBean.getPicurl();
        ImageView imgCheck = holder.getView(R.id.img_check);
        if (listPic != null && listPic.size() > 0) {
            String picUrl = listPic.get(0);
            Glide.with(Utils.getApp()).load(picUrl).into(imgCheck);
        }
        holder.setText(R.id.tv_time, checkBean.getDatetime());
        holder.setText(R.id.tv_title, checkBean.getHydname());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), HealthRecordCheckDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailInfo", checkBean);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
