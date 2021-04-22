package com.xy.xydoctor.bean;

public class OnlineTestGetBloodSugar {

    /**
     * glucosevalue : 5.1
     * category : 1
     * datetime : 1563160005
     * addtime : 1563160005
     */

    private double glucosevalue;
    private String category;
    private long datetime;
    private long addtime;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }
}
