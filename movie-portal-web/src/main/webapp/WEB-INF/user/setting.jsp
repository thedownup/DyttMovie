<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<title>setting</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
<!--引入图标-->
<link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
<!--导入header的css-->
<link rel="stylesheet" href="./css/zhuye.css">
<!--导入content的css-->
<link rel="stylesheet" href="./css/content.css">
<!--导入content的css-->
<link rel="stylesheet" href="./css/content.css">
<!--导入content的css-->
<link rel="stylesheet" href="./css/seting.css">
<!--导入页脚的css-->
<link rel="stylesheet" href="./css/footer.css">
<!--日期的css-->
<link rel="stylesheet" href="./css/bootstrap-datetimepicker.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="./bootstrap-3.3.7/dist/js/bootstrap.js"></script>
<!--导入日期控件-->
<script src="./js/bootstrap-datetimepicker.min.js"></script>
<script src="./js/bootstrap-datetimepicker.zh-CN.js"></script>
<!-- 上传文件插件 -->
<script src="./js/ajaxfileupload.js"></script>
<style type="text/css">
body {
	font-size: 14px;
	font-family: 'HanHei SC', 'PingFang SC', 'Helvetica Neue', 'Helvetica',
		'Microsoft YaHei', 'WenQuanYi Micro Hei', 'STHeitiSC-Light', 'Arial',
		'Segoe UI', sans-serif;
	background: rgb(245, 245, 245);
}

