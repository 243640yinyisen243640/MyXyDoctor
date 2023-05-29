package com.xy.xydoctor.adapter;

import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.imp.AdapterClickImp;

import java.util.List;

public class MyDeviceListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private AdapterClickImp clickImp;
    private int isSelf;

    public MyDeviceListAdapter(@Nullable List<String> data, int isSelf, AdapterClickImp clickImp) {
        super(R.layout.item_my_device_list, data);
        this.clickImp = clickImp;
        this.isSelf = isSelf;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String s) {
        Log.i("yys", "MyDeviceListAdaptertype==" + isSelf);
        int layoutPosition = viewHolder.getLayoutPosition();
        //设置图片
        if (1 == isSelf) {
            TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.my_device_list_pic);
            viewHolder.setImageResource(R.id.img_device, imgArray.getResourceId(layoutPosition, 0));
        } else {
            TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.my_device_list_pic_new_add);
            viewHolder.setImageResource(R.id.img_device, imgArray.getResourceId(layoutPosition, 0));
        }
        //设置文字
        viewHolder.setText(R.id.tv_name, s);

        SingleOnClick click = new SingleOnClick(layoutPosition);
        viewHolder.setOnClickListener(R.id.ll_device_click, click);

        //        viewHolder.itemView.setOnClickListener(v -> {
        //
        //            PermissionUtils
        //                    .permission(PermissionConstants.CAMERA)
        //                    .callback(new PermissionUtils.SimpleCallback() {
        //                        @Override
        //                        public void onGranted() {
        //                            Intent intent = null;
        //                            //是否给自己添加设备  1:绑定设备 血糖仪绑定  2：绑定设备 血压计绑定 3：患者详情绑定血糖仪和血压计
        //                            if (1 == isSelf) {
        //                                switch (layoutPosition) {
        //                                    case 0:
        //                                        intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                        intent.putExtra("type", 3);
        //                                        intent.putExtra("isSelf", 1);
        //                                        intent.putExtra("userid", userid);
        //                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                        Utils.getApp().startActivity(intent);
        //                                        break;
        //                                    case 1:
        //                                        intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                        intent.putExtra("type", 4);
        //                                        intent.putExtra("isSelf", 1);
        //                                        intent.putExtra("userid", userid);
        //                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                        Utils.getApp().startActivity(intent);
        //                                        break;
        //                                    case 2:
        //                                        intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                        intent.putExtra("type", 6);
        //                                        intent.putExtra("isSelf", 1);
        //                                        intent.putExtra("userid", userid);
        //                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                        Utils.getApp().startActivity(intent);
        //                                        break;
        //                                    default:
        //                                        break;
        //                                }
        //                            } else {
        //                                switch (layoutPosition) {
        //                                    case 0:
        //                                        intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                        intent.putExtra("type", 3);
        //                                        intent.putExtra("isSelf", 3);
        //                                        intent.putExtra("userid", userid);
        //                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                        Utils.getApp().startActivity(intent);
        //                                        break;
        //                                    case 1:
        //                                        intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                        intent.putExtra("type", 4);
        //                                        intent.putExtra("isSelf", 3);
        //                                        intent.putExtra("userid", userid);
        //                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                        Utils.getApp().startActivity(intent);
        //                                        break;
        //                                    case 2:
        //                                        if (TextUtils.isEmpty(suNum)) {
        //                                            intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                            intent.putExtra("type", 5);
        //                                            intent.putExtra("isSelf", 3);
        //                                            intent.putExtra("userid", userid);
        //                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                            Utils.getApp().startActivity(intent);
        //                                        } else {
        //                                            intent = new Intent(Utils.getApp(), InputImeiDetailsActivity.class);
        //                                            intent.putExtra("imei", suNum);
        //                                            Utils.getApp().startActivity(intent);
        //                                        }
        //
        //                                        break;
        //                                    case 3:
        //                                        intent = new Intent(Utils.getApp(), ScanActivity.class);
        //                                        intent.putExtra("type", 6);
        //                                        intent.putExtra("isSelf", 3);
        //                                        intent.putExtra("userid", userid);
        //                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                                        Utils.getApp().startActivity(intent);
        //                                        break;
        //                                    default:
        //                                        break;
        //                                }
        //                            }
        //
        //                        }
        //
        //                        @Override
        //                        public void onDenied() {
        //                            ToastUtils.showShort("请允许使用相机权限");
        //                        }
        //                    }).request();
        //
        //        });
    }


    private class SingleOnClick implements View.OnClickListener {
        private int position;

        public SingleOnClick(int position) {
            this.position = position;

        }

        @Override
        public void onClick(View v) {
            if (clickImp != null) {
                clickImp.onAdapterClick(v, position);
            }
        }
    }

    //    private void beforClick(int layoutPosition) {
    //        if (4 == type) {
    //            Intent intent;
    //            switch (layoutPosition) {
    //                case 0:
    //
    //                case 1:
    //
    //                case 2:
    //                    intent = new Intent(Utils.getApp(), InputImeiActivity.class);
    //                    intent.putExtra("imei", "");
    //                    intent.putExtra("type", type);
    //                    intent.putExtra("userid", userid);
    //                    intent.putExtra("position", layoutPosition);
    //                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                    Utils.getApp().startActivity(intent);
    //                    break;
    //                case 3:
    //                    intent = new Intent(Utils.getApp(), InputImeiActivity.class);
    //                    intent.putExtra("imei", "");
    //                    intent.putExtra("type", 5);
    //                    intent.putExtra("userid", userid);
    //                    intent.putExtra("position", layoutPosition);
    //                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                    Utils.getApp().startActivity(intent);
    //                    break;
    //                default:
    //                    break;
    //            }
    //            //                Intent intent = new Intent(Utils.getApp(), InputImeiActivity.class);
    //            //                intent.putExtra("imei", "");
    //            //                intent.putExtra("type", type);
    //            //                intent.putExtra("userid", userid);
    //            //                intent.putExtra("position", layoutPosition);
    //            //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //            //                Utils.getApp().startActivity(intent);
    //        } else {
    //            PermissionUtils
    //                    .permission(PermissionConstants.CAMERA)
    //                    .callback(new PermissionUtils.SimpleCallback() {
    //                        @Override
    //                        public void onGranted() {
    //                            Intent intent = null;
    //                            switch (layoutPosition) {
    //                                case 0:
    //                                    intent = new Intent(Utils.getApp(), ScanActivity.class);
    //                                    intent.putExtra("type", 2);
    //                                    intent.putExtra("userid", userid);
    //                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                                    Utils.getApp().startActivity(intent);
    //                                    break;
    //                                case 1:
    //                                    intent = new Intent(Utils.getApp(), ScanActivity.class);
    //                                    intent.putExtra("type", 1);
    //                                    intent.putExtra("userid", userid);
    //                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                                    Utils.getApp().startActivity(intent);
    //                                    break;
    //                                case 2:
    //                                    intent = new Intent(Utils.getApp(), ScanActivity.class);
    //                                    intent.putExtra("type", 5);
    //                                    intent.putExtra("userid", userid);
    //                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                                    Utils.getApp().startActivity(intent);
    //                                    break;
    //                                default:
    //                                    break;
    //                            }
    //                        }
    //
    //                        @Override
    //                        public void onDenied() {
    //                            ToastUtils.showShort("请允许使用相机权限");
    //                        }
    //                    }).request();
    //        }
    //    }
}
