package com.xy.xydoctor.net;

import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * 描述: RxHttp Url开始
 * 作者: LYD
 * 创建日期: 2019/12/25 13:37
 */
public class XyUrl {
    //处理状态以及处理完后的 建议查看
    public final static String CHECK_ADVICE = "/port/userdata/checkAdvice";
    public final static String ADD_FEED_BACK = "/addFeedback";
    //时间模糊搜索血糖记录
    public final static String GET_BLOOD_GLUCOSE_SEARCH = "/port/userdata/bloodglucoseSearch";
    //智能决策开始
    //药物详情
    public final static String GET_DRUGS_DETAIL = "/port/userdata/getdrugsinfo";
    //降压方案列表
    public final static String PLAN_GET_PLAN_LIST = "/port/userdata/getPlanlist";
    //药物分组列表
    public final static String GET_DRUGS_GROUP = "/port/userdata/drugsGroup";
    //其他同类药物
    public final static String GET_DRUGS_OTHER = "/port/userdata/getOtherDrugs";
    //更换药物
    public final static String DRUGS_CHANGE = "/port/userdata/changeDrugs";
    //降压方案详情
    public final static String PLAN_GET_PLAN_DETAIL = "/port/userdata/getPlandetails";
    //获取药物
    public final static String GET_DRUGS_GROUP_DETAIL = "/port/userdata/drugs";
    //处方列表
    public final static String GET_DOCTOR_PROFESSION_LIST = "/port/Doctor/professionList";
    //添加血糖
    public final static String ADD_SUGAR = "/port/record/addbloodglucose";
    //添加血压
    public final static String ADD_BLOOD = "/port/record/addbloodpressure";
    //智能分析开始
    //血压折线图
    public final static String GET_INDEX_BLOOD_INDEX = "/port/userdata/index";
    //血压分析
    public final static String GET_INDEX_BLOOD_REPORTBP = "/port/userdata/reportbp";
    //智能决策结束
    //月份列表
    public final static String GET_PORT_PERSONAL_MONTHLIST = "/port/Personal/monthlist";
    //血糖分析
    public final static String GET_PORT_ANALYSIS_BLOOD = "/port/analysis/blood";
    //首页获取控制目标
    public final static String GET_USERDATE = "/index/index/getuserdata";
    //健康记录添加
    //随访管理
    public final static String GET_FOLLOW_DETAIL_NEW = "/doctor/Followmgt/followDetail";
    //    //随访提交
    //    public final static String FOLLOW_ADD = "/port/Followmgt/addFollow";

