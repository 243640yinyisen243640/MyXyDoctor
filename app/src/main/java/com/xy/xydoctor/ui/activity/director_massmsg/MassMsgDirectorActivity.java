package com.xy.xydoctor.ui.activity.director_massmsg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgDirectorDoctorListAdapter;
import com.xy.xydoctor.adapter.SelectUserAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgHaveSelectPeopleActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgHistoryActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 群发消息
 * 作者: LYD
 * 创建日期: 2020/5/18 19:37
 */
@BindEventBus
public class MassMsgDirectorActivity extends BaseEventBusActivity {
    private static final int HAVE_SELECT = 10010;
    @BindView(R.id.rv_doctor_list)
    RecyclerView rvDoctorList;
    @BindView(R.id.img_right_arrow)
    ImageView imgRightArrow;
    @BindView(R.id.rl_have_select)
    RelativeLayout rlHaveSelect;
    @BindView(R.id.rv_have_select_list)
    RecyclerView rvHaveSelectList;
    @BindView(R.id.et_msg)
    EditText etMsg;

    @BindView(R.id.bt_send)
    Button btSend;

    private ArrayList<GroupUserBean> mainList;
    private SelectUserAdapter selectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mass_msg_director;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("群发消息");
        getDoctorList();
        initRv();
        getTvMore().setText("历史记录");
        getTvMore().setVisibility(View.VISIBLE);
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent massMsgIntent = new Intent(getPageContext(), MassMsgHistoryActivity.class);
                massMsgIntent.putExtra("type", "0");
                startActivity(massMsgIntent);
            }
        });
    }

    private void initRv() {
        mainList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置滑动方向：横向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHaveSelectList.setLayoutManager(layoutManager);
        selectAdapter = new SelectUserAdapter(mainList);
        rvHaveSelectList.setAdapter(selectAdapter);
        rvHaveSelectList.setVisibility(View.GONE);
    }

    private void getDoctorList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_LIST)
                .addAll(map)
                .asResponseList(DoctorListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DoctorListBean>>() {
                    @Override
                    public void accept(List<DoctorListBean> list) throws Exception {
                        if (list != null && list.size() > 0) {
                            MassMsgDirectorDoctorListAdapter adapter = new MassMsgDirectorDoctorListAdapter(list);
                            rvDoctorList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvDoctorList.setAdapter(adapter);
                            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                                    switch (view.getId()) {
                                        case R.id.img_check:
                                            if (!adapter.isSelected.get(position)) {
                                                //修改map的值保存状态
                                                adapter.isSelected.put(position, true);
                                                adapter.notifyItemChanged(position);
                                                //添加
                                                List<GroupUserBean> userids = list.get(position).getUserids();
                                                mainList.addAll(userids);
                                                rvHaveSelectList.setVisibility(View.VISIBLE);
                                                selectAdapter.notifyDataSetChanged();
                                            } else {
                                                //修改map的值保存状态
                                                adapter.isSelected.put(position, false);
                                                adapter.notifyItemChanged(position);
                                                //删除
                                                List<GroupUserBean> userids = list.get(position).getUserids();
                                                mainList.removeAll(userids);
                                                selectAdapter.notifyDataSetChanged();
                                            }
                                            break;
                                        case R.id.tv_doctor_name:
                                            List<GroupUserBean> userids = list.get(position).getUserids();
                                            Intent intent = new Intent(getPageContext(), SelectPeopleListDirectorActivity.class);
                                            intent.putExtra("userids", (Serializable) userids);
                                            intent.putExtra("checkList", mainList);
                                            startActivity(intent);
                                            break;
                                    }
                                }
                            });
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @OnClick({R.id.rl_have_select, R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_have_select://已经选择的消息接收人
                if (mainList != null && mainList.size() > 0) {
                    Intent intent = new Intent(getPageContext(), MassMsgHaveSelectPeopleActivity.class);
                    List<GroupUserBean> list = new ArrayList(mainList);
                    intent.putExtra("haveSelectPeople", (Serializable) list);
                    startActivityForResult(intent, HAVE_SELECT);
                } else {
                    ToastUtils.showShort("还没有选择消息接收人");
                }
                break;
            case R.id.bt_send:
                toCheckSend();
                break;
        }
    }

    private void toCheckSend() {
        String msg = etMsg.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showShort("请输入要发送的内容");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mainList.size(); i++) {
            int userid = mainList.get(i).getUserid();
            stringBuilder.append(userid);
            stringBuilder.append(",");
        }
        //截取最后,
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", msg);
        map.put("userids", substring);
        RxHttp.postForm(XyUrl.SEND_MSG)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("获取成功");
                        //跳转消息历史记录
                        Intent intent = new Intent(getPageContext(), MassMsgHistoryActivity.class);
                        intent.putExtra("type", "0");
                        startActivity(intent);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.SEND_SELECT_PEOPLE:
                List<GroupUserBean> haveSelectList = (List<GroupUserBean>) event.getData();
                if (haveSelectList != null && haveSelectList.size() > 0) {
                    rvHaveSelectList.setVisibility(View.VISIBLE);
                    mainList.clear();
                    mainList.addAll(haveSelectList);
                    selectAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 去重
     *
     * @param list
     * @return
     */
    private ArrayList<GroupUserBean> removeDuplicate(ArrayList<GroupUserBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getUserid() == (list.get(i).getUserid())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case HAVE_SELECT:
                    mainList.clear();//清空原有数据
                    List<GroupUserBean> listObjHaveSelect = (List<GroupUserBean>) data.getSerializableExtra("selectUserInfo");
                    if (listObjHaveSelect != null && listObjHaveSelect.size() > 0) {
                        rvHaveSelectList.setVisibility(View.VISIBLE);
                        mainList.addAll(listObjHaveSelect);
                        selectAdapter.notifyDataSetChanged();
                    } else {
                        rvHaveSelectList.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
