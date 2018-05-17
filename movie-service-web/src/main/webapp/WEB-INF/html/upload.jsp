<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>upload</title>
</head>
<body>
	<h2>请选择图片进行上传 (上传限制10M)</h2>
	<p>上传成功后返回图片的地址</p>
	<div style="margin:20px 0;"></div>
	<form id="ff" enctype="multipart/form-data" method="post">
		<div class="easyui-panel" title="Upload File" style="width:400px;padding:30px 70px 50px 70px">
			<div style="margin-bottom:20px">
				<div>图片:</div>
				<input class="easyui-filebox" name="file" data-options="prompt:'Choose a image...'" style="width:100%">
			</div>
			<div>
				<input href="#" type="reset" class="easyui-linkbutton" style="width:100%;height: 30px"></input>
			</div>
			<div>
				<a href="#" id="upload" class="easyui-linkbutton" style="width:100%">Upload</a>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		$(function(){
			$("#upload").click(function(){
				if($("#ff input[name='file']").val() == null || $("#ff input[name='file']").val() == ""){
					$.messager.alert('警告',"文件不应该为空");
				} else {
					$("#ff").form('submit', {
		                        url: './file/uploadimg',
		                        method: "post",
		                        data: $("#ff").serialize(),
		                        dataType : "json",
		                        success: function (data) {
									$.messager.alert({
										width:500,
										heigth:500,
										title: '链接地址',
										msg:JSON.parse(data).msg
									});
		                        }
		             });
				}
			});
		});
	</script>
</body>
</html>