    //随访管理开始
    public final static String GET_FOLLOW_NEW = "/doctor/Followmgt/getFollow";
    //创建随访
    public final static String FOLLOW_UP_VISIT_CREATE = "/doctor/Followmgt/addFollow";
    //个人中心我的设备 开始
    //绑定设备
    public final static String DEVICE_BIND = "/doctor/Docinfo/bindSugarm";
    //绑定设备
    public final static String SN_BIND = "/doctor/Docinfo/bindsnnum";
    //解锁设备
    public final static String DEVICE_UN_BIND = "/doctor/Docinfo/unbindSugarm";
    //解锁设备
    public final static String SN_UN_BIND = "/doctor/Docinfo/unbindsnnum";
    //绑定设备
    public final static String DEVICE_BIND_PATIENT = "/port/Equipment/bindPequipment";
    //解锁设备
    public final static String DEVICE_UN_BIND_PATIENT = "/port/Equipment/unbindPequipment";
    //家签开始
    //家庭签约列表弹窗
    public final static String FAMILY_LIST = "/doctor/Familysign/familyLists";
    //家签医生添加
    public final static String FAMILY_DOCTOR_ADD = "/doctor/Familysign/familyDoctorAdd";
    //家签医生添加
    public final static String FAMILY_MEMBER_ADD = "/doctor/Familysign/familyMembersAdd";
    //医生信息
    public final static String SIGN_DOCTOR_INFO = "/port/Familysign/doctorinfo";
    //个人中心我的设备 结束
    //家签首页 未读消息数量
    public final static String HOME_SIGN_UN_READ_COUNT = "/doctor/Familysign/todonum";
    //待办事项列表 未读消息数量(家签)
    public final static String HOME_SIGN_TO_DO_NUMBER = "/doctor/Familysign/todoList";
    //待办事项列表 未读消息数量 (工作台)
    public final static String TO_DO_NUMBER = "/doctor/Message/todoList";
    //待办事项列表 新的患者列表 (家签)
    public final static String HOME_SIGN_APPLY_USER = "/doctor/Familysign/applyUserList";
    //待办事项列表 新的患者列表 (工作台)
    public final static String APPLY_USER = "/doctor/message/applyUserList";
    //签约详情页
    public final static String HOME_SIGN_DETAIL = "/port/message/familyApplyDdetail";
    //医生处理患者签约
    public final static String HOME_SIGN_DEAL = "/doctor/Familysign/apply_deal";
    //住院申请列表(家签)
    public final static String HOME_SIGN_APPLY_HOSPITAL = "/doctor/Familysign/familyInhospitalList";
    //住院申请列表(工作台)
    public final static String APPLY_HOSPITAL = "/doctor/message/applyInhospitalList";
    //随访待办(家签)
    public final static String HOME_SIGN_FOLLOW_LIST = "/doctor/Familysign/familyFollowList";
    //随访待办(工作台)
    public final static String FOLLOW_LIST = "/doctor/message/followList";
    //家庭成员删除
    public static final String FAMILY_DEL = "/doctor/Familysign/familyMembersDel";
    //判断医生所属医院是否加入家签
    public static final String IS_FAMILY = "/doctor/Familysign/isFamily";
    //群发消息发送(工作台)
    public static final String SEND_MSG = "/doctor/Message/send";
    //群发消息发送(家签)
    public static final String HOME_SIGN_SEND_MSG = "/doctor/Familysign/send";
    //消息历史(工作台)
    public final static String MASS_MSG_HISTORY = "/doctor/Message/sendHistory";
    //消息历史(家签)
    public final static String HOME_SIGN_MASS_MSG_HISTORY = "/doctor/Familysign/sendHistory";
    //消息历史(家签)
    public final static String HOME_SIGN_MASS_SELECT_PEOPLE = "/doctor/Familysign/familysignUsers";
    //个人档案
    public final static String PERSONAL_RECORD = "/port/Personal/personalshow";
    //更新
    //    public final static String GET_UPDATE = "/port/Doctor/getDocVersion";
    public final static String GET_UPDATE = "/port/appversion/getDocVersion";
    //获取融云token
    public final static String GET_IM_TOKEN = "/doctor/Docinfo/getRongToken";
    //获取血压异常
    public final static String GET_BLOOD_PRESSURE_ABNORMAL = "/port/userdata/bloodpressureCondition";
    //血糖血压处理接口
    public final static String SEND_APPLY = "/doctor/message/fastreplydel";
    //家签结束
    //患者沟通快捷回复列表
    public final static String GET_FAST_REPLY_LIST = "doctor/message/fastreplyList";
    //患者沟通血糖异常
    public final static String GET_SUGAR_ABNORMAL = "/port/userdata/bloodglucoseCondition";
    //医生提交随访建议
    public final static String FOLLOW_UP_VISIT_SUMMARY_ADD = "/doctor/message/editfollow";
    //患者沟通快捷回复列表添加
    public final static String ADD_FAST_REPLY = "/doctor/message/fastreplyAdd";
    //分组外成员
    public final static String GET_UNGROUP_LIST = "/doctor/Users/getUngroupuser";
    //分组成员添加
    public final static String ADD_GROUP_USER = "/doctor/Index/addGroupUser";
    //分组成员
    public final static String GET_GROUP_USER = "/doctor/Index/getGroupusers";

