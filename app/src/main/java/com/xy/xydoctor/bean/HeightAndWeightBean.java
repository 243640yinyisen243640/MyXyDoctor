package com.xy.xydoctor.bean;

import java.util.List;

public class HeightAndWeightBean {

    /**
     * id : 12
     * uid : 369
     * height : 177
     * weight : 75
     * bmi : 23.9
     * datetime : 2019-02-20 14:46
     * remark : [null]
     */

    private int id;
    private int uid;
    private String height;
    private String weight;
    private double bmi;
    private String datetime;
    private List<Object> remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<Object> getRemark() {
        return remark;
    }

    public void setRemark(List<Object> remark) {
        this.remark = remark;
    }
}
