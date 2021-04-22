package com.xy.xydoctor.bean;

public class FollowUpVisitAddRvBean {
    private String title;
    private String desc;
    private int img;
    private boolean isCheck;//是否选中

    public FollowUpVisitAddRvBean(String title, String desc, int img, boolean isCheck) {
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
