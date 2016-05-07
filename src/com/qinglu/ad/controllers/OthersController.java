package com.qinglu.ad.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.Device;
import com.qinglu.ad.model.User;
import com.qinglu.ad.service.DeviceService;
import com.qinglu.ad.service.UserService;

public class OthersController  extends MultiActionController{

	public OthersController() {
		
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// mav.addObject("list", null);
		mav.setViewName("others/form");
		return mav;
	}

	public void send(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}
	
	//添加设备信息
	public void addDeviceInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		
		response.getWriter().print(0);
		
	}
	
	
}
