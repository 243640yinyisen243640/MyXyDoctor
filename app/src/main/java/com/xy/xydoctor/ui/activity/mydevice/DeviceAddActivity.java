package com.xy.xydoctor.ui.activity.mydevice;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 描述: 设备添加
 * 作者: LYD
 * 创建日期: 2019/3/22 11:10
 */
public class DeviceAddActivity extends BaseActivity {
    private static final String TAG = "10010";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("血压设备");
    }


    @OnClick(R.id.bt_add)
    public void onViewClicked() {
        openCamera();
    }

    private void openCamera() {
        PermissionUtils
                .permission(PermissionConstants.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getPageContext(), ScanActivity.class);
                        intent.putExtra("type", 5);
                        intent.putExtra("isSelf", 2);
                        startActivity(intent);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用相机权限");
                    }
                }).request();
    }
}
