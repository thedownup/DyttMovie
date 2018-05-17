<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>私信</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="./bootstrap-3.3.7/dist/css/bootstrap.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="./js/jquery-3.3.1.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="./bootstrap-3.3.7/dist/js/bootstrap.js"></script>
    <!--导入私信的css-->
	<link rel="stylesheet" href="./css/sixin.css">
    <style type="text/css">
        body{
            font-size: 14px;
            font-family: 'HanHei SC', 'PingFang SC', 'Helvetica Neue', 'Helvetica',
            'Microsoft YaHei', 'WenQuanYi Micro Hei', 'STHeitiSC-Light',
            'Arial', 'Segoe UI', sans-serif;
            background: rgb(245,245,245);
        }
        div{
            display: block;
        }
        
        .list-group a{
        	cursor: pointer;
        }
    </style>
</head>
<body>
	<!-- 头部 -->
	<jsp:include page="../common/head.jsp"></jsp:include>
  <div class="container">
    <div class="row">
        <div class="col-md-3 padding-right-0">
            <div class="panel panel-default">
                <div class="panel-heading title-breadcrumb">最近联系人</div>
                <div class="list-group">
                   <a id="xitong" class="list-group-item active" data-uname="系统" data-id="-1">来自系统的消息</a>
                   <c:forEach items="${recentlyUser}" var="ruser">
                   		  <a class="list-group-item" data-touxian="${ruser.touXiangImg}"  data-uname="${ruser.userName}" data-id="${ruser.id}">${ruser.userName}</a>
                   </c:forEach>
                   <c:if test="${not empty suser}">
	                  <a class="list-group-item" data-touxian="${suser.touXiangImg}"  data-uname="${suser.userName}" data-id="${suser.id}">${suser.userName}</a>
                   	  <div id="ff" style="display: none;"></div>
                   </c:if>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-heading title-breadcrumb">来自 <span id="uname" style="color:red;font-weight:bold;">系统</span> 的消息：</div>
                <div id="sixin" class="panel-body" style="min-height:400px;max-height:500px;overflow:auto;">
              		<!--  <div class="pm-list"> 
				        <a target="_blank" href="/u/405932" class="pm-author-avatar pm-item-right"><img src="http://www.id97.com/uploads/avatar/405932.png?t=1523975397" /></a> 
					        <div class="pm-item pm-item-right"> 
						         <div class="meta">
						          <em>2018-04-18 16:39:59</em>
					         </div> 111
				        </div> 
			       </div>  -->
                </div>
                <div class="panel-footer">
                    <div class="input-group">
                        <textarea maxlength="300" id="pm_content" onpropertychange="this.style.height=this.scrollHeight+2 + 'px'" oninput="this.style.height=this.scrollHeight+2 + 'px'" class="form-control" style="overflow-y:hidden;height: 34px;"></textarea>
                        <span class="input-group-btn">
                            <button id="send" class="btn btn-default" type="button" onclick="send_pm(this);">发送</button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 
