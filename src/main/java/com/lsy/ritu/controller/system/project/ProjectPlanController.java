package com.lsy.ritu.controller.system.project;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lsy.ritu.controller.base.BaseController;
import com.lsy.ritu.entity.Page; 
import com.lsy.ritu.entity.system.Dictionaries;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.dictionaries.DictionariesService; 
import com.lsy.ritu.service.system.project.ProjectPlanService;
import com.lsy.ritu.service.system.user.UserService;
import com.lsy.ritu.util.AppUtil;
import com.lsy.ritu.util.Const;
import com.lsy.ritu.util.DateUtil;
import com.lsy.ritu.util.DelAllFile;
import com.lsy.ritu.util.FileUpload;
import com.lsy.ritu.util.Jurisdiction;
import com.lsy.ritu.util.ObjectExcelView;
import com.lsy.ritu.util.PageData;
import com.lsy.ritu.util.PathUtil;
import com.lsy.ritu.util.Tools;
import com.lsy.ritu.util.Watermark;

@Controller
@RequestMapping(value="/projectplan")
public class ProjectPlanController  extends BaseController{

	String menuUrl = "projectplan/list.do"; //菜单地址(权限用)
	@Resource(name="projectplanService")
	private ProjectPlanService projectplanService;
	
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name="userService")
	private UserService userService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(PrintWriter out) throws Exception{
		logBefore(logger, "新增projectplan");   
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		logBefore(logger, "projectplan save:"+df.format(new Date())); 
		logBefore(logger, "add"); 
		pd.put("PId", pd.getString("PId"));	
		pd.put("Title", pd.getString("Title"));	
		pd.put("Priority", pd.getString("Priority"));	  
		pd.put("State", "open");	  
		pd.put("Ver", pd.getString("Ver"));	 //getZDnameForId(pd.getString("TestVer"))
		pd.put("DPlanDate", pd.getString("DPlanDate"));	  
		pd.put("AddDate", df.format(new Date()));	
		pd.put("IsVisible", "1");	
		//pd.put("IsDelete", "0");	
		//pd.put("IsVerify", "0");	 
		pd.put("Des", pd.getString("CONTENT"));				//版本 
		pd.put("Remark", pd.getString("Remark"));
		 
		 if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){projectplanService.save(pd);} //判断新增权限
		 mv.addObject("result","ok");
		 
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除projectplan");
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				pd = this.getPageData();
				 
				projectplanService.delete(pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest request, 
			@RequestParam(value="tpz",required=false) String tpz,
			@RequestParam(value="ID",required=false) String ID, 
			@RequestParam(value="PId",required=false) String PId,
			@RequestParam(value="Title",required=false) String Title, 
			@RequestParam(value="Priority",required=false) String Priority,
			@RequestParam(value="Ver",required=false) String Ver,
			@RequestParam(value="DPlanDate",required=false) String DPlanDate, 
			@RequestParam(value="CONTENT",required=false) String Des
			) throws Exception{
		logBefore(logger, "修改projectplan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "修改projectplan edit:"+pd.toString());
		 
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("ID", ID);		//ID 
			
			pd.put("PId", pd.getString("PId"));	
			pd.put("Title", pd.getString("Title"));	
			pd.put("Priority", pd.getString("Priority"));	   
			pd.put("Ver", pd.getString("Ver"));	 //getZDnameForId(pd.getString("TestVer"))
			pd.put("DPlanDate", pd.getString("DPlanDate"));	   
			//pd.put("IsDelete", "0");	
			//pd.put("IsVerify", "0");	 
			pd.put("Des", pd.getString("CONTENT"));			
		 
			
			if(null == tpz){tpz = "";}
		 
			projectplanService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	PageData pd2;
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表projectplan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String KEYW = pd.getString("keyword");
			String PId = pd.getString("PId");
			if(null != PId && !"".equals(PId)){
				PId = PId.trim();
				pd.put("PId", PId);
			}
			logBefore(logger, "require PId:"+PId);
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			String DPlanDate = pd.getString("DPlanDate");
			String EndDate = pd.getString("EndDate");
			
			if(DPlanDate != null && !"".equals(DPlanDate)){
				DPlanDate = DPlanDate+" 00:00:00";
				pd.put("DPlanDate", DPlanDate);
			}
			if(EndDate != null && !"".equals(EndDate)){
				EndDate = EndDate+" 00:00:00";
				pd.put("EndDate", EndDate);
			} 
			List<Dictionaries> PLevelList;
			String PARENT_ID="";
			pd2 = new PageData();
			pd2.put("BIANMA", "LandmarkPriority");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PLevelList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PLevelList=null;
			}
			
			page.setPd(pd);
			List<PageData>	varList = projectplanService.list(page);	//列出require列表
			mv.setViewName("system/projectplan/projectplan_list");
			mv.addObject("varList", varList);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增projectplan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			
			String PARENT_ID=""; 
			List<Dictionaries> PLevelList;
			
			
			pd2 = new PageData();
			pd2.put("BIANMA", "LandmarkPriority");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "projectplan add findBmCount:"+PARENT_ID);
				PLevelList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "projectplan add:"+"null");
				PLevelList=null;
			}
	

			
			mv.setViewName("system/projectplan/projectplan_add");
			mv.addObject("pd", pd);

			mv.addObject("PLevelList", PLevelList);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改projectplan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			logBefore(logger, "projectplan edit:"+pd.toString());
			pd = projectplanService.findById(pd);	//根据ID读取
			
			logBefore(logger, "projectplan edit:"+pd.toString());
			String PARENT_ID=""; 
			List<Dictionaries> PLevelList;
		
		
			pd2 = new PageData();
			pd2.put("BIANMA", "LandmarkPriority");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PLevelList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PLevelList=null;
			}
	
			
			mv.setViewName("system/projectplan/projectplan_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("PLevelList", PLevelList);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	
	/**
	 * 去更新页面
	 */
	@RequestMapping(value="/goUpdateState")
	public ModelAndView goUpdateState(){
		logBefore(logger, "去修改goUpdateState页面");
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, "project goUpdateState ModelAndView:"+mv.toString());
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project goUpdateState:"+pd.toString());
		try {
			String PARENT_ID=""; 
			List<Dictionaries> PSTATEList;
		 
			pd2 = new PageData();
			pd2.put("BIANMA", "PSTATE");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PSTATEList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PSTATEList=null;
			} 
	        String strId=pd.getString("Plan_Id");
	       
	        logBefore(logger, "updatestate strId:"+strId.toString());
			 if(!strId.contains(","))
			 {
			pd = projectplanService.findById(pd);	//根据ID读取
			 }else
			 {
				 pd.put("Plan_Id", strId); 
			 }
			 
			 
			 
					logBefore(logger, "project strRoleName:PM 研发");
					mv.setViewName("system/projectplan/projectplan_update");
					mv.addObject("msg", "edit");
				 
			logBefore(logger, "project EDIT:"+pd.toString());
			//mv.setViewName("system/problem/softissue_updatestate");
			//mv.addObject("msg", "edit");
			mv.addObject("PSTATEList", PSTATEList);
		
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/*
	 * 
	 * */
	@RequestMapping(value="/updatepm")
	@ResponseBody
	public Object updatepm(PrintWriter out) throws Exception {
		logBefore(logger, "批量删除project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			pd = this.getPageData();
			logBefore(logger, "updateState  pd:"+pd.toString());
			if(Jurisdiction.buttonJurisdiction(menuUrl, "confirm")){
				 
				String DATA_IDS = pd.getString("Plan_Id");
				String state = pd.getString("State");
				logBefore(logger, "updateState:"+DATA_IDS.toString()+"====State:"+state);
				pd.put("State", state);
				pd.put("EndDate", df.format(new Date()));	
				
			 
				pd.put("Remark2", pd.getString("CONTENT"));	
				
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					for(int i=0;i<ArrayDATA_IDS.length;i++)
					{
						if(ArrayDATA_IDS[i]!="")
						{
							pd.put("Plan_Id", ArrayDATA_IDS[i]);
							projectplanService.updatepm(pd);
						}
						
					} 
					mv.addObject("msg","success");
				}else{
					mv.addObject("msg","no");
				}
				 
			}
		 
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
		 
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除projectplan");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				List<PageData> pdList = new ArrayList<PageData>();
				 
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					 
					projectplanService.deleteAll(ArrayDATA_IDS);
					pd.put("msg", "ok");
				}else{
					pd.put("msg", "no");
				}
				pdList.add(pd);
				map.put("list", pdList);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出projectplan到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("编号");	//1
			titles.add("项目编号");	//1
			titles.add("标题");	//1
			titles.add("状态");	//2
			titles.add("版本");	//3
			titles.add("重要度");	//4
			titles.add("计划完成时间");	//5
			titles.add("实际完成时间");	//6
			titles.add("是否显示");	//6
			titles.add("详细描述");	//6
			titles.add("备注");	//6
			titles.add("添加时间");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = projectplanService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				/*
				 *   Plan_Id, PId, Title, State, Ver, Priority, DPlanDate, EndDate, IsVisible,
    Des, Remark,AddDate
				 * */
				vpd.put("var1",String.valueOf(varOList.get(i).getInteger("Plan_Id")));	//1
				vpd.put("var2", varOList.get(i).getString("PId"));	//2
				vpd.put("var3", varOList.get(i).getString("Title"));	//3
				vpd.put("var4", varOList.get(i).getString("State"));	//4
				vpd.put("var5", varOList.get(i).getString("Priority"));	//5
				vpd.put("var6", String.valueOf(varOList.get(i).getDateTime("DPlanDate")));	//6
				vpd.put("var6", String.valueOf(varOList.get(i).getDateTime("EndDate")));	//6
				vpd.put("var6", varOList.get(i).getString("IsVisible"));	//6
				vpd.put("var6", varOList.get(i).getString("Des"));	//6
				vpd.put("var6", varOList.get(i).getString("Remark"));	//6
				vpd.put("var6", String.valueOf(varOList.get(i).getDateTime("AddDate")));	//6 
			 
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
