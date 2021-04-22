package com.xy.xydoctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xy.xydoctor.adapter.HealthArchiveGroupLevelAdapter;

public class HealthArchiveGroupLevelTwoBean implements MultiItemEntity {
    private String name;//左边名称
    private String content;//右边内容

    public HealthArchiveGroupLevelTwoBean(String name, String content) {
        this.name = name;
        this.content = content;
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

    @Override
    public int getItemType() {
        return HealthArchiveGroupLevelAdapter.TYPE_LEVEL_2;
    }
}
