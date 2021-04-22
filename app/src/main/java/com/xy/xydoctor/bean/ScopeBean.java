package com.xy.xydoctor.bean;

import java.io.Serializable;

public class ScopeBean implements Serializable {
    /**
     * bp : <140/90
     * glbefore : 3.0-6.0
     * gllater : 3.0-7.8
     * gltype : 4
     * target : {"empstomach":[4.4,7],"aftbreakfast":[4.4,10],"beflunch":[4.4,10],"aftlunch":[4.4,10],"befdinner":[4.4,10],"aftdinner":[4.4,10],"befsleep":[4.4,10],"inmorning":[4.4,10]}
     */
    private String bp;
    private String glbefore;
    private String gllater;
    private int gltype;
    private ResetTargetBean target;

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getGlbefore() {
        return glbefore;
    }

    public void setGlbefore(String glbefore) {
        this.glbefore = glbefore;
    }

    public String getGllater() {
        return gllater;
    }

    public void setGllater(String gllater) {
        this.gllater = gllater;
    }

    public int getGltype() {
        return gltype;
    }

    public void setGltype(int gltype) {
        this.gltype = gltype;
    }


    public ResetTargetBean getTarget() {
        return target;
    }

    public void setTarget(ResetTargetBean target) {
        this.target = target;
    }
}
