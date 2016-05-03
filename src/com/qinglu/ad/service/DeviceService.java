package com.qinglu.ad.service;

import org.springframework.stereotype.Service;

import com.qinglu.ad.model.Device;

@Service
public interface DeviceService {

	public boolean addDevice(Device device);
	
	public Device findByDeviceId(String deviceId);
}
