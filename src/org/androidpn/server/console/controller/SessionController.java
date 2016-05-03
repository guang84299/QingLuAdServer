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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.console.vo.SessionVO;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.xmpp.packet.Presence;

import com.qinglu.ad.model.Device;
import com.qinglu.ad.service.DeviceService;

/** 
 * A controller class to process the session related requests.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class SessionController extends MultiActionController {
	 private DeviceService deviceService;
    //private UserService userService;

    public SessionController() {
        //userService = ServiceLocator.getUserService();
    	deviceService = (DeviceService) ServiceLocator.getService("deviceService");
    }

    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ClientSession[] sessions = new ClientSession[0];
        sessions = SessionManager.getInstance().getSessions().toArray(sessions);
        
        String sindex = request.getParameter("index");
        int index = 0;
        if(sindex != null && !"".equals(sindex))
        	index = Integer.parseInt(sindex);
        int num = sessions.length;
        int start = index*20;
        int end = (index+1)*20;
        
        if(start > num)
        {
        	start = 0;
        	if(num <= 20)
        		end = num;
        	else
        		end = 20;
        }
        else
        {
        	if(num <= end)
        		end = num;
        }
        List<SessionVO> voList = new ArrayList<SessionVO>();
        for (int i=start;i<end;i++) {
        	ClientSession sess = sessions[i];
            SessionVO vo = new SessionVO();
            vo.setUsername(sess.getUsername());
            
            // Status
            if (sess.getStatus() == Session.STATUS_CONNECTED) {
                vo.setStatus("CONNECTED");
            } else if (sess.getStatus() == Session.STATUS_AUTHENTICATED) {
                vo.setStatus("AUTHENTICATED");
            } else if (sess.getStatus() == Session.STATUS_CLOSED) {
                vo.setStatus("CLOSED");
            } else {
                vo.setStatus("UNKNOWN");
            }
            // Presence
            if (!sess.getPresence().isAvailable()) {
                vo.setPresence("Offline");
            } else {
                Presence.Show show = sess.getPresence().getShow();
                if (show == null) {
                    vo.setPresence("Online");
                } else if (show == Presence.Show.away) {
                    vo.setPresence("Away");
                } else if (show == Presence.Show.chat) {
                    vo.setPresence("Chat");
                } else if (show == Presence.Show.dnd) {
                    vo.setPresence("Do Not Disturb");
                } else if (show == Presence.Show.xa) {
                    vo.setPresence("eXtended Away");
                } else {
                    vo.setPresence("Unknown");
                }
            }
            vo.setClientIP(sess.getHostAddress());
            vo.setCreatedDate(sess.getCreationDate());
            Device device = deviceService.findByDeviceId(sess.getUsername());
            vo.setDevice(device);
            vo.setResource(device.getAppName());
            
            voList.add(vo);
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("sessionList", voList);
        mav.addObject("maxNum", num);
        mav.setViewName("session/list");
        return mav;
    }

}
