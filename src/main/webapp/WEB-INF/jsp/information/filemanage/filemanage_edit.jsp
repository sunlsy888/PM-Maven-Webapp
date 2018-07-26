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
		<base href="<%=basePath%>">
		
		<meta charset="utf-8" />
		<title></title>
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
	
	//保存
	function save(){
			 
		if(typeof($("#tpz").val()) == "undefined"){
			if($("#tp").val()=="" || document.getElementById("tp").files[0] =='请选择文件'){
				
				$("#tp").tips({
					side:3,
		            msg:'请选文件',
		            bg:'#AE81FF',
		            time:3
		        });
				return false;
			}
		}
		
	 
		if($("#FType").val()==""){
			$("#FType").tips({
				side:3,
	            msg:'请选择文件分类',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FType").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	//过滤类型 gif|png|jpg|jpeg
	function fileType(obj){
		var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	    if(fileType != '.mov' &&fileType != '.avi' &&fileType != '.mp3' &&fileType != '.mp4' &&fileType != '.bmp' &&fileType != '.txt' &&fileType != '.gif' &&fileType != '.png' &&fileType != '.jpg' &&fileType != '.jpeg' &&fileType != '.rar' && fileType != '.zip' && fileType != '.7z' && fileType != '.*'){
	    	$("#tp").tips({
				side:3,
	            msg:'请上传压缩文件',
	            bg:'#AE81FF',
	            time:3
	        });
	    	$("#tp").val('');
	    	document.getElementById("tp").files[0] = '请选择图片';
	    }
	}
	
	//删除
	function delP(PATH,Id){
		 if(confirm("确定要删除？")){
			var url = "filemanage/deltp.do?FilePath="+PATH+"&Id="+Id+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}
</script>
	</head>
<body>
	<form action="filemanage/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="Id" id="Id" value="${pd.Id}"/>
				<input type="hidden" name="FileName" id="FileName" value="${pd.FileName}"/>
				<input type="hidden" name="FileSize" id="FileSize" value="${pd.FileSize}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
		 
			<tr>
				<th>文件:</th>
				<td>
					<c:if test="${pd == null || pd.FilePath == '' || pd.FilePath == null }">
					<input type="file" id="tp" name="tp" onchange="fileType(this)"/>
					</c:if>
					<c:if test="${pd != null && pd.FilePath != '' && pd.FilePath != null }">
						<a href="<%=basePath%>uploadFiles/netfiles/${pd.FilePath}" target="_blank">${pd.FileName}</a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.FilePath}','${pd.Id }');" />
						<input type="hidden" name="tpz" id="tpz" value="${pd.FilePath }"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>属于:</th>
				<td>  
					 <select class="chzn-select" name="FType" id="FType" data-placeholder="请选择" style="vertical-align:top;"  >
						<option value=""></option>
					 	<c:forEach items="${onlinedocumentationList}" var="dictionaries"> 
					 
						<c:if test="${null!=dictionaries.ZD_ID}">
							<option value="${dictionaries.ZD_ID }" <c:if test="${pd.FType==dictionaries.ZD_ID}">selected</c:if>>${dictionaries.NAME }</option>
						 <c:forEach items="${dictionariesList}" var="dictionariesall">
						<c:if test="${dictionaries.ZD_ID==dictionariesall.PARENT_ID}">
							<option value="${dictionariesall.ZD_ID }" <c:if test="${pd.FType==dictionariesall.ZD_ID}">selected</c:if>>${dictionaries.NAME }>>${dictionariesall.NAME }</option>
						  <c:forEach items="${dictionariesList}" var="dictionariesall2">
						<c:if test="${dictionariesall.ZD_ID==dictionariesall2.PARENT_ID}">
							<option value="${dictionariesall2.ZD_ID }" <c:if test="${pd.FType==dictionariesall2.ZD_ID}">selected</c:if>>${dictionaries.NAME }>>${dictionariesall.NAME }>>${dictionariesall2.NAME }</option>
						 </c:if>
					 
                       </c:forEach>  
						 </c:if>
					 
                       </c:forEach>  
						
					 
                       </c:if>
                       
						
						</c:forEach>  
						
					  	</select>
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="static/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="static/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/assets/js/ace/ace.js"></script>
			<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
 
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//上传
			$('#tp').ace_file_input({
				no_file:'请选择文件 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'rar|zip|7z|gif|png|jpg|jpeg|txt|bmp|mp4|mp3|mov|avi|*',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
		});
		
		</script>
</body>
</html>