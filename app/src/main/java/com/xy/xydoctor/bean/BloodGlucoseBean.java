package com.xy.xydoctor.bean;

public class BloodGlucoseBean {

    /**
     * glucosevalue : 8.9
     * category : 午餐前
     * datetime : 2018-10-08 13:35
     * type : 1
     */

    private double glucosevalue;
    private String category;
    private String datetime;
    private int type;

    public double getGlucosevalue() {
        return glucosevalue;
    }

    public void setGlucosevalue(double glucosevalue) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
