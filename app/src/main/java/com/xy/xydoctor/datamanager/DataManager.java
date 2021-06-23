package com.xy.xydoctor.datamanager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.librongim.myrongim.GroupListAllDataBean;
import com.xy.xydoctor.base.retrofit.BaseNetworkUtils;
import com.xy.xydoctor.base.retrofit.HHSoftBaseResponse;
import com.xy.xydoctor.bean.UpdateBean;
import com.xy.xydoctor.constant.ConstantParam;

import java.util.HashMap;

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
        map.put("version",ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, GroupListAllDataBean.class, "/doctor/index/getGroupusers", map, successCallBack, failureCallBack);
    }

}
