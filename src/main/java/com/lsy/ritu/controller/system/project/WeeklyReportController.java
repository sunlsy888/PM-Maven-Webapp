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
import javax.servlet.http.HttpServletResponse;

import org.apache.james.mime4j.field.datetime.DateTime;
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
import com.lsy.ritu.controller.system.dictionaries.DictionariesController;
import com.lsy.ritu.entity.Page; 
import com.lsy.ritu.entity.system.Dictionaries;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.entity.system.SoftVer;
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.dictionaries.DictionariesService;
import com.lsy.ritu.service.system.project.NaviProjectService;
import com.lsy.ritu.service.system.project.SoftIssueService;
import com.lsy.ritu.service.system.project.SoftVerService;
import com.lsy.ritu.service.system.project.WeeklyReportService;
import com.lsy.ritu.service.system.role.RoleService;
import com.lsy.ritu.service.system.user.UserService;
import com.lsy.ritu.util.AppUtil;
import com.lsy.ritu.util.Const;
import com.lsy.ritu.util.FileDownload;
import com.lsy.ritu.util.FileUpload;
import com.lsy.ritu.util.Jurisdiction;
import com.lsy.ritu.util.ObjectExcelRead;
import com.lsy.ritu.util.ObjectExcelView;
import com.lsy.ritu.util.PageData;
import com.lsy.ritu.util.PathUtil;
import com.lsy.ritu.util.Tools;



@Controller
@RequestMapping(value="/report")
public class WeeklyReportController  extends BaseController{

