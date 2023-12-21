package com.xy.xydoctor.bean.insulin;

import java.util.List;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinAllInfo {
    private String total;
    private String plan_num;

    private List<InsulinPersonInfo> list;

    public String getPlan_num() {
        return plan_num;
    }

    public void setPlan_num(String plan_num) {
        this.plan_num = plan_num;
    }

    public List<InsulinPersonInfo> getList() {
        return list;
    }

    public void setList(List<InsulinPersonInfo> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
