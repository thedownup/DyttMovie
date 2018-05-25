
</html><%@ page language="java" import="java.util.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${movie.movieName}电影详情</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
<!--导入douban250的css-->
<link rel="stylesheet" href="./css/movieshow.css">
<!--引入图标-->
<link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript"
	src="./bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
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

	<jsp:include page="../common/head.jsp" flush="true" />
	<div style="display: none;" id="user_login">${session.user.id}</div>
	<div class="container">
		<div class="container-fluid"
			style="width: 1300px;background: rgb(245,245,245);">
			<!--页脚导航标签-->
			<ol class="breadcrumb" style="  background: rgb(245,245,245);">
				<li><a href="./index">首页</a></li>
				<li><a href="./movie">电影</a></li>
				<c:forTokens items="${movie.type}" delims="/" var="tag">
					<li><a style="color: green;cursor: default;"><c:out
								value="${tag}"></c:out> </a></li>
				</c:forTokens>
			</ol>
			<!--电影介绍table-->
			<div class="row ">
				<!--左半部分-->
				<div class="col-xs-9" style="width: 780px;">
					<!--电影名称-->
					<h3>${movie.movieName}</h3>
					<div class="col-xs-4">
						<a style="display: block;position: relative"> <img
							style="width: 100%" alt="${movie.movieName}"
							class="img-thumbnail" src="${movie.movieImgUrl}"
							onerror="this.onerror=null; this.src='./pmovie/../noimage.png'">
						</a>
					</div>

					<!--table介绍框-->
					<div class="col-xs-8" id="indextable">
						<table class="table table-striped table-condensed table-bordered"
							style="">
							<tbody>
								<tr>
									<td>导演</td>
									<td>${movieInfo.director}</td>
								</tr>
								<tr>
									<td>编剧</td>
									<td>${movieInfo.writers}</td>
								</tr>
								<tr>
									<td>主演</td>
									<td><c:forTokens items=" ${movieInfo.starring}" delims="/"
											var="ty">
											<c:set var="string" value="${ty}" />
											/<a href="./search?page=1&q=${fn:trim(string)}">${ty}</a>
										</c:forTokens></td>
								</tr>
								<tr>
									<td>类型</td>
									<td style="color: #337ab7;text-decoration: none;"><c:forTokens
											items="${movie.type}" delims="/" var="ty">
											<a href="./movie?type=${ty}">${ty}</a> /
                            	</c:forTokens></td>
								</tr>
								<tr>
									<td>地区</td>
									<td style="color: #337ab7;text-decoration: none;"><c:forTokens
											items="${movie.area}" delims="/" var="ar">
											<a href="./movie?area=${ar}">${ar}</a> /
	                            		</c:forTokens></td>
								</tr>
								<tr>
									<td>语言</td>
									<td><c:forTokens items="${movieInfo.language}" delims="/"
											var="la">
											<a href="./search?q=${la}">${la}</a>
										</c:forTokens></td>
								</tr>
								<tr>
									<td>上映时间</td>
									<td>${movie.year}</td>
								</tr>
								<tr>
									<td>片长</td>
									<td>${movieInfo.rTime}</td>
								</tr>
								<tr>
									<td>又名</td>
									<td>${movieInfo.alias}</td>
								</tr>
								<tr>
									<td>评分</td>
									<td>豆瓣 &nbsp${movie.score}</td>
								</tr>
							</tbody>
						</table>

						<!--按钮组-->
						<div class="btn-group" style="border-radius: 3px" role="group">
							<a id="down-btn" class="btn btn-default btn-sm" role="button"
								href="#down"> <span class="glyphicon glyphicon-download-alt"></span>下载
							</a> <a id="wantSee" class="btn btn-default btn-sm" role="button">
								<span class="glyphicon glyphicon-time"></span>想看
							</a> <a id="seeBefore" class="btn btn-default btn-sm" role="button">
								<span class="glyphicon glyphicon-check"></span>看过
							</a> <a id="likeSee" class="btn btn-default btn-sm" role="button">
								<span class="glyphicon glyphicon-heart"></span>喜欢
							</a>
						</div>
					</div>
					<!--电影介绍-->
					<div class="row">
						<div class="col-xs-12 dyjieshao">
							<h2>电影介绍</h2>
						</div>
						<!--介绍-->
						<div class="col-xs-12" style="border-bottom: 2px dotted gray">
							<p>${movieInfo.movieIntroduce}</p>
						</div>

						<!--下载链接-->
						<div class="col-xs-12 dyjieshao">
							<h2 id="down">资源下载</h2>
						</div>
						<!--介绍-->
						<div class="col-xs-12" style="border-bottom: 2px dotted gray">
							<table class="table table-hover" style="margin-bottom:0;">
								<tbody>
									<c:forEach items="${links}" var="link">
										<tr>
											<td><a href="<c:out value="${link.linkUrl}"></c:out>">
													<c:out value="${link.linkName}"></c:out>
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<!--下载帮助-->
							<div class="panel-footer resource-help"
								style="margin-bottom: 50px">
								<strong>《${movie.movieName}》-下载帮助：</strong><br> <span>1、想要在线观看，请保存到百度网盘中，没有网盘链接或者链接失效的，请搜索：百度云网盘离线下载教程</span><br>
								<span>2、如需下载电影，请先安装迅雷（旋风），然后右键资源链接，选择迅雷（旋风）下载。</span><br> <span>3、资源名称中含HD为高清，BD为蓝光
									,两者都有为双音轨。</span><br> <span>4、如果浏览器无法识别下载链接 请复制到迅雷中下载 </span><br>
							</div>
						</div>

						<!--精彩推荐-->
						<div class="col-xs-12 dyjieshao">
							<h2>精彩推荐</h2>
						</div>

						<!--精彩推荐电影的推荐栏-->
						<div class="col-xs-12">
							<div style="padding: 0 10px">
								<!--循环-->
								<c:forEach items="${wonderfulMovie}" var="wmovie">
									<div class="col-xs-2 movie-recommened">
										<a href="./movieinfo?mid=${wmovie.id}" target="_blank"
											style="position:relative;display:block;"> <img
											alt="${wmovie.movieName}" src="${wmovie.movieImgUrl}"
											width="100%"
											onerror="this.onerror=null; this.src='./pmovie/../noimage.png'">
											<h5>${wmovie.movieName}</h5>
										</a>
									</div>
								</c:forEach>
							</div>
						</div>

						<!--影片评论-->
						<div class="col-xs-12 dyjieshao">
							<h2>影片评论</h2>
						</div>

						<!--评论展现区-->
						<div class="col-xs-12">
							<div class="panel panel-success">
								<!-- 	<div class="panel-heading">
									<h3 class="panel-title">评论</h3>
								</div> -->
								<div id="dynamic"></div>

							</div>
							<!-- 分页 -->
							<div id="example" style="text-align: center">
								<ul id="pageLimit"></ul>
							</div>
						</div>

						<div id="reply_controller">
							<div class="pull-right" style="margin-bottom: 20px" id="reply">
								<a class="btn btn-primary" id="replyInfo">收回</a>
							</div>

							<!--发送评论-->
							<div class="col-xs-12" id="sendreply">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">进行评论</h3>
									</div>
									<div class="panel-body">
										<textarea class="form-control" maxlength="150" id="message"></textarea>
									</div>
									<div class="panel-footer">
										<div class="row">
											<div class="col-xs-4">
												<!--验证码-->
												<div class="input-group">
													<span class="input-group-addon"
														style="padding: 0;width:85px;overflow: hidden;"> <img
														id="validation" src="createImage"
														onclick="this.src='createImage?'+ Math.random()"
														style="display: inline;vertical-align: top;height:32px;width: 85px;cursor:pointer;">
													</span> <input type="text" class="form-control" id="checkCode"
														placeholder="请输入验证码" style="display: inline">
												</div>
											</div>
											<!--回复-->
											<div class="col-xs-8">
												<div class="pull-right">
													<button type="submit" id="huifu"
														class="btn btn-primary btn-small pull-right"
														style="width: 100px">回复</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div id="login_check" class="col-xs-12"
							style="height: 50px;background-color: orange;line-height: 50px">
							<strong>提示:请先</strong>&nbsp&nbsp&nbsp <strong><a
								href="./signin">登陆&nbsp</a></strong>/ <strong><a href="./signup">&nbsp注册</a>
								&nbsp&nbsp&nbsp&nbsp后可发送评论 </strong>
						</div>
					</div>
				</div>

				<!--右半部分-->
				<div class="col-xs-3 sidebar"
					style="padding-left:15px;width:270px;padding-right:5px;">
					<div class="row">
						<div class="col-xs-12 dyjieshao">
							<h2>最近浏览过的用户</h2>
						</div>
						<!--浏览用户头像-->
						<div id="ruser" class="view-list">
							<!-- 	<p>暂且没人访问</p> -->
						</div>

						<div class="col-xs-12" style="margin-top: 10px">
							<span>${wantSeeNum}人想看 / ${seeBeforeNum}人看过 /
								${linkeSeeNum}人喜欢</span>
						</div>

						<div class="col-xs-12">
							<h3 class="right-text">最近更新</h3>
							<div class="list-group">
								<c:forEach items="${recentlyMovies}" var="map"
									varStatus="status">
									<a target="_blank"
										href="./movieinfo?mid=<c:out value="${map.key}"></c:out>"
										class="list-group-item"> <span class="list-item">${status.index+1}</span>
									<c:out value="${map.value}" />
									</a>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--尾部-->
	<jsp:include page="../common/footer.html"></jsp:include>
