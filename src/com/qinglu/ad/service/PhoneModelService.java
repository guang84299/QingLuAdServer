package com.qinglu.ad.service;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.PhoneModel;

public interface PhoneModelService {

	void add(PhoneModel phoneModel);
	
	PhoneModel find(int id);
	
	PhoneModel find(String model);
	
	QueryResult<PhoneModel> findAll();
}
