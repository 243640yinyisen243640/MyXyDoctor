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

    private List<InsulinPersonInfo> list;

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
