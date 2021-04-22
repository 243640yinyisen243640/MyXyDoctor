package com.xy.xydoctor.ui.activity.groupmanage;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.GroupMemberSelectAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 分组成员添加
 * 作者: LYD
 * 创建日期: 2019/2/27 11:45
 */
public class GroupMemberAddActivity extends BaseActivity {
    private static final String TAG = "GroupMemberAddActivity";
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;

    @BindView(R.id.bt_group_add)
    Button btGroupAdd;

    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<GroupUserBean> list = new ArrayList<>();//ListView显示的数据
    private List<GroupUserBean> tempList;//用于临时保存ListView显示的数据
    private GroupMemberSelectAdapter adapter;

    private void initReFresh() {
        srlList.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                initValue();
                srlList.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                initValue();
            }
        });
    }

    private void initValue() {
        String gid = getIntent().getStringExtra("gid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("gid", gid);
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_UNGROUP_LIST)
                .addAll(map)
                .asResponseList(GroupUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupUserBean>>() {
                    @Override
                    public void accept(List<GroupUserBean> myTreatPlanBeans) throws Exception {
                        tempList = myTreatPlanBeans;
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlList.finishLoadMoreWithNoMoreData();
                        } else {
                            srlList.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            //
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            //
                            list.addAll(tempList);
                            adapter = new GroupMemberSelectAdapter(Utils.getApp(), R.layout.item_group_member_list_select, list);
                            lvList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                            lvList.setAdapter(adapter);
                        } else {
                            list.addAll(tempList);
                            adapter.notifyDataSetChanged();
                        }
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
                if (checkedItemPositions != null) {
                    ArrayList<Integer> checkedList = new ArrayList<>();
                    for (int i = 0; i < checkedItemPositions.size(); i++) {
                        int key = checkedItemPositions.keyAt(i);
                        boolean value = checkedItemPositions.valueAt(i);
                        if (value) {
                            checkedList.add(key);
                        }
                    }
                    if (checkedList.size() < 1) {
                        ToastUtils.showShort("请先选择你要添加的成员");
                        //return;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            boolean b = checkedItemPositions.get(i);
                            if (b) {
                                int uid = list.get(i).getUserid();
                                stringBuilder.append(uid);
                                stringBuilder.append(",");
                            }
                        }
                        toAdd(stringBuilder.toString());
                    }
                }
                break;
        }
    }

    private void toAdd(String groupUserStr) {
        //截取最后,
        String substring = groupUserStr.substring(0, groupUserStr.length() - 1);
        String gid = getIntent().getStringExtra("gid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupuser", substring);
        map.put("gid", gid);

        RxHttp.postForm(XyUrl.ADD_GROUP_USER)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("添加成功");
                        finish();
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.GROUP_MEMBER_ADD));//刷新列表
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_member_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("添加成员");
        initValue();
        initReFresh();
    }
}
