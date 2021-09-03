package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/1 11:08
 * Description: 社区数据
 */
public class CommunityManagerInfo {
    /**
     * 小区数量
     */
    private String communityCount;
    /**
     * 楼栋数量
     */
    private String buildingCount;
    /**
     * 小区用户数
     */
    private String memberCount;

    /**
     * 随访待办数量
     */
    private String followCount;
    /**
     * 数据异常数量
     */
    private String abnormalCount;
    /**
     * 用药提醒数量
     */
    private String reminderCount;

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getAbnormalCount() {
        return abnormalCount;
    }

    public void setAbnormalCount(String abnormalCount) {
        this.abnormalCount = abnormalCount;
    }

    public String getReminderCount() {
        return reminderCount;
    }

    public void setReminderCount(String reminderCount) {
        this.reminderCount = reminderCount;
    }

    public String getCommunityCount() {
        return communityCount;
    }

    public void setCommunityCount(String communityCount) {
        this.communityCount = communityCount;
    }

    public String getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(String buildingCount) {
        this.buildingCount = buildingCount;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }
}
