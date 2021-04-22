package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.mydevice.DeviceAddActivity;
import com.xy.xydoctor.ui.activity.mydevice.MyDeviceActivity;
import com.xy.xydoctor.ui.activity.mydevice.MyDeviceListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class DeviceAddListAdapter extends CommonAdapter<String> {
    public DeviceAddListAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_device_add_name, item);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (position) {
                    //绑定血糖仪
                    case 0:
                        String imei = SPStaticUtils.getString("imei");
                        if (TextUtils.isEmpty(imei)) {
                            intent = new Intent(Utils.getApp(), MyDeviceListActivity.class);
                        } else {
                            intent = new Intent(Utils.getApp(), MyDeviceActivity.class);
                            intent.putExtra("position", position);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                        break;
                    //绑定血压计
                    case 1:
                        String imeiBp = SPStaticUtils.getString("snnum");
                        if (TextUtils.isEmpty(imeiBp)) {
                            intent = new Intent(Utils.getApp(), DeviceAddActivity.class);
                        } else {
                            intent = new Intent(Utils.getApp(), MyDeviceActivity.class);
                            intent.putExtra("position", position);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                        break;
                }
            }
        });
    }
}
