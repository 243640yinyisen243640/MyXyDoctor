package com.xy.xydoctor.adapter;

import android.content.Intent;
import android.content.res.TypedArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.mydevice.InputImeiActivity;
import com.xy.xydoctor.ui.activity.mydevice.ScanActivity;

import java.util.List;

public class MyDeviceListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int type;
    private String userid;

    public MyDeviceListAdapter(int type, @Nullable List<String> data,String userid) {
        super(R.layout.item_my_device_list, data);
        this.type = type;
        this.userid = userid;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String s) {
        int layoutPosition = viewHolder.getLayoutPosition();
        //设置图片
        if (4 == type) {
            TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.my_device_list_pic_new_add);
            viewHolder.setImageResource(R.id.img_device, imgArray.getResourceId(layoutPosition, 0));
        } else {
            TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.my_device_list_pic);
            viewHolder.setImageResource(R.id.img_device, imgArray.getResourceId(layoutPosition, 0));
        }
        //设置文字
        viewHolder.setText(R.id.tv_name, s);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (4 == type) {
                    Intent intent = new Intent(Utils.getApp(), InputImeiActivity.class);
                    intent.putExtra("imei", "");
                    intent.putExtra("type", type);
                    intent.putExtra("userid", userid);
                    intent.putExtra("position", layoutPosition);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    PermissionUtils
                            .permission(PermissionConstants.CAMERA)
                            .callback(new PermissionUtils.SimpleCallback() {
                                @Override
                                public void onGranted() {
                                    Intent intent = null;
                                    switch (layoutPosition) {
                                        case 0:
                                            intent = new Intent(Utils.getApp(), ScanActivity.class);
                                            intent.putExtra("type", 2);
                                            intent.putExtra("userid",userid);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            Utils.getApp().startActivity(intent);
                                            break;
                                        case 1:
                                            intent = new Intent(Utils.getApp(), ScanActivity.class);
                                            intent.putExtra("type", 1);
                                            intent.putExtra("userid",userid);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            Utils.getApp().startActivity(intent);
                                            break;
                                    }
                                }

                                @Override
                                public void onDenied() {
                                    ToastUtils.showShort("请允许使用相机权限");
                                }
                            }).request();
                }
            }
        });
    }
}
