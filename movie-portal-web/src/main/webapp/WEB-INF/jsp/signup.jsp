<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>注册</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript">
        addEventListener("load", function() {
            setTimeout(hideURLbar, 0); }, false);
        function hideURLbar(){ window.scrollTo(0,1);
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
    $('.close').on('click', function(c){
        $('.login-form').fadeOut('slow', function(c){
            $('.login-form').remove();
        });
    });
});
</script>
	<!--SIGN UP-->
	<h1>欢迎来到电影天堂-注册</h1>
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
		<form id="signup-form" action="" method="post">
			<input id="email" name="email" type="text" class="text" value="" placeholder="邮箱">
			<input id="UserName" name="UserName" type="text" class="text" value="" placeholder="用户名">
						
			<div class="key">
				<input id="passWord" name="passWord" type="password" value="" placeholder="密码">
				<div style="color: red">${signupError}</div>
			</div>
			<div >
				<span 
					style="padding: 0;width:85px;overflow: hidden;"> <img 
					id="validation" src="createlsImage?type=signup"
					onclick="this.src='createlsImage?type=signup&'+Math.random()"
					style="display: inline;vertical-align: top;height:32px;width: 85px;cursor:pointer;">
				</span> <input type="text"  id="checkCode" name="code"
					placeholder="请输入验证码" style="display: inline">
			</div>
			<div class="signin">
				<input id="btn-submit" type="button" value="注册">
			</div>
		</form>
	</div>
<script type="text/javascript">
	
		$(function(){
			$("#btn-submit").click(function(){
				$.ajax({
					type: "POST",
		             url: "signup_change",
		             data: $("#signup-form").serialize(),
		             dataType: "json",
		             success: function(data){
		             	alert(data);
						if (data == "注册成功"){
							alert(" 跳转到登陆页面");
							location = "./signin";
						}
		             },
		             complete: function(){
		             	var random = Math.random();
		             	$("#validation").attr("src","createlsImage?type=signup&rd="+random);
		             }
				});
			});
		});
	
		window.load = function(){   
			document.getElementById('email').value='';   
			document.getElementById('passWord').value='';   
			document.getElementById('UserName').value='';   
		};  
		
	</script>
</body>
</html>