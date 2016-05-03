package com.qinglu.ad.serviceImpl;

import java.util.LinkedHashMap;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Ad;
import com.qinglu.ad.service.AdService;

public class AdServiceImpl implements AdService{

	private DaoTools daoTools;
	
	
	
	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

	public void add(Ad ad) {
		daoTools.add(ad);
	}

	public void delete(Long id) {
		daoTools.delete(Ad.class, id);
	}

	public void update(Ad ad) {
		daoTools.update(ad);
	}

	public Ad find(Long id) {
		return daoTools.find(Ad.class, id);
	}

	public QueryResult<Ad> findAds(int firstindex) {
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("id", "desc");
		return daoTools.find(Ad.class, null, null, firstindex, 20, lhm);
	}

}
