package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/9 11:48
 * Description:
 */
public class FollowListChildListInfo {

    /**
     *
     */
    private String userid;
    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private String tel;
    /**
     *
     */
    private String sex;
    /**
     *
     血糖类型 1、I型糖尿病 2、II型糖尿病 3、妊娠糖尿病 4、其他 0无
     */
    private String diabeteslei;
    /**
     *
     血压等级 血压等级 1 1级 2 2级 0无
     */
    private String bloodLevel;
    /**
     *
     */
    private String build_id;
    /**
     *
     1号楼
     */
    private String build_name;
    /**
     *
     1单元
     */
    private String unit_name;
    /**
     *
     201 房间号
     */
    private String house_num;
    /**
     *
     31 年龄
     */
    private String age;
    /**
     *2/3 血糖信息
     */
    private String sugar;
    /**
     *
     0/3 血压信息
     */
    private String blood;
    /**
     *0 不做处理 1未接通 2拒绝随访 3拒接 4数据不清 5其它
     */
    private String reason;
    /**
     *
     时间
     */
    private String finishtime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getHouse_num() {
        return house_num;
    }

    public void setHouse_num(String house_num) {
        this.house_num = house_num;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }
}
