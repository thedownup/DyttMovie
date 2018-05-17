<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
	<div style="margin-top: 70px;">
	    <p style="font-size: 55px;color: orange;text-align: center">欢迎使用电影天堂管理系统</p>
		<div  id="show" style="text-align: center;font-size: 30px;color: green"></div>
	</div>
<script type="text/javascript">
		 function run(){
			 var time = new Date();//获取系统当前时间
			 var year = time.getFullYear();
			 var month = time.getMonth()+1;
			 var date= time.getDate();//系统时间月份中的日
			 var day = time.getDay();//系统时间中的星期值
			 var weeks = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
			 var week = weeks[day];//显示为星期几
			 var hour = time.getHours();
			 var minutes = time.getMinutes();
			 var seconds = time.getSeconds();
			 if(month<10){
			 month = "0"+month; 
			 }
			 if(date<10){
			 date = "0"+date; 
			 }
			 if(hour<10){
			 hour = "0"+hour; 
			 }
			 if(minutes<10){
			 minutes = "0"+minutes; 
			 }
			 if(seconds<10){
			 seconds = "0"+seconds; 
			 }
			 //var newDate = year+"年"+month+"月"+date+"日"+week+hour+":"+minutes+":"+seconds;
			 document.getElementById("show").innerHTML = year+"年 "+month+"月 "+date+"日 "+week+" "+hour+":"+minutes+":"+seconds;
			 setTimeout('run()',1000);
		 }
		  
		 run();
</script>
    
</body>
</html>