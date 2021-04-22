//package com.xy.xydoctor.utils.jpush;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.blankj.utilcode.util.Utils;
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
//import cn.jpush.android.api.NotificationMessage;
//import cn.jpush.android.service.JPushMessageReceiver;
//
///**
// * 描述:
// * 作者: LYD
// * 创建日期: 2020/4/2 9:18
// */
//public class MyJPushReceiver extends JPushMessageReceiver {
//    private static final String TAG = "MyJPushReceiver";
//
//    @Override
//    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
//        super.onNotifyMessageArrived(context, notificationMessage);
//        //int notificationType = notificationMessage.notificationType;
//        Log.e(TAG, "消息到达");
//        String notificationTitle = notificationMessage.notificationTitle;
//        String notificationContent = notificationMessage.notificationContent;
//        Log.e(TAG, "消息到达标题==" + notificationTitle);
//        Log.e(TAG, "消息到达内容==" + notificationContent);
//        //额外参数示例: {"key":"自定义附加字段0","type":"自定义附加字段1"}
//        String notificationExtras = notificationMessage.notificationExtras;
//        Log.e(TAG, "消息到达额外参数==" + notificationExtras);
//    }
//
//    @Override
//    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
//        super.onNotifyMessageOpened(context, notificationMessage);
//        Log.e(TAG, "消息点击");
//        String notificationTitle = notificationMessage.notificationTitle;
//        String notificationContent = notificationMessage.notificationContent;
//        Log.e(TAG, "消息点击标题==" + notificationTitle);
//        Log.e(TAG, "消息点击内容==" + notificationContent);
//        //额外参数示例: {"key":"自定义附加字段0","type":"自定义附加字段1"}
//        String extras = notificationMessage.notificationExtras;
//        Log.e(TAG, "消息点击额外参数==" + extras);
//        //判断跳转
//        try {
//            JSONObject jsonExtra = new JSONObject(extras);
//            String type = jsonExtra.optString("type");
//            Log.e(TAG, "type==" + type);
//            Intent intent = null;
//            switch (type) {
//                case "1"://患者添加申请
//                    intent = new Intent(Utils.getApp(), NewPatientListActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Utils.getApp().startActivity(intent);
//                    break;
//                case "2"://患者住院申请
//                    intent = new Intent(Utils.getApp(), ApplyToHospitalDetailActivity.class);
//                    intent.putExtra("id", jsonExtra.optString("id"));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Utils.getApp().startActivity(intent);
//                    break;
//                case "3"://到待办事项列表
//                    intent = new Intent(Utils.getApp(), ToDoListActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Utils.getApp().startActivity(intent);
//                    break;
//                case "4"://随访详情
//                    String types = jsonExtra.optString("types");
//                    //Log.i(TAG, "随访类型==" + type);
//                    String id = jsonExtra.optString("id");
//                    if ("1".equals(types)) {
//                        intent = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmitActivity.class);
//                        intent.putExtra("id", id);
//                    } else if ("2".equals(types)) {
//                        intent = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmitActivity.class);
//                        intent.putExtra("id", id);
//                    } else {
//                        intent = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
//                        intent.putExtra("id", id);
//                    }
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Utils.getApp().startActivity(intent);
//                    break;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
