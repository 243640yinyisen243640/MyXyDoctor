package com.xy.xydoctor.bean;

public class MyTreatPlanBean {
    /**
     * id : 424
     * time : 2019-02-27 18:16
     */

    private int id;
    private String time;
    private String treatment;
    /**
     * "sendtime": "2018-12-28 16:41:57"
     * "url": "http://chronic.xiyuns.cn/chufang/pinggu/cfid/84/secret/da317b17f20f2638f60364666e83b2b7/num/56dcc01afa0c4a768d6f1e54e3e62asdf"
     */
    private String sendtime;
    private String url;

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
