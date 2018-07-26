package com.lsy.ritu.entity.system;

import java.util.Date;

public class ProjectPlan {
    private Integer Plan_Id;

    private String PId;

    private String Title;

    private String State;

    private String Ver;

    private String Priority;

    private Date DPlanDate;

    private Date EndDate;

    private String IsVisible;
    
    private String Remark2;

    public Integer getPlanId() {
        return Plan_Id;
    }

    public void setPlanId(Integer planId) {
        this.Plan_Id = planId;
    }

    public String getPid() {
        return PId;
    }

    public void setPid(String pid) {
        this.PId = pid == null ? null : pid.trim();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title == null ? null : title.trim();
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state == null ? null : state.trim();
    }

    public String getVer() {
        return Ver;
    }

    public void setVer(String ver) {
        this.Ver = ver == null ? null : ver.trim();
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        this.Priority = priority == null ? null : priority.trim();
    }

    public Date getDplandate() {
        return DPlanDate;
    }

    public void setDplandate(Date dplandate) {
        this.DPlanDate = dplandate;
    }

    public Date getEnddate() {
        return EndDate;
    }

    public void setEnddate(Date enddate) {
        this.EndDate = enddate;
    }

    public String getIsvisible() {
        return IsVisible;
    }

    public void setIsvisible(String isvisible) {
        this.IsVisible = isvisible == null ? null : isvisible.trim();
    }
    
    
    private String Des;

    private String Remark;

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        this.Des = des == null ? null : des.trim();
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark == null ? null : remark.trim();
    }
    
    public String getRemark2() {
        return Remark2;
    }

    public void setRemark2(String remark2) {
        this.Remark2 = remark2 == null ? null : remark2.trim();
    }
}