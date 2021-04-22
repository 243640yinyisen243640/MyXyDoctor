package com.xy.xydoctor.bean;

public class SystemMessageListBean {

        /**
         * pid : 406
         * userid : 643717
         * title : 系统消息
         * type : 19
         * notice : 血糖偏低提醒
         * extras : {"extras":{"type":19,"id":56,"content":"系统监测到您的血糖低..."},"title":"系统消息"}
         * isread : 2
         * addtime : 2020-12-09 10:47
         * edittime : 1607482065
         * content : 系统监测到您的血糖低...
         */

        private int pid;
        private int userid;
        private String title;
        private int type;
        private String notice;
        private String extras;
        private int isread;
        private String addtime;
        private int edittime;
        private String content;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

        public int getIsread() {
            return isread;
        }

        public void setIsread(int isread) {
            this.isread = isread;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getEdittime() {
            return edittime;
        }

        public void setEdittime(int edittime) {
            this.edittime = edittime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
}

