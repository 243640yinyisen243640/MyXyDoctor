package com.xy.xydoctor.bean;

import androidx.annotation.NonNull;

import java.util.List;

public class FollowUpVisitCreateBean {

    /**
     * times : 11
     * visittime : 2019-08-17
     * subject : [1,2,3,4]
     * access_token : b086585bf7a707a10381357e894fb08c
     * status : 2
     * vid : 8
     */

    private String times;
    private String visittime;
    private List<String> subject;
    private String uid;
    private String access_token;
    private int type;
    private int status;
    private int vid;

    private String way;
    private String remind;
    private String recontent;
    private int is_family;

    public int getIs_family() {
        return is_family;
    }

    public void setIs_family(int is_family) {
        this.is_family = is_family;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getRecontent() {
        return recontent;
    }

    public void setRecontent(String recontent) {
        this.recontent = recontent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
