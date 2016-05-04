package com.qinglu.ad.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.Device;
import com.qinglu.ad.service.DeviceService;

public class BaseController  extends MultiActionController{
	private NotificationManager notificationManager;
	private DeviceService deviceService;
	private UserService userService;

	public BaseController() {
		notificationManager = new NotificationManager();
		userService = ServiceLocator.getUserService();
		deviceService = (DeviceService) ServiceLocator.getService("deviceService");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// mav.addObject("list", null);
		mav.setViewName("notification/form");
		return mav;
	}

	public void send(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}
	
	//添加设备信息
	public void addDeviceInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			JSONObject jso = JSONObject.fromObject(s);
			Device device = (Device) JSONObject.toBean(jso,Device.class);
			Device d = deviceService.findByDeviceId(device.getDeviceId());
			if(d != null)
			{
				if(d.getSubscriberId().equals(device.getSubscriberId()))
				{
					response.getWriter().print(1);
					return;
				}
			}
			else
			{
				deviceService.addDevice(device);
				response.getWriter().print(1);
			}					
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.getWriter().print(0);
		}
		
	}
	
	//test 添加测试信息
	public void test(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		for(int i=1;i<50;i++)
		{
			User user = new User();
			user.setUsername("000000000"+i);
			user.setPassword("111111");
			
			userService.saveUser(user);
			
			Device device = new Device(user.getUsername(),"10086"+i , "networkOperatorName"+i, 
					"simSerialNumber"+i, "networkCountryIso"+i, "networkOperator",
					"WIFI", "1000"+i, 1, "19283882"+i, "com.test."+i, "test"+i,
					"GT I9"+i, "4.3.1");
			deviceService.addDevice(device);
		}
		
		response.getWriter().print(1);
	}
}
