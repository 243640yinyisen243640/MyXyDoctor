package com.xy.xydoctor.bean;

import java.util.List;

public class PatientEducationHistoryListBean {
    /**
     * data : [{"id":2,"addtime":"2019-05-31 17:23:47","receiveuids":"537,538","articleids":"1,2","users":[{"userid":537,"userId":"e8c115dd56e74a313759ab2c69906eea","picture":"http://www.doctor.com/Public/images/navimg/userimg.png","username":"17698302666","nickname":"我的测试","sex":1,"age":28,"diabeteslei":4},{"userid":538,"userId":"d7e2aae8f91ab7f7a9a7870f0a981483","picture":"http://www.doctor.com/Public/images/navimg/userimg.png","username":"17698302555","nickname":"我的测试2","sex":1,"age":28,"diabeteslei":3}],"count":2,"titles":["发送","3423"]},{"id":1,"addtime":"2018-08-06 17:07:07","receiveuids":"537,538","articleids":"1,2","users":[{"userid":537,"userId":"e8c115dd56e74a313759ab2c69906eea","picture":"http://www.doctor.com/Public/images/navimg/userimg.png","username":"17698302666","nickname":"我的测试","sex":1,"age":28,"diabeteslei":4},{"userid":538,"userId":"d7e2aae8f91ab7f7a9a7870f0a981483","picture":"http://www.doctor.com/Public/images/navimg/userimg.png","username":"17698302555","nickname":"我的测试2","sex":1,"age":28,"diabeteslei":3}],"count":2,"titles":["发送","3423"]}]
     * morepage : false
     */

    private boolean morepage;
    private List<DataBean> data;

    public boolean isMorepage() {
        return morepage;
    }

    public void setMorepage(boolean morepage) {
        this.morepage = morepage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * addtime : 2019-05-31 17:23:47
         * receiveuids : 537,538
         * articleids : 1,2
         * users : [{"userid":537,"userId":"e8c115dd56e74a313759ab2c69906eea","picture":"http://www.doctor.com/Public/images/navimg/userimg.png","username":"17698302666","nickname":"我的测试","sex":1,"age":28,"diabeteslei":4},{"userid":538,"userId":"d7e2aae8f91ab7f7a9a7870f0a981483","picture":"http://www.doctor.com/Public/images/navimg/userimg.png","username":"17698302555","nickname":"我的测试2","sex":1,"age":28,"diabeteslei":3}]
         * count : 2
         * titles : ["发送","3423"]
         */

        private int id;
        private String addtime;
        private String receiveuids;
        private String articleids;
        private int count;
        private List<UsersBean> users;
        private List<String> titles;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getReceiveuids() {
            return receiveuids;
        }

        public void setReceiveuids(String receiveuids) {
            this.receiveuids = receiveuids;
        }

        public String getArticleids() {
            return articleids;
        }

        public void setArticleids(String articleids) {
            this.articleids = articleids;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public List<String> getTitles() {
            return titles;
        }

        public void setTitles(List<String> titles) {
            this.titles = titles;
        }

        public static class UsersBean {
            /**
             * userid : 537
             * userId : e8c115dd56e74a313759ab2c69906eea
             * picture : http://www.doctor.com/Public/images/navimg/userimg.png
             * username : 17698302666
             * nickname : 我的测试
             * sex : 1
             * age : 28
             * diabeteslei : 4
             */

            private int userid;
            private String userId;
            private String picture;
            private String username;
            private String nickname;
            private int sex;
            private int age;
            private int diabeteslei;

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
        }
    }
}
