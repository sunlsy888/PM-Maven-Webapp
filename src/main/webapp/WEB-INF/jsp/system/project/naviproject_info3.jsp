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
	<title>${pd.PName }项目详情</title>
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
 
  </head>
  
  <body>
<form action="project/pminfo.do" method="post" name="PmForm" id="PmForm">   
<input name="Id" id="Id"  type="hidden"  value="${pd.ID }"/>
<input name="PNo" id="PNo"  type="hidden"  value="${pd.PNo }"/>
 <div class="container-fluid" id="main-container">
	<div id="breadcrumbs">

						<ul class="breadcrumb">

							<li><i class="icon-home"></i> <a href="#">项目管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>

							<li><a href="#">${pd.PName }</a> <span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">项目详情</li>
						</ul><!--.breadcrumb-->



						<div id="nav-search">

							<form class="form-search">

									<span class="input-icon">
<!-- 
										<input autocomplete="off" id="nav-search-input" type="text" class="input-small search-query" placeholder="Search ..." />
 -->
										<i id="nav-search-icon" class="icon-search"></i>

									</span>

							</form>

						</div><!--#nav-search-->

					</div><!--#breadcrumbs-->

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">
<!--  
	 <div class="page-header position-relative">
							<h1>${pd.PName } <small><i class="icon-double-angle-right"></i> ${pd.PState }</small></h1>
						</div>-->
						<!--/page-header-->
						
						<div class="row-fluid">
<!-- PAGE CONTENT BEGINS HERE -->

<div class="space"></div>
<div class="row-fluid">
 <div class="span12">
	<div class="tabbable">
            <ul class="nav nav-tabs" id="myTab">
              <li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> item details </a></li>
              <li><a data-toggle="tab" href="#requirement">requirement </a></li>
              <li><a data-toggle="tab" href="#issue">Project issue <span class="badge badge-important">4</span></a></li>
              <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">Dropdown <b class="caret"></b></a>
                <ul class="dropdown-menu dropdown-info">
                  <li><a data-toggle="tab" href="#dropdown1">@fat</a></li>
                  <li><a data-toggle="tab" href="#dropdown2">@mdo</a></li>
                </ul>
              </li>
            </ul>
            <div class="tab-content">
			  <div id="home" class="tab-pane in active">
				<div class="row-fluid">
 <div class="span12">
   <div class="tabbable tabs-left">
	<ul class="nav nav-tabs" id="myTab3">
	  <li class="active"><a data-toggle="tab" href="#home3"><i class="pink icon-dashboard bigger-110"></i> Home</a></li>
	  <li><a data-toggle="tab" href="#plan"><i class="blue icon-user bigger-110"></i> PLan</a></li>
	  <li><a data-toggle="tab" href="#dropdown13"><span class="badge badge-success badge-icon"><i class="icon-caret-right"></i></span> More</a></li>
	</ul>
	<div class="tab-content">
	  <div id="home3" class="tab-pane in active">
	 
<!-- PAGE CONTENT BEGINS HERE -->
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
	  </div>
	  <div id="plan" class="tab-pane">
		<div class="row-fluid">
  
 <div class="span12">
	<div class="widget-box ">
		
		<div class="widget-header">
			<h4 class="lighter smaller"><i class="icon-comment blue"></i>Conversation</h4>
		</div>
		
		<div class="widget-body">
		 <div class="widget-main no-padding">
			
			<div class="dialogs">
				<div class="itemdiv dialogdiv">
					<div class="user">
						<img alt="Alexa's Avatar" src="static/avatars/avatar2.png" />
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
						<img alt="John's Avatar" src="static/avatars/avatar2.png" />
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
						<img alt="Jim's Avatar" src="static/avatars/avatar2.png" />
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
						<img alt="Alexa's Avatar" src="static/avatars/avatar2.png" />
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
	  </div>
	  <div id="dropdown13" class="tab-pane">
		${pd.PRemark }
	  </div>
	</div>
  </div>
 </div><!--/span-->
 
 
</div><!--/row--> 
			  </div>
			  <!-- 需求开始 -->
			  <div id="requirement" class="tab-pane">
				<p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid.</p>
			  </div>
			   <!-- 需求结束 -->
			  <!-- 问题开始 -->
			   <div id="issue" class="tab-pane"> 
			
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" onclick="problemSelectAll()">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th> 
						<th>问题编号</th>
						<th>问题标题</th>
						<th>问题类型</th>
						<th>问题等级</th>
						<th>测试版本</th> 
						<th>修复计划</th>
						<th>问题状态</th>
						 
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty softissueList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${softissueList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.Id}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td> 
										 <td>${var.Id}</td>
										<td>
										
										    <c:if test="${QX.cha == 1 }">
											 <a style="cursor:pointer;" title="查看" href="project/pminfo.do?ID=${var.Id}"  target="_blank" class="tooltip-success" > ${var.Title}</a>
										</c:if>
						           <c:if test="${QX.cha == 0 }">
							          ${var.Title}
						                 </c:if> 
										</td>
										<td>${var.ItemType}</td> 
										<td>${var.Priority}</td>
										<td>${var.TestVer}</td>
										<td>${var.FixPlan}</td>
										<td>${var.State}</td>  
										 
								<td style="width: 30px;" class="center">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div>
											<c:if test="${QX.edit == 1 }">
											<li><a style="cursor:pointer;" title="编辑" onclick="editProblem('${var.Id}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											</c:if>
											<c:if test="${QX.del == 1 }">
											<li><a style="cursor:pointer;" title="删除" onclick="delProblem('${var.Id}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
											</c:if>
										</div>
								</td>
							</tr>
						
						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${QX.add == 1 }">
					<a class="btn btn-small btn-success" onclick="addProblem();">新增</a>
					</c:if> 
					<c:if test="${QX.del == 1 }">
					<a title="批量删除" class="btn btn-small btn-danger" onclick="problemMakeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i></a>
					</c:if>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		
		</div>
			  </div>
			
		
		 
			  
			   <!-- 问题结束 -->
			  <div id="dropdown1" class="tab-pane">
				<p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade.</p>
			  </div>
			  <div id="dropdown2" class="tab-pane">
				<p>Trust fund seitan letterpress, keytar raw denim keffiyeh etsy art party before they sold out master cleanse gluten-free squid scenester freegan cosby sweater. Fanny pack portland seitan DIY, art party locavore wolf cliche high life echo park Austin.</p>
			  </div>
            </div>
	</div>
 </div><!--/span-->
 
</div><!--/row-->
 	<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
			
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
			<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
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
