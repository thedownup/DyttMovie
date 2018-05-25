<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${ouser.userName}的主页</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
<!--引入图标-->
<link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
<!--导入header的css-->
<link rel="stylesheet" href="./css/zhuye.css">
<!--导入header的css-->
<link rel="stylesheet" href="./css/content.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="./bootstrap-3.3.7/dist/js/bootstrap.js"></script>
<!-- 分页插件 -->
<script type="text/javascript" src="./js/bootstrap-paginator.min.js"></script>
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

	<div id="user_login" style="display: none">${session.user.id}</div>
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12 user-zone-cover"
					style="background: #EEE url(${ouser.backImg}) ">
					<div class="user-portrait">
						<a class="user-avatar" href=""><img src="${ouser.touXiangImg}"
							height="88px" width="88px"></a>
						<div class="user-name">${ouser.userName}</div>
						<div class="other-button" style="margin-top: 10px;">
							<button id="follow" onclick="follow()" type="button" class="btn btn-success btn-sm" onclick="">关注TA</button>
							<button id="unfollow"  onclick="unfollow()" type="button" class="btn btn-danger btn-sm">取消关注 </button>
							<a id="siliao" onclick="siliao()" class="btn btn-info btn-sm" href="./usersixin?userName=${ouser.userName}&touXiangImg=${ouser.touXiangImg}&id=${ouser.id}">私聊TA <span class="glyphicon glyphicon-envelope"></span></a>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-9 padding-right-0">
						<div class="panel panel-default">
							<div class="panel-heading">
								<span class="glyphicon glyphicon-user"></span> 他的资料
							</div>
							<div class="panel-body">
								<p>昵称：${ouser.userName}</p>
								<p>生日：${ouser.birthday}</p>
								<p>个性签名：${ouser.signature}</p>
							</div>
						</div>


						<!--喜欢的电影-->
						<div class="panel panel-default">
							<div class="panel panel-default">
								<div class="panel-heading">
									<span class="glyphicon glyphicon-film"></span> 他喜欢的电影
								</div>
								<div class="panel-body like-list">
									<div id="like-list"></div>
									<!-- 分页 -->
									<div class="col-xs-12 ">
										<div id="example" style="text-align: center">
											<ul id="pageLimit"></ul>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="col-xs-3">
						<div class="panel panel-default">
							<div class="panel-heading">
								<span class="glyphicon glyphicon-th-list"></span> 他最近浏览过
							</div>
							<div class="list-group" id="recentlyMovies"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../common/footer.html" flush="true"></jsp:include>

