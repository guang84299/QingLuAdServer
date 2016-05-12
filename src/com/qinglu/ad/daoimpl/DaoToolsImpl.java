package com.qinglu.ad.daoimpl;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.persistence.Entity;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;

public class DaoToolsImpl extends HibernateDaoSupport implements DaoTools {

	public void add(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
		getHibernateTemplate().flush();		
	}

	public <T> void delete(Class<T> entityclass, Object id) {
		getHibernateTemplate().delete(find(entityclass,id));
		getHibernateTemplate().flush();
	}

	public void update(Object obj) {
		getHibernateTemplate().update(obj);
		getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> entityclass, Object id) {	
		T t = (T) getHibernateTemplate().get(entityclass, (Serializable) id);
		return t;
	}

	public <T> QueryResult<T> find(Class<T> entityclass, String columnName,
			String value, int firstindex, int maxresult,
			LinkedHashMap<String, String> orderby) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityclass);
		String orderbysq = getOrderBy(orderby);
		String sql = null;
		Query query = null;
		if(columnName!=null && value!=null)
		{
			sql = "select o from "+entityname+" o where o."+columnName+" = '"+value+"' "+orderbysq;
			query = this.getSession().createQuery(sql);
		}else{
			sql = "select o from "+entityname+" o "+orderbysq;	
			query = this.getSession().createQuery(sql);
		}	
		query.setFirstResult(firstindex).setMaxResults(maxresult);
		qr.setList(query.list());
		if(columnName!=null && value!=null)
		{
			query = this.getSession().createQuery("select count(o) from "+entityname+" o where o."+columnName+" = '"+value+"' "+orderbysq);
			qr.setNum((Long)query.uniqueResult());
		}else{
			query = this.getSession().createQuery("select count(o) from "+entityname+" o");
			qr.setNum((Long)query.uniqueResult());
		}
		this.getSession().close();
		return qr;
	}
	
	public <T> QueryResult<T> findGreater(Class<T> entityclass, String columnName,
			String value, int firstindex, int maxresult,
			LinkedHashMap<String, String> orderby) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityclass);
		String orderbysq = getOrderBy(orderby);
		String sql = null;
		Query query = null;
		if(columnName!=null && value!=null)
		{
			sql = "select o from "+entityname+" o where o."+columnName+" >= '"+value+"' "+orderbysq;
			query = this.getSession().createQuery(sql);
		}else{
			sql = "select o from "+entityname+" o "+orderbysq;	
			query = this.getSession().createQuery(sql);
		}	
		query.setFirstResult(firstindex).setMaxResults(maxresult);
		qr.setList(query.list());
		if(columnName!=null && value!=null)
		{
			query = this.getSession().createQuery("select count(o) from "+entityname+" o where o."+columnName+" = '"+value+"' "+orderbysq);
			qr.setNum((Long)query.uniqueResult());
		}else{
			query = this.getSession().createQuery("select count(o) from "+entityname+" o");
			qr.setNum((Long)query.uniqueResult());
		}	
		this.getSession().close();
		return qr;
	}
	
	//按条件查询
	protected String getOrderBy(LinkedHashMap<String, String> orderby)
	{
		StringBuffer orderbysq = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			orderbysq.append(" order by ");
			for(String key : orderby.keySet()){
				orderbysq.append("o.").append(key+" ").append(orderby.get(key)+",");
			}
			orderbysq.deleteCharAt(orderbysq.length()-1);
		}
		return orderbysq.toString();			
	}
	//获取实体对象在数据库中的表名
	protected <T> String getEntityName(Class<T> entityclass){
		String entityname = entityclass.getSimpleName();
		Entity entity = entityclass.getAnnotation(Entity.class);
		if(entity.name()!=null&& !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}

	public <T> QueryResult<T> find(Class<T> entityclass, String columnName,
			String value, String columnName2, String value2, int firstindex,
			int maxresult, LinkedHashMap<String, String> orderby) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityclass);
		String orderbysq = getOrderBy(orderby);
		String sql = null;
		Query query = null;
		if(columnName!=null && value!=null && columnName2!=null && value2!=null)
		{
			sql = "select o from "+entityname+" o where o."+columnName+" = '"+value+"' and o."+columnName2+" = '"+value2+"' " +orderbysq;
			query = this.getSession().createQuery(sql);
		}else{
			sql = "select o from "+entityname+" o "+orderbysq;	
			query = this.getSession().createQuery(sql);
		}	
		query.setFirstResult(firstindex).setMaxResults(maxresult);
		qr.setList(query.list());
		if(columnName!=null && value!=null)
		{
			query = this.getSession().createQuery("select count(o) from "+entityname+" o where o."+columnName+" = '"+value+"' and o."+columnName2+" = '"+value2+"' "+orderbysq);
			qr.setNum((Long)query.uniqueResult());
		}else{
			query = this.getSession().createQuery("select count(o) from "+entityname+" o");
			qr.setNum((Long)query.uniqueResult());
		}	
		this.getSession().close();
		return qr;
	}
}
