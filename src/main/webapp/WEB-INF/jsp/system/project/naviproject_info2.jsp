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
		
	 
		</form>
		
		
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		
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
