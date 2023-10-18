package com.xy.xydoctor.bean.community_manamer;

/**
 * Author: LYD
 * Date: 2021/9/8 18:34
 * Description:
 */
public class InjectionBaseData {
    private String value;
    private String ishight;
    private String times;
    private String isshot;
    private String isshot_num;
    private String all_times;
    private String drug_name;
    private String action_year;
    private String action_time;

    @Override
    public String toString() {
        return "InjectionBaseData{" +
                "value='" + value + '\'' +
                ", ishight='" + ishight + '\'' +
                ", times='" + times + '\'' +
                ", isshot='" + isshot + '\'' +
                ", isshot_num='" + isshot_num + '\'' +
                ", all_times='" + all_times + '\'' +
                ", drug_name='" + drug_name + '\'' +
                ", action_year='" + action_year + '\'' +
                ", action_time='" + action_time + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIshight() {
        return ishight;
    }

    public void setIshight(String ishight) {
        this.ishight = ishight;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getIsshot() {
        return isshot;
    }

    public void setIsshot(String isshot) {
        this.isshot = isshot;
    }

    public String getIsshot_num() {
        return isshot_num;
    }

    public void setIsshot_num(String isshot_num) {
        this.isshot_num = isshot_num;
    }

    public String getAll_times() {
        return all_times;
    }

    public void setAll_times(String all_times) {
        this.all_times = all_times;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getAction_year() {
        return action_year;
    }

    public void setAction_year(String action_year) {
        this.action_year = action_year;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }
}
