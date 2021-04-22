package com.xy.xydoctor.bean;

import java.util.List;

public class FollowUpVisitWaitDoListBean {
    /**
     * data : [{"id":5,"uid":123,"status":4,"nickname":"身体健康","picture":"http://ceshi.xiyuns.cn/public/uploads/20190131/92558d3ebc75a1bc4c3ab7925a07025a.png"},{"id":4,"uid":123,"status":4,"nickname":"身体健康","picture":"http://ceshi.xiyuns.cn/public/uploads/20190131/92558d3ebc75a1bc4c3ab7925a07025a.png"}]
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
         * id : 5
         * uid : 123
         * status : 4
         * nickname : 身体健康
         * picture : http://ceshi.xiyuns.cn/public/uploads/20190131/92558d3ebc75a1bc4c3ab7925a07025a.png
         */

        private int id;
        private int uid;
        private int status;
        private String addtime;
        private String nickname;
        private String picture;
        private String type;//1血糖 2血压

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
