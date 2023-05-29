package com.xy.xydoctor.ui.activity.mydevice;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MyDeviceListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.imp.AdapterClickImp;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 类名：
 * 传参：
 * isSelf  是否给自己添加设备  1:绑定设备 血糖仪绑定  2：绑定设备 血压计绑定 3：患者详情绑定血糖仪和血压计
 * 描述: 我的设备列表
 * 作者: beauty
 * 创建日期: 2023/4/13 16:12
 */
public class MyDeviceListActivity extends BaseActivity {

    private static final int REQUEST_CODE_FOR_REFRESH = 1;


    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;

    private String userid;

    /**
     * 是否给自己添加设备  1:绑定设备 血糖仪绑定  2：绑定设备 血压计绑定 3：患者详情绑定血糖仪和血压计
     */
    private int isSelf;
    /**
     * 血压设备设备号
     */
    private String suNum;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_device_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userid = getIntent().getStringExtra("userid");
        suNum = getIntent().getStringExtra("imei");
        isSelf = getIntent().getIntExtra("isSelf", 0);
        setTitle("选择设备");
        setRv();
    }

    private void setRv() {
        //String guid = getIntent().getStringExtra("guid");
        //是否给自己添加设备  1:绑定设备 血糖仪绑定  2：绑定设备 血压计绑定 3：患者详情绑定血糖仪和血压计
        if (1 == isSelf) {
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.my_device_list_name);
            List<String> list = Arrays.asList(stringArray);
            rvDeviceList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvDeviceList.setAdapter(new MyDeviceListAdapter(list, isSelf, new AdapterClickImp() {
                @Override
                public void onAdapterClick(View view, int position) {
                    switch (view.getId()) {
                        case R.id.ll_device_click:

                            PermissionUtils
                                    .permission(PermissionConstants.CAMERA)
                                    .callback(new PermissionUtils.SimpleCallback() {
                                        @Override
                                        public void onGranted() {
                                            Intent intent = null;
                                            //是否给自己添加设备  1:绑定设备 血糖仪绑定  2：绑定设备 血压计绑定 3：患者详情绑定血糖仪和血压计
                                            switch (position) {
                                                case 0:
                                                    intent = new Intent(getPageContext(), ScanActivity.class);
                                                    intent.putExtra("type", 3);
                                                    intent.putExtra("isSelf", 1);
                                                    intent.putExtra("userid", userid);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    break;
                                                case 1:
                                                    intent = new Intent(getPageContext(), ScanActivity.class);
                                                    intent.putExtra("type", 4);
                                                    intent.putExtra("isSelf", 1);
                                                    intent.putExtra("userid", userid);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    break;
                                                case 2:
                                                    intent = new Intent(getPageContext(), ScanActivity.class);
                                                    intent.putExtra("type", 6);
                                                    intent.putExtra("isSelf", 1);
                                                    intent.putExtra("userid", userid);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    break;
                                                default:
                                                    break;
                                            }


                                        }

                                        @Override
                                        public void onDenied() {
                                            ToastUtils.showShort("请允许使用相机权限");
                                        }
                                    }).request();
                            break;
                        default:
                            break;
                    }
                }
            }));
        } else {
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.my_device_list_name_new_add);
            List<String> list = Arrays.asList(stringArray);
            rvDeviceList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvDeviceList.setAdapter(new MyDeviceListAdapter(list, isSelf, new AdapterClickImp() {
                @Override
                public void onAdapterClick(View view, int position) {
                    switch (view.getId()) {
                        case R.id.ll_device_click:
                            Intent intent;
                            switch (position) {
                                case 0:
                                    intent = new Intent(getPageContext(), ScanActivity.class);
                                    intent.putExtra("type", 3);
                                    intent.putExtra("isSelf", 3);
                                    intent.putExtra("userid", userid);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    intent = new Intent(getPageContext(), ScanActivity.class);
                                    intent.putExtra("type", 4);
                                    intent.putExtra("isSelf", 3);
                                    intent.putExtra("userid", userid);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    break;
                                case 2:
                                    if (TextUtils.isEmpty(suNum)) {
                                        intent = new Intent(getPageContext(), ScanActivity.class);
                                        intent.putExtra("type", 5);
                                        intent.putExtra("isSelf", 3);
                                        intent.putExtra("userid", userid);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        intent = new Intent(getPageContext(), InputImeiDetailsActivity.class);
                                        intent.putExtra("imei", suNum);
                                        startActivityForResult(intent, REQUEST_CODE_FOR_REFRESH);
                                    }

                                    break;
                                case 3:
                                    intent = new Intent(getPageContext(), ScanActivity.class);
                                    intent.putExtra("type", 6);
                                    intent.putExtra("isSelf", 3);
                                    intent.putExtra("userid", userid);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FOR_REFRESH:
                    suNum = "";
                    break;
                default:
                    break;
            }
        }
    }
}