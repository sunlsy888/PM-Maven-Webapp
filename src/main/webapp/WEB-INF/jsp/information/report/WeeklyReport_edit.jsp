<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML >
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title>添加新BUG</title>
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
	 
	 
	$(document).ready(function(){
		if($("#ID").val()!=""){
			//$("#loginname").attr("readonly","readonly");
			//$("#loginname").css("color","gray");
		}
	});
	
	var locat = (window.location+'').split('/'); 
	$(function(){if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});
 
	//ueditor纯文本
	function getContentTxt() {
	    var arr = [];
	    arr.push(UE.getEditor('editor').getContentTxt());
	    return arr.join("");
	}
	function getContentTxt2() {
	    var arr = [];
	    arr.push(UE.getEditor('editor2').getContentTxt());
	    return arr.join("");
	}
	//ueditor有标签文本
	function getContent() {
	    var arr = [];
	    arr.push(UE.getEditor('editor').getContent());
	    return arr.join("");
	}
	function getContent2() {
	    var arr = [];
	    arr.push(UE.getEditor('editor2').getContent());
	    return arr.join("");
	}
	setTimeout("ueditor()",500);
	function ueditor(){
		var ue = UE.getEditor('editor');
	}
	setTimeout("ueditor2()",500);
	function ueditor2(){
		var ue = UE.getEditor('editor2');
	}
	
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
		
	
		if($("#TYPE").val()=="1"){
			$("#Requirement").val(getContentTxt2());
		}else{
			$("#Requirement").val(getContent2());
		}
		if($("#TYPE").val()=="1"){
			$("#ItemDetails").val(getContentTxt());
		}else{
			$("#ItemDetails").val(getContent());
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
	<form action="report/${msg }.do" name="projectForm" id="projectForm" method="post">
		<input name="Id" id="Id"  type="hidden"  value="${pd.Id }"/>
		<div id="zhongxin">
		<table>
				<tr>
		       <td><input type="text" name="PM" id="PM"   value="${pd.PM }"  placeholder="项目负责人 "/></td>
				<td ><input type="text" name="PName" id="PName"  value="${pd.PName }"  placeholder="这里输入项目名称" title="名称"/></td>
				</tr>
			<tr>
		 
					<td id="nr" colspan="2">
							<textarea name="Requirement" id="Requirement"  value="${pd.Requirement }"  style="display:none" ></textarea>
								 
							 <script id="editor2" type="text/plain" style="width:95%;height:110px;">${pd.Requirement }</script>
						</td>
			</tr>
			
			<tr>
			<td> <select class="chzn-select" name="PType" id="PType" data-placeholder="这里输入项目类型" style="vertical-align:top;"  >
						<option value=""></option>
							<c:forEach items="${PTypeList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.PType==dictionaries.ZD_ID}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				<td> 
				<input class=" date-picker" name="PlanDate" id="PlanDate"  value="${pd.PlanDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="计划日期" title="计划发布日期"/> 
				</td>
				  
			</tr>
	   
			<tr>
						<td id="nr" colspan="2">
							<textarea name="ItemDetails" id="ItemDetails" value="${pd.ItemDetails }" style="display:none" ></textarea>
								<input type="hidden" name="TYPE" id="TYPE" value="0"/>
							 <script id="editor" type="text/plain" style="width:95%;height:110px;">${pd.ItemDetails }</script>
						</td>
					</tr>     
			<tr> 
				<td class="center"  colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td> 
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