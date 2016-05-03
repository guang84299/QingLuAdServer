package com.qinglu.ad.serviceImpl;

import com.qinglu.ad.dao.AdPlatfromDao;
import com.qinglu.ad.model.AdPlatfrom;
import com.qinglu.ad.service.AdPlatfromService;

public class AdPlatfromServiceImpl implements AdPlatfromService{

	
	private AdPlatfromDao adPlatfromDao;
	
	
	
	public AdPlatfromDao getAdPlatfromDao() {
		return adPlatfromDao;
	}

	public void setAdPlatfromDao(AdPlatfromDao adPlatfromDao) {
		this.adPlatfromDao = adPlatfromDao;
	}

	public void save(AdPlatfrom adPlatfrom) {
		adPlatfromDao.save(adPlatfrom);
	}

	public AdPlatfrom find() {
		return adPlatfromDao.find();
	}

	public void update(int platfrom) {
		adPlatfromDao.update(platfrom);
	}

}
