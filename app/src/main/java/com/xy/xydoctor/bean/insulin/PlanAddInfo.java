package com.xy.xydoctor.bean.insulin;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class PlanAddInfo {
    private String begin;
    private String end;
    private String value;

    @Override
    public String toString() {
        return "PlanAddInfo{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public PlanAddInfo(String begin, String end) {
        this.begin = begin;
        this.end = end;
        this.value = "0.1";
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
