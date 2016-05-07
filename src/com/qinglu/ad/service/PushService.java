package com.qinglu.ad.service;


import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Push;

public interface PushService {

	void add(Push ad);
	void delete(Long id);
	void update(Push ad);
	Push find(Long id);
	QueryResult<Push> findAds(int firstindex);
}
