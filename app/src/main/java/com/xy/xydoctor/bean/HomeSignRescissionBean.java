package com.xy.xydoctor.bean;
/*
 * 类名:     HomeSignRescissionBean
 * 描述:     家签解约
 * 作者:     ZWK
 * 创建日期: 2020/1/16 11:54
 */

public class HomeSignRescissionBean {
    /**
     * id : 416
     * tel : 234
     * idcard :
     * nickname : xy_7ji954
     * starttime : 2020-01-08
     * endtime : 2020-01-08
     */

    private int id;
    private String tel;
    private String idcard;
    private String nickname;
    private String starttime;
    private String endtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
