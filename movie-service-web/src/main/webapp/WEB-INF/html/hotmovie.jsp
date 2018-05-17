<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<body>

	<div class="wu-toolbar-button" style="background-color: #FAFAFA">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add"
			onclick="openAdd()" plain="true">添加</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()"
			plain="true">修改</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
	</div>

	<table id="dghm" style="width:100%"></table>

	<!--添加热门电影-->
	<div id="save_hotmovie" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){hmSave()}},{text:'Close',handler:function(){mClose()}}]"
		style="width:500px; padding:10px;">
		<form id="save_hotmovie_form" method="post">
			<table >
				<tr>
					<td width="60" align="right">电影id:</td>
					<td><input type="text" name="id"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">热门分数:</td>
					<td><input type="text" name="score"
						class="wu-text easyui-validatebox" /></td>
				</tr>
			</table>
				<tr > 
				</tr>
		</form>
	</div>



	<!--修改热门电影-->
	<div id="update_hotmovie" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){hmUpdate()}},{text:'Close',handler:function(){mClose()}}]"
		style="width:500px; padding:10px;">
		<form id="update_hotmovie_form" method="post">
			<table >
				<tr>
					<td width="60" align="right">热门分数:</td>
					<td><input type="text" name="newScore"
						class="wu-text easyui-validatebox" /></td>
				</tr>
			</table>
				<tr > 
					<td><input type="text" name="id" style="display: none" /></td>
					<td><input type="text" name="movieName" style="display: none" /></td>
					<td><input type="text" name="movieImgUrl" style="display: none" /></td>
					<td><input type="text" name="year" style="display: none" /></td>
					<td><input type="text" name="hid" style="display: none" /></td>
					<td><input type="text" name="movie" style="display: none" /></td>
					<td><input type="text" name="score" style="display: none" /></td>
				</tr>
		</form>
	</div>
	

	<script>
		$(function() {
			init(0, 10);
		 	Initpagination(); 
		});
		
		function init(page, num) {
			$('#dghm').datagrid({
				url : './hotmovie/get',
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
						field : 'ck',
						checkbox : true,
						width : 100
					},
					{
						field : 'id',
						title : '电影id',
						width : 30
					},
					{
						field : 'movieName',
						title : '电影名字',
						width : 30
					},
					{
						field : 'movieImgUrl',
						title : '电影图片地址',
						width : 30
					},
					{
						field : 'year',
						title : '上映年份',
						width : 30,
						hidden : true
					},
					{
						field : 'score',
						title : '热门分数',
						width : 30,
						sortable:true
					},
					{
						field : 'hid',
						title : '隐藏字段',
						width : 30,
						hidden : true
					},
					{
						field : 'movie',
						title : '隐藏字段',
						width : 30,
						hidden : true
					}
				]]
			});
		}
	
		/*分页设置*/
		function Initpagination() {
			var p = $("#dghm").datagrid('getPager');
			$(p).pagination({
				onSelectPage : function(pageNumber, pageSize) {
					$.ajax({
						type : "POST",
						url : "./hotmovie/get",
						dataType : "json",
						success : function(data) {
							$('#dghm').datagrid('loadData', data);
						}
					});
				}
			});
		}
		
		function remove(){
			var len = $("#dghm").datagrid("getChecked").length;
			if (len == 0) {
				$.messager.confirm('提示', "选择一行进行删除");
			} else {
				var id = $("#dghm").datagrid("getChecked")[0].id;
				var movieName = $("#dghm").datagrid("getChecked")[0].movieName;
				var movieImgUrl = $("#dghm").datagrid("getChecked")[0].movieImgUrl;
				var score = $("#dghm").datagrid("getChecked")[0].score;
				var hid = $("#dghm").datagrid("getChecked")[0].hid;
				var year = $("#dghm").datagrid("getChecked")[0].year;
				$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : "./hotmovie/delete",
							data : {
								id : id,
								movieName : movieName,
								movieImgUrl : movieImgUrl,
								score : score,
								hid : hid,
								movie : 'false',
								year : year
							},
							dataType : "json",
							success : function(data) {
								$.messager.alert('删除消息', data.msg);
								$('#dghm').datagrid('reload');
							}
						});
					}
				});
			}
		}
		
		/* 修改热门电影 */
		function openEdit(){
			var len = $("#dghm").datagrid("getChecked").length;
			var hotmovie = $("#dghm").datagrid("getChecked");
			$("#update_hotmovie_form input").each(function(){
				$(this).val("");
			});
			if (len != 1) {
				$.messager.confirm('提示', "选择一行进行修改");
			} else {
				/*进行回写操作*/
				$("#update_hotmovie_form input[name='score']").val(hotmovie[0].score);
				$("#update_hotmovie_form input[name='movieName']").val(hotmovie[0].movieName);
				$("#update_hotmovie_form input[name='id']").val(hotmovie[0].id);
				$("#update_hotmovie_form input[name='movieImgUrl']").val(hotmovie[0].movieImgUrl);
				$("#update_hotmovie_form input[name='year']").val(hotmovie[0].year);
				$("#update_hotmovie_form input[name='hid']").val(hotmovie[0].hid);
				$("#update_hotmovie_form input[name='movie']").val(hotmovie[0].movie);
				$("#update_hotmovie").dialog({
					title : '热门修改',
					modal : true,
				});
				/* 初始化日期 */
				$("#update_hotmovie").dialog('open');
			}
		}
		
		/* 更新修改 */
		function hmUpdate(){
			if(isNaN($("#update_hotmovie_form input[name='newScore']").val()) || $("#update_hotmovie_form input[name='newScore']").val() == "" ||$("#update_hotmovie_form input[name='newScore']").val() >= 500){
				if($("#update_hotmovie_form input[name='newScore']").val() >= 500){
					$.messager.confirm('提示', "分数不能大于500");
					return;
				}
				$.messager.confirm('提示', "非法分数");
			} else {
				$.ajax({
					type : "POST",
					url : "./hotmovie/update",
					data : $("#update_hotmovie_form").serialize(),
					dataType : "json",
					success : function(data) {
						$.messager.alert('修改消息', data.msg);
						$("#update_hotmovie").dialog('close');
						$('#dghm').datagrid("reload");
					}
				});
			}
			$("#update_hotmovie_form input").each(function(){
				$(this).val("");
			});
		}
		
		/* 添加热门电影  */
		function openAdd(){
			$("#save_hotmovie").dialog({
					title : '热门电影添加',
					maximizable : true,
					resizable : true,
					modal : true
				});
			$("#save_hotmovie").dialog('open');
		}
		
		/* 添加 */
		function hmSave(){
			if(isNaN($("#save_hotmovie_form input[name='score']").val()) || $("#save_hotmovie_form input[name='score']").val() == "" || $("#save_hotmovie_form input[name='score']").val() >= 500){
				if($("#save_hotmovie_form input[name='score']").val() >= 500){
					$.messager.confirm('提示', "分数不能大于500");
					return;
				}
				$.messager.confirm('提示', "非法分数1");
			} else {
				$.ajax({
					type : "POST",
					url : "./hotmovie/save",
					data : $("#save_hotmovie_form").serialize(),
					dataType : "json",
					success : function(data) {
						$.messager.alert('修改消息', data.msg);
						$("#save_hotmovie").dialog('close');
						$('#dghm').datagrid("reload");
					}
				});
			}
		}
		
		/* 总关闭按钮 */
		function mClose() {
			$("#update_hotmovie").dialog('close');
			$("#save_hotmovie").dialog('close');
		}
		
	</script>
</body>
</html>
