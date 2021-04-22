package com.xy.xydoctor.adapter;

import android.content.Context;

import com.xy.xydoctor.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class ApplyToHospitalDetailLvAdapter extends CommonAdapter {
    private List<String> leftStingList;
    private List<String> rightStingList;

    public ApplyToHospitalDetailLvAdapter(Context context, int layoutId, List datas, List leftStingList, List rightStingList) {
        super(context, layoutId, datas);
        this.leftStingList = leftStingList;
        this.rightStingList = rightStingList;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        viewHolder.setText(R.id.tv_left, leftStingList.get(position));
        viewHolder.setText(R.id.tv_right, rightStingList.get(position));
    }

}