</body>
<script type="text/javascript">
	
	var allPage = 1;
	
	var currentPage = 1;
	
	var id = $("#user_login").text();
	
	$(function() {
		$("#fileupbutton").click(function() {
			$('#coverForm').submit();
		});
		getLikeMovies(1); 
		getRecentlyMovies();
		//判断登陆了
		if(check_login()){
			//判断是否关注
			checkFocusUser();
			if(id == ${ouser.id}){
				window.location = "./userzhuye";	
			}
		} else {
			$("#unfollow").css("display","none");
		}
	});
	
	function getLikeMovies(page){
		$.ajax({
				 type: "get",
	             url: "ajax/getLikeMovies_movieFlag",
	             data: {page:page,uid:${ouser.id}},
	             dataType: "json",
	             success: function(data){
	             	var obj = $.parseJSON(data);
	             	allPage = obj.num;
	             	if(allPage != 0){
		             	parseData(obj);
	             	}
	             }  
			});
	}
	
	function parseData(data){
		$("#like-list").empty();
		for(var i = 0;i<data.objects.length;i++){
			var html = '<div class="col-xs-3 movie-item">'+
                    '<a style="position:relative;display:block;" title="'+data.objects[i].movieName+'\" target="_blank" href=\"'+"./movieinfo?mid="+data.objects[i].id+'\">'+
                    '<img alt="'+data.objects[i].movieName+'\" title="'+ data.objects[i].movieName+'\" src="'+data.objects[i].movieImgUrl+'\">'+
                    '<div class="item-hover"></div>'+
                    '</a>'+
                    '<div class="meta">'+
                    '<h4><a href=\"'+"./movieinfo?mid="+data.objects[i].id+'\"' + ' target="_blank" title="'+data.objects[i].movieName+'\">'+data.objects[i].movieName+'</a><em> - '+data.objects[i].score+'分</em></h4>'+
                    '</div>'+
                    '</div>';
						
	    	$("#like-list").append(html);
		}
			fenye();
	}
	
	function fenye() {
		
			if(allPage%8 == 0){
				allPage = allPage/8;
			}else{
				allPage = allPage/8+1
			}
		
			$('#pageLimit').bootstrapPaginator({
				//当前的请求页面。
				currentPage: currentPage,
				//一共多少页。
				totalPages: allPage,
				size: "normal",
				//应该是页眉的大小。
				bootstrapMajorVersion: 3,
				//bootstrap的版本要求。
				alignment: "right",
				numberOfPages: 10,
				//一页列出多少数据。
				itemTexts: function(type, page, current) { //如下的代码是将页眉显示的中文显示我们自定义的中文。
					switch (type) {
					case "first":
						return "首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "末页";
					case "page":
						return page;
					}
				},
				onPageClicked: function(event, originalEvent, type, page){
					currentPage = page;
					getLikeMovies(page);
         	   	}
			});
		}
		
		function getRecentlyMovies(){
			$.ajax({
				 type: "get",
	             url: "ajax/getRecentlyMovies_reids",
	             data: {uid:${ouser.id}},
	             dataType: "json",
	             success: function(data){
					var obj = JSON.parse(data);
					$("#recentlyMovies").empty();
					for(var i=0;i<obj.length;i++){
						var html = '<a target="_blank" href=\"'+"./movieinfo?mid="+JSON.parse(obj[i]).mid+'\" class="list-group-item">'+ JSON.parse(obj[i]).movieName +'</a>';
						$("#recentlyMovies").append(html);
					}
	             }  
			});
		}
		
		 /* 判断是否登录 */
    function check_login(){
    	var id = $("#user_login").text();
    	if(id == ''){
    		return false;
    	} else {
    		return true;
    	}
    }
		
		/* 判断是否关注 */
		function checkFocusUser(){
			$.ajax({
				type: "get",
	             url: "ajax/checkFocusUser_comments",
	             data: {uid:id,fid:${ouser.id}},
	             dataType: "json",
	             success: function(data){
	             	if (data == 'true'){
	             		$("#follow").css("display","none");
	             	} else {
	             		$("#unfollow").css("display","none");
	             	}
	             }  
			});
		}
		
		/* 关注他 */
		function follow(){
			if(id == '') {
				alert("清先登录");
				return;
			}
			$.ajax({
				type: "get",
	             url: "ajax/insertFocusUser_comments",
	             data: {uid:id,fid:${ouser.id}},
	             dataType: "json",
	             success: function(data){
	             	if (data == 200){
	             		$("#follow").css("display","none");
	             		$("#unfollow").css("display","inline");
	             	} else {
	             		$("#unfollow").css("display","none");
	             		$("#follow").css("display","inline");
	             	}
	             }  
			});
		}
		
		/* 取消关注 */
		function unfollow(){
			$.ajax({
				type: "get",
	             url: "ajax/removeFocusUser_comments",
	             data: {uid:id,fid:${ouser.id}},
	             dataType: "json",
	             success: function(data){
	             	if (data == 200){
	             		$("#unfollow").css("display","none");
	             		$("#follow").css("display","inline");
	             	} else {
	             		$("#follow").css("display","none");
	             		$("#unfollow").css("display","inline");
	             	}
	             }  
			});
		}
		
		function siliao(){
			if(check_login() == false){
				alert("清先登录");
			}		
		}
</script>
</html>