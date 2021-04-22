package com.xy.xydoctor.bean;

import com.lyd.librongim.myrongim.GroupUserBean;

import java.io.Serializable;
import java.util.List;

public class DoctorListBean implements Serializable {
    /**
     * userid : 573493
     * picture : http://doctor.xiyuns.cn/Public/images/navimg/doctorimg.png
     * docname : 张文霞
     * rong_token :
     * login_time :
     * username : jzsnfmzwx
     * telephone : null
     * num : 0
     * userids : [{"userid":573579,"username":"13523675673","picture":"http://doctor.xiyuns.cn/Public/images/navimg/userimg.png"}]
     */

    private int userid;
    private String picture;
    private String docname;
    private String rong_token;
    private String login_time;
    private String username;
    private String telephone;
    private int num;
    private List<GroupUserBean> userids;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getRong_token() {
        return rong_token;
    }

    public void setRong_token(String rong_token) {
        this.rong_token = rong_token;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public List<GroupUserBean> getUserids() {
        return userids;
    }

    public void setUserids(List<GroupUserBean> userids) {
        this.userids = userids;
    }

}
