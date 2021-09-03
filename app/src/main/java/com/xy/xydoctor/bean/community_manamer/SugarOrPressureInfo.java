package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/2 15:23
 * Description:
 */
public class SugarOrPressureInfo {


    /**
     *
     */
    private String userid;
    /**
     * 血糖值
     */
    private String glucosevalue;

    private String category;
    private String datetime;
    /**
     * 1偏高2低3正常
     */
    private String ishight;
    /**
     * 晚餐前
     */
    private String categoryname;
    /**
     * 收缩压
     */
    private String diastole;
    /**
     * 舒张压
     */
    private String systolic;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGlucosevalue() {
        return glucosevalue;
    }

    public void setGlucosevalue(String glucosevalue) {
        this.glucosevalue = glucosevalue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIshight() {
        return ishight;
    }

    public void setIshight(String ishight) {
        this.ishight = ishight;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDiastole() {
        return diastole;
    }

    public void setDiastole(String diastole) {
        this.diastole = diastole;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }
}
