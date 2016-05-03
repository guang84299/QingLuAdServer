package com.qinglu.ad.service;


import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Ad;

public interface AdService {

	void add(Ad ad);
	void delete(Long id);
	void update(Ad ad);
	Ad find(Long id);
	QueryResult<Ad> findAds(int firstindex);
}
