package com.qinglu.ad.serviceImpl;

import java.util.LinkedHashMap;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.App;
import com.qinglu.ad.service.AppService;

public class AppServiceImpl implements AppService{

	private DaoTools daoTools;
	
	
	
	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

	public void add(App ad) {
		daoTools.add(ad);
	}

	public void delete(Long id) {
		daoTools.delete(App.class, id);
	}

	public void update(App ad) {
		daoTools.update(ad);
	}

	public App find(Long id) {
		return daoTools.find(App.class, id);
	}


	public QueryResult<App> findApps(int firstindex) {
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("id", "desc");
		return daoTools.find(App.class, null, null, firstindex, 20, lhm);
	}

	public QueryResult<App> findAppsByUserId(long userId) {
		return daoTools.find(App.class, "userId", userId+"", 0, 30, null);
	}

}
