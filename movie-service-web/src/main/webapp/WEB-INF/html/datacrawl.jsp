<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>datacrawl</title>
</head>
<body>
	<div class="group-a">
		<h2>数据爬取</h2>
	<div>
		<div  style="margin-bottom: 15px;"><a id="updatedyttnewmovie" data-click="true" href="#" style="background-color: #19CAAD;" class="easyui-linkbutton" >爬取更新电影天堂最新电影模块</a></div>
		<div  style="margin-bottom: 15px;"><a id="updatedyttchinamovie" data-click="true" href="#" style="background-color: #D1BA74;" class="easyui-linkbutton" >爬取更新电影天堂内地电影模块</a></div>
		<div  style="margin-bottom: 15px;"><a id="updatedyttoumeimovie" data-click="true" href="#" style="background-color: #F4606C;" class="easyui-linkbutton" >爬取更新电影天堂欧美电影模块</a></div>
	</div>
	<hr>
	
	<h2>97电影网爬取</h2>
		<div  style="margin-bottom: 15px;"><a id="updatejiuqimovie" data-click="true" href="#" style="background-color: #A0EEE1;" class="easyui-linkbutton" >爬取更新97电影</a></div>
	<hr>
	
	<h2>更新豆瓣推荐电影</h2>
		<div style="margin-bottom: 15px;"><a id="douban" data-click="true" href="#" style="background-color: #A0E001;" class="easyui-linkbutton" >爬取更新豆瓣电影</a></div>
	<hr>
	
	<h2>手动更新代理池ip</h2>
		<div style="margin-right: 15px;display: inline;"><a id="updateproxyip" data-click="true" href="#" style="background-color: #ECAD9E;" class="easyui-linkbutton" >更新ip代理池</a></div>
		<div style="margin-right: 15px;display: inline;"><a id="getproxynum" data-click="true" href="#" style="background-color: #ECCC9E;" class="easyui-linkbutton" >查看ip代理池数量</a></div>
		<div style="margin-right: 15px;display: inline;"><a id="deleteproxyid" data-click="true" href="#" style="background-color: #EDCD2E;" class="easyui-linkbutton" >去除无效ip</a></div>
	<hr>
	<h2>合并重复的电影</h2>
		<div style="margin-right: 15px;display: inline;"><a id="merge" data-click="true" href="#" style="background-color: #EACDCE;" class="easyui-linkbutton" >合并重复的电影</a></div>
	</div>
	<script type="text/javascript">
		
		$(function(){
			$(".group-a a").click(function(){
				if(!($(this).attr("data-click") == 'true')) {
					return;
				}
				
				$(this).attr("data-click","false");
				var obj = $(this);
				$(this).linkbutton('disable');
				var id = $(this).attr("id");
				var messge = $(this).text();
				$.messager.confirm('提示消息', messge+"?",function(r){
					if(r){
					   $.ajax({
							type : "POST",
							url : "./data/"+id,
							dataType : "json",
							success : function(data) {
								$.messager.alert('提示消息', data.msg);
							},
							complete : function(){
								obj.linkbutton('enable');
								obj.attr("data-click","true");
							}
						}); 
					} else {
						$.messager.alert('提示', "取消操作");
						obj.linkbutton('enable');
						obj.attr("data-click","true");
					}
				});
			});
		});

	</script>	
</body>
</html>