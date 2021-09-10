package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/9 17:47
 * Description:
 */
public class UpLoadParamInfo {
    private String unit_name;
    private String household;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UpLoadParamInfo() {
    }

    public UpLoadParamInfo(String unit_name, String household) {
        this.unit_name = unit_name;
        this.household = household;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }


}
