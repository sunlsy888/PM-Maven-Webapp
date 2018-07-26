package com.lsy.ritu.util;

import com.lsy.ritu.service.system.menu.MenuService;
import com.lsy.ritu.service.system.role.RoleService;
import com.lsy.ritu.service.system.user.UserService;



/**
 * @author lsy
 * 获取Spring容器中的service bean
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static UserService getUserService(){
		return (UserService) getService("userService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
}
