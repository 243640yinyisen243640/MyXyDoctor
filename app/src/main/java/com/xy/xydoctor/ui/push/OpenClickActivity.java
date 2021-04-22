//package com.xy.xydoctor.ui.push;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.TextView;
//
//import com.blankj.utilcode.util.AppUtils;
//import com.blankj.utilcode.util.Utils;
//import com.lyd.baselib.util.RxTimer;
//import com.xy.xydoctor.ui.activity.MainActivity;
//import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmitActivity;
//import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmitActivity;
//import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
//import com.xy.xydoctor.ui.activity.todo.ApplyToHospitalDetailActivity;
//import com.xy.xydoctor.ui.activity.todo.NewPatientListActivity;
//import com.xy.xydoctor.ui.activity.todo.ToDoListActivity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import cn.jpush.android.api.JPushInterface;
//
//public class OpenClickActivity extends Activity {
//    private static final String TAG = "OpenClickActivity";
//    /**
//     * 消息Id
//     **/
//    private static final String KEY_MSGID = "msg_id";
//    /**
//     * 该通知的下发通道
//     **/
//    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
//    /**
//     * 通知标题
//     **/
//    private static final String KEY_TITLE = "n_title";
//    /**
//     * 通知内容
//     **/
//    private static final String KEY_CONTENT = "n_content";
//    /**
//     * 通知附加字段
//     **/
//    private static final String KEY_EXTRAS = "n_extras";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        TextView tv = new TextView(this);
//        setContentView(tv);
//
//        handleOpenClick();
//    }
//
//
//    /**
//     * 处理点击事件，当前启动配置的Activity都是使用
//     * Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
//     * 方式启动，只需要在onCreate中调用此方法进行处理
//     */
//    private void handleOpenClick() {
//        Log.e(TAG, "用户点击打开了通知");
//        String data = null;
//        //获取华为平台附带的jpush信息
//        if (getIntent().getData() != null) {
//            data = getIntent().getData().toString();
//        }
//
//        //获取fcm、小米、oppo、vivo平台附带的jpush信息
//        if (TextUtils.isEmpty(data) && getIntent().getExtras() != null) {
//            data = getIntent().getExtras().getString("JMessageExtra");
//        }
//
//        Log.e(TAG, "msg content is " + data);
//        if (TextUtils.isEmpty(data)) return;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String msgId = jsonObject.optString(KEY_MSGID);
//            byte whichPushSDK = (byte) jsonObject.optInt(KEY_WHICH_PUSH_SDK);
//            //判断跳转
//            String extras = jsonObject.optString(KEY_EXTRAS);
//            Log.e(TAG, "extras==" + extras);
//            JSONObject jsonExtra = new JSONObject(extras);
//            String type = jsonExtra.optString("type");
//            Log.e(TAG, "type==" + type);
//
//            Intent[] intents = new Intent[2];
//            intents[0] = new Intent(this, MainActivity.class);
//            switch (type) {
//                case "1"://患者添加申请
//                    intents[1] = new Intent(Utils.getApp(), NewPatientListActivity.class);
//                    startActivities(intents);
//                    break;
//                case "2"://患者住院申请
//                    intents[1] = new Intent(Utils.getApp(), ApplyToHospitalDetailActivity.class);
//                    intents[1].putExtra("id", jsonExtra.optString("id"));
//                    startActivities(intents);
//                    break;
//                case "3"://到待办事项列表
//                    AppUtils.launchApp("com.xy.xydoctor");
//                    RxTimer rxTimer = new RxTimer();
//                    rxTimer.timer(2 * 1000, new RxTimer.RxAction() {
//                        @Override
//                        public void action(long number) {
//                            intents[1] = new Intent(Utils.getApp(), ToDoListActivity.class);
//                            startActivities(intents);
//                        }
//                    });
//                    break;
//                case "4"://随访详情
//                    String types = jsonExtra.optString("types");
//                    //Log.i(TAG, "随访类型==" + type);
//                    String id = jsonExtra.optString("id");
//                    if ("1".equals(types)) {
//                        intents[1] = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmitActivity.class);
//                        intents[1].putExtra("id", id);
//                    } else if ("2".equals(types)) {
//                        intents[1] = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmitActivity.class);
//                        intents[1].putExtra("id", id);
//                    } else {
//                        intents[1] = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
//                        intents[1].putExtra("id", id);
//                    }
//                    startActivities(intents);
//                    break;
//            }
//            //关闭当前
//            finish();
//            //上报点击事件
//            JPushInterface.reportNotificationOpened(this, msgId, whichPushSDK);
//        } catch (JSONException e) {
//            Log.e(TAG, "parse notification error");
//        }
//    }
//}
