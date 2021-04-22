package com.xy.xydoctor.utils.alipush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.xy.xydoctor.bean.AliPushBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.MainActivity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmitActivity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmitActivity;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
import com.xy.xydoctor.ui.activity.todo.ApplyToHospitalDetailActivity;
import com.xy.xydoctor.ui.activity.todo.NewPatientListActivity;
import com.xy.xydoctor.ui.activity.todo.SystemMsgDetailActivity;
import com.xy.xydoctor.ui.activity.todo.ToDoListActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class MyMessageReceiver extends MessageReceiver {
    //消息接收部分的LOG_TAG

    /**
     * 处理推送通知
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        Log.e("MyMessageReceiver", "收到扩展参数==" + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
    }

    /**
     * 通知点击处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "点击扩展参数==" + extraMap);

        AliPushBean pushBean = GsonUtils.fromJson(extraMap, AliPushBean.class);
        String type = pushBean.getType();
        changeIsRead(pushBean.getPid() + "");
        Intent intent = null;
        switch (type) {
            case "1"://患者 添加申请
                intent = new Intent(Utils.getApp(), NewPatientListActivity.class);
                break;
            case "2"://患者 预约住院
                intent = new Intent(Utils.getApp(), ApplyToHospitalDetailActivity.class);
                intent.putExtra("id", pushBean.getId());
                break;
            case "3"://预警消息 到待办事项列表
                intent = new Intent(Utils.getApp(), ToDoListActivity.class);
                break;
            //血糖随访
            case "4":
                intent = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmitActivity.class);
                intent.putExtra("id", pushBean.getId());
                break;
            //血压随访
            case "5":
                intent = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmitActivity.class);
                intent.putExtra("id", pushBean.getId());
                break;
            //肝病随访
            case "6":
                intent = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
                intent.putExtra("id", pushBean.getId());
                break;
            //患者随访时间到期提醒
            case "10":
            case "19":
                intent = new Intent(Utils.getApp(), SystemMsgDetailActivity.class);
                intent.putExtra("id", pushBean.getPid());
                break;
            default:
                intent = new Intent(Utils.getApp(), MainActivity.class);
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(intent);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
    }

    private void changeIsRead(String pid) {
        HashMap map = new HashMap<>();
        map.put("pid", pid);
        RxHttp.postForm(XyUrl.GET_PORT_MESSAGE_EDIT)
                .addAll(map)
                .asResponse(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String updateBean) throws Exception {
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.APPLY_TO_HOSPITAL));//刷新列表
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
