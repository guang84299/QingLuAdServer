package com.qinglu.ad.serviceImpl;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Area;
import com.qinglu.ad.service.AreaService;

public class AreaServiceImpl implements AreaService {
	private DaoTools daoTools;

	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

	public void add(Area area) {
		try {
			daoTools.add(area);
		} catch (Exception e) {
		}
	}

	public Area find(int id) {
		return daoTools.find(Area.class, id);
	}

	public QueryResult<Area> findAll() {
		return daoTools.find(Area.class, null, null, 0, 10000, null);
	}

}
