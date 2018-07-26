<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

	<!-- jsp文件头和头部 -->
	<meta charset="utf-8" />
	<title>PM SYSTEM</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<!-- page specific plugin styles -->
	<!-- 下拉框-->
	<link rel="stylesheet" href="static/css/chosen.css" />
	<!-- ace styles -->
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	
	<!-- 即时通讯 -->
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/css/websocket.css" />
	<script type="text/javascript" src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	<script type="text/javascript" src="plugins/websocketInstantMsg/websocket.js"></script>
	<!-- 即时通讯 -->
	
</head>
<body>

	<!-- 页面顶部¨ -->
	<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand"><small><i class="icon-leaf"></i> PM SYSTEM</small> </a>
			  
			  <ul class="nav ace-nav pull-right">
			  
			  
					<li class="grey">
						<a href="javascript:alert('预留功能,待开发');" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-tasks"></i>
							<span class="badge">1</span>
						</a>
						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
							<li class="nav-header">
								<i class="icon-ok"></i> 4 任务完成
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">软件更新</span>
										<span class="pull-right">65%</span>
									</div>
									<div class="progress progress-mini"><div class="bar" style="width:65%"></div></div>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">软件更新</span>
										<span class="pull-right">35%</span>
									</div>
									<div class="progress progress-mini progress-danger"><div class="bar" style="width:35%"></div></div>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">运行测试</span>
										<span class="pull-right">15%</span>
									</div>
									<div class="progress progress-mini progress-warning"><div class="bar" style="width:15%"></div></div>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">Bug 修复</span>
										<span class="pull-right">90%</span>
									</div>
									<div class="progress progress-mini progress-success progress-striped active"><div class="bar" style="width:90%"></div></div>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									查看任务明细
									<i class="icon-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
					
					
					
					<li class="green">
						<a href="javascript:alert('预留功能,待开发');" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-envelope-alt icon-animated-vertical icon-only"></i>
							<span class="badge badge-success">1</span>
						</a>
						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
							<li class="nav-header">
								<i class="icon-envelope"></i> 5 条信件
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<img alt="Alex's Avatar" class="msg-photo" src="static/avatars/avatar.png" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">张三:</span>
											你好，我们在哪里吃饭? ...
										</span>
										<span class="msg-time">
											<i class="icon-time"></i> <span>1个月以前</span>
										</span>
									</span>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<img alt="Susan's Avatar" class="msg-photo" src="static/avatars/avatar3.png" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">李四:</span>
											你在哪上班? ...
										</span>
										<span class="msg-time">
											<i class="icon-time"></i> <span>20分钟前</span>
										</span>
									</span>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<img alt="Bob's Avatar" class="msg-photo" src="static/avatars/avatar4.png" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">王五:</span>
											你好，我对你很感兴趣 ...
										</span>
										<span class="msg-time">
											<i class="icon-time"></i> <span>下午 3:15 </span>
										</span>
									</span>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									查看所有信件
									<i class="icon-arrow-right"></i>
								</a>
							</li>									
	
						</ul>
					</li>
					
					
					<li class="purple" onclick="creatw();">
						<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-bell-alt icon-animated-bell icon-only"></i>
						</a>
					</li>
					
					
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:;" data-toggle="dropdown">
							<img alt="FH" src="static/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info">
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editUserH();" style="cursor:pointer;"><i class="icon-user"></i> 修改资料</a></li>
							<li id="systemset"><a onclick="editSys();" style="cursor:pointer;"><i class="icon-cog"></i> 系统设置</a></li>
							<li id="productCode"><a onclick="productCode();" style="cursor:pointer;"><i class="icon-cogs"></i> 代码生成</a></li>
							<li class="divider"></li>
							<li><a href="logout"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
	
	
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/pmhead.js"></script>

	<div id="websocket_button"></div>
	<div class="container-fluid" id="main-container">
		<a href="#" id="menu-toggler"><span></span></a>
		<!-- menu toggler -->

		<!-- 左侧菜单 -->
		<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
		<div id="sidebar" class="menu-min">
  
				<ul class="nav nav-list">

					<li class="active" id="fhindex">
					  <a href="javascript:void(0);"><i class="icon-dashboard"></i><span>项目详情</span></a>
					</li>

		 
				<li id="lm0001">
					  <a style="cursor:pointer;" class="dropdown-toggle" >
						<i class="icon-desktop"></i>
						<span>项目详情管理</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu">
							<c:forEach items="${menuList}" var="menu">
								<c:if test="${menu.hasMenu}">
								<c:choose>
									<c:when test="${not empty menu.MENU_URL}">
									<li id="z${menu.MENU_ID }">
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z${menu.MENU_ID }','lm${menu.MENU_ID }','${menu.MENU_NAME }','${menu.MENU_URL }?PId=${pd.PNo}')"><i class="icon-double-angle-right"></i>${menu.MENU_NAME }</a></li>
									</c:when>
									<c:otherwise>
									<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${menu.MENU_NAME }</a></li>
									</c:otherwise>
								</c:choose>
								</c:if>
							</c:forEach>
				  		</ul>
				</li>
		 
