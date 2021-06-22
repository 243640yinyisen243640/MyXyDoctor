package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.librongim.rongim.RongImInterface;
import com.lyd.librongim.rongim.RongImUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.ui.activity.director.DoctorAddAndEditActivity;
import com.xy.xydoctor.ui.activity.todo.ToDoListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class DoctorListAdapter extends CommonAdapter<DoctorListBean> {
    private static final String TAG = "DoctorListAdapter";
    private String type;

    public DoctorListAdapter(Context context, int layoutId, List<DoctorListBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, DoctorListBean item, int position) {
        ColorTextView tvUnReadNum = viewHolder.getView(R.id.tv_un_read_num);
        ImageView imgRightArrow = viewHolder.getView(R.id.img_right_arrow);
        if ("homeDoctor".equals(type)) {
            tvUnReadNum.setVisibility(View.GONE);
            imgRightArrow.setVisibility(View.VISIBLE);
        } else {
            tvUnReadNum.setVisibility(View.VISIBLE);
            imgRightArrow.setVisibility(View.GONE);
            int totalUnReadCount = item.getNum();
            if (totalUnReadCount > 0) {
                tvUnReadNum.setVisibility(View.VISIBLE);
                if (totalUnReadCount > 999) {
                    tvUnReadNum.setText("...");
                } else {
                    tvUnReadNum.setText(totalUnReadCount + "");
                }
            } else {
                tvUnReadNum.setVisibility(View.GONE);
            }
        }

        String picture = item.getPicture();
        QMUIRadiusImageView imgHead = viewHolder.getView(R.id.img_head);
        Glide.with(Utils.getApp()).load(picture).into(imgHead);
        viewHolder.setText(R.id.tv_doc_name, item.getDocname());
        viewHolder.setText(R.id.tv_login_time, item.getLogin_time());

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("homeDoctor".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), DoctorAddAndEditActivity.class);
                    intent.putExtra("doctorInfo", item);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    RongImUtils.logout();
                    RongImUtils.connect(item.getRong_token(), new RongImInterface.ConnectCallback() {
                        @Override
                        public void onSuccess() {
                            Log.e(TAG, "主任代替医生 连接融云服务器成功");
                        }
                    });
                    //保存医生id
                    SPStaticUtils.put("imDocid", item.getUserid());
                    SPStaticUtils.put("imDocName", item.getDocname());
                    SPStaticUtils.put("imDocPic", item.getPicture());
                    EventBusUtils.post(new EventMessage(ConstantParam.EventCode.DIRECTOR_REFRESH));
                    //待办事项
                    Intent intent = new Intent(Utils.getApp(), ToDoListActivity.class);
                    intent.putExtra("userid", item.getUserid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });
    }
}
