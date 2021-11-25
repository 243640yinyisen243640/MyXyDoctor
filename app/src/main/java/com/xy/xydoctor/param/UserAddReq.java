package com.xy.xydoctor.param;

import com.blankj.utilcode.util.SPStaticUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: LYD
 * Date: 2021/9/10 9:59
 * Description: 添加用户的请求参数
 */
public class UserAddReq {

    /**
     *
     */
    private String house_id = "0";
    /**
     *
     */
    private String userid = "0";
    /**
     *
     */
    private String nickname = "";
    /**
     *
     */
    private String tel = "";
    /**
     *
     */
    private String sex = "2";
    /**
     *
     */
    private String birthtime = "";
    /**
     *
     */
    private String relation = "-1";
    /**
     *
     */
    private String hos_userid = "0";
    /**
     *
     */
    private String dep_userid = "0";
    /**
     *
     */
    private String doc_userid = "0";
    /**
     * 糖尿病类型
     * 1：I型糖尿病
     * 2：II型糖尿病
     * 3：妊娠糖尿病
     * 4：其他
     * 0：无
     */
    private String diabeteslei = "0";
    /**
     * 高血压 1是 2否
     */
    private String hypertension = "2";
    /**
     * 血压水平 1一级 2二级
     */
    private String bloodLevel = "1";
    /**
     * 脑卒中 1是 2否
     */
    private String stroke = "2";
    /**
     * 肥胖 1是 2否
     */
    private String fat = "2";
    /**
     * 脂肪肝 1是 2否
     */
    private String fattyliver = "2";
    /**
     * 冠心病 1是 2否
     */
    private String coronary = "2";
    /**
     * 党员 1是 2否
     */
    private String party_member = "2";
    /**
     * 精神问题 1是 2否
     */
    private String mental_illness = "2";
    /**
     * 孕1是 2否
     */
    private String pregnant;
    /**
     * 结核：1是 2否
     */
    private String tuberculosis;

    /**
     * 是否残疾  1是 2否
     */
    private String disability = "2";
    /**
     * 关注 1是 2否
     */
    private String iscare = "2";
    /**
     * 血糖设备
     */
    private String sugar_imei = "";
    /**
     * 血压设备
     */
    private String blood_imei = "";


    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getHos_userid() {
        return hos_userid;
    }

    public void setHos_userid(String hos_userid) {
        this.hos_userid = hos_userid;
    }

    public String getDep_userid() {
        return dep_userid;
    }

    public void setDep_userid(String dep_userid) {
        this.dep_userid = dep_userid;
    }

    public String getDoc_userid() {
        return doc_userid;
    }

    public void setDoc_userid(String doc_userid) {
        this.doc_userid = doc_userid;
    }

    public String getDiabeteslei() {
        return diabeteslei;
    }

    public void setDiabeteslei(String diabeteslei) {
        this.diabeteslei = diabeteslei;
    }

    public String getHypertension() {
        return hypertension;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getBloodLevel() {
        return bloodLevel;
    }

    public void setBloodLevel(String bloodLevel) {
        this.bloodLevel = bloodLevel;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFattyliver() {
        return fattyliver;
    }

    public void setFattyliver(String fattyliver) {
        this.fattyliver = fattyliver;
    }

    public String getCoronary() {
        return coronary;
    }

    public void setCoronary(String coronary) {
        this.coronary = coronary;
    }

    public String getParty_member() {
        return party_member;
    }

    public void setParty_member(String party_member) {
        this.party_member = party_member;
    }

    public String getMental_illness() {
        return mental_illness;
    }

    public void setMental_illness(String mental_illness) {
        this.mental_illness = mental_illness;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getIscare() {
        return iscare;
    }

    public void setIscare(String iscare) {
        this.iscare = iscare;
    }

    public String getSugar_imei() {
        return sugar_imei;
    }

    public void setSugar_imei(String sugar_imei) {
        this.sugar_imei = sugar_imei;
    }

    public String getBlood_imei() {
        return blood_imei;
    }

    public void setBlood_imei(String blood_imei) {
        this.blood_imei = blood_imei;
    }

    public Map<String, String> initAddParamMap() {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", SPStaticUtils.getString("token"));
        map.put("house_id", house_id);
        map.put("userid", userid);
        map.put("nickname", nickname);
        map.put("tel", tel);
        map.put("sex", sex);
        map.put("birthtime", birthtime);
        map.put("relation", relation);
        map.put("hos_userid", hos_userid);
        map.put("dep_userid", dep_userid);
        map.put("doc_userid", doc_userid);
        map.put("diabeteslei", diabeteslei);
        map.put("hypertension", hypertension);
        map.put("bloodLevel", bloodLevel);
        map.put("stroke", stroke);
        map.put("fat", fat);
        map.put("fattyliver", fattyliver);
        map.put("coronary", coronary);
        map.put("party_member", party_member);
        map.put("mental_illness", mental_illness);
        map.put("disability", disability);
        map.put("iscare", iscare);
        map.put("sugar_imei", sugar_imei);
        map.put("blood_imei", blood_imei);

        return map;
    }
}
