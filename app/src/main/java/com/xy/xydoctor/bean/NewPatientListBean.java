package com.xy.xydoctor.bean;

import java.util.List;

public class NewPatientListBean {


    /**
     * data : [{"id":1,"userid":126,"status":"1","name":"222","pic":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg"},{"id":3,"userid":127,"status":"1","name":"23455","pic":"http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg"},{"id":7,"userid":123,"status":"0","name":"身体健康","pic":"http://ceshi.xiyuns.cn/public/uploads/20190131/92558d3ebc75a1bc4c3ab7925a07025a.png"}]
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
         * id : 1
         * userid : 126
         * status : 1
         * name : 222
         * pic : http://app.xiyuns.cn/Public/upload/20180711/201807112155511531317351.jpg
         */

        private int id;
        private int userid;
        private String status;
        private String name;
        private String pic;
        private String creattime;
        private String edittime;

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
        }

        public String getEdittime() {
            return edittime;
        }

        public void setEdittime(String edittime) {
            this.edittime = edittime;
        }
        //private String click;//是否可以点头像（1可以，0否）
        //private boolean isOperate;//是否操作


        //        public String getClick() {
        //            return click;
        //        }
        //
        //        public void setClick(String click) {
        //            this.click = click;
        //        }

        public boolean isOperate() {
            return "0".equals(status);
        }

        //        public void setOperate(boolean operate) {
        //            isOperate = operate;
        //        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
