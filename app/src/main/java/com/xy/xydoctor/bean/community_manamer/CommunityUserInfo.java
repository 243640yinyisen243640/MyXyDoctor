package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/8 11:04
 * Description:
 */
public class CommunityUserInfo {

    /**
     * 用户id
     */
    private String userid;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 头像
     */
    private String picture;
    /**
     * 用户id
     */
    private String nickname;
    /**
     * 糖尿病类型
     * 1：I型糖尿病
     * 2：II型糖尿病
     * 3：妊娠糖尿病
     * 4：其他
     * 0：无
     */
    private String diabeteslei;
    /**
     * 血压等级
     * 1：1级
     * 2：2级
     */
    private String bloodLevel;
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别
     */
    private String sex;
    /**
     * 0/2 血糖统计
     */
    private String sugarNum;
    /**
     * 0/2 血压统计
     */
    private String bloodNum;
    /**
     * 0/2 血糖随访
     */
    private String sugarFlg;
    /**
     * 0/2 血压随访
     */
    private String bloodFlg;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDiabeteslei() {
        return diabeteslei;
    }

    public void setDiabeteslei(String diabeteslei) {
        this.diabeteslei = diabeteslei;
    }

    public String getBloodLevel() {
        return bloodLevel;
    }

    public void setBloodLevel(String bloodLevel) {
        this.bloodLevel = bloodLevel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSugarNum() {
        return sugarNum;
    }

    public void setSugarNum(String sugarNum) {
        this.sugarNum = sugarNum;
    }

    public String getBloodNum() {
        return bloodNum;
    }

    public void setBloodNum(String bloodNum) {
        this.bloodNum = bloodNum;
    }

    public String getSugarFlg() {
        return sugarFlg;
    }

    public void setSugarFlg(String sugarFlg) {
        this.sugarFlg = sugarFlg;
    }

    public String getBloodFlg() {
        return bloodFlg;
    }

    public void setBloodFlg(String bloodFlg) {
        this.bloodFlg = bloodFlg;
    }
}
