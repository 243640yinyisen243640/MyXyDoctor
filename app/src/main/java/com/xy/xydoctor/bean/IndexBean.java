package com.xy.xydoctor.bean;

public class IndexBean {


    /**
     * yhtj : {"total":7,"fufei":1,"mfei":6,"gxy":2,"tnb":6}
     * xztj : {"total":0}
     * hztj : {"xtg":0,"xtd":0,"xtzc":0,"xtwcl":7,"xyg":0,"xyd":0,"xyzc":0,"xywcl":7}
     * hztjs : {"xtg":3,"xtd":2,"xtzc":1,"xtwcl":2,"xyg":2,"xyd":0,"xyzc":0,"xywcl":5}
     * hztjm : {"xtg":3,"xtd":2,"xtzc":1,"xtwcl":2,"xyg":3,"xyd":0,"xyzc":0,"xywcl":4}
     * msgnum : 35
     */

    private YhtjBean yhtj;
    private XztjBean xztj;
    private HztjBean hztj;
    private HztjsBean hztjs;
    private HztjmBean hztjm;
    private SugarBean sugars;
    private int msgnum;

    private String articleurl;
    /**
     * 快舒尔注射
     */
    private int ydshbtj;
    /**
     * 胰岛注射设备
     */
    private int ydsbtj;

    public int getYdsbtj() {
        return ydsbtj;
    }

    public void setYdsbtj(int ydsbtj) {
        this.ydsbtj = ydsbtj;
    }

    public int getYdshbtj() {
        return ydshbtj;
    }

    public void setYdshbtj(int ydshbtj) {
        this.ydshbtj = ydshbtj;
    }

    public SugarBean getSugars() {
        return sugars;
    }

    public void setSugars(SugarBean sugars) {
        this.sugars = sugars;
    }

    public String getArticleurl() {
        return articleurl;
    }

    public void setArticleurl(String articleurl) {
        this.articleurl = articleurl;
    }

    public YhtjBean getYhtj() {
        return yhtj;
    }

    public void setYhtj(YhtjBean yhtj) {
        this.yhtj = yhtj;
    }

    public XztjBean getXztj() {
        return xztj;
    }

    public void setXztj(XztjBean xztj) {
        this.xztj = xztj;
    }

    public HztjBean getHztj() {
        return hztj;
    }

    public void setHztj(HztjBean hztj) {
        this.hztj = hztj;
    }

    public HztjsBean getHztjs() {
        return hztjs;
    }

    public void setHztjs(HztjsBean hztjs) {
        this.hztjs = hztjs;
    }

    public HztjmBean getHztjm() {
        return hztjm;
    }

    public void setHztjm(HztjmBean hztjm) {
        this.hztjm = hztjm;
    }

    public int getMsgnum() {
        return msgnum;
    }

    public void setMsgnum(int msgnum) {
        this.msgnum = msgnum;
    }

    public static class YhtjBean {
        /**
         * total : 7
         * fufei : 1
         * mfei : 6
         * gxy : 2
         * tnb : 6
         */

        private int total;
        private int fufei;
        private int mfei;
        private int gxy;
        private int tnb;
        private int blsg;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getFufei() {
            return fufei;
        }

        public void setFufei(int fufei) {
            this.fufei = fufei;
        }

        public int getMfei() {
            return mfei;
        }

        public void setMfei(int mfei) {
            this.mfei = mfei;
        }

        public int getGxy() {
            return gxy;
        }

        public void setGxy(int gxy) {
            this.gxy = gxy;
        }

        public int getTnb() {
            return tnb;
        }

        public void setTnb(int tnb) {
            this.tnb = tnb;
        }

        public int getBlsg() {
            return blsg;
        }

        public void setBlsg(int blsg) {
            this.blsg = blsg;
        }
    }

    public static class XztjBean {
        /**
         * total : 0
         */

        private int total;
        private String gxy;
        private String tnb;

        public String getGxy() {
            return gxy;
        }

        public void setGxy(String gxy) {
            this.gxy = gxy;
        }

        public String getTnb() {
            return tnb;
        }

