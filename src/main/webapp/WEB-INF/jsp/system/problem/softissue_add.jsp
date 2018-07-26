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
	//ueditor有标签文本
	function getContent() {
	    var arr = [];
	    arr.push(UE.getEditor('editor').getContent());
	    return arr.join("");
	}

	setTimeout("ueditor()",500);
	function ueditor(){
		var ue = UE.getEditor('editor');
	}

	
	//保存
	function save(){
		
 		 
		if($("#Title").val()=="" ){
			
			$("#Title").tips({
				side:3,
	            msg:'输入产品名称',
	            bg:'#AE81FF',
	            time:1
	        });
			
			$("#Title").focus();
			$("#Title").val('');
			$("#Title").css("background-color","white");
			return false;
		}else{
			$("#Title").val(jQuery.trim($('#Title').val()));
		}
		
		if($("#TestVer").val()==""){
			$("#TestVer").tips({
				side:3,
	            msg:'输入版本号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TestVer").focus();
			return false;
		}else{
			$("#TestVer").val($.trim($("#TestVer").val()));
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
	<form action="problem/save.do" name="projectForm" id="projectForm" method="post">
		<input name="Id" id="Id"  type="hidden"  value="${pd.ID }"/>
		<div id="zhongxin">
		<table>
				<tr>
		       <td><input type="text" name="PId" id="PId"   value="${pd.PId }"  placeholder="项目编号"/></td>
				<td ><input type="text" name="Title" id="Title"  placeholder="这里输入标题" title="标题"/></td>
				</tr>
			<tr>
		 
				<td><select class="chzn-select" name="Priority" id="Priority" data-placeholder="请选择问题等级" style="vertical-align:top;"  >
						<option value=""></option>
					 	<c:forEach items="${PLevelList}" var="dictionaries">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.ZD_ID==dictionaries.ZD_ID}">selected</c:if>>${dictionaries.NAME }</option>
						</c:forEach>  
					  	</select></td>
				 <td>
                   <select class="chzn-select" name="TestVer" id="TestVer" data-placeholder="请选择版本" style="vertical-align:top;"  >
						<option value=""></option>
					 	<c:forEach items="${verList}" var="sver">
							<option value="${sver.vname }" <c:if test="${pd.TestVer==sver.vname}">selected</c:if>>${sver.vname }</option>
						</c:forEach>  
					  	</select>
                   
</td> 
			</tr>
			
			<tr>
			<td> 
			  <select class="chzn-select" name="TestOne" id="TestOne" data-placeholder="这里输入测试者" style="vertical-align:top;"  >
						<option value=""></option>
						<option value="">全部</option>
					 	<c:forEach items="${pmanagerList}" var="user">
							<option value="${user.NAME }" <c:if test="${pd.TestOne==user.NAME}">selected</c:if>>${user.NAME }</option>
						</c:forEach>  
					  	</select>	
			</td>
				<td> <select class="chzn-select" name="ItemType" id="ItemType" data-placeholder="请选择问题类型" style="vertical-align:top;"  >
						<option value=""></option>
							<option value="软件"  <c:if test="${pd.ItemType=='软件'}">selected</c:if>>软件</option> 
						<option value="数据"  <c:if test="${pd.ItemType=='数据'}">selected</c:if>>数据</option> 
					  	</select></td>
				 <td> </td> 
			</tr>
	   
			<tr>
						<td id="nr" colspan="2">
							<textarea name="CONTENT" id="CONTENT" style="display:none" ></textarea>
								<input type="hidden" name="TYPE" id="TYPE" value="0"/>
							 <script id="editor" type="text/plain" style="width:95%;height:110px;"></script>
						</td>
					</tr>    
		<!-- 	<tr>
			<td><input type="text" name="PVer" id="PVer"  placeholder="这里输入版本号" title="版本号" /></td>
				<td><input type="text" name="Remark" id="Remark"  placeholder="备注"  title="备注"/></td>
				 <td><input class="span3.5 date-picker" name="START_TIME" id="START_TIME" value="${pd.PDATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开通日期"  title="开通日期"/></td> 
			</tr>   -->   
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