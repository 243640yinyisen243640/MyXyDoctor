package com.xy.xydoctor.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xy.xydoctor.adapter.PatientGroupLevelAdapter;

/**
 * 描述: 患者分组Zero
 * 作者: LYD
 * 创建日期: 2019/2/27 11:01
 */
public class PatientGroupLevelZeroBean extends AbstractExpandableItem<PatientGroupLevelOneBean> implements MultiItemEntity {
    private String groupName;//组名
    private int groupId;//组ID

    public PatientGroupLevelZeroBean(String groupName, int groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
        return PatientGroupLevelAdapter.TYPE_LEVEL_0;
    }
}
