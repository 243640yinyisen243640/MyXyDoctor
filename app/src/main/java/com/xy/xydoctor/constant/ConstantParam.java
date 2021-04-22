package com.xy.xydoctor.constant;

import com.xy.xydoctor.BuildConfig;

/**
 * 描述: 常量接口
 * 作者: LYD
 * 创建日期: 2018/6/12 17:35
 */
public interface ConstantParam {
    String SERVER_VERSION = "201409";
    boolean EXTERNAL_RELEASE = BuildConfig.ENVIRONMENT;

    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 10;

    int DEFAULT_REQUEST_SUCCESS = 200;
    /**
     * 网络请求 默认数据为空
     */
    int DEFAULT_NO_DATA = 30002;
    /**
     * token过期
     */
    int DEFAULT_TOKEN_EXPIRED = 20001;
    /**
     * 融云开始
     */

    String IM_KEY_RELEASE = "x18ywvqfxbd8c";
    String IM_KEY_DEBUG = "e0x9wycfe4jxq";
    String IM_KEY = EXTERNAL_RELEASE ? IM_KEY_RELEASE : IM_KEY_DEBUG;

    /**
     * 小米推送通道配置开始
     */
    String MI_PUSH_ID = "2882303761517993860";
    String MI_PUSH_KEY = "5111799398860";
    /**
     * 小米推送通道配置结束
     */
    /**
     * OPPO推送通道开始
     */
    String OPPO_PUSH_KEY = "32ff9fa6e0d549b787780cb2318b7247";
    String OPPO_PUSH_APP_SECRET = "eb2c515fa4854eee9d619cd5d098766d";
    /**
     * OPPO推送通道开始
     */
    /**
     * EventBus Code(静态内部类)
     */
    final class EventCode {
        //患者添加
        public static final int PATIENT_ADD = 1000;
        //添加分组
        public static final int GROUP_ADD = 1001;
        //删除分组
        public static final int GROUP_DEL = 1002;
        //添加分组成员
        public static final int GROUP_MEMBER_ADD = 1003;
        //申请住院
        public static final int APPLY_TO_HOSPITAL = 1004;
        //发送GroupID
        public static final int SEND_GROUP_ID = 1005;
        //更换药物
        public static final int CHANGE_MEDICINE = 1006;
        //添加回复
        public static final int ADD_REPLY = 1007;
        //医生操作
        public static final int NEW_PATIENT_OPERATE = 1008;
        //融云消息刷新
        public static final int IM_MESSAGE_REFRESH = 1009;
        //分组成员删除
        public static final int GROUP_MEMBER_DEL = 1010;
        //融云未读消息数量
        public static final int IM_UNREAD_MSG_COUNT = 1011;
        //手动添加血压 高血压
        public static final int BLOOD_PRESSURE_ADD_HIGH = 1012;
        //手动添加血压 低血压
        public static final int BLOOD_PRESSURE_ADD_LOW = 1013;
        //手动添加血压 时间
        public static final int BLOOD_PRESSURE_ADD_TIME = 1014;
        //随访创建完成
        public static final int FOLLOW_UP_VISIT_SUBMIT = 1015;
        //随访填写总结完成
        public static final int FOLLOW_UP_VISIT_SUMMARY_ADD = 1016;
        //签约操作
        public static final int SIGN_DEAL = 1017;
        //家签家庭成员添加成功
        public static final int FAMILY_MEMBER_ADD = 1018;
        //医生添加成功
        public static final int DOCTOR_ADD_SUCCESS = 1019;
        //发送选择的人
        public static final int SEND_SELECT_PEOPLE = 1020;
        //医生待办到主任待办
        public static final int DOCTOR_TO_DO_2_DIRECTOR_TO_DO = 1021;
        //添加用药
        public static final int ADD_MEDICINE = 1022;
        //添加用药
        public static final int PHARMACY_RECORD_ADD = 1023;
        //主任
        public static final int DIRECTOR_REFRESH = 1024;
        //患者详情绑定设备
        public static final int PATIENT_INFO_DEVICE_BIND = 1025;

    }


}
