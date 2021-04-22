package com.xy.xydoctor.bean;

public class SportBean {


    /**
     * id : 7
     * uid : 128958
     * type : 散步走
     * duration : 24
     * imgsrc : http://app.xiyuns.cn/Public/upload/20181127/201811271813041543313584.jpg
     * datetime : 2018-12-01 13:43
     * remark : 不需要备注
     */

    private int id;
    private int uid;
    private String type;
    private int duration;
    private String imgsrc;
    private String datetime;
    private String remark;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
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
}
