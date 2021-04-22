package com.xy.xydoctor.bean;

public class SearchUserIndexBean {
    /**
     * userid : 643599
     * userId : 60181135decfb0f6072682ad31add1a8
     * picture : http://doctor.xiyuns.cn/Public/images/navimg/userimg.png
     * username : 17698302799
     * nickname : 李林峰
     * sex : 1
     * age : 29
     * diabeteslei : 3
     * diabetesleitime : 0
     * height : 1000
     * weight : 否1否 否 否1 否 f
     */

    private int userid;
    private String userId;
    private String picture;
    private String username;
    private String nickname;
    private int sex;
    private int age;
    private int diabeteslei;
    private int diabetesleitime;
    private String height;
    private String weight;

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

    public int getDiabeteslei() {
        return diabeteslei;
    }

    public void setDiabeteslei(int diabeteslei) {
        this.diabeteslei = diabeteslei;
    }

    public int getDiabetesleitime() {
        return diabetesleitime;
    }

    public void setDiabetesleitime(int diabetesleitime) {
        this.diabetesleitime = diabetesleitime;
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
}