    public final static String PATIENT_ADD_TODAY_LIST = "/newAddPatients";
    //分组成员删除
    public final static String DEL_GROUP_USER = "/doctor/Users/delGroupuser";
    //血压
    public final static String GET_BLOOD_PRESSURE_LIST = "/port/Record/getbloodpressure"; //血压
    public final static String GET_TEMPERATURE_LIST = "/port/record/getTemperature";//体温
    //退出
    public final static String LOGIN_OUT = "/docout";
    //每个时间点血糖测量详情
    public final static String GET_SUGAR_LIST = "/port/userdata/bloodglucosedetail";
    //
    public final static String GET_SEVEN_AND_THIRTY_BLOOD_SUGAR = "/port/userdata/bloodglucose";
    //检查记录
    public final static String GET_CHECK_LIST = "/port/Record/getexamine";
    //饮食
    public final static String GET_FOOD_AND_DRINK_LIST = "/port/Record/getdiet";
    //Bmi
    public final static String GET_HEIGHT_AND_WEIGHT_LIST = "/port/Record/getbmi";
    //用药
    public final static String GET_PHARMACY_LIST = "/port/Record/getpharmacy";
    //糖化
    public final static String GET_SACCHARIFY_LIST = "/port/Record/gethemoglobin";
    //运动
    public final static String GET_SPORT_LIST = "/port/Record/getexercise";
    //添加血氧
    public final static String ADD_BLOOD_OXYGEN = "/port/Record/addBloodox";
    //添加体重
    public final static String ADD_BMI = "/port/Record/addbmi";//添加体重
    public final static String ADD_TEMPERATURE = "/port/record/addTemperatureData";
    //家庭签约家庭成员列表
    public final static String GET_FAMILY_MEMBERS_LIST = "/doctor/Familysign/familyMembersList";
    //签约预约住院详情
    public final static String GET_FAMILY_IN_HOSPITAL_DETAIL = "/port/Familysign/familyinhospitaldetail";
    //签约预约住院审批
    public final static String GET_FAMILY_IN_HOSPITAL_DEAL = "/doctor/Familysign/familyinhospitalDeal";
    //家庭用户移出列表
    public final static String GET_FAMILY_DEL_LIST = "/doctor/Familysign/familyDelList";
    //家庭签约列表
    public final static String GET_FAMILY_LIST = "/doctor/Familysign/familyList";
    //家庭用户移入
    public final static String FAMILY_MEMBER_IN = "/doctor/Familysign/familyMemberin";
    //家庭签约列表
    public final static String GET_RESCISSION_LIST = "/doctor/Familysign/rescissionList";
    //签约协议
    public final static String GET_AGREEMENT = "/port/Familysign/agreement";
    //患者分组
    public final static String GET_GROUP_LIST = "/doctor/Index/getGroups";
    //患者信息(返回随机码)
    public final static String GET_USER_INFO = "/doctor/Users/getUserInfo";
    //添加患者
    public final static String ADD_USER = "/doctor/Users/addUser";
    //扫描二维码
    public final static String GET_QRCODE_INFO = "/qrcodeInfo";
    //搜索患者
    public final static String SEARCH_USER = "/doctor/Users/searchUser";
    //搜索或者
    public final static String SEARCH_USER_INDEX = "/doctor/Index/searchusers";
    //患教文章列表
    public final static String GET_EDU_ARTICLE_LIST = "/doctor/users/eduArticleList";
    //患教文章发送历史记录
    public final static String GET_ARTICLE_HISTORY_LIST = "/doctor/users/eduHistory";
    //患教文章群发
    public final static String SEND_ARTICLE_MSG = "/doctor/users/articlesend";//00000
    //患者体质监测列表
    public final static String GET_TCM_LIST = "/port/Physical/physical_list";
    //住院申请审批以及详情页
    //只传id是详情页，id和status一起传是审批
    public final static String APPLY_TO_HOSPITAL_DETAIL = "/doctor/message/inhospitaldetail";
    //医生审批患者申请  审批结果（1：拒绝 2：同意 ）
    public final static String CHANGE_PATIENT_STATE = "/doctor/message/setStatus";
    //未读系统消息
    public final static String GET_UNREAD_SYSTEM_MESSAGE_LIST = "/doctor/message/sysmsg";
    //系统消息已读接口
    public final static String GET_PORT_MESSAGE_EDIT = "/port/Message/editMessage";
    //未读系统消息 详情
    public final static String GET_UNREAD_SYSTEM_MESSAGE_DETAIL = "/doctor/message/altermessage";
    //登录
    public final static String LOGIN = "/doclogin";
    //修改密码
    public final static String EDIT_PWD = "/doctor/Docinfo/editPass";
    //分组添加
    public final static String ADD_GROUP = "/doctor/Index/addGroup";
    //获取血糖血压统计数据
    public final static String GET_APPLY_TO_HOSPITAL_LIST = "/doctor/index/statistics";
    //获取用药历史
    public final static String GET_MEDICINE_HISTORY = "/port/Personal/medicine";
    //在线测量医生获取血糖数据
    public final static String GET_ONLINE_TEST_BLOOD_SUGAR = "/doctor/Message/getSugars";
    //在线测量医生获取血压数据
    public final static String GET_ONLINE_TEST_BLOOD_PRESSURE = "/doctor/Message/getbloods";
    //添加血糖
    public final static String ADD_BLOOD_SUGAR = "/port/Record/addbloodglucose";
    //首页数据统计接口
    public final static String GET_INDEX = "/doctor/Index/index";
    //医生信息
    public final static String GET_DOCTOR_INFO = "/doctor/Docinfo/myInfo";
    //分组删除
    public final static String DEL_GROUP = "/doctor/Index/delGroup";
    //肝病档案显示
    public final static String GET_LIVER_FILES = "/port/Personal/hepatopathy";
    //肝病档案修改
    public final static String EDIT_LIVER_FILE = "/port/Personal/hepatopathyEdit";
    //档案类型判断
    public final static String IS_LIVER_FILE = "/port/Personal/archivestyle";
    //医生列表
    public final static String GET_DOCTOR_LIST = "/doctorList";
    //医生列表
    public final static String EDIT_DOCTOR_INFO = "/editDocInfo";
    //添加编辑医生
    public final static String ADD_DOCTOR = "/addDoctor";
    //及时消息已读设置
    public final static String SET_READ_MESSAGE = "/setReadMessage";
    //环境
    private final static boolean EXTERNAL_RELEASE = BuildConfig.ENVIRONMENT;
    //正式地址
        private final static String DOMAIN = "http://port.xiyuns.cn";
//    private final static String DOMAIN = "http://alb.xiyuns.cn";
    //测试地址
    private final static String DOMAIN_TEST = "http://d.xiyuns.cn";
    //设置为默认域名
    @DefaultDomain()
    public static String HOST_URL = EXTERNAL_RELEASE ? DOMAIN : DOMAIN_TEST;
    //修改控制目标
    public final static String ADD_TARGET = HOST_URL + "/addTarget";
    //恢复血糖控制目标
    public final static String RESET_TARGET = HOST_URL + "/reasetApplay";
    //接口列表开始
    //血氧记录
    public final static String GET_BLOOD_OXYGEN = HOST_URL + "/port/Record/getBloodox";
    //减重处方列表
    public final static String GET_LOSE_WEIGHT_LIST = HOST_URL + "/weightLossPrescription";
    //健康记录添加
    //血糖保存
    public final static String PERSONAL_SAVE_GLUCOSE = HOST_URL + "/port/Personal/glucose";
    //体重列表
    public final static String GET_WEIGHT_LIST = HOST_URL + "/getweight";
    //体重图表
    public final static String GET_WEIGHT_CHART = HOST_URL + "/getweightNum";
    //个人档案保存  个人资料编辑
    public final static String PERSONAL_SAVE = HOST_URL + "/port/Personal/personal";
    //既往病史添加
    public final static String PERSONAL_SAVE_BINGFA = HOST_URL + "/port/Personal/bingfa";
    //肝病记录列表
    public final static String HEPATOPATHY_PABULUM_LIVER_LIST = HOST_URL + "/port/Userdata/liverList";
    //肝病详情
    public final static String HEPATOPATHY_PABULUM_DETAIL = HOST_URL + "/port/Userdata/liverDetails";
    //添加用药
    public final static String ADD_PHARMACY = HOST_URL + "/port/record/addpharmacy";
    //添加用药
    public final static String ADD_PHARMACY_RECORD = HOST_URL + "/port/Personal/addmedicine";
    //接口列表结束
    //随访提交
    public final static String FOLLOW_ADD = HOST_URL + "/port/Followmgt/addFollow";
    public final static String ADD_BUILDING = HOST_URL + "/doctor/Community/addCommunity";
    //获取随访模板列表
    public final static String FOLLOW_UP_ADD_FOLLOW_LIST = HOST_URL + "/doctor/Followmgt/followsPlan";


