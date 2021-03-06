/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.qinglu.ad.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.Area;
import com.qinglu.ad.model.NetworkOperator;
import com.qinglu.ad.model.PhoneModel;
import com.qinglu.ad.model.Push;
import com.qinglu.ad.model.UserPush;
import com.qinglu.ad.service.AdService;
import com.qinglu.ad.service.AppService;
import com.qinglu.ad.service.AreaService;
import com.qinglu.ad.service.DeviceService;
import com.qinglu.ad.service.NetworkOperatorService;
import com.qinglu.ad.service.PhoneModelService;
import com.qinglu.ad.service.PushService;
import com.qinglu.ad.service.UserPushService;
import com.qinglu.ad.service.UserService;

/**
 * A controller class to process the notification related requests.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationController extends MultiActionController {

	private NotificationManager notificationManager;
	private UserService userService;
	private AppService appService;
	private AdService adService;
	private PushService pushService;
	private UserPushService userPushService;
	private AreaService areaService;
	private PhoneModelService phoneModelService;
	private NetworkOperatorService networkOperatorService;

	public NotificationController() {
		notificationManager = new NotificationManager();
		userService = (UserService) ServiceLocator.getService("userService");
		appService = (AppService) ServiceLocator.getService("appService");
		adService = (AdService) ServiceLocator.getService("adService");
		pushService = (PushService) ServiceLocator.getService("pushService");
		userPushService = (UserPushService) ServiceLocator.getService("userPushService");
		areaService =  (AreaService) ServiceLocator.getService("areaService");
		phoneModelService = (PhoneModelService) ServiceLocator.getService("phoneModelService");
		networkOperatorService = (NetworkOperatorService) ServiceLocator.getService("networkOperatorService");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
			
		ModelAndView mav = new ModelAndView();					
		// mav.addObject("areaList", areaList);
		mav.setViewName("notification/form");
		return mav;
	}

	public ModelAndView send(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String broadcast = ServletRequestUtils.getStringParameter(request,
				"broadcast", "all");
		String username = ServletRequestUtils.getStringParameter(request,
				"username");
		String appname = ServletRequestUtils.getStringParameter(request,
				"appname");
		
		String area_province = ServletRequestUtils.getStringParameter(request,
				"area_province");
		String area_city = ServletRequestUtils.getStringParameter(request,
				"area_city");
		String phone_model = ServletRequestUtils.getStringParameter(request,
				"phone_model");
		String network_operator = ServletRequestUtils.getStringParameter(request,
				"network_operator");
		String session_from = ServletRequestUtils.getStringParameter(request,
				"session_from");
		String session_to = ServletRequestUtils.getStringParameter(request,
				"session_to");
		String createDate_from = ServletRequestUtils.getStringParameter(request,
				"createDate_from");
		String createDate_to = ServletRequestUtils.getStringParameter(request,
				"createDate_to");
		
		String title = ServletRequestUtils.getStringParameter(request, "title");
		String message = ServletRequestUtils.getStringParameter(request,
				"message");
		String adId = ServletRequestUtils.getStringParameter(request, "adId");
		String uri = null;
		if (adId != null && !"".equals(adId)) {
			Ad ad = adService.find(Long.parseLong(adId));
			if (ad != null) {
				uri = ad.getDownloadPath();

				String apiKey = Config.getString("apiKey", "");

				Push push = new Push(ad.getId(), 0, 0, 0, 0, 0, 0, 0);
				pushService.add(push);				

				//消息体 ： pushid ：包名
				message = message + "&&&&&" + push.getId()  + "&&&&&" + ad.getPackageName();

				if (broadcast.equalsIgnoreCase("all")) {
					int num = notificationManager.sendBroadcast(apiKey, title,
							message, uri,userService,userPushService,area_province,area_city,phone_model,
							network_operator,session_from,session_to,createDate_from,createDate_to);
					
					synchronized (pushService) {
						push = pushService.findAds(0).getList().get(0);
						push.setSendNum(num);
						pushService.update(push);
					}
					
				} else if (broadcast.equalsIgnoreCase("single")) {
					int num = notificationManager.sendNotifcationToUser(apiKey,
							username, title, message, uri,userPushService);
					
					synchronized (pushService) {
						push = pushService.findAds(0).getList().get(0);
						push.setSendNum(num);
						push.setUserType(1);
						pushService.update(push);
					}
					
				} else {
					int num = notificationManager.sendNotifcationToAppUser(
							apiKey, appname, title, message, uri, userService,
							appService,userPushService,area_province,area_city,phone_model,
							network_operator,session_from,session_to,createDate_from,createDate_to);
					
					synchronized (pushService) {
						push = pushService.findAds(0).getList().get(0);
						push.setSendNum(num);
						push.setUserType(2);
						pushService.update(push);
					}
					
				}
			}
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:notification.do");
		return mav;
	}

	// 推送插屏广告
	public ModelAndView sendSpot(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String broadcast = ServletRequestUtils.getStringParameter(request,
				"broadcast2", "all");
		String adId = ServletRequestUtils.getStringParameter(request, "adId");
		String username = ServletRequestUtils.getStringParameter(request,
				"username");
		String appname = ServletRequestUtils.getStringParameter(request,
				"appname");
		
		String area_province = ServletRequestUtils.getStringParameter(request,
				"area_province");
		String area_city = ServletRequestUtils.getStringParameter(request,
				"area_city");
		String phone_model = ServletRequestUtils.getStringParameter(request,
				"phone_model");
		String network_operator = ServletRequestUtils.getStringParameter(request,
				"network_operator");
		String session_from = ServletRequestUtils.getStringParameter(request,
				"session_from");
		String session_to = ServletRequestUtils.getStringParameter(request,
				"session_to");
		String createDate_from = ServletRequestUtils.getStringParameter(request,
				"createDate_from");
		String createDate_to = ServletRequestUtils.getStringParameter(request,
				"createDate_to");

		String apiKey = "pushSpot";

		if (adId != null && !"".equals(adId)) {
			Ad ad = adService.find(Long.parseLong(adId));
			
			Push push = new Push(Long.parseLong(adId), 1, 0, 0, 0, 0, 0, 0);
			pushService.add(push);
			
			//adId ： pushid ：包名
			adId = adId + "&&&&&" + push.getId()+ "&&&&&" + ad.getPackageName();
			if (broadcast.equalsIgnoreCase("all")) {
				int num = notificationManager.sendBroadcast(apiKey, "", adId,
						"",userService,userPushService,area_province,area_city,phone_model,
						network_operator,session_from,session_to,createDate_from,createDate_to);
				
				synchronized (pushService) {
					push = pushService.findAds(0).getList().get(0);
					push.setSendNum(num);
					pushService.update(push);
				}
				
			} else if (broadcast.equalsIgnoreCase("single")) {
				int num = notificationManager.sendNotifcationToUser(apiKey,
						username, "", adId, "",userPushService);
				
				synchronized (pushService) {
					push = pushService.findAds(0).getList().get(0);
					push.setUserType(1);
					push.setSendNum(num);
					pushService.update(push);
				}
				
			} else {
				int num = notificationManager.sendNotifcationToAppUser(apiKey,
						appname, "", adId, "", userService, appService,userPushService,area_province,area_city,phone_model,
						network_operator,session_from,session_to,createDate_from,createDate_to);
				
				synchronized (pushService) {
					push = pushService.findAds(0).getList().get(0);
					push.setUserType(2);
					push.setSendNum(num);
					pushService.update(push);
				}
				
			}
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:notification.do");
		return mav;
	}

	//推送点击 下载 安装 过的在线用户
	public void sendClickDownloadInstallAd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		if(data != null)
		{
			String arr[] = data.split(",");
			long pushId = Long.parseLong(arr[0]);
			int type = Integer.parseInt(arr[1]);//type : 1 点击 2：下载 3：安装
			long adId = Long.parseLong(arr[2]);
			
			Push pushs = pushService.find(pushId);
			if(pushs != null)
			{
				//int pushType = push.getType();//暂时不用 为后来添加文字推送用 现在只推插屏
				List<UserPush> list = null;
				if(type == 1)
				{
					//user_push 表 ，只要点击了，必然上传一条数据
					list = userPushService.findByPushIdAndIsClick(pushId,1,100000).getList();					
				}
				else if(type == 2)
				{
					list = userPushService.findByPushIdAndIsDownload(pushId, 1,100000).getList();
				}
				else if(type == 3)
				{
					list = userPushService.findByPushIdAndIsInstall(pushId, 1,10000).getList();
				}
				
				if(list != null)
				{
					Ad ad = adService.find(adId);
					
					Push push = new Push(adId, 1, 3, 0, 0, 0, 0, 0);
					pushService.add(push);
					
					//广告id 加上 push的id 和 包名
					String adData = adId + "&&&&&" + push.getId() +"&&&&&" + ad.getPackageName();
					String apiKey = "pushSpot";
					int num = notificationManager.sendBroadcastClickDownloadInstall(apiKey, "", adData,
							"",list,userPushService);
					
					synchronized (pushService) {
						push = pushService.findAds(0).getList().get(0);
						push.setSendNum(num);
						pushService.update(push);
					}
					
				}
			}
		}
	}
	
	
	//获得地区
	public void getAreas(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Area> areaList = areaService.findAll().getList();
		String s = JSONArray.fromObject(areaList).toString();
		response.getWriter().print(s);
	}
	
	//获得手机型号
	public void getPhoneModels(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<PhoneModel> list = phoneModelService.findAll().getList();
		String s = JSONArray.fromObject(list).toString();
		response.getWriter().print(s);
	}
	
	//获得运营商
	public void getNetworkOperators(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			List<NetworkOperator> list = networkOperatorService.findAll().getList();
			String s = JSONArray.fromObject(list).toString();
			response.getWriter().print(s);
		}
	
	//test
	public void addAreas(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		areaService.add(new Area("河南省", "焦作市"));
		areaService.add(new Area("四川省", "泸州市"));
		areaService.add(new Area("广东省", "江门市"));
		areaService.add(new Area("上海市", "上海市"));
		
		response.getWriter().print(1);
	}
}
