/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.qinglu.ad.serviceImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.androidpn.server.dao.UserDao;
import org.androidpn.server.service.UserExistsException;
import org.androidpn.server.service.UserNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import com.qinglu.ad.dao.DaoTools;
import com.qinglu.ad.dao.QueryResult;
import com.qinglu.ad.model.Ad;
import com.qinglu.ad.model.User;
import com.qinglu.ad.service.UserService;

/** 
 * This class is the implementation of UserService.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class UserServiceImpl implements UserService {

    protected final Log log = LogFactory.getLog(getClass());

    private DaoTools daoTools;
	
	
	
	public DaoTools getDaoTools() {
		return daoTools;
	}

	public void setDaoTools(DaoTools daoTools) {
		this.daoTools = daoTools;
	}

    public User getUser(String userId) {
    	return daoTools.find(User.class, new Long(userId));
    }

    public QueryResult<User> getUsers(int firstindex) {
    	LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("id", "desc");
		return daoTools.find(User.class, null, null, firstindex, 20, lhm);
    }
    
    public QueryResult<User> getUsersFromCreatedDate(Date createDate) {
    	LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("createdDate", "desc");
		return daoTools.findGreater(User.class, "createdDate", createDate.toString(), 0, 20, lhm);
    }

    public void saveUser(User user) throws UserExistsException {
        try {
        		daoTools.add(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername()
                    + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername()
                    + "' already exists!");
        }
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
    	QueryResult<User> qr = daoTools.find(User.class, "username", username, 0, 1, null);
    	if(qr.getList() != null && qr.getList().size() > 0)
    		return qr.getList().get(0);
    	else 
    		return null;
    }

    public void removeUser(Long userId) {
    	daoTools.delete(User.class, userId);
    }

	public void updateUser(User user) {
		daoTools.update(user);
	}

}
