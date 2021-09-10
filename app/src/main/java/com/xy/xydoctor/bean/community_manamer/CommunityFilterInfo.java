package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/7 18:54
 * Description: 筛选
 */
public class CommunityFilterInfo {
    /**
     * 是否为空房间
     * 0：空房间
     * 1：非空房间
     */
    private String isempty;
    private String com_id = "0";
    private String sex;
    private String age_min;
    private String age_max;
    private String other;
    private String disease;

    /**
     * 房间号
     */
    private String house_num;
    /**
     * 特殊信息图片地址
     */
    private String img;
    /**
     * 是否有待办
     * 0：无
     * 1：有
     */
    private String istodo;
    /**
     * 有无异常
     * 0：无
     * 1：有
     */
    private String abnormal;
    private String unit_name;
    private String unit_id;
    private String build_id;
    private String build_name;

    public String getHouse_num() {
        return house_num;
    }

    public void setHouse_num(String house_num) {
        this.house_num = house_num;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIstodo() {
        return istodo;
    }

    public void setIstodo(String istodo) {
        this.istodo = istodo;
    }

    public String getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    private List<FilterSugarPressureInfo> diseaseTypeInfos;
    private List<FilterSugarPressureInfo> otherList;

    private List<CommunityFilterChildInfo> lists;

    private String totalCount;

    public List<CommunityFilterChildInfo> getLists() {
        return lists;
    }

    public void setLists(List<CommunityFilterChildInfo> lists) {
        this.lists = lists;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<FilterSugarPressureInfo> getDiseaseTypeInfos() {
        return diseaseTypeInfos;
    }

    public void setDiseaseTypeInfos(List<FilterSugarPressureInfo> diseaseTypeInfos) {
        this.diseaseTypeInfos = diseaseTypeInfos;
    }

    public List<FilterSugarPressureInfo> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<FilterSugarPressureInfo> otherList) {
        this.otherList = otherList;
    }

    public String getIsempty() {
        return isempty;
    }

    public void setIsempty(String isempty) {
        this.isempty = isempty;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge_min() {
        return age_min;
    }

    public void setAge_min(String age_min) {
        this.age_min = age_min;
    }

    public String getAge_max() {
        return age_max;
    }

    public void setAge_max(String age_max) {
        this.age_max = age_max;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
