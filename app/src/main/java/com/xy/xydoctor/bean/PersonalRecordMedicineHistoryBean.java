package com.xy.xydoctor.bean;

import java.util.List;

public class PersonalRecordMedicineHistoryBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"id":23,"times":"2","drugname":"22","dosage":"1mg"},{"id":22,"times":"1","drugname":"111","dosage":"2mg"}]
     */

    private int code;
    private String msg;
    private List<PersonalRecordMedicineHistoryDataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PersonalRecordMedicineHistoryDataBean> getData() {
        return data;
    }

    public void setData(List<PersonalRecordMedicineHistoryDataBean> data) {
        this.data = data;
    }


}
