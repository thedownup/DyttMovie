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
	<table id="dg" style="width:100%"></table>
	
	<!--创建表单验证-->
	<div id="save_user" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){sSave()}},{text:'Close',handler:function(){sClose()}}]"
		 data-options="novalidate:true"
		style="width:500px; padding:10px;">
		<form id="save" method="post">
			<table>
				<tr>
					<td width="60" align="right">名称(不可重复):</td>
					<td><input type="text" name="userName"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">密码:</td>
					<td><input type="text" name="passWord"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">生日:</td>
					<td><input type="text" name="birthday" id="cc"
						class="wu-text easyui-validatebox easyui-datetimebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">验证状态(0或1):</td>
					<td><input type="text" name="state" data-options="min:0,max:1" id="yz"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">性别(0或1):</td>
					<td><input type="text" name="sex" id="xb"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">个性签名:</td>
					<td><input type="text" name="signature"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">邮箱:</td>
					<td><input type="text"
						data-options="required:true,validType:'email'" name="email"
						class="wu-text" /></td>
				</tr>
				<tr>
					<td align="right">背景图片:</td>
					<td><input type="text" name="touXiangImg"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">头像图片:</td>
					<td><input type="text" name="backImg"
						class="wu-text easyui-validatebox" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	<!--更新表单验证-->
	<div id="change_user" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){cSave()}},{text:'Close',handler:function(){cClose()}}]"
		 data-options="novalidate:true"
		style="width:500px; padding:10px;">
		<form id="change" method="post">
			<table>
				<tr>
					<td width="60" align="right">名称(不可重复):</td>
					<td><input type="text" name="userName"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">密码:</td>
					<td><input type="text" name="passWord"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">生日:</td>
					<td><input type="text" name="birthday" id="ccu"
						class="wu-text easyui-validatebox easyui-datetimebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">验证状态(0或1):</td>
					<td><input type="text" name="state" data-options="min:0,max:1" id="yz"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">性别(0或1):</td>
					<td><input type="text" name="sex" id="xb"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">个性签名:</td>
					<td><input type="text" name="signature"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">邮箱:</td>
					<td><input type="text"
						data-options="required:true,validType:'email'" name="email"
						class=" wu-text" /></td>
				</tr>
				<tr>
					<td align="right">背景图片:</td>
					<td><input type="text" name="touXiangImg"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">头像图片:</td>
					<td><input type="text" name="backImg"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><input type="text" name="updated" style="display: none" /></td>
					<td><input type="text" name="created" style="display: none" /></td>
					<td><input type="text" name="id" style="display: none" /></td>
					<td><input type="text" name="flag" style="display: none" /></td>
				</tr>

			</table>
			<!-- <input class="easyui-linkbutton" type="button" value="提交" /> <input
				class="easyui-linkbutton" type="button" value="取消" /> -->
		</form>
	</div>

	<script>
	
		var flag2 = true;
		
		//判断是否该密码的
		var flag = "false";
		var oldPassWord = "";
	
		$(function() {
			if (flag2) {
				flag2 = false;
				init(0, 10);
			}
			Initpagination();
		});
	
		/*分页设置*/
		function Initpagination() {
			var p = $("#dg").datagrid('getPager');
			$(p).pagination({
				onSelectPage : function(pageNumber, pageSize) {
					//ajax请求
					/* $('#dg').datagrid('reload').datagrid('getPager').pagination('refresh', {
						pageSize : pageSize
					}) */
					$.ajax({
						type : "POST",
						url : "./user/get",
						data : {
							page : pageNumber,
							num : pageSize
						},
						dataType : "json",
						success : function(data) {
							$('#dg').datagrid('loadData', data);
						}
					});
				}
			});
		}
	
		function init(page, num) {
			$('#dg').datagrid({
				url : './user/get?page=' + page + "&num=" + num,
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
						field : 'userName',
						title : '名称',
						width : 100
					},
					{
						field : 'passWord',
						title : '密码(md5)',
						width : 100
					},
					{
						field : 'birthday',
						title : '生日',
						width : 100
					},
					{
						field : 'state',
						title : '验证状态',
						width : 100
					},
					{
						field : 'sex',
						title : '性别',
						width : 100
					},
					{
						field : 'signature',
						title : '个性签名',
						width : 100
					},
					{
						field : 'email',
						title : '邮箱',
						width : 100
					},
					{
						field : 'backImg',
						title : '背景图片',
						width : 100
					},
					{
						field : 'touXiangImg',
						title : '头像图片',
						width : 100,
						align : 'right'
					},
					{
						field : 'created',
						title : '注册时间',
						width : 100,
						align : 'right'
					},
					{
						field : 'updated',
						title : '修改时间',
						width : 100,
						align : 'right'
					}
				] ]
			});
		}
	
		function openEdit() {
			var len = $("#dg").datagrid("getChecked").length;
			var user = $("#dg").datagrid("getChecked");
			clearForm();
			if (len != 1) {
				$.messager.confirm('提示', "选择一行进行修改");
			} else {
				var userId = user[0].id;
				/*进行回写操作*/
				$("#change_user input[name='userName']").val(user[0].userName);
				$("#change_user input[name='passWord']").val(user[0].passWord);
				$("#change_user input[name='state']").val(user[0].state);
				$("#change_user input[name='sex']").val(user[0].sex);
				$("#change_user input[name='signature']").val(user[0].signature);
				$("#change_user input[name='email']").val(user[0].email);
				$("#change_user input[name='backImg']").val(user[0].backImg);
				$("#change_user input[name='touXiangImg']").val(user[0].touXiangImg);
				$("#change_user input[name='created']").val(user[0].created);
				$("#change_user input[name='updated']").val(user[0].updated);
				$("#change_user input[name='id']").val(user[0].id);
				
				/* 用来判断是否用改密码 */
				oldPassWord = user[0].passWord;
	
				$("#change_user").dialog({
					title : '用户修改',
					modal : true,
				});
				/* 初始化日期 */
				$("#ccu").datetimebox('setValue',user[0].birthday);	
				$("#change_user").dialog('open');
			}
		}
	
		/*删除*/
		function remove() {
			var len = $("#dg").datagrid("getChecked").length;
			if (len != 1) {
				$.messager.confirm('提示', "选择一行进行删除");
			} else {
				var id = $("#dg").datagrid("getChecked")[0].id;
				$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : "./user/delete",
							data : {
								id : id
							},
							dataType : "json",
							success : function(data) {
								$.messager.alert('删除消息', data.msg);
								$('#dg').datagrid('reload');
							}
						});
					}
				});
			}
		}
	
		/* 修改保存 */
		function cSave() {
			if(oldPassWord != $("#change_user input[name='passWord']").val()){
				flag = "true";				
			}
			$("#change_user input[name='flag']").val(flag);
			if($("#change_user input[name='state']").val() == "" || $("#change_user input[name='state']").val() != "0" || $("#change_user input[name='state']").val() != "1"){
				$("#change_user input[name='state']").val("0");
			}
			if($("#change_user input[name='sex']").val() == "" || $("#change_user input[name='sex']").val() != "0" || $("#change_user input[name='sex']").val() != "1"){
				$("#change_user input[name='sex']").val("0");
			}
			$.ajax({
				type : "POST",
				url : "./user/update",
				data : $("#change").serialize(),
				dataType : "json",
				success : function(data) {
					$.messager.alert('修改消息', data.msg);
					$("#change_user").dialog('close');
					$('#dg').datagrid("reload");
					clearForm();
				}
			});
		}
	
		/* 修改关闭 */
		function cClose() {
			$("#change_user").dialog('close');
		}
		
		/*添加用户按钮*/
		function openAdd() {
			$("#save_user").dialog({
				title : '创建用户',
				modal : true
			});
			$("#save_user").dialog('open');
			$("#save input").each(function(){
				$(this).val("");
			});
		}
		
		/* 用户添加 */
		function sSave() {
			if($("#save_user input[name='state']").val() == "" || $("#save_user input[name='state']").val() != "0" || $("#save_user input[name='state']").val() != "1"){
				$("#save_user input[name='state']").val("0");
			}
			if($("#save_user input[name='sex']").val() == "" || $("#save_user input[name='sex']").val() != "0" || $("#save_user input[name='sex']").val() != "1"){
				$("#save_user input[name='sex']").val("0");
			}
			$.ajax({
				type : "POST",
				url : "./user/create",
				data : $("#save").serialize(),
				dataType : "json",
				success : function(data) {
					$.messager.alert('修改消息', data.msg);
					$("#save_user").dialog('close');
					$('#dg').datagrid("reload");
					clearForm();
				}
			});
		}
		
		/* 创建关闭 */
		function sClose() {
			$("#save_user").dialog('close');
		}
		
		/*清空表单  */
		function clearForm(){
			$("#change input").each(function(){
				$(this).val("");
			});
		}
	</script>

</body>
</html>
