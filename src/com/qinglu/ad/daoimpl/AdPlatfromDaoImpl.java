package com.qinglu.ad.daoimpl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.qinglu.ad.dao.AdPlatfromDao;
import com.qinglu.ad.model.AdPlatfrom;

public class AdPlatfromDaoImpl extends HibernateDaoSupport implements AdPlatfromDao{

	public void save(AdPlatfrom adPlatfrom) {
		getHibernateTemplate().saveOrUpdate(adPlatfrom);
		getHibernateTemplate().flush();
	}

	public AdPlatfrom find() {
		return (AdPlatfrom) getHibernateTemplate().get(AdPlatfrom.class, 1l);
	}

	public void update(int platfrom) {
		AdPlatfrom adPlatfrom =  find();
		adPlatfrom.setPlatfrom(platfrom);
		getHibernateTemplate().update(adPlatfrom);
		getHibernateTemplate().flush();
	}

}
