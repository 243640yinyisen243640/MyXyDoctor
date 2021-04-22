package com.xy.xydoctor.ui.activity.director_massmsg;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SelectPeopleDirectorAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.ui.activity.director_patienteducation.PatientEductionDirectorActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2020/5/19 10:43
 */
public class SelectPeopleListDirectorActivity extends BaseActivity {
    private static final String TAG = "SelectPeopleActivity";
    @BindView(R.id.rv_select_list)
    RecyclerView rvList;
    @BindView(R.id.bt_group_add)
    Button btGroupAdd;


    private List<GroupUserBean> itemList;
    private List<GroupUserBean> checkList;
    private List<String> selectData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_people_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("消息接收人");
        setLv();
    }

    private void setLv() {
        itemList = (List<GroupUserBean>) getIntent().getSerializableExtra("userids");
        checkList = (List<GroupUserBean>) getIntent().getSerializableExtra("checkList");
        //提示数据为空
        if (itemList == null || itemList.size() == 0) {
            ToastUtils.showShort("数据为空");
        }
        //相同
        List<GroupUserBean> sameList = new ArrayList<>();
        if (checkList != null && checkList.size() > 0) {
            for (int i = 0; i < checkList.size(); i++) {
                int userid = checkList.get(i).getUserid();
                for (int j = 0; j < itemList.size(); j++) {
                    if (userid == itemList.get(j).getUserid()) {
                        sameList.add(itemList.get(j));
                        selectData.add(j + "");
                    }
                }
            }
        }
        //减去
        Log.e(TAG, "原来所有的大小==" + checkList.size());
        Log.e(TAG, "原来单行的大小==" + itemList.size());
        //迭代器删除
        Iterator<GroupUserBean> itCheck = checkList.iterator();
        while (itCheck.hasNext()) {
            //next()返回下一个元素
            GroupUserBean checkBean = itCheck.next();
            int userid = checkBean.getUserid();
            Iterator<GroupUserBean> itItem = itemList.iterator();
            while (itItem.hasNext()) {
                GroupUserBean itemBean = itItem.next();
                int userid1 = itemBean.getUserid();
                if (userid == userid1) {
                    itCheck.remove();
                }
            }
        }
        Log.e(TAG, "删除后大小==" + checkList.size());
        rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        SelectPeopleDirectorAdapter adapter = new SelectPeopleDirectorAdapter(itemList, sameList);
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (!adapter.isSelected.get(position)) {
                    //修改map的值保存状态
                    adapter.isSelected.put(position, true);
                    adapter.notifyItemChanged(position);
                    selectData.add(position + "");
                    Log.e(TAG, "selectData==" + selectData);
                } else {
                    //修改map的值保存状态
                    adapter.isSelected.put(position, false);
                    adapter.notifyItemChanged(position);
                    selectData.remove(position + "");
                    Log.e(TAG, "selectData==" + selectData);
                }
            }
        });
    }


    @OnClick(R.id.bt_group_add)
    public void onViewClicked() {
        if (selectData.size() <= 0) {
            ToastUtils.showShort("请选择消息接收人");
            return;
        } else {
            List<GroupUserBean> currentSelectList = new ArrayList<>();
            for (int i = 0; i < selectData.size(); i++) {
                String selectPosition = selectData.get(i);
                GroupUserBean groupUserBean = itemList.get(TurnsUtils.getInt(selectPosition, 0));
                currentSelectList.add(groupUserBean);
            }
            checkList.addAll(currentSelectList);
            //传递选中数据
            EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.SEND_SELECT_PEOPLE, checkList));
            String type = getIntent().getStringExtra("type");
            if ("patientEducation".equals(type)) {
                ActivityUtils.finishToActivity(PatientEductionDirectorActivity.class, false);
            } else {
                ActivityUtils.finishToActivity(MassMsgDirectorActivity.class, false);
            }
        }

    }
}