<jsp:include page="../common/footer.html" flush="true"></jsp:include>
<script type="text/javascript">
	$(function(){
		//去重
		clearSame();
		//初始显示有active class的 
		getActiveClassMessage();
		// 按钮的切换
		qiehuan();
	});
	
	/* 切换私信对象 */
	function qiehuan(){
		$(".list-group a").click(function(){
			$(".list-group a").each(function(){
				$(this).removeClass("active");
			});
			$(this).addClass("active");
			$("#uname").text($(this).attr("data-uname"));
			var name = $(this).text();
			if (name == '来自系统的消息'){
				getSystemMessage();
			} else {
				var img = $(this).attr("data-touxian");
				var fid = $(this).attr("data-id");
				getMessage(img,fid);
			}
		});
	}
	
	/* 获取系统数据 */
	function getSystemMessage(){
		$.ajax({
			 type: "get",
             url: "ajax/getSystemMessage_sixin",
             dataType: "json",
             success: function(data){
             	var obj = JSON.parse(data);
              	$("#sixin div").remove(); 
             	for(var i= 0;i<obj.length;i++){
					var html = '<div class="pm-list">'+ 
				        '<a target="_blank" href="#" class="pm-author-avatar pm-item-left"><img height="45px" width="45px" src="http://101.132.183.157/group1/M00/00/01/rBMi_lrwfcyAe3L7AAALNaWjICw120.jpg" /></a>'+ 
					        '<div class="pm-item pm-item-left">'+ 
						         '<div class="meta">'+
						          '<em>'+obj[i].sendDate+'</em>'+
					         '</div>'+obj[i].message+
				       ' </div> </div>';              	
             		$("#sixin").append(html);
             	}
             } 
		});
	}
	
	
	/* 获取其他人的数据 */
	function getMessage(img,fid){
		$.ajax({
			 type: "get",
             url: "ajax/getPrivateMessage_sixin",
             data : {uid:${session.user.id},fid:fid},
             dataType: "json",
             success: function(data){
             	var obj = JSON.parse(data);
              	$("#sixin div").remove(); 
             	for(var i= 0;i<obj.length;i++){
             		//判断发送还是接收
             		if(obj[i].sid != ${session.user.id}){
             			var html = '<div class="pm-list">'+ 
				        '<a target="_blank" href="./otherzhuye?uid='+obj[i].fid+'" class="pm-author-avatar pm-item-left"><img height="45px" width="45px" src="'+img+'" /></a>'+ 
					        '<div class="pm-item pm-item-left">'+ 
						         '<div class="meta">'+
						          '<em>'+obj[i].sendDate+'</em>'+
					         '</div>'+obj[i].message+
				       ' </div> </div>';              	
             			$("#sixin").append(html);

             		} else {
             			var html = '<div class="pm-list">'+ 
				        '<a target="_blank" href="./otherzhuye?uid='+obj[i].uid+'" class="pm-author-avatar pm-item-right"><img height="45px" width="45px" src="${session.user.touXiangImg}" /></a>'+ 
					        '<div class="pm-item pm-item-right">'+ 
						         '<div class="meta">'+
						          '<em>'+obj[i].sendDate+'</em>'+
					         '</div>'+obj[i].message+
				       ' </div> </div>';              	
             			$("#sixin").append(html);
             		}
             	}
             } 
		});
	}
	
	/* 发送点击 */
	function send_pm(){
		var fid;
		var img;
		$(".list-group a").each(function(){
			var flag = $(this).hasClass("active");
			if(flag){
				fid = $(this).attr("data-id");
				img = $(this).attr("data-touxian");
				return false;
			}
		});
		var message = $("#pm_content").val();
		if(fid == -1){
			alert("不能发送给系统");
			return;
		}	
		
		if(message == ''){
			alert("不能发送无效消息");
			return;
		}
		sendMessage(fid,message,img);
		//清空message
		$("#pm_content").val("");
	}
	
	/* 发送私信 */
	function sendMessage(fid,message,img){
		$.ajax({
			type: "get",
             url: "ajax/sendMessage_sixin",
			 data : {uid:${session.user.id},fid:fid,message:message},
             dataType: "json",
             success: function(data){
				//刷新数据 
             	getMessage(img,fid);
             	alert(data);
             } 
		});
	}
	
	/* 去重 */
	function clearSame(){
		var temp = "";
		var flag = true;
		
		
	/* 	if($("#ff").length > 0){
			clearActive();
			$(".list-group a").last().addClass("active");
		} */
		
		$(".list-group a").each(function(index,obj){
			
		
			if(temp != $(this).text()){
				temp = $(this).text();
			} else {
				$(this).remove();
				$(".list-group a").each(function(){
					$(this).removeClass("active");
				});
				$(".list-group a").eq(index-1).addClass("active");	
				flag = false;
				return false;
			}
		});
		if(flag) $("#xitong").addClass("active");
	}
	
	/* 获取active class对象的会话 */
	function getActiveClassMessage(){
		$(".list-group a").each(function(){
			var flag = $(this).hasClass("active");
			if (flag){
				var name = $(this).text();
				if(name == '来自系统的消息'){
					getSystemMessage();
				} else {
					var img = $(this).attr("data-touxian");
					var fid = $(this).attr("data-id");
					getMessage(img, fid);
					return false;
				}
			}
		});
	}
	
	/* 清除active */
	function clearActive(){
		$(".list-group a").each(function(){
				$(this).removeClass("active");
		});
	}
	
</script>
</body>
</html>