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

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.presence.PresenceManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.User;
import com.qinglu.ad.service.DeviceService;
import com.qinglu.ad.service.UserService;

/**
 * A controller class to process the user related requests.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class UserController extends MultiActionController {

	private UserService userService;

	public UserController() {
		userService = ServiceLocator.getUserService();
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PresenceManager presenceManager = new PresenceManager();

		QueryResult<User> qr = userService.getUsers(0);

		String sindex = request.getParameter("index");
		int index = 0;
		if (sindex != null && !"".equals(sindex))
			index = Integer.parseInt(sindex);
		Long num = qr.getNum();
		int start = index * 20;
		if (start > num) {
			start = 0;
		}

		List<User> userList = userService.getUsers(start).getList();
		for (User user : userList) {
			if (presenceManager.isAvailable(user)) {
				user.setOnline(true);
			} else {
				user.setOnline(false);
			}

			// user.setDevice(deviceService.findByDeviceId(user.getUsername()));
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("userList", userList);
		mav.addObject("maxNum", num);
		mav.setViewName("user/list");
		return mav;
	}

	// 删除用户记录
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				id = Long.parseLong(s);
			}
			userService.removeUser(id);
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}
}
