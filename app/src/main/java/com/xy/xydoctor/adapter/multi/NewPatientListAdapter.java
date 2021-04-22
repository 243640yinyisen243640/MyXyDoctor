package com.xy.xydoctor.adapter.multi;

import android.content.Context;

import com.xy.xydoctor.bean.NewPatientListBean;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;

import java.util.List;

public class NewPatientListAdapter extends MultiItemTypeAdapter<NewPatientListBean.DataBean> {

    public NewPatientListAdapter(Context context, List<NewPatientListBean.DataBean> datas, AdapterClickImp listener) {
        super(context, datas);
        addItemViewDelegate(new Delegate0(listener));
        addItemViewDelegate(new Delegate12());
    }
}
