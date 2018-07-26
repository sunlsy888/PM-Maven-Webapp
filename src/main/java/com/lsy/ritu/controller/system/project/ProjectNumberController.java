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
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.dictionaries.DictionariesService; 
import com.lsy.ritu.service.system.project.ProjectNumberService;
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
@RequestMapping(value="/projectnumber")
public class ProjectNumberController  extends BaseController{

	String menuUrl = "projectnumber/list.do"; //菜单地址(权限用)
	@Resource(name="projectnumberService")
	private ProjectNumberService projectnumberService;
	
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
		logBefore(logger, "新增projectnumber");   
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		logBefore(logger, "projectnumber save:"+df.format(new Date())); 
		logBefore(logger, "add"); 
		pd.put("PId", pd.getString("PId"));	
		pd.put("UserId", pd.getString("UserId"));	  
		pd.put("AddDate", df.format(new Date()));	 
		  
		 if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){projectnumberService.save(pd);} //判断新增权限
		 mv.addObject("result","ok");
		 
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除projectnumber");
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				pd = this.getPageData();
				 
				projectnumberService.delete(pd);
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
			@RequestParam(value="Id",required=false) String Id, 
			@RequestParam(value="PId",required=false) String PId,
			@RequestParam(value="UserId",required=false) String UserId
			) throws Exception{
		logBefore(logger, "修改projectnumber");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "修改projectnumber edit:"+pd.toString());
		 
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("Id", Id);		//ID 
			
			pd.put("PId", pd.getString("PId"));	
			pd.put("UserId", pd.getString("UserId"));	 	
		 
			
			if(null == tpz){tpz = "";}
		 
			projectnumberService.edit(pd);				//执行修改数据库
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
		logBefore(logger, "列表projectnumber");
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
			List<User> pmanagerList  = userService.listUserForRole(pd2);	
			page.setPd(pd);
			List<PageData>	varList = projectnumberService.list(page);	//列出require列表
			mv.setViewName("system/projectnumber/projectnumber_list");
			mv.addObject("varList", varList);
			mv.addObject("pmanagerList", pmanagerList);
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
		logBefore(logger, "去新增projectnumber页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			
			String PARENT_ID=""; 
			List<Dictionaries> PLevelList;
			String PId = pd.getString("PId");
			if(null != PId && !"".equals(PId)){
				PId = PId.trim();
				pd.put("PId", PId);
			}
			
			pd2 = new PageData();
			pd2.put("BIANMA", "LandmarkPriority");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "projectnumber add findBmCount:"+PARENT_ID);
				PLevelList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "projectnumber add:"+"null");
				PLevelList=null;
			}
	
			List<User> pmanagerList  = userService.listUserForRole(pd2);	
			
			mv.setViewName("system/projectnumber/projectnumber_add");
			mv.addObject("pd", pd);
			mv.addObject("pmanagerList", pmanagerList);
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
		logBefore(logger, "去修改projectnumber页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			logBefore(logger, "projectnumber edit:"+pd.toString());
			pd = projectnumberService.findById(pd);	//根据ID读取
			
			logBefore(logger, "projectnumber edit:"+pd.toString());
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
	
			List<User> pmanagerList  = userService.listUserForRole(pd2);	
			mv.setViewName("system/projectnumber/projectnumber_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("pmanagerList", pmanagerList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除projectnumber");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				List<PageData> pdList = new ArrayList<PageData>();
				 
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					 
					projectnumberService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出projectnumber到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("标题");	//1
			titles.add("文件名");	//2
			titles.add("路径");	//3
			titles.add("创建时间");	//4
			titles.add("属于");	//5
			titles.add("备注");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = projectnumberService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TITLE"));	//1
				vpd.put("var2", varOList.get(i).getString("NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("PATH"));	//3
				vpd.put("var4", varOList.get(i).getString("CREATETIME"));	//4
				vpd.put("var5", varOList.get(i).getString("MASTER_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("BZ"));	//6
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
