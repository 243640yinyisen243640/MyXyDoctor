package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/9 19:58
 * Description:
 */
public class DepartmentInfo {
    private String dep_userid;
    private String depname;
    private String hosp_userid;
    private String hospitalname;
    private String doc_userid;
    private String docname;

    public String getHosp_userid() {
        return hosp_userid;
    }

    public void setHosp_userid(String hosp_userid) {
        this.hosp_userid = hosp_userid;
    }

    public String getHosp_name() {
        return hospitalname;
    }

    public void setHosp_name(String hosp_name) {
        this.hospitalname = hosp_name;
    }

    public String getDoc_userid() {
        return doc_userid;
    }

    public void setDoc_userid(String doc_userid) {
        this.doc_userid = doc_userid;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDep_userid() {
        return dep_userid;
    }

    public void setDep_userid(String dep_userid) {
        this.dep_userid = dep_userid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }
}
