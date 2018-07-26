package com.lsy.ritu.controller.system.login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lsy.ritu.controller.base.BaseController;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.entity.system.Menu;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.entity.system.User;
import com.lsy.ritu.service.system.menu.MenuService;
import com.lsy.ritu.service.system.project.NaviProjectService;
import com.lsy.ritu.service.system.project.ProjectPlanService;
import com.lsy.ritu.service.system.project.SoftIssueService;
import com.lsy.ritu.service.system.project.TongJiService;
import com.lsy.ritu.service.system.role.RoleService;
import com.lsy.ritu.service.system.user.UserService;
import com.lsy.ritu.util.AppUtil;
import com.lsy.ritu.util.Const;
import com.lsy.ritu.util.DateUtil;
import com.lsy.ritu.util.PageData;
import com.lsy.ritu.util.RightsHelper;
import com.lsy.ritu.util.Tools;
/*
 * 总入口
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name="userService")
	private UserService userService;
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="projectplanService")
	private ProjectPlanService projectplanService;
	
	@Resource(name="softissueService")
	private SoftIssueService softissueService;
	
	@Resource(name="tongjiService")
	private TongJiService tongjiService;
	
	
	String[] strColor={"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};
	
	
	
	/**
	 * 获取登录用户的IP
	 * @throws Exception 
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("system/admin/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		//qq313596790fh   lsy843855003
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("lsy843855003", "").replaceAll("lsy843855003", "").split(",lsy,");
		
		if(null != KEYDATA && KEYDATA.length == 3){
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			
			String code = KEYDATA[2];
			if(null == code || "".equals(code)){
				errInfo = "nullcode"; //验证码为空
			}else{
				String USERNAME = KEYDATA[0];
				String PASSWORD  = KEYDATA[1];
				pd.put("USERNAME", USERNAME);
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					pd.put("PASSWORD", passwd);
					pd = userService.getUserByNameAndPwd(pd);
					if(pd != null){
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						session.setAttribute(Const.SESSION_USER, user);
						session.removeAttribute(Const.SESSION_SECURITY_CODE);
						logBefore(logger, "Login user:"+user.toString());
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    try { 
					        subject.login(token); 
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					    
					}else{
						errInfo = "usererror"; 				//用户名或密码有误
					}
				}else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 访问系统首页
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//pd.put("IS_FRONTPAGE", "1");
			//logBefore(logger, "Login IS_FRONTPAGE:"+pd.getString("IS_FRONTPAGE"));
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
				logBefore(logger, "Login roleRights:"+roleRights.toString());
				List<Menu> allmenuList = new ArrayList<Menu>();
				
				if(null == session.getAttribute(Const.SESSION_allmenuList)){
					//pd.put("IS_FRONTPAGE", "1");
					allmenuList = menuService.listAllMenu();
					//pd.put("IS_FRONTPAGE", "1");
					//allmenuList = menuService.listAllMenuByIsFrontPage(pd);
					//logBefore(logger, "Login allmenuList:"+allmenuList.toString());
					if(Tools.notEmpty(roleRights)){
						for(Menu menu : allmenuList){
							menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
							if(menu.isHasMenu()){
								List<Menu> subMenuList = menu.getSubMenu();
								for(Menu sub : subMenuList){
									sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
								}
							}
						}
					}
					session.setAttribute(Const.SESSION_allmenuList, allmenuList);			//菜单权限放入session中
				}else{
					allmenuList = (List<Menu>)session.getAttribute(Const.SESSION_allmenuList);
				}
				
				//切换菜单=====
				List<Menu> menuList = new ArrayList<Menu>();
				//if(null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(pd.getString("changeMenu")))){
				if(null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(changeMenu))){
					List<Menu> menuList1 = new ArrayList<Menu>();
					List<Menu> menuList2 = new ArrayList<Menu>();
					
					//拆分菜单
					for(int i=0;i<allmenuList.size();i++){
						Menu menu = allmenuList.get(i);
						if("1".equals(menu.getMENU_TYPE())){
							menuList1.add(menu);
						}else{
							menuList2.add(menu);
						}
					}
					
					session.removeAttribute(Const.SESSION_menuList);
					if("2".equals(session.getAttribute("changeMenu"))){
						session.setAttribute(Const.SESSION_menuList, menuList1);
						session.removeAttribute("changeMenu");
						session.setAttribute("changeMenu", "1");
						menuList = menuList1;
					}else{
						session.setAttribute(Const.SESSION_menuList, menuList2);
						session.removeAttribute("changeMenu");
						session.setAttribute("changeMenu", "2");
						menuList = menuList2;
					}
				}else{
					menuList = (List<Menu>)session.getAttribute(Const.SESSION_menuList);
				}
				//切换菜单=====
				
				if(null == session.getAttribute(Const.SESSION_QX)){
					session.setAttribute(Const.SESSION_QX, this.getUQX(session));	//按钮权限放到session中
				}
				
				//FusionCharts 报表
			 	String strXML = "<graph caption='前12个月订单销量柱状图' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'><set name='2013-05' value='4' color='AFD8F8'/><set name='2013-04' value='0' color='AFD8F8'/><set name='2013-03' value='0' color='AFD8F8'/><set name='2013-02' value='0' color='AFD8F8'/><set name='2013-01' value='0' color='AFD8F8'/><set name='2012-01' value='0' color='AFD8F8'/><set name='2012-11' value='0' color='AFD8F8'/><set name='2012-10' value='0' color='AFD8F8'/><set name='2012-09' value='0' color='AFD8F8'/><set name='2012-08' value='0' color='AFD8F8'/><set name='2012-07' value='0' color='AFD8F8'/><set name='2012-06' value='0' color='AFD8F8'/></graph>" ;
			 	mv.addObject("strXML", strXML);
			 	//FusionCharts 报表
			 	
				mv.setViewName("system/admin/index");
				mv.addObject("user", user);
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("system/admin/login");//session失效后跳转登录页面
			}
			
			
		} catch(Exception e){
			mv.setViewName("system/admin/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/admin/tab";
	}
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/pmtab")
	public ModelAndView pmtab(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String Id = pd.getString("Id");
		logBefore(logger, "pmtab:"+Id);
		//return "system/admin/pmtab?PId="+Pno;
		  
		pd.put("Id", Id); 
		mv.setViewName("system/admin/pmtab");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage(Page page){
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//String Id = pd.getString("Id");
		//logBefore(logger, "pmtab:"+Id);
		try{
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
		
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //java.util.Date now = new Date();
	    String year=  DateUtil.getYear();
	    logBefore(logger, "year:"+year  );
	    pd.put("year", year);
		page.setPd(pd);
		
		
		//按本年状态
		List<PageData>	stateList = tongjiService.findPStateForNaviproject(page) ;	//列出product列表 
		String strState = "<graph caption='本年项目状态统计表' xAxisName='项目状态' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
		for(int i=0;i<stateList.size();i++)
		{
			PageData str= stateList.get(i);
			 //strMonth += "<set name='"+str.getInteger("month")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";
			strState += "<set name='"+str.getString("PState")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";

		}
		 
		strState += "</graph>";

		//按月
		List<PageData>	monthList = tongjiService.findMonthForNaviproject(page) ;	//列出product列表 
		String strMonth = "<graph caption='本年项目按月统计表' xAxisName='月份' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
		for(int i=0;i<monthList.size();i++)
		{
			PageData str= monthList.get(i);
			//strMonth += "<set name='"+str.getString("State")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";
			strMonth += "<set name='"+str.getInteger("month")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";

		}
			 
		//for(PageData str : monthList){
	           // System.out.println(str);
				//logBefore(logger, "project:"+str+":"+str.getInteger("month"));
				//strMonth += "<set name='"+str.getInteger("month")+"' value='"+str.getLong("count").toString()+"' color='AFD8F8'/>";

	       // }
		strMonth += "</graph>";
		
		//本月
		List<PageData>	curmonthList = tongjiService.findCurMonthForNaviproject(page) ;	//列出product列表 
		String strCurMonth = "<graph caption='本月项目统计表' xAxisName='月份' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
		for(int i=0;i<curmonthList.size();i++)
		{
			PageData str= curmonthList.get(i);
			strCurMonth += "<set name='"+str.getString("PState")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";
  
		}
		//for(PageData str : curmonthList){
	           // System.out.println(str);
				//logBefore(logger, "project:"+str+":"+str.getString("PState"));
				//strCurMonth += "<set name='"+str.getString("PState")+"' value='"+str.getLong("count").toString()+"' color='AFD8F8'/>";

	       // }
		strCurMonth += "</graph>";
		
		//按年统计
		List<PageData>	yearList = tongjiService.findYearForNaviproject(page) ;	//列出product列表 
		String strYear = "<graph caption='项目按年统计表' xAxisName='年' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
		for(int i=0;i<yearList.size();i++)
		{
			PageData str= yearList.get(i); 
			strYear += "<set name='"+str.getInteger("year")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";

		}
		//for(PageData str : yearList){
			//System.out.println(str);
			//logBefore(logger, "project:"+str+":"+str.getInteger("year"));
			//strYear += "<set name='"+str.getInteger("year")+"' value='"+str.getLong("count").toString()+"' color='AFD8F8'/>";

		//}
		strYear += "</graph>";
		
		pd.put("strState", strState); 
		pd.put("strMonth", strMonth); 
		pd.put("strCurMonth", strCurMonth); 
		pd.put("strYear", strYear); 
		mv.setViewName("system/admin/default");
		mv.addObject("stateList", stateList);
		mv.addObject("monthList", monthList);
		mv.addObject("curmonthList", curmonthList);
		mv.addObject("yearList", yearList);
		mv.addObject("pd",pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
		//return "system/admin/default";
	}
	
	
	@Resource(name="naviprojectService")
	private NaviProjectService naviprojectService;
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/pminfo0")
	public ModelAndView defaultPminfoPage(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try { 
		String Id = pd.getString("Id");
		logBefore(logger, "pminfo0:"+Id);
		pd.put("ID", Id); 
		pd = naviprojectService.findById(pd);	//根据ID读取
		
		 
		pd.put("PId", pd.getString("PNo")); 
		page.setPd(pd);
		
		
		
		//按需求状态
		List<PageData>	requirestateList = tongjiService.findStateForRequire(page) ;	//列出product列表 
		String strRequireState = "<graph caption='项目需求状态表' xAxisName='需求状态' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
		 
		for(int i=0;i<requirestateList.size();i++)
		{
			PageData str= requirestateList.get(i);
			strRequireState += "<set name='"+str.getString("State")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";
			
		}
		strRequireState += "</graph>";
		//按问题状态
		List<PageData>	issuestateList = tongjiService.findStateForSoftIssue(page) ;	//列出product列表 
		String strIssueState = "<graph caption='项目问题状态表' xAxisName='问题状态' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
		for(int i=0;i<issuestateList.size();i++)
		{
			PageData str= issuestateList.get(i);
			strIssueState += "<set name='"+str.getString("State")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[i]+"'/>";
	
		}
			 
		strIssueState += "</graph>";
				
		/*
		for(PageData str : requirestateList){
			    System.out.println(str);
				logBefore(logger, "project:"+str+":"+str.getString("State"));
				 
				strRequireState += "<set name='"+str.getString("State")+"' value='"+str.getLong("count").toString()+"' color='"+strColor[0]+"'/>";

			}
		strRequireState += "</graph>";
		
		//按问题状态
		List<PageData>	issuestateList = tongjiService.findStateForSoftIssue(page) ;	//列出product列表 
		String strIssueState = "<graph caption='项目问题状态表' xAxisName='问题状态' yAxisName='数量' decimalPrecision='0' formatNumberScale='0'>";
 
		for(PageData str : issuestateList){
	            System.out.println(str);
				logBefore(logger, "project:"+str+":"+str.getString("State"));
				strIssueState += "<set name='"+str.getString("State")+"' value='"+str.getLong("count").toString()+"' color='AFD8F8'/>";

	        }
		strIssueState += "</graph>";
		*/
		
		List<PageData>	planList = projectplanService.list(page);	//列出plan列表
		logBefore(logger, "pminfo0:"+planList.toString());
		List<PageData>	issueList = softissueService.list(page);	//列出rissue列表
		mv.setViewName("system/project/naviproject_info_wolcome");
		mv.addObject("pd",pd);
		mv.addObject("strRequireState",strRequireState);
		mv.addObject("strIssueState",strIssueState);
		mv.addObject("planList",planList);
		mv.addObject("issueList",issueList);
		mv.addObject("requirestateList",requirestateList);
		mv.addObject("issuestateList",issuestateList);
		
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
		//return "system/project/naviproject_info2";
	}
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_allmenuList);
		session.removeAttribute(Const.SESSION_menuList);
		session.removeAttribute(Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		
		pd = this.getPageData();
		String  msg = pd.getString("msg");
		pd.put("msg", msg);
		
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("system/admin/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 获取用户权限
	 */
	public Map<String, String> getUQX(Session session){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
			pd.put(Const.SESSION_USERNAME, USERNAME);
			String ROLE_ID = userService.findByUId(pd).get("ROLE_ID").toString();
			
			pd.put("ROLE_ID", ROLE_ID);
			
			PageData pd2 = new PageData();
			pd2.put(Const.SESSION_USERNAME, USERNAME);
			pd2.put("ROLE_ID", ROLE_ID);
			
			pd = roleService.findObjectById(pd);																
				
			pd2 = roleService.findGLbyrid(pd2);
			if(null != pd2){
				map.put("FX_QX", pd2.get("FX_QX").toString());
				map.put("FW_QX", pd2.get("FW_QX").toString());
				map.put("QX1", pd2.get("QX1").toString());
				map.put("QX2", pd2.get("QX2").toString());
				map.put("QX3", pd2.get("QX3").toString());
				map.put("QX4", pd2.get("QX4").toString());
				map.put("QX5", pd2.get("QX5").toString());
				map.put("QX6", pd2.get("QX6").toString());
				map.put("QX7", pd2.get("QX7").toString());
				map.put("QX8", pd2.get("QX8").toString());
				map.put("QX9", pd2.get("QX9").toString());
			
				pd2.put("ROLE_ID", ROLE_ID);
				pd2 = roleService.findYHbyrid(pd2);
				map.put("C1", pd2.get("C1").toString());
				map.put("C2", pd2.get("C2").toString());
				map.put("C3", pd2.get("C3").toString());
				map.put("C4", pd2.get("C4").toString());
				map.put("Q1", pd2.get("Q1").toString());
				map.put("Q2", pd2.get("Q2").toString());
				map.put("Q3", pd2.get("Q3").toString());
				map.put("Q4", pd2.get("Q4").toString());
				map.put("Q5", pd2.get("Q5").toString());
				map.put("Q6", pd2.get("Q6").toString());
				map.put("Q7", pd2.get("Q7").toString());
				map.put("Q8", pd2.get("Q8").toString());
				map.put("Q9", pd2.get("Q9").toString());
			}
			
			map.put("adds", pd.getString("ADD_QX"));
			map.put("dels", pd.getString("DEL_QX"));
			map.put("edits", pd.getString("EDIT_QX"));
			map.put("chas", pd.getString("CHA_QX"));
			map.put("ups", pd.getString("UP_QX"));
			map.put("downs", pd.getString("DOWN_QX"));
			map.put("verifys", pd.getString("VERIFY_QX"));
			map.put("replys", pd.getString("REPLY_QX"));
			map.put("confirms", pd.getString("CONFIRM_QX"));
			
			//System.out.println(map);
			
			this.getRemortIP(USERNAME);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
}
