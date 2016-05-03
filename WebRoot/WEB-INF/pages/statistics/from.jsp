<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上海青露广告平台</title>
	<meta name="menu" content="statistics" />    
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/tablesorter/style.css'/>" />
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

<h1>广告统计</h1>

<table id="tableList" class="tablesorter" cellspacing="1">
	<thead>
		<tr>
			
			<th>公司</th>
			<th>广告类型</th>
			<th>图片路径</th>
			<th>下载路径</th>	
			<th>展示次数</th>
			<th>点击次数</th>		
		</tr>
	</thead>
	<tbody>
		<c:forEach var="sess" items="${list}">
			<tr>
				<td><c:out value="${sess.company}" /></td>
				<td><c:out value="${sess.type}" /></td>
				<td align="center"><c:out value="${sess.picPath}" /></td>				
				<td><c:out value="${sess.downloadPath}" /></td>
				<td><c:out value="${sess.showNum}" /></td>
				<td><c:out value="${sess.clickNum}" /></td>
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

a_1.href = "statistics.do?index=" + (parseInt(index)-1);
a_2.href = "statistics.do?index=" + (parseInt(index)+1);	
}

resf();
	
</script>
</body>
</html>
