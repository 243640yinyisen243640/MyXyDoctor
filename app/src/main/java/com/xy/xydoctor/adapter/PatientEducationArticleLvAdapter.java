package com.xy.xydoctor.adapter;

import android.content.Context;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.PatientEducationArticleListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class PatientEducationArticleLvAdapter extends CommonAdapter<PatientEducationArticleListBean.DataBean> {
    public PatientEducationArticleLvAdapter(Context context, int layoutId, List<PatientEducationArticleListBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PatientEducationArticleListBean.DataBean item, int position) {
        int type = item.getType();
        switch (type) {
            case 1:
                viewHolder.setImageResource(R.id.img_type, R.drawable.patient_education_video);
                break;
            case 2:
                viewHolder.setImageResource(R.id.img_type, R.drawable.patient_education_music);
                break;
            case 3:
                viewHolder.setImageResource(R.id.img_type, R.drawable.patient_education_text);
                break;
        }
        viewHolder.setText(R.id.tv_title, item.getTitle());
        viewHolder.setText(R.id.tv_sub_title, item.getSubtitle());
        viewHolder.setVisible(R.id.img_check, false);
    }
}
