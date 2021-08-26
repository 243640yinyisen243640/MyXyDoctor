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

    private int oid;
    private String oxygen;
    private String bpmval;
    private int type;
    private String datetime;

    private List<FollowUpAgentListBean> plan_list;

    public List<FollowUpAgentListBean> getPlan_list() {
        return plan_list;
    }

    public void setPlan_list(List<FollowUpAgentListBean> plan_list) {
        this.plan_list = plan_list;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getOxygen() {
        return oxygen;
    }

    public void setOxygen(String oxygen) {
        this.oxygen = oxygen;
    }

    public String getBpmval() {
        return bpmval;
    }

    public void setBpmval(String bpmval) {
        this.bpmval = bpmval;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
