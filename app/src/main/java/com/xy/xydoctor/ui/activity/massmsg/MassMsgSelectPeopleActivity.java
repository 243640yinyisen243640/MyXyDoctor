package com.xy.xydoctor.ui.activity.massmsg;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.GroupMemberSelectAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.PatientCountListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 选择消息接收人
 * 作者: LYD
 * 创建日期: 2019/3/11 20:36
 * key:type  value:{0:群发消息   1:患者情况统计群发消息  2:家签消息群发  3:患教}
 */
public class MassMsgSelectPeopleActivity extends BaseActivity {
    private static final String TAG = "MassMsgSelectPeople";
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.bt_group_add)
    Button btGroupAdd;


    private List<GroupUserBean> list = new ArrayList<>();
    private GroupMemberSelectAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mass_msg_select_people;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setIsShowMultiCheck();
        setFrom();
    }

    /**
     * 设置全选
     */
    private void setIsShowMultiCheck() {
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            getTvMore().setText("全选");
            getTvMore().setVisibility(View.VISIBLE);
            getTvMore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list != null && list.size() > 0) {
                        if ("全选".equals(getTvMore().getText().toString())) {
                            //设置全选
                            for (int i = 0; i < list.size(); i++) {
                                lvList.setItemChecked(i, true);
                            }
                            getTvMore().setText("反选");
                        } else {
                            //设置反选
                            for (int i = 0; i < list.size(); i++) {
                                lvList.setItemChecked(i, false);
                            }
                            getTvMore().setText("全选");
                        }
                    }
                }
            });
        } else {
            getTvMore().setVisibility(View.GONE);
        }
    }

    /**
     * 设置
     */
    private void setFrom() {
        String type = getIntent().getStringExtra("type");
        String gname = getIntent().getStringExtra("gname");
        if ("1".equals(type)) {
            //提醒消息
            setTitle("消息接收人");
            getCountList();
        } else {
            //患教 群发
            setTitle(gname);
            getGroupList();
        }
    }


    /**
     * 获取患者统计列表
     */
    private void getCountList() {
        int mainPosition = getIntent().getIntExtra("mainPosition", 0);
        int listPosition = getIntent().getIntExtra("listPosition", 0);
        String type;
        String url;
        if (-1 == mainPosition) {
            //家签
            url = XyUrl.HOME_SIGN_MASS_SELECT_PEOPLE;
            type = "-1";
        } else if (0 == mainPosition) {
            //血糖
            type = "2";
            url = XyUrl.GET_APPLY_TO_HOSPITAL_LIST;
        } else {
            //血压
            type = "1";
            url = XyUrl.GET_APPLY_TO_HOSPITAL_LIST;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (0 == mainPosition) {
            //血糖
            String beginTime = getIntent().getStringExtra("beginTime");
            String endTime = getIntent().getStringExtra("endTime");
            String beginSugar = getIntent().getStringExtra("beginSugar");
            String endSugar = getIntent().getStringExtra("endSugar");
            map.put("type", type);
            map.put("style", listPosition + "");
            map.put("starttime", beginTime);
            map.put("endtime", endTime);
            map.put("startSugar", beginSugar);
            map.put("endSugar", endSugar);
            //map.put("time", time);
        } else if (1 == mainPosition) {
            //血压
            String time = getIntent().getStringExtra("time");
            map.put("type", type);
            map.put("style", listPosition + 1 + "");
            map.put("time", time);
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponseList(PatientCountListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<PatientCountListBean>>() {
                    @Override
                    public void accept(List<PatientCountListBean> data) throws Exception {
                        for (int i = 0; i < data.size(); i++) {
                            PatientCountListBean bean = data.get(i);
                            int userid = bean.getUserid();
                            String imgUrl = bean.getPicture();
                            String name = bean.getNickname();
                            int sex = bean.getSex();
                            int age = bean.getAge();
                            GroupUserBean groupUserBean = new GroupUserBean(userid, imgUrl, name, sex, age);
                            list.add(groupUserBean);
                        }
                        adapter = new GroupMemberSelectAdapter(Utils.getApp(), R.layout.item_group_member_list_select, list);
                        lvList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        lvList.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 获取群组列表
     */
    private void getGroupList() {
        String gid = getIntent().getStringExtra("gid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("gid", gid);
        RxHttp.postForm(XyUrl.GET_GROUP_USER)
                .addAll(map)
                .asResponseList(GroupUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupUserBean>>() {
                    @Override
                    public void accept(List<GroupUserBean> getList) throws Exception {
                        list = getList;
                        adapter = new GroupMemberSelectAdapter(Utils.getApp(), R.layout.item_group_member_list_select, list);
                        lvList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        lvList.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.bt_group_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_group_add:
                SparseBooleanArray checkedItemPositions = lvList.getCheckedItemPositions();
                ArrayList<Integer> checkedList = new ArrayList<>();
                for (int i = 0; i < checkedItemPositions.size(); i++) {
                    int key = checkedItemPositions.keyAt(i);
                    boolean isChecked = checkedItemPositions.valueAt(i);
                    if (isChecked) {
                        checkedList.add(key);
                    }
                }
                if (checkedList.size() < 1) {
                    ToastUtils.showShort("请先选择消息接收人");
                } else {
                    List<GroupUserBean> listUser = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        boolean isChecked = checkedItemPositions.get(i);
                        if (isChecked) {
                            GroupUserBean groupUserBean = list.get(i);
                            int uid = groupUserBean.getUserid();
                            String imgUrl = groupUserBean.getPicture();
                            String name = groupUserBean.getNickname();
                            int sex = groupUserBean.getSex();
                            int age = groupUserBean.getAge();
                            listUser.add(new GroupUserBean(uid, imgUrl, name, sex, age));
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra("selectUserInfo", (Serializable) listUser);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }


}
