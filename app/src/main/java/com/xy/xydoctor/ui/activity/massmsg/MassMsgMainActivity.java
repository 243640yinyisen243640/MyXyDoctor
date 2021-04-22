package com.xy.xydoctor.ui.activity.massmsg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.widget.view.MyListView;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgGroupListAdapter;
import com.xy.xydoctor.adapter.SelectUserAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 群发消息首页
 * 作者: LYD
 * 创建日期: 2019/3/1 11:24
 * key:type  value:{0:群发消息   1:患者情况统计群发消息  2:家签消息群发}
 */
public class MassMsgMainActivity extends BaseActivity {
    private static final int HAVE_SELECT = 10010;
    private static final int SELECT = 10086;
    private static final String TAG = "MassMsgMainActivity";
    @BindView(R.id.ll_mass_msg_main)
    RelativeLayout llMassMsgMain;
    @BindView(R.id.img_right_arrow)
    ImageView imgRightArrow;

    @BindView(R.id.lv_group_list)
    MyListView lvGroupList;
    @BindView(R.id.el_mass_msg_main)
    ExpandableLayout elMassMsgMain;

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rl_have_select)
    RelativeLayout rlHaveSelect;

    @BindView(R.id.et_msg)
    EditText etMsg;

    private ArrayList<GroupUserBean> mainList;
    private SelectUserAdapter adapter;
    private List<GroupListBean> data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mass_msg_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initRv();
        setFrom();
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        setTitle("群发消息");
        getTvMore().setText("历史记录");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent massMsgIntent = new Intent(getPageContext(), MassMsgHistoryActivity.class);
                String type = getIntent().getStringExtra("type");
                if ("1".equals(type)) {
                    type = "-1";
                } else {
                    type = "0";
                }
                massMsgIntent.putExtra("type", type);
                startActivity(massMsgIntent);
            }
        });
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mainList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置滑动方向：横向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvList.setLayoutManager(layoutManager);
        adapter = new SelectUserAdapter(mainList);
        rvList.setAdapter(adapter);
        rvList.setVisibility(View.GONE);
    }

    /**
     * 设置来自
     */
    private void setFrom() {
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            getGroupMemberList();
        } else {
            setGoToSelectPeople(type);
        }
    }

    /**
     * 获取列表数据
     */
    private void getGroupMemberList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
                .addAll(map)
                .asResponseList(GroupListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupListBean>>() {
                    @Override
                    public void accept(List<GroupListBean> myTreatPlanBeans) throws Exception {
                        data = myTreatPlanBeans;
                        if (myTreatPlanBeans != null) {
                            addData(myTreatPlanBeans);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
        //切换
        llMassMsgMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elMassMsgMain.toggle();
                Bitmap bitmap = ImageUtils.getBitmap(R.drawable.right_arrow);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (elMassMsgMain.isExpanded()) {
                    Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 90, width, height);
                    imgRightArrow.setImageBitmap(resizedBitmap);
                } else {
                    Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 0, width, height);
                    imgRightArrow.setImageBitmap(resizedBitmap);
                }
            }
        });
    }

    @OnItemClick(R.id.lv_group_list)
    void onItemClick(int position) {
        String type = getIntent().getStringExtra("type");
        GroupListBean item = data.get(position);
        int gid = item.getGid();
        String gname = item.getGname();
        Intent intent = new Intent(getPageContext(), MassMsgSelectPeopleActivity.class);
        intent.putExtra("gid", gid + "");
        intent.putExtra("gname", gname);
        intent.putExtra("type", type);
        startActivityForResult(intent, SELECT);
    }

    /**
     * 获取列表数据之添加数据
     *
     * @param data
     */
    private void addData(List<GroupListBean> data) {
        MassMsgGroupListAdapter adapter = new MassMsgGroupListAdapter(Utils.getApp(), R.layout.item_mass_msg_group, data);
        lvGroupList.setAdapter(adapter);
    }


    /**
     * 跳转选择成员列表
     */
    private void setGoToSelectPeople(String type) {
        if ("1".equals(type)) {
            int mainPosition = getIntent().getIntExtra("mainPosition", 0);
            int listPosition = getIntent().getIntExtra("listPosition", 0);
            llMassMsgMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getPageContext(), MassMsgSelectPeopleActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("mainPosition", mainPosition);
                    intent.putExtra("listPosition", listPosition);
                    //判断
                    if (1 == mainPosition) {
                        //血压
                        String time = getIntent().getStringExtra("time");
                        intent.putExtra("time", time);
                    } else {
                        //血糖
                        String beginTime = getIntent().getStringExtra("beginTime");
                        String endTime = getIntent().getStringExtra("endTime");
                        String beginSugar = getIntent().getStringExtra("beginSugar");
                        String endSugar = getIntent().getStringExtra("endSugar");
                        Log.e(TAG, "listPosition==" + listPosition);
                        Log.e(TAG, "beginTime==" + beginTime);
                        Log.e(TAG, "endTime==" + endTime);
                        Log.e(TAG, "beginSugar==" + beginSugar);
                        Log.e(TAG, "endSugar==" + endSugar);
                        intent.putExtra("beginTime", beginTime);
                        intent.putExtra("endTime", endTime);
                        intent.putExtra("beginSugar", beginSugar);
                        intent.putExtra("endSugar", endSugar);
                    }
                    startActivityForResult(intent, SELECT);
                }
            });
        } else {
            llMassMsgMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getPageContext(), MassMsgSelectPeopleActivity.class);
                    intent.putExtra("mainPosition", -1);
                    intent.putExtra("type", type);
                    startActivityForResult(intent, SELECT);
                }
            });
        }
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


    /**
     * 发送前校验
     */
    private void toCheckSend() {
        if (!(mainList.size() > 0)) {
            ToastUtils.showShort("还没有选择消息接收人");
            return;
        }
        String msg = etMsg.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showShort("请输入要发送的内容");
            return;
        }
        toDoSend(msg);
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    private void toDoSend(String msg) {
        String type = getIntent().getStringExtra("type");
        String wId;
        if ("0".equals(type)) {
            wId = "0";
        } else {
            wId = "-1";
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
        String url;
        if ("0".equals(type) || "1".equals(type)) {
            map.put("wid", wId);
            url = XyUrl.SEND_MSG;
        } else {
            url = XyUrl.HOME_SIGN_SEND_MSG;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("发送成功");
                        //跳转消息历史记录
                        Intent intent = new Intent(getPageContext(), MassMsgHistoryActivity.class);
                        String type = getIntent().getStringExtra("type");
                        if ("1".equals(type)) {
                            type = "-1";
                        } else {
                            type = "0";
                        }
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case SELECT:
                    rvList.setVisibility(View.VISIBLE);
                    List<GroupUserBean> listObj = (List<GroupUserBean>) data.getSerializableExtra("selectUserInfo");
                    mainList.addAll(listObj);
                    adapter.notifyDataSetChanged();
                    break;
                case HAVE_SELECT:
                    mainList.clear();//清空原有数据
                    List<GroupUserBean> listObjHaveSelect = (List<GroupUserBean>) data.getSerializableExtra("selectUserInfo");
                    if (listObjHaveSelect != null && listObjHaveSelect.size() > 0) {
                        rvList.setVisibility(View.VISIBLE);
                        mainList.addAll(listObjHaveSelect);
                        adapter.notifyDataSetChanged();
                    } else {
                        rvList.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
