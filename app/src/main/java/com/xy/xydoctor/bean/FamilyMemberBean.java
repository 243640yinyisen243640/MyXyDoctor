package com.xy.xydoctor.bean;
/*
 * 类名:     FamilyMemberBean
 * 描述:     家签家庭成员
 * 作者:     ZWK
 * 创建日期: 2020/1/14 16:09
 */

import androidx.annotation.NonNull;

public class FamilyMemberBean {

    /**
     * id : 1
     * tel :
     * idcard :
     * nickname : 张二
     * relation : 1
     */

    private int id;
    private String tel;
    private String idcard;
    private String nickname;
    private String userid;
    private int relation;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    @NonNull
    @Override
    public String toString() {
        return "FamilyMemberBean{" +
                "id=" + id +
                ", tel='" + tel + '\'' +
                ", idcard='" + idcard + '\'' +
                ", nickname='" + nickname + '\'' +
                ", relation=" + relation +
                '}';
    }
}
