package com.lyd.librongim.myrongim;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/6/23 14:06
 * Description:
 */
public class GroupListAllDataBean {
    private List<GroupUserBeanPatient> groupers;

    /**
     * 数量
     */
    private String num;
    /**
     *分组的名字
     */
    private String gname;

    public List<GroupUserBeanPatient> getGroupers() {
        return groupers;
    }

    public void setGroupers(List<GroupUserBeanPatient> groupers) {
        this.groupers = groupers;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
}
