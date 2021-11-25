package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/11/25 15:20
 * Description: 建档数据
 */
public class UserRecordDataInfo {
    /**
     * 我的建档数据-我的小区 户数
     */
    private String myHouseCount;
    /**
     * 我的建档数据-我的小区 居民总数
     */
    private String myMemberCount;
    /**
     * 我的建档数据-其他小区 户数
     */
    private String myOtherHouseCount;
    /**
     * 我的建档数据-其他小区 居民总数
     */
    private String myOtherMemberCount;
    /**
     * 其他建档数据-我的小区 户数
     */
    private String otherHouseCount;
    /**
     * 其他建档数据-我的小区 居民总数
     */
    private String otherMemberCount;


    public String getMyHouseCount() {
        return myHouseCount;
    }

    public void setMyHouseCount(String myHouseCount) {
        this.myHouseCount = myHouseCount;
    }

    public String getMyMemberCount() {
        return myMemberCount;
    }

    public void setMyMemberCount(String myMemberCount) {
        this.myMemberCount = myMemberCount;
    }

    public String getMyOtherHouseCount() {
        return myOtherHouseCount;
    }

    public void setMyOtherHouseCount(String myOtherHouseCount) {
        this.myOtherHouseCount = myOtherHouseCount;
    }

    public String getMyOtherMemberCount() {
        return myOtherMemberCount;
    }

    public void setMyOtherMemberCount(String myOtherMemberCount) {
        this.myOtherMemberCount = myOtherMemberCount;
    }

    public String getOtherHouseCount() {
        return otherHouseCount;
    }

    public void setOtherHouseCount(String otherHouseCount) {
        this.otherHouseCount = otherHouseCount;
    }

    public String getOtherMemberCount() {
        return otherMemberCount;
    }

    public void setOtherMemberCount(String otherMemberCount) {
        this.otherMemberCount = otherMemberCount;
    }
}
