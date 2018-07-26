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
		<title>编辑产品</title>
	   <meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 --> 
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>

  
<script type="text/javascript">

	$(top.hangge());
	 
	 
	$(document).ready(function(){
		if($("#Id").val()!=""){
			//$("#loginname").attr("readonly","readonly");
			//$("#loginname").css("color","gray");
		}
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
		
		if($("#PVer").val()==""){
			$("#PVer").tips({
				side:3,
	            msg:'输入版本号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PVer").focus();
			return false;
		}else{
			$("#PVer").val($.trim($("#PVer").val()));
		}	
		
		if($("#ReleaseTime").val()==""){
			
			$("#ReleaseTime").tips({
				side:3,
	            msg:'输入发布日期',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#ReleaseTime").focus();
			return false;
		} 
		 $("#productForm").submit();
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
					$("#productForm").submit();
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
	<form action="product/${msg }.do" name="productForm" id="productForm" method="post">
		<input name="Id" id="Id"  type="hidden"  value="${pd.Id }"/>
		<div id="zhongxin">
		<table>
				
			<tr>
				<td><input type="text" name="PName" id="PName" value="${pd.PName}"  placeholder="这里输入产品名称" title="产品名称"/></td>
				 
			</tr>
			
			<tr>
				<td><input type="text" name="PVer" id="PVer" value="${pd.PVer}"  placeholder="这里输入版本号" title="版本号" /></td>
				 
			</tr>
			<tr>
				<td><input class="span10 date-picker" name="ReleaseTime" name="ReleaseTime"  value="${pd.ReleaseTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="发布时间" title="发布时间"/></td>
				 
			</tr>
			<tr>
				<td><input type="text" name="Remark" id="Remark" value="${pd.Remark}"   placeholder="备注"  title="备注"/></td>
				 
			</tr>   
			<tr>
				<td class="center" colspan="2">
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
	
</body>
</html>
