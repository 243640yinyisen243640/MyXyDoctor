package com.xy.xydoctor.ui.push;

import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.blankj.utilcode.util.AppUtils;
import com.lyd.baselib.util.RxTimer;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
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

public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";

    /**
     * 实现通知打开回调方法，获取通知相关信息
     *
     * @param title   标题
     * @param summary 内容
     * @param extMap  额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
        Log.e(TAG, "标题===" + title);
        Log.e(TAG, "内容===" + summary);
        Log.e(TAG, "额外参数===" + extMap);
        String type = extMap.get("type");
        changeIsRead(extMap.get("pid"));
        Log.e(TAG, "type==" + type);
        Intent mainIntent = new Intent(this, MainActivity.class);
        Intent intent = null;
        switch (type) {
            //患者添加申请
            case "1":
                intent = new Intent(this, NewPatientListActivity.class);
                break;
            //患者住院申请
            case "2":
                intent = new Intent(this, ApplyToHospitalDetailActivity.class);
                intent.putExtra("id", extMap.get("id"));
                break;
            case "3"://到待办事项列表
                AppUtils.launchApp("com.xy.xydoctor");
                RxTimer rxTimer = new RxTimer();
                rxTimer.timer(2 * 1000, new RxTimer.RxAction() {
                    @Override
                    public void action(long number) {
                        Intent intent = new Intent(PopupPushActivity.this, ToDoListActivity.class);
                        Intent[] intents = new Intent[]{mainIntent, intent};
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivities(intents);
                    }
                });
                break;
            //血糖随访
            case "4":
                intent = new Intent(this, FollowUpVisitBloodSugarSubmitActivity.class);
                intent.putExtra("id", extMap.get("id") + "");
                break;
            //血压随访
            case "5":
                intent = new Intent(this, FollowUpVisitBloodPressureSubmitActivity.class);
                intent.putExtra("id", extMap.get("id") + "");
                break;
            //肝病随访
            case "6":
                intent = new Intent(this, FollowUpVisitHepatopathySubmitActivity.class);
                intent.putExtra("id", extMap.get("id") + "");
                break;
            //患者随访时间到期提醒
            case "19":
            case "10":
                intent = new Intent(this, SystemMsgDetailActivity.class);
                intent.putExtra("id", extMap.get("pid") + "");
                break;
        }
        if (intent != null) {
            Intent[] intents = new Intent[]{mainIntent, intent};
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivities(intents);
        }
        //关闭当前
        finish();
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
