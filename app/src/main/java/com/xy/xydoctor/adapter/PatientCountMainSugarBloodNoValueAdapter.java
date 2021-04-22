//package com.xy.xydoctor.adapter;
//
//import android.content.Intent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.blankj.utilcode.util.PhoneUtils;
//import com.blankj.utilcode.util.Utils;
//import com.bumptech.glide.Glide;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.xy.xydoctor.R;
//import com.xy.xydoctor.bean.PatientCountListBean;
//import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
//
//import java.util.List;
//
///**
// * 描述:
// * 作者: LYD
// * 创建日期: 2020/11/10 14:24
// */
//public class PatientCountMainSugarBloodNoValueAdapter extends BaseQuickAdapter<PatientCountListBean, BaseViewHolder> {
//    public PatientCountMainSugarBloodNoValueAdapter(@Nullable List<PatientCountListBean> data) {
//        super(R.layout.item_patient_count_main_sugar_blood_no_value, data);
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, PatientCountListBean item) {
//        ImageView imgHead = helper.getView(R.id.img_head);
//        Glide.with(Utils.getApp())
//                .load(item.getPicture())
//                .placeholder(R.drawable.patient_count_main_default_head)
//                .error(R.drawable.patient_count_main_default_head)
//                .into(imgHead);
//        helper.setText(R.id.tv_name, item.getNickname());
//        TextView tvTel = helper.getView(R.id.tv_tel);
//        tvTel.setText(item.getUsername());
//        tvTel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //直接拨打电话
//                PhoneUtils.dial(item.getUsername());
//            }
//        });
//        helper.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //跳转患者详情
//                Intent intent = new Intent(Utils.getApp(), PatientInfoActivity.class);
//                intent.putExtra("userid", item.getUserid() + "");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Utils.getApp().startActivity(intent);
//            }
//        });
//    }
//}
