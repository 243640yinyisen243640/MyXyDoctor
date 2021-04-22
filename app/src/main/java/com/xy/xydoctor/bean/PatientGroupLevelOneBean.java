package com.xy.xydoctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xy.xydoctor.adapter.PatientGroupLevelAdapter;

import java.io.Serializable;

/**
 * 描述: 患者分组 One
 * 作者: LYD
 * 创建日期: 2019/2/27 11:01
 */
public class PatientGroupLevelOneBean implements MultiItemEntity, Serializable {
    private String imgHeadUrl;
    private String name;
    private int sex;//1男2女
    private int age;
    private String tel;
    private String type;//糖尿病类型
    private int userid;//用户ID

    public PatientGroupLevelOneBean() {
    }

    public PatientGroupLevelOneBean(int userid, String imgHeadUrl, String name, int sex, int age, String tel, String type) {
        this.userid = userid;
        this.imgHeadUrl = imgHeadUrl;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.tel = tel;
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImgHeadUrl() {
        return imgHeadUrl;
    }

    public void setImgHeadUrl(String imgHeadUrl) {
        this.imgHeadUrl = imgHeadUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int getItemType() {
        return PatientGroupLevelAdapter.TYPE_LEVEL_1;
    }
}
