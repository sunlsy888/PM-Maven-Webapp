package com.lsy.ritu.controller.system.require;

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
import com.lsy.ritu.entity.system.Menu;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.entity.system.SoftVer;
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.dictionaries.DictionariesService;
import com.lsy.ritu.service.system.menu.MenuService;
import com.lsy.ritu.service.system.project.SoftIssueService;
import com.lsy.ritu.service.system.project.SoftVerService;
import com.lsy.ritu.service.system.require.RequireService;
import com.lsy.ritu.service.system.role.RoleService;
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
import com.lsy.ritu.util.RightsHelper;
import com.lsy.ritu.util.Tools;
import com.lsy.ritu.util.Watermark;

@Controller
@RequestMapping(value="/require")
public class RequireController  extends BaseController{

	String menuUrl = "require/list.do"; //菜单地址(权限用)
	@Resource(name="requireService")
	private RequireService requireService;
	
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name="softverService")
	private SoftVerService softverService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="roleService")
	private RoleService roleService;
	 
	@Resource(name="menuService")
	private MenuService menuService; 
	
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
		pd.put("PId", pd.getString("PId"));	
		pd.put("ItemTitle", pd.getString("ItemTitle"));	
		pd.put("Priority", pd.getString("Priority"));	  
		pd.put("State", "new");	 
		pd.put("SoftCompany", pd.getString("SoftCompany"));	
		pd.put("Ver", pd.getString("Ver"));	 //getZDnameForId(pd.getString("TestVer"))
		pd.put("SubItem", pd.getString("SubItem"));	  
		pd.put("AddDate", df.format(new Date()));	
		pd.put("IsVisible", "1");	
		//pd.put("IsDelete", "0");	
		//pd.put("IsVerify", "0");	 
		pd.put("Des", pd.getString("CONTENT"));				//版本 
		 
		 
		 if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){requireService.save(pd);} //判断新增权限
		 mv.addObject("result","ok");
		 
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除require");
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				pd = this.getPageData();
				 
				requireService.delete(pd);
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
			@RequestParam(value="ItemTitle",required=false) String ItemTitle, 
			@RequestParam(value="Priority",required=false) String Priority,
			@RequestParam(value="SoftCompany",required=false) String SoftCompany,
			@RequestParam(value="Ver",required=false) String Ver,
			@RequestParam(value="SubItem",required=false) String SubItem, 
			@RequestParam(value="CONTENT",required=false) String Des
			) throws Exception{
		logBefore(logger, "修改project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "修改project edit:"+pd.toString());
		 
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("ID", ID);		//ID 
			
			pd.put("PId", pd.getString("PId"));	
			pd.put("ItemTitle", pd.getString("ItemTitle"));	
			pd.put("Priority", pd.getString("Priority"));	   
			pd.put("SoftCompany", pd.getString("SoftCompany"));	
			pd.put("Ver", pd.getString("Ver"));	 //getZDnameForId(pd.getString("TestVer"))
			pd.put("SubItem", pd.getString("SubItem"));	   
			//pd.put("IsDelete", "0");	
			//pd.put("IsVerify", "0");	 
			pd.put("Des", pd.getString("CONTENT"));			
		 
			
			if(null == tpz){tpz = "";}
		 
			requireService.edit(pd);				//执行修改数据库
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
		logBefore(logger, "列表require");
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
			String lastLoginStart = pd.getString("lastLoginStart");
			String lastLoginEnd = pd.getString("lastLoginEnd");
			
			if(lastLoginStart != null && !"".equals(lastLoginStart)){
				lastLoginStart = lastLoginStart+" 00:00:00";
				pd.put("lastLoginStart", lastLoginStart);
			}
			if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
				lastLoginEnd = lastLoginEnd+" 00:00:00";
				pd.put("lastLoginEnd", lastLoginEnd);
			} 
			
			
			page.setPd(pd);
			List<PageData>	varList = requireService.list(page);	//列出require列表
			mv.setViewName("system/require/require_list");
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
	public ModelAndView goAdd(Page page){
		logBefore(logger, "去新增require页面");
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
			pd2.put("BIANMA", "RequirePriority");//ZD_ID
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
			String PId=pd.getString("PId");
			logBefore(logger, "project PId:"+pd.getString("PId").toString());
			//page.setPd(pd);
			List<SoftVer> verList=softverService.listSoftVer(PId);
			
			logBefore(logger, "project verList:"+verList.toString());
			mv.setViewName("system/require/require_add");
			mv.addObject("pd", pd);
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("verList", verList);
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
		logBefore(logger, "去修改require页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = requireService.findById(pd);	//根据ID读取
			
			logBefore(logger, "require edit:"+pd.toString());
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
			pd2.put("BIANMA", "RequirePriority");//ZD_ID
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
			String PId=pd.getString("PId");
			logBefore(logger, "project PId:"+pd.getString("PId").toString());
			//page.setPd(pd);
			List<SoftVer> verList=softverService.listSoftVer(PId);
			
			mv.setViewName("system/require/require_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("verList", verList);
			mv.addObject("roleList", roleList);
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
	        String strId=pd.getString("Require_Id");
	       
	        logBefore(logger, "updatestate strId:"+strId.toString());
			 if(!strId.contains(","))
			 {
			pd = requireService.findById(pd);	//根据ID读取
			 }else
			 {
				 pd = requireService.findById(pd);
				 pd.put("Require_Id", strId); 
			 }
			 
			//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				String strRoleName="";
				User user = (User)session.getAttribute(Const.SESSION_USER);
				logBefore(logger, "project user:"+user.getUSERNAME()+"---------"+user.getROLE_ID());
				if (user != null) {
					
					User userr = (User)session.getAttribute(Const.SESSION_USERROL);
					if(null == userr){
						user = userService.getUserAndRoleById(user.getUSER_ID());
						session.setAttribute(Const.SESSION_USERROL, user);
					}else{
						user = userr;
					}
					Role role = user.getRole();
					
					logBefore(logger, "project role:"+user.getRole().toString()+"---------"+role.getROLE_NAME().toString());
				
					strRoleName=role.getROLE_NAME();
				}
				//strRoleName=strRoleName.getBytes("UTF-8").toString();
			    
			    //byte[] bytes = strRoleName.getBytes();   
			    //strRoleName = new String(bytes,"UTF-8");   
			    //String s2 = new String(bytes);  
			    //strRoleName = new String(strRoleName.getBytes("GBK"),"GBK");
			    //strRoleName = new String(strRoleName.toString().getBytes("UTF-8"));  
			    
				logBefore(logger, "project strRoleName:"+strRoleName.toString()+"====");
				
				if(strRoleName.contains("PM")||strRoleName.contains("DR")||strRoleName.contains("研发"))
				{
					
					logBefore(logger, "project strRoleName:qita");
					mv.setViewName("system/require/require_update");
					mv.addObject("msg", "edit");
				}else
				{
					logBefore(logger, "project strRoleName:PM 研发");
					mv.setViewName("system/require/require_updatestate");
					mv.addObject("msg", "edit");
				}
			   
			 
			logBefore(logger, "project EDIT:"+pd.toString());
			//mv.setViewName("system/require/require_updatestate");
			//mv.addObject("msg", "edit");
			mv.addObject("PSTATEList", PSTATEList);
		
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
			logBefore(logger, "updateState  pd:"+pd.toString());
			if(Jurisdiction.buttonJurisdiction(menuUrl, "confirm")){
				 
				String DATA_IDS = pd.getString("Require_Id");
				String state = pd.getString("State");
				logBefore(logger, "updateState:"+DATA_IDS.toString()+"====State:"+state);
				pd.put("State", state);
				pd.put("AddDate", df.format(new Date()));	
				
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				String strRoleName="";
				User user = (User)session.getAttribute(Const.SESSION_USER);
				logBefore(logger, "project user:"+user.getUSERNAME()+"---------"+user.getROLE_ID());
				//pd.put("DAuthor", user.getUSERNAME());
				 
				
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					for(int i=0;i<ArrayDATA_IDS.length;i++)
					{
						if(ArrayDATA_IDS[i]!="")
						{
							pd.put("item", ArrayDATA_IDS[i]);
							requireService.updateState(pd);
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
				 
				String DATA_IDS = pd.getString("Require_Id");
				String state = pd.getString("State");
				logBefore(logger, "updateState:"+DATA_IDS.toString()+"====State:"+state);
				pd.put("State", state);
				pd.put("AddDate", df.format(new Date()));	
				
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				String strRoleName="";
				User user = (User)session.getAttribute(Const.SESSION_USER);
				logBefore(logger, "project user:"+user.getUSERNAME()+"---------"+user.getROLE_ID());
				pd.put("DAuthor", user.getUSERNAME());
				pd.put("DPlanDate", pd.getString("DPlanDate"));
				 
				pd.put("Remark", pd.getString("CONTENT"));	
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					for(int i=0;i<ArrayDATA_IDS.length;i++)
					{
						if(ArrayDATA_IDS[i]!="")
						{
							pd.put("Require_Id", ArrayDATA_IDS[i]);
							requireService.updatepm(pd);
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
	
	@RequestMapping(value="/reinfo")
	public ModelAndView goReInfo(Page page){
		logBefore(logger, "去project页面");
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project PMInfo:"+pd.toString());
		try { 
			pd = requireService.findById(pd);	//根据ID读取
			 
			String KEYW = pd.getString("keyword");
			int Require_Id = pd.getInteger("Require_Id");
			logBefore(logger, "project PMInfo:"+Require_Id);
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			} 
			pd.put("Require_Id", Require_Id); 
			  
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
			}
			
			page.setPd(pd);
			//logBefore(logger, "project PMInfo page:"+page.toString());
			//List<PageData>	softissueList = softissueService.list(page);	//列出project列表
			List<PageData>	varList = requireService.list(page);	//列出project列表
			mv.setViewName("system/require/require_info");
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
		logBefore(logger, "批量删除require");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				List<PageData> pdList = new ArrayList<PageData>();
				 
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					 
					requireService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出require到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("序号");	//1
			titles.add("项目编号");	//2
			titles.add("负责公司");	//3
			titles.add("状态");	//4
			titles.add("版本名称");	//5
			titles.add("标题");	//6
			titles.add("类别");	//6
			titles.add("需求描述");	//6
			titles.add("重要等级");	//6
			titles.add("提交者");	//6
			titles.add("提交日期");	//6
			titles.add("研发状态");	//6
			titles.add("研发负责人");	//6
			titles.add("研发计划时间");	//6
			titles.add("结束时间");	//6
			titles.add("是否延迟");	//6
			titles.add("是否显示");	//6
			titles.add("添加时间");	//6 
			
			dataMap.put("titles", titles);
			List<PageData> varOList = requireService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", String.valueOf(varOList.get(i).getInteger("Require_Id")));	//1
				vpd.put("var2", varOList.get(i).getString("PId"));	//2
				vpd.put("var3", varOList.get(i).getString("SoftCompany"));	//3
				vpd.put("var4", varOList.get(i).getString("State"));	//4
				vpd.put("var5", varOList.get(i).getString("Ver"));	//5
				vpd.put("var6", varOList.get(i).getString("ItemTitle"));	//6
				vpd.put("var7", varOList.get(i).getString("SubItem"));	//6
				vpd.put("var8", varOList.get(i).getString("Des"));	//6
				vpd.put("var9", varOList.get(i).getString("Priority"));	//6
				vpd.put("var10", varOList.get(i).getString("SAuthor"));	//6
				vpd.put("var11", varOList.get(i).getDateTime("SDate"));	//6
				vpd.put("var12", varOList.get(i).getString("DState"));	//6
				vpd.put("var13", varOList.get(i).getString("DAuthor"));	//6
				vpd.put("var14", varOList.get(i).getDateTime("DPlanDate"));	//6
				vpd.put("var15", varOList.get(i).getDateTime("EndDate"));	//6
				vpd.put("var16", varOList.get(i).getString("Remark"));	//6
				vpd.put("var17", varOList.get(i).getString("IsDefer"));	//6
				vpd.put("var18", varOList.get(i).getString("IsVisible"));	//6
				vpd.put("var19", varOList.get(i).getDateTime("AddDate"));	//6 
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
