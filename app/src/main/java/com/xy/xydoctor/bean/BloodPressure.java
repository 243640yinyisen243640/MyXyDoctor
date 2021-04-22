package com.xy.xydoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yicheng on 2018/5/28.
 * 获取血压数据实体类
 */

public class BloodPressure implements Serializable {


    /**
     * id : 92
     * uid : 369
     * systolic : 120
     * diastole : 88
     * heartrate : 80
     * datetime : 2019-02-20 14:37
     * remark : [null]
     * timepoint : 3
     * addtime : 1550644476
     * level : 4
     */

    private int id;
    private int uid;
    private int systolic;
    private int diastole;
    private int heartrate;
    private String datetime;
    private int timepoint;
    private int addtime;
    private int level;
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

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastole() {
        return diastole;
    }

    public void setDiastole(int diastole) {
        this.diastole = diastole;
    }

    public int getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(int heartrate) {
        this.heartrate = heartrate;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(int timepoint) {
        this.timepoint = timepoint;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Object> getRemark() {
        return remark;
    }

    public void setRemark(List<Object> remark) {
        this.remark = remark;
    }
}
