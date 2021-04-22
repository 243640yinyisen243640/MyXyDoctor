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

public class MyDeviceListActivity extends BaseActivity {
    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_device_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("选择设备");
        setRv();
    }

    private void setRv() {
        //String guid = getIntent().getStringExtra("guid");
        int type = getIntent().getIntExtra("type", -1);
        if (4 == type) {
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.my_device_list_name_new_add);
            List<String> list = Arrays.asList(stringArray);
            rvDeviceList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvDeviceList.setAdapter(new MyDeviceListAdapter(type, list));
        }else{
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.my_device_list_name);
            List<String> list = Arrays.asList(stringArray);
            rvDeviceList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvDeviceList.setAdapter(new MyDeviceListAdapter(type, list));
        }
    }


}