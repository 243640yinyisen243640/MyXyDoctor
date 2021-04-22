package com.xy.xydoctor.ui.activity.todo;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.util.sp.SPUtils;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.lyd.librongim.rongim.RongImInterface;
import com.lyd.librongim.rongim.RongImUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.ToDoListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.ToDoListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 待办事项
 * 作者: LYD
 * 创建日期: 2019/5/29 15:40
 */
@BindEventBus
public class ToDoListActivity extends BaseEventBusActivity {
    private static final String TAG = "ToDoListActivity";
    @BindView(R.id.lv_to_do)
    ListView lvToDo;

    /**
     * 添加Fragment到Activity
     */
    private void addFragment() {
        //步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //步骤二：用add()方法加上Fragment的对象rightFragment
        Fragment conversationFragment = initConversationFragment();
        transaction.replace(R.id.fl_fragment_container, conversationFragment);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    /***
     * 得到融云会话Fragment
     * @return
     */
    private Fragment initConversationFragment() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName)
                .buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .build();
        fragment.setUri(uri);
        return fragment;
    }

    /**
     * 获取待办事项列表
     */
    private void getToDoList() {
        String type = getIntent().getStringExtra("type");
        HashMap<String, String> map = new HashMap<>();
        String url;
        if ("homeSign".equals(type)) {
            url = XyUrl.HOME_SIGN_TO_DO_NUMBER;
        } else {
            url = XyUrl.TO_DO_NUMBER;
        }
        map.put("userid", getIntent().getIntExtra("userid", 0) + "");
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(ToDoListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ToDoListBean>() {
                    @Override
                    public void accept(ToDoListBean data) throws Exception {
                        List<ToDoListBean> list = new ArrayList<>();
                        int count;
                        if ("homeSign".equals(type)) {
                            count = 3;
                        } else {
                            count = 4;
                        }
                        for (int i = 0; i < count; i++) {
                            list.add(data);
                        }
                        int userid = getIntent().getIntExtra("userid", 0);
                        ToDoListAdapter adapter = new ToDoListAdapter(getPageContext(), R.layout.item_to_do, list, type, userid);
                        lvToDo.setAdapter(adapter);
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
            case ConstantParam.EventCode.NEW_PATIENT_OPERATE:
            case ConstantParam.EventCode.APPLY_TO_HOSPITAL:
            case ConstantParam.EventCode.FOLLOW_UP_VISIT_SUMMARY_ADD:
                getToDoList();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_do_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("待办事项");
        getToDoList();
        addFragment();
        setBack();
    }

    private void setBack() {
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoFinish();
            }
        });
    }

    private void toDoFinish() {
        RongImUtils.logout();
        String directorImToken = SPStaticUtils.getString("loginImToken");
        RongImUtils.connect(directorImToken, new RongImInterface.ConnectCallback() {
            @Override
            public void onSuccess() {
                List<GroupUserBean> list = (List<GroupUserBean>) SPUtils.getBean("allUsers");
                String docId = SPStaticUtils.getString("docId");
                String docName = SPStaticUtils.getString("docName");
                String docHeadImg = SPStaticUtils.getString("docHeadImg");
                GroupUserBean doctorUserBean = new GroupUserBean();
                int intDoctorId = TurnsUtils.getInt(docId, 0);
                doctorUserBean.setUserid(intDoctorId);
                doctorUserBean.setPicture(docHeadImg);
                doctorUserBean.setNickname(docName);
                list.add(doctorUserBean);
                SPUtils.putBean("allUsers", list);
                RongImUtils.setCurrentUserInfo(list, doctorUserBean);
            }
        });
        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.DOCTOR_TO_DO_2_DIRECTOR_TO_DO));
        finish();
    }

    /**
     * 退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toDoFinish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
