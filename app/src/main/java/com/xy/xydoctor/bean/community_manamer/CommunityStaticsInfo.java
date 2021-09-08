package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/8 16:33
 * Description:
 */
public class CommunityStaticsInfo {
    /**
     * 居民总数
     */
    private String communityUser;
    /**
     * 随访人数
     */
    private String followUser;
    /**
     *正在随访人数
     *
     */
    private String followingUser;
    /**
     *
     * 随访总数
     */
    private String followNum;
    /**
     *
     * 完成总数
     */
    private String followedNum;
    /**
     * 未随访总数
     */
    private String unfollowNum;
    /**
     * 失访人数
     */
    private String lostfollowNum;
    /**
     * 患者比例
     */
    private String communityUserPre;
    /**
     * 年完成率
     */
    private String finished;
    /**
     * 年失访率
     */
    private String losted;
    /**
     * 未接通
     */
    private String unthrough;
    /**
     * 拒接
     */
    private String reject;
    /**
     *
     * 拒绝随访
     */
    private String rejectFollow;
    /**
     * 数据不请
     */
    private String unknow;
    /**
     * 关闭随访
     */
    private String closed;

    private int position;

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCommunityUser() {
        return communityUser;
    }

    public void setCommunityUser(String communityUser) {
        this.communityUser = communityUser;
    }

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }

    public String getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(String followingUser) {
        this.followingUser = followingUser;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public String getFollowedNum() {
        return followedNum;
    }

    public void setFollowedNum(String followedNum) {
        this.followedNum = followedNum;
    }

    public String getUnfollowNum() {
        return unfollowNum;
    }

    public void setUnfollowNum(String unfollowNum) {
        this.unfollowNum = unfollowNum;
    }

    public String getLostfollowNum() {
        return lostfollowNum;
    }

    public void setLostfollowNum(String lostfollowNum) {
        this.lostfollowNum = lostfollowNum;
    }

    public String getCommunityUserPre() {
        return communityUserPre;
    }

    public void setCommunityUserPre(String communityUserPre) {
        this.communityUserPre = communityUserPre;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getLosted() {
        return losted;
    }

    public void setLosted(String losted) {
        this.losted = losted;
    }

    public String getUnthrough() {
        return unthrough;
    }

    public void setUnthrough(String unthrough) {
        this.unthrough = unthrough;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getRejectFollow() {
        return rejectFollow;
    }

    public void setRejectFollow(String rejectFollow) {
        this.rejectFollow = rejectFollow;
    }

    public String getUnknow() {
        return unknow;
    }

    public void setUnknow(String unknow) {
        this.unknow = unknow;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }
}
