package com.xy.xydoctor.bean;

public class PatientApplyBean {
    /**
     * 类型ID 0：不启用 1：血糖 2：血压
     */
    private int id=0;
    /**
     * 计划名称
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
