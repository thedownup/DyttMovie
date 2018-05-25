<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>豆瓣排行榜</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
<!--导入douban250的css-->
<link rel="stylesheet" href="./css/douban250.css">
<!--引入图标-->
<link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="./bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="./js/bootstrap-paginator.min.js"></script>
<style type="text/css">
body {
	font-size: 14px;
	font-family: 'HanHei SC', 'PingFang SC', 'Helvetica Neue', 'Helvetica',
		'Microsoft YaHei', 'WenQuanYi Micro Hei', 'STHeitiSC-Light', 'Arial',
		'Segoe UI', sans-serif;
	background: rgb(245, 245, 245);
}
</style>
</head>
<body>
	<!-- 头部 -->
	<jsp:include page="../common/head.jsp"></jsp:include>
	<div data-tag="3" style="display: none" id="tag-index"></div>
	<!--豆瓣电影排名-->
	<div class="container">
		<div class="container-fluid">
			<c:forEach items="${doubans}" var="douban">
				<div class="row"
					style="width: 1050px;border-bottom: 2px dotted silver;padding-bottom: 10px">
					<div class="col-xs-2" style="width: 120px">
						<a class="ranking">${douban.rank}</a>
					</div>

					<div class="col-xs-8">
						<div class="row">
							<div class="col-xs-2"
								style="padding-left: 5px;width: 110px;overflow: hidden">
								<a style="cursor: pointer;"
									href="./search?q=${douban.movieName}">
								<img class="doubanimg" src="${douban.movieUrl}">
								</a>
							</div>
							<div class="col-xs-10">
								<a style="cursor: pointer;"
									href="./search?q=${douban.movieName}"><h3>${douban.movieName}</h3></a>
								<p style="font-size: 12px">${douban.movieIntroduce}</p>
							</div>
						</div>
					</div>
					<div class="col-xs-2">
						<a class="pull-right score">评分${douban.score}</a>
					</div>
				</div>
			</c:forEach>
			<!-- 分页 -->
			<div id="example" style="text-align: center;clear: both;position: relative;">
				<ul id="pageLimit"></ul>
			</div>
		</div>
		<!--尾部-->
		<footer class="footer" style="clear: both;">
			<div class="container">
				<div class="row">
					<div class="col-xs-12">
						<h6>免责声明： 本网站所有内容都是靠程序在互联网上自动搜集而来，仅供测试和学习交流。若侵犯了您的权益，请发邮件通知站长</h6>
					</div>
				</div>
			</div>
		</footer>
	</div>
	<form id="movie_form" action=""  method="get" style="display:none;" accept-charset="utf-8">
		<input type="text" value="1" name="page"> 
	</form>
</body>
	<script type="text/javascript">
	
		$(function(){
			fenye();
			randomLine();
		});
	
		function fenye() {
			$('#pageLimit').bootstrapPaginator({
				currentPage: ${page},
				//当前的请求页面。
				totalPages: 10,
				//一共多少页。
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
					$("#movie_form input[name='page']").attr("value",page);
					$("#movie_form").submit();
         	   	}
			});
		}
		
		/*  */
		function randomLine(){
		
			$(".ranking").each(function(){
  				  $(this).css("background-color",'#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6));
			});
			
			$(".score").each(function(){
  				  $(this).css("background-color",'#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6));
			});
		}
	</script>
</html>