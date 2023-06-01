package com.xy.xydoctor.bean;

public class UserInfoBean {
    /**
     * userid : 129199
     * userId : 409f4d086c2b769e929de6672a3d646c
     * picture : http://port.xiyuns.cn/public/uploads/20190628/9c77d91388e80918dbc285146f3c236d.jpg
     * username : 13137750608
     * nickname : 赵丽颖
     * sex : 1
     * age : 0
     * diabeteslei : 2
     * diabetesleitime : 2000-01-01
     * height : 170
     * weight : 65
     */

    private int userid;
    private String userId;
    private String picture;
    private String username;
    private String nickname;
    private int sex;
    private int age;
    private int diabeteslei;
    private String diabetesleitime;
    private String height;
    private String weight;
    /**
     * 血压计的设备号
     */
    private String snnum;
    /**
     * 血糖计的设备号
     */
    private String imei;

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

    public String getDiabetesleitime() {
        return diabetesleitime;
    }

    public void setDiabetesleitime(String diabetesleitime) {
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

    public String getSnnum() {
        return snnum;
    }

    public void setSnnum(String snnum) {
        this.snnum = snnum;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
