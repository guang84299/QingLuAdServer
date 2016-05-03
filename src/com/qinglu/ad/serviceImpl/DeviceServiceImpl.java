package com.qinglu.ad.serviceImpl;


import org.springframework.stereotype.Service;

import com.qinglu.ad.dao.DeviceDao;
import com.qinglu.ad.model.Device;
import com.qinglu.ad.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService{
	 private  DeviceDao deviceDao;
	
	

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}



	public boolean addDevice(Device device) {
		deviceDao.save(device);
		return true;
	}



	public Device findByDeviceId(String deviceId) {
		return deviceDao.find("deviceId",deviceId);
	}

}
