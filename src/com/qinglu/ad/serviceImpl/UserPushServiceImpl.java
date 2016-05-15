package com.qinglu.ad.serviceImpl;

import java.util.LinkedHashMap;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.UserPush;
import com.qinglu.ad.service.UserPushService;

public class UserPushServiceImpl implements UserPushService{

	private DaoTools daoTools;
	
	
	
	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

	public void add(UserPush userPush) {
		daoTools.add(userPush);
	}

	public void delete(Long id) {
		daoTools.delete(UserPush.class, id);
	}

	public void update(UserPush userPush) {
		daoTools.update(userPush);
	}

	public UserPush find(Long id) {
		return daoTools.find(UserPush.class, id);
	}

	public QueryResult<UserPush> findUserPushs(int firstindex) {
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("id", "desc");
		return daoTools.find(UserPush.class, null, null, firstindex, 20, lhm);
	}

	public QueryResult<UserPush> findUserPushByPushId(long pushId,int start) {
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("id", "desc");
		return daoTools.find(UserPush.class, "pushId", pushId+"", start, 10000000, lhm);
	}

	public UserPush findByPushIdAndUserName(long pushId, String username) {
		QueryResult<UserPush> qr = daoTools.find(UserPush.class, "pushId", pushId+"", "username",username,0, 1, null);
		
		if(qr.getList() != null && qr.getList().size() > 0)
			return qr.getList().get(0);
		return null;
	}

	public QueryResult<UserPush> findByPushIdAndIsDownload(long pushId,
			int isDownload,int maxNum) {
		return daoTools.find(UserPush.class, "pushId", pushId+"","isDownload",isDownload+"", 0, maxNum, null);
	}

	public QueryResult<UserPush> findByPushIdAndIsInstall(long pushId,
			int isInstall,int maxNum) {
		return daoTools.find(UserPush.class, "pushId", pushId+"","isInstall",isInstall+"", 0, maxNum, null);
	}

	public QueryResult<UserPush> findByPushIdAndIsClick(long pushId,
			int isClick, int maxNum) {
		return daoTools.find(UserPush.class, "pushId", pushId+"","isClick",isClick+"", 0, maxNum, null);
	}

}
