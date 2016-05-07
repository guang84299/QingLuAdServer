<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上海青露广告平台</title>
	<meta name="menu" content="pushStatistics" />    
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/tablesorter/style.css'/>" />
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.js'/>"></script>
</head>

<body>

<h1>推送统计</h1>


<table id="tableList" class="tablesorter"  cellspacing="1">
	<thead>
		<tr>
			
			<th>ID</th>
			<th>广告ID</th>
			<th>推送类型</th>
			<th>用户类型</th>	
			<th>推送人数</th>	
			<th>展示次数</th>
			<th>点击次数</th>	
			<th>下载次数</th>
			<th>安装次数</th>	
			<th>操作</th>		
		</tr>
	</thead>
	<tbody>
		<c:forEach var="sess" items="${list}">
			<tr>
				<td><c:out value="${sess.id}" /></td>
				<td><c:out value="${sess.adId}" /></td>
				
				<c:choose>
					<c:when test="${sess.type eq 0}">
					<td >消息</td>		
					</c:when>
					<c:when test="${sess.type eq 1}">
					<td >插屏</td>		
					</c:when>
				</c:choose>
				
				<c:choose>
					<c:when test="${sess.userType eq 0}">
					<td >所有在线</td>		
					</c:when>
					<c:when test="${sess.userType eq 1}">
					<td >单个用户</td>		
					</c:when>
					<c:when test="${sess.userType eq 2}">
					<td >应用用户</td>		
					</c:when>
					<c:otherwise>
					<td >其他用户</td>		
					</c:otherwise>
				</c:choose>
				
				<td ><c:out value="${sess.sendNum}" /></td>				
				<td><c:out value="${sess.showNum}" /></td>
				<td class="tdclickNum"><c:out value="${sess.clickNum}" /></td>
				<td class="tddownloadNum"><c:out value="${sess.downloadNum}" /></td>
				<td class="tdinstallNum"><c:out value="${sess.installNum}" /></td>
				<td class="thUpdate"><input type="button" value="操作"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div id="my_div" title="<c:out value="${maxNum}" />">

<a id="a_1" href="#" > 上一页    </a>
<a id="a_2" href="#"> 下一页</a>

<a  herf="#">总记录数：<c:out value="${maxNum}" /></a>
</div>

<div id="my_div2" style="display:none;position:absolute;width:100px;">
<table  class="tablesorter" cellspacing="1">
	<thead>
		<tr>			
			<th>用户ID</th>
		</tr>
	</thead>
	<tbody id="div2_body">		
	</tbody>
	<tr>
		<td>广告ID:<input type="text" id="adId" name="adId" value="" style="width:20px;" /></td>		
	</tr>
	<tr><td><input type="button" value="推送" id="tuisong"/></td></tr>
</table>
</div>

<div id="div_update" style="display:none;position:absolute;width:100px;">
<table  class="tablesorter" cellspacing="1">
	<thead>
		<tr>			
			<th>操作</th>
		</tr>
	</thead>		
	<tr><td><input type="button" value="删除" id="delete"/></td></tr>
</table>
</div>


<script lanuage="javascript">

$("#delete").click(function()
{
	var data = $("#div_update").attr("title");
	
	var urll = "<%out.print(basePath); %>pushStatistics.do?action=deletePush&data=";
	urll = urll + data;
	$.ajax({url:urll,async:false});
	$("#div_update").hide();
	location.reload();
});

$(".thUpdate").click(function(){	
	var x = $(this).offset().top; 
	var y = $(this).offset().left - 100; 
	var div = $("#div_update");
	div.css("left",y + "px"); 
	div.css("top",x + "px");
	var preall = $(this).prevAll();
	var id = preall[preall.length-1].innerHTML;
	
	div.attr("title",id);
	div.show();
});


$("#tuisong").click(function()
{
	var data = $("#my_div2").attr("title") + "," + $("#adId").attr("value");
	
	var urll = "<%out.print(basePath); %>notification.do?action=sendClickDownloadInstallAd&data=";
	urll = urll + data;
	$.ajax({url:urll,async:false});
	$("#my_div2").hide();
});

var myHide = function(div,e)
{
	if(div.css('display') != "none")
		{
			var w = div.width();
			var h = div.height();
			
			var left =  div.offset().left;
			var top = div.offset().top;
			if(e.pageX <= left+w && e.pageX >= left && e.pageY >= top && e.pageY <= top + h)
			{
				return;			
			}
			else
			{
				div.hide();
			}
		}
}

