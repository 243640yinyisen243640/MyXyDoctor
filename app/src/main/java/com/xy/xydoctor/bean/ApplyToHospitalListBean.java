package com.xy.xydoctor.bean;

import java.util.List;

public class ApplyToHospitalListBean {
    /**
     * data : [{"id":1,"name":"李四","status":"1","uid":538,"pic":"http://www.doctor.com/Public/images/navimg/userimg.png"}]
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
         * name : 李四
         * status : 1
         * uid : 538
         * pic : http://www.doctor.com/Public/images/navimg/userimg.png
         */

        private int id;
        private String name;
        private String status;
        private int uid;
        private String pic;
        private String creattime;
        private String edittime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }


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
    }
}
