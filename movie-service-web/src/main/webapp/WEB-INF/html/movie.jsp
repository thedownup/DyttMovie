<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<body>

	<div class="wu-toolbar-button" style="background-color: #FAFAFA">
	<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
			onclick="openAdd()" plain="true">添加</a> -->
			 <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()"
			plain="true">修改</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
	</div>
	
	<table id="dgmm" style="width:100%"></table>
	
	<!--增加电影表单-->
	<div id="save_movie" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){mSave()}},{text:'Close',handler:function(){mClose()}}]"
		 data-options="novalidate:true"
		style="width:500px; padding:10px;">
		<form id="save_movie_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">电影名字:</td>
					<td><input type="text" name="movieName"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">清晰度(无就不填):</td>
					<td><input type="text" name="clarity" data-options="valueField:'id',textField:'text',url:'./json/clarity.json',method:'get',separator:'/'"
						class="wu-text easyui-combobox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">创建日期:</td>
					<td><input type="text" name="date" id="cc"
						class="wu-text easyui-validatebox easyui-datebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">上映年份:</td>
					<td><input type="text" name="year" data-options="min:0,max:1" id="yz"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">地区:</td>
					<td><input type="text" name="area" 
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">电影类型:</td>
					<td><input id="sm_type" type="xx" data-options="valueField:'id',textField:'text',url:'./json/type.json',method:'get',multiple:'true',separator:'/'" 
						class="wu-text easyui-combobox" /></td>
				</tr>
				<tr>
					<td align="right">评分:</td>
					<td><input type="text"
						 name="score"
						class="easyui-validatebox wu-text" /></td>
				</tr>
				<!-- <tr>
					<td align="right">是否为电影:</td>
					<td><input type="text" name="isMovie"
						class="wu-text easyui-validatebox" /></td>
				</tr> -->
				<tr>
					<td align="right">电影图片:</td>
					<td><input type="text" name="movieImgUrl"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<!-- <td><input type="text" value="false" name="movie" style="display: none" /></td> -->
					<!-- <td><input type="text" name="id" style="display: none" /></td> -->
				</tr>
			</table>
				<input type="hidden" name="type" id ="com_multi"/>
			<!-- <input class="easyui-linkbutton" type="button" value="提交" /> <input
				class="easyui-linkbutton" type="button" value="取消" /> -->
		</form>
	</div>
	
	<!--更新电影-->
	<div id="change_movie" class="easyui-dialog"
		data-options="closed:true,iconCls:'icon-save' ,buttons:[{text:'Save', handler:function(){mUpdate()}},{text:'Close',handler:function(){mClose()}}]"
		 data-options="novalidate:true"
		style="width:500px; padding:10px;">
		<form id="change_movie_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">电影名字:</td>
					<td><input type="text" name="movieName"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">清晰度:</td>
					<td><input type="text" name="clarity"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">创建日期:</td>
					<td><input type="text" name="date" id="ccu"
						class="wu-text easyui-validatebox easyui-datebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">上映年份:</td>
					<td><input type="text" name="year" data-options="min:0,max:1" id="yz"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">地区:</td>
					<td><input type="text" name="area" 
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td width="60" align="right">电影类型:</td>
					<td><input type="text" name="type"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td align="right">评分:</td>
					<td><input type="text"
						 name="score"
						class="easyui-validatebox wu-text" /></td>
				</tr>
				<!-- <tr>
					<td align="right">是否为电影:</td>
					<td><input type="text" name="isMovie"
						class="wu-text easyui-validatebox" /></td>
				</tr> -->
				<tr>
					<td align="right">电影图片:</td>
					<td><input type="text" name="movieImgUrl"
						class="wu-text easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><input type="text" name="isMovie" style="display: none" /></td>
					<td><input type="text" name="id" style="display: none" /></td>
				</tr>
			</table>
			
			<!-- <input class="easyui-linkbutton" type="button" value="提交" /> <input
				class="easyui-linkbutton" type="button" value="取消" /> -->
		</form>
	</div>

	<script>
	
	
		$(function() {
			init(0, 10);
		 	Initpagination(); 
		});
	
		/*分页设置*/
		function Initpagination() {
			var p = $("#dgmm").datagrid('getPager');
			$(p).pagination({
				onSelectPage : function(pageNumber, pageSize) {
					$.ajax({
						type : "POST",
						url : "./movie/get",
						data : {
							page : pageNumber,
							num : pageSize
						},
						dataType : "json",
						success : function(data) {
						
							$('#dgmm').datagrid('loadData', data);
						}
					});
				}
			});
		}
	
		function init(page, num) {
			$('#dgmm').datagrid({
				url : './movie/get?page=' + page + "&num=" + num,
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
						field : 'movieName',
						title : '名称',
						width : 100
					},
					{
						field : 'clarity',
						title : '清晰度',
						width : 100
					},
					{
						field : 'year',
						title : '上映年份',
						width : 100
					},
					{
						field : 'area',
						title : '地区',
						width : 100
					},
					{
						field : 'type',
						title : '类别',
						width : 100
					},
					{
						field : 'score',
						title : '评分',
						width : 100
					},
					{
						field : 'date',
						title : '创建时间',
						width : 100
					},
					{
						field : 'movieImgUrl',
						title : '电影图片',
						width : 100,
						align : 'right'
					}
				] ]
			});
		}
		
		/* 电影删除 */
		function remove(){
			var len = $("#dgmm").datagrid("getChecked").length;
			if (len == 0) {
				$.messager.confirm('提示', "选择一行进行删除");
			} else {
				var id = $("#dgmm").datagrid("getChecked");
				var ids = "";
				$.each(id,function(index, obj){
					if((index+1) == len) ids += obj.id;
					else ids = ids + obj.id +",";
				})
				$.messager.confirm('确认', '您确认想要删除记录吗电影详情也将被删除？', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : "./movie/delete",
							data : {
								ids : ids
							},
							dataType : "json",
							success : function(data) {
								$.messager.alert('删除消息', data.msg);
								$('#dgmm').datagrid('reload');
							}
						});
					}
				});
			}
		}
		
		/* 电影修改 */
		function openEdit(){
			var len = $("#dgmm").datagrid("getChecked").length;
			var movie = $("#dgmm").datagrid("getChecked");
			$("#change_movie_form input").each(function(){
				$(this).val("");
			});
			if (len != 1) {
				$.messager.confirm('提示', "选择一行进行修改");
			} else {
				var movieId = movie[0].id;
				/*进行回写操作*/
				$("#change_movie_form input[name='movieName']").val(movie[0].movieName);
				$("#change_movie_form input[name='clarity']").val(movie[0].clarity);
				$("#change_movie_form input[name='year']").val(movie[0].year);
				$("#change_movie_form input[name='area']").val(movie[0].area);
				$("#change_movie_form input[name='type']").val(movie[0].type);
				$("#change_movie_form input[name='score']").val(movie[0].score);
				$("#change_movie_form input[name='isMovie']").val(movie[0].isMovie);
				$("#change_movie_form input[name='data']").val(movie[0].date);
				$("#change_movie_form input[name='movieImgUrl']").val(movie[0].movieImgUrl);
				$("#change_movie_form input[name='id']").val(movie[0].id);
				
				$("#change_movie").dialog({
					title : '电影修改',
					modal : true,
				});
				/* 初始化日期 */
				$('#ccu').datebox('setValue',movie[0].date);	
				$("#change_movie").dialog('open');
			}
		}
		
		/* 电影更新 */
		function mUpdate() {
			$.ajax({
				type : "POST",
				url : "./movie/update",
				data : $("#change_movie_form").serialize(),
				dataType : "json",
				success : function(data) {
					$.messager.alert('修改消息', data.msg);
					$("#change_movie").dialog('close');
					$('#dgmm').datagrid("reload");
				}
			});
		}
		
		/* 更新关闭 */
		function mClose() {
			$("#change_movie").dialog('close');
			$("#save_movie").dialog('close');
		}
		
		/* 增加电影 */
		function openAdd(){
				$("#save_movie").dialog({
					title : '电影添加',
					modal : true,
				});
				$("#save_movie").dialog('open');
		}
		
		function mSave(){
			$("#save_movie_form input[name='type']").val($('#sm_type').combobox('getText'));
			$.ajax({
				type : "POST",
				url : "./movie/create",
				data : $("#save_movie_form").serialize(),
				dataType : "json",
				success : function(data) {
					$.messager.alert('保存消息', data.msg);
					$("#save_movie").dialog('close');
					$('#dgmm').datagrid("reload");
						$("#change_movie_form input").each(function(){
						$(this).val("");
					});
				}
			});
		}
		
		function combobox(){
			$('#sm_type').combobox({
				  onSelect: function(record){
					   var val = record.text;  //得到其value值
					   $('#com_multi').val($("#save_movie_form input[name='type']").val()+"/"+val);  //拼接按逗号分隔
				  }
			});
		}
		
	</script>
</body>
</html>
