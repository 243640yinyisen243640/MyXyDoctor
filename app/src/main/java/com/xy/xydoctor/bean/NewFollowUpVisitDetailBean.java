package com.xy.xydoctor.bean;

import androidx.annotation.NonNull;

import java.util.List;

public class NewFollowUpVisitDetailBean {

    /**
     * vid : 178
     * height : 180
     * weight : 70
     * systolic : 120
     * diastolic : 150
     * drug : null
     * smok : 2
     * drink : 5
     * psychological : 2
     * paquestion :
     * measures :
     * target :
     * symptom : ["2","5","8","3","6"]
     * bmi : 21.6
     * pulsation : 2
     * other :
     * behavior : 1
     * fastingbloodsugar : 4.6
     * hemoglobin : 0.5
     * examinetime : 2019-11-13
     * reaction : 3
     * followstyle : 0
     * medicdetail : [["hghg","12","455"],["","",""],["","",""]]
     * insulin :
     * insulinnum :
     * heartrate : 0
     * saltrelated : 0
     * sportnum : 0
     * sporttime : 0
     * mainfood : 58
     * compliance : 2
     * drugreactions : 2
     * liverfun1 : http://port.xiyuns.cn/public/uploads/20191113/444cf491cbc70fb6a0696facd3b0d031.jpg
     * liverfun2 : http://port.xiyuns.cn/public/uploads/20191113/52a7c99009368f4a93cb02dc15496cf7.jpg
     * liverfun3 :
     * routine : ["chhc","","hchv","","","chhv","","","","",""]
     * bloodfat : ["hchf","","dghf",""]
     * urinemicro : fyug
     * creatinine :
     * stimulating : chhv
     * heartpic1 : http://port.xiyuns.cn/public/uploads/20191113/6cdd4ff810d2e9de974b42ee0254c6c7.jpg
     * heartpic2 :
     * heartpic3 :
     * heartcontent : hcjvhj
     * eyespic1 : http://port.xiyuns.cn/public/uploads/20191113/6cdd4ff810d2e9de974b42ee0254c6c7.jpg
     * eyespic2 :
     * eyespic3 :
     * eyescontent : hvhvvkjvk
     * neuropathypic1 : http://port.xiyuns.cn/public/uploads/20191113/52a7c99009368f4a93cb02dc15496cf7.jpg
     * neuropathypic2 :
     * neuropathypic3 :
     * neuropathycontent : fhvjjb
     * sugars : [["hc","","","","","","",""],["","hc","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""]]
     * questionstr : ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"]
     * visittime : 2019-11-13
     * status : 4
     * times : 6666
     * way : 1
     * remind : 2
     * recontent : 门诊测试消息提醒内容
     */

    private int vid;
    private double height;
    private double weight;
    private String systolic="";
    private String diastolic="";
    private Object drug;
    private String smok;
    private String drink;
    private int psychological;
    private String paquestion;
    private String measures;
    private String target;
    private String bmi;
    private int pulsation;
    private String other;
    private int behavior;
    private String fastingbloodsugar;
    private String hemoglobin;
    private String examinetime;
    private int reaction;
    private int followstyle;
    private String insulin;
    private String insulinnum;
    private int heartrate;
    private int saltrelated;
    private String sportnum;
    private String sporttime;
    private String mainfood;
    private int compliance;
    private int drugreactions;
    private String liverfun1;
    private String liverfun2;
    private String liverfun3;
    private String livercon;
    private String urinemicro;
    private String creatinine;
    private String stimulating;
    private String heartpic1;
    private String heartpic2;
    private String heartpic3;
    private String heartcontent;
    private String eyespic1;
    private String eyespic2;
    private String eyespic3;
    private String eyescontent;
    private String neuropathypic1;
    private String neuropathypic2;
    private String neuropathypic3;
    private String neuropathycontent;
    private String visittime;
    /**
     * 1:草稿箱 无操作 不会进到随访报告的页面
     * 2:等待患者开启
     * 3:等待患者完成
     * 4:等待医生总结 以前 医生对患者提交的数据进行保存 也只是修改 患者目前的问题
     * 5:已完成 ->不可点击
     * */
    private int status;
    private int times;
    private int way;
    private int remind;
    private String recontent;
    private List<String> symptom;
    private List<List<String>> medicdetail;
    private List<String> routine;
    private List<String> bloodfat;
    private List<List<String>> sugars;
    private List<String> questionstr;

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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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

    public int getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(int heartrate) {
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

    public String getLiverfun1() {
        return liverfun1;
    }

    public void setLiverfun1(String liverfun1) {
        this.liverfun1 = liverfun1;
    }

    public String getLiverfun2() {
        return liverfun2;
    }

    public void setLiverfun2(String liverfun2) {
        this.liverfun2 = liverfun2;
    }

    public String getLiverfun3() {
        return liverfun3;
    }

    public void setLiverfun3(String liverfun3) {
        this.liverfun3 = liverfun3;
    }

    public String getUrinemicro() {
        return urinemicro;
    }

    public void setUrinemicro(String urinemicro) {
        this.urinemicro = urinemicro;
    }

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine;
    }

    public String getStimulating() {
        return stimulating;
    }

    public void setStimulating(String stimulating) {
        this.stimulating = stimulating;
    }

    public String getHeartpic1() {
        return heartpic1;
    }

    public void setHeartpic1(String heartpic1) {
        this.heartpic1 = heartpic1;
    }

    public String getHeartpic2() {
        return heartpic2;
    }

    public void setHeartpic2(String heartpic2) {
        this.heartpic2 = heartpic2;
    }

