package com.lsy.ritu.entity.system;

import java.util.Date;

public class WeeklyReport {
    private Integer Id;

    private String PName;

    private String PType; 

    private String Requirement;

    private String PM;

    private Date PlanDate;

    private String ItemDetails; 

    private Date AddDate;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer vId) {
        this.Id = vId;
    }

    public String getPname() {
        return PName;
    }

    public void setVname(String pname) {
        this.PName = pname == null ? null : pname.trim();
    }

    public String getPType() {
        return PType;
    }

    public void setPType(String PType) {
        this.PType = PType == null ? null : PType.trim();
    }

 
    public String getRequirement() {
        return Requirement;
    }

    public void setRequirement(String Requirement) {
        this.Requirement = Requirement == null ? null : Requirement.trim();
    }

    public String getPM() {
        return PM;
    }

    public void setPM(String PM) {
        this.PM = PM == null ? null : PM.trim();
    }

    public Date getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(Date PlanDate) {
        this.PlanDate = PlanDate;
    }

    public String getItemDetails() {
        return ItemDetails;
    }

    public void setItemDetails(String ItemDetails) {
        this.ItemDetails = ItemDetails == null ? null : ItemDetails.trim();
    }

    public Date getAddDate() {
        return AddDate;
    }

    public void setAddDate(Date AddDate) {
        this.AddDate = AddDate ;
    }

  
}