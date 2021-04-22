package com.xy.xydoctor.bean;

import java.io.Serializable;

public class NewSearchUserBean implements Serializable {

    /**
     * code : 30017
     * msg : 患者已绑定您为专属医生！
     * data : {"userid":643660,"userId":"e42dd289de888b4eda2d968dd8fbf8ea","username":"13213071373","nickname":"李亚东","petname":"15617862405","sex":2,"diabeteslei":2,"age":24,"picture":"http://ceshi.xiyuns.cn/public/uploads/20191224/f9dd44b4f33fe34e031b4f003947e8e7.jpg","docid":643717}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * userid : 643660
         * userId : e42dd289de888b4eda2d968dd8fbf8ea
         * username : 13213071373
         * nickname : 李亚东
         * petname : 15617862405
         * sex : 2
         * diabeteslei : 2
         * age : 24
         * picture : http://ceshi.xiyuns.cn/public/uploads/20191224/f9dd44b4f33fe34e031b4f003947e8e7.jpg
         * docid : 643717
         */
        private String nickname;
        private String username;
        private String userId;
        private int sex;
        private int height;
        private int idcard;
        private int userid;
        private String petname;
        private int diabeteslei;
        private int age;
        private String picture;
        private int docid;
        private String registercode;
        private String imei;
        private String birthtime;

        public String getRegistercode() {
            return registercode;
        }

        public void setRegistercode(String registercode) {
            this.registercode = registercode;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPetname() {
            return petname;
        }

        public void setPetname(String petname) {
            this.petname = petname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getDiabeteslei() {
            return diabeteslei;
        }

        public void setDiabeteslei(int diabeteslei) {
            this.diabeteslei = diabeteslei;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getDocid() {
            return docid;
        }

        public void setDocid(int docid) {
            this.docid = docid;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getIdcard() {
            return idcard;
        }

        public void setIdcard(int idcard) {
            this.idcard = idcard;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getBirthtime() {
            return birthtime;
        }

        public void setBirthtime(String birthtime) {
            this.birthtime = birthtime;
        }
    }
}
