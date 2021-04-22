package com.xy.xydoctor.bean;

import java.io.Serializable;
import java.util.List;

public class MassHistoryBean implements Serializable {

    /**
     * content : 哈哈哈
     * uid : 106
     * time : 2019-09-28 21:41:57
     * user : [{"userid":106,"userId":"78c80737453442e49e5f32e5553895c9","picture":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg","username":"15517558259","nickname":"程立雪","sex":2,"age":23,"diabeteslei":2,"diabetesleitime":"2018-11-30","height":"0.0","weight":"0.0"}]
     * num : 1
     */

    private String content;
    private String uid;
    private String time;
    private int num;
    private List<UserBean> user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean implements Serializable {
        /**
         * userid : 106
         * userId : 78c80737453442e49e5f32e5553895c9
         * picture : http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg
         * username : 15517558259
         * nickname : 程立雪
         * sex : 2
         * age : 23
         * diabeteslei : 2
         * diabetesleitime : 2018-11-30
         * height : 0.0
         * weight : 0.0
         */

        private int userid;
        private String userId;
        private String picture;
        private String username;
        private String nickname;
        private int sex;
        private int age;
        private int diabeteslei;
        private String diabetesleitime;
        private String height;
        private String weight;

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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getDiabeteslei() {
            return diabeteslei;
        }

        public void setDiabeteslei(int diabeteslei) {
            this.diabeteslei = diabeteslei;
        }

        public String getDiabetesleitime() {
            return diabetesleitime;
        }

        public void setDiabetesleitime(String diabetesleitime) {
            this.diabetesleitime = diabetesleitime;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }
}
