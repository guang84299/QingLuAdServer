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
package org.androidpn.server.xmpp.push;

import java.util.List;
import java.util.Random;

import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

import com.qinglu.ad.model.App;
import com.qinglu.ad.model.Device;
import com.qinglu.ad.model.User;
import com.qinglu.ad.model.UserPush;
import com.qinglu.ad.service.AppService;
import com.qinglu.ad.service.DeviceService;
import com.qinglu.ad.service.UserService;

/** 
 * This class is to manage sending the notifcations to the users.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationManager {

    private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

    private final Log log = LogFactory.getLog(getClass());

    private SessionManager sessionManager;

    /**
     * Constructor.
     */
    public NotificationManager() {
        sessionManager = SessionManager.getInstance();
    }

    /**
     * Broadcasts a newly created notification message to all connected users.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public int sendBroadcast(String apiKey, String title, String message,
            String uri) {
        log.debug("sendBroadcast()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
            }
        }
        return num;
    }

    /**
     * Sends a newly created notification message to the specific user.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public int sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri) {
        log.debug("sendNotifcationToUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        ClientSession session = sessionManager.getSession(username);
        if (session != null) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
            }
        }
        return num;
    }
    
    public int sendNotifcationToAppUser(String apiKey, String appname,
            String title, String message, String uri,UserService userService,AppService appService) throws UserNotFoundException {
        log.debug("sendNotifcationToAppUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
        	//Device device = deviceService.findByDeviceId(session.getUsername());
        	User user = userService.getUserByUsername(session.getUsername());
        	List<App> listapp = appService.findAppsByUserId(user.getId()).getList();
        	boolean b = false;
        	for(App app : listapp)
        	{
        		if(app.getPackageName().equals(appname))
        		{
        			b = true;
        			break;
        		}
        	}
            if (session.getPresence().isAvailable()  && b) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
            }
        }
        return num;
    }
    
    //推送点击 下载 安装 过的在线用户
    public int sendBroadcastClickDownloadInstall(String apiKey, String title, String message,
            String uri,List<UserPush> list) throws UserNotFoundException {        
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
            if (session.getPresence().isAvailable() && judgeOnline(list,session.getUsername())) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
            }
        }
        return num;
    }
    
    //判断list中用户是否在线
    public boolean judgeOnline(List<UserPush> list,String username)
    {
    	for(UserPush up : list)
    	{
    		if(username.equals(up.getUsername()))
    		{
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Creates a new notification IQ and returns it.
     */
    private IQ createNotificationIQ(String apiKey, String title,
            String message, String uri) {
        Random random = new Random();
        String id = Integer.toHexString(random.nextInt());
        // String id = String.valueOf(System.currentTimeMillis());

        Element notification = DocumentHelper.createElement(QName.get(
                "notification", NOTIFICATION_NAMESPACE));
        notification.addElement("id").setText(id);
        notification.addElement("apiKey").setText(apiKey);
        notification.addElement("title").setText(title);
        notification.addElement("message").setText(message);
        notification.addElement("uri").setText(uri);

        IQ iq = new IQ();
        iq.setType(IQ.Type.set);
        iq.setChildElement(notification);

        return iq;
    }
}
