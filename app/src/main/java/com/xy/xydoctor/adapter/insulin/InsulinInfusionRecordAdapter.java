package com.xy.xydoctor.adapter.insulin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.InsulinBaseInfo;

import java.util.List;


/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinInfusionRecordAdapter extends BaseAdapter {
    private Context context;
    private List<InsulinBaseInfo> list;

    public InsulinInfusionRecordAdapter(Context context, List<InsulinBaseInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //        if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_insulin_infusion_record, parent, false);


        //        }
        return convertView;
    }


}
