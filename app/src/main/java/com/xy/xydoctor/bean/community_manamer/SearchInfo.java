package com.xy.xydoctor.bean.community_manamer;

import java.io.Serializable;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/9/10 9:37
 * Description:
 */
public class SearchInfo implements Serializable {
    /**
     *
     */
    private String userid;
    /**
     *
     */
    private String nickname="";
    /**
     * 手机号
     */
    private String tel="";
    /**
     * 身份证
     */
    private String idcard="";
    /**
     *
     */
    private String sex = "0";
    /**
     *
     */
    private String age="";
    /**
     *
     */
    private String birthtime="";
    /**
     * 糖尿病类型
     */
    private String diabeteslei;
    /**
     * 高血压  0:无  1：一级  2：二级   在家庭成员model中
     * 这个仅表示为是否高血压
     */
    private String hypertension;
    /**
     * 血压等级 1：一级  2：二级
     */
    private String bloodLevel;
    /**
     * 医院id
     */
    private String hos_userid;
    private String house_id;
    private String build_id;
    /**
     * 医院名字
     */
    private String hospitalname;
    /**
     * 科室id
     */
    private String dep_userid;
    /**
     * 科室名字
     */
    private String depname;
    /**
     * 医生id
     */
    private String doc_id;
    /**
     * 医生名字
     */
    private String docname;
    /**
     * 血糖仪设备号
     */
    private String sugar_imei;
    /**
     * 血压设备号
     */
    private String blood_imei;
    /**
     * 冠心病 1是 2否
     */
    private String coronary;
    /**
     * 脂肪肝 1是 2否
     */
    private String fattyliver;

    /**
     * 肥胖 1是 2否
     */
    private String fat;
    /**
     * 脑卒中 1是 2否
     */
    private String stroke;
    /**
     * 党员 1是 2否
     */
    private String party_member;

    /**
     * 残疾 1是 2否
     */
    private String disability;
    /**
     * 低保
     */
    private String dibao;
    /**
     * 重慢病
     */
    private String scd;
    /**
     * 特殊家庭
     */
    private String special_family;
    /**
     * 疫情防控
     */
    private String epidemic;
    /**
     * 精神病 1是 2否
     */
    private String mental_illness;

    /**
     * 怀孕
     */
    private String pregnant;
    /**
     * 结核
     */
    private String tuberculosis;

    /**
     * 关注 1是 2否
     */
    private String iscare;

    private DepartmentInfo hospital;
    private DepartmentInfo hosInfo;
    /**
     * 楼栋信息
     */
    private String houseinfo;
    /**
     * 随访状态，
     * 0：不显示待随访
     * 1：显示待随访
     */
    private String follow_status;
    /**
     * 血糖数据是否为空
     * 1：为空
     * 2：不为空
     */
    private String sugarEmpty;
    /**
     * 血压数据是否为空
     * 1：为空
     * 2：不为空
     */
    private String bloodEmpty;
    /**
     * 血糖值
     */
    private String glucosevalue;
    /**
     * 时间
     */
    private String datetime;
    /**
     * 测量时间点 早餐后
     */
    private String category;
    /**
     * 1偏高 2偏低 3正常
     */
    private String ishight;

    /**
     * 收缩
     */
    private String systolic;
    /**
     * 舒张
     */
    private String diastole;
    /**
     * 户主关系 1 户主 2配偶 3子女 4儿媳 5女婿 6父母 0其他
     */
    private String relation;

