<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>系统私信</title>
</head>
<body>
	<h2 style="text-align: center;">发布系统私信</h2>
	<hr>
	<form id="sixin" style="text-align: center;">
		<input class="easyui-textbox" data-options="multiline:true" maxlength="300" placeholder="请输入要发布的消息" style="width:300px;height:100px">
		<a id="bb" data-click="true" href="#" style="background-color: #F4606C;" class="easyui-linkbutton" >发布全体私信</a>
	</form>
	
	<table id="dg" style="width:100%"></table>

	<script type="text/javascript">
	
		var f = true;

		$(function(){
			init();
			if (f) {
				f = false;
				init();
			} 
 			init();
			Initpagination();
			send_message();
		});
		
		
		function init() {
			$('#dg').datagrid({
				url : './privatemessage/get',
				pagination : true,
				rownumbers : true,
				showFooter : true,
				singleSelect : true,
				checkbox : true,
				striped : true,
				resize : true,
				fitColumns : true,
				resizable : true,
				loadMsg : '加载数据中',
				columns : [[
					{
						field : 'sendDate',
						title : '日期',
						width : 50
					},
					{
						field : 'message',
						title : '消息内容',
						width : 50
					}
				]]
			});
		}
		
		/*分页设置*/
		function Initpagination() {
			var p = $("#dg").datagrid('getPager');
			$(p).pagination({
				onSelectPage : function(pageNumber, pageSize) {
					$.ajax({
						type : "get",
						url : "./privatemessage/get",
						dataType : "json",
						success : function(data) {
							$('#dg').datagrid('loadData', data);
						}
					});
				}
			});
		}
		
		/* 发送事件 */
		function send_message(){
			$("#bb").click(function(){
				if($(".easyui-textbox").val() == ""){
					$.messager.alert('提示', "私信不能为空");
				} else {
					$.ajax({
						type : "POST",
						url : "./privatemessage/send",
						data : {
							message : $(".easyui-textbox").val()
						},
						dataType : "json",
						success : function(data) {
							init();
							$.messager.alert('提示', data.msg);
							//清空私信框
							$(".easyui-textbox").val("");
						}
					});
				}
			});
		}
	
	</script>
</body>
</html>