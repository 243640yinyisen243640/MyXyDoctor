package com.xy.xydoctor.datamanager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.xy.xydoctor.base.retrofit.BaseNetworkUtils;
import com.xy.xydoctor.base.retrofit.HHSoftBaseResponse;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.bean.UpdateBean;
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
     * @param goodsUserID
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


}
