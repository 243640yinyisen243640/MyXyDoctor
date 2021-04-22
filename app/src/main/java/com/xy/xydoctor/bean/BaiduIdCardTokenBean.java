package com.xy.xydoctor.bean;

public class BaiduIdCardTokenBean {
    /**
     * refresh_token : 25.059cf607d37708d39c4688be78e1a436.315360000.1894258737.282335-18266084
     * expires_in : 2592000
     * session_key : 9mzdA8+UPOCGwcdyMvTEaD05mJci5EFOjo+/0AwCwMz0QPnZfluO+1LoKER0Q7G0UUwIViWXSSwDA0xjkkaOTyKm6QYxTQ==
     * access_token : 24.8eda639e70f3aae786062fe40510dcc9.2592000.1581490737.282335-18266084
     * scope : public vis-ocr_ocr vis-classify_dishes vis-classify_car brain_all_scope brain_ocr_idcard vis-classify_animal vis-classify_plant brain_object_detect brain_realtime_logo brain_dish_detect brain_car_detect brain_animal_classify brain_plant_classify brain_ingredient brain_advanced_general_classify brain_custom_dish brain_poi_recognize brain_vehicle_detect brain_redwine brain_currency brain_vehicle_damage wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理
     * session_secret : aac2854739c79100b6c69d3d810dde81
     */

    private String refresh_token;
    private int expires_in;
    private String session_key;
    private String access_token;
    private String scope;
    private String session_secret;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSession_secret() {
        return session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }
}
