<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>${q}搜索</title>
<meta charset="UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
<!--引入图标-->
<link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
<!--导入搜索的css-->
<link rel="stylesheet" href="./css/search.css">
<link rel="stylesheet" href="./css/content.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="application/javascript" src="./js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="application/javascript"
	src="./bootstrap-3.3.7/dist/js/bootstrap.js"></script>
<script src="./js/bootstrap-paginator.min.js"></script>
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
		<ol class="breadcrumb" style="padding: 10px 15px">
			<li><a href="./index">首页</a></li>
			<li><a href="./movie">资源搜索</a></li>
			<li><a href="#" class="active">${q}</a></li>
		</ol>

		<c:forEach items="${movies}" var="movie">
			<div class="result-item">
				<div class="row">
					<div class="col-xs-2">
						<a target="_blank" href="./movieinfo?mid=${movie.id}"
							style="position: relative;display: block"> <img
							src="${movie.movieImgUrl}" class="img-thumbnail" alt=""> 
							<c:set value="${movie.clarity}" var="clarity"></c:set>
							<c:if test="${clarity == '高清'}">	<span class="gqtag">高清</span></c:if>
							<c:if test="${clarity == '超清'}">	<span class="cltag">超清</span></c:if>
						</a>
					</div>
					<div class="col-xs-7">
						<p class="movie-name">
							名称 <strong> <a target="_blank" href="./movieinfo?mid=${movie.id}">${movie.movieName}</a></strong>
							<span class="subtype">电影</span>
							<%-- <c:set value="${movie.isMovie}" var="ismovie"/>
							<c:if test="${ismovie == 0}"><span class="subtype">电影</span></c:if>
							<c:if test="${ismovie == 1}"><span class="subtype">电视剧</span></c:if>  --%>
						</p>
						<div class="intro">
							<p>
								上映时间：${movie.year} / 评分： 
								<c:set value="${movie.score}" var="score"></c:set>
								<c:if test="${not empty score}"><strong>${movie.score}</strong></c:if>
								<c:if test="${empty score}"><strong>N/A</strong></c:if>
								
							</p>
							<p class="movie-introduce">${movie.movieInfo.movieIntroduce}</p>
							<p>
								资源下载： <a target="_blank" href="./movieinfo?mid=${movie.id}">${movie.movieName}</a>
							</p>
						</div>
					</div>
					<div class="col-xs-3"></div>
				</div>
			</div>
		</c:forEach>
		<!-- 隐藏的表单 -->
		<form id="form" style="display: none;">
			<input type="text" name="page" value="1">
			<input type="text" name="q" value="${q}">
		</form>
		
		<!-- 分页 -->
		<div id="example" style="text-align: center">
			<ul id="pageLimit"></ul>
		</div>
	</div>
	<div style="height: 50px;clear: both;position: relative;"></div>
	<!--尾部-->
	<jsp:include page="../common/footer.html"></jsp:include>
</body>
	<script type="text/javascript">
		$(function(){
			fenye();
		});
		
		
		function submitForm(){
			$("form").submit();
		}
	
		function fenye() {
			$('#pageLimit').bootstrapPaginator({
				currentPage: ${page},
				//当前的请求页面。
				totalPages: ${pageNum},
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
         	   		$("#form input[name='page']").attr("value",page);
         	   		submitForm();
         	   	}
			});
		}
	</script>

</html>