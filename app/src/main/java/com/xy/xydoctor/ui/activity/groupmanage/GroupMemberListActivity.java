package com.xy.xydoctor.ui.activity.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgSelectAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.DialogUtils;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 分组成员列表
 * 作者: LYD
 * 创建日期: 2019/2/27 11:45
 */
@BindEventBus
public class GroupMemberListActivity extends BaseEventBusActivity {
    @BindView(R.id.lv_group_member_list)
    ListView lvGroupMemberList;

    private CommonAdapter<GroupUserBean> adapter;
    private List<GroupUserBean> groupMemberList;

    private void initValue() {
        String gid = getIntent().getStringExtra("gid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("gid", gid);
        RxHttp.postForm(XyUrl.GET_GROUP_USER)
                .addAll(map)
                .asResponseList(GroupUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupUserBean>>() {
                    @Override
                    public void accept(List<GroupUserBean> myTreatPlanBeans) throws Exception {
                        groupMemberList = myTreatPlanBeans;
                        if (groupMemberList != null && groupMemberList.size() > 0) {
                            adapter = new MassMsgSelectAdapter(Utils.getApp(), R.layout.item_group_member_list, groupMemberList);
                            lvGroupMemberList.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    /**
     * 删除组员
     *
     * @param userId
     * @param position
     */
    private void toDel(String userId, int position) {
        String gid = getIntent().getStringExtra("gid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("gid", gid);
        map.put("userId", userId);
        RxHttp.postForm(XyUrl.DEL_GROUP_USER)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        groupMemberList.remove(position);
                        adapter.notifyDataSetChanged();
                        //刷新列表
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.GROUP_MEMBER_DEL));
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
            //新增分组成员
            case R.id.bt_group_add:
                String gid = getIntent().getStringExtra("gid");
                Intent intent = new Intent(this, GroupMemberAddActivity.class);
                intent.putExtra("gid", gid);
                startActivity(intent);
                break;
        }
    }

    @OnItemClick(R.id.lv_group_member_list)
    void onItemClick(int position) {
        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
            toDel(groupMemberList.get(position).getUserId(), position);
        });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.GROUP_MEMBER_ADD:
                initValue();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_member_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        initValue();
    }
}
