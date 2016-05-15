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

<h1>广告统计</h1>



<script lanuage="javascript">
var urll = "<%out.print(basePath); %>pushStatistics.do?action=updateShowNum&data=57&&&&&1";
for(var i=0;i<3000;i++)
{
htmlobj=$.ajax({url:urll,async:false});
}

	
</script>
</body>
</html>
