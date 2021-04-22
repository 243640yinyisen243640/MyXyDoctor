//package com.xy.xydoctor.adapter;
///*
// * 类名:     MyRescissionAdapter
// * 描述:     解约列表
// * 作者:     ZWK
// * 创建日期: 2020/1/16 13:22
// */
//
//import android.content.Context;
//import android.view.View;
//
//import com.xy.xydoctor.R;
//import com.xy.xydoctor.bean.HomeSignRescissionBean;
//import com.zhy.adapter.recyclerview.CommonAdapter;
//import com.zhy.adapter.recyclerview.base.ViewHolder;
//
//import java.util.List;
//
//public class MyRescissionAdapter extends CommonAdapter<HomeSignRescissionBean> {
//
//    private OnItemClickListener onItemClickListener;
//
//    public MyRescissionAdapter(Context context, int layoutId, List<HomeSignRescissionBean> datas) {
//        super(context, layoutId, datas);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    @Override
//    protected void convert(ViewHolder holder, HomeSignRescissionBean bean, int position) {
//        holder.setText(R.id.tv_name, bean.getNickname());
//        holder.setText(R.id.tv_tel, String.format("手机号码 %s", bean.getTel()));
//        holder.setText(R.id.tv_id, String.format("身份证 %s", bean.getIdcard()));
//        holder.setText(R.id.tv_time, String.format("签约时间 %s   解约时间 %s", bean.getStarttime(), bean.getEndtime()));
//        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.OnItemClick(v);
//            }
//        });
//    }
//
//    public interface OnItemClickListener {
//        void OnItemClick(View v);
//    }
//}
