package com.lsy.ritu.entity.system;

import java.util.Date;

public class SoftVer {
    private Integer V_Id;

    private String VName;

    private String VState;

    private Date VDate;

    private String VPath;

    private String PId;

    private Date VUpdateDate;

    private String IsDelete;

    private String IsVisble;

    private String VInfo;
    
    private String Remark;

    public Integer getvId() {
        return V_Id;
    }

    public void setvId(Integer vId) {
        this.V_Id = vId;
    }

    public String getVname() {
        return VName;
    }

    public void setVname(String vname) {
        this.VName = vname == null ? null : vname.trim();
    }

    public String getVstate() {
        return VState;
    }

    public void setVstate(String vstate) {
        this.VState = vstate == null ? null : vstate.trim();
    }

    public Date getVdate() {
        return VDate;
    }

    public void setVdate(Date vdate) {
        this.VDate = vdate;
    }

    public String getVpath() {
        return VPath;
    }

    public void setVpath(String vpath) {
        this.VPath = vpath == null ? null : vpath.trim();
    }

    public String getPid() {
        return PId;
    }

    public void setPid(String pid) {
        this.PId = pid == null ? null : pid.trim();
    }

    public Date getVupdatedate() {
        return VUpdateDate;
    }

    public void setVupdatedate(Date vupdatedate) {
        this.VUpdateDate = vupdatedate;
    }

    public String getIsdelete() {
        return IsDelete;
    }

    public void setIsdelete(String isdelete) {
        this.IsDelete = isdelete == null ? null : isdelete.trim();
    }

    public String getIsvisble() {
        return IsVisble;
    }

    public void setIsvisble(String isvisble) {
        this.IsVisble = isvisble == null ? null : isvisble.trim();
    }

    public String getVinfo() {
        return VInfo;
    }

    public void setVinfo(String vinfo) {
        this.VInfo = vinfo == null ? null : vinfo.trim();
    }
    
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark == null ? null : Remark.trim();
    }
}