package com.xy.xydoctor.bean;

public class PatientHealthRecordBean {
    private int imgUrl;
    private String name;

    public PatientHealthRecordBean(int imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name = name;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
