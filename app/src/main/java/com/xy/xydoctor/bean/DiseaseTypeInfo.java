package com.xy.xydoctor.bean;

/**
 * Author: LYD
 * Date: 2021/8/12 14:50
 * Description: 疾病类型
 */
public class DiseaseTypeInfo {
    private boolean isCheck;
    private String diseaseName;

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
