package com.qinglu.ad.serviceImpl;

import java.util.LinkedHashMap;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Push;
import com.qinglu.ad.service.PushService;

public class PushServiceImpl implements PushService{

	private DaoTools daoTools;
	
	
	
	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

	public void add(Push ad) {
		daoTools.add(ad);
	}

	public void delete(Long id) {
		daoTools.delete(Push.class, id);
	}

	public void update(Push ad) {
		daoTools.update(ad);
	}

	public Push find(Long id) {
		return daoTools.find(Push.class, id);
	}

	public QueryResult<Push> findAds(int firstindex) {
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("id", "desc");
		return daoTools.find(Push.class, null, null, firstindex, 20, lhm);
	}

}
