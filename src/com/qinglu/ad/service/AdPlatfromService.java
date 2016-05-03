package com.qinglu.ad.service;

import com.qinglu.ad.model.AdPlatfrom;

public interface AdPlatfromService {
	public void save(AdPlatfrom adPlatfrom);
	public AdPlatfrom find();
	public void update(int platfrom);
}
