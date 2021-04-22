package com.xy.xydoctor.bean;

public class ImTokenBean {
    /**
     * userid : 105
     * token : 4m19VAFL5LUNbN4aCb38L2YHOvdl02GLvRBGCe+oom0aWux/Wl1ry7MQhX914YRIxjFwYXkThPAfftUR9MywXg==
     * username : 盛医生
     * picture : http://app.xiyuns.cn/Public/upload/20180711/201807112208231531318103.jpg
     */

    private int userid;
    private String token;
    private String username;
    private String picture;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

