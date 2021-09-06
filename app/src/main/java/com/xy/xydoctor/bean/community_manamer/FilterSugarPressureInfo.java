package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/8/12 14:50
 * Description: 疾病类型
 */
public class FilterSugarPressureInfo {
    private boolean isCheck;
    private String diseaseName;

    private String checkID="-1";

    private String sugarType;
    private String pressureType;

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getSugarType() {
        return sugarType;
    }

    public void setSugarType(String sugarType) {
        this.sugarType = sugarType;
    }

    public String getPressureType() {
        return pressureType;
    }

    public void setPressureType(String pressureType) {
        this.pressureType = pressureType;
    }

    public FilterSugarPressureInfo(String diseaseName, String checkID) {
        this.diseaseName = diseaseName;
        this.checkID = checkID;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}
