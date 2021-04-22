package com.xy.xydoctor.bean;

public class BloodPressureBean {

    /**
     * id : 64
     * uid : 123
     * systolic : 63
     * diastole : 48
     * heartrate : 60
     * datetime : 2018-09-28 14:32
     * remark :
     * timepoint : null
     * addtime : 1538116102
     * level : 1
     */

    private int id;
    private int uid;
    private int systolic;
    private int diastole;
    private int heartrate;
    private String datetime;
    private String remark;
    private Object timepoint;
    private int addtime;
    private int level;

    //1自动  2手动
    private int type;
    //1偏高  2偏低  3正常
    private int ishight;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Object timepoint) {
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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIshight() {
        return ishight;
    }

    public void setIshight(int ishight) {
        this.ishight = ishight;
    }
}
