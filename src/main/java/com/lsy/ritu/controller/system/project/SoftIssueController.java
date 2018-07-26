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
import com.lsy.ritu.entity.system.SoftVer;
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.dictionaries.DictionariesService;
import com.lsy.ritu.service.system.menu.MenuService;
import com.lsy.ritu.service.system.project.NaviProjectService;
import com.lsy.ritu.service.system.project.SoftIssueService;
import com.lsy.ritu.service.system.project.SoftVerService;
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
@RequestMapping(value="/problem")
public class SoftIssueController  extends BaseController{

	String menuUrl = "problem/list.do"; //菜单地址(权限用)
	@Resource(name="naviprojectService")
	private NaviProjectService naviprojectService;
	
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="softissueService")
	private SoftIssueService softissueService;
	
	@Resource(name="softverService")
	private SoftVerService softverService; 
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="menuService")
	private MenuService menuService; 
	
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
		pd.put("Title", pd.getString("Title"));	
		pd.put("Priority", pd.getString("Priority"));	  
		pd.put("State", "新项目");	 
		pd.put("ItemType", pd.getString("ItemType"));	
		pd.put("TestVer", pd.getString("TestVer"));	 //getZDnameForId(pd.getString("TestVer"))
		pd.put("TestOne", pd.getString("TestOne"));	  
		pd.put("TestDate", df.format(new Date()));	
		//pd.put("IsVisible", "1");	
		//pd.put("IsDelete", "0");	
		//pd.put("IsVerify", "0");	 
		pd.put("Content", pd.getString("CONTENT"));				//版本 
		 
		 
		 if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){softissueService.save(pd);} //判断新增权限
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
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){softissueService.delete(pd);}
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
			@RequestParam(value="Title",required=false) String Title,
			@RequestParam(value="Priority",required=false) String Priority,
			@RequestParam(value="ItemType",required=false) String ItemType,
			@RequestParam(value="TestVer",required=false) String TestVer,
			@RequestParam(value="TestOne",required=false) String TestOne,
			@RequestParam(value="TestDate",required=false) Date TestDate, 
			@RequestParam(value="PId",required=false) String PId, 
			@RequestParam(value="CONTENT",required=false) String Content
			) throws Exception{
		logBefore(logger, "修改project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "修改project edit:"+pd.toString());
		
		logBefore(logger, "project edit ID:"+Id.toString());
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("Id", Id);		//ID 
			pd.put("Title", Title);	
			pd.put("Priority", Priority);	 //getZDnameForId
			pd.put("ItemType", ItemType);	
			pd.put("TestVer", TestVer);	
			pd.put("TestOne", TestOne);	 
			//pd.put("PId", PId);					//产品名称	
			pd.put("Content", Content);			  
			
			if(null == tpz){tpz = "";}
		 
			softissueService.edit(pd);				//执行修改数据库
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
			List<PageData>	varList = softissueService.list(page);	//列出project列表
			mv.setViewName("system/problem/softissue_list");
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
			List<PageData>	varList = softissueService.list(page);	//列出product列表
			mv.setViewName("system/project/softissue_list");
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
			List<Dictionaries> PLevelList;
			String PARENT_ID="";
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
			
			page.setPd(pd);
			List<PageData>	varList = softissueService.list(page);	//列出product列表
			mv.setViewName("system/problem/softissue_list");
			mv.addObject("varList", varList);
			mv.addObject("PLevelList", PLevelList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/info")
	public ModelAndView goReInfo(Page page){
		logBefore(logger, "去project页面");
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project PMInfo:"+pd.toString());
		try { 
			
			 
			String KEYW = pd.getString("keyword");
			String  Id = pd.getString("Id");
			logBefore(logger, "project PMInfo:"+Id);
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			} 
			pd.put("Id", Id); 
			pd = softissueService.findById(pd);	//根据ID读取
			logBefore(logger, "softissueService PMInfo:"+pd.toString());
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
			//List<PageData>	varList = softissueService.list(page);	//列出project列表
			mv.setViewName("system/problem/softissue_info");
			mv.addObject("msg", "cha"); 
			//mv.addObject("softissueList", softissueList);
			//mv.addObject("varList", varList); 
			mv.addObject("pd", pd);
			 
		} catch (Exception e) {
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
			String PId=pd.getString("PId");
			logBefore(logger, "project PId:"+pd.getString("PId").toString());
			//page.setPd(pd);
			List<SoftVer> verList=softverService.listSoftVer(PId);

            mv.setViewName("system/problem/softissue_add");
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
			//获取版本信息
			//pd.put("PId", pd.getString("PId"));
			String PId=pd.getString("PId");
			logBefore(logger, "project PId:"+pd.getString("PId").toString());
			//page.setPd(pd);
			List<SoftVer> verList=softverService.listSoftVer(PId);
			 
		 
			pd = softissueService.findById(pd);	//根据ID读取
			logBefore(logger, "project EDIT:"+pd.toString());
			mv.setViewName("system/problem/softissue_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pmanagerList", pmanagerList);
			mv.addObject("PDataVerList", PDataVerList);
			mv.addObject("PTypeList", PTypeList);
			mv.addObject("PLevelList", PLevelList); 
			mv.addObject("pd", pd);
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
	@RequestMapping(value="/pminfo")
	public ModelAndView goPMInfo(){
		logBefore(logger, "去project页面");
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "project PMInfo:"+pd.toString());
		try { 
			pd = softissueService.findById(pd);	//根据ID读取
			 
			String KEYW = pd.getString("keyword");
			String PId = pd.getString("PId");
			logBefore(logger, "project PMInfo:"+PId.toString());
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			pd.put("PId", PId); 
			 
			mv.setViewName("system/problem/softissue_info");
			mv.addObject("msg", "cha");  
			mv.addObject("pd", pd);
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
	        String strId=pd.getString("Id");
	       
	        logBefore(logger, "updatestate strId:"+strId.toString());
			 if(!strId.contains(","))
			 {
			pd = softissueService.findById(pd);	//根据ID读取
			 }else
			 {
				 pd.put("Id", strId); 
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
					mv.setViewName("system/problem/softissue_update");
					mv.addObject("msg", "edit");
				}else
				{
					logBefore(logger, "project strRoleName:PM 研发");
					mv.setViewName("system/problem/softissue_updatestate");
					mv.addObject("msg", "edit");
				}
			   
			 
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
				 
				String DATA_IDS = pd.getString("Id");
				String state = pd.getString("State");
				logBefore(logger, "updateState:"+DATA_IDS.toString()+"====State:"+state);
				pd.put("State", state);
				pd.put("CloseDate", df.format(new Date()));	
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					for(int i=0;i<ArrayDATA_IDS.length;i++)
					{
						if(ArrayDATA_IDS[i]!="")
						{
							pd.put("item", ArrayDATA_IDS[i]);
							softissueService.updateState(pd);
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
				 
				String DATA_IDS = pd.getString("Id");
				String state = pd.getString("State");
				logBefore(logger, "updateState:"+DATA_IDS.toString()+"====State:"+state);
				pd.put("State", state);
				pd.put("CloseDate", df.format(new Date()));	
				
				//shiro管理的session
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				String strRoleName="";
				User user = (User)session.getAttribute(Const.SESSION_USER);
				logBefore(logger, "project user:"+user.getUSERNAME()+"---------"+user.getROLE_ID());
				pd.put("DOne", user.getUSERNAME());
				pd.put("FIxPlan", pd.getString("FIxPlan"));
				pd.put("Cause", pd.getString("Cause"));
				pd.put("Resolvent", pd.getString("Resolvent"));
				pd.put("Remark", pd.getString("CONTENT"));	
				
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					for(int i=0;i<ArrayDATA_IDS.length;i++)
					{
						if(ArrayDATA_IDS[i]!="")
						{
							pd.put("Id", ArrayDATA_IDS[i]);
							softissueService.updatepm(pd);
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
					 
					softissueService.deleteAll(ArrayDATA_IDS);
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
			String PId = pd.getString("PId");
			logBefore(logger, "project excel:"+PId.toString());
			pd.put("PId", PId);
			logBefore(logger, "project excel pd:"+pd.toString());
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			 
			titles.add("编号");	//1
			titles.add("标题");	//2
			titles.add("测试项");	//3
			titles.add("问题类型");	//4
			titles.add("严重程度");	//5 
			titles.add("测试版本");	//5 
			titles.add("测试时间");	//5 
			titles.add("测试人员");	//5 
			titles.add("修复计划");	//5 
			titles.add("研发人员");	//5 
			titles.add("验证时间");	//5 
			titles.add("严重版本");	//5 
			titles.add("验证人员");	//5 
			titles.add("问题状态");	//5 
			titles.add("关闭时间");	//5 
			titles.add("问题描述");	//5 
			titles.add("问题原因");	//5 
			titles.add("解决方案");	//5 
			titles.add("备注");	//5 
			titles.add("项目编号");	//5 
			
			dataMap.put("titles", titles);
			List<PageData> varOList = softissueService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", String.valueOf(varOList.get(i).getInteger("Id")));	//1
				vpd.put("var2", varOList.get(i).getString("Title"));	//2
				vpd.put("var3", varOList.get(i).getString("ItemType"));	//3
				vpd.put("var4", varOList.get(i).getString("SubType"));	//3
				vpd.put("var5", varOList.get(i).getString("Priority"));	//3
				vpd.put("var6", varOList.get(i).getString("TestVer"));	//3 
				vpd.put("var7", String.valueOf(varOList.get(i).getDateTime("TestDate")));	//3
				vpd.put("var8", varOList.get(i).getString("TestOne"));	//3
				vpd.put("var9", String.valueOf(varOList.get(i).getDateTime("FixPlan")));	//3
				vpd.put("var10", varOList.get(i).getString("DOne"));	//3
				vpd.put("var11", String.valueOf(varOList.get(i).getDateTime("RegressionTestingDate")));	//3
				vpd.put("var12", varOList.get(i).getString("RegressionTestingVer"));	//3 
				vpd.put("var13", varOList.get(i).getString("RegressionTestingOne"));	//3
				vpd.put("var14", varOList.get(i).getString("State"));	//3
				vpd.put("var15", String.valueOf(varOList.get(i).getDateTime("CloseDate")));	//3
				vpd.put("var16", varOList.get(i).getString("Content"));	//3
				vpd.put("var17", varOList.get(i).getString("Cause"));	//3
				vpd.put("var18", varOList.get(i).getString("Resolvent"));	//3
				vpd.put("var19", varOList.get(i).getString("Remark"));	//3  
				vpd.put("var20", varOList.get(i).getString("PId"));	//5 
				 
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
