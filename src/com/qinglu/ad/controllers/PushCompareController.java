package com.qinglu.ad.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.presence.PresenceManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.User;
import com.qinglu.ad.model.UserPush;
import com.qinglu.ad.service.UserPushService;
import com.qinglu.ad.service.UserService;

public class PushCompareController extends MultiActionController{
	private UserService userService;
	private UserPushService userPushService;
	
	public PushCompareController()
	{
		userService = (UserService) ServiceLocator.getService("userService");
		userPushService = (UserPushService) ServiceLocator.getService("userPushService");
	}
	
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {			
		String pushId = request.getParameter("pushId");
		if(pushId == null || "".equals(pushId))
			pushId = "0";
		long pid = Long.parseLong(pushId);
		QueryResult<UserPush> qr = userPushService.findUserPushByPushId(pid, 0);
		
		String sindex = request.getParameter("index");
		int index = 0;
		if (sindex != null && !"".equals(sindex))
			index = Integer.parseInt(sindex);
		Long num = qr.getNum();
		int start = index * 20;
		if (start > num) {
			start = 0;
		}
		
		qr = userPushService.findUserPushByPushId(pid, start);
		List<User> list = new ArrayList<User>();
		PresenceManager presenceManager = new PresenceManager();
		for(UserPush up : qr.getList())
		{
			User u = userService.getUserByUsername(up.getUsername());
			if (presenceManager.isAvailable(u)) {
				u.setOnline(true);
			} else {
				u.setOnline(false);
			}
			list.add(u);
		}
		
		ModelAndView mav = new ModelAndView();					
		mav.addObject("list", list);
		mav.addObject("maxNum", num);
		mav.addObject("pushId", pushId);
		mav.setViewName("pushCompare/from");
		return mav;
	}
	
	public void deletePushUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pushId = request.getParameter("pushId");
		String name = request.getParameter("name");
		if(pushId != null && !"".equals(pushId) && name != null && !"".equals(name))
		{
			UserPush up = userPushService.findByPushIdAndUserName(Long.parseLong(pushId), name);
			userPushService.delete(up.getId());
		}
	}
}
