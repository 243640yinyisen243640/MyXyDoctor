package com.xy.xydoctor.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xy.xydoctor.adapter.HealthArchiveGroupLevelAdapter;

/**
 * 描述: 健康档案一级标题
 * 作者: LYD
 * 创建日期: 2019/3/5 10:17
 */
public class HealthArchiveGroupLevelZeroBean extends AbstractExpandableItem<HealthArchiveGroupLevelOneBean> implements MultiItemEntity {
    private String groupName;//组名

    public HealthArchiveGroupLevelZeroBean(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return HealthArchiveGroupLevelAdapter.TYPE_LEVEL_0;
    }
}
