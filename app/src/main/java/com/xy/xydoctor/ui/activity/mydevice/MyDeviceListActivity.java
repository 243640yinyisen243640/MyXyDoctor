package com.xy.xydoctor.ui.activity.mydevice;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MyDeviceListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;

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
    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;

    private String userid;

    /**
     * 是否给自己添加设备  1:绑定设备 血糖仪绑定  2：绑定设备 血压计绑定 3：患者详情绑定血糖仪和血压计
     */
    private int isSelf;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_device_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userid = getIntent().getStringExtra("userid");
        isSelf = getIntent().getIntExtra("isSelf",0);
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
            rvDeviceList.setAdapter(new MyDeviceListAdapter(isSelf, list, userid));
        } else {
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.my_device_list_name_new_add);
            List<String> list = Arrays.asList(stringArray);
            rvDeviceList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvDeviceList.setAdapter(new MyDeviceListAdapter(isSelf, list, userid));
        }

    }


}