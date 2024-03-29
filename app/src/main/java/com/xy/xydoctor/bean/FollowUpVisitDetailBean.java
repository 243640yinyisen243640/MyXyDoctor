package com.xy.xydoctor.bean;

import java.util.List;

public class FollowUpVisitDetailBean {

    /**
     * vid : 55
     * height :
     * weight :
     * systolic :
     * diastolic :
     * drug : null
     * smok :
     * drink :
     * psychological : 0
     * paquestion :
     * measures :
     * target :
     * symptom : []
     * bmi :
     * pulsation : 0
     * else :
     * behavior : 0
     * fastingbloodsugar :
     * hemoglobin :
     * examinetime :
     * reaction : 0
     * followstyle : 0
     * medicdetail : []
     * insulin :
     * insulinnum :
     * heartrate :
     * saltrelated : 0
     * sportnum :
     * sporttime :
     * mainfood :
     * compliance : 0
     * drugreactions : 0
     * questionstr : ["1","3"]
     * stime : 2019-07-25
     * etime : 2019-07-31
     * addtime : 2019-07-30
     * status : 3
     */

    private int vid;
    private double height;
    private double weight;
    private String systolic;
    private String diastolic;
    private Object drug;
    private String smok;
    private String drink;
    private int psychological;
    private String paquestion;
    private String measures;
    private String target;
    private String bmi;
    private int pulsation;

    //    @SerializedName("else")
    //    private String elseX;
    private String other;
    private String heartrate;
    private int behavior;
    private String fastingbloodsugar;
    private String hemoglobin;
    private String examinetime;
    private int reaction;
    private int followstyle;
    private String insulin;
    private String insulinnum;
    private int saltrelated;
    private String sportnum;
    private String sporttime;
    private String mainfood;
    private int compliance;
    private int drugreactions;
    private String stime;
    private String etime;
    private String addtime;
    private int status;
    private List<String> symptom;
    private List<List<String>> medicdetail;
    private List<String> questionstr;
    private String visittime;
    private String times;

    //新增随访方式 随访
    private String way;
    private String remind;
    private String recontent;

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getRecontent() {
        return recontent;
    }

    public void setRecontent(String recontent) {
        this.recontent = recontent;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public Object getDrug() {
        return drug;
    }

    public void setDrug(Object drug) {
        this.drug = drug;
    }

    public String getSmok() {
        return smok;
    }

    public void setSmok(String smok) {
        this.smok = smok;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public int getPsychological() {
        return psychological;
    }

    public void setPsychological(int psychological) {
        this.psychological = psychological;
    }

    public String getPaquestion() {
        return paquestion;
    }

    public void setPaquestion(String paquestion) {
        this.paquestion = paquestion;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public int getPulsation() {
        return pulsation;
    }

    public void setPulsation(int pulsation) {
        this.pulsation = pulsation;
    }


    public int getBehavior() {
        return behavior;
    }

    public void setBehavior(int behavior) {
        this.behavior = behavior;
    }

    public String getFastingbloodsugar() {
        return fastingbloodsugar;
    }

    public void setFastingbloodsugar(String fastingbloodsugar) {
        this.fastingbloodsugar = fastingbloodsugar;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getExaminetime() {
        return examinetime;
    }

    public void setExaminetime(String examinetime) {
        this.examinetime = examinetime;
    }

    public int getReaction() {
        return reaction;
    }

    public void setReaction(int reaction) {
        this.reaction = reaction;
    }

    public int getFollowstyle() {
        return followstyle;
    }

    public void setFollowstyle(int followstyle) {
        this.followstyle = followstyle;
    }

    public String getInsulin() {
        return insulin;
    }

    public void setInsulin(String insulin) {
        this.insulin = insulin;
    }

    public String getInsulinnum() {
        return insulinnum;
    }

    public void setInsulinnum(String insulinnum) {
        this.insulinnum = insulinnum;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public int getSaltrelated() {
        return saltrelated;
    }

    public void setSaltrelated(int saltrelated) {
        this.saltrelated = saltrelated;
    }

    public String getSportnum() {
        return sportnum;
    }

    public void setSportnum(String sportnum) {
        this.sportnum = sportnum;
    }

    public String getSporttime() {
        return sporttime;
    }

    public void setSporttime(String sporttime) {
        this.sporttime = sporttime;
    }

    public String getMainfood() {
        return mainfood;
    }

    public void setMainfood(String mainfood) {
        this.mainfood = mainfood;
    }

    public int getCompliance() {
        return compliance;
    }

    public void setCompliance(int compliance) {
        this.compliance = compliance;
    }

    public int getDrugreactions() {
        return drugreactions;
    }

    public void setDrugreactions(int drugreactions) {
        this.drugreactions = drugreactions;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getSymptom() {
        return symptom;
    }

    public void setSymptom(List<String> symptom) {
        this.symptom = symptom;
    }

    public List<List<String>> getMedicdetail() {
        return medicdetail;
    }

    public void setMedicdetail(List<List<String>> medicdetail) {
        this.medicdetail = medicdetail;
    }

    public List<String> getQuestionstr() {
        return questionstr;
    }

    public void setQuestionstr(List<String> questionstr) {
        this.questionstr = questionstr;
    }


}
