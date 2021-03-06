package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.BaseRecycleViewAdapter;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/6/22 18:00
 * Description:
 */
public class PatientForDoctorAdapter extends BaseRecycleViewAdapter<GroupUserBeanPatient> {
    public PatientForDoctorAdapter(Context mContext, List<GroupUserBeanPatient> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_patient_for_doctor, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.ageSexTextView.setCompoundDrawablesWithIntrinsicBounds(getList().get(position).getSex() == 1 ? R.drawable.male : R.drawable.female, 0, 0, 0);
        viewHolder.ageSexTextView.setText(getList().get(position).getAge()+"");
        viewHolder.nameTextView.setText(getList().get(position).getNickname());
        if (getList().get(position).isCheck()) {
            viewHolder.checkCheckBox.setChecked(true);
        } else {
            viewHolder.checkCheckBox.setChecked(false);
        }
        Glide.with(Utils.getApp()).load(getList().get(position).getPicture()).into(viewHolder.headImgView);
        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        QMUIRadiusImageView headImgView;
        TextView nameTextView;
        TextView ageSexTextView;
        CheckBox checkCheckBox;
        LinearLayout clickLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headImgView = itemView.findViewById(R.id.img_head_remove);
            nameTextView = itemView.findViewById(R.id.tv_remove_name);
            ageSexTextView = itemView.findViewById(R.id.tv_remove_age_sex);
            checkCheckBox = itemView.findViewById(R.id.cb_remove_check);
            clickLinearLayout = itemView.findViewById(R.id.ll_click_remove);
        }

    }
}
