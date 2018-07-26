<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<meta charset="utf-8" />
	<title>${pd.PName } 项目详情</title>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <!-- basic styles -->
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="static/css/font-awesome-ie7.min.css" />
		<![endif]-->
		<!-- page specific plugin styles -->
		
		<link rel="stylesheet" href="static/css/chosen.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" /> 
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" /> 
		<!--[if lt IE 9]>
		  <link rel="stylesheet" href="static/css/ace-ie.min.css" />
		<![endif]-->
		
		<!-- basic styles -->
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts 

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
-->
		<!-- ace styles -->



		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
  </head>
  
  <body>

 <div class="container-fluid" id="main-container">

	<div id="breadcrumbs">

						<ul class="breadcrumb">

							<li><i class="icon-home"></i> <a href="#">项目管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>

							<li><a href="#">${pd.PName }</a> <span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">项目详情</li>
						</ul><!--.breadcrumb-->
 
</div><!--#breadcrumbs-->
<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			 
			<form action="project/pminfo.do" method="post" name="PmForm" id="PmForm">   
		 <input name="Id" id="Id"  type="hidden"  value="${pd.ID }"/>
<input name="PNo" id="PNo"  type="hidden"  value="${pd.PNo }"/>
	 
			 
		 	<div id="fuelux-wizard" class="row-fluid hidden">
					  <ul class="wizard-steps">
					  <c:forEach items="${planList}" var="plan">
					  
							<li data-target="#step${plan.Plan_Id }" <c:if test="${plan.State!='open'}"> class="active" </c:if> ><span class="step">${plan.Title }</span> <span class="title">${plan.Title  }[${plan.State }]</span></li>
							
						</c:forEach>
					<!--  <li data-target="#step1" class="active"><span class="step">1</span> <span class="title">Validation states</span></li>
						<li data-target="#step2"><span class="step">2</span> <span class="title">Alerts</span></li>
						<li data-target="#step3"><span class="step">3</span> <span class="title">Payment Info</span></li>
						<li data-target="#step4"><span class="step">4</span> <span class="title">Other Info</span></li>
					  -->	
					  
					 
					  </ul>
					</div>
					
					<hr />
		<!--   <c:forTokens var="item" items="${planList}" delims="}" varStatus="status" begin="1" >
       ${status.index}
       <c:out value="${status.index}" />  
        
        <c:if test="${status.last}"> 
           <br>总共被分为<c:out value="${status.count}" />段.  
        </c:if>  
         
        <c:if test="${status.first}"> 
           <c:out value="第一次迭代！"/> 
        </c:if>  
        <br />
    </c:forTokens>  -->
    
 
 <div class="row-fluid">

					<div class="space-6"></div>
					<div class="row-fluid">
						<%
							String strXML = "";

							strXML += "<graph caption='需求状态表' xAxisName='状态' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
							strXML += "<set name='已完成' value='22' color='AFD8F8'/>";
							strXML += "<set name='开发中' value='12' color='F6BD0F'/>";
							strXML += "<set name='确认中' value='5' color='8BBA00'/>";
							strXML += "<set name='评估中' value='3' color='FF8E46'/>"; 
							strXML += "</graph>";
							
							String strXML2 = "";

							strXML2 += "<graph caption='问题状态表' xAxisName='状态' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
							strXML2 += "<set name='已修复' value='22' color='AFD8F8'/>";
							strXML2 += "<set name='修复中' value='12' color='F6BD0F'/>";
							strXML2 += "<set name='确认中' value='5' color='8BBA00'/>";
							strXML2 += "<set name='调查中' value='3' color='FF8E46'/>"; 
							strXML2 += "<set name='复现中' value='6' color='D64646'/>";
							strXML2 += "<set name='验证中' value='32' color='8E468E'/>";
							strXML2 += "<set name='已关闭' value='322' color='588526'/>"; 
							strXML2 += "</graph>";
							//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
						%>

	 


						<!-- 柱状图 -->
						<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
									<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Pie3D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${strRequireState }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td> 
									</tr>
								</table>
							</div>
							<div style="float:right;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Column3D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${strIssueState }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>



 </div></div>
    
 	 <div class="row-fluid">
 <div class="vspace"></div>
 <div class="span12">
	 <div class="widget-box transparent">
			<div class="widget-header widget-header-flat">
				<h4 class="lighter"><i class="icon-star orange"></i>Popular Domains</h4>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
			
			<div class="widget-body">
			 <div class="widget-main no-padding">
			  <table id="table_bug_report" class="table table-bordered table-striped">
				<thead>
					<tr>
						<th><i class="icon-caret-right blue"></i>name</th>
						<th><i class="icon-caret-right blue"></i>price</th>
						<th class="hidden-phone"><i class="icon-caret-right blue"></i>status</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="">internet.com</td>
						<td>
							<small><s class="red">$29.99</s></small>
							<b class="green">$19.99</b>
						</td>
						<td class="hidden-phone"><span class="label label-info arrowed-right arrowed-in">on sale</span></td>
					</tr>
					
					<tr>
						<td class="">online.com</td>
						<td>
							<b class="blue">$16.45</b>
						</td>
						<td class="hidden-phone"><span class="label label-success arrowed-in arrowed-in-right">approved</span></td>
					</tr>
					
					<tr>
						<td class="">newnet.com</td>
						<td>
							<b class="blue">$15.00</b>
						</td>
						<td class="hidden-phone"><span class="label label-important arrowed">pending</span></td>
					</tr>
					<tr>
						<td class="">web.com</td>
						<td>
							<small><s class="red">$19.95</s></small>
							<b class="green">$14.99</b>
						</td>
						<td class="hidden-phone"><span class="label arrowed"><s>out of stock</s></span></td>
					</tr>
					
					<tr>
						<td class="">domain.com</td>
						<td>
							<b class="blue">$12.00</b>
						</td>
						<td class="hidden-phone"><span class="label label-warning arrowed arrowed-right">SOLD</span></td>
					</tr>
				</tbody>
			  </table>
			 </div><!--/widget-main-->
			</div><!--/widget-body-->
		</div><!--/widget-box-->
 </div><!--/span-->
 
