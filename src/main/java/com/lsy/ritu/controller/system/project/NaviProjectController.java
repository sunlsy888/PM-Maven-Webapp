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
import com.lsy.ritu.entity.system.Menu;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.dictionaries.DictionariesService;
import com.lsy.ritu.service.system.menu.MenuService;
import com.lsy.ritu.service.system.project.NaviProjectService;
import com.lsy.ritu.service.system.project.SoftIssueService;
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
import com.lsy.ritu.util.RightsHelper;
import com.lsy.ritu.util.Tools;



@Controller
@RequestMapping(value="/project")
public class NaviProjectController  extends BaseController{

	String menuUrl = "project/list.do"; //菜单地址(权限用)
	@Resource(name="naviprojectService")
	private NaviProjectService naviprojectService;
	
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name="userService")
	private UserService userService;
	 
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="softissueService")
	private SoftIssueService softissueService;
	
	
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
		pd.put("PNo", pd.getString("PNo"));	
		pd.put("PName", pd.getString("PName"));	
		pd.put("PManager", pd.getString("PManager"));	
		logBefore(logger, "project save:"+pd.getString("PManager"));  
		pd.put("PDate", df.format(new Date()));	
		
		pd.put("PState", "新项目");	 
		pd.put("PDataVer", getZDnameForId(pd.getString("PDataVer")));	
		pd.put("PType", getZDnameForId(pd.getString("PType")));	
		pd.put("PLevel", getZDnameForId(pd.getString("PLevel")));	
		pd.put("PCustomer", pd.getString("PCustomer"));	
		pd.put("SoftCompany", getZDnameForId(pd.getString("SoftCompany")));	
		pd.put("HardSolution", getZDnameForId(pd.getString("HardSolution")));	
		pd.put("ProductType", getZDnameForId(pd.getString("ProductType")));	
		pd.put("ScreenResolution", getZDnameForId(pd.getString("ScreenResolution")));	
		pd.put("Memory", pd.getString("Memory"));	
		pd.put("OperSystem", getZDnameForId(pd.getString("OperSystem")));	
		pd.put("Storage", getZDnameForId(pd.getString("Storage")));	
		pd.put("PortRate", getZDnameForId(pd.getString("PortRate")));	
		pd.put("CPUType", getZDnameForId(pd.getString("CPUType")));	
		pd.put("CompilerEnv", getZDnameForId(pd.getString("CompilerEnv")));	
		pd.put("EquipType", pd.getString("EquipType"));	
		pd.put("NaviPath", pd.getString("NaviPath"));	
		pd.put("BindMode", getZDnameForId(pd.getString("BindMode")));	
		pd.put("ActMode", getZDnameForId(pd.getString("ActMode")));	
		pd.put("CPUFrequency", pd.getString("CPUFrequency"));	
		pd.put("IsVisible", "1");	
		pd.put("IsDelete", "0");	
		pd.put("IsVerify", "0");	
		pd.put("PSource", pd.getString("PSource"));					//产品名称	
		pd.put("PRemark", pd.getString("CONTENT"));				//版本 
		pd.put("ItemType", pd.getString("ItemType"));	
		pd.put("PUpdateDate", df.format(new Date()));	
		logBefore(logger, "add editor:"+pd.getString("editor")+"add editor CONTENT:"+pd.getString("CONTENT")); 
		
		 
		 if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){naviprojectService.save(pd);} //判断新增权限
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
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){naviprojectService.delete(pd);}
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
			@RequestParam(value="PName",required=false) String PName,
			@RequestParam(value="PManager",required=false) String PManager,
			@RequestParam(value="PDate",required=false) Date PDate,
			@RequestParam(value="PState",required=false) String PState,
			@RequestParam(value="PDataVer",required=false) String PDataVer,
			@RequestParam(value="PType",required=false) String PType,
			@RequestParam(value="PLevel",required=false) String PLevel,
			@RequestParam(value="PCustomer",required=false) String PCustomer,
			@RequestParam(value="SoftCompany",required=false) String SoftCompany,
			@RequestParam(value="HardSolution",required=false) String HardSolution,
			@RequestParam(value="ProductType",required=false) String ProductType,
			@RequestParam(value="ScreenResolution",required=false) String ScreenResolution,
			@RequestParam(value="Memory",required=false) String Memory,
			@RequestParam(value="OperSystem",required=false) String OperSystem,
			@RequestParam(value="Storage",required=false) String Storage,
			@RequestParam(value="PortRate",required=false) String PortRate,
			@RequestParam(value="CPUType",required=false) String CPUType,
			@RequestParam(value="CompilerEnv",required=false) String CompilerEnv,
			@RequestParam(value="EquipType",required=false) String EquipType,
			@RequestParam(value="NaviPath",required=false) String NaviPath,
			@RequestParam(value="BindMode",required=false) String BindMode,
			@RequestParam(value="ActMode",required=false) String ActMode,
			@RequestParam(value="CPUFrequency",required=false) String CPUFrequency, 
			@RequestParam(value="PSource",required=false) String PSource, 
			@RequestParam(value="CONTENT",required=false) String Remark,
			@RequestParam(value="ItemType",required=false) String ItemType
			) throws Exception{
		logBefore(logger, "修改project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "修改project edit:"+pd.toString());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		logBefore(logger, "project edit ID:"+ID.toString());
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("ID", ID);		//ID 
			pd.put("PName", PName);	
			pd.put("PManager", PManager);	 
			pd.put("PDataVer", getZDnameForId(PDataVer));	
			pd.put("PType", getZDnameForId(PType));	
			pd.put("PLevel", getZDnameForId(PLevel));	
			pd.put("PCustomer", PCustomer);	
			pd.put("SoftCompany", getZDnameForId(SoftCompany));	
			pd.put("HardSolution", getZDnameForId(HardSolution));	
			pd.put("ProductType", getZDnameForId(ProductType));	
			pd.put("ScreenResolution", getZDnameForId(ScreenResolution));	
			pd.put("Memory", Memory);	
			pd.put("OperSystem", getZDnameForId(OperSystem));	
			pd.put("Storage", getZDnameForId(Storage));	
			pd.put("PortRate", getZDnameForId(PortRate));	
			pd.put("CPUType", getZDnameForId(CPUType));	
			pd.put("CompilerEnv", getZDnameForId(CompilerEnv));	
			pd.put("EquipType", EquipType);	
			pd.put("NaviPath", NaviPath);	
			pd.put("BindMode", getZDnameForId(BindMode));	
			pd.put("ActMode", getZDnameForId(ActMode));	
			pd.put("CPUFrequency", CPUFrequency);	 
			pd.put("PSource", PSource);					//产品名称	
			pd.put("PRemark", Remark);			  
			pd.put("ItemType", ItemType);
			pd.put("PUpdateDate", df.format(new Date()));	
			if(null == tpz){tpz = "";}
		 
			naviprojectService.edit(pd);				//执行修改数据库
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
			pd.put("IsVisible", "1");
			pd.put("IsDelete", "0");
			pd.put("IsVerify", "1");
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
		  
			page.setPd(pd);
			List<PageData>	varList = naviprojectService.list(page);	//列出project列表
			mv.setViewName("system/project/naviproject_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			logBefore(logger, "列表project:"+this.getHC());
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
			pd.put("IsVerify", "1");
			
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
			List<PageData>	varList = naviprojectService.list(page);	//列出product列表
			mv.setViewName("system/project/mynaviproject_list");
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
	@RequestMapping(value="/cylist")
	public ModelAndView cylist(Page page){
		logBefore(logger, "列表project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String KEYW = pd.getString("keyword");
			pd.put("IsVisible", "1");
			pd.put("IsDelete", "0");
			pd.put("IsVerify", "1");
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			 
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			logBefore(logger, "project:"+pds.toString());
			if(null != pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
				String NAME=pds.getString("NAME");
				String USER_ID=pds.getString("USER_ID");
				pd.put("PManager", NAME);
				pd.put("USER_ID", USER_ID);
				logBefore(logger, "project:"+NAME+":"+USER_ID);
			}
		
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
		 
			
			page.setPd(pd);
			List<PageData>	varList = naviprojectService.listcy(page);	//列出product列表
			mv.setViewName("system/project/naviprojectforme_list");
			mv.addObject("varList", varList);
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
			List<Dictionaries> dictionariesList = dictionariesService.listAllDictionaries();
			List<User> pmanagerList;
			List<Dictionaries> PDataVerList,PTypeList,PLevelList,SoftCompanyList,HardSolutionList,ProductTypeList,
			ScreenResolutionList,OperSystemList,StorageList,PortRateList,CPUTypeList,CompilerEnvList,BindModeList,ActModeList;
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
			pd2.put("BIANMA", "PLevel");//ZD_ID
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
			pd2 = new PageData();
			pd2.put("BIANMA", "SoftCompany");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				SoftCompanyList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				SoftCompanyList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "HardSolution");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				HardSolutionList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				HardSolutionList=null;
			} 
			 
			pd2 = new PageData();
			pd2.put("BIANMA", "ProductType");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				ProductTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				ProductTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "ScreenResolution");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				ScreenResolutionList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				ScreenResolutionList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "OperSystem");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				OperSystemList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				OperSystemList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "Storage");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				StorageList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				StorageList=null;
			}
			
			pd2 = new PageData();
			pd2.put("BIANMA", "PortRate");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				PortRateList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				PortRateList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "CPUType");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				CPUTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				CPUTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "CompilerEnv");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				CompilerEnvList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				CompilerEnvList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "BindMode");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				BindModeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				BindModeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "ActMode");//ZD_ID
			pd2.put("ZD_ID", "");  
			logBefore(logger, "project add:"+pd2.toString());
			if(dictionariesService.findBmCount(pd2) != null){
				logBefore(logger, "project add findBmCount:"+pd2);
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID");
				logBefore(logger, "project add findBmCount:"+PARENT_ID);
				ActModeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				logBefore(logger, "project add:"+"null");
				ActModeList=null;
			} 
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式 yyyy-MM-dd HH:mm:ss
			pd.put("PNo", df.format(new Date()));  
			//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			logBefore(logger, "project save:"+df.format(new Date())); 
		//pmanagerList,PDataVerList,PTypeList,PLevelList,SoftCompanyList,HardSolutionList,ProductTypeList,
			//ScreenResolutionList,OperSystemList,StorageList,PortRateList,CPUTypeList,CompilerEnvList,BindModeList,ActModeList;		 
			mv.setViewName("system/project/naviproject_add");
			mv.addObject("pd", pd);
			//mv.addObject("PNo",df.format(new Date()));
			mv.addObject("dictionariesList", dictionariesList);
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("SoftCompanyList", SoftCompanyList);
			mv.addObject("HardSolutionList", HardSolutionList);
			mv.addObject("ProductTypeList", ProductTypeList);
			mv.addObject("ScreenResolutionList", ScreenResolutionList);
			mv.addObject("OperSystemList", OperSystemList);
			mv.addObject("StorageList", StorageList);
			mv.addObject("PortRateList", PortRateList);
			mv.addObject("CPUTypeList", CPUTypeList);
			mv.addObject("CompilerEnvList", CompilerEnvList);
			mv.addObject("BindModeList", BindModeList);
			mv.addObject("ActModeList", ActModeList); 
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
	        String strId=pd.getString("ID");
	       
	        logBefore(logger, "updatestate strId:"+strId.toString());
			 if(!strId.contains(","))
			 {
			pd = naviprojectService.findById(pd);	//根据ID读取
			 }else
			 {
				 pd.put("Id", strId); 
			 }
			logBefore(logger, "project EDIT:"+pd.toString());
			mv.setViewName("system/project/naviproject_updatestate");
			mv.addObject("msg", "edit");
			mv.addObject("PSTATEList", PSTATEList);
		
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/pminfo")
	public ModelAndView goPMInfo(Page page){
		logBefore(logger, "去project页面");
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project PMInfo:"+pd.toString());
		try { 
			pd = naviprojectService.findById(pd);	//根据ID读取
			 
			String KEYW = pd.getString("keyword");
			String Pno = pd.getString("PNo");
			logBefore(logger, "project PMInfo:"+Pno.toString());
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			pd.put("PNo", Pno); 
			pd.put("PId", Pno); 
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());
					session.setAttribute(Const.SESSION_USERROL, user);
				}else{
					user = userr;
				}
				Role role = user.getRole();
				String roleRights = role!=null ? role.getRIGHTS() : "";
				//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
				session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); 		//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, user.getUSERNAME());	//放入用户名
				
			List<Menu> pmmenuList = new ArrayList<Menu>();
			//session.setAttribute(Const.SESSION_menuList, menuList1);
			if(null == session.getAttribute(Const.SESSION_pmmenuList)){
				pmmenuList = menuService.listSubMenuByParentId("32");
				if(Tools.notEmpty(roleRights)){
					for(Menu menu : pmmenuList){
						logBefore(logger, "project PMInfo menu.getMENU_ID():"+menu.getMENU_ID());
						menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
						 
					}
				}
				session.setAttribute(Const.SESSION_pmmenuList, pmmenuList);			//菜单权限放入session中
			}else{
				pmmenuList = (List<Menu>)session.getAttribute(Const.SESSION_pmmenuList);
			}
			
		 
			 
			page.setPd(pd);
			//logBefore(logger, "project PMInfo page:"+page.toString());
			List<PageData>	softissueList = softissueService.list(page);	//列出project列表
			List<PageData>	varList = naviprojectService.list(page);	//列出project列表
			mv.setViewName("system/project/naviproject_info");
			mv.addObject("msg", "cha"); 
			mv.addObject("softissueList", softissueList);
			mv.addObject("varList", varList);
			mv.addObject("menuList", pmmenuList);
			mv.addObject("pd", pd);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/pminfo2")
	public ModelAndView goPMInfo2(Page page){
		logBefore(logger, "去project页面");
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project PMInfo:"+pd.toString());
		try { 
			
			String KEYW = pd.getString("keyword");
			String PId = pd.getString("PId");
			logBefore(logger, "project PMInfo:"+PId.toString());
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			pd.put("PNo", PId); 
			pd.put("PId", PId); 
			pd = naviprojectService.findByPId(pd);	//根据ID读取
			 
			page.setPd(pd);
			//logBefore(logger, "project PMInfo page:"+page.toString());
			//List<PageData>	softissueList = softissueService.list(page);	//列出project列表
			List<PageData>	varList = naviprojectService.list(page);	//列出project列表
			mv.setViewName("system/project/naviproject_info2");
			mv.addObject("msg", "cha"); 
			//mv.addObject("softissueList", softissueList);
			mv.addObject("varList", varList);
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
					 
					naviprojectService.deleteAll(ArrayDATA_IDS);
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
			List<Dictionaries> PDataVerList,PTypeList,PLevelList,SoftCompanyList,HardSolutionList,ProductTypeList,
			ScreenResolutionList,OperSystemList,StorageList,PortRateList,CPUTypeList,CompilerEnvList,BindModeList,ActModeList;
			pd2 = new PageData();
			pd2.put("BIANMA", "PM");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				pmanagerList = userService.listUserzu(pd2);	
			} else
			{ 
				pmanagerList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PDataVer");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				PDataVerList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				PDataVerList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PType");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				PTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				PTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "PLevel");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				PLevelList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				PLevelList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "SoftCompany");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				SoftCompanyList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				SoftCompanyList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "HardSolution");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				HardSolutionList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				HardSolutionList=null;
			} 
			 
			pd2 = new PageData();
			pd2.put("BIANMA", "ProductType");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				ProductTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				ProductTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "ScreenResolution");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				ScreenResolutionList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				ScreenResolutionList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "OperSystem");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				OperSystemList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				OperSystemList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "Storage");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				StorageList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				StorageList=null;
			}
			
			pd2 = new PageData();
			pd2.put("BIANMA", "PortRate");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				PortRateList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				PortRateList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "CPUType");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				CPUTypeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				CPUTypeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "CompilerEnv");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				CompilerEnvList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				CompilerEnvList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "BindMode");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				BindModeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{ 
				BindModeList=null;
			}
			pd2 = new PageData();
			pd2.put("BIANMA", "ActMode");//ZD_ID
			pd2.put("ZD_ID", "");   
			if(dictionariesService.findBmCount(pd2) != null){ 
				PageData pd3=dictionariesService.findBmCount(pd2);
				PARENT_ID = pd3.getString("ZD_ID"); 
				ActModeList = dictionariesService.listGetDictionaries(PARENT_ID);	
			} else
			{
				
				ActModeList=null;
			} 
	 
			 
			pd = naviprojectService.findById(pd);	//根据ID读取
			logBefore(logger, "project EDIT:"+pd.toString());
			mv.setViewName("system/project/naviproject_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("SoftCompanyList", SoftCompanyList);
			mv.addObject("HardSolutionList", HardSolutionList);
			mv.addObject("ProductTypeList", ProductTypeList);
			mv.addObject("ScreenResolutionList", ScreenResolutionList);
			mv.addObject("OperSystemList", OperSystemList);
			mv.addObject("StorageList", StorageList);
			mv.addObject("PortRateList", PortRateList);
			mv.addObject("CPUTypeList", CPUTypeList);
			mv.addObject("CompilerEnvList", CompilerEnvList);
			mv.addObject("BindModeList", BindModeList);
			mv.addObject("ActModeList", ActModeList); 
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	/**
	 * 批量更新
	 */
	@RequestMapping(value="/updateState")
	@ResponseBody
	public Object updateState(PrintWriter out) throws Exception {
		logBefore(logger, "批量删除project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "confirm")){
				 
				String DATA_IDS = pd.getString("Id");
				String pstate = pd.getString("PState");
				logBefore(logger, "updateState:"+DATA_IDS.toString()+"====PState:"+pstate);
				pd.put("PState", pstate);
				pd.put("PUpdateDate", df.format(new Date()));	
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					for(int i=0;i<ArrayDATA_IDS.length;i++)
					{
						if(ArrayDATA_IDS[i]!="")
						{
							pd.put("item", ArrayDATA_IDS[i]);
							naviprojectService.updateState(pd);
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
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page){
		logBefore(logger, "导出project到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("序号");	//1 Id
			titles.add("项目编号");	//2 PNo
			titles.add("项目名称");	//3  PName
			titles.add("项目负责人");	//4  PManager
			titles.add("立项日期");	//5  PDate
			titles.add("项目状态");	//6 PState
			titles.add("数据版本");	//7 PDataVer
			titles.add("项目类型");	//8 PType
			titles.add("项目等级");	//9 PLevel
			titles.add("客户名称");	//10 PCustomer
			titles.add("研发部门");	//11 SoftCompany
			titles.add("硬件方案");	//12 HardSolution
			titles.add("产品类型");	//13 ProductType
			titles.add("屏幕分辨率");	//14 ScreenResolution
			titles.add("内存");	//15 Memory
			titles.add("操作系统");	//16 OperSystem
			titles.add("存储设备");	//17 Storage
			titles.add("端口号");	//18 PortRate
			titles.add("CPU类型");	//19 CPUType
			titles.add("开发环境");	//20 CompilerEnv
			titles.add("设备类型");	//21 EquipType
			titles.add("导航路径");	//22 NaviPath
			titles.add("绑定方式");	//23 BindMode
			titles.add("激活方式");	//24 ActMode
			titles.add("CPU主频");	//25 CPUFrequency	
			titles.add("来源");	//26 PSource
			titles.add("备注");	//27 PRemark
			titles.add("项目分类");	//28 ItemType
			titles.add("是否显示");	//29 IsVisible
			titles.add("是否删除");	//30 IsDelete
			titles.add("是否通过审核");	//31 IsVerify
			titles.add("更新时间");	//31 PUpdateDate
		//?type=my
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			 
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			logBefore(logger, "project:"+pds.toString());
			if(null != pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
				String NAME=pds.getString("NAME");
				String USER_ID=pds.getString("USER_ID");
				pd.put("PManager", NAME);
				pd.put("USER_ID", USER_ID);
				logBefore(logger, "project:"+NAME+":"+USER_ID);
			}
			pd.put("IsVisible", "1");
			pd.put("IsDelete", "0");
			pd.put("IsVerify", "1");

			page.setPd(pd);
			
			List<PageData> varOList ;
			String type = pd.getString("type");
			logBefore(logger, "project type:"+type.toString());
			
			dataMap.put("titles", titles);
			if(type.equals("me"))
			{
				logBefore(logger, "project type:me:"+pd.toString());
				varOList = naviprojectService.listcy(page);
			}else if(type.equals("my"))
			{
				logBefore(logger, "project type:my:"+pd.toString());
				varOList = naviprojectService.list(page);
			}else if(type.equals("all")){
				logBefore(logger, "project type:all:"+pd.toString());
			  varOList = naviprojectService.listAll(pd);
			}else
			{
				logBefore(logger, "project type:other:"+pd.toString());
				varOList = naviprojectService.list(page);
			}
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", String.valueOf(varOList.get(i).getInteger("Id")));	//1
				vpd.put("var2", varOList.get(i).getString("PNo"));	//2 PNo
				vpd.put("var3", varOList.get(i).getString("PName"));	//3
				vpd.put("var4", varOList.get(i).getString("PManager"));	//4
				vpd.put("var5", varOList.get(i).getString("PDate"));	//5
				vpd.put("var6", varOList.get(i).getString("PState"));	//5 
				vpd.put("var7", varOList.get(i).getString("PDataVer"));	//5 
				vpd.put("var8", varOList.get(i).getString("PType"));	//5 
				vpd.put("var9", varOList.get(i).getString("PLevel"));	//5 
				vpd.put("var10", varOList.get(i).getString("PCustomer"));	//5 
				vpd.put("var11", varOList.get(i).getString("SoftCompany"));	//5 
				vpd.put("var12", varOList.get(i).getString("HardSolution"));	//5 
				vpd.put("var13", varOList.get(i).getString("ProductType"));	//5 
				vpd.put("var14", varOList.get(i).getString("ScreenResolution"));	//5 
				vpd.put("var15", varOList.get(i).getString("Memory"));	//5 
				vpd.put("var16", varOList.get(i).getString("OperSystem"));	//5 
				vpd.put("var17", varOList.get(i).getString("Storage"));	//5 
				vpd.put("var18", varOList.get(i).getString("PortRate"));	//5 
				vpd.put("var19", varOList.get(i).getString("CPUType"));	//5 
				vpd.put("var20", varOList.get(i).getString("CompilerEnv"));	//5 
				vpd.put("var21", varOList.get(i).getString("EquipType"));	//5 
				vpd.put("var22", varOList.get(i).getString("NaviPath"));	//5 
				vpd.put("var23", varOList.get(i).getString("BindMode"));	//5 
				vpd.put("var24", varOList.get(i).getString("ActMode"));	//5 
				vpd.put("var25", varOList.get(i).getString("CPUFrequency"));	//5 
				vpd.put("var26", varOList.get(i).getString("PSource"));	//5 
				vpd.put("var27", varOList.get(i).getString("PRemark"));	//5 
				vpd.put("var28", varOList.get(i).getString("ItemType"));	//5 
				vpd.put("var29", varOList.get(i).getString("IsVisible"));	//5 
				vpd.put("var30", varOList.get(i).getString("IsDelete"));	//5 
				vpd.put("var31", varOList.get(i).getString("IsVerify"));	//5 
				vpd.put("var32", varOList.get(i).getString("PUpdateDate"));	//5 
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
