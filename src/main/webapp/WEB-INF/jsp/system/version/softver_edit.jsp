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
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	//保存
	function save(){
			if($("#VName").val()==""){
			$("#VName").tips({
				side:3,
	            msg:'请输入标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#VName").focus();
			return false;
		}
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
		
		if($("#PId").val()==""){
			$("#PId").tips({
				side:3,
	            msg:'请输入属于',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PId").focus();
			return false;
		}
		if($("#VInfo").val()==""){
			$("#VInfo").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#VInfo").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	//过滤类型
	function fileType(obj){
		var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	    if(fileType != '.rar' && fileType != '.zip' && fileType != '.7z'){
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
	function delP(PATH,V_Id){
		 if(confirm("确定要删除？")){
			var url = "version/deltp.do?VPath="+PATH+"&V_Id="+V_Id+"&guid="+new Date().getTime();
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
<form action="version/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="V_Id" id="V_Id" value="${pd.V_Id}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<th>版本名称:</th>
				<td><input type="text" name="VName" id="VName" value="${pd.VName}" maxlength="32" style="width:95%;" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<tr>
				<th>文件:</th>
				<td>
					<c:if test="${pd == null || pd.VPath == '' || pd.VPath == null }">
					<input type="file" id="tp" name="tp" onchange="fileType(this)"/>
					</c:if>
					<c:if test="${pd != null && pd.VPath != '' && pd.VPath != null }">
						<a href="<%=basePath%>uploadFiles/pmfiles/${pd.VPath}" target="_blank"> ${pd.VName }</a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.VPath}','${pd.V_Id }');" />
						<input type="hidden" name="tpz" id="tpz" value="${pd.VPath }"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>属于:</th>
				<td><input type="text" name="PId" id="PId" value="${pd.PId}" maxlength="32" placeholder="这里输入属于" title="属于"/></td>
			</tr>
			<tr>
				<th>备注:</th>
				<td><input type="text" name="VInfo" id="VInfo" value="${pd.VInfo}" maxlength="32" style="width:95%;" placeholder="这里输入备注" title="备注"/></td>
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
				whitelist:'rar|zip|7z',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			
		});
		
		</script>
</body>
</html>