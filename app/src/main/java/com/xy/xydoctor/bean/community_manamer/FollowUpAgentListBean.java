package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

public class FollowUpAgentListBean {


    /**
     * oid : 47
     * oxygen : 95.7
     * bpmval : 85.0
     * type : 2
     * datetime : 2020-05-14 14:40
     */

    /**
     * 小区id
     */
    private int com_id;
    /**
     * 小区名
     */
    private String com_name;
    /**
     * 小区地址
     */
    private String com_address;
    /**
     * 楼栋数
     */
    private String build_count;
    /**
     * 单元数
     */
    private String unity_count;
    /**
     * 户数
     */
    private String house_count;
    /**
     * 总人数
     */
    private String member_count;

    private List<FollowUpAgentListBean> plan_list;

    public List<FollowUpAgentListBean> getPlan_list() {
        return plan_list;
    }

    public void setPlan_list(List<FollowUpAgentListBean> plan_list) {
        this.plan_list = plan_list;
    }

    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
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

    public String getBuild_count() {
        return build_count;
    }

    public void setBuild_count(String build_count) {
        this.build_count = build_count;
    }

    public String getUnity_count() {
        return unity_count;
    }

    public void setUnity_count(String unity_count) {
        this.unity_count = unity_count;
    }

    public String getHouse_count() {
        return house_count;
    }

    public void setHouse_count(String house_count) {
        this.house_count = house_count;
    }

    public String getMember_count() {
        return member_count;
    }

    public void setMember_count(String member_count) {
        this.member_count = member_count;
    }
}
