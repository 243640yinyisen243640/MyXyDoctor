package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.PatientCountListBean;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/9/28 15:47
 */
public class PatientCountListAdapter extends CommonAdapter<PatientCountListBean> {
    private String type;

    public PatientCountListAdapter(Context context, int layoutId, List<PatientCountListBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, PatientCountListBean item, int position) {
        viewHolder.setText(R.id.tv_name, item.getNickname());
        ImageView imgHead = viewHolder.getView(R.id.img_head);
        Glide.with(Utils.getApp())
                .load(item.getPicture())
                .error(R.drawable.patient_count_img_item_normal)
                .placeholder(R.drawable.patient_count_img_item_normal)
                .into(imgHead);
        int diabeteslei = item.getDiastole();
        int systolic = item.getSystolic();
        viewHolder.setText(R.id.tv_value, systolic + "/" + diabeteslei);
        switch (type) {
            case "0":
                viewHolder.setTextColor(R.id.tv_value, ColorUtils.getColor(R.color.red_text));
                break;
            case "1":
                viewHolder.setTextColor(R.id.tv_value, ColorUtils.getColor(R.color.blue_text));
                break;
            case "2":
                viewHolder.setTextColor(R.id.tv_value, ColorUtils.getColor(R.color.green_text));
                break;
        }
        viewHolder.setText(R.id.tv_time, item.getAddtime());
        viewHolder.setText(R.id.tv_type, item.getCategoryname());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), PatientInfoActivity.class);
                intent.putExtra("userid", item.getUserid() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
