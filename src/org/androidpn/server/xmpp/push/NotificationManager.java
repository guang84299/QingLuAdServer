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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.qinglu.ad.service.UserPushService;
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
    //判断是否符合地区
    public boolean judeProvince_city(String area_province,String area_city,User user)
    {
    	if(area_province == null || "".equals(area_province))
    		return true;
    	if(area_city == null || "".equals(area_city))
    		return true;
    	boolean province_city = false;
    	//判断是否选择所有的省
    	if(!"all".equals(area_province))
    	{
    		//是否选择当前省的所有市
    		if(!"all".equals(area_city))
    		{
    			if(area_province.equals(user.getProvince()) && area_city.equals(user.getCity()))
    			{
    				province_city = true;
    			}
    		}
    		else
    		{
    			if(area_province.equals(user.getProvince()))
    			{
    				province_city = true;
    			}
    		}
    	}
    	else
    	{
    		province_city = true;
    	}
    	return province_city;
    }
   
    //判断是否符合手机型号
    public boolean judeModel(String model,User user)
    {
    	if("all".equals(model))
    	{
    		return true;
    	}
    	else
    	{
    		if(user.getModel().equals(model))
    		{
    			return true;
    		}
    	}
    	return false;
    }
 
    //判断是否符合运营商
    public boolean judeNetwork_operator(String network_operator,User user)
    {
    	if("all".equals(network_operator))
    	{
    		return true;
    	}
    	else
    	{
    		if(user.getNetworkOperatorName().equals(network_operator))
    			return true;
    	}
    	return false;
    }
   
    //判断是否在session区间内
    public boolean judeSessionFromTo(String session_from,String session_to,long id)
    {
    	if(session_from == null || "".equals(session_from))
    		return true;
    	if(session_to == null || "".equals(session_to))
    		return true;
    	int from  = Integer.parseInt(session_from);
    	int to = Integer.parseInt(session_to);
    	
    	if(to == 0)
    		return true;
    	if(id >= from && id <= to)
    		return true;
    	return false;
    }
   
    //判断是否属于该应用
    public boolean judeApp(String appname,long id,AppService appService)
    {
    	List<App> listapp = appService.findAppsByUserId(id).getList();
    	boolean b = false;
    	for(App app : listapp)
    	{
    		if(app.getPackageName().equals(appname))
    		{
    			b = true;
    			break;
    		}
    	}
    	return b;
    }
  
    //判断是否在日期内
    public boolean judeCreateDate(String createDate_from,String createDate_to,Date createDate)
    {
    	if(createDate_from == null || "".equals(createDate_from))
    		return true;
    	if(createDate_to == null || "".equals(createDate_to))
    		return true;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			Date from = sdf.parse(createDate_from);
			Date to = sdf.parse(createDate_to);
			
			if(createDate.getTime() >= from.getTime() && createDate.getTime() <= to.getTime())
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return false;
    }
    /**
     * Broadcasts a newly created notification message to all connected users.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     * @throws UserNotFoundException 
     */
    public int sendBroadcast(String apiKey, String title, String message,
            String uri,UserService userService,UserPushService userPushService,String area_province,String area_city,
            String phone_model,String network_operator,String session_from,String session_to,
            String createDate_from,String createDate_to) throws UserNotFoundException {
        log.debug("sendBroadcast()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
        	User user = userService.getUserByUsername(session.getUsername());
        	
            if (session.getPresence().isAvailable() 
            		&& judeProvince_city(area_province,area_city,user)
            		&& judeModel(phone_model,user)
            		&& judeNetwork_operator(network_operator,user)
            		&& judeSessionFromTo(session_from,session_to,session.getConnection().getSessionId())
            		&& judeCreateDate(createDate_from,createDate_to,user.getCreatedDate())) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
                
                String pushId = message.split("&&&&&")[1];
                UserPush pushUser = new UserPush(Long.parseLong(pushId), user.getUsername(), 0, 0, 0);
    			userPushService.add(pushUser);
            }
        }
        return num;
    }
    //sendBroadcast 2
    public int sendBroadcast(String apiKey, String title, String message,
            String uri) throws UserNotFoundException {
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
     * @throws UserNotFoundException 
     * @throws NumberFormatException 
     */
    public int sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri,UserPushService userPushService) throws NumberFormatException, UserNotFoundException {
        log.debug("sendNotifcationToUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        ClientSession session = sessionManager.getSession(username);
        if (session != null) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
                
                String pushId = message.split("&&&&&")[1];
                UserPush pushUser = new UserPush(Long.parseLong(pushId), session.getUsername(), 0, 0, 0);
    			userPushService.add(pushUser);
            }
        }
        return num;
    }
    //sendNotifcationToUser 2
    public int sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri) throws NumberFormatException, UserNotFoundException {
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
            String title, String message, String uri,UserService userService,
            AppService appService,UserPushService userPushService,String area_province,String area_city,
            String phone_model,String network_operator,String session_from,String session_to,
            String createDate_from,String createDate_to) throws UserNotFoundException {
        log.debug("sendNotifcationToAppUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
        	User user = userService.getUserByUsername(session.getUsername());
        	
            if (session.getPresence().isAvailable()  
            		&& judeApp(appname,user.getId(),appService)
            		&& judeProvince_city(area_province,area_city,user)
            		&& judeModel(phone_model,user)
            		&& judeNetwork_operator(network_operator,user)
            		&& judeSessionFromTo(session_from,session_to,session.getConnection().getSessionId())
            		&& judeCreateDate(createDate_from,createDate_to,user.getCreatedDate())) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
                
                String pushId = message.split("&&&&&")[1];
                UserPush pushUser = new UserPush(Long.parseLong(pushId), session.getUsername(), 0, 0, 0);
    			userPushService.add(pushUser);
            }
        }
        return num;
    }
    //sendNotifcationToAppUser 2
    public int sendNotifcationToAppUser(String apiKey, String appname,
            String title, String message, String uri,UserService userService,
            AppService appService) throws UserNotFoundException {
        log.debug("sendNotifcationToAppUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
        	User user = userService.getUserByUsername(session.getUsername());
        	
            if (session.getPresence().isAvailable()  
            		&& judeApp(appname,user.getId(),appService)) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
            }
        }
        return num;
    }
    
    //推送点击 下载 安装 过的在线用户
    public int sendBroadcastClickDownloadInstall(String apiKey, String title, String message,
            String uri,List<UserPush> list,UserPushService userPushService) throws UserNotFoundException {        
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        int num = 0;
        for (ClientSession session : sessionManager.getSessions()) {
            if (session.getPresence().isAvailable() && judgeOnline(list,session.getUsername())) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                num++;
                
                String pushId = message.split("&&&&&")[1];
                UserPush pushUser = new UserPush(Long.parseLong(pushId), session.getUsername(), 0, 0, 0);
    			userPushService.add(pushUser);
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
