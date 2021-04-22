package com.lyd.librongim.myrongim;

import java.io.Serializable;

/**
 * 分组成员
 */
public class GroupUserBean implements Serializable {

    /**
     * userid : 108
     * userId : 1db6f47e0dbd4d6996243b8ec03df3fa
     * picture : http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg
     * username : 13603442088
     * nickname : 王俊娜
     * sex : 2
     * age : 34
     */

    private int userid;
    private String userId;
    private String picture;
    private String username;
    private String nickname;
    private int sex;
    private int age;

    public GroupUserBean() {
    }

    public GroupUserBean(int userid, String picture, String nickname, int sex, int age) {
        this.userid = userid;
        this.picture = picture;
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
