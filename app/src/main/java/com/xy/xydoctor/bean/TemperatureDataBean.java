package com.xy.xydoctor.bean;

import java.util.List;

/**
 * Copyright (C)
 * <p>
 * FileName: TemperatureDataBean
 * <p>
 * Author: LYD
 * <p>
 * Date: 2021/6/15 14:36
 * <p>
 * Description:
 */
public class TemperatureDataBean {
    private int totalCount;
    private int listRows;
    private List<TemperatureDataListBean>lists;

    public class TemperatureDataListBean{
        private String datetime;
        private String temperature;//可能需要int类型
        /**
         * 1:自动上传 2:手动
         */
        private String type;//可能需要int类型

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getListRows() {
        return listRows;
    }

    public void setListRows(int listRows) {
        this.listRows = listRows;
    }

    public List<TemperatureDataListBean> getLists() {
        return lists;
    }

    public void setLists(List<TemperatureDataListBean> lists) {
        this.lists = lists;
    }
}
