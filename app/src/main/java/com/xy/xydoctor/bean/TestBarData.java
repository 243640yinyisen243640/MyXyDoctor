package com.xy.xydoctor.bean;

import com.horen.chart.barchart.IBarData;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/13:02
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class TestBarData implements IBarData {
    private String name;
    private int valueData;

    public TestBarData(int valueData, String name) {
        this.name = name;
        this.valueData = valueData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getValue() {
        return valueData;
    }

    public void setValue(int value) {
        this.valueData = value;
    }

    /**
     * 对应名字
     */
    @Override
    public String getLabelName() {
        return name;
    }
}
