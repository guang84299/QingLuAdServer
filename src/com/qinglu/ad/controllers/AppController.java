package com.qinglu.ad.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.App;
import com.qinglu.ad.model.Push;
import com.qinglu.ad.service.AppService;
import com.qinglu.ad.service.PushService;

public class AppController extends MultiActionController {
	private NotificationManager notificationManager;
	private AppService appService;

	public AppController() {
		notificationManager = new NotificationManager();
		appService = (AppService) ServiceLocator.getService("appService");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();

		QueryResult<App> qr = appService.findApps(0);

		String sindex = request.getParameter("index");
		int index = 0;
		if (sindex != null && !"".equals(sindex))
			index = Integer.parseInt(sindex);
		Long num = qr.getNum();
		int start = index * 20;
		if (start > num) {
			start = 0;
		}
		qr = appService.findApps(start);

		mav.addObject("list", qr.getList());
		mav.addObject("maxNum", num);
		mav.setViewName("app/from");
		return mav;
	}

	// 删除app记录
	public void deleteApp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				id = Long.parseLong(s);
			}
			appService.delete(id);
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

}
