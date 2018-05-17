<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>电影天堂</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
    <!--引入图标-->
    <link rel="stylesheet" href="./css/glyphicons-halflings-regular.svg">
    <!--导入header的css-->
    <link rel="stylesheet" href="./css/head.css">
    <!--导入中间主体的css-->
    <link rel="stylesheet" href="./css/content.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="./js/jquery-3.3.1.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="./bootstrap-3.3.7/dist/js/bootstrap.js"></script>
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
    <!-- 头部 -->
    <jsp:include page="../common/head.jsp"></jsp:include>
    <div data-tag="0" style="display: none" id="tag-index"></div> 
    <!--主体内容-->
    <div class="container">
       <!--轮播主件 bootrap Carousel.js-->
        <div id="carousel" class="carousel slide" data-ride="carousel">
            <!-- 圆点按钮 -->
            <ol class="carousel-indicators">
            	<c:forEach varStatus="stauts" items="${rollings}" var="rolling">
            		<c:if test="${stauts.index == 0}"> <li data-target="#carousel-example-generic" data-slide-to="${stauts.index}" class="active"></li></c:if>
            		<c:if test="${stauts.index != 0}"> <li data-target="#carousel-example-generic" data-slide-to="${stauts.index}"></li></c:if>
            	</c:forEach>
            </ol>

            <!-- 图片放在这里 -->
            <div class="carousel-inner" role="listbox">
	            <c:forEach varStatus="stauts" items="${rollings}" var="rolling">
	            	<c:if test="${stauts.index == 0}">
						<div class="item active">
	                    	<img src="${rolling.bigImageUrl}" alt="...">
		                    <div class="carousel-caption"> </div>
	              		</div>	            	
	            	</c:if>
	            	<c:if test="${stauts.index != 0}">
	            		<div class="item">
	                    	<img src="${rolling.bigImageUrl}" alt="...">
		                    <div class="carousel-caption"> </div>
	              		</div>	    
	            	</c:if>
	            </c:forEach>
            </div>

            <!-- 控制左右两边 -->
            <a id="left" class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a id="right" class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>



        <div class="row">
            <!--热门电影-->
            <div class="col-xs-12">
                <h2>热门电影</h2>
                <div style="margin: 0 -10px">
                  	<c:forEach items="${hotMovies}" var="hotMovie" >
	                  		<!--存放图片链接-->
	                    <div class="col-xs-1-5 movie-item">
								<a class="item-a" target="_blank" style="position: relative;display: block" href="./movieinfo?mid=${hotMovie.id}"> <img
									src="${hotMovie.movieImgUrl}"> <!--清晰度--> <c:set
										var="clarity" scope="request" value="${hotMovie.clarity}" /> <c:if
										test="${hotMovie == '高清'}">
										<span class="cltag">高清</span>
									</c:if> <c:if test="${hotMovie == '超清'}">
										<span class="gqtag">超清</span>
									</c:if>
								</a>
								<!--列表小简介-->
								<div class="meta">
									<a class="title_a" target="_blank" title="${hotMovie.movieName}"
										style="background-color: transparent">${hotMovie.movieName}</a>
									<c:set var="score" value="${hotMovie.score}" />
									<c:if test="${empty  score}">
										<em style="display: block;">--N/A分</em>
									</c:if>
									<c:if test="${not empty score }">
										<em style="display: block;">--${hotMovie.score}分</em>
									</c:if>
									<div class="otherinfo">
										类型:
										<c:forTokens items="${hotMovie.type}" delims="/" var="tag">
											<a target="_blank" href="#" class="movieclass">${tag}</a>
										</c:forTokens>
									</div>
								</div>
							</div>
                  	</c:forEach>
                </div>
            </div>

            <!--缓冲带-->
            <div class="col-xs-12">
                <div style="width: 100%;height: 20px;border: none;background: #FFF"></div>
            </div>
<!-- 
            最新电影
            <div class="col-xs-12">
                <h2>最新电影</h2>
                <div style="margin: 0 -10px">
                    存放图片链接
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            重复模块
            <div class="col-xs-12">
                <div style="margin: 0 -10px">
                    存放图片链接
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1-5 movie-item">
                        <a style="position: relative;display: block">
                            <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                            清晰度
                            <span class="cltag">高清</span>
                        </a>
                        列表小简介
                        <div class="meta">
                            <a target="_blank" title="">预兆</a>-<em>6分</em>
                            <div class="otherinfo">
                                类型:
                                <a target="_blank" href="#" class="movieclass">科幻</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> -->

        <!--查看更多-->
        <div class="col-xs-12" style="margin-bottom: 30px">
            <a href="./movie" class="btn btn-success btn-block" style="margin-top: 30px;display: block">
                <span style="text-align: center;display: block">查看更多</span>
            </a>
        </div>
    </div>
    <!--尾部-->
 	 <jsp:include page="../common/footer.html"></jsp:include>
</body>
<script type="text/javascript">
	$(function(){
		$('#carousel').carousel({
			interval: 3000
		});
		
		$("#left").click(function(){
			$("#carousel").carousel('prev');
		});
		
		$("#right").click(function(){
			$("#carousel").carousel('next');
		});
	});
</script>
</html>