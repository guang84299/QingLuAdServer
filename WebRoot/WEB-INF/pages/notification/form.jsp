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
	<meta name="menu" content="notification" />    
	<script type="text/javascript" src="<c:url value='/scripts/laydate.dev.js'/>"></script>
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
	<td>选择地区:</td>
	<td>
		<select id='area_province' name="area_province" style='width:88px'>		
		</select>
		
		<select id='area_city' name="area_city" style='width:88px'>
		</select>
	</td>		
</tr>

<tr>
	<td>选择手机型号:</td>
	<td>
		<select id='phone_model' name="phone_model" style='width:88px'>		
		</select>
	</td>		
</tr>

<tr>
	<td>选择运营商:</td>
	<td>
		<select id='network_operator' name="network_operator" style='width:88px'>		
		</select>
	</td>		
</tr>

<tr>
	<td>在线用户ID区间:</td>
	<td>	
	<input type="text" id="session_from" name="session_from" value="0" style="width:100px;" />
	至
	<input type="text" id="session_to" name="session_to" value="100" style="width:100px;" />
	</td>
</tr>

<tr>
	<td>创建日期区间:</td>
	<td>	
	<input type="text" id="createDate_from" name="createDate_from" value="" style="width:120px;" />
	至
	<input type="text" id="createDate_to" name="createDate_to" value="" style="width:120px;" />
	</td>
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

<tr >
	<td>选择地区:</td>
	<td>
		<select id='area_province2' name="area_province" style='width:88px'>		
		</select>
		
		<select id='area_city2' name="area_city" style='width:88px'>
		</select>
	</td>		
</tr>

<tr>
	<td>选择手机型号:</td>
	<td>
		<select id='phone_model2' name="phone_model" style='width:88px'>		
		</select>
	</td>		
</tr>

<tr>
	<td>选择运营商:</td>
	<td>
		<select id='network_operator2' name="network_operator" style='width:88px'>		
		</select>
	</td>		
</tr>

<tr>
	<td>在线用户ID区间:</td>
	<td>	
	<input type="text" id="session_from" name="session_from" value="0" style="width:100px;" />
	至
	<input type="text" id="session_to" name="session_to" value="100" style="width:100px;" />
	</td>
</tr>

<tr>
	<td>创建日期区间:</td>
	<td>	
	<input type="text" id="createDate_from2" name="createDate_from" value="" style="width:120px;" />
	至
	<input type="text" id="createDate_to2" name="createDate_to" value="" style="width:120px;" />
	</td>
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
//选择地区
var urll = "<%out.print(basePath); %>notification.do?action=getAreas";
var htmlobj = $.ajax({url:urll,async:false});
var arr = htmlobj.responseText;
var jsonarr = eval("("+arr+")"); 

var areas = {};

for(var i=0;i<jsonarr.length;i++)
{
	var item = jsonarr[i];
	var province = item.province;
	var pros = areas[province];
	if(!pros)
	{
		areas[province] = [];
	}
	areas[province].push(item.city);
}

var areaselect = function(area_province,area_city)
{
	area_province.html("");
	area_city.html("");
	area_province.append("<option value='all' selected>所有</option>");
	for(key in areas)
	{
		var str = "<option value='" + key + "' selected>" + key + "</option>";
		area_province.append(str);	
	}
	area_province.val("all");
	area_province.change(function(){
		var val = area_province.val();
		if(val == "all")
		{
			return;
		}
		var arr = areas[val];
		area_city.html("");
		area_city.append("<option value='all' selected>所有</option>");
		for(key in arr)
		{
			var str = "<option value='" + arr[key] + "' selected>" + arr[key] + "</option>";
			area_city.append(str);	
		}
		area_city.val("all");
	});
}
areaselect($("#area_province"),$("#area_city"));
areaselect($("#area_province2"),$("#area_city2"));

//选择手机型号
urll = "<%out.print(basePath); %>notification.do?action=getPhoneModels";
var htmlobj = $.ajax({url:urll,async:false});
var arr = htmlobj.responseText;
var models = eval("("+arr+")"); 

var modelselect = function(phone_model)
{
	phone_model.html("");
	phone_model.append("<option value='all' selected>所有</option>");
	
	for(var i=0;i<models.length;i++)
	{
		var item = models[i];
		var str = "<option value='" + item.model + "' selected>" + item.model + "</option>";
		phone_model.append(str);	
	}
	phone_model.val("all");
}

modelselect($("#phone_model"));
modelselect($("#phone_model2"));

//选择运营商
urll = "<%out.print(basePath); %>notification.do?action=getNetworkOperators";
var htmlobj = $.ajax({url:urll,async:false});
var arr = htmlobj.responseText;
var networkOperator = eval("("+arr+")"); 

var network_operatorselect = function(network_operator)
{
	network_operator.html("");
	network_operator.append("<option value='all' selected>所有</option>");
	
	for(var i=0;i<networkOperator.length;i++)
	{
		var item = networkOperator[i];
		var str = "<option value='" + item.name + "' selected>" + item.name + "</option>";
		network_operator.append(str);	
	}
	network_operator.val("all");
}

network_operatorselect($("#network_operator"));
network_operatorselect($("#network_operator2"));

//创建日期选择
laydate({
			istime: true,
            elem: '#createDate_from',
            format: 'YYYY-MM-DD hh:mm:ss'
        });
laydate({
			istime: true,
            elem: '#createDate_to',
            format: 'YYYY-MM-DD hh:mm:ss'
        });        
laydate({
			istime: true,
            elem: '#createDate_from2',
            format: 'YYYY-MM-DD hh:mm:ss'
        });
laydate({
			istime: true,
            elem: '#createDate_to2',
            format: 'YYYY-MM-DD hh:mm:ss'
        });        

       
//]]>
</script>

</body>
</html>
