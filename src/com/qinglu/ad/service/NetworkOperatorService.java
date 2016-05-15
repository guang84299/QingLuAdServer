package com.qinglu.ad.service;

import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.NetworkOperator;

public interface NetworkOperatorService {
	
	void add(NetworkOperator networkOperator);

	NetworkOperator find(int id);

	NetworkOperator find(String name);

	QueryResult<NetworkOperator> findAll();
}
