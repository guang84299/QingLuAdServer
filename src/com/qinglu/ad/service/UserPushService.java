package com.qinglu.ad.service;


import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.UserPush;

public interface UserPushService {

	void add(UserPush userPush);
	void delete(Long id);
	void update(UserPush userPush);
	UserPush find(Long id);
	QueryResult<UserPush> findUserPushs(int firstindex);	
	QueryResult<UserPush> findUserPushByPushId(long pushId,int start);
	UserPush findByPushIdAndUserName(long pushId,String username);
	QueryResult<UserPush> findByPushIdAndIsClick(long pushId,int isClick,int maxNum);
	QueryResult<UserPush> findByPushIdAndIsDownload(long pushId,int isDownload,int maxNum);
	QueryResult<UserPush> findByPushIdAndIsInstall(long pushId,int isInstall,int maxNum);
}
