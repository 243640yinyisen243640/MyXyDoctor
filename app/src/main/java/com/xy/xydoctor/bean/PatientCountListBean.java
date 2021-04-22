package com.xy.xydoctor.bean;

public class PatientCountListBean {
    /**
     * picture : http://ceshi.xiyuns.cn/public/uploads/20190131/92558d3ebc75a1bc4c3ab7925a07025a.png
     * username : 17600900989
     * sex : 1
     * diabeteslei : 2
     * age : 0
     * height : 170
     * weight : 78
     * diabetesleitime : 2019-02-21
     * nickname : 身体健康1
     * addtime : 2019-09-07 11:25
     * glucosevalue : 5.6
     * category : 3
     * categoryname : 午餐前
     * userid : 123
     */
    private int userid;
    private String picture;
    private String username;
    private String nickname;
    private int sex;
    private int age;

    private int diabeteslei;
    private int systolic;
    private int diastole;
    private String height;
    private String weight;
    private String diabetesleitime;
    private String addtime;
    private double glucosevalue;
    private String category;
    private String categoryname;


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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getDiabetesleitime() {
        return diabetesleitime;
    }

    public void setDiabetesleitime(String diabetesleitime) {
        this.diabetesleitime = diabetesleitime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public double getGlucosevalue() {
        return glucosevalue;
    }

    public void setGlucosevalue(double glucosevalue) {
        this.glucosevalue = glucosevalue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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
}
