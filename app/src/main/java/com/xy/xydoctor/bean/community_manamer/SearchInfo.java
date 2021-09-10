package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/10 9:37
 * Description:
 */
public class SearchInfo {
    /**
     *
     */
    private String userid;
    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private String sex;
    /**
     *
     */
    private String birthtime;
    /**
     * 糖尿病类型
     */
    private String diabeteslei;
    /**
     * 高血压  0:无  1：一级  2：二级
     */
    private String hypertension;
    /**
     * 医院id
     */
    private String hos_userid;
    /**
     * 医院名字
     */
    private String hospitalname;
    /**
     * 科室id
     */
    private String dep_userid;
    /**
     * 科室名字
     */
    private String depname;
    /**
     * 医生id
     */
    private String doc_id;
    /**
     * 医生名字
     */
    private String docname;
    /**
     * 血糖仪设备号
     */
    private String sugar_imei;
    /**
     * 血压设备号
     */
    private String blood_imei;
    /**
     * 冠心病 1是 2否
     */
    private String coronary;
    /**
     * 脂肪肝 1是 2否
     */
    private String fattyliver;

    /**
     * 肥胖 1是 2否
     */
    private String fat;
    /**
     * 脑卒中 1是 2否
     */
    private String stroke;
    /**
     * 党员 1是 2否
     */
    private String party_member;

    /**
     * 残疾 1是 2否
     */
    private String disability;
    /**
     * 精神病 1是 2否
     */
    private String mental_illness;

    /**
     * 关注 1是 2否
     */
    private String iscare;

    private List<DepartmentInfo> hosInfo;

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

    public String getHos_userid() {
        return hos_userid;
    }

    public void setHos_userid(String hos_userid) {
        this.hos_userid = hos_userid;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getDep_userid() {
        return dep_userid;
    }

    public void setDep_userid(String dep_userid) {
        this.dep_userid = dep_userid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
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

    public String getCoronary() {
        return coronary;
    }

    public void setCoronary(String coronary) {
        this.coronary = coronary;
    }

    public String getFattyliver() {
        return fattyliver;
    }

    public void setFattyliver(String fattyliver) {
        this.fattyliver = fattyliver;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getParty_member() {
        return party_member;
    }

    public void setParty_member(String party_member) {
        this.party_member = party_member;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getMental_illness() {
        return mental_illness;
    }

    public void setMental_illness(String mental_illness) {
        this.mental_illness = mental_illness;
    }

    public String getIscare() {
        return iscare;
    }

    public void setIscare(String iscare) {
        this.iscare = iscare;
    }

    public List<DepartmentInfo> getHosInfo() {
        return hosInfo;
    }

    public void setHosInfo(List<DepartmentInfo> hosInfo) {
        this.hosInfo = hosInfo;
    }
}
