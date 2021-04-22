package com.xy.xydoctor.bean;

public class FamilySelectUserBean extends SelectedBean {

    /**
     * id : 20
     * tel : 17698302799
     * idcard : 410521199101155033
     * nickname : 李林峰
     * relation : 1
     */

    private int id;
    private String tel;
    private String idcard;
    private String nickname;
    private int relation;
    private String relationname;

    public String getRelationname() {
        return relationname;
    }

    public void setRelationname(String relationname) {
        this.relationname = relationname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