    /**
     * 小区的名字
     */
    private String com_name;
    /**
     * 单元的名字
     */
    private String unit_name;
    /**
     * 楼号的名字
     */
    private String build_name;
    /**
     * 房间的名字
     */
    private String housenum;
    /**
     * 房间号
     */
    private String house_num;
    /**
     * 是否死亡
     */
    private String isdeath;
    /**
     * 位置信息
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsdeath() {
        return isdeath;
    }

    public void setIsdeath(String isdeath) {
        this.isdeath = isdeath;
    }

    public String getEpidemic() {
        return epidemic;
    }

    public void setEpidemic(String epidemic) {
        this.epidemic = epidemic;
    }

    public String getSpecial_family() {
        return special_family;
    }

    public void setSpecial_family(String special_family) {
        this.special_family = special_family;
    }

    public String getScd() {
        return scd;
    }

    public void setScd(String scd) {
        this.scd = scd;
    }

    public String getDibao() {
        return dibao;
    }

    public void setDibao(String dibao) {
        this.dibao = dibao;
    }

    public String getTuberculosis() {
        return tuberculosis;
    }

    public void setTuberculosis(String tuberculosis) {
        this.tuberculosis = tuberculosis;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHouse_num() {
        return house_num;
    }

    public void setHouse_num(String house_num) {
        this.house_num = house_num;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getHousenum() {
        return housenum;
    }

    public void setHousenum(String housenum) {
        this.housenum = housenum;
    }

    private List<DepartmentInfo> doc_list;

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public List<DepartmentInfo> getDoc_list() {
        return doc_list;
    }

    public void setDoc_list(List<DepartmentInfo> doc_list) {
        this.doc_list = doc_list;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastole() {
        return diastole;
    }

    public void setDiastole(String diastole) {
        this.diastole = diastole;
    }

    public String getGlucosevalue() {
        return glucosevalue;
    }

    public void setGlucosevalue(String glucosevalue) {
        this.glucosevalue = glucosevalue;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIshight() {
        return ishight;
    }

    public void setIshight(String ishight) {
        this.ishight = ishight;
    }

    private SugarOrPressureInfo sugar;
    private SugarOrPressureInfo blood;

    private List<String> img;

    private String sugar_follow;
    private String blood_follow;


    public String getSugar_follow() {
        return sugar_follow;
    }

    public void setSugar_follow(String sugar_follow) {
        this.sugar_follow = sugar_follow;
    }

    public String getBlood_follow() {
        return blood_follow;
    }

    public void setBlood_follow(String blood_follow) {
        this.blood_follow = blood_follow;
    }

    public String getBloodLevel() {
        return bloodLevel;
    }

    public void setBloodLevel(String bloodLevel) {
        this.bloodLevel = bloodLevel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHouseinfo() {
        return houseinfo;
    }

    public void setHouseinfo(String houseinfo) {
        this.houseinfo = houseinfo;
    }

    public String getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(String follow_status) {
        this.follow_status = follow_status;
    }

    public String getSugarEmpty() {
        return sugarEmpty;
    }

    public void setSugarEmpty(String sugarEmpty) {
        this.sugarEmpty = sugarEmpty;
    }

    public String getBloodEmpty() {
        return bloodEmpty;
    }

    public void setBloodEmpty(String bloodEmpty) {
        this.bloodEmpty = bloodEmpty;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public SugarOrPressureInfo getSugar() {
        return sugar;
    }

    public void setSugar(SugarOrPressureInfo sugar) {
        this.sugar = sugar;
    }

    public SugarOrPressureInfo getBlood() {
        return blood;
    }

    public void setBlood(SugarOrPressureInfo blood) {
        this.blood = blood;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getDiabeteslei() {
        return diabeteslei;
    }

    public void setDiabeteslei(String diabeteslei) {
        this.diabeteslei = diabeteslei;
    }

    public String getHypertension() {
        return hypertension;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getHos_userid() {
        return hos_userid;
    }

    public void setHos_userid(String hos_userid) {
        this.hos_userid = hos_userid;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
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

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getSugar_imei() {
        return sugar_imei;
    }

    public void setSugar_imei(String sugar_imei) {
        this.sugar_imei = sugar_imei;
    }

    public String getBlood_imei() {
        return blood_imei;
    }

    public void setBlood_imei(String blood_imei) {
        this.blood_imei = blood_imei;
    }

    public String getCoronary() {
        return coronary;
    }

    public void setCoronary(String coronary) {
        this.coronary = coronary;
    }

    public String getFattyliver() {
        return fattyliver;
    }

    public void setFattyliver(String fattyliver) {
        this.fattyliver = fattyliver;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getParty_member() {
        return party_member;
    }

    public void setParty_member(String party_member) {
        this.party_member = party_member;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getMental_illness() {
        return mental_illness;
    }

    public void setMental_illness(String mental_illness) {
        this.mental_illness = mental_illness;
    }

    public String getIscare() {
        return iscare;
    }

    public void setIscare(String iscare) {
        this.iscare = iscare;
    }

    public DepartmentInfo getHospital() {
        return hospital;
    }

    public void setHospital(DepartmentInfo hospital) {
        this.hospital = hospital;
    }

    public DepartmentInfo getHosInfo() {
        return hosInfo;
    }

    public void setHosInfo(DepartmentInfo hosInfo) {
        this.hosInfo = hosInfo;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
