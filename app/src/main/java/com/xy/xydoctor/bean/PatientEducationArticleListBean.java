package com.xy.xydoctor.bean;

import java.io.Serializable;
import java.util.List;

public class PatientEducationArticleListBean {

    /**
     * data : [{"id":1,"content":"否双方都","title":"发送","image":"http://port.xiyuns.cn/public/uploads/20180813/12c4caa3e394d599fb1747359cc0b9e6.jpg"},{"id":2,"content":"2342","title":"3423","image":"http://port.xiyuns.cn/public/uploads/20180813/12c4caa3e394d599fb1747359cc0b9e6.jpg"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * content : 否双方都
         * title : 发送
         * image : http://port.xiyuns.cn/public/uploads/20180813/12c4caa3e394d599fb1747359cc0b9e6.jpg
         */

        private int id;
        private String title;
        private int type;
        private String links;
        private String subtitle;
        private String year;
        private String time;
        private String content;
        private String url;
        private String duration;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
