<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>movie</title>
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
</head>
<body>

	<jsp:include page="../common/head.jsp" flush="true"/>
	<div data-tag="2" style="display: none" id="tag-index"></div>
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
                            <td class="tags">
                                <a href="#" class="tag active">2017</a>
                                <a href="#" class="tag">2016</a>
                                <a href="#" class="tag">2015</a>
                                <a href="#" class="tag">2014</a>
                                <a href="#" class="tag">2013</a>
                                <a href="#" class="tag">2012</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tag-span">评分排序：</td>
                            <td class="tags">
                                <a href="#" class="tag active">全部</a>
                                <a href="#" class="tag">9-10</a>
                                <a href="#" class="tag">2015</a>
                                <a href="#" class="tag">2014</a>
                                <a href="#" class="tag">2013</a>
                                <a href="#" class="tag">2012</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tag-span">国家地区:</td>
                            <td class="tags">
                                <a href="#" class="tag active">2017</a>
                                <a href="#" class="tag">2016</a>
                                <a href="#" class="tag">2015</a>
                                <a href="#" class="tag">2014</a>
                                <a href="#" class="tag">2013</a>
                                <a href="#" class="tag">2012</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tag-span">影视类型:</td>
                            <td class="tags">
                                <a href="#" class="tag active">2017</a>
                                <a href="#" class="tag">2016</a>
                                <a href="#" class="tag">2015</a>
                                <a href="#" class="tag">2014</a>
                                <a href="#" class="tag">2013</a>
                                <a href="#" class="tag">2012</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-xs-12" style="padding: 0 5px">
                <div class="col-xs-12">
                    <div style="margin: 0 -10px">
                        <!--存放图片链接-->
                        <div class="col-xs-1-5 movie-item">
                            <a style="position: relative;display: block">
                                <img src="http://img1.xmspc.com/uploads/movie-poster/j6dktpnsxnl2.jpg">
                                <!--清晰度-->
                                <span class="cltag">高清</span>
                            </a>
                            <!--列表小简介-->
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
                                <!--清晰度-->
                                <span class="cltag">高清</span>
                            </a>
                            <!--列表小简介-->
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
                                <!--清晰度-->
                                <span class="cltag">高清</span>
                            </a>
                            <!--列表小简介-->
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
                                <!--清晰度-->
                                <span class="cltag">高清</span>
                            </a>
                            <!--列表小简介-->
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
                                <!--清晰度-->
                                <span class="cltag">高清</span>
                            </a>
                            <!--列表小简介-->
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
            </div>
        </div>
    </div>
    
    <jsp:include page="../common/footer.html" flush="true"></jsp:include>

</body>
</html>