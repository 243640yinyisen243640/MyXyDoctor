package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/9 11:47
 * Description:
 */
public class FollowListInfo {
    private String build_id;
    /**
     * 1号楼
     */
    private String build_name;
    /**
     * 1单元
     */
    private String unit_name;

    private String id;
    private List<UpLoadParamInfo> units;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UpLoadParamInfo> getUnits() {
        return units;
    }

    public void setUnits(List<UpLoadParamInfo> units) {
        this.units = units;
    }

    List<UpLoadParamInfo> build_data;
    private String unit_num;
    private String layer;

    public List<UpLoadParamInfo> getBuild_data() {
        return build_data;
    }

    public void setBuild_data(List<UpLoadParamInfo> build_data) {
        this.build_data = build_data;
    }

    public String getUnit_num() {
        return unit_num;
    }

    public void setUnit_num(String unit_num) {
        this.unit_num = unit_num;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    private List<FollowListChildListInfo> communityUser;

    public List<FollowListChildListInfo> getCommunityUser() {
        return communityUser;
    }

    public void setCommunityUser(List<FollowListChildListInfo> communityUser) {
        this.communityUser = communityUser;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
