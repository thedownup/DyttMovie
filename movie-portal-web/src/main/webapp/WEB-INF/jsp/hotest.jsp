<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>douban250</title>
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
	<div data-tag="4" style="display: none" id="tag-index"></div>
	<!--豆瓣电影排名-->
	<div class="container">
		<div class="container-fluid">
			<div class="row"
				style="width: 1050px;border-bottom: 2px dotted silver;padding-bottom: 10px">
				<div class="col-xs-2" style="width: 120px">
					<a class="ranking">1</a>
				</div>

				<div class="col-xs-8">
					<div class="row">
						<div class="col-xs-2"
							style="padding-left: 5px;width: 110px;overflow: hidden">
							<img class="doubanimg"
								src="http://img1.xmspc.com/uploads/movie-poster/912.jpg">
						</div>
						<div class="col-xs-10">
							<h3>电影名字</h3>
							<p style="font-size: 12px">段小楼（张丰毅）与程蝶衣（张国荣）是一对打小一起长大的师兄弟，两人一个演生，一个饰旦，一向配合天衣无缝，尤其一出《霸王别姬》，更是誉满京城，为此，两人约定合演一辈子《霸王别姬》。但两人对戏剧与人生关系的理解有本质不同，段小楼深知戏非人生，程蝶衣则是人戏不分。
								段小楼在认为该成家立业之时迎娶了名妓菊仙（巩俐），致使程蝶衣认定菊仙是可耻的第三者，使段小楼做了叛徒，自此，三人围绕一出《霸王别姬》生出的爱恨情仇战开始随着时代风云的变迁不断升级，终酿成悲剧。</p>
						</div>
					</div>
				</div>

				<div class="col-xs-2">
					<a class="pull-right score">评分8.0</a>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../common/footer.html" flush="true"></jsp:include>
</body>
	<script type="text/javascript">
		$(function(){
			$("#asd").html("s");
		});
	</script>
</html>