    public String getHeartpic3() {
        return heartpic3;
    }

    public void setHeartpic3(String heartpic3) {
        this.heartpic3 = heartpic3;
    }

    public String getHeartcontent() {
        return heartcontent;
    }

    public void setHeartcontent(String heartcontent) {
        this.heartcontent = heartcontent;
    }

    public String getEyespic1() {
        return eyespic1;
    }

    public void setEyespic1(String eyespic1) {
        this.eyespic1 = eyespic1;
    }

    public String getEyespic2() {
        return eyespic2;
    }

    public void setEyespic2(String eyespic2) {
        this.eyespic2 = eyespic2;
    }

    public String getEyespic3() {
        return eyespic3;
    }

    public void setEyespic3(String eyespic3) {
        this.eyespic3 = eyespic3;
    }

    public String getEyescontent() {
        return eyescontent;
    }

    public void setEyescontent(String eyescontent) {
        this.eyescontent = eyescontent;
    }

    public String getNeuropathypic1() {
        return neuropathypic1;
    }

    public void setNeuropathypic1(String neuropathypic1) {
        this.neuropathypic1 = neuropathypic1;
    }

    public String getNeuropathypic2() {
        return neuropathypic2;
    }

    public void setNeuropathypic2(String neuropathypic2) {
        this.neuropathypic2 = neuropathypic2;
    }

    public String getNeuropathypic3() {
        return neuropathypic3;
    }

    public void setNeuropathypic3(String neuropathypic3) {
        this.neuropathypic3 = neuropathypic3;
    }

    public String getNeuropathycontent() {
        return neuropathycontent;
    }

    public void setNeuropathycontent(String neuropathycontent) {
        this.neuropathycontent = neuropathycontent;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public String getRecontent() {
        return recontent;
    }

    public void setRecontent(String recontent) {
        this.recontent = recontent;
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

    public List<String> getRoutine() {
        return routine;
    }

    public void setRoutine(List<String> routine) {
        this.routine = routine;
    }

    public List<String> getBloodfat() {
        return bloodfat;
    }

    public void setBloodfat(List<String> bloodfat) {
        this.bloodfat = bloodfat;
    }

    public List<List<String>> getSugars() {
        return sugars;
    }

    public void setSugars(List<List<String>> sugars) {
        this.sugars = sugars;
    }

    public List<String> getQuestionstr() {
        return questionstr;
    }

    public void setQuestionstr(List<String> questionstr) {
        this.questionstr = questionstr;
    }

    @NonNull
    @Override
    public String toString() {
        return "NewFollowUpVisitDetailBean{" +
                "vid=" + vid +
                ", height=" + height +
                ", weight=" + weight +
                ", systolic='" + systolic + '\'' +
                ", diastolic='" + diastolic + '\'' +
                ", drug=" + drug +
                ", smok='" + smok + '\'' +
                ", drink='" + drink + '\'' +
                ", psychological=" + psychological +
                ", paquestion='" + paquestion + '\'' +
                ", measures='" + measures + '\'' +
                ", target='" + target + '\'' +
                ", bmi='" + bmi + '\'' +
                ", pulsation=" + pulsation +
                ", other='" + other + '\'' +
                ", behavior=" + behavior +
                ", fastingbloodsugar='" + fastingbloodsugar + '\'' +
                ", hemoglobin='" + hemoglobin + '\'' +
                ", examinetime='" + examinetime + '\'' +
                ", reaction=" + reaction +
                ", followstyle=" + followstyle +
                ", insulin='" + insulin + '\'' +
                ", insulinnum='" + insulinnum + '\'' +
                ", heartrate=" + heartrate +
                ", saltrelated=" + saltrelated +
                ", sportnum='" + sportnum + '\'' +
                ", sporttime='" + sporttime + '\'' +
                ", mainfood='" + mainfood + '\'' +
                ", compliance=" + compliance +
                ", drugreactions=" + drugreactions +
                ", liverfun1='" + liverfun1 + '\'' +
                ", liverfun2='" + liverfun2 + '\'' +
                ", liverfun3='" + liverfun3 + '\'' +
                ", urinemicro='" + urinemicro + '\'' +
                ", creatinine='" + creatinine + '\'' +
                ", stimulating='" + stimulating + '\'' +
                ", heartpic1='" + heartpic1 + '\'' +
                ", heartpic2='" + heartpic2 + '\'' +
                ", heartpic3='" + heartpic3 + '\'' +
                ", heartcontent='" + heartcontent + '\'' +
                ", eyespic1='" + eyespic1 + '\'' +
                ", eyespic2='" + eyespic2 + '\'' +
                ", eyespic3='" + eyespic3 + '\'' +
                ", eyescontent='" + eyescontent + '\'' +
                ", neuropathypic1='" + neuropathypic1 + '\'' +
                ", neuropathypic2='" + neuropathypic2 + '\'' +
                ", neuropathypic3='" + neuropathypic3 + '\'' +
                ", neuropathycontent='" + neuropathycontent + '\'' +
                ", visittime='" + visittime + '\'' +
                ", status=" + status +
                ", times=" + times +
                ", way=" + way +
                ", remind=" + remind +
                ", recontent='" + recontent + '\'' +
                ", symptom=" + symptom +
                ", medicdetail=" + medicdetail +
                ", routine=" + routine +
                ", bloodfat=" + bloodfat +
                ", sugars=" + sugars +
                ", questionstr=" + questionstr +
                '}';
    }

    public String getLivercon() {
        return livercon;
    }

    public void setLivercon(String livercon) {
        this.livercon = livercon;
    }
}
