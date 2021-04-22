package com.xy.xydoctor.bean;

import java.util.List;

public class BloodPressureAbnormalBean {
    /**
     * pressure : {"systolic":100,"diastole":60,"datetime":1559703021}
     * sevenpressure : [{"systolic":120,"diastole":80,"datetime":"08-21"},{"systolic":130,"diastole":85,"datetime":"08-21"},{"systolic":130,"diastole":85,"datetime":"08-21"},{"systolic":63,"diastole":48,"datetime":"09-28"},{"systolic":123,"diastole":1234,"datetime":"03-15"},{"systolic":123,"diastole":123,"datetime":"03-15"},{"systolic":106,"diastole":2,"datetime":"03-15"}]
     */

    private PressureBean pressure;
    private List<SevenpressureBean> sevenpressure;

    public PressureBean getPressure() {
        return pressure;
    }

    public void setPressure(PressureBean pressure) {
        this.pressure = pressure;
    }

    public List<SevenpressureBean> getSevenpressure() {
        return sevenpressure;
    }

    public void setSevenpressure(List<SevenpressureBean> sevenpressure) {
        this.sevenpressure = sevenpressure;
    }

    public static class PressureBean {
        /**
         * systolic : 100
         * diastole : 60
         * datetime : 1559703021
         */

        private int systolic;
        private int diastole;
        private int datetime;

        public int getSystolic() {
            return systolic;
        }

        public void setSystolic(int systolic) {
            this.systolic = systolic;
        }

        public int getDiastole() {
            return diastole;
        }

        public void setDiastole(int diastole) {
            this.diastole = diastole;
        }

        public int getDatetime() {
            return datetime;
        }

        public void setDatetime(int datetime) {
            this.datetime = datetime;
        }
    }

    public static class SevenpressureBean {
        /**
         * systolic : 120 收缩压
         * diastole : 80 舒张压
         * datetime : 08-21
         */

        private int systolic;
        private int diastole;
        private String datetime;

        public int getSystolic() {
            return systolic;
        }

        public void setSystolic(int systolic) {
            this.systolic = systolic;
        }

        public int getDiastole() {
            return diastole;
        }

        public void setDiastole(int diastole) {
            this.diastole = diastole;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}