$("html").mousedown(function(e){
	var div = $("#my_div2");
	
	myHide(div,e);
	myHide($("#div_update"),e);
});
$(".tdclickNum").mousedown(function(e){ 
 // 1 = 鼠标左键 left; 2 = 鼠标中键; 3 = 鼠标右键 
	if(e.which == 1)
	{
		var x = $(this).offset().top; 
		var y = $(this).offset().left - 125; 
		var my_div2 = $("#my_div2");
		my_div2.css("left",y + "px"); 
		my_div2.css("top",x + "px");
		var preall = $(this).prevAll();
		var pushId = preall[preall.length-1].innerHTML;
		var urll = "<%out.print(basePath); %>pushStatistics.do?action=getUserPushByClick&data=";
		urll = urll + pushId;
		
		htmlobj=$.ajax({url:urll,async:false});
		var arr = htmlobj.responseText;
		var jsonarr = eval("("+arr+")"); 
		$("#div2_body").html("");
		for(var i=0;i<jsonarr.length;i++)
		{
			var item = jsonarr[i];
			var str = "<tr><td>" + item.username + "</td></tr>";
			$("#div2_body").append(str);
		}
		$("#my_div2").attr("title",pushId+",1");
		$("#my_div2").show();
	}
	return false;//阻止链接跳转 
});

$(".tddownloadNum").mousedown(function(e){ 
 // 1 = 鼠标左键 left; 2 = 鼠标中键; 3 = 鼠标右键 
	if(e.which == 1)
	{
		var x = $(this).offset().top ; 
		var y = $(this).offset().left - 125; 
		var my_div2 = $("#my_div2");
		my_div2.css("left",y + "px"); 
		my_div2.css("top",x + "px");
		var preall = $(this).prevAll();
		var pushId = preall[preall.length-1].innerHTML;
		var urll = "<%out.print(basePath); %>pushStatistics.do?action=getUserPushByDownload&data=";
		urll = urll + pushId;
		
		htmlobj=$.ajax({url:urll,async:false});
		var arr = htmlobj.responseText;
		var jsonarr = eval("("+arr+")"); 
		$("#div2_body").html("");
		for(var i=0;i<jsonarr.length;i++)
		{
			var item = jsonarr[i];
			var str = "<tr><td>" + item.username + "</td></tr>";
			$("#div2_body").append(str);
		}
		$("#my_div2").attr("title",pushId+",2");
		$("#my_div2").show();
	}
	return false;//阻止链接跳转 
});

$(".tdinstallNum").mousedown(function(e){ 
 // 1 = 鼠标左键 left; 2 = 鼠标中键; 3 = 鼠标右键 
	if(e.which == 1)
	{
		var x = $(this).offset().top ; 
		var y = $(this).offset().left - 125; 
		var my_div2 = $("#my_div2");
		my_div2.css("left",y + "px"); 
		my_div2.css("top",x + "px");
		var preall = $(this).prevAll();
		var pushId = preall[preall.length-1].innerHTML;
		var urll = "<%out.print(basePath); %>pushStatistics.do?action=getUserPushByInstall&data=";
		urll = urll + pushId;
		
		htmlobj=$.ajax({url:urll,async:false});
		var arr = htmlobj.responseText;
		var jsonarr = eval("("+arr+")"); 
		$("#div2_body").html("");
		for(var i=0;i<jsonarr.length;i++)
		{
			var item = jsonarr[i];
			var str = "<tr><td>" + item.username + "</td></tr>";
			$("#div2_body").append(str);
		}
		$("#my_div2").attr("title",pushId+",3");
		$("#my_div2").show();
	}
	return false;//阻止链接跳转 
});
	
var div = document.getElementById("my_div");
var a_1 = document.getElementById("a_1");
var a_2 = document.getElementById("a_2");

var resf = function()
{
var maxNum = div.title;
var maxIndex = Math.ceil(maxNum / 20)-1;
var index = location.href.split("=")[1];

if(!index || index > maxIndex)
index = 0;

if(index == 0)
{
	a_1.style.display = "none";
}
else
{
	a_1.style.display = "";
}
if(index == maxIndex)
{
	a_2.style.display = "none";
}
else
{
	a_2.style.display = "";
}

a_1.href = "pushStatistics.do?index=" + (parseInt(index)-1);
a_2.href = "pushStatistics.do?index=" + (parseInt(index)+1);	
}

resf();
	
</script>
</body>
</html>
