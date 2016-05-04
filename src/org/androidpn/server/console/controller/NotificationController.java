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
package org.androidpn.server.console.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.service.DeviceService;

/** 
 * A controller class to process the notification related requests.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationController extends MultiActionController {

    private NotificationManager notificationManager;
    private DeviceService deviceService;

    public NotificationController() {
        notificationManager = new NotificationManager();
        deviceService = (DeviceService) ServiceLocator.getService("deviceService");
    }

    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // mav.addObject("list", null);
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
        String title = ServletRequestUtils.getStringParameter(request, "title");
        String message = ServletRequestUtils.getStringParameter(request,
                "message");
        String uri = ServletRequestUtils.getStringParameter(request, "uri");

        String apiKey = Config.getString("apiKey", "");

        if (broadcast.equalsIgnoreCase("all")) {
            notificationManager.sendBroadcast(apiKey, title, message, uri);
        } else if (broadcast.equalsIgnoreCase("single")){
            notificationManager.sendNotifcationToUser(apiKey, username, title,
                    message, uri);
        }
        else
        {
        	notificationManager.sendNotifcationToAppUser(apiKey, appname, title,
                    message, uri, deviceService);
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:notification.do");
        return mav;
    }
    
    //推送插屏广告
    public ModelAndView sendSpot(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String broadcast = ServletRequestUtils.getStringParameter(request,
                "broadcast2", "all");
        String adId = ServletRequestUtils.getStringParameter(request,
                "adId");
       
        String apiKey = "pushSpot";


        if (broadcast.equalsIgnoreCase("all")) {
            notificationManager.sendBroadcast(apiKey, "", adId, "");
        } else if (broadcast.equalsIgnoreCase("single")){
            notificationManager.sendNotifcationToUser(apiKey, "", "",
            		adId, "");
        }
        else
        {
        	notificationManager.sendNotifcationToAppUser(apiKey, "", "",
        			adId, "", deviceService);
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:notification.do");
        return mav;
    }

}
