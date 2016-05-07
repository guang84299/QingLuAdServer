package com.qinglu.ad.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Push;
import com.qinglu.ad.model.UserPush;
import com.qinglu.ad.service.PushService;
import com.qinglu.ad.service.UserPushService;

public class PushStatisticsController extends MultiActionController {
	private NotificationManager notificationManager;
	private PushService pushService;
	private UserPushService userPushService;

	public PushStatisticsController() {
		notificationManager = new NotificationManager();
		pushService = (PushService) ServiceLocator.getService("pushService");
		userPushService = (UserPushService) ServiceLocator
				.getService("userPushService");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();

		QueryResult<Push> qr = pushService.findAds(0);

		String sindex = request.getParameter("index");
		int index = 0;
		if (sindex != null && !"".equals(sindex))
			index = Integer.parseInt(sindex);
		Long num = qr.getNum();
		int start = index * 20;
		if (start > num) {
			start = 0;
		}
		qr = pushService.findAds(start);

		mav.addObject("list", qr.getList());
		mav.addObject("maxNum", num);
		mav.setViewName("pushStatistics/from");
		return mav;
	}

	// 更新广告展示次数
	public void updateShowNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				s = s.split("&&&&&")[0];
				id = Long.parseLong(s);
			}
			Push push = pushService.find(id);
			push.setShowNum(push.getShowNum() + 1);
			pushService.update(push);
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 更新广告点击次数
	public void updateClickNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			String username = null;
			if (s != null && !"".equals(s)) {
				String arr[] = s.split("&&&&&");
				id = Long.parseLong(arr[0]);
				username = arr[1];
			}
			Push push = pushService.find(id);
			push.setClickNum(push.getClickNum() + 1);
			pushService.update(push);

			UserPush pushUser = new UserPush(push.getId(), username, 1, 0, 0);
			userPushService.add(pushUser);

			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 更新广告下载次数
	public void updateDownloadNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			String username = null;
			if (s != null && !"".equals(s)) {
				String arr[] = s.split("&&&&&");
				id = Long.parseLong(arr[0]);
				username = arr[1];
			}
			Push push = pushService.find(id);
			push.setDownloadNum(push.getDownloadNum() + 1);
			pushService.update(push);

			UserPush pushUser = userPushService.findByPushIdAndUserName(id,
					username);
			if (pushUser != null) {
				pushUser.setIsDownload(1);
				userPushService.update(pushUser);
			}

			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 更新广告安装次数
	public void updateInstallNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			String username = null;
			if (s != null && !"".equals(s)) {
				String arr[] = s.split("&&&&&");
				id = Long.parseLong(arr[0]);
				username = arr[1];
			}
			Push push = pushService.find(id);
			push.setInstallNum(push.getInstallNum() + 1);
			pushService.update(push);

			UserPush pushUser = userPushService.findByPushIdAndUserName(id,
					username);
			if (pushUser != null) {
				pushUser.setIsInstall(1);
				userPushService.update(pushUser);
			}

			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 获取当前push的点击人
	public void getUserPushByClick(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				id = Long.parseLong(s);
			}
			List<UserPush> list = userPushService.findUserPushByPushId(id, 5)
					.getList();
			String data = JSONArray.fromObject(list).toString();
			response.getWriter().print(data);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 获取当前push的下载人
	public void getUserPushByDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				id = Long.parseLong(s);
			}
			List<UserPush> list = userPushService.findByPushIdAndIsDownload(id,
					1, 5).getList();
			String data = JSONArray.fromObject(list).toString();
			response.getWriter().print(data);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 获取当前push的安装人
	public void getUserPushByInstall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				id = Long.parseLong(s);
			}
			List<UserPush> list = userPushService.findByPushIdAndIsInstall(id,
					1, 5).getList();
			String data = JSONArray.fromObject(list).toString();
			response.getWriter().print(data);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}

	// 删除push记录
	public void deletePush(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if (s != null && !"".equals(s)) {
				id = Long.parseLong(s);
			}
			pushService.delete(id);
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}
}
