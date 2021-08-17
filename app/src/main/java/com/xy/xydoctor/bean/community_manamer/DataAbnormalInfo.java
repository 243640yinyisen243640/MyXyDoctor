package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/8/17 15:28
 * Description: 数据异常
 */
public class DataAbnormalInfo {
    public DataAbnormalInfo(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
