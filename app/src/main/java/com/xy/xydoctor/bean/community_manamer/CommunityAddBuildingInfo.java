package com.xy.xydoctor.bean.community_manamer;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/10 15:51
 * Description:
 */
public class CommunityAddBuildingInfo {

    private List<UpLoadParamInfo> unit_data;
    private String com_id;
    private String build_name;
    private String access_token;
    private String layer;

    public List<UpLoadParamInfo> getUnit_data() {
        return unit_data;
    }

    public void setUnit_data(List<UpLoadParamInfo> unit_data) {
        this.unit_data = unit_data;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }
}
