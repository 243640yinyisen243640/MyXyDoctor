package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/8 13:52
 * Description:
 */
public class SugarOrPressureChildInfo {

    /**
     * id
     */
    private String id;
    /**
     * 次数
     */
    private int times;
    /**
     * 结束时间
     */
    private String endtime;
    /**
     * 随访状态 0待开启（不可点击） 1待随访 2随访完成 3已过期 4关闭随访
     */
    private String status;
    /**
     * 随访网址
     */
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
