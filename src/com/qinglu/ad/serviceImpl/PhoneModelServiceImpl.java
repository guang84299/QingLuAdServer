package com.qinglu.ad.serviceImpl;

import java.util.List;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.PhoneModel;
import com.qinglu.ad.service.PhoneModelService;

public class PhoneModelServiceImpl implements PhoneModelService {
	private DaoTools daoTools;

	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

	public void add(PhoneModel phoneModel) {
		try {
			daoTools.add(phoneModel);
		} catch (Exception e) {
			
		}
	}

	public PhoneModel find(int id) {
		return daoTools.find(PhoneModel.class, id);
	}

	public PhoneModel find(String model) {
		List<PhoneModel> list = daoTools.find(PhoneModel.class, "model", model, 0, 1, null).getList();
		if(list != null && list.size() > 0)
		return list.get(0);
		return null;
	}

	public QueryResult<PhoneModel> findAll() {
		return daoTools.find(PhoneModel.class, null, null, 0, 100000, null);
	}

}
