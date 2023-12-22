package com.xy.xydoctor.bean;

public class ToDoListBean {
    /**
     * xtxx : 20
     * xdhz : 1
     * xdhzname : 张三
     * zyshq : 2
     * zyshqname : 历任色
     */

    private int xtxx;//系统消息数量

    private int xdhz;//新的患者数量
    private String xdhzname;//最新患者名字

    private int zyshq;//住院申请数量
    private String zyshqname;//最新住院申请者名字

    private int follow;
    private String followname;
    /**
     * 数量
     */
    private String eqInsulins;
    /**
     * 标题名称
     */
    private String equser_name;

    public String getEqInsulins() {
        return eqInsulins;
    }

    public void setEqInsulins(String eqInsulins) {
        this.eqInsulins = eqInsulins;
    }

    public String getEquser_name() {
        return equser_name;
    }

    public void setEquser_name(String equser_name) {
        this.equser_name = equser_name;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String getFollowname() {
        return followname;
    }

    public void setFollowname(String followname) {
        this.followname = followname;
    }

    public int getXtxx() {
        return xtxx;
    }

    public void setXtxx(int xtxx) {
        this.xtxx = xtxx;
    }

    public int getXdhz() {
        return xdhz;
    }

    public void setXdhz(int xdhz) {
        this.xdhz = xdhz;
    }

    public String getXdhzname() {
        return xdhzname;
    }

    public void setXdhzname(String xdhzname) {
        this.xdhzname = xdhzname;
    }

    public int getZyshq() {
        return zyshq;
    }

    public void setZyshq(int zyshq) {
        this.zyshq = zyshq;
    }

    public String getZyshqname() {
        return zyshqname;
    }

    public void setZyshqname(String zyshqname) {
        this.zyshqname = zyshqname;
    }
}