div {
	display: block;
}
</style>
</head>
<body>
	<!-- 头部 -->
	<jsp:include page="../common/head.jsp"></jsp:include>
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12 user-zone-cover" style="background: #EEE url(${session.user.backImg})">
					<div class="user-portrait">
						<a class="user-avatar" ><img
							src="${session.user.touXiangImg}" height="88px" width="88px"></a>
						<div class="user-name">${session.user.userName}</div>
					</div>
					<a href="javascript:;" onclick="$('#modifyModal').modal('show');"
						class="modify-cover-btn">修改背景</a>
				</div>

				<div class="row">
					<div class="col-xs-6 padding-right-0">
						<div class="panel panel-default">
							<div class="panel-heading">
								<span class="glyphicon glyphicon-user"></span> 我的资料
							</div>
							<div class="panel-body">
								<p>昵称：${session.user.userName}</p>
								<p>生日：${request.birthday}</p>
								<p>
									性别 ：
									<c:if test="${session.user.sex == '0'}">
									男
								</c:if>
									<c:if test="${session.user.sex == '1'}">
									女
								</c:if>
								</p>
								<p >个性签名：${session.user.signature}</p>
							</div>
						</div>
						<!--喜欢的电影-->
						<div class="panel panel-default">
							<div class="panel panel-default">
								<div class="panel-heading">基本资料</div>

								<div class="panel-body">
									<div class="form-group">
										<label>头像</label> <!-- <input type="file" name="file"> -->
										<%-- <input id="userId" name="userId" type="text" value="${session.user.id}" style="display: none;"> --%>
										<button onclick="$('#modifyTouxiang').modal('show');"   class="btn btn-warning pull-right">设置头像</button></br><hr>
									</div>
									<form id="baseData" method="post" enctype="multipart/form-data"
										role="form">
										<div class="form-group">
											<label for="sex">性别</label>
											<label class="radio-inline"><input type="radio" name="sex" value="0">男</label> 
											<label class="radio-inline"><input type="radio" name="sex" value="1">女</label>
										</div>
										<div class="form-group">
											<label>生日</label> <input class="form_datetime form-control"
												type="text" value="" size="30" name="birthday" readonly>
										</div>
										<div class="form-group">
											<label>个性签名：（150个字符）</label>
											<textarea style="margin-bottom: 30px; width: 455px; height: 70px" class="form-control"
												maxlength="150"></textarea>
											<input id="signature" type="text" class="hidden" name="signature">
											<button type="button" id="saveUpdate" class="btn btn-primary pull-right">保存资料</button>
										</div>
									</form>
								</div>
							</div>
						</div>

					</div>
					<div class="col-xs-6">
						<div class="panel panel-default">
							<div class="panel-heading">邮箱验证</div>
							<div class="panel-body">
								<c:if test="${session.user.state == '1'}">
									${session.user.email} --<strong style="color: green;">已认证</strong>
								</c:if>
								<c:if test="${session.user.state != '1'}">
									${session.user.email} --<strong style="color: red;">未认证</strong> -- <a
										id="sendMail" href="#">发送验证邮箱</a>
								</c:if>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">帐号安全 .</div>
							<div class="panel-body">
								<form id="changePassword" method="post" role="form" >
									<div class="form-group">
										<label>旧密码</label> <input type="passWord" class="form-control"
										name="passWord">
									</div>
									<div class="form-group">
										<label>新密码</label> <input id="firstPassword" type="passWord"
											class="form-control" name="newPassword">
									</div>
									<div class="form-group">
										<label>新密码</label> <input id="secondPassword" type="password"
											class="form-control">
									</div>
									<div class="form-group">
										<button type="button" id="savePassword" class="btn btn-primary pull-right">保存资料</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<jsp:include page="../common/footer.html" flush="true"></jsp:include>
	<div class="modal fade" id="modifyModal" tabindex="-1" resid=""
		role="dialog" aria-labelledby="shareModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="shareModalLabel">修改背景</h4>
				</div>
				<div class="modal-body">
					<form id="coverForm" method="POST" enctype="multipart/form-data" >
						<div class="form-group">
							<label for="imgsrc">上传图片：</label> <input type="file" id="imgsrc"
								name="file">
						</div>
						<input id="userId" name="userId" type="text" value="${session.user.id}" style="display: none;">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button class="btn btn-primary" type="button" id="fileupbutton">提交</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="modifyTouxiang" tabindex="-1" resid=""
		role="dialog" aria-labelledby="shareModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="shareModalLabel">修改背景</h4>
				</div>
				<div class="modal-body">
					<form id="touxian" method="POST" enctype="multipart/form-data">
						<div class="form-group">
							<label for="imgsrc">上传图片：</label> <input type="file" name="file">
						</div>
						<input id="userId" name="userId" type="text" value="${session.user.id}" style="display: none;">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button class="btn btn-primary" type="button" id="saveTouxian">提交</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript">

	$(function() {
		$("#sendMail").click(function() {
			$.ajax({
				url : "sendMail_Mail",
				type : "POST",
				success : function(data) {
					alert(data);
				},
				error : function(data) {
					alert("未知错误");
				}
			});
		});
		
		$("#savePassword").click(function(){
			if($("#firstPassword").val() == $("#secondPassword").val()){
				$.ajax({
					url : "updatePassword_change",
					type : "POST",
					data : $('#changePassword').serialize(),
					success : function(data) {
						alert(data);
					},
					error : function(data) {
						alert(data);
					}
				});
			}else{
				alert("两次密码不一样");
			}
		
		});
		
		/* 保存头像 */
		$("#saveTouxian").click(function(){
		 	 var formData = new FormData(document.getElementById("touxian"));
			 $.ajax({
				  url: "${filedomain}/file/updatetouxian",
				  type: "POST",
				  data: formData,
				  contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
				  processData: false, //必须false才会自动加上正确的Content-Type
				  dataType: "json",
				  success: function (data) {
				  	 alert(data.msg);
				  	 $('#modifyTouxiang').modal('hide');
				  	 location.reload();
				  }
			 }); 
		});
		
		$("#saveUpdate").click(function() {
			
			$("#signature").attr("value",$("textarea").val());
			/* 修改资料 */
			$.ajax({
				url : "updateUser_change",
				type : "POST",
				data : $('#baseData').serialize(),
				dataType: "json",
				success : function(data) {
					alert(data); 
					window.location.reload();
				}
			});
		});
		
		$("#fileupbutton").click(function(){
			 var formData = new FormData(document.getElementById("coverForm"));
 
			 $.ajax({
				  url: "${filedomain}/file/updateback",
				  type: "POST",
				  data: formData,
				  contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
				  processData: false, //必须false才会自动加上正确的Content-Type
				  dataType: "json",
				  success: function (data) {
				  	alert(data.msg)
				  	 $('#modifyModal').modal('hide');
				  	 location.reload();
				  }
			 });
		});
	});


	$(".form_datetime").datetimepicker({
		format : "yyyy-mm-dd",
		autoclose : true,
		todayBtn : true,
		todayHighlight : true,
		showMeridian : true,
		pickerPosition : "bottom-center",
		language : 'zh-CN', //中文，需要引用zh-CN.js包
		startView : 2, //月视图
		minView : 2 //日期时间选择器所能够提供的最精确的时间选择视图
	});
</script>
</html>