<!-- 
                <li id="lm1001">
					  <a style="cursor:pointer;" class="dropdown-toggle" >
						<i class="icon-desktop"></i>
						<span>项目详情管理</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu">
						 
									
										<c:if test="${QX.cha == 1 }">
										<li id="z1001">
					<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1001','lm1001','项目详情','project/pminfo2.do?ID=${pd.Id}')"><i class="icon-double-angle-right"></i>项目详情</a>
							</li>		
					</c:if>
					 <c:if test="${QX.cha == 1 }">
									 <li id="z1002">
									
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1002','lm1002','项目需求','require/list.do?PId=${pd.PNo}')"><i class="icon-double-angle-right"></i>项目需求</a>
									
									</li></c:if>
				  		 <c:if test="${QX.cha == 1 }">
				  		 <li id="z1003"> 
				  		
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1003','lm1003','项目问题','problem/listall.do?PNo=${pd.PNo}')"><i class="icon-double-angle-right"></i>项目问题</a>
									</li></c:if>
				  		 <c:if test="${QX.cha == 1 }"><li id="z1004"> 
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1004','lm1004','项目计划','projectplan/list.do?PId=${pd.PNo}')"><i class="icon-double-angle-right"></i>项目计划</a>
								</li>	</c:if>
				  		 <c:if test="${QX.cha == 1 }"> <li id="z1005">
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1005','lm1005','版本管理','version/list.do?PId=${pd.PNo}')"><i class="icon-double-angle-right"></i>版本管理</a>
									</li></c:if>
									 <c:if test="${QX.cha == 1 }"> <li id="z1006">
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1006','lm1006','项目文件','filemanage/list.do?PId=${pd.PNo}')"><i class="icon-double-angle-right"></i>项目文件</a>
									</li></c:if>
									<c:if test="${QX.cha == 1 }"> <li id="z1007"> 
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z1007','lm1007','项目成员','filemanage/list.do?PId=${pd.PNo}')"><i class="icon-double-angle-right"></i>项目成员</a>
									</li></c:if>
				  		</ul>
				</li>
  -->
			 
				</ul><!--/.nav-list-->

				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>

			</div><!--/#sidebar-->



		<div id="main-content" class="clearfix">

			<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999999;">
			<div class="commitopacity" id="bkbgjz"></div>
			<div style="padding-left: 70%;padding-top: 1px;">
				<div style="float: left;margin-top: 3px;"><img src="static/images/loadingi.gif" /> </div>
				<div style="margin-top: 5px;"><h4 class="lighter block red">&nbsp;加载中 ...</h4></div>
			</div>
			</div>

			<div>
				<iframe name="mainFrame" id="mainFrame" frameborder="0" src="pmtab.do?Id=${pd.Id}" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</div>

			<!-- 换肤 -->
			<div id="ace-settings-container">
				<div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
					<i class="icon-cog"></i>
				</div>
				<div id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hidden">
								<option data-class="default" value="#438EB9"
									<c:if test="${user.SKIN =='default' }">selected</c:if>>#438EB9</option>
								<option data-class="skin-1" value="#222A2D"
									<c:if test="${user.SKIN =='skin-1' }">selected</c:if>>#222A2D</option>
								<option data-class="skin-2" value="#C6487E"
									<c:if test="${user.SKIN =='skin-2' }">selected</c:if>>#C6487E</option>
								<option data-class="skin-3" value="#D0D0D0"
									<c:if test="${user.SKIN =='skin-3' }">selected</c:if>>#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>
					<div>
						<label><input type='checkbox' name='menusf' id="menusf"
							onclick="menusf();" /><span class="lbl" style="padding-top: 5px;">菜单缩放</span></label>
					</div>
				</div>
			</div>
			<!--/#ace-settings-container-->

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		
		<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="static/js/myjs/menusf.js"></script>
		
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/index.js"></script>
</body>
</html>
