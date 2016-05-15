package com.qinglu.ad.serviceImpl;

import java.util.List;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.NetworkOperator;
import com.qinglu.ad.service.NetworkOperatorService;

public class NetworkOperatorServiceImpl implements NetworkOperatorService{
	private DaoTools daoTools;

	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}
	public void add(NetworkOperator networkOperator) {
		try {
			daoTools.add(networkOperator);
		} catch (Exception e) {
		}
	}

	public NetworkOperator find(int id) {
		return daoTools.find(NetworkOperator.class, id);
	}

	public NetworkOperator find(String name) {
		List<NetworkOperator> list = daoTools.find(NetworkOperator.class, "name", name, 0, 1, null).getList();
		if(list != null && list.size() > 0)
		return list.get(0);
		return null;
	}

	public QueryResult<NetworkOperator> findAll() {
		return daoTools.find(NetworkOperator.class, null, null, 0, 100, null);
	}

}
