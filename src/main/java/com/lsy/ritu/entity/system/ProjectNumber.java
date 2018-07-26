package com.lsy.ritu.entity.system;

import java.util.Date;

public class ProjectNumber {
    private Integer Id; 

    private String PId;

    private String UserId;

    private Date AddDate;
 

    public Integer getId() {
        return Id;
    }

    public void setvId(Integer Id) {
        this.Id = Id;
    }
 
    public Date getAddDate() {
        return AddDate;
    }

    public void setAddDate(Date vdate) {
        this.AddDate = vdate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId == null ? null : UserId.trim();
    }

    public String getPid() {
        return PId;
    }

    public void setPid(String pid) {
        this.PId = pid == null ? null : pid.trim();
    }

    
   
}