	String menuUrl = "report/list.do"; //菜单地址(权限用)
	@Resource(name="naviprojectService")
	private NaviProjectService naviprojectService;
	
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="weeklyreportService")
	private WeeklyReportService weeklyreportService;
	
	@Resource(name="softverService")
	private SoftVerService softverService; 
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(PrintWriter out) throws Exception{
		logBefore(logger, "新增project");   
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		logBefore(logger, "project save:"+df.format(new Date())); 
		logBefore(logger, "add"); 
		pd.put("PName", pd.getString("PName"));	
		pd.put("PType", pd.getString("PType"));	
		pd.put("Requirement", pd.getString("Requirement"));	   
		pd.put("PlanDate", pd.getString("PlanDate"));	
		pd.put("PM", pd.getString("PM"));	 //getZDnameForId(pd.getString("TestVer"))
		pd.put("ItemDetails", pd.getString("ItemDetails"));	  
		pd.put("AddDate", df.format(new Date()));	 
		 
		 
		 if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){weeklyreportService.save(pd);} //判断新增权限
		 mv.addObject("result","ok");
		 
		mv.setViewName("save_result");
		return mv;
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除project");
		PageData pd = new PageData();
		try{
			
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){weeklyreportService.delete(pd);}
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
			@RequestParam(value="PName",required=false) String PName,
			@RequestParam(value="PType",required=false) String PType,
			@RequestParam(value="Requirement",required=false) String Requirement,
			@RequestParam(value="PM",required=false) String PM, 
			@RequestParam(value="PlanDate",required=false) Date PlanDate,  
			@RequestParam(value="ItemDetails",required=false) String ItemDetails
			) throws Exception{
		logBefore(logger, "修改project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "修改project edit:"+pd.toString());
		
		logBefore(logger, "project edit ID:"+Id.toString());
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("Id", Id);		//ID 
			pd.put("PName", PName);	
			pd.put("PType", PType);	 //getZDnameForId
			pd.put("Requirement", Requirement);	
			pd.put("PM", PM);	
			pd.put("PlanDate", PlanDate);	 
			//pd.put("PId", PId);					//产品名称	
			pd.put("ItemDetails", ItemDetails);			  
			
			if(null == tpz){tpz = "";}
		 
			weeklyreportService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String KEYW = pd.getString("keyword"); 
			//pd.put("IsVerify", "1");
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
		  
			page.setPd(pd);
			List<PageData>	varList = weeklyreportService.list(page);	//列出project列表
			mv.setViewName("information/report/WeeklyReport_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 列表
	 */
	@RequestMapping(value="/mylist")
	public ModelAndView mylist(Page page){
		logBefore(logger, "列表project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String KEYW = pd.getString("keyword");
			pd.put("IsVisible", "1");
			pd.put("IsDelete", "0");
			pd.put("IsVerify", "0");
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			 
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			logBefore(logger, "project:"+pds.toString());
			if(null != pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
				String NAME=pds.getString("NAME");
				pd.put("PManager", NAME);
				logBefore(logger, "project:"+NAME);
			}
		
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
		 
			
			page.setPd(pd);
			List<PageData>	varList = weeklyreportService.list(page);	//列出product列表
			mv.setViewName("information/report/WeeklyReport_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 列表
	 */
	@RequestMapping(value="/listall")
	public ModelAndView listAll(Page page){
		logBefore(logger, "列表project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String KEYW = pd.getString("keyword"); 
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			} 
			logBefore(logger, "issue:"+pd.toString());
			List<Dictionaries> PTypeList;
			String PARENT_ID="";
			pd2 = new PageData();
			pd2.put("BIANMA", "PType");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PTypeList=null;
			}
			
			page.setPd(pd);
			List<PageData>	varList = weeklyreportService.list(page);	//列出product列表
			mv.setViewName("information/report/WeeklyReport_list");
			mv.addObject("varList", varList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	PageData pd2;
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增project页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String PARENT_ID=""; 
			List<User> pmanagerList;
			List<Dictionaries> PDataVerList,PTypeList,PLevelList;
			pd2 = new PageData();
			pd2.put("BIANMA", "PM");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				pmanagerList = userService.listUserzu(pd2);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				pmanagerList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PDataVer");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PDataVerList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PDataVerList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PType");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "ProblemLevel");//ZD_ID
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
			List<Role> roleList = roleService.listAllRoles();				//列出所有部门
			//获取版本信息
			//pd.put("PId", pd.getString("PId"));
			 
			//page.setPd(pd);
			 
            mv.setViewName("information/report/WeeklyReport_add");
			mv.addObject("pd", pd);
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList); 
			mv.addObject("roleList", roleList);
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
		logBefore(logger, "去修改project页面");
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, "project goedit ModelAndView:"+mv.toString());
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project goedit:"+pd.toString());
		try {
			String PARENT_ID=""; 
			List<User> pmanagerList;
			List<Dictionaries> PDataVerList,PTypeList,PLevelList;
			pd2 = new PageData();
			pd2.put("BIANMA", "PM");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				pmanagerList = userService.listUserzu(pd2);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				pmanagerList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PDataVer");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PDataVerList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PDataVerList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PType");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "ProblemLevel");//ZD_ID
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
			List<Role> roleList = roleService.listAllRoles();				//列出所有部门
		
			 
		 
			pd = weeklyreportService.findById(pd);	//根据ID读取
			logBefore(logger, "project EDIT:"+pd.toString());
			mv.setViewName("information/report/WeeklyReport_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList); 
			mv.addObject("pd", pd);

			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/pminfo")
	public ModelAndView goPMInfo(){
		logBefore(logger, "去project页面");
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project PMInfo:"+pd.toString());
		try { 
			pd = weeklyreportService.findById(pd);	//根据ID读取
			 
			String KEYW = pd.getString("keyword");
			String PId = pd.getString("PId");
			logBefore(logger, "project PMInfo:"+PId.toString());
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			pd.put("PId", PId); 
			 
			mv.setViewName("information/report/weeklyreport_info");
			mv.addObject("msg", "cha");  
			mv.addObject("pd", pd);
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
		logBefore(logger, "批量删除project");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				List<PageData> pdList = new ArrayList<PageData>();
				 
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					 
					weeklyreportService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出project到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("编号");	//1
			titles.add("产品名称");	//2
			titles.add("版本号");	//3
			titles.add("发布时间");	//4
			titles.add("备注");	//5 
			dataMap.put("titles", titles);
			List<PageData> varOList = weeklyreportService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", String.valueOf(varOList.get(i).getInteger("Id")));	//1
				vpd.put("var2", varOList.get(i).getString("PName"));	//2
				vpd.put("var3", varOList.get(i).getString("PVer"));	//3
				vpd.put("var4", String.valueOf(varOList.get(i).getDateTime("ReleaseTime")));	//4
				vpd.put("var5", varOList.get(i).getString("Remark"));	//5 
				 
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
	 
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/project/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "naviproject.xls", "naviproject.xls");
		
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			//String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String filePath = "./" + Const.FILEPATHFILE;			
			logBefore(logger, "readExcel PathUtil.getClasspath() :"+PathUtil.getClasspath()+":"+Const.FILEPATHFILE);
			
			logBefore(logger, "readExcel file :"+filePath+":");
			String fileName =  FileUpload.fileUp(file, filePath, "productexcel");							//执行上传
			logBefore(logger, "readExcel file :"+filePath+":"+fileName);
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);	//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
			/*存入数据库操作======================================*/
			pd.put("Id", "");					//Id
			pd.put("PName", "");					//产品名称	
			pd.put("PVer", "");				//版本
			pd.put("ReleaseTime", "");	//创建时间 
			pd.put("Remark", "");						//备注
			 
			 
			/**
			 * var0 :Id
			 * var1 :名称
			 * var2 :版本
			 * var3 :时间
			 * var4 :备注 
			 */
			for(int i=0;i<listPd.size();i++){		
			 
				//String Id =  listPd.get(i).getString("var0");
				logBefore(logger, "listPd var1:"+listPd.get(i).getString("var1"));
				pd.put("Id",Integer.valueOf(listPd.get(i).getString("var1")));				
				pd.put("PName", listPd.get(i).getString("var1"));							//名称
				  
				if(naviprojectService.findByPN(pd) != null){										//判断名称是否重复
				 
					pd.put("PName", listPd.get(i).getString("var1")+Tools.getRandomNum());	
				}
				pd.put("PVer", listPd.get(i).getString("var2"));	//版本
				pd.put("ReleaseTime", Date.parse(listPd.get(i).getString("var3")));	//时间
				pd.put("Remark", listPd.get(i).getString("var4"));	//备注		
				
				if(naviprojectService.findById(pd)!=null)
				{
					naviprojectService.update(pd);
				}
				else
				{
					naviprojectService.save(pd);
				}
			}
			/*存入数据库操作======================================*/
			
			mv.addObject("msg","success");
		}
		
		mv.setViewName("save_result");
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
	
	//获取名称
		public String getZDnameForId(String Id) {
			logBefore(logger, "递归");
			try {
				PageData pdps = new PageData();;
				pdps.put("ZD_ID", Id);
				pdps = dictionariesService.findById(pdps);
				if(pdps != null){
					 
					String strName = pdps.getString("NAME");
					return  strName;
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				return "";
			}
			return "";
		}
	
}