</div><!--/row-->
<div class="hr hr32 hr-dotted"></div>
  

<div class="row-fluid">
 <div class="span6">
	<div class="widget-box transparent">
		
		<div class="widget-header">
			<h4 class="lighter smaller"><i class="icon-rss orange"></i>RECENT</h4>
			<div class="widget-toolbar no-border">
				<ul class="nav nav-tabs" id="recent-tab">
					<li class="active"><a data-toggle="tab" href="#task-tab">Tasks</a></li>
					<li><a data-toggle="tab" href="#member-tab">Members</a></li>
					<li><a data-toggle="tab" href="#comment-tab">Comments</a></li>
				</ul>
			</div>
		</div>
		
		<div class="widget-body">
		 <div class="widget-main padding-5">
			<div class="tab-content padding-8">
				<div id="task-tab" class="tab-pane active">
					<h4 class="smaller lighter green"><i class="icon-list"></i> Sortable Lists</h4>
					<ul id="tasks" class="item-list">
						<li class="item-orange clearfix">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Answering customer questions</span></label>
							<div class="pull-right easy-pie-chart percentage" data-size="30" data-color="#ECCB71" data-percent="42">
								<span class="percent">42</span>%
							</div>
						</li>
						<li class="item-red clearfix">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Fixing bugs</span></label>
							<div class="pull-right">
								<div class="btn-group">
									<button class="btn btn-mini btn-info"><i class="icon-edit"></i></button>
									<button class="btn btn-mini btn-danger "><i class="icon-trash"></i></button>
									<button class="btn btn-mini btn-yellow"><i class="icon-flag"></i></button>
								</div>
							</div>
						</li>
						<li class="item-default clearfix">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Adding new features</span></label>
							<div class="inline pull-right position-relative">
								<button class="btn btn-minier bigger btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-angle-down icon-only"></i></button>
								<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-closer">
									<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Mark&nbsp;as&nbsp;done" data-placement="left"><span class="green"><i class="icon-ok"></i></span></a></li>
									<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span></a></li>
								</ul>
							</div>
						</li>
						<li class="item-blue">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Upgrading scripts used in template</span></label>
						</li>
						<li class="item-grey">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Adding new skins</span></label>
						</li>
						<li class="item-green">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Updating server software up</span></label>
						</li>
						<li class="item-pink">
							<label class="inline"><input type="checkbox" /><span class="lbl"> Cleaning up</span></label>
						</li>
					</ul>
				</div>
				
				
				
				<div id="member-tab" class="tab-pane">
					<div class="clearfix">
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Bob's avatar" src="static/avatars/user.jpg" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Bob Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">20 min</span></div>
								<div>
									<span class="label label-warning">pending</span>
									<div class="inline position-relative">
										<button class="btn btn-minier bigger btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-angle-down icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
											<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Approve" data-placement="right"><span class="green"><i class="icon-ok"></i></span></a></li>
											<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject" data-placement="right"><span class="orange"><i class="icon-remove"></i></span> </a></li>
											<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="right"><span class="red"><i class="icon-trash"></i></span> </a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
			
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Joe's Avatar" src="static/avatars/avatar2.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Joe Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">1 hour</span></div>
								<div>
									<span class="label label-warning">pending</span>
									<div class="inline position-relative">
										<button class="btn btn-minier bigger btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-angle-down icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
											<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Approve" data-placement="left"><span class="green"><i class="icon-ok"></i></span></a></li>
											<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject" data-placement="left"><span class="orange"><i class="icon-remove"></i></span> </a></li>
											<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
			  
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Jim's Avatar" src="static/avatars/avatar.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Jim Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">2 hour</span></div>
								<div>
									<span class="label label-warning">pending</span>
									<div class="inline position-relative">
										<button class="btn btn-minier bigger btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-angle-down icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
											<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Approve" data-placement="right"><span class="green"><i class="icon-ok"></i></span></a></li>
											<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject" data-placement="right"><span class="orange"><i class="icon-remove"></i></span> </a></li>
											<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="right"><span class="red"><i class="icon-trash"></i></span> </a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Alex's Avatar" src="static/avatars/avatar2.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Alex Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">3 hour</span></div>
								<div class="label label-important">blocked</div>
							</div>
						</div>
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Bob's Avatar" src="static/avatars/avatar2.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Bob Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">6 hour</span> </div>
								<div class="label label-success arrowed-in">approved</div>
							</div>
						</div>
						
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Susan's Avatar" src="static/avatars/avatar3.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Susan</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">yesterday</span></div>
								<div class="label label-success arrowed-in">approved</div>
							</div>
						</div>
			  
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Phil's Avatar" src="static/avatars/avatar4.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Phil Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">2 days ago</span></div>
								<div class="label label-info arrowed-in  arrowed-in-right">online</div>
							</div>
						</div>
						
						<div class="itemdiv memberdiv">
							<div class="user">
								<img alt="Alexa's Avatar" src="static/avatars/avatar1.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Alexa Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">3 days ago</span></div>
								<div class="label label-success arrowed-in">approved</div>
							</div>
						</div>
					</div>
					<div class="center">
						<i class="icon-group icon-2x green"></i> &nbsp; <a href="#">See all members &nbsp; <i class="icon-arrow-right"></i></a>
					</div>
					<div class="hr hr-double hr8"></div>
				</div><!-- member-tab -->
				
				
				
				<div id="comment-tab" class="tab-pane">
					<div class="comments">
						<div class="itemdiv commentdiv">
							<div class="user">
								<img alt="Bob's Avatar" src="static/avatars/avatar.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Bob Doe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="green">6 min</span></div>
								<div class="text">
									<i class="icon-quote-left"></i> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
								</div>
							</div>
							
							<div class="tools">
								<div class="inline position-relative">
									<button class="btn btn-minier bigger btn-yellow dropdown-toggle" data-toggle="dropdown"><i class="icon-angle-down icon-only"></i></button>
									<ul class="dropdown-menu dropdown-icon-only dropdown-yellow pull-right dropdown-caret dropdown-close">
										<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Approve" data-placement="left"><span class="green"><i class="icon-ok"></i></span></a></li>
										<li><a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject" data-placement="left"><span class="orange"><i class="icon-remove"></i></span> </a></li>
										<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
									</ul>
								</div>
							</div>
						</div>
						
						
						<div class="itemdiv commentdiv">
							<div class="user">
								<img alt="Jennifer's Avatar" src="static/avatars/avatar1.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Jennifer</a></div>
								<div class="time"><i class="icon-time"></i> <span class="blue">15 min</span></div>
								<div class="text">
									<i class="icon-quote-left"></i> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip; 
								</div>
							</div>
							
							<div class="tools">
								<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-pencil"></i></a>
								<a href="#" class="btn btn-minier btn-danger"><i class="icon-only icon-trash"></i></a>
							</div>
						</div>
						
						
						<div class="itemdiv commentdiv">
							<div class="user">
								<img alt="Joe's Avatar" src="static/avatars/avatar2.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Joe</a></div>
								<div class="time"><i class="icon-time"></i> <span class="orange">22 min</span></div>
								<div class="text">
									<i class="icon-quote-left"></i> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
								</div>
							</div>
							
							<div class="tools">
								<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-pencil"></i></a>
								<a href="#" class="btn btn-minier btn-danger"><i class="icon-only icon-trash"></i></a>
							</div>
						</div>
						
						
						<div class="itemdiv commentdiv">
							<div class="user">
								<img alt="Rita's Avatar" src="static/avatars/avatar3.png" />
							</div>
							
							<div class="body">
								<div class="name"><a href="#">Rita</a></div>
								<div class="time"><i class="icon-time"></i> <span class="red">50 min</span></div>
								<div class="text">
									<i class="icon-quote-left"></i> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
								</div>
							</div>
							
							<div class="tools">
								<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-pencil"></i></a>
								<a href="#" class="btn btn-minier btn-danger"><i class="icon-only icon-trash"></i></a>
							</div>
						</div>
						
					</div>
					
					<div class="hr hr8"></div>
					<div class="center">
							<i class="icon-comments-alt icon-2x green"></i> &nbsp; <a href="#">See all comments &nbsp; <i class="icon-arrow-right"></i></a>
					</div>
					<div class="hr hr-double hr8"></div>
					
				</div>
			</div>
		 </div><!--/widget-main-->
		</div><!--/widget-body-->
		
		
	</div><!--/widget-box-->
 </div><!--/span-->
 
 <div class="span6">
	<div class="widget-box ">
		
		<div class="widget-header">
			<h4 class="lighter smaller"><i class="icon-comment blue"></i>Conversation</h4>
		</div>
		
		<div class="widget-body">
		 <div class="widget-main no-padding">
			
			<div class="dialogs">
				<div class="itemdiv dialogdiv">
					<div class="user">
						<img alt="Alexa's Avatar" src="static/avatars/avatar1.png" />
					</div>
					
					<div class="body">
						<div class="time"><i class="icon-time"></i> <span class="green">4 sec</span></div>
						<div class="name"><a href="#">Alexa</a></div>
						<div class="text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis. </div>
						
						<div class="tools">
							<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-share-alt"></i></a>
						</div>
					</div>
				</div>
				
				
				<div class="itemdiv dialogdiv">
					<div class="user">
						<img alt="John's Avatar" src="static/avatars/avatar.png" />
					</div>
					
					<div class="body">
						<div class="time"><i class="icon-time"></i> <span class="blue">38 sec</span></div>
						<div class="name"><a href="#">John</a></div>
						<div class="text">Raw denim you probably haven't heard of them jean shorts Austin.</div>
						
						<div class="tools">
							<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-share-alt"></i></a>
						</div>
					</div>
				</div>
				
				
				<div class="itemdiv dialogdiv">
					<div class="user">
						<img alt="Bob's avatar" src="static/avatars/user.jpg" />
					</div>
					
					<div class="body">
						<div class="time"><i class="icon-time"></i> <span class="orange">2 min</span></div>
						<div class="name"><a href="#">Bob</a> <span class="label label-info arrowed arrowed-in-right">admin</span></div>
						<div class="text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis. </div>
						
						<div class="tools">
							<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-share-alt"></i></a>
						</div>
					</div>
				</div>
				
				
				<div class="itemdiv dialogdiv">
					<div class="user">
						<img alt="Jim's Avatar" src="static/avatars/avatar4.png" />
					</div>
					
					<div class="body">
						<div class="time"><i class="icon-time"></i> <span class="muted">3 min</span></div>
						<div class="name"><a href="#">Jim</a></div>
						<div class="text">Raw denim you probably haven't heard of them jean shorts Austin.</div>
						
						<div class="tools">
							<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-share-alt"></i></a>
						</div>
					</div>
				</div>
				
				
				<div class="itemdiv dialogdiv">
					<div class="user">
						<img alt="Alexa's Avatar" src="static/avatars/avatar1.png" />
					</div>
					
					<div class="body">
						<div class="time"><i class="icon-time"></i> <span class="green">4 min</span></div>
						<div class="name"><a href="#">Alexa</a></div>
						<div class="text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>
						
						<div class="tools">
							<a href="#" class="btn btn-minier btn-info"><i class="icon-only icon-share-alt"></i></a>
						</div>
					</div>
				</div>
			</div>
			
			<form>
				<div class="form-actions input-append">
					<input placeholder="Type your message here ..." type="text" class="width-75" name="message" />
					<button class="btn btn-small btn-info no-radius" onclick="return false;"><i class="icon-share-alt"></i> <span class="hidden-phone">Send</span></button>
				</div>
			</form>
		
		 </div><!--/widget-main-->
		</div><!--/widget-body-->
		
	</div><!--/widget-box-->
 </div><!--/span-->
