import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.androidpn.server.xmpp.push.NotificationManager;

import com.qinglu.ad.model.Ad;
import com.qinglu.ad.tools.PinYinTools;

//
//  DemoAndroidpn.java
//  FeOA
//
//  Created by LuTH on 2012-3-26.
//  Copyright 2012 flyrise. All rights reserved.
//

public class DemoAndroidpn {

	public static void main(String[] args) {

//		String apiKey = "1234567890";
//		String title = "feoa";
//		String message = "Hello World!";
//		String uri = "http://www.baidu.com";
//
//		NotificationManager notificationManager = new NotificationManager();
//		notificationManager.sendBroadcast(apiKey, title, message, uri);
		// notificationManager.sendNotifcationToUser(apiKey, username, title,
		// message, uri);
		
//		String path = "a/b/c.png";
//		String s = path.substring(0, path.lastIndexOf("/"));
//		System.out.println(s);
//		for(int i=0;i<10;i++)
//		System.out.println((int)(Math.random()*10%5));
//		Ad ad = new Ad(1, "ss", 1, "sss", "kkk");
//		System.out.println(objectToJson(ad).toString());
//sudo cp /usr/local/mysql/support-files/my-default.cnf /etc/my.cnf		
		//System.out.print(PinYinTools.getPinYin("我的"));
		 String uuidRaw = UUID.randomUUID().toString();
		 System.out.println(uuidRaw);
		 
//		 [deviceId=867875022577552,
//				 phoneNumber=+8618626058374,
//				 networkOperatorName=中国联通,
//				 simSerialNumber=89860115834016195104,
//				 networkCountryIso=cn,
//				 networkOperator=46001, 
//				 networkType=WIFI, 
//				 location=[6297,8816661,-1], 
//				 phoneType=1, 
//				 subscriberId=460016072667533,
//				 packageName=com.guang.migong.www,
//				 appName=Demo,
//				 model=ATH-AL00, 
//				 release=5.1.1
//				 ]
	}
	

	
}