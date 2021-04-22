package com.xy.xydoctor.bean;

public class FollowListBean {
    /**
     * 模板ID
     */
    private int id;
    /**
     * 模板名称
     */
    private String planname;
    /**
     * 是否选中 0：否1 ：是
     */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }
}
