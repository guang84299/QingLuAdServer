package com.qinglu.ad.dao;

import java.util.List;

import com.qinglu.ad.model.Device;


public interface DeviceDao {

	 public void save(Device device);

	 public void remove(Long id);
	 
	 public Device find(Long id);
	 
	 public Device find(String colName,String value);
	 
	 public boolean exists(Long id);
	 
	 public List<Device> findAll();
	 
}
