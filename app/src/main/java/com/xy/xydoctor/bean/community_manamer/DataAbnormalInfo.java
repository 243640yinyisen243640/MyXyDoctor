package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/17 15:28
 * Description: 数据异常
 */
public class DataAbnormalInfo {


    public DataAbnormalInfo(String com_name) {
        this.com_name = com_name;
    }

    private List<DataAbnormalChildInfo> communityUser;


    private String com_name;
    private String com_id;



    public List<DataAbnormalChildInfo> getCommunityUser() {
        return communityUser;
    }

    public void setCommunityUser(List<DataAbnormalChildInfo> communityUser) {
        this.communityUser = communityUser;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }
}
