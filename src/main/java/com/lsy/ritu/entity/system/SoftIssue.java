package com.lsy.ritu.entity.system;

import java.util.Date;

public class SoftIssue {
    private Integer Id;

    private String Title;

    private String ItemType;

    private String SubType;

    private String Priority;

    private String TestVer;

    private Date TestDate;

    private String TestOne;

    private Date FixPlan;

    private String Done;

    private Date RegressionTestingDate;

    private String RegressionTestingVer;

    private String RegressionTestingOne;

    private String State;

    private Date CloseDate;

    private String PId;
    
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title == null ? null : title.trim();
    }

    public String getItemtype() {
        return ItemType;
    }

    public void setItemtype(String itemtype) {
        this.ItemType = itemtype == null ? null : itemtype.trim();
    }

    public String getSubtype() {
        return SubType;
    }

    public void setSubtype(String subtype) {
        this.SubType = subtype == null ? null : subtype.trim();
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        this.Priority = priority == null ? null : priority.trim();
    }

    public String getTestver() {
        return TestVer;
    }

    public void setTestver(String testver) {
        this.TestVer = testver == null ? null : testver.trim();
    }

    public Date getTestdate() {
        return TestDate;
    }

    public void setTestdate(Date testdate) {
        this.TestDate = testdate;
    }

    public String getTestone() {
        return TestOne;
    }

    public void setTestone(String testone) {
        this.TestOne = testone == null ? null : testone.trim();
    }

    public Date getFixplan() {
        return FixPlan;
    }

    public void setFixplan(Date fixplan) {
        this.FixPlan = fixplan;
    }

    public String getDone() {
        return Done;
    }

    public void setDone(String done) {
        this.Done = done == null ? null : done.trim();
    }

    public Date getRegressiontestingdate() {
        return RegressionTestingDate;
    }

    public void setRegressiontestingdate(Date regressiontestingdate) {
        this.RegressionTestingDate = regressiontestingdate;
    }

    public String getRegressiontestingver() {
        return RegressionTestingVer;
    }

    public void setRegressiontestingver(String regressiontestingver) {
        this.RegressionTestingVer = regressiontestingver == null ? null : regressiontestingver.trim();
    }

    public String getRegressiontestingone() {
        return RegressionTestingOne;
    }

    public void setRegressiontestingone(String regressiontestingone) {
        this.RegressionTestingOne = regressiontestingone == null ? null : regressiontestingone.trim();
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state == null ? null : state.trim();
    }

    public Date getClosedate() {
        return CloseDate;
    }

    public void setClosedate(Date closedate) {
        this.CloseDate = closedate;
    }
    
    
    private String Content;

    private String Cause;

    private String Resolvent;

    private String Remark;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content == null ? null : content.trim();
    }

    public String getCause() {
        return Cause;
    }

    public void setCause(String cause) {
        this.Cause = cause == null ? null : cause.trim();
    }

    public String getResolvent() {
        return Resolvent;
    }

    public void setResolvent(String resolvent) {
        this.Resolvent = resolvent == null ? null : resolvent.trim();
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark == null ? null : remark.trim();
    }
    
    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId == null ? null : PId.trim();
    }
}