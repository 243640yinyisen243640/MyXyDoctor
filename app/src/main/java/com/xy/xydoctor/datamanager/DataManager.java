package com.xy.xydoctor.datamanager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.xy.xydoctor.base.retrofit.BaseNetworkUtils;
import com.xy.xydoctor.base.retrofit.HHSoftBaseResponse;
import com.xy.xydoctor.bean.UpdateBean;
import com.xy.xydoctor.bean.community_manamer.CommunityManagerInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineInfo;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.constant.ConstantParam;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.BiConsumer;
import retrofit2.Call;

public class DataManager {
    public static Call<String> getUpData(String accessToken, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("version_code", AppUtils.getAppVersionCode() + "");
        map.put("access_token", accessToken);
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, UpdateBean.class, "port/appversion/getDocVersion", map, successCallBack, failureCallBack);
    }

    /**
     * 上传温度数据
     *
     * @param accessToken
     * @param uid
     * @param temperature
     * @param time
     * @param docId
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> saveDataTemperature(String accessToken, String uid, String temperature, String time, String docId, String type, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);
        map.put("uid", uid);
        map.put("temperature", temperature);
        map.put("datetime", time);
        //上传类型 1自动 2手动
        map.put("type", type);
        map.put("docuserid", docId);
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/port/record/addTemperatureData", map, successCallBack, failureCallBack);
    }

    /**
     * 获取医生下面的患者
     *
     * @param doctorID
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getGroupList(String doctorID, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", doctorID);
        map.put("access_token", SPStaticUtils.getString("token"));
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, GroupUserBeanPatient.class, "/doctor/index/getGroupusers", map, successCallBack, failureCallBack);
    }

    /**
     * 搜索结果
     *
     * @param keyWord
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getGroupListSearch(String keyWord, String docid, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", keyWord);
        map.put("docid", docid);
        map.put("access_token", SPStaticUtils.getString("token"));
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, GroupUserBeanPatient.class, "/doctor/Index/searchusers", map, successCallBack, failureCallBack);
    }

    /**
     * 转移患者
     *
     * @param docid
     * @param userids
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> removePatient(String docid, String userids, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("docid", docid);
        map.put("userids", userids);
        map.put("access_token", SPStaticUtils.getString("token"));
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/index/moveUsers", map, successCallBack, failureCallBack);
    }

    /**
     * 解绑患者
     *
     * @param guid
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> breakBind(String guid, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("guid", guid);
        map.put("access_token", SPStaticUtils.getString("token"));
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/unBindDoc", map, successCallBack, failureCallBack);
    }

    /**
     * cbl
     *
     * @param userID
     * @param page
     * @param pageSize
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> goodsList(String userID, String page, String pageSize, String goodsType, String keyWords, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("goodsType", goodsType);
        map.put("userID", userID);
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("keyWords", keyWords);
        return BaseNetworkUtils.getRequest(false, BaseNetworkUtils.JSON_ARRAY, FollowUpAgentListBean.class, "goodslist", map, successCallBack, failureCallBack);
    }

    public static Call<String> getCommunityHomeData(BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityManagerInfo.class, "/doctor/community/communityDataMap", map, successCallBack, failureCallBack);
    }

    public static Call<String> getWaitingInfo(BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityManagerInfo.class, "/doctor/community/toDoDataMap", map, successCallBack, failureCallBack);
    }

    /**
     * @param page
     * @param com_name        小区名称
     * @param type            查询类型 1:普通小区列表 2：待办小区列表
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getAgentList(String page, String com_name, String type, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", SPStaticUtils.getString("token"));
        map.put("page", page);
        map.put("com_name", com_name);
        map.put("type", type);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, FollowUpAgentListBean.class, "/doctor/community/communityLists", map, successCallBack, failureCallBack);
    }


    /**
     * @param type            1血压2血糖
     * @param starttime       开始时间
     * @param endtime         结束时间
     * @param style           1高2低3正常4未测量5自定义
     * @param startSugar      血糖起始值
     * @param endSugar        血糖结束值
     * @param page            页码
     * @param status          处理是否 0全部 1是 2否
     * @param
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getDataAbnormalList(String type, String starttime, String endtime, String style,
                                                   String startSugar, String endSugar, String page, String status,
                                                   BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("style", style);
        map.put("startSugar", startSugar);
        map.put("endSugar", endSugar);
        map.put("page", page);
        map.put("status", status);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, DataAbnormalInfo.class, "/doctor/Community/getStatistics", map, successCallBack, failureCallBack);
    }


    /**
     * @param userid          用户id集合，英文逗号隔开
     * @param type            1血压2血糖
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> loadCheckList(String userid, String type, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("type", type);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, FollowUpAgentListBean.class, "/doctor/Community/editWorning", map, successCallBack, failureCallBack);
    }

    /**
     * @param userid          患者userid，患者详情进入，必填
     * @param page
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> useMedicineRemind(String userid, String page, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("page", page);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityUseMedicineInfo.class, "/doctor/Pharmacy/getPharmacyLists", map, successCallBack, failureCallBack);
    }


}
