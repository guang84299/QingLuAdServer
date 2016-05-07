<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上海青露广告平台</title>
	<meta name="menu" content="notification" />    
</head>

<body>

<h1>推送消息</h1>

<%--<div style="background:#eee; margin:20px 0px; padding:20px; width:500px; border:solid 1px #999;">--%>
<div style="margin:20px 0px;">
<form action="notification.do?action=send" method="post" style="margin: 0px;">
<table width="600" cellpadding="4" cellspacing="0" border="0">
<tr>
	<td width="20%">类型:</td>
	<td width="80%">
		<input type="radio" name="broadcast" value="all" checked="checked" />  所有在线用户 
        <input type="radio" name="broadcast" value="single" /> 单个用户 
        <input type="radio" name="broadcast" value="app" /> 应用用户 
	</td>
</tr>
<tr id="trUsername" style="display:none;">
	<td>用户ID:</td>
	<td><input type="text" id="username" name="username" value="" style="width:380px;" /></td>
</tr>
<tr id="trApp" style="display:none;">
	<td>应用包名:</td>
	<td><input type="text" id="appname" name="appname" value="" style="width:380px;" /></td>
</tr>
<tr>
	<td>标题:</td>
	<td><input type="text" id="title" name="title" value="聚合广告测试" style="width:380px;" /></td>
</tr>
<tr>
	<td>主题:</td>
	<td><textarea id="message" name="message" style="width:380px; height:80px;" >这是一个聚合广告推送!</textarea></td>
</tr>
<tr>
	<td>广告ID:</td>
	<td><input type="text" id="adId" name="adId" value="" style="width:380px;" /></td>
</tr>
<%--
<tr>
	<td>Ticker:</td>
	<td><input type="text" id="ticker" name="ticker" value="" style="width:380px;" /></td>
</tr>
--%>

<tr>
	<td>&nbsp;</td>
	<td><input type="submit" value="推送" /></td>
</tr>
</table> 
</form>
</div>


<h1>推送插屏</h1>

<div style="margin:20px 0px;">
<form action="notification.do?action=sendSpot" method="post" style="margin: 0px;">
<table width="600" cellpadding="4" cellspacing="0" border="0">
<tr>
	<td width="20%">类型:</td>
	<td width="80%">
		<input type="radio" name="broadcast2" value="all" checked="checked" />  所有在线用户 
        <input type="radio" name="broadcast2" value="single" /> 单个用户 
        <input type="radio" name="broadcast2" value="app" /> 应用用户 
	</td>
</tr>
<tr id="trUsername2" style="display:none;">
	<td>用户ID:</td>
	<td><input type="text" id="username" name="username" value="" style="width:380px;" /></td>
</tr>
<tr id="trApp2" style="display:none;">
	<td>应用包名:</td>
	<td><input type="text" id="appname" name="appname" value="" style="width:380px;" /></td>
</tr>
<tr>
	<td>广告ID:</td>
	<td><input type="text" id="adId" name="adId" value="" style="width:380px;" /></td>
</tr>

<tr>
	<td>&nbsp;</td>
	<td><input type="submit" value="推送" /></td>
</tr>
</table> 
</form>
</div>

<script type="text/javascript"> 
//<![CDATA[
 
$(function() {
	$('input[name=broadcast]').click(function() {
		if ($('input[name=broadcast]')[0].checked) {
			$('#trUsername').hide();
			$('#trApp').hide();
		}
		else if ($('input[name=broadcast]')[1].checked) {
			$('#trUsername').show();
			$('#trApp').hide();
		}
		else if ($('input[name=broadcast]')[2].checked) {
			$('#trApp').show();
			$('#trUsername').hide();
		}
	});
	
	if ($('input[name=broadcast]')[0].checked) {
		$('#trUsername').hide();
		$('#trApp').hide();
	}
	else if ($('input[name=broadcast]')[1].checked) {
		$('#trUsername').show();
		$('#trApp').hide();
	}
	else if ($('input[name=broadcast]')[2].checked) {
		$('#trApp').show();
		$('#trUsername').hide();
	}
	 
});

$(function() {
	$('input[name=broadcast2]').click(function() {
		if ($('input[name=broadcast2]')[0].checked) {
			$('#trUsername2').hide();
			$('#trApp2').hide();
		}
		else if ($('input[name=broadcast2]')[1].checked) {
			$('#trUsername2').show();
			$('#trApp2').hide();
		}
		else if ($('input[name=broadcast2]')[2].checked) {
			$('#trApp2').show();
			$('#trUsername2').hide();
		}
	});
	
	if ($('input[name=broadcast2]')[0].checked) {
		$('#trUsername2').hide();
		$('#trApp2').hide();
	}
	else if ($('input[name=broadcast2]')[1].checked) {
		$('#trUsername2').show();
		$('#trApp2').hide();
	}
	else if ($('input[name=broadcast2]')[2].checked) {
		$('#trApp2').show();
		$('#trUsername2').hide();
	}
	 
});
 
//]]>
</script>

</body>
</html>