        public void setTnb(String tnb) {
            this.tnb = tnb;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class HztjBean {
        /**
         * xtg : 0
         * xtd : 0
         * xtzc : 0
         * xtwcl : 7
         * xyg : 0
         * xyd : 0
         * xyzc : 0
         * xywcl : 7
         */

        private int xtg;
        private int xtd;
        private int xtzc;
        private int xtwcl;
        private int xyg;
        private int xyd;
        private int xyzc;
        private int xywcl;

        public int getXtg() {
            return xtg;
        }

        public void setXtg(int xtg) {
            this.xtg = xtg;
        }

        public int getXtd() {
            return xtd;
        }

        public void setXtd(int xtd) {
            this.xtd = xtd;
        }

        public int getXtzc() {
            return xtzc;
        }

        public void setXtzc(int xtzc) {
            this.xtzc = xtzc;
        }

        public int getXtwcl() {
            return xtwcl;
        }

        public void setXtwcl(int xtwcl) {
            this.xtwcl = xtwcl;
        }

        public int getXyg() {
            return xyg;
        }

        public void setXyg(int xyg) {
            this.xyg = xyg;
        }

        public int getXyd() {
            return xyd;
        }

        public void setXyd(int xyd) {
            this.xyd = xyd;
        }

        public int getXyzc() {
            return xyzc;
        }

        public void setXyzc(int xyzc) {
            this.xyzc = xyzc;
        }

        public int getXywcl() {
            return xywcl;
        }

        public void setXywcl(int xywcl) {
            this.xywcl = xywcl;
        }
    }

    public static class HztjsBean {
        /**
         * xtg : 3
         * xtd : 2
         * xtzc : 1
         * xtwcl : 2
         * xyg : 2
         * xyd : 0
         * xyzc : 0
         * xywcl : 5
         */

        private int xtg;
        private int xtd;
        private int xtzc;
        private int xtwcl;
        private int xyg;
        private int xyd;
        private int xyzc;
        private int xywcl;

        public int getXtg() {
            return xtg;
        }

        public void setXtg(int xtg) {
            this.xtg = xtg;
        }

        public int getXtd() {
            return xtd;
        }

        public void setXtd(int xtd) {
            this.xtd = xtd;
        }

        public int getXtzc() {
            return xtzc;
        }

        public void setXtzc(int xtzc) {
            this.xtzc = xtzc;
        }

        public int getXtwcl() {
            return xtwcl;
        }

        public void setXtwcl(int xtwcl) {
            this.xtwcl = xtwcl;
        }

        public int getXyg() {
            return xyg;
        }

        public void setXyg(int xyg) {
            this.xyg = xyg;
        }

        public int getXyd() {
            return xyd;
        }

        public void setXyd(int xyd) {
            this.xyd = xyd;
        }

        public int getXyzc() {
            return xyzc;
        }

        public void setXyzc(int xyzc) {
            this.xyzc = xyzc;
        }

        public int getXywcl() {
            return xywcl;
        }

        public void setXywcl(int xywcl) {
            this.xywcl = xywcl;
        }
    }

    public static class HztjmBean {
        /**
         * xtg : 3
         * xtd : 2
         * xtzc : 1
         * xtwcl : 2
         * xyg : 3
         * xyd : 0
         * xyzc : 0
         * xywcl : 4
         */

        private int xtg;
        private int xtd;
        private int xtzc;
        private int xtwcl;
        private int xyg;
        private int xyd;
        private int xyzc;
        private int xywcl;

        public int getXtg() {
            return xtg;
        }

        public void setXtg(int xtg) {
            this.xtg = xtg;
        }

        public int getXtd() {
            return xtd;
        }

        public void setXtd(int xtd) {
            this.xtd = xtd;
        }

        public int getXtzc() {
            return xtzc;
        }

        public void setXtzc(int xtzc) {
            this.xtzc = xtzc;
        }

        public int getXtwcl() {
            return xtwcl;
        }

        public void setXtwcl(int xtwcl) {
            this.xtwcl = xtwcl;
        }

        public int getXyg() {
            return xyg;
        }

        public void setXyg(int xyg) {
            this.xyg = xyg;
        }

        public int getXyd() {
            return xyd;
        }

        public void setXyd(int xyd) {
            this.xyd = xyd;
        }

        public int getXyzc() {
            return xyzc;
        }

        public void setXyzc(int xyzc) {
            this.xyzc = xyzc;
        }

        public int getXywcl() {
            return xywcl;
        }

        public void setXywcl(int xywcl) {
            this.xywcl = xywcl;
        }
    }

    public static class SugarBean {
        //            "total": 17,//总
        //           "empstomach": 0,//空腹
        //           "unempstomach": 22//非空腹
        private int total;
        private int empstomach;
        private int unempstomach;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getEmpstomach() {
            return empstomach;
        }

        public void setEmpstomach(int empstomach) {
            this.empstomach = empstomach;
        }

        public int getUnempstomach() {
            return unempstomach;
        }

        public void setUnempstomach(int unempstomach) {
            this.unempstomach = unempstomach;
        }
    }
}
