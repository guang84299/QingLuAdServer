package com.qinglu.ad.controllers;


import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.AdPlatfrom;
import com.qinglu.ad.model.Device;
import com.qinglu.ad.service.AdPlatfromService;
import com.qinglu.ad.service.AdService;
import com.qinglu.ad.service.DeviceService;
import com.qinglu.ad.tools.PinYinTools;

public class AdController  extends MultiActionController{
	private NotificationManager notificationManager;
	private DeviceService deviceService;
	private AdPlatfromService adPlatfromService;
	private AdService adService;

	public AdController() {
		notificationManager = new NotificationManager();
		deviceService = (DeviceService) ServiceLocator.getService("deviceService");
		adPlatfromService = (AdPlatfromService) ServiceLocator.getService("adPlatfromService");
		adService = (AdService) ServiceLocator.getService("adService");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		AdPlatfrom adPlatfrom = adPlatfromService.find();		
		     
		int platfrom = 0;
		if(adPlatfrom != null)
			platfrom = adPlatfrom.getPlatfrom();
		mav.addObject("platfrom", platfrom);
		mav.setViewName("ad/from");
		return mav;
	}

	public void send(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		String apiKey = ProtocolKey.GET_ADD;
//		String title = null;
//		String message = null;
//		String uri = null;
//		notificationManager.sendBroadcast(apiKey, title, message, uri);
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("redirect:notification.do");
//		return mav;
	}
	//获取广告平台
	public void getAdPlatfrom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdPlatfrom adPlatfrom = adPlatfromService.find();
		
		String deviceId = request.getParameter("data");
		String deviceInfo = "0";
		if(deviceId != null)
		{
			Device device = deviceService.findByDeviceId(deviceId);
			if(device != null)
				deviceInfo = device.getSubscriberId();
		}
		
		if(adPlatfrom != null)
		{
			response.getWriter().print(adPlatfrom.getPlatfrom() +"&"+deviceInfo);
		}
		else
		{
			response.getWriter().print(-1+"&"+deviceInfo);
		}		
	}
	//添加广告平台
	public void addAdPlatfrom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdPlatfrom adPlatfrom = new AdPlatfrom();
		adPlatfrom.setPlatfrom(0);
		adPlatfromService.save(adPlatfrom);
		
		response.getWriter().print(1);
	}
	
	//更新广告平台
	public void updateAdPlatfrom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		adPlatfromService.update(1);
		
		response.getWriter().print(1);
	}
	
	//获取广告
	public void getAds(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QueryResult<Ad> qr = adService.findAds(0);
		String s = JSONArray.fromObject(qr.getList()).toString();
		response.getWriter().print(s);
	}
	
	//切换广告
	public ModelAndView changeAdPlatform(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String broadcast = ServletRequestUtils.getStringParameter(request,
                "broadcast", "all");
        String username = ServletRequestUtils.getStringParameter(request,
                "username");
        String appname = ServletRequestUtils.getStringParameter(request,
                "appname");
        String ad_platfrom = ServletRequestUtils.getStringParameter(request,
                "ad_platfrom");
        adPlatfromService.update(Integer.parseInt(ad_platfrom));
        String title = "changeAd";
        String message = ad_platfrom;
        String uri = "apn" ;//ServletRequestUtils.getStringParameter(request, "uri");

        String apiKey = title;

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
        mav.setViewName("redirect:ad.do");
        return mav;
	}
	
	//上传广告
	@SuppressWarnings("deprecation")
	public void addAd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String tmp = realPath + "tmp/";
		
		File tmpFile = new File(tmp);
		if(!tmpFile.exists())
		{
			tmpFile.mkdirs();
		}
		
		String company = null;
		String type = null;
		String picPath = null;
		String downloadPath = null;
		FileItem file = null;
		
		DiskFileUpload diskFileUpload = new DiskFileUpload();
		diskFileUpload.setHeaderEncoding("utf-8");
	    // 允许文件最大长度
	    diskFileUpload.setSizeMax( 100*1024*1024 );
	    // 设置内存缓冲大小
	    diskFileUpload.setSizeThreshold( 4096 );
	    // 设置临时目录
	    diskFileUpload.setRepositoryPath( tmp );
	 
	    List fileItems = diskFileUpload.parseRequest( request );
	    Iterator iter = fileItems.iterator();
	    for( ; iter.hasNext(); ) {
	        FileItem fileItem = (FileItem) iter.next();
	        if( fileItem.isFormField() ) {
	            // 当前是一个表单项
	            if("company".equals(fileItem.getFieldName())) 
	            {
	            	company = fileItem.getString("iso8859-1");
	            }
	            if("type".equals(fileItem.getFieldName())) 
	            {
	            	type = fileItem.getString("iso8859-1");
	            }
	            if("downloadPath".equals(fileItem.getFieldName())) 
	            {
	            	downloadPath = fileItem.getString("iso8859-1");
	            }	            
	        } else {
	            // 当前是一个上传的文件
	        	picPath = fileItem.getName();
	        	file = fileItem;
	            
	        }
	    }
	    company = new String(company.getBytes("iso8859-1"),"utf-8");
	    String filePath = realPath + "images/";
	    String companyPinyin = "";
		if(company != null && !"".equals(company))
		{
			companyPinyin = PinYinTools.getPinYin(company);
			filePath += companyPinyin;
			File f = new File(filePath);
			if(!f.exists())
			{
				f.mkdirs();
			}
		}
				
		String fileName = picPath;
		int index = picPath.lastIndexOf("/");
		if(index != -1)
		{
			fileName = picPath.substring(index);			
		}
		else
		{
			index = picPath.lastIndexOf("\\");
			if(index != -1)
			{
				fileName = picPath.substring(index);			
			}
		}		    
		
		file.write( new File(filePath+"/"+fileName) );
		
		int t = 1;
		if(type != null && !"".equals(type))
			t = Integer.parseInt(type);
		Ad ad = new Ad(company, t, "images/"+companyPinyin+"/"+fileName, downloadPath);
		adService.add(ad);
		
		response.setHeader("content-type","text/html;charset=UTF-8");
		response.getWriter().print("上传成功！");		
	}
	
	//添加测试广告
	public void addTestAds(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ad ad = new Ad( "qinglu", 1, "images/qingluad/001.jpg","images/xingxing.apk");
		Ad ad2 = new Ad( "qinglu", 1, "images/qingluad/002.jpg","images/xingxing.apk");
		Ad ad3 = new Ad( "qinglu", 1, "images/qingluad/003.jpg","images/xingxing.apk");
		Ad ad4 = new Ad( "qinglu", 1, "images/qingluad/004.jpg","images/xingxing.apk");
		Ad ad5 = new Ad( "青露", 1, "images/qingluad/005.jpg","images/xingxing.apk");
		
		adService.add(ad);
		adService.add(ad2);
		adService.add(ad3);
		adService.add(ad4);
		adService.add(ad5);
				
		response.getWriter().print(1);
	}
}
