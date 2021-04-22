package com.xy.xydoctor.ui.activity.mydevice;

import android.os.Bundle;
import android.widget.ListView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.DeviceAddListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 设备添加列表
 * 作者: LYD
 * 创建日期: 2020/1/6 9:26
 */
public class DeviceAddListActivity extends BaseActivity {
    @BindView(R.id.lv_list)
    ListView lvList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_add_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("添加设备");
        setLvAdapter();
    }


    private void setLvAdapter() {
        List<String> list = new ArrayList<>();
        list.add("绑定血糖仪");
        list.add("绑定血压计");
        DeviceAddListAdapter adapter = new DeviceAddListAdapter(getPageContext(), R.layout.item_device_add_list, list);
        lvList.setAdapter(adapter);
    }

}
