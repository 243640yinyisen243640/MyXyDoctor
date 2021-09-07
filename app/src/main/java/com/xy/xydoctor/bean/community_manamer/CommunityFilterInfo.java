package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/7 18:54
 * Description: 筛选
 */
public class CommunityFilterInfo {
    private String isempty;
    private String com_id = "0";
    private String sex;
    private String age_min;
    private String age_max;
    private String other;
    private String disease;

    private List<FilterSugarPressureInfo> diseaseTypeInfos;
    private List<FilterSugarPressureInfo> otherList;

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
