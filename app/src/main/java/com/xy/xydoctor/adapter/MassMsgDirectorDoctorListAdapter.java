package com.xy.xydoctor.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.DoctorListBean;

import java.util.HashMap;
import java.util.List;

public class MassMsgDirectorDoctorListAdapter extends BaseQuickAdapter<DoctorListBean, BaseViewHolder> {
    public HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private List<DoctorListBean> datas;

    public MassMsgDirectorDoctorListAdapter(@Nullable List<DoctorListBean> datas) {
        super(R.layout.item_mass_msg_director_doctor_list, datas);
        this.datas = datas;
        init();
    }

    private void init() {
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DoctorListBean doctorListBean) {
        ImageView imgCheck = holder.getView(R.id.img_check);
        //选中处理
        int position = holder.getLayoutPosition();
        Boolean check = isSelected.get(position);
        if (check) {
            imgCheck.setImageResource(R.drawable.mass_msg_director_checked);
        } else {
            imgCheck.setImageResource(R.drawable.mass_msg_director_check);
        }
        holder.setText(R.id.tv_doctor_name, doctorListBean.getDocname());
        //添加点击事件
        holder.addOnClickListener(R.id.img_check, R.id.tv_doctor_name);
    }
}
