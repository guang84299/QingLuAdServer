package com.qinglu.ad.daoimpl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.qinglu.ad.dao.DeviceDao;
import com.qinglu.ad.model.Device;

public class DeviceDaoImpl extends HibernateDaoSupport implements DeviceDao {

	public void save(Device device) {
		getHibernateTemplate().saveOrUpdate(device);
		getHibernateTemplate().flush();
	}

	public void remove(Long id) {
		getHibernateTemplate().delete(find(id));
	}

	public Device find(Long id) {
		return (Device) getHibernateTemplate().get(Device.class, id);
	}

	public boolean exists(Long id) {
		Device device = (Device) getHibernateTemplate().get(Device.class, id);
		return device != null;
	}

	public List<Device> findAll() {
		return getHibernateTemplate().find(
				"from Device d order by d.id desc");
	}

	public Device find(String colName, String value) {
		List<Device> list = getHibernateTemplate().find(
				"from Device d where d."+colName+"=?",value);
		if(list.size() > 0)
			return list.get(0);
		return null;
	}

}
