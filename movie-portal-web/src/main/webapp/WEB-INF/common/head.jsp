<%@page import="com.movie.pojo.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<title>header</title>
<!--导入header的css-->
<link rel="stylesheet" href="./css/head.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
</head> 
<body>
	<!--头部内容-->
	<header style="background: #FFF">
		<!--欢迎小字-->
		<div class="welcomePanel">
			<div class="container">
				<p>欢迎访问本站，本站数据均从网络上爬取</p>
			</div>
		</div>
		<!--logo容器 第二行-->
		<div class="container"
			style="padding: 20px 15px; padding-right: 0;height: 95px">
			<a class="logo pull-left" href="./index"></a>
			<div class="pull-right">
				<form id="search" action="search" class="navbar-form pull-right" accept-charset="utf-8">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="请输入搜索关键词" name="q">
						<button type="submit" class="btn btn-primary">
							<span style="border-radius: 0" class="glyphicon glyphicon-search">搜索</span>
						</button>
					</div>
				</form>
				<!--推荐栏-->
				<div class="header-hot">
				</div>
			</div>
		</div>

		<!--导航栏-->
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<ul class="nav navbar-nav" id="actives-controller">
					<li class=""><a href="./index">首页</a></li>
					<li class=""><a href="./movie">电影</a></li>
					<li class=""><a href="./douban250">豆瓣TOP250</a></li>
				</ul>
				<div class="pull-right" id="login-before">
					<ul class="nav navbar-nav">
						<li><a href="./signup">注册</a></li>
						<li><a href="./signin">登陆</a></li>
					</ul>
				</div>
				<div class="btn-group pull-right" id="login-after">
					<ul class="nav navbar-nav">
						<li class="dropdown">
						<a href="#" id="username" class="dropdown-toggle" data-toggle="dropdown" data-name=${session.user.userName}> 
						${session.user.userName}
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu dropdown-li" role="menu">
								<li><a href="./userzhuye" style="color: black;">个人中心</a></li>
								<li><a href="./usersetting" style="color: black;">个人设置</a></li>
								<li><a href="./usersixin" style="color: black;">我的私信</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="./user_signout" style="color: black;">退出登录</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	<%-- 	<s:debug></s:debug> --%>
</body>
<script type="text/javascript">
		
		var userflag = false;

		$(function(){
			/* 标签设置 */
			var index = $("#tag-index").attr("data-tag");
			$("#actives-controller li").eq(index).addClass("actives");
			
			/* 查找cookie */
            function getCookie(name){
                var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
                if(arr=document.cookie.match(reg))
                    return unescape(arr[2]);
                else
                    return null;
            }
            
            
            var username = $("#username").attr("data-name");
            if($("#username").attr("data-name").length>0){
            	/* 登陆 */
                $('#login-before').addClass("hide");
                $('#login_check').addClass("hide");
                userflag = true;
			}else{
				/* 未登陆 */
				$('#login-after').addClass("hide");
				  $("#reply_controller").addClass("hide");
			}
            
		});
	</script>
</html>