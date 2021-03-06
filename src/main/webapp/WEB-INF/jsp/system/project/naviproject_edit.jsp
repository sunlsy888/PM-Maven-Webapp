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
    <base href="<%=basePath%>"> 
		<title>编辑项目信息</title>
 <meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<style type="text/css">
		#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:99999999; display:none;}
		.commitopacity{position:absolute; width:100%; height:700px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		.commitbox{width:100%; margin:0px auto; position:absolute; top:120px; z-index:99999;}
		.commitbox_inner{width:96%; height:255px;  margin:6px auto; background:#efefef; border-radius:5px;}
		.commitbox_top{width:100%; height:250px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
		.commitbox_top textarea{width:95%; height:195px; display:block; margin:0px auto; border:0px;}
		.commitbox_cen{width:95%; height:40px; padding-top:10px;}
		.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
		.commitbox_cen div.left img{width:30px;}
		.commitbox_cen div.right{float:right; margin-top:7px;}
		.commitbox_cen div.right span{cursor:pointer;}
		.commitbox_cen div.right span.save{border:solid 1px #c7c7c7; background:#6FB3E0; border-radius:3px; color:#FFF; padding:5px 10px;}
		.commitbox_cen div.right span.quxiao{border:solid 1px #f77400; background:#f77400; border-radius:3px; color:#FFF; padding:4px 9px;}
		</style>
 
<script type="text/javascript">

	$(top.hangge());
	 
	 
	
	
	var locat = (window.location+'').split('/'); 
	$(function(){if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});
 
	//ueditor纯文本
	function getContentTxt() {
	    var arr = [];
	    arr.push(UE.getEditor('editor').getContentTxt());
	    return arr.join("");
	}
	//ueditor有标签文本
	function getContent() {
	    var arr = [];
	    arr.push(UE.getEditor('editor').getContent());
	    return arr.join("");
	}
	 
	 
	setTimeout("ueditor()",500);
	function ueditor(){
		var ue = UE.getEditor('editor');
		//ue.setContent("pd-PRemark");
	}
	$(document).ready(function(){
		if($("#ID").val()!=""){
			//$("#loginname").attr("readonly","readonly");
			//$("#loginname").css("color","gray");
		}
		//alert("${pd.PRemark }");
		//UE.getEditor('editor').setContent(${pd.PRemark });
		// UE.getEditor('editor').setContent("${pd.PRemark }");  //赋值给UEditor  
	});
	
	//保存
	function save(){
		
 		 
		if($("#PName").val()=="" ){
			
			$("#PName").tips({
				side:3,
	            msg:'输入产品名称',
	            bg:'#AE81FF',
	            time:1
	        });
			
			$("#PName").focus();
			$("#PName").val('');
			$("#PName").css("background-color","white");
			return false;
		}else{
			$("#PName").val(jQuery.trim($('#PName').val()));
		}
		
		if($("#PDataVer").val()==""){
			$("#PDataVer").tips({
				side:3,
	            msg:'输入版本号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PDataVer").focus();
			return false;
		}else{
			$("#PDataVer").val($.trim($("#PDataVer").val()));
		}	
		if($("#TYPE").val()=="1"){
			$("#CONTENT").val(getContentTxt());
		}else{
			$("#CONTENT").val(getContent());
		}
		 
		 $("#projectForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		
		 
	}
	
	//判断是否存在
	function hasU(){
		var PNAME = $("#PName").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>product/hasU.do',
	    	data: {PNAME:PNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#projectForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#PName").css("background-color","#D16E6C");
					setTimeout("$('#PName').val('此产品已存在!')",500);
				 }
			}
		});
	}
	
	 
	 
</script>
	</head>
<body>
	<form action="project/${msg }.do" name="projectForm" id="projectForm" method="post">
		<input name="ID" id="ID"  type="hidden"  value="${pd.Id }"/>
		<div id="zhongxin">
		<table>
				
			<tr>
			<td><input type="text" name="PNo" id="PNo" value="${pd.PNo }" placeholder="这里输入项目编号" title="项目编号"/></td>
				<td><input type="text" name="PName" id="PName"  value="${pd.PName }" placeholder="这里输入项目名称" title="项目名称"/></td>
				 <td>
                   <select class="chzn-select" name="PManager" id="PManager" data-placeholder="请选择项目负责人" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${pmanagerList}" var="user">
							<option value="${user.NAME }" <c:if test="${pd.PManager==user.NAME}">selected</c:if>>${user.NAME }</option>
						</c:forEach>  
					  	</select>	
</td> 
			</tr>
			
			<tr>
			<td> <select class="chzn-select" name="PDataVer" id="PDataVer" data-placeholder="请选择数据版本" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${PDataVerList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.PDataVer==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				<td> <select class="chzn-select" name="PType" id="PType" data-placeholder="请选择项目类型" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${PTypeList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.PType==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				 <td> <select class="chzn-select" name="PLevel" id="PLevel" data-placeholder="请选择项目等级" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${PLevelList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.PLevel==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td> 
			</tr>
			<tr>
			<td><input type="text" name="PCustomer" id="PCustomer"   value="${pd.PCustomer }" placeholder="这里输入客户名称" title="客户" /></td>
				<td><select class="chzn-select" name="SoftCompany" id="SoftCompany" data-placeholder="请选择研发公司" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${SoftCompanyList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.SoftCompany==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select>
					  	</td>
				 <td>
<select class="chzn-select" name="HardSolution" id="HardSolution" data-placeholder="请选择硬件方案" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${HardSolutionList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.HardSolution==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td> 
			</tr>
			<tr>
			<td><select class="chzn-select" name="ProductType" id="ProductType" data-placeholder="请选择产品类型" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${ProductTypeList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.ProductType==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				<td><select class="chzn-select" name="ScreenResolution" id="ScreenResolution" data-placeholder="请选择屏幕分辨率" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${ScreenResolutionList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.ScreenResolution==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				 <td><input type="text" name="Memory" id="Memory"   value="${pd.Memory }" placeholder="这里输入可用内存" title="可用内存" /></td> 
			</tr> 
			<tr>
			<td><select class="chzn-select" name="OperSystem" id="OperSystem" data-placeholder="请选择操作系统" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${OperSystemList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.OperSystem==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				<td><select class="chzn-select" name="Storage" id="Storage" data-placeholder="请选择存储设备" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${StorageList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.Storage==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				 <td><select class="chzn-select" name="PortRate" id="PortRate" data-placeholder="请选择GPS端口波特率 " style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${PortRateList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.PortRate==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td> 
			</tr>   
			<tr>
			<td><select class="chzn-select" name="CPUType" id="CPUType" data-placeholder="请选择CPU类型 " style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${CPUTypeList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.CPUType==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				<td><select class="chzn-select" name="CompilerEnv" id="CompilerEnv" data-placeholder="请选择编译环境 " style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${CompilerEnvList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.CompilerEnv==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				 <td><input type="text" name="EquipType" id="EquipType"   value="${pd.EquipType }" placeholder="这里输入设备型号" title="设备型号" /></td> 
			</tr>   
			<tr>
			<td><input type="text" name="NaviPath" id="NaviPath" value="${pd.NaviPath }" placeholder="这里输入导航路径" title="路径" /></td>
				<td><select class="chzn-select" name="BindMode" id="BindMode" data-placeholder="请选择绑定方式 " style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${BindModeList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.BindMode==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				 <td><select class="chzn-select" name="ActMode" id="ActMode" data-placeholder="请选择激活方式 " style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${ActModeList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.ActMode==dictionaries.NAME}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td> 
			</tr>   
			<tr>
			<td><input type="text" name="CPUFrequency" id="CPUFrequency"  value="${pd.CPUFrequency }"  placeholder="这里输入CPU主频 " title="CPU主频 " /></td>
				<td><input type="text" name="PSource" id="PSource"   value="${pd.PSource }" placeholder="需求类型"  title="需求类型"/></td>
				 <td><input type="text" name="PRemark" id="PRemark"  placeholder="这里输入项目说明" title="说明"   style="display:none" />
				  <select class="chzn-select" name="ItemType" id="ItemType" data-placeholder="请选择立项类型 " style="vertical-align:top;"  >
						<option value="1" <c:if test="${pd.ItemType==1}">selected</c:if>>在研项目</option>
						<option value="0" <c:if test="${pd.ItemType==0}">selected</c:if>>预研项目</option>
					 	 
					  	</select>
				 </td> 
			</tr> 
			<tr>
						<td id="nr" colspan="3">
							<textarea name="CONTENT" id="CONTENT"  value="${pd.PRemark }" style="display:none" ></textarea>
								<input type="hidden" name="TYPE" id="TYPE" value="0"/>
							 <script id="editor" type="text/plain" style="width:90%;height:100px;">${pd.PRemark }</script>
						</td>
					</tr>    
		<!-- 	<tr>
			<td><input type="text" name="PVer" id="PVer"  placeholder="这里输入版本号" title="版本号" /></td>
				<td><input type="text" name="Remark" id="Remark"  placeholder="备注"  title="备注"/></td>
				 <td><input class="span3.5 date-picker" name="START_TIME" id="START_TIME" value="${pd.PDATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开通日期"  title="开通日期"/></td> 
			</tr>   -->   
			<tr>
			<td></td>
				<td class="center" >
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
				<td></td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
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
			<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!--引入属于此页面的js 
		<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>		-->
</body>
</html>
