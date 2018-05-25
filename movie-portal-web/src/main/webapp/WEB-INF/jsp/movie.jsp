<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>电影大全</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
<!--引入图标-->
<link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
<!--导入中间主体的css-->
<link rel="stylesheet" href="./css/movie.css">
<!--导入中间主体的css-->
<link rel="stylesheet" href="./css/content.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="./bootstrap-3.3.7/dist/js/bootstrap.js"></script>
<script src="./js/bootstrap-paginator.min.js"></script>
  <style type="text/css">
        body{
            font-size: 14px;
            font-family: 'HanHei SC', 'PingFang SC', 'Helvetica Neue', 'Helvetica',
            'Microsoft YaHei', 'WenQuanYi Micro Hei', 'STHeitiSC-Light',
            'Arial', 'Segoe UI', sans-serif;
            background: rgb(245,245,245);
        }
        div{
            display: block;
        }
    </style>
</head>
<body>

	<jsp:include page="../common/head.jsp" flush="true" />
	<div data-tag="1" style="display: none" id="tag-index"></div>
	<!--主体-->
	<div class="container">
		<div class="row">
			<!--选项栏-->
			<div class="tag-box">
				<!--选项表格-->
				<table class="table" style="margin-bottom: 10px">
					<tbody>
						<tr>
							<td class="tag-span">年份</td>
							<td class="tags" id="year"><a href="#" class="tag" data-value="全部">全部</a>
								<a href="#" class="tag" data-value="2017">2017</a> <a href="#" class="tag" data-value="2016">2016</a>
								<a href="#" class="tag" data-value="2015">2015</a> <a href="#" class="tag" data-value="2014">2014</a>
								<a href="#" class="tag" data-value="2013">2013</a> <a href="#" class="tag" data-value="2012">2012</a>
								<a href="#" class="tag" data-value="2011">2011</a> <a href="#" class="tag" data-value="2010">2010</a>
								<a href="#" class="tag" data-value="2009">2009</a> <a href="#" class="tag" data-value="2008">2008</a>
								<a href="#" class="tag" data-value="2007">2007</a> <a href="#" class="tag" data-value="2006">2006</a>
								<a href="#" class="tag" data-value="2005">2005</a> <a href="#" class="tag" data-value="faraway">十年前</a>
							</td>
						</tr>
						<tr>
							<td class="tag-span">评分排序：</td>
							<td class="tags" id="score"><a data-value="全部" href="#" class="tag">全部</a>
								<a href="#" class="tag" data-value="9-10">9-10分</a> <a href="#" class="tag" data-value="8-9">8-9分</a>
								<a href="#" class="tag" data-value="7-8">7-8分</a> <a href="#" class="tag" data-value="6-7">6-7分</a>
								<a href="#" class="tag" data-value="5-6">5-6分</a></td>
						</tr>
						<tr>
							<td class="tag-span">国家地区:</td>
							<td class="tags" id="area"><a href="#" class="tag" data-value="全部">全部</a>
								<a href="#" class="tag" data-value="中国">中国</a> <a href="#" class="tag" data-value="美国">美国</a> <a
								href="#" class="tag" data-value="日本">日本</a> <a href="#" class="tag" data-value="韩国">韩国</a> <a
								href="#" class="tag" data-value="印度">印度</a> <a href="#" class="tag" data-value="法国">法国</a> <a
								href="#" class="tag" data-value="泰国">泰国</a> <a href="#" class="tag" data-value="英国">英国</a> <a
								href="#" class="tag" data-value="俄罗斯">俄罗斯</a> <a href="#" class="tag" data-value="加拿大">加拿大</a> <a
								href="#" class="tag" data-value="意大利">意大利</a></td>
						</tr>
						<tr>
							<td class="tag-span">影视类型:</td>
							<td class="tags" id="type">
								  <a href="#" class="tag" data-value="全部">全部</a> 
								  <a href="#" class="tag" data-value="传记">传记</a> 
								  <a href="#" class="tag" data-value="喜剧">喜剧</a> 
								  <a href="#" class="tag" data-value="剧情">剧情</a> 
								  <a href="#" class="tag" data-value="恐怖">恐怖</a> 
								  <a href="#" class="tag" data-value="动画">动画</a> 
								  <a href="#" class="tag" data-value="歌舞">歌舞</a> 
								  <a href="#" class="tag" data-value="悬疑">悬疑</a> 
								  <a href="#" class="tag" data-value="爱情">爱情</a> 
								  <a href="#" class="tag" data-value="惊悚">惊悚</a> 
								  <a href="#" class="tag" data-value="真人秀">综艺</a> 
								  <a href="#" class="tag" data-value="纪录">纪录</a> 
								  <a href="#" class="tag" data-value="青春">青春</a> 
								  <a href="#" class="tag" data-value="科幻">科幻</a> 
								  <a href="#" class="tag" data-value="冒险">冒险</a> 
								  <a href="#" class="tag" data-value="奇幻">魔幻</a> 
								  <a href="#" class="tag" data-value="动作">动作</a> 
								  <a href="#" class="tag" data-value="战争">战争</a> 
								  <a href="#" class="tag" data-value="情色">情色</a> 
								  <a href="#" class="tag" data-value="古装">古装</a> 
								  <a href="#" class="tag" data-value="灾难">灾难</a> 
								  <a href="#" class="tag" data-value="纪录片">纪录片</a> 
								  <a href="#" class="tag" data-value="同性">同性</a> 
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-xs-12" style="padding: 0 5px">
				<div class="col-xs-12">
					<div style="margin: 0 -10px">
						<!--存放图片链接-->
						<c:forEach items="${request.movies}" var="movie">
							<div class="col-xs-1-5 col-sm-4 col-xs-6 movie-item">
								<div class="movie-item-in">
									<a class="item-a" target="_blank" style="position:relative;display:block;" href="./movieinfo?mid=${movie.id}"> <img
									src="${movie.movieImgUrl}" onerror="this.onerror=null; this.src='./pmovie/../noimage.png'"> <!--清晰度--> <c:set
										var="clarity" scope="request" value="${movie.clarity}" /> 
										<c:choose>
											<c:when test="${clarity == '高清'}">
												<span class="cltag">高清</span>
											</c:when>
											<c:when test="${clarity == '超清'}">
												<span class="gqtag">超清</span>
											</c:when>
											<c:otherwise>
												<span class="otag">${movie.clarity}</span>
											</c:otherwise>
										</c:choose>
								</a>
								<!--列表小简介-->
								<div class="meta">
									<a class="title_a" target="_blank" title="${movie.movieName}"
										style="background-color: transparent">${movie.movieName}</a>
									<c:set var="score" value="${movie.score}" />
									<c:if test="${empty  score}">
										<em style="display: block;">--N/A分</em>
									</c:if>
									<c:if test="${not empty score }">
										<em style="display: block;">--${movie.score}分</em>
									</c:if>
									<div class="otherinfo">
										类型:
										<c:forTokens items="${movie.type}" delims="/" var="tag">
											<a target="_blank" href="#" class="movieclass">${tag}</a>
										</c:forTokens>
									</div>
								</div>
							</div>
								</div>
						</c:forEach>
					</div>
				</div>
				<!-- 分页 -->
				<div id="example" style="text-align: center">
					<ul id="pageLimit"></ul>
				</div>
			</div>
		</div>
	</div>

	<form id="movie_form" action=""  method="get" style="display:none;" accept-charset="utf-8">
		<input type="text" value="1" name="page"> 
		<input type="text" value="全部" name="year"> 
		<input type="text" value="全部" name="score"> 
		<input type="text" value="全部" name="area">
		<input type="text" value="全部" name="type">
	</form>
	<jsp:include page="../common/footer.html" flush="true"></jsp:include>
