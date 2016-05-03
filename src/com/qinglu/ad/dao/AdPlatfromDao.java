package com.qinglu.ad.dao;

import com.qinglu.ad.model.AdPlatfrom;

public interface AdPlatfromDao {
	public void save(AdPlatfrom adPlatfrom);
	public AdPlatfrom find();
	public void update(int platfrom);
}
