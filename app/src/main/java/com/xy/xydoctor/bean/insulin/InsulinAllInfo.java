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

    private List<InsulinDeviceInfo> list;

    public List<InsulinDeviceInfo> getList() {
        return list;
    }

    public void setList(List<InsulinDeviceInfo> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
