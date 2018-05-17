<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>登录</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript">
	addEventListener("load", function() {
		setTimeout(hideURLbar, 0);
	}, false);
	function hideURLbar() {
		window.scrollTo(0, 1);
	}
</script>
<link href="./css/login.css" rel='stylesheet' type='text/css' />
<!--webfonts-->
<!--<link href='http://fonts.useso.com/css?family=PT+Sans:400,700,400italic,700italic|Oswald:400,300,700' rel='stylesheet' type='text/css'>-->
<!--<link href='http://fonts.useso.com/css?family=Exo+2' rel='stylesheet' type='text/css'>-->
<!--//webfonts-->
<script src="./js/jquery-3.3.1.min.js"></script>
</head>
<body>
	<script>$(document).ready(function(c) {
			$('.close').on('click', function(c) {
				$('.login-form').fadeOut('slow', function(c) {
					$('.login-form').remove();
				});
			});
		});
	</script>
	<!--SIGN UP-->
	<h1>欢迎登陆电影天堂</h1>
	<div class="login-form">
		<div class="close"></div>
		<div class="head-info">
			<label class="lbl-1"> </label> <label class="lbl-2"> </label> <label
				class="lbl-3"> </label>
		</div>
		<div class="clear"></div>
		<div class="avtar">
			<img src="./image/login/avtar.png" />
		</div>
		<form id="signin-form" action="" method="post">
			<input name="userName" type="text" class="text" placeholder="账号"
				autocomplete="off">
			<div class="key">
				<input name="passWord" type="password" placeholder="密码"
					autocomplete="off">
				<div style="color: red">${loginError}</div>
			</div>
			<div>
				<span style="padding: 0;width:85px;overflow: hidden;"> <img
					id="validation" src="createlsImage?type=signin"
					onclick="this.src='createlsImage?type=signin&'+Math.random()"
					style="display: inline;vertical-align: top;height:32px;width: 85px;cursor:pointer;">
				</span> <input type="text" id="checkCode" name="code" placeholder="请输入验证码"
					style="display: inline">
			</div>
			<div class="signin">
				<input id="btn-submit" type="button" value="登陆">
			</div>
		</form>

		<script type="text/javascript">
			$(function() {
				$("#btn-submit").click(function(){
					$.ajax({
						type: "POST",
			             url: "signin_change",
			             data: $("#signin-form").serialize(),
			             dataType: "json",
			             success: function(data){
			             	alert(data);
			             	//登陆成功进行跳转
							if (data == "登陆成功"){
								alert(" 跳转到首页");
								location = "./index";
							}
			             },
			             complete: function(){
			             	var random = Math.random();
			             	$("#validation").attr("src","createlsImage?type=signin&rd="+random);
			             }
					});
				});
			});
		</script>
	</div>

</body>
</html>