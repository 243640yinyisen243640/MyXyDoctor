package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/7 20:50
 * Description:
 */
public class CommunityFilterChildInfo {
    /**
     * 用户userid
     */
    private String userid;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 性别
     */
    private String sex;
    /**
     * 小区名称
     */
    private String com_name;
    /**
     * 户号
     */
    private String house_num;
    /**
     * 楼栋id
     */
    private String build_id;
    /**
     * 楼栋
     */
    private String build_name;
    /**
     * 单元
     */
    private String unit_name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 图片地址
     */
    private List<String> imgs;
    /**
     * 病名称
     */
    private List<String> diseases;
    /**
     * 小区地址
     */
    private String city_address;
    /**
     * 是否死亡
     * 1:是
     * 2:否
     */
    private String isdeath;

    private String unit_id;
    private String house_id;
    private String com_id;
    /**
     * 建档人
     */
    private String create_user;

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

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

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getHouse_num() {
        return house_num;
    }

    public void setHouse_num(String house_num) {
        this.house_num = house_num;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    public String getCity_address() {
        return city_address;
    }

    public void setCity_address(String city_address) {
        this.city_address = city_address;
    }

    public String getIsdeath() {
        return isdeath;
    }

    public void setIsdeath(String isdeath) {
        this.isdeath = isdeath;
    }
}
