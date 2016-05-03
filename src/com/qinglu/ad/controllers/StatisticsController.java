package com.qinglu.ad.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.Device;
import com.qinglu.ad.service.AdService;

public class StatisticsController  extends MultiActionController{
	private NotificationManager notificationManager;
	private AdService adService;
	
	public StatisticsController() {
		notificationManager = new NotificationManager();
		adService = (AdService) ServiceLocator.getService("adService");
	}
	
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		QueryResult<Ad> qr = adService.findAds(0);
		
		String sindex = request.getParameter("index");
        int index = 0;
        if(sindex != null && !"".equals(sindex))
        	index = Integer.parseInt(sindex);
        Long num = qr.getNum();
        int start = index*20;       
        if(start > num)
        {
        	start = 0;       	
        }      		
        qr = adService.findAds(start);	
              		
		mav.addObject("list", qr.getList());
		mav.addObject("maxNum", qr.getNum());
		mav.setViewName("statistics/from");
		return mav;
	}
	
	//更新广告展示次数
	public void updateShowNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {			
			long id = 0;
			if(s != null && !"".equals(s))
			{
				id = Long.parseLong(s);
			}
			Ad ad = adService.find(id);
			ad.setShowNum(ad.getShowNum() + 1);
			adService.update(ad);
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}
	
	//更新广告点击次数
	public void updateClickNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = request.getParameter("data");
		try {
			long id = 0;
			if(s != null && !"".equals(s))
			{
				id = Long.parseLong(s);
			}
			Ad ad = adService.find(id);
			ad.setClickNum(ad.getClickNum() + 1);
			adService.update(ad);
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}
}
