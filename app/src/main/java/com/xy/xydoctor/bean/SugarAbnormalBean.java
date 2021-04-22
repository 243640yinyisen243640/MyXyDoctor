package com.xy.xydoctor.bean;

import java.util.List;

public class SugarAbnormalBean {
    /**
     * glucose : {"glucosevalue":12.2,"category":"早餐后","datetime":1559872545}
     * sevenglucose : [{"zcq":10,"zcqmore":1,"zch":10,"zchmore":1,"lch":"","lchmore":0,"wcq":"","wcqmore":0,"wch":"","wchmore":0,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"time":"2019-06-05"},{"zch":11.2,"zchmore":1,"zcq":"","zcqmore":0,"wcq":13.2,"wcqmore":1,"wch":14.2,"wchmore":1,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"lch":"","lchmore":0,"time":"2019-06-06"},{"zch":10,"zchmore":1,"zcq":"","zcqmore":0,"wcq":10,"wcqmore":1,"wch":"","wchmore":0,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"lch":"","lchmore":0,"time":"2019-06-05"},{"zch":"","zchmore":0,"zcq":"","zcqmore":0,"wcq":10,"wcqmore":1,"wch":"","wchmore":0,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"lch":"","lchmore":0,"time":"2019-06-04"},{"zch":"","zchmore":0,"zcq":"","zcqmore":0,"wcq":"","wcqmore":0,"wch":"","wchmore":0,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"lch":"","lchmore":0,"time":"2019-06-03"},{"zch":"","zchmore":0,"zcq":"","zcqmore":0,"wcq":"","wcqmore":0,"wch":"","wchmore":0,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"lch":"","lchmore":0,"time":"2019-06-02"},{"zch":"","zchmore":0,"zcq":"","zcqmore":0,"wcq":"","wcqmore":0,"wch":"","wchmore":0,"wfq":"","wfqmore":0,"wfh":"","wfhmore":0,"shq":"","shqmore":0,"lch":"","lchmore":0,"time":"2019-06-01"}]
     */

    private GlucoseBean glucose;
    private List<SevenglucoseBean> sevenglucose;

    public GlucoseBean getGlucose() {
        return glucose;
    }

    public void setGlucose(GlucoseBean glucose) {
        this.glucose = glucose;
    }

    public List<SevenglucoseBean> getSevenglucose() {
        return sevenglucose;
    }

    public void setSevenglucose(List<SevenglucoseBean> sevenglucose) {
        this.sevenglucose = sevenglucose;
    }

    public static class GlucoseBean {
        /**
         * glucosevalue : 12.2
         * category : 早餐后
         * datetime : 1559872545
         */

        private double glucosevalue;
        private String category;
        private int datetime;

        public double getGlucosevalue() {
            return glucosevalue;
        }

        public void setGlucosevalue(double glucosevalue) {
            this.glucosevalue = glucosevalue;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getDatetime() {
            return datetime;
        }

        public void setDatetime(int datetime) {
            this.datetime = datetime;
        }
    }

    public static class SevenglucoseBean {
        /**
         * zcq : 10
         * zcqmore : 1
         * zch : 10
         * zchmore : 1
         * lch :
         * lchmore : 0
         * wcq :
         * wcqmore : 0
         * wch :
         * wchmore : 0
         * wfq :
         * wfqmore : 0
         * wfh :
         * wfhmore : 0
         * shq :
         * shqmore : 0
         * time : 2019-06-05
         */

        private String zcq;
        private int zcqmore;
        private String zch;
        private int zchmore;
        private String lch;
        private int lchmore;
        private String wcq;
        private int wcqmore;
        private String wch;
        private int wchmore;
        private String wfq;
        private int wfqmore;
        private String wfh;
        private int wfhmore;
        private String shq;
        private int shqmore;
        private String time;

        public String getZcq() {
            return zcq;
        }

        public void setZcq(String zcq) {
            this.zcq = zcq;
        }

        public int getZcqmore() {
            return zcqmore;
        }

        public void setZcqmore(int zcqmore) {
            this.zcqmore = zcqmore;
        }

        public String getZch() {
            return zch;
        }

        public void setZch(String zch) {
            this.zch = zch;
        }

        public int getZchmore() {
            return zchmore;
        }

        public void setZchmore(int zchmore) {
            this.zchmore = zchmore;
        }

        public String getLch() {
            return lch;
        }

        public void setLch(String lch) {
            this.lch = lch;
        }

        public int getLchmore() {
            return lchmore;
        }

        public void setLchmore(int lchmore) {
            this.lchmore = lchmore;
        }

        public String getWcq() {
            return wcq;
        }

        public void setWcq(String wcq) {
            this.wcq = wcq;
        }

        public int getWcqmore() {
            return wcqmore;
        }

        public void setWcqmore(int wcqmore) {
            this.wcqmore = wcqmore;
        }

        public String getWch() {
            return wch;
        }

        public void setWch(String wch) {
            this.wch = wch;
        }

        public int getWchmore() {
            return wchmore;
        }

        public void setWchmore(int wchmore) {
            this.wchmore = wchmore;
        }

        public String getWfq() {
            return wfq;
        }

        public void setWfq(String wfq) {
            this.wfq = wfq;
        }

        public int getWfqmore() {
            return wfqmore;
        }

        public void setWfqmore(int wfqmore) {
            this.wfqmore = wfqmore;
        }

        public String getWfh() {
            return wfh;
        }

        public void setWfh(String wfh) {
            this.wfh = wfh;
        }

        public int getWfhmore() {
            return wfhmore;
        }

        public void setWfhmore(int wfhmore) {
            this.wfhmore = wfhmore;
        }

        public String getShq() {
            return shq;
        }

        public void setShq(String shq) {
            this.shq = shq;
        }

        public int getShqmore() {
            return shqmore;
        }

        public void setShqmore(int shqmore) {
            this.shqmore = shqmore;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
