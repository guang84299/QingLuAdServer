<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上海青露广告平台</title>
<meta name="menu" content="user" />
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/tablesorter/style.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

<h1>排序</h1>

<table id="tableList" class="tablesorter" cellspacing="1">
	<thead>
		<tr>
			<%--
			<th width="5%">Online</th>
			<th width="30%">Username</th>
			<th width="20%">Name</th>
			<th width="20%">Email</th>
			<th width="25%">Created</th>
			--%>
			<th>在线</th>
			<th>ID</th>
			<th>用户ID</th>
			<th>设备ID</th>
			<th>手机型号</th>
			<th>手机号码</th>
			<%--<th>网络类型</th>--%>
			<th>运营商</th>
			<th>sim卡序列号</th>
			<%--<th>国家</th>--%>
			<th>系统版本</th>
			<th>省份</th>
			<th>城市</th>
			<th>注册日期</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td align="center">
				<c:choose>
				<c:when test="${user.online eq true}">
					<img src="images/user-online.png" />
				</c:when>
				<c:otherwise>
					<img src="images/user-offline.png" />
				</c:otherwise>
				</c:choose>
				</td>
				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.username}" /></td>
				<td><c:out value="${user.deviceId}" /></td>
				<td><c:out value="${user.model}" /></td>
				<td><c:out value="${user.phoneNumber}" /></td>
				<%--<td><c:out value="${user.device.networkType}" /></td>--%>
				<td><c:out value="${user.networkOperatorName}" /></td>
				<td><c:out value="${user.simSerialNumber}" /></td>
				<%--<td><c:out value="${user.device.networkCountryIso}" /></td>--%>
				<td><c:out value="${user.release}" /></td>
				<td><c:out value="${user.province}" /></td>
				<td><c:out value="${user.city}" /></td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.createdDate}" /></td>
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

<script type="text/javascript">
//<![CDATA[
$(function() {
	$('#tableList').tablesorter();
	//$('#tableList').tablesorter( {sortList: [[0,0], [1,0]]} );
	//$('table tr:nth-child(odd)').addClass('odd');
	$('table tr:nth-child(even)').addClass('even');	 
});
//]]>


$("#delete").click(function()
{
	var data = $("#div_update").attr("title");
	
	var urll = "<%out.print(basePath); %>user.do?action=deleteUser&data=";
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
	var id = preall[preall.length-2].innerHTML;
	
	div.attr("title",id);
	div.show();
});

$("html").mousedown(function(e){
	var div = $("#div_update");
	
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

a_1.href = "user.do?index=" + (parseInt(index)-1);
a_2.href = "user.do?index=" + (parseInt(index)+1);	
}

resf();


</script>

</body>
</html>
