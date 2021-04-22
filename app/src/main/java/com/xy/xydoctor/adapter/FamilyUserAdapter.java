package com.xy.xydoctor.adapter;

import android.content.Context;
import android.widget.CheckBox;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FamilySelectUserBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FamilyUserAdapter extends CommonAdapter<FamilySelectUserBean> {

    public FamilyUserAdapter(Context context, int layoutId, List<FamilySelectUserBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FamilySelectUserBean item, int position) {
        String relation = item.getRelationname();
        String nickname = item.getNickname();
        String name = relation + ":" + nickname;
        viewHolder.setText(R.id.tv_name, name);
        //选中状态设置
        CheckBox cbCheck = viewHolder.getView(R.id.cb_check);
        boolean selected = item.isSelected();
        if (selected) {
            cbCheck.setChecked(true);
        } else {
            cbCheck.setChecked(false);
        }
    }


}