    private static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");


    public final static String GET_COMMUNITY_LIST = HOST_URL + "/doctor/community/communityLists";


    /**
     * 上传json数据
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void okPostJson(String url, String json, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(body).build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //dismissProgress();
                try {
                    String res = response.body().string();
                    JSONObject object = new JSONObject(res);
                    String code = object.getString("code");
                    if ("200".equals(code)) {
                        String msg = object.getString("msg");
                        callback.onSuccess(msg);
                    } else if ("20001".equals(code)) {
                        exit();
                    } else {
                        String msg = object.getString("msg");
                        callback.onError(Integer.valueOf(code), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 退出
     */
    private static void exit() {
        Log.e("BaseActivity", "发送广播");
        Intent intent = new Intent();
        intent.setAction("LoginOut");
        Utils.getApp().sendBroadcast(intent);
    }

    /**
     * 基本Okhttp请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void okPost(String url, Map<String, Object> params, final OkHttpCallBack<String> callback) {
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        //Json方式提交
        JSONObject json = new JSONObject(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
        //提交
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpInstance.getInstance()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        try {
                            String res = response.body().string();
                            JSONObject object = new JSONObject(res);
                            String code = object.getString("code");
                            if ("200".equals(code)) {
                                String data = object.getString("data");
                                callback.onSuccess(data);
                            } else if ("20001".equals(code)) {
                                exit();
                            } else {
                                String msg = object.getString("msg");
                                callback.onError(Integer.valueOf(code), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
