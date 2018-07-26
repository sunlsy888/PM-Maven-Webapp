package com.lsy.ritu.entity.system;

import java.util.Date;

public class Require {

	 private Integer requireId;

	    private String pid;

	    private String softcompany;

	    private String state;

	    private String ver;

	    private String itemtitle;

	    private String subitem;

	    private String priority;

	    private String sauthor;

	    private Date sdate;

	    private String dstate;

	    private String dauthor;

	    private Date dplandate;

	    private Date enddate;

	    private String isdefer;

	    private String isvisible;

	    private Date adddate;
	    private String des;

	    private String remark;

	    public Integer getRequireId() {
	        return requireId;
	    }

	    public void setRequireId(Integer requireId) {
	        this.requireId = requireId;
	    }

	    public String getPid() {
	        return pid;
	    }

	    public void setPid(String pid) {
	        this.pid = pid == null ? null : pid.trim();
	    }

	    public String getSoftcompany() {
	        return softcompany;
	    }

	    public void setSoftcompany(String softcompany) {
	        this.softcompany = softcompany == null ? null : softcompany.trim();
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state == null ? null : state.trim();
	    }

	    public String getVer() {
	        return ver;
	    }

	    public void setVer(String ver) {
	        this.ver = ver == null ? null : ver.trim();
	    }

	    public String getItemtitle() {
	        return itemtitle;
	    }

	    public void setItemtitle(String itemtitle) {
	        this.itemtitle = itemtitle == null ? null : itemtitle.trim();
	    }

	    public String getSubitem() {
	        return subitem;
	    }

	    public void setSubitem(String subitem) {
	        this.subitem = subitem == null ? null : subitem.trim();
	    }

	    public String getPriority() {
	        return priority;
	    }

	    public void setPriority(String priority) {
	        this.priority = priority == null ? null : priority.trim();
	    }

	    public String getSauthor() {
	        return sauthor;
	    }

	    public void setSauthor(String sauthor) {
	        this.sauthor = sauthor == null ? null : sauthor.trim();
	    }

	    public Date getSdate() {
	        return sdate;
	    }

	    public void setSdate(Date sdate) {
	        this.sdate = sdate;
	    }

	    public String getDstate() {
	        return dstate;
	    }

	    public void setDstate(String dstate) {
	        this.dstate = dstate == null ? null : dstate.trim();
	    }

	    public String getDauthor() {
	        return dauthor;
	    }

	    public void setDauthor(String dauthor) {
	        this.dauthor = dauthor == null ? null : dauthor.trim();
	    }

	    public Date getDplandate() {
	        return dplandate;
	    }

	    public void setDplandate(Date dplandate) {
	        this.dplandate = dplandate;
	    }

	    public Date getEnddate() {
	        return enddate;
	    }

	    public void setEnddate(Date enddate) {
	        this.enddate = enddate;
	    }

	    public String getIsdefer() {
	        return isdefer;
	    }

	    public void setIsdefer(String isdefer) {
	        this.isdefer = isdefer == null ? null : isdefer.trim();
	    }

	    public String getIsvisible() {
	        return isvisible;
	    }

	    public void setIsvisible(String isvisible) {
	        this.isvisible = isvisible == null ? null : isvisible.trim();
	    }

	    public Date getAdddate() {
	        return adddate;
	    }

	    public void setAdddate(Date adddate) {
	        this.adddate = adddate;
	    }
	 
	    public String getDes() {
	        return des;
	    }

	    public void setDes(String des) {
	        this.des = des == null ? null : des.trim();
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }
}
