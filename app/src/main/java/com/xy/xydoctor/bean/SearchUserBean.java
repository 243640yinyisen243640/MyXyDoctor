package com.xy.xydoctor.bean;

import java.io.Serializable;

public class SearchUserBean implements Serializable {

    /**
     * userid : 415
     * userId : 781104d0ab8eed18036318cf1fa962a1
     * username : 13213071373
     * nickname : null
     * petname : null
     * sex : 2
     * diabeteslei : 4
     * picture : http://ceshi.xiyuns.cn/public/uploads/20190314/9c03c8eec1d7a5eba33506cd3bd7073c.jpg
     * docid : 0
     */

    private int userid;
    private String userId;
    private String username;//手机号
    private String nickname;//姓名
    private String petname;//昵称
    private int sex;
    private int diabeteslei;
    private int age;
    private String picture;
    private int docid;
    private String registercode;//是否为新注册患者

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getDiabeteslei() {
        return diabeteslei;
    }

    public void setDiabeteslei(int diabeteslei) {
        this.diabeteslei = diabeteslei;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getRegistercode() {
        return registercode;
    }

    public void setRegistercode(String registercode) {
        this.registercode = registercode;
    }
}

