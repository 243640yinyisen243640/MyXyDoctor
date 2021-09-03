package com.xy.xydoctor.bean.community_manamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/2 15:16
 * Description:
 */
public class DataAbnormalChildInfo {


    private List<SugarOrPressureInfo> list = new ArrayList<>();

    private String userid;
    private String nickname;
    private String tel;

    private String com_id;
    /**
     * 医院名称
     */
    private String com_name;
    /**
     * 几号楼 1号楼
     */
    private String build_name;
    /**
     * 几单元 1单元
     */
    private String unit_name;
    /**
     * 几房间 1001
     */
    private String house_num;

    /**
     * 是否选中，这个是在activity里面点击头部按钮，让选择按钮显示隐藏
     */

    private boolean isCheck;

    /**
     * 点击是按钮是选中状态还是未选中状态
     */
    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<SugarOrPressureInfo> getList() {
        return list;
    }

    public void setList(List<SugarOrPressureInfo> list) {
        this.list = list;
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

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
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
}
