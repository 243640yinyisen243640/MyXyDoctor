package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.xy.xydoctor.ui.activity.director.PatientGroupListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class HomeDoctorListAdapter extends CommonAdapter<DoctorListBean> {
    public HomeDoctorListAdapter(Context context, int layoutId, List<DoctorListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DoctorListBean item, int position) {
        ColorTextView tvUnReadNum = viewHolder.getView(R.id.tv_un_read_num);
        tvUnReadNum.setVisibility(View.GONE);
        ImageView imgRightArrow = viewHolder.getView(R.id.img_right_arrow);
        imgRightArrow.setVisibility(View.VISIBLE);
        String picture = item.getPicture();
        QMUIRadiusImageView imgHead = viewHolder.getView(R.id.img_head);
        Glide.with(Utils.getApp()).load(picture).into(imgHead);
        viewHolder.setText(R.id.tv_doc_name, item.getDocname());
        viewHolder.setText(R.id.tv_login_time, item.getLogin_time());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongImUtils.logout();
                RongImUtils.connect(item.getRong_token(), new RongImInterface.ConnectCallback() {
                    @Override
                    public void onSuccess() {
                        //                        String docId = item.getUserid() + "";
                        //                        String docName = item.getDocname();
                        //                        String docHeadImg = item.getPicture();
                        //                        UserInfo docInfo = new UserInfo(docId, docName, Uri.parse(docHeadImg));
                        //                        //RongImUtils.setCurrentUserInfo(docInfo);
                    }
                });
                //保存医生id
                SPStaticUtils.put("imDocid", item.getUserid());
                SPStaticUtils.put("imDocName", item.getDocname());
                SPStaticUtils.put("imDocPic", item.getPicture());
                EventBusUtils.post(new EventMessage(ConstantParam.EventCode.DIRECTOR_REFRESH));
                Intent intent = new Intent(Utils.getApp(), PatientGroupListActivity.class);
                intent.putExtra("userid", item.getUserid());
                intent.putExtra("title", item.getDocname());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
