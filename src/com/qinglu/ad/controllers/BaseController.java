package com.qinglu.ad.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.App;
import com.qinglu.ad.model.User;
import com.qinglu.ad.service.AppService;
import com.qinglu.ad.service.UserService;

public class BaseController  extends MultiActionController{
	private NotificationManager notificationManager;
	private UserService userService;
	private AppService appService;

	public BaseController() {
		notificationManager = new NotificationManager();
		userService = ServiceLocator.getUserService();
		appService = (AppService) ServiceLocator.getService("appService");
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
	
	//添加APP信息
	public void addAppInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			JSONObject jso = JSONObject.fromObject(s);
			String name = jso.getString("name");
			String packageName =  jso.getString("packageName");
			String id =  jso.getString("id");
			
			User user = userService.getUserByUsername(id);
			
			if(user == null)
			{
				response.getWriter().print(0);
				return;
			}
			QueryResult<App> qr = appService.findAppsByUserId(user.getId());
			boolean b = false;
			
			for(App app : qr.getList())
			{
				if(app.getPackageName().equals(packageName))
				{
					b = true;
					break;
				}
			}
			//存在了
			if(b)
			{
				response.getWriter().print(1);
				return;
			}
			else
			{
				App app = new App(user.getId(), name, packageName);
				appService.add(app);
				response.getWriter().print(1);
			}		
		} catch (Exception e) {
			response.getWriter().print(0);
		}
		
	}
	
	
}