</body>
<script type="text/javascript">
    
    var flag = false;
    var currentPage = 1;
	var commentsPage = 0;
	var flag = true;
	
	var uid = $("#user_login").text();
	var wantSee = 0;
	var seeBefore = 0;
	var likeSee = 0;
	
    $(function () {
    
    	initFenYe(1);
    	getRecentlyWatchUser();
    	//检查登陆
	    btnGroupClick();
    	if(check_login() == true){
    		RecordUser();
	    	replyClick();
	    	getMovieFlag();
	    	$("#huifu").click(function(){
	    		sendComments();
	    		initFenYe(1);
	    	});
	    	lookTrace();
    	}
    });
    
    /* 判断是否登录 */
    function check_login(){
    	var id = $("#user_login").text();
    	if(id == ''){
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /* 回复按钮点击 */
    function replyClick(){
    	$('#reply').click(function () {
            $("#sendreply").fadeToggle("slow");
            if (flag == true){
                $('#replyInfo').html("收回");
                flag = false;
            }else {
                $('#replyInfo').html("展开");
                flag = true;
            }
        });
    }
    
    /* 初始化分页 */
    function initFenYe(cPage){
    	$.ajax({
			 type: "get",
             url: "ajax/getComments_comments",
             data: {movieInfoId:${movieInfo.id},page:cPage},
             dataType: "json",
             success: function(data){
             	//转为json对象
             	parseJson(JSON.parse(data));
             	commentsPage = JSON.parse(data).num;
             	fenye();
             }
    	});
    }
    
    /* 解析json */
    function parseJson(data){
    	if(data.replyInners.length == 0){
			if(flag){
	    		$("#dynamic").append('<div class="info">暂无评论</div>');
				flag = false;			
			}    	
    		$("#example").addClass("hidden");
    	}else{	
    			$("#dynamic").empty();
	    		for(var i = 0; i < data.replyInners.length;i++){
		    		var html = '<div class="panel-body" style="border-bottom: 2px solid grey">'+
		                    ' <span id="name"><img src=\"'+data.replyInners[i].touxiangUrl+'\" style="height:33px;width:33px;"><a href="./otherzhuye?uid='+data.replyInners[i].id+'" >'+data.replyInners[i].name+'</a></span> ' +
		                    '<p>'+ data.replyInners[i].message +'</p>'+
		                    '<div class="pull-right">'+
		                    '<p style="font-size: 12px;color: orange">'+data.replyInners[i].date+'</p>'+
		                    '</div>'+
		                    '</div>';
	    			$("#dynamic").append(html);
    			} 
    	}
    }
    
    function fenye() {
			$('#pageLimit').bootstrapPaginator({
				//当前的请求页面。
				currentPage: currentPage,
				//一共多少页。
				totalPages: commentsPage,
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
					initFenYe(page);
					currentPage = page;
         	   	}
			});
		}
		
		/* 发送评论 */
		function sendComments(){
			var message = $("#message").val();
			var checkCode = $("#checkCode").val();
			$.ajax({
				 type: "get",
	             url: "ajax/sendComments_comments",
	             data: {movieInfoId:${movieInfo.id},uid:uid,messages:message,checkCode:checkCode},
	             dataType: "json",
	             success: function(data){
	             	//转为json对象
	             	initFenYe(currentPage);
	             	/* 刷新验证码*/
	             	$("#validation").attr("src",'createImage?'+ Math.random());
	             	alert(data);
	             }  
			});
		}
		
		/* 获取movieflag */
		function getMovieFlag(){
			$.ajax({
				 type: "get",
	             url: "ajax/getMovieFlag_movieFlag",
	             data: {mid:${movie.id},uid:uid},
	             dataType: "json",
	             success: function(data){
	             	var json = JSON.parse(data);
	             	if(json.wantSee == 1){
		             	$("#wantSee").addClass("btn-primary");
		             	wantSee = 1;
	             	}
	             	if(json.seeBefore == 1){
		             	$("#seeBefore").addClass("btn-success");
		             	seeBefore = 1;
	             	}
	             	if(json.likeSee == 1){
		             	$("#likeSee").addClass("btn-warning");
		             	likeSee = 1;
	             	}
	             }  
			});
		}
		
		/* 改变movieflag */
		function saveMovieFlag(){
			$.ajax({
				 type: "get",
	             url: "ajax/saveMovieFlag_movieFlag",
	             data: {mid:${movie.id},uid:uid,likeSee:likeSee,wantSee:wantSee,seeBefore:seeBefore},
	             dataType: "json",
	             success: function(data){
	             }  
			});
		}
		
		function btnGroupClick(){
			$(".btn-group a").click(function(){
				if(check_login() == false && $(this).text().trim()  != "下载") {
					alert("清先登录");
					return;			
				}
			
				if($(this).text().trim()  == "想看"){
					if($(this).hasClass("btn-primary") == true){
						$(this).removeClass("btn-primary");
						wantSee = 0;
					}else{
						$(this).addClass("btn-primary");
						wantSee = 1;
					}
				}
				
				if($(this).text().trim() == "看过"){
					if($(this).hasClass("btn-success") == true){
						$(this).removeClass("btn-success");
						seeBefore = 0;
					}else{
						$(this).addClass("btn-success");
						seeBefore = 1;
					}
				}
				
				if($(this).text().trim() == "喜欢"){
					if($(this).hasClass("btn-warning") == true){
						$(this).removeClass("btn-warning");
						likeSee = 0;
					}else{
						$(this).addClass("btn-warning");
						likeSee = 1;
					}
				}
				saveMovieFlag();
			});
		};
		
		/* 检测浏览的痕迹 */
		function lookTrace(){
			$.ajax({
				 type: "get",
	             url: "ajax/saveRecentlyMovies_reids",
	             data: {mid:${movie.id},uid:uid,movieName:"${movie.movieName}"},
	             dataType: "json",
	             success: function(data){
	             }  
			});
		}
		
		/* 记录访问过 */
		function RecordUser(){
			$.ajax({
				type: "get",
	             url: "ajax/recordUser_reids",
	             data: {mid:${movie.id},uid:uid},
	             dataType: "json",
	             success: function(data){
	             }  
			});
		}
		
		/* 获取访问记录 */
		function getRecentlyWatchUser(){
			$.ajax({
				type: "get",
	             url: "ajax/getRecentlyWatchUser_reids",
	             data: {mid:${movie.id}},
	             dataType: "json",
	             success: function(data){
	             	var obj = JSON.parse(data);
	             	if (data != null && data != 'null'){
	         
	             		for(var i =0;i < obj.num;i++){
	             			var html = '<div class="col-xs-3 viewer-list-avatar">'+
								'<a data-toggle="popover" data-original-title href="./otherzhuye?uid='+obj.objects[i].uid+'"> <img style="height:55px;width:55px" '+
									'src="'+obj.objects[i].url+'">'+
								'</a></div>';
							$("#ruser").append(html);
	             		}
	             	}
	             }  
			});
		}
		
</script>
</html>