</body>
<script type="text/javascript">

		var befpage = ${page};
		var befyear = "${filterTag.year}";
		var befscore = "${filterTag.score}";
		var befarea = "${filterTag.area}";
		var beftype = "${filterTag.type}";
		
		$(function(){
			remberClaa();
 			changeActive(); 
			fenye();
		});
		
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
					befpage = page;
         	   		submitMovieForm();
         	   	}
			});
		}
		
		/* 给表单赋值 */
		function setForm(){
			$("#year").find("a").click(function(){
				$("#year > a").removeClass("active");				
				$(this).addClass("active");
			});			
		}
				
		/* 控制那个标签高亮的 */
		function changeActive(){
			$("#year").find("a").click(function(){
				$("#year > a").removeClass("active");				
				$(this).addClass("active");
				submitMovieForm();
			});	
			$("#score").find("a").click(function(){
				$("#score > a").removeClass("active");				
				$(this).addClass("active");
				submitMovieForm();
			});	
			$("#area").find("a").click(function(){
				$("#area > a").removeClass("active");				
				$(this).addClass("active");
				submitMovieForm();
			});	
			$("#type").find("a").click(function(){
				$("#type > a").removeClass("active");				
				$(this).addClass("active");
				submitMovieForm();
			});	
			
		}
		
		/* 填充movieform */
		function submitMovieForm(){
		
			var year = $("#year .active").attr("data-value");
			var score = $("#score .active").attr("data-value");
			var area = $("#area .active").attr("data-value");
			var type = $("#type .active").attr("data-value");
		
			$("#movie_form input[name='year']").attr("value",year);
			$("#movie_form input[name='score']").attr("value",score);
			$("#movie_form input[name='area']").attr("value",area);
			$("#movie_form input[name='type']").attr("value",type);
		
			if(year != befyear || score != befscore || area != befarea || type != beftype){
				$("#movie_form input[name='page']").attr("value",1);
			}else{
				$("#movie_form input[name='page']").attr("value",befpage);
			}
			
			$("#movie_form").submit();
		}
		
		/* 记住上次所按下的 */
		function remberClaa(){
			$("#year a").each(function(){
				$("#year  a").removeClass("active");				
				if($(this).attr("data-value") == befyear){
					$(this).addClass("active");
					return false;
				}
			});
			$("#score a").each(function(){
				$("#score  a").removeClass("active");				
				if($(this).attr("data-value") == befscore){
					$(this).addClass("active");
					return false;
				}
			});
			$("#area a").each(function(){
				$("#area  a").removeClass("active");				
				if($(this).attr("data-value") == befarea){
					$(this).addClass("active");
					return false;
				}
			});
			$("#type a").each(function(){
				$("#type  a").removeClass("active");				
				if($(this).attr("data-value") == beftype){
					$(this).addClass("active");
					return false;
				}
			});	
		}
	</script>
</html>