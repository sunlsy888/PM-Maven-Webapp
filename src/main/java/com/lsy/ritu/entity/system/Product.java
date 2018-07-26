package com.lsy.ritu.entity.system;

import java.util.Date;

public class Product {

	 private Integer Id;
 
	    private String pname;

	    private String pver; 
	    private Date releasetime; 
	    private String remark;

	    public Integer getId() {
	        return Id;
	    }

	    public void setId(Integer Id) {
	        this.Id = Id;
	    }

	    public String getPName() {
	        return pname;
	    }

	    public void setPName(String name) {
	        this.pname = name == null ? null : name.trim();
	    }
 
	    public String getPVer() {
	        return pver;
	    }

	    public void setPVer(String ver) {
	        this.pver = ver == null ? null : ver.trim();
	    }

	    
	   
	    public Date getReleaseTime() {
	        return releasetime;
	    }

	    public void setReleaseTime(Date sdate) {
	        this.releasetime = sdate;
	    }

	  
	  

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }
}
