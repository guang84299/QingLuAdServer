<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上海青露广告平台</title>
	<meta name="menu" content="app" />    
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/tablesorter/style.css'/>" />
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

<h1>应用统计</h1>

<table id="tableList" class="tablesorter" cellspacing="1">
	<thead>
		<tr>
			
			<th>ID</th>
			<th>用户ID</th>
			<th>应用名字</th>
			<th>包名</th>	
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="sess" items="${list}">
			<tr>
				<td><c:out value="${sess.id}" /></td>
				<td><c:out value="${sess.userId}" /></td>									
				<td ><c:out value="${sess.name}" /></td>				
				<td><c:out value="${sess.packageName}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div id="my_div" title="<c:out value="${maxNum}" />">

<a id="a_1" href="#" > 上一页    </a>
<a id="a_2" href="#"> 下一页</a>

<a  herf="#">总记录数：<c:out value="${maxNum}" /></a>
</div>

<script lanuage="javascript">
	
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

a_1.href = "app.do?index=" + (parseInt(index)-1);
a_2.href = "app.do?index=" + (parseInt(index)+1);	
}

resf();
	
</script>
</body>
</html>