package com.lsy.ritu.entity.system;

public class Dictionaries {
    private String ZD_ID;

    private String NAME;

    private String BIANMA;

    private Integer ORDY_BY;

    private String PARENT_ID;

    private Integer JB;

    private String P_BM;

    public String getZD_ID() {
        return ZD_ID;
    }

    public void setZD_ID(String zdId) {
        this.ZD_ID = zdId == null ? null : zdId.trim();
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String name) {
        this.NAME = name == null ? null : name.trim();
    }

    public String getBIANMA() {
        return BIANMA;
    }

    public void setBIANMA(String bianma) {
        this.BIANMA = bianma == null ? null : bianma.trim();
    }

    public Integer getORDY_BY() {
        return ORDY_BY;
    }

    public void setORDY_BY(Integer ordyBy) {
        this.ORDY_BY = ordyBy;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String parentId) {
        this.PARENT_ID = parentId == null ? null : parentId.trim();
    }

    public Integer JB() {
        return JB;
    }

    public void setJB(Integer jb) {
        this.JB = jb;
    }

    public String getP_BM() {
        return P_BM;
    }

    public void setP_BM(String pBm) {
        this.P_BM = pBm == null ? null : pBm.trim();
    }
}