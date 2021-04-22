package com.xy.xydoctor.bean;

public class DoctorInfoBean {
    /**
     * docname : 盛医生
     * telephone : 13233701245
     * imgurl : http://xydoc.xiyuns.cn/Public/upload/20190219/201902191648521550566132.jpg
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * depname : 糖尿病中心
     * doczhc : 专业医生
     * specialty : 糖尿病
     * contents : 专攻糖尿病
     */

    private String docname;
    private String telephone;
    private String imgurl;
    private String hospitalname;
    private String depname;
    private String doczhc;
    private String specialty;
    private String contents;
    //血糖设备码
    private String imei;
    //血压设备码
    private String snnum;
    private String guid;

    public String getSnnum() {
        return snnum;
    }

    public void setSnnum(String snnum) {
        this.snnum = snnum;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDoczhc() {
        return doczhc;
    }

    public void setDoczhc(String doczhc) {
        this.doczhc = doczhc;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "DoctorInfoBean{" + "docname='" + docname + '\'' + ", telephone='" + telephone + '\'' + ", imgurl='" + imgurl + '\'' + ", hospitalname='" + hospitalname + '\'' + ", depname='" + depname + '\'' + ", doczhc='" + doczhc + '\'' + ", specialty='" + specialty + '\'' + ", contents='" + contents + '\'' + ", imei='" + imei + '\'' + '}';
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}

