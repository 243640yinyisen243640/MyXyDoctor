package com.xy.xydoctor.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {
    /**
     * depname : 糖尿病科室
     * docname : 宋医生
     * type : 4
     * telephoe : null
     * access_token : 17af434ddca2fc5026479c8a758792f9
     * docid : 644168
     * userid : 644168
     * username : 13838574205
     * picture : http://doctor.xiyuns.cn/Public/upload/20191012/201910121824391570875879.jpg
     */
    private String depname;
    private String docname;
    /**
     * 3:主任  4:医生  10:物业
     */
    private int type;
    private String telephoe;
    private String access_token;
    private int docid;
    private int userid;
    private String username;
    private String picture;
    /**
     * 是否加入社区医院 1:未加入 2:已加入
     */
    private String isproperty;
    /**
     * 医院名字
     */
    private String hospitalname;


    public String getIsproperty() {
        return isproperty;
    }

    public void setIsproperty(String isproperty) {
        this.isproperty = isproperty;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTelephoe() {
        return telephoe;
    }

    public void setTelephoe(String telephoe) {
        this.telephoe = telephoe;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}


