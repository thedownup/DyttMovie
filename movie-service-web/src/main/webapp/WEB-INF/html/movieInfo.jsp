<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<body>

	<div class="wu-toolbar-button" style="background-color: #FAFAFA">
	<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a> --> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit1()" plain="true">修改</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a> 
		<input id="search" class="easyui-searchbox" style="width:300px" data-options="menu:'#mm'" />
		<div id="mm" style="width:150px" >
			<div data-options="name:'item1'">电影的id</div>
		</div>
	</div>

	<table id="dgmi" style="width:100%"></table>
	
	<!--添加电影-->
	<div id="save_movieInfo" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){miSave()}},{text:'Close',handler:function(){mClose()}}]"
		style="width:500px; padding:10px;">
		<form id="save_movieInfo_form" method="post">
			<table >
			<!-- 	<button onclick="increase_input()" class="increase_input easyui-linkbutton" type="button" >增加资源对数</button>
				<div id="links">
					<div id="input_clone">
						下载链接名字<input type="text" name="LinkName" class="wu-text easyui-validatebox" /></br>下载链接地址<input type="text" name="LinkUrl" class="wu-text easyui-validatebox" /></br>
					</div>
				</div> -->
				<tr>
					<td width="60" align="right">导演:</td>
					<td><input type="text" name="director"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">演员:</td>
					<td><input type="text" name="starring"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">编剧:</td>
					<td><input type="text" name="writers" 
						class="wu-text" /></td>
				</tr>
				<tr>
					<td width="60" align="right">电影类型:</td>
					<td><input type="text" name="type" data-options="min:0,max:1" id="yz"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">语言:</td>
					<td><input type="text" name="language" 
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">电影别名:</td>
					<td><input type="text" name="alias"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">片长:</td>
					<td><input type="text"
						 name="rTime"
						class="easyui-validatebox wu-text" /></td>
				</tr>
				<tr>
					<td align="right">电影简介:</td>
					<td><input type="text" name="movieIntroduce"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				
				<tr>
					<td align="right">电影简介:</td>
					<td><input type="text" name="movieIntroduce"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				
			</table>
				<tr > 
					<td><input type="text" name="id" style="display: none" /></td>
				</tr>
		</form>
	</div>
	
	<!--更新电影-->
	<div id="change_movieInfo" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){miUpdate()}},{text:'Close',handler:function(){mClose()}}]"
		 data-options="novalidate:true"
		style="width:500px; padding:10px;">
		<form id="change_movieInfo_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">导演:</td>
					<td><input type="text" name="director"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">演员:</td>
					<td><input type="text" name="starring"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">编剧:</td>
					<td><input type="text" name="writers" 
						class="wu-text" /></td>
				</tr>
				<tr>
					<td width="60" align="right">电影类型:</td>
					<td><input type="text" name="type" data-options="min:0,max:1" id="yz"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">语言:</td>
					<td><input type="text" name="language" 
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">电影别名:</td>
					<td><input type="text" name="alias"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">片长:</td>
					<td><input type="text"
						 name="rTime"
						class="easyui-validatebox wu-text" /></td>
				</tr>
				<tr>
					<td align="right">电影简介:</td>
					<td><input type="text" name="movieIntroduce"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">电影下载链接:</td>
					<td><input type="text" name="link"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><input type="text" name="id" style="display: none" /></td>
				</tr>
			</table>
			
		</form>
	</div>

	<script>
		$(function() {
			init(0, 10);
		 	Initpagination(); 
		 	search();
		});
	
		/*分页设置*/
		function Initpagination() {
			var p = $("#dgmi").datagrid('getPager');
			$(p).pagination({
				onSelectPage : function(pageNumber, pageSize) {
					$.ajax({
						type : "POST",
						url : "./movieInfo/get",
						data : {
							page : pageNumber,
							num : pageSize
						},
						dataType : "json",
						success : function(data) {
							$('#dgmi').datagrid('loadData', data);
						}
					});
				}
			});
		}
	
		function init(page, num) {
			$('#dgmi').datagrid({
				url : './movieInfo/get?page=' + page + "&num=" + num,
				pagination : true,
				rownumbers : true,
				showFooter : true,
				resize : true,
				singleSelect : false,
				checkbox : true,
				striped : true,
				fitColumns : true,
				resizable : true,
				loadMsg : '加载数据中',
				columns : [ [
					{
						field : 'ck',
						checkbox : true,
						width : 100
					},
					{
						field : 'id',
						title : 'id',
						width : 100
					},
					{
						field : 'director',
						title : '导演',
						width : 200
					},
					{
						field : 'starring',
						title : '演员',
						width : 200
					},
					{
						field : 'writers',
						title : '编剧',
						width : 200
					},
					{
						field : 'type',
						title : '电影类型',
						width : 200
					},
					{
						field : 'language',
						title : '语言',
						width : 200
					},
					{
						field : 'alias',
						title : '电影别名',
						width : 200
					},
					{
						field : 'rTime',
						title : '片长',
						width : 200
					},
					{
						field : 'movieIntroduce',
						title : '电影简介',
						width : 200,
					},
					{
						field : 'link',
						title : '电影链接',
						width : 500,
					}
				] ]
			});
		}
		
		/* 电影删除 */
		function remove(){
			var len = $("#dgmi").datagrid("getChecked").length;
			
			
			if (len == 0) {
				$.messager.confirm('提示', "选择一行进行删除");
			} else {
				var id = $("#dgmi").datagrid("getChecked");
				var ids = "";
				$.each(id,function(index, obj){
					if((index+1) == len) ids += obj.id;
					else ids = ids + obj.id +",";
				})
				$.messager.confirm('确认', '您确认想要删除记录吗电影也将被删除？', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : "./movieInfo/delete",
							data : {
								ids : ids
							},
							dataType : "json",
							success : function(data) {
								$.messager.alert('删除消息', data.msg);
								$('#dgmi').datagrid('reload');
							}
						});
					}
				});
			}
		}
		
		function openEdit1(){
			var len = $("#dgmi").datagrid("getChecked").length;
			var movieInfo = $("#dgmi").datagrid("getChecked");
			$("#change_movieInfo_form input").each(function(){
				$(this).val("");
			});
			if (len != 1) {
				$.messager.confirm('提示', "选择一行进行修改");
			} else {
				var movieId = movieInfo[0].id;
				/*进行回写操作*/
				$("#change_movieInfo_form input[name='director']").val(movieInfo[0].director);
				$("#change_movieInfo_form input[name='starring']").val(movieInfo[0].starring);
				$("#change_movieInfo_form input[name='writers']").val(movieInfo[0].writers);
				$("#change_movieInfo_form input[name='type']").val(movieInfo[0].type);
				$("#change_movieInfo_form input[name='language']").val(movieInfo[0].language);
				$("#change_movieInfo_form input[name='alias']").val(movieInfo[0].alias);
				$("#change_movieInfo_form input[name='rTime']").val(movieInfo[0].rTime);
				$("#change_movieInfo_form input[name='movieIntroduce']").val(movieInfo[0].movieIntroduce);
				$("#change_movieInfo_form input[name='id']").val(movieInfo[0].id);
				$("#change_movieInfo_form input[name='link']").val(movieInfo[0].link);
				
				$("#change_movieInfo").dialog({
					title : '电影详情修改',
					modal : true,
				});
				/* 初始化日期 */
				$("#change_movieInfo").dialog('open');
			}
		}
		
		/* 电影详情更新 */
		function miUpdate(){
			$.ajax({
				type : "POST",
				url : "./movieInfo/update",
				data : $("#change_movieInfo_form").serialize(),
				dataType : "json",
				success : function(data) {
					$.messager.alert('修改消息', data.msg);
					$("#change_movieInfo").dialog('close');
					$('#dgmi').datagrid("reload");
				}
			});
		}
		
		/* 增加电影 */
		function openAdd(){
			$("#save_movieInfo").dialog({
					title : '电影详情添加',
					maximizable : true,
					resizable : true,
					modal : true
				});
			$("#save_movieInfo").dialog('open');
		}
		
		/* 搜索按钮 */
		function search(){
			$("#search").searchbox({
				searcher : function(value,name){
					if(!(value != "" && !isNaN(value))){
						$.messager.alert('提示消息', "请填入正确格式的id号");
					} else {
						$.ajax({
							type : "POST",
							url : "./movieInfo/search",
							data : {mid:value},
							dataType : "json",
							success : function(data) {
								if(data.total == 0){
										$.messager.alert('提示消息', "没有此id所对应的电影");
								} else {
									$("#change_movieInfo").dialog('close');
									$('#dgmi').datagrid('loadData',data);								
								}
							}
						}); 
					}
				}
			});
		}
		
		/* 电影详情的添加 */
		function miSave(){
			$.ajax({
				type : "POST",
				url : "./movieInfo/save",
				data : $("#save_movieInfo_form").serialize(),
				dataType : "json",
				success : function(data) {
					$.messager.alert('修改消息', data.msg);
					$("#save_movieInfo").dialog('close');
					$('#dgmi').datagrid("reload");
				}
			});
		}
		
		/* 增加链接的条数 */
		function increase_input(){
			/* $("#input_clone").append('下载链接名字<input type="text" name="LinkName" class="wu-text easyui-validatebox" /></br>下载链接地址<input type="text" name="LinkUrl" class="wu-text easyui-validatebox" /></br>'); */
			$("#save_movieInfo").dialog('open');
		}
		
		/* 总关闭按钮 */
		function mClose() {
			$("#change_movieInfo").dialog('close');
			$("#save_movieInfo").dialog('close');
			$("#input_clone").remove();
			$("#links").append('下载链接名字<input type="text" name="LinkName" class="wu-text easyui-validatebox" /></br>下载链接地址<input type="text" name="LinkUrl" class="wu-text easyui-validatebox" /></br>');
		}
		
	</script>
</body>
</html>
