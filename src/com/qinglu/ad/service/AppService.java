package com.qinglu.ad.service;


import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.App;

public interface AppService {

	void add(App app);
	void delete(Long id);
	void update(App app);
	App find(Long id);
	QueryResult<App> findApps(int firstindex);
	QueryResult<App> findAppsByUserId(long userId);
}
