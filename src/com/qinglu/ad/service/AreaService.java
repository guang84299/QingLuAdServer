package com.qinglu.ad.service;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Area;

public interface AreaService {

	void add(Area area);
	
	Area find(int id);
	
	QueryResult<Area> findAll();
}
