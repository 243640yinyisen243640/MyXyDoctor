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

    /**
     * 是否选中
     */
    private boolean isCheck;


    /**
     * 点击是按钮是选中状态还是未选中状态
     */
    private boolean isSelected;

    private List<CommunityFilterChildInfo> members;

    public List<CommunityFilterChildInfo> getMembers() {
        return members;
    }

    public void setMembers(List<CommunityFilterChildInfo> members) {
        this.members = members;
    }

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
