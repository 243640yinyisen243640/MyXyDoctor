package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/9 17:47
 * Description:
 */
public class UpLoadParamAddBuildingInfo {
    private String unit_name;
    private String household;



    public UpLoadParamAddBuildingInfo() {
    }

    public UpLoadParamAddBuildingInfo(String unit_name, String household) {
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
