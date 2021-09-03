package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/8/12 14:50
 * Description: 疾病类型
 */
public class DiseaseTypeInfo {
    private boolean isCheck;
    private String diseaseName;

    private int checkID;

    private String sugarType;
    private String pressureType;

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) {
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

    public DiseaseTypeInfo(String diseaseName) {
        this.diseaseName = diseaseName;
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
