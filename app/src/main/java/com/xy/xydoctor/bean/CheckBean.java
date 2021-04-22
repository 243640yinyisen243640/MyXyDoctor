package com.xy.xydoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckBean implements Serializable {


    /**
     * id : 19
     * uid : 369
     * hydname :
     * picurl : ["http://ceshi.xiyuns.cn/public/uploads/20190225/e768d4dbd312cc24856eac873cfa09be.jpg","http://ceshi.xiyuns.cn/public/uploads/20190225/5c676c3c30a3ac7ca4860b20b178a277.jpg","http://ceshi.xiyuns.cn/public/uploads/20190225/237257c2e05beb512e72435afb23367d.jpg"]
     * datetime : 2019-02-25 17:03
     * remark : [null]
     */

    private int id;
    private int uid;
    private String hydname;
    private String datetime;
    private ArrayList<String> picurl;
    //private List<Object> remark;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHydname() {
        return hydname;
    }

    public void setHydname(String hydname) {
        this.hydname = hydname;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    //    public <String> getPicurl() {
    //        return picurl;
    //    }
    //
    //    public void setPicurl(ArrayList<String> picurl) {
    //        this.picurl = picurl;
    //    }

    public ArrayList<String> getPicurl() {
        return picurl;
    }

    public void setPicurl(ArrayList<String> picurl) {
        this.picurl = picurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
