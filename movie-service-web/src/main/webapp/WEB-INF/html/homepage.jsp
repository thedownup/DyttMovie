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

	<!--添加滚动-->
	<div id="save_rolling" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){rSave()}},{text:'Close',handler:function(){mClose()}}]"
		style="width:500px; padding:10px;">
		<form id="save_rolling_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">电影id:</td>
					<td><input type="text" name="mid" class="wu-text" /></td>
				</tr>
				<tr>
					<td width="100" align="right">滚动图:</td>
					<td><input type="text" name="bigImageUrl" class="wu-text" /></td>
				</tr>
			</table>
		</form>
	</div>


	<!--修改动-->
	<div id="update_rolling" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){rlSave()}},{text:'Close',handler:function(){mClose()}}]"
		style="width:500px; padding:10px;">
		<form id="update_rolling_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">电影id:</td>
					<td><input type="text" name="mid" class="wu-text" /></td>
				</tr>
				<tr>
					<td width="100" align="right">滚动图:</td>
					<td><input type="text" name="bigImageUrl" class="wu-text" /></td>
				</tr>
				<td><input type="text" name="id" class="wu-text" style="display: none;"/></td>
			</table>
		</form>
	</div>



	<table id="dgrl" style="width:100%"></table>

	<script>
		$(function() {
			init(0, 10);
		 	Initpagination(); 
		});
		
		function init(page, num) {
			$('#dgrl').datagrid({
				url : './rolling/get',
				pagination : true,
				rownumbers : true,
				showFooter : true,
				resize : true,
				singleSelect : true,
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
						width : 30,
						sortable:true
					},
					{
						field : 'mid',
						title : '电影id',
						width : 30
					},
					{
						field : 'bigImageUrl',
						title : '背景大图',
						width : 100
					}
				] ]
			});
		}
	
		/*分页设置*/
		function Initpagination() {
			var p = $("#dgrl").datagrid('getPager');
			$(p).pagination({
				onSelectPage : function(pageNumber, pageSize) {
					$.ajax({
						type : "get",
						url : "./rolling/get",
						dataType : "json",
						success : function(data) {
							$('#dgrl').datagrid('loadData', data);
						}
					});
				}
			});
		}
		
		function remove(){
			var len = $("#dgrl").datagrid("getChecked").length;
			if (len == 0) {
				$.messager.confirm('提示', "选择一行进行删除");
			} else {
				var id = $("#dgrl").datagrid("getChecked")[0].id;
				$.messager.confirm('确认', '您确认想要删除记录吗电影详情也将被删除？', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : "./rolling/delete",
							data : {
								id : id
							},
							dataType : "json",
							success : function(data) {
								$.messager.alert('删除消息', data.msg);
								$('#dgrl').datagrid('reload');
							}
						});
					}
				});
			}
		}
	
		/* 修改 */
		function openAdd(){
			$("#save_rolling").dialog({
				title : '滚动添加',
				maximizable : true,
				resizable : true,
				modal : true
			});
			$("#save_rolling").dialog('open');	
		}
		
		/* 修改保存 */
		function rSave(){
			if(isNaN($("#save_rolling_form input[name='mid']").val())){
				$.messager.alert('提示消息', "mid应该为数字");
			} else {
				$.ajax({
					type : "get",
					url : "./rolling/save",
					data : $("#save_rolling_form").serialize(),
					dataType : "json",
					success : function(data) {
						$.messager.alert('修改消息', data.msg);
						$("#save_rolling").dialog('close');
						$('#dgrl').datagrid("reload");
					}
				});
			}
		}
		
		/* 修改滚动栏 */
		function openEdit(){
			var len = $("#dgrl").datagrid("getChecked").length;
			var rollings = $("#dgrl").datagrid("getChecked");
			$("#update_rolling_form input").each(function(){
				$(this).val("");
			});
			if (len != 1) {
				$.messager.confirm('提示', "选择一行进行修改");
			} else {
				var rolling = rollings[0].id;
				/*进行回写操作*/
				$("#update_rolling_form input[name='mid']").val(rollings[0].mid);
				$("#update_rolling_form input[name='bigImageUrl']").val(rollings[0].bigImageUrl);
				$("#update_rolling_form input[name='id']").val(rolling);
				
				$("#update_rolling").dialog({
				title : '滚动栏修改',
				maximizable : true,
				resizable : true,
				modal : true
			});
				/* 初始化日期 */
				$("#update_rolling").dialog('open');	
			}
		}
		
		function rlSave(){
		if(isNaN($("#update_rolling_form input[name='mid']").val())){
				$.messager.alert('提示消息', "mid应该为数字");
			} else {
				$.ajax({
					type : "POST",
					url : "./rolling/update",
					data : $("#update_rolling_form").serialize(),
					dataType : "json",
					success : function(data) {
						$.messager.alert('修改消息', data.msg);
						$("#update_rolling").dialog('close');
						$('#dgrl').datagrid("reload");
					}
				});
			}
		}
		
		/* 总关闭按钮 */
		function mClose() {
			$("#update_rolling").dialog('close');
			$("#save_rolling").dialog('close');
		}
		
	</script>
</body>
</html>
