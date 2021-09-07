package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/6 15:05
 * Description: 用药提醒
 */
public class CommunityUseMedicineInfo {
    private String com_id;
    /**
     * 小区名称
     */
    private String com_name;
    /**
     * 小区地址
     */
    private String com_address;

    private List<CommunityUseMedicineUserInfo> pharmacys;


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

    public String getCom_address() {
        return com_address;
    }

    public void setCom_address(String com_address) {
        this.com_address = com_address;
    }

    public List<CommunityUseMedicineUserInfo> getPharmacys() {
        return pharmacys;
    }

    public void setPharmacys(List<CommunityUseMedicineUserInfo> pharmacys) {
        this.pharmacys = pharmacys;
    }
}
