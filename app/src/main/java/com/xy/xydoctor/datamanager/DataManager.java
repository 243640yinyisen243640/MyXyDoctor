package com.xy.xydoctor.datamanager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.xy.xydoctor.base.retrofit.BaseNetworkUtils;
import com.xy.xydoctor.base.retrofit.HHSoftBaseResponse;
import com.xy.xydoctor.bean.UpdateBean;
import com.xy.xydoctor.bean.community_manamer.BuildInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityDataStaticsInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityFilterInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityManagerInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityStaticsInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityUseMedicineUserInfo;
import com.xy.xydoctor.bean.community_manamer.CommunityUserInfo;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalInfo;
import com.xy.xydoctor.bean.community_manamer.DepartmentInfo;
import com.xy.xydoctor.bean.community_manamer.FamilyAllInfo;
import com.xy.xydoctor.bean.community_manamer.FollowListChildListInfo;
import com.xy.xydoctor.bean.community_manamer.FollowListInfo;
import com.xy.xydoctor.bean.community_manamer.FollowUpAgentListBean;
import com.xy.xydoctor.bean.community_manamer.FollowUpListAllInfo;
import com.xy.xydoctor.bean.community_manamer.SearchInfo;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureInfo;
import com.xy.xydoctor.bean.community_manamer.UserRecordDataInfo;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.param.UserAddReq;
import com.xy.xydoctor.param.UserRecordReq;

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
     * ??????????????????
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
        //???????????? 1?????? 2??????
        map.put("type", type);
        map.put("docuserid", docId);
        map.put("version", ConstantParam.SERVER_VERSION);
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/port/record/addTemperatureData", map, successCallBack, failureCallBack);
    }

    /**
     * ???????????????????????????
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
     * ????????????
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
     * ????????????
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
     * ????????????
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
     * @param com_name        ????????????
     * @param type            ???????????? 1:?????????????????? 2?????????????????????
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
     * @param type            1??????2??????
     * @param starttime       ????????????
     * @param endtime         ????????????
     * @param style           1???2???3??????4?????????5?????????
     * @param startSugar      ???????????????
     * @param endSugar        ???????????????
     * @param page            ??????
     * @param status          ???????????? 0?????? 1??? 2???
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
     * @param userid          ??????id???????????????????????????
     * @param type            1??????2??????
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
     * @param userid          ??????userid??????????????????????????????
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
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, CommunityUseMedicineInfo.class, "/doctor/Pharmacy/getPharmacyLists", map, successCallBack, failureCallBack);
    }

    /**
     * @param userid          ??????userid??????????????????????????????
     * @param page
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> useMedicineRemindUser(String userid, String page, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("page", page);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, CommunityUseMedicineUserInfo.class, "/doctor/Pharmacy/getPharmacyLists", map, successCallBack, failureCallBack);
    }


    /**
     * ????????????
     *
     * @param pharmacy_id     ??????id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> finishWaiting(String pharmacy_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("pharmacy_id", pharmacy_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Pharmacy/finishPharmacy", map, successCallBack, failureCallBack);
    }

    /**
     * @param pharmacy_id     ??????id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> remindUser(String pharmacy_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("pharmacy_id", pharmacy_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Pharmacy/sendMsg", map, successCallBack, failureCallBack);
    }


    /**
     * ??????????????????
     *
     * @param pharmacy_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getUseMedicineInfo(String pharmacy_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("id", pharmacy_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityUseMedicineUserInfo.class, "/doctor/Pharmacy/communityMedicine", map, successCallBack, failureCallBack);
    }


    /**
     * @param id              ??????id ????????????
     * @param userid          ??????id ????????????
     * @param drugname        ?????????
     * @param number          ??????
     * @param timeType        ???????????? 1??? 2??? 3???
     * @param dosage          ????????????
     * @param type            ?????????????????? 1mg 2g 3iu 4ml 5ug
     * @param starttime       ?????????????????? 2021-01-01
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> loadMedicineData(String id, String userid, String drugname, String number, String times,
                                                String timeType, String dosage, String type,
                                                String starttime,
                                                BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("userid", userid);
        map.put("drugname", drugname);
        map.put("number", number);
        map.put("times", times);
        map.put("timeType", timeType);
        map.put("dosage", dosage);
        map.put("type", type);
        map.put("starttime", starttime);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityUseMedicineUserInfo.class, "/doctor/Pharmacy/addCommunityMedicine", map, successCallBack, failureCallBack);
    }

    /**
     * ??????????????????
     *
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getCommunityList(BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, CommunityUseMedicineInfo.class, "/doctor/community/communityBaseList", map, successCallBack, failureCallBack);
    }


    /**
     * @param isempty         ???????????????  0????????????  1??????????????????
     * @param com_id          ??????id??????????????????0
     * @param sex             ?????? 1?????? 2??????
     * @param age_min         ???????????????
     * @param age_max         ???????????????
     * @param other           ????????????(??????????????????)1????????????  2??????????????? 3????????? 4??????????????? 5?????????
     * @param disease         ????????????(??????????????????)
     *                        1????????????
     *                        2????????????
     *                        3?????????/??????
     *                        4????????????
     *                        5????????????
     *                        6????????????
     * @param page
     * @param successCallBack
     * @param failureCallBack
     * @return
     */

    public static Call<String> getFilterList(String isempty, String com_id, String sex, String age_min,
                                             String age_max, String other, String disease, String page, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("isempty", isempty);
        map.put("com_id", com_id);
        map.put("sex", sex);
        map.put("age_min", age_min);
        map.put("age_max", age_max);
        map.put("other", other);
        map.put("disease", disease);
        map.put("page", page);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityFilterInfo.class, "/doctor/Community/search", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     *
     * @param userid
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getCommunityUserInfo(String userid, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityUserInfo.class, "/doctor/Community/communityUserInfo", map, successCallBack, failureCallBack);
    }


    /**
     * @param userid
     * @param type            1??????2??????
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getSugarOrPressureList(String userid, String type, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("type", type);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, SugarOrPressureInfo.class, "/doctor/Community/communityFollowList", map, successCallBack, failureCallBack);
    }

    /**
     * @param years           ??????
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getFollowStatics(String years, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("years", years);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityStaticsInfo.class, "/doctor/Community/followStatistic", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     *
     * @param years
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getDataStatics(String years, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("years", years);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, CommunityDataStaticsInfo.class, "/doctor/Community/dataStatistic", map, successCallBack, failureCallBack);
    }

    /**
     * @param com_id
     * @param status          1????????? 2?????? 3?????????
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getFollowList(String status, String com_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("status", status);
        map.put("com_id", com_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, FollowUpListAllInfo.class, "/doctor/Community/followLists", map, successCallBack, failureCallBack);
    }


    /**
     * @param status          1????????? 2??????
     * @param userid          ??????userid
     * @param close           ?????? 1??? 0???
     * @param reasonType      1????????? 2???????????? 3?????? 4???????????? 5??????
     * @param reason          ??????
     * @param successCallBack
     * @param failureCallBack
     * @return
     */

    public static Call<String> followEdit(String status, String userid, String close, String reasonType, String reason, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("status", status);
        map.put("userid", userid);
        map.put("close", close);
        map.put("reasonType", reasonType);
        map.put("reason", reason);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Community/editFollow", map, successCallBack, failureCallBack);


    }


    /**
     * ????????????
     *
     * @param com_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getBuildingList(String com_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("com_id", com_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, FollowListChildListInfo.class, "/doctor/Community/buildings", map, successCallBack, failureCallBack);
    }

    public static Call<String> ces(String unit_data, String com_id, String build_name, String layer, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("unit_data", unit_data);
        map.put("com_id", com_id);
        map.put("build_name", build_name);
        map.put("layer", layer);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Community/addCommunity", map, successCallBack, failureCallBack);
    }

    /**
     * ??????????????????
     *
     * @param build_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getBuildingInfo(String build_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("build_id", build_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, FollowListInfo.class, "/doctor/Community/buildDetail", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     *
     * @param build_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> deleteBuilding(String build_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("build_id", build_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Community/delBuild", map, successCallBack, failureCallBack);
    }

    /**
     * ??????????????????
     *
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getDepartmentList(String build_id, String hosp_userid, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("build_id", build_id);
        map.put("hosp_userid", hosp_userid);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, DepartmentInfo.class, "/doctor/community/getHosDep", map, successCallBack, failureCallBack);
    }

    /**
     * ??????????????????
     *
     * @param dep_userid  dep_userid
     * @param build_id  build_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getDoctorList(String dep_userid, String build_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("dep_userid", dep_userid);
        map.put("build_id", build_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, SearchInfo.class, "/doctor/Community/getDepDoc", map, successCallBack, failureCallBack);
    }


    /**
     * ???????????????id,???????????????
     *
     * @param tel
     * @param build_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getHospitalList(String tel, String build_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("tel", tel);
        map.put("build_id", build_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, SearchInfo.class, "/doctor/Community/searchTel", map, successCallBack, failureCallBack);
    }


    /**
     * ????????????
     *
     * @param addReq
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> addUser(UserAddReq addReq, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {

        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, SearchInfo.class, "/doctor/Community/addCommunityUser", addReq.initAddParamMap(), successCallBack, failureCallBack);
    }

    /**
     * @param type            ???????????? 1:?????????????????? 2?????????????????????
     * @param com_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getCommunityDetails(String type, String com_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("com_id", com_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, FollowUpAgentListBean.class, "/doctor/Community/communityDetail", map, successCallBack, failureCallBack);
    }


    /**
     * ??????????????????
     *
     * @param com_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getCommunityBuildUnitInfo(String com_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("com_id", com_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, FollowListInfo.class, "/doctor/Community/getBuilds", map, successCallBack, failureCallBack);
    }


    /**
     * @param unit_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getCommunityRoomInfo(String unit_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("unit_id", unit_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, CommunityFilterInfo.class, "/doctor/Community/getHouses", map, successCallBack, failureCallBack);
    }

    /**
     * ??????????????????
     *
     * @param house_id
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getFamilyInfo(String house_id, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("house_id", house_id);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, FamilyAllInfo.class, "/doctor/Community/houseUsers", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     *
     * @param care            ?????? 1??? 2???
     * @param userid
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> followUser(String care, String userid, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("care", care);
        map.put("userid", userid);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Community/editCare", map, successCallBack, failureCallBack);
    }

    public static Call<String> searchUser(String com_id, String keyword, String page, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("com_id", com_id);
        map.put("keyword", keyword);
        map.put("page", page);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, FollowUpAgentListBean.class, "/doctor/Community/searchUser", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     *
     * @param userid
     * @param type            1??????2??????
     * @param reason
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> editUser(String userid, String type, String reason, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("type", type);
        map.put("reason", reason);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Community/editUser", map, successCallBack, failureCallBack);
    }


    /**
     * @param id              ??????/??????/??????id???
     *                        type != 1???,??????????????????
     * @param type            1???????????????2???????????????3??????????????? 4???????????????
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getCommunityInfo(String id, String type, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("type", type);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, BuildInfo.class, "/doctor/community/communityAllList", map, successCallBack, failureCallBack);
    }

    /**
     * @param datetime
     * @param page
     * @param type            ????????????
     *                        1:??????????????????-????????????
     *                        2?????????????????????-????????????
     *                        3???????????????-????????????
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getuserDateiData(String datetime, String page, String type, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("datetime", datetime);
        map.put("page", page);
        map.put("type", type);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, DataAbnormalInfo.class, "/doctor/community/recordLists", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     *
     * @param datetime
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getRecordData(String datetime, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("datetime", datetime);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, UserRecordDataInfo.class, "/doctor/community/recordStatistic", map, successCallBack, failureCallBack);
    }


    /**
     * ??????????????????
     *
     * @param userid
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getRecordInfo(String userid, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_OBJECT, SearchInfo.class, "/doctor/community/getMemberComInfo", map, successCallBack, failureCallBack);
    }

    /**
     * ????????????
     * @param addReq
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> addRecord(UserRecordReq addReq, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {

        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.NONE, null, "/doctor/Community/saveMemberConInfo", addReq.initAddParamMap(), successCallBack, failureCallBack);
    }


    /**
     * ??????????????????????????????
     *
     * @param page
     * @param successCallBack
     * @param failureCallBack
     * @return
     */
    public static Call<String> getWaitImportData(String page, BiConsumer<Call<String>, HHSoftBaseResponse> successCallBack, BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("access_token", SPStaticUtils.getString("token"));
        return BaseNetworkUtils.postRequest(false, BaseNetworkUtils.JSON_ARRAY, SearchInfo.class, "/doctor/community/getImportLists", map, successCallBack, failureCallBack);
    }

}