</div><!--/row-->

 
<!-- PAGE CONTENT BEGINS HERE  
<div class="left">
	<div class="row-fluid">
	 <div class="span2">项目编号</div>
	 <div class="span4">${pd.PNo }</div>
	 <div class="span2">数据版本</div>
	 <div class="span4">${pd.PDataVer }</div>
	</div>
	<div class="row-fluid">
	 <div class="span2">项目名称</div>
	 <div class="span4">${pd.PName }</div>
	 <div class="span2">项目状态</div>
	 <div class="span4">${pd.PState }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">项目类型</div>
	 <div class="span4">${pd.PType }</div>
	 <div class="span2">项目等级</div>
	 <div class="span4">${pd.PLevel }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">客户名称</div>
	 <div class="span4">${pd.PCustomer }</div>
	 <div class="span2">立项日期</div>
	 <div class="span4">${pd.PDate }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">研发团队</div>
	 <div class="span4">${pd.SoftCompany }</div>
	 <div class="span2">项目来源</div>
	 <div class="span4">${pd.PSource }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">硬件方案</div>
	 <div class="span4">${pd.HardSolution }</div>
	 <div class="span2">产品类型</div>
	 <div class="span4">${pd.ProductType }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">屏幕分辨率</div>
	 <div class="span4">${pd.ScreenResolution }</div>
	 <div class="span2">可用内存</div>
	 <div class="span4">${pd.Memory }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">硬件设备系统</div>
	 <div class="span4">${pd.OperSystem }</div>
	 <div class="span2">存储介质</div>
	 <div class="span4">${pd.Storage }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">端口波特率</div>
	 <div class="span4">${pd.PortRate }</div>
	 <div class="span2">编译环境</div>
	 <div class="span4">${pd.CompilerEnv }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">设备类型</div>
	 <div class="span4">${pd.EquipType }</div>
	 <div class="span2">导航路径</div>
	 <div class="span4">${pd.NaviPath }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">绑定方式</div>
	 <div class="span4">${pd.BindMode }</div>
	 <div class="span2">激活方式</div>
	 <div class="span4">${pd.ActMode }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">CPU主频</div>
	 <div class="span4">${pd.CPUFrequency }</div>
	 <div class="span2">审核状态</div>
	 <div class="span4">${pd.IsVerify }</div>
	</div> 
	<div class="row-fluid">
	 <div class="span2">项目说明</div>
	 <div class="span9">${pd.PRemark }</div> 
	  <div class="span1">  </div>
	</div> 
			</div>
		 -->
	 
		</form>
		
		
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
			<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

	  

	

 

		 
		
 	<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
			
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/fuelux.wizard.js"></script>
		<script type="text/javascript" src="static/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.maskedinput.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
		
			<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
			
				<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="assets/js/jquery.slimscroll.min.js"></script>
		<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="assets/js/jquery.sparkline.min.js"></script>
		<script src="assets/js/flot/jquery.flot.min.js"></script>
		<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="assets/js/flot/jquery.flot.resize.min.js"></script>
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			$('#fuelux-wizard').ace_wizard().show();
			$('#fuelux-wizard2').ace_wizard().show();
			/*
			var $validation = false;
			$('#fuelux-wizard').ace_wizard().on('change' , function(e, info){
				if(info.step == 1 && $validation) {
					if(!$('#validation-form').valid()) return false;
				}
			}).on('finished', function(e) {
				bootbox.dialog("Thank you! Your information was successfully saved!", [{
					"label" : "OK",
					"class" : "btn-small btn-primary",
					}]
				);
			});
			*/
		});
		
		</script>
	<!-- 编辑框-->
		<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
		<!-- 编辑框--> 
		
		<style type="text/css">
		li {list-style-type:none;}
		</style>
		<ul class="navigationTabs">
            <li><a></a></li>
            <li></li>
        </ul>
      <script type="text/javascript">
  	$(top.hangge());
	 
		//新增
		function addProblem(){
			if(confirm("提交新问题:"+basePath)){ }
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="提交新问题";
			 diag.URL = '<%=basePath%>problem/goAdd.do';
			 diag.Width = 480;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				 if('${page.currentPage}' == '0'){
					 top.jzts();
					 setTimeout("self.location=self.location",100);
				 }else{
					 nextPage(${page.currentPage});
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delProblem(Id){
			
			if(confirm("确定要删除?")){ 
				top.jzts();
				var url = "<%=basePath%>problem/delete.do?ID="+Id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					nextPage(${page.currentPage});
				});
			}
		}
		
		//修改
		function editProblem(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = "<%=basePath%>problem/goEdit.do?Id="+Id;
			 diag.Width = 480;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		 
		</script>
		
		<script type="text/javascript">
		
		//全选 （是/否）
		function problemSelectAll(){
			 var checklist = document.getElementsByName ("ids");
			   if(document.getElementById("zcheckbox").checked){
			   for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			 }
		}

		
		
		//批量操作
		function problemMakeAll(msg){
			
			if(confirm(msg)){ 
				
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						alert("您没有选择任何内容!"); 
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>problem/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
			}
		}
		
		//导出excel
		function problemtoExcel(){
			window.location.href='<%=basePath%>problem/excel.do';
		}
		</script>
		
		
		 
		 </form>
  </body>
</html>
