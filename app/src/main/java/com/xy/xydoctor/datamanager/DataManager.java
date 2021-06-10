package com.xy.xydoctor.datamanager;

import com.blankj.utilcode.util.AppUtils;
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
}
