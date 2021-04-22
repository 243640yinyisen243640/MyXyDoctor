package com.xy.xydoctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xy.xydoctor.adapter.MySugarFilesAdapter;


public class MySugarLevel1Bean implements MultiItemEntity {
    private String name;//左边名称
    private String content;//右边内容
    private String unit;

    public MySugarLevel1Bean() {
    }

    public MySugarLevel1Bean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public MySugarLevel1Bean(String name, String content, String unit) {
        this.name = name;
        this.content = content;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int getItemType() {
        return MySugarFilesAdapter.TYPE_LEVEL_1;
    }
}
