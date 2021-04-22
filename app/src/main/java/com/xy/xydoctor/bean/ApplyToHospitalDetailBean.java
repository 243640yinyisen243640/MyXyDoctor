package com.xy.xydoctor.bean;

import java.util.ArrayList;

public class ApplyToHospitalDetailBean {

    /**
     * id : 1
     * name : 李四
     * type : 2
     * age : 27
     * describe : 头疼
     * sex : 1
     * telephone : 13845769523
     * result : 继续治疗
     * blpic : ["http://ceshi.xiyuns.cn/public/uploads/20190225/3b4ea20194921810635046ac4f9761b9.jpg","http://ceshi.xiyuns.cn/public/uploads/20190225/9fd50c323c906ce48494a3cfdda8dbdc.jpg","http://ceshi.xiyuns.cn/public/uploads/20190225/cbf22af84c5df426cd4f307d1e8cb0d1.jpg"]
     * intime : 2018-07-05
     */

    private int id;
    private String name;
    private String type;
    private String age;
    private String describe;
    private String sex;
    private String telephone;
    private String result;
    private String intime;
    private ArrayList<String> blpic;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public ArrayList<String> getBlpic() {
        return blpic;
    }

    public void setBlpic(ArrayList<String> blpic) {
        this.blpic = blpic;
